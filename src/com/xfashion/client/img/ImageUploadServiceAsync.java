package com.xfashion.client.img;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.xfashion.shared.img.ArticleTypeImageDTO;

public interface ImageUploadServiceAsync {

	void createUploadUrl(AsyncCallback<String> callback);

	void createArticleTypeImage(ArticleTypeImageDTO dto, AsyncCallback<ArticleTypeImageDTO> callback);

	void readArticleTypeImage(Long id, AsyncCallback<ArticleTypeImageDTO> callback);

	void readArticleTypeImages(AsyncCallback<List<ArticleTypeImageDTO>> callback);

}
