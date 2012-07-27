package com.xfashion.server.img;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.xfashion.client.img.ImageUploadService;
import com.xfashion.server.PMF;
import com.xfashion.shared.ArticleTypeImageDTO;

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
		Images images = readArticleTypeImages(pm);
		ArticleTypeImage articleTypeImage = new ArticleTypeImage(dto);
		images.getArticleTypeImages().add(articleTypeImage);
		return pm.makePersistent(articleTypeImage);
	}
	
	@Override
	public ArticleTypeImageDTO readArticleTypeImage(String key) throws IllegalArgumentException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		ArticleTypeImageDTO dto = null;
		try {
			ArticleTypeImage ati = readArticleTypeImage(pm, key);
			if (ati != null) {
				dto = ati.createDTO(imagesService);
			}
		} finally {
			pm.close();
		}
		return dto;
	}
	
	private ArticleTypeImage readArticleTypeImage(PersistenceManager pm, String key) {
		return readArticleTypeImage(pm, KeyFactory.stringToKey(key));
	}
	
	private ArticleTypeImage readArticleTypeImage(PersistenceManager pm, Key key) {
		return pm.getObjectById(ArticleTypeImage.class, key);
	}
	
	@Override
	public List<ArticleTypeImageDTO> readArticleTypeImages() throws IllegalArgumentException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		List<ArticleTypeImageDTO> dtos = null;
		try {
			Images images = readArticleTypeImages(pm);
			dtos = images.getArticleTypeImageDtos(imagesService);
		} finally {
			pm.close();
		}
		return dtos;
	}
	
	@SuppressWarnings("unchecked")
	private Images readArticleTypeImages(PersistenceManager pm) {
		Query query = pm.newQuery(Images.class);
		Images item;
		List<Images> items = (List<Images>) query.execute();
		if (items.size() == 0) {
			item = new Images();
			item = pm.makePersistent(item);
		} else {
			item = items.get(0);
		}
		return item;
	}

	public Map<String, String> readImageUrls(Collection<String> articleTypeImageKeys) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		HashMap<String, String> urls = new HashMap<String, String>();
		try {
			for (String key: articleTypeImageKeys) {
				ArticleTypeImage image = readArticleTypeImage(pm, key);
				urls.put(key, image.createDTO(imagesService).getImageUrl());
			}
		} finally {
			pm.close();
		}
		return urls;
	}
	
}
