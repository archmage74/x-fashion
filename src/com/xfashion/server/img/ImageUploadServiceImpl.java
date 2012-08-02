package com.xfashion.server.img;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

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

	private static Logger log = Logger.getLogger(ImageUploadServiceImpl.class.getName());
	
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
	public List<ArticleTypeImageDTO> readArticleTypeImages(int from, int to) throws IllegalArgumentException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		List<ArticleTypeImageDTO> dtos = new ArrayList<ArticleTypeImageDTO>();
		try {
			Images images = readArticleTypeImages(pm);
			int cnt = (int) from;
			List<ArticleTypeImage> articleTypeImages = images.getArticleTypeImages(); 
			while (cnt < to && cnt < articleTypeImages.size()) {
				try {
					dtos.add(articleTypeImages.get(cnt).createDTO(imagesService));
				} catch (Exception e) {
					log.warning("could not create ArticleTypeImageDTO for image with index " + (from + cnt) + ", " + e.getMessage());
				}
				cnt++;
			}
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

	private Collection<ArticleTypeImage> readArticleTypeImagesByStrings(PersistenceManager pm, Collection<String> keyStrings) {
		Collection<Key> keys = new ArrayList<Key>(keyStrings.size());
		for (String s : keyStrings) {
			keys.add(KeyFactory.stringToKey(s));
		}
		return readArticleTypeImages(pm, keys);
	}
	
	@SuppressWarnings("unchecked")
	private Collection<ArticleTypeImage> readArticleTypeImages(PersistenceManager pm, Collection<Key> keys) {
		Query query = pm.newQuery(ArticleTypeImage.class, ":p.contains(key)");
		Collection<ArticleTypeImage> images = (Collection<ArticleTypeImage>) query.execute(keys);
		return images;
	}
	
	public Map<String, String> readImageUrls(Collection<String> articleTypeImageKeys) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		HashMap<String, String> urls = new HashMap<String, String>();
		try {
			BatchIterator<String> batchIterator = new BatchIterator<String>(articleTypeImageKeys, 30);
			Collection<String> batchKeys = null;
			while ((batchKeys = batchIterator.next()) != null) {
				Collection<ArticleTypeImage> images = readArticleTypeImagesByStrings(pm, batchKeys);
				for (ArticleTypeImage image : images) {
					urls.put(image.getKeyString(), image.createDTO(imagesService).getImageUrl());
				}
			}
		} finally {
			pm.close();
		}
		return urls;
	}
	
}
