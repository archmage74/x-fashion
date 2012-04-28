package com.xfashion.client.img;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.xfashion.shared.img.ArticleTypeImageDTO;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("imageUploadService")
public interface ImageUploadService extends RemoteService {
	
	String createUploadUrl() throws IllegalArgumentException;
	
	ArticleTypeImageDTO createArticleTypeImage(ArticleTypeImageDTO dto) throws IllegalArgumentException;
	
	ArticleTypeImageDTO readArticleTypeImage(Long id) throws IllegalArgumentException;

	List<ArticleTypeImageDTO> readArticleTypeImages() throws IllegalArgumentException;

}
