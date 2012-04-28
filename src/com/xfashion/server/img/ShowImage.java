package com.xfashion.server.img;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.xfashion.shared.img.ArticleTypeImageDTO;

public class ShowImage extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
	
	private ImageUploadServiceImpl imageUploadService = new ImageUploadServiceImpl();

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
		String id = req.getParameter("id");
		if (id != null && id.length() > 0) {
			Long imageId = Long.parseLong(id);
			ArticleTypeImageDTO ati = imageUploadService.readArticleTypeImage(imageId);
			
			BlobKey blobKey = new BlobKey(ati.getBlobKey());
			blobstoreService.serve(blobKey, res);
		}
	}
}
