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

	void readCategory(Long categoryId, AsyncCallback<CategoryDTO> callback);
	
	void readCategories(AsyncCallback<List<CategoryDTO>> callback);

	void updateCategory(CategoryDTO category, AsyncCallback<Void> callback);
	
	void deleteCategory(CategoryDTO category, AsyncCallback<Void> callback);


	// styles
	void createStyle(StyleDTO style, AsyncCallback<StyleDTO> callback);

	void readStyle(Long categoryId, AsyncCallback<StyleDTO> callback);
	
	void readStyles(AsyncCallback<List<StyleDTO>> callback);

	void updateStyle(StyleDTO dto, AsyncCallback<Void> callback);

	void deleteStyle(StyleDTO brand, AsyncCallback<Void> callback);

	
	// brands
	void createBrand(BrandDTO brand, AsyncCallback<BrandDTO> callback);

	void readBrand(Long categoryId, AsyncCallback<BrandDTO> callback);
	
	void readBrands(AsyncCallback<List<BrandDTO>> callback);

	void updateBrand(BrandDTO dto, AsyncCallback<Void> callback);

	void deleteBrand(BrandDTO brand, AsyncCallback<Void> callback);

	
	// sizes
	void createSize(SizeDTO brand, AsyncCallback<SizeDTO> callback);

	void readSize(Long categoryId, AsyncCallback<SizeDTO> callback);
	
	void readSizes(AsyncCallback<List<SizeDTO>> callback);

	void updateSize(SizeDTO dto, AsyncCallback<Void> callback);

	void deleteSize(SizeDTO brand, AsyncCallback<Void> callback);

	
	// colors
	void createColor(ColorDTO brand, AsyncCallback<ColorDTO> callback);

	void readColor(Long categoryId, AsyncCallback<ColorDTO> callback);
	
	void readColors(AsyncCallback<List<ColorDTO>> callback);

	void updateColor(ColorDTO dto, AsyncCallback<Void> callback);

	void deleteColor(ColorDTO brand, AsyncCallback<Void> callback);


	// article-types
	void createArticleType(ArticleTypeDTO articleType, AsyncCallback<ArticleTypeDTO> callback);

	void readArticleTypes(AsyncCallback<List<ArticleTypeDTO>> callback);
	
	void readArticleType(Long productNumber, AsyncCallback<ArticleTypeDTO> callback);

	void updateArticleType(ArticleTypeDTO articleType, AsyncCallback<Void> callback);

	void deleteArticleType(ArticleTypeDTO articleType, AsyncCallback<Void> callback);

}
