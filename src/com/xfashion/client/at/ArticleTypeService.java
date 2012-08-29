package com.xfashion.client.at;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.xfashion.shared.ArticleTypeDTO;
import com.xfashion.shared.BrandDTO;
import com.xfashion.shared.CategoryDTO;
import com.xfashion.shared.ColorDTO;
import com.xfashion.shared.PriceChangeDTO;
import com.xfashion.shared.SizeDTO;
import com.xfashion.shared.StyleDTO;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("articleTypeService")
public interface ArticleTypeService extends RemoteService {
	
	// categories
	CategoryDTO readCategory(String categoryKey) throws IllegalArgumentException;
	
	List<CategoryDTO> readCategories() throws IllegalArgumentException;
	
	CategoryDTO updateCategory(CategoryDTO category) throws IllegalArgumentException;
	
	List<CategoryDTO> updateCategories(List<CategoryDTO> categories) throws IllegalArgumentException;
	
	void deleteCategory(CategoryDTO category);

	
	// styles
	StyleDTO readStyle(String styleKey) throws IllegalArgumentException;
	
	List<StyleDTO> readStyles() throws IllegalArgumentException;

	void updateStyle(StyleDTO dto) throws IllegalArgumentException;

	void deleteStyle(StyleDTO brand);
	
	
	// brands
	BrandDTO readBrand(String key) throws IllegalArgumentException;
	
	List<BrandDTO> readBrands() throws IllegalArgumentException;
	
	void updateBrand(BrandDTO dto) throws IllegalArgumentException;
	
	List<BrandDTO> updateBrands(List<BrandDTO> brands) throws IllegalArgumentException;
	
	void deleteBrand(BrandDTO brand);
	
	
	// sizes
	SizeDTO readSize(String key) throws IllegalArgumentException;
	
	List<SizeDTO> readSizes() throws IllegalArgumentException;
	
	void updateSize(SizeDTO dto) throws IllegalArgumentException;
	
	List<SizeDTO> updateSizes(List<SizeDTO> sizes) throws IllegalArgumentException;
	
	void deleteSize(SizeDTO brand);
	
	
	// colors
	ColorDTO readColor(String key) throws IllegalArgumentException;
	
	List<ColorDTO> readColors() throws IllegalArgumentException;
	
	void updateColor(ColorDTO dto) throws IllegalArgumentException;
	
	List<ColorDTO> updateColors(List<ColorDTO> colors) throws IllegalArgumentException;
	
	void deleteColor(ColorDTO brand);
	
	
	// article-types
	ArticleTypeDTO createArticleType(ArticleTypeDTO articleType) throws IllegalArgumentException;
	
	Set<ArticleTypeDTO> readArticleTypes() throws IllegalArgumentException;
	
	ArticleTypeDTO readArticleType(String key) throws IllegalArgumentException;
	
	ArticleTypeDTO readArticleType(Long productNumber) throws IllegalArgumentException;
	
	void updateArticleType(ArticleTypeDTO articleType) throws IllegalArgumentException;
	
	void updateArticleTypes(Collection<ArticleTypeDTO> articleTypes) throws IllegalArgumentException;
	
	void deleteArticleType(ArticleTypeDTO articleType) throws IllegalArgumentException;

	
	// price-changes
	void createPriceChanges(Collection<PriceChangeDTO> articles) throws IllegalArgumentException;

	PriceChangeDTO readPriceChange(String priceChangeKey) throws IllegalArgumentException;
	
	void deletePriceChange(PriceChangeDTO priceChange) throws IllegalArgumentException;
	
}
