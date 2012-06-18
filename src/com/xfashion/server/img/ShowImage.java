package com.xfashion.server.img;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.xfashion.shared.ArticleTypeImageDTO;

public class ShowImage extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
	
	private ImageUploadServiceImpl imageUploadService = new ImageUploadServiceImpl();

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
		String imageId = req.getParameter("id");
		if (imageId != null && imageId.length() > 0) {
			ArticleTypeImageDTO ati = imageUploadService.readArticleTypeImage(imageId);
			BlobKey blobKey = new BlobKey(ati.getBlobKey());
			blobstoreService.serve(blobKey, res);
		}
	}
}
