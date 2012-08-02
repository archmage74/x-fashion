package com.xfashion.client.img;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.xfashion.shared.ArticleTypeImageDTO;

public interface ImageUploadServiceAsync {

	void createUploadUrl(AsyncCallback<String> callback);

	void createArticleTypeImage(ArticleTypeImageDTO dto, AsyncCallback<ArticleTypeImageDTO> callback);

	void readArticleTypeImage(String key, AsyncCallback<ArticleTypeImageDTO> callback);

	void readArticleTypeImages(int from, int to, AsyncCallback<List<ArticleTypeImageDTO>> callback);

}
