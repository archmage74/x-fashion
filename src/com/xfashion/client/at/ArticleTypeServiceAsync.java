package com.xfashion.client.at;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.xfashion.shared.ArticleTypeDTO;
import com.xfashion.shared.BrandDTO;
import com.xfashion.shared.CategoryDTO;
import com.xfashion.shared.ColorDTO;
import com.xfashion.shared.PriceChangeDTO;
import com.xfashion.shared.SizeDTO;
import com.xfashion.shared.StyleDTO;

public interface ArticleTypeServiceAsync {

	// categories
	void readCategory(String categoryKey, AsyncCallback<CategoryDTO> callback);
	
	void readCategories(AsyncCallback<List<CategoryDTO>> callback);

	void updateCategory(CategoryDTO category, AsyncCallback<CategoryDTO> callback);
	
	void updateCategories(List<CategoryDTO> categories, AsyncCallback<List<CategoryDTO>> callback);
	
	void deleteCategory(CategoryDTO category, AsyncCallback<Void> callback);


	// styles
	void readStyle(String styleKey, AsyncCallback<StyleDTO> callback);
	
	void readStyles(AsyncCallback<List<StyleDTO>> callback);

	void updateStyle(StyleDTO dto, AsyncCallback<Void> callback);

	void deleteStyle(StyleDTO brand, AsyncCallback<Void> callback);

	
	// brands
	void readBrand(String key, AsyncCallback<BrandDTO> callback);
	
	void readBrands(AsyncCallback<List<BrandDTO>> callback);

	void updateBrand(BrandDTO dto, AsyncCallback<Void> callback);

	void updateBrands(List<BrandDTO> sizes, AsyncCallback<List<BrandDTO>> callback);

	void deleteBrand(BrandDTO brand, AsyncCallback<Void> callback);

	
	// sizes
	void readSize(String key, AsyncCallback<SizeDTO> callback);
	
	void readSizes(AsyncCallback<List<SizeDTO>> callback);

	void updateSize(SizeDTO dto, AsyncCallback<Void> callback);

	void updateSizes(List<SizeDTO> sizes, AsyncCallback<List<SizeDTO>> callback);

	void deleteSize(SizeDTO brand, AsyncCallback<Void> callback);

	
	// colors
	void readColor(String key, AsyncCallback<ColorDTO> callback);
	
	void readColors(AsyncCallback<List<ColorDTO>> callback);

	void updateColor(ColorDTO dto, AsyncCallback<Void> callback);

	void updateColors(List<ColorDTO> colors, AsyncCallback<List<ColorDTO>> callback);

	void deleteColor(ColorDTO brand, AsyncCallback<Void> callback);


	// article-types
	void createArticleType(ArticleTypeDTO articleType, AsyncCallback<ArticleTypeDTO> callback);

	void readArticleTypes(AsyncCallback<Set<ArticleTypeDTO>> callback);
	
	void readArticleType(String key, AsyncCallback<ArticleTypeDTO> callback);
	
	void readArticleType(Long productNumber, AsyncCallback<ArticleTypeDTO> callback);

	void updateArticleType(ArticleTypeDTO articleType, AsyncCallback<Void> callback);

	void updateArticleTypes(Collection<ArticleTypeDTO> articleTypes, AsyncCallback<Void> callback);

	void deleteArticleType(ArticleTypeDTO articleType, AsyncCallback<Void> callback);

	
	// price-changes
	void createPriceChanges(Collection<PriceChangeDTO> articles, AsyncCallback<Void> callback);

	void readPriceChange(String priceChangeKey, AsyncCallback<PriceChangeDTO> callback);

	void deletePriceChange(PriceChangeDTO priceChange, AsyncCallback<Void> callback);

}
