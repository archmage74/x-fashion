package com.xfashion.client;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.xfashion.shared.ArticleTypeDTO;
import com.xfashion.shared.BrandDTO;
import com.xfashion.shared.CategoryDTO;
import com.xfashion.shared.StyleDTO;

public interface ArticleTypeServiceAsync {

	void createCategories(AsyncCallback<Void> callback);
	
	void readCategories(AsyncCallback<List<CategoryDTO>> callback);

	void saveCategory(CategoryDTO category, AsyncCallback<Void> callback);

	void readStyles(AsyncCallback<List<StyleDTO>> callback);

	void addStyle(StyleDTO style, AsyncCallback<Void> callback);

	void readBrands(AsyncCallback<List<BrandDTO>> callback);

	void addBrand(BrandDTO brand, AsyncCallback<Void> callback);

	void addArticleType(ArticleTypeDTO articleType, AsyncCallback<Void> callback);

	void readArticleTypes(AsyncCallback<List<ArticleTypeDTO>> callback);

}
