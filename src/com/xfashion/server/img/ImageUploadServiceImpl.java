package com.xfashion.server.img;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.xfashion.client.img.ImageUploadService;
import com.xfashion.server.PMF;
import com.xfashion.shared.img.ArticleTypeImageDTO;

public class ImageUploadServiceImpl extends RemoteServiceServlet implements ImageUploadService {

	private static final long serialVersionUID = 1L;

	protected BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
	
	protected ImagesService imagesService = ImagesServiceFactory.getImagesService();
	
	@Override
	public String createUploadUrl() throws IllegalArgumentException {
		String url = blobstoreService.createUploadUrl("/img/imageupload");
		url = url.replaceAll("http://necromancer:8888", "http://127.0.0.1:8888");
		return url;
	}
	
	@Override
	public ArticleTypeImageDTO createArticleTypeImage(ArticleTypeImageDTO dto) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			ArticleTypeImage ati = createArticleTypeImage(pm, dto);
			dto = ati.createDTO(imagesService);
		} finally {
			pm.close();
		}
		return dto;
	}
	
	private ArticleTypeImage createArticleTypeImage(PersistenceManager pm, ArticleTypeImageDTO dto) {
		ArticleTypeImage ati = new ArticleTypeImage(dto);
		return pm.makePersistent(ati);
	}
	
	@Override
	public ArticleTypeImageDTO readArticleTypeImage(Long id) throws IllegalArgumentException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		ArticleTypeImageDTO dto = null;
		try {
			ArticleTypeImage ati = readArticleTypeImage(pm, id);
			if (ati != null) {
				dto = ati.createDTO(imagesService);
			}
		} finally {
			pm.close();
		}
		return dto;
	}
	
	private ArticleTypeImage readArticleTypeImage(PersistenceManager pm, Long id) {
		return pm.getObjectById(ArticleTypeImage.class, id);
	}
	
	@Override
	public List<ArticleTypeImageDTO> readArticleTypeImages() throws IllegalArgumentException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		List<ArticleTypeImageDTO> dtos = new ArrayList<ArticleTypeImageDTO>();
		try {
			List<ArticleTypeImage> atis = readArticleTypeImages(pm);
			for (ArticleTypeImage ati : atis) {
				dtos.add(ati.createDTO(imagesService));
			}
		} finally {
			pm.close();
		}
		return dtos;
	}
	
	@SuppressWarnings("unchecked")
	private List<ArticleTypeImage> readArticleTypeImages(PersistenceManager pm) {
		Query query = pm.newQuery(ArticleTypeImage.class);
		List<ArticleTypeImage> images = (List<ArticleTypeImage>) query.execute();
		return images;
	}
}
