package com.xfashion.client.at;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.xfashion.shared.ArticleTypeDTO;
import com.xfashion.shared.BrandDTO;
import com.xfashion.shared.CategoryDTO;
import com.xfashion.shared.ColorDTO;
import com.xfashion.shared.SizeDTO;
import com.xfashion.shared.StyleDTO;

public interface ArticleTypeServiceAsync {

	// categories
	void createCategories(AsyncCallback<Void> callback);
	
	void createCategory(CategoryDTO category, AsyncCallback<CategoryDTO> callback);

	void readCategories(AsyncCallback<List<CategoryDTO>> callback);

	void updateCategory(CategoryDTO category, AsyncCallback<Void> callback);
	
	void deleteCategory(CategoryDTO category, AsyncCallback<Void> callback);


	// styles
	void createStyle(StyleDTO style, AsyncCallback<StyleDTO> callback);

	void readStyles(AsyncCallback<List<StyleDTO>> callback);

	void updateStyle(StyleDTO dto, AsyncCallback<Void> callback);

	void deleteStyle(StyleDTO brand, AsyncCallback<Void> callback);

	
	// brands
	void createBrand(BrandDTO brand, AsyncCallback<BrandDTO> callback);

	void readBrands(AsyncCallback<List<BrandDTO>> callback);

	void updateBrand(BrandDTO dto, AsyncCallback<Void> callback);

	void deleteBrand(BrandDTO brand, AsyncCallback<Void> callback);

	
	// sizes
	void readSizes(AsyncCallback<List<SizeDTO>> callback);

	void addSize(SizeDTO brand, AsyncCallback<Void> callback);

	
	// colors
	void readColors(AsyncCallback<List<ColorDTO>> callback);

	void addColor(ColorDTO brand, AsyncCallback<Void> callback);


	// article-types
	void addArticleType(ArticleTypeDTO articleType, AsyncCallback<Void> callback);

	void readArticleTypes(AsyncCallback<List<ArticleTypeDTO>> callback);

}
