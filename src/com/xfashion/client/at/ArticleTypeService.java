package com.xfashion.client.at;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.xfashion.shared.ArticleTypeDTO;
import com.xfashion.shared.BrandDTO;
import com.xfashion.shared.CategoryDTO;
import com.xfashion.shared.ColorDTO;
import com.xfashion.shared.SizeDTO;
import com.xfashion.shared.StyleDTO;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("articleTypeService")
public interface ArticleTypeService extends RemoteService {
	
	// categories
	void createCategories() throws IllegalArgumentException;
	
	CategoryDTO createCategory(CategoryDTO category);
	
	CategoryDTO readCategory(Long categoryId) throws IllegalArgumentException;
	
	List<CategoryDTO> readCategories() throws IllegalArgumentException;
	
	void updateCategory(CategoryDTO category) throws IllegalArgumentException;
	
	void deleteCategory(CategoryDTO category);

	
	// styles
	StyleDTO createStyle(StyleDTO style) throws IllegalArgumentException;

	StyleDTO readStyle(Long categoryId) throws IllegalArgumentException;
	
	List<StyleDTO> readStyles() throws IllegalArgumentException;

	void updateStyle(StyleDTO dto) throws IllegalArgumentException;

	void deleteStyle(StyleDTO brand);
	
	
	// brands
	BrandDTO createBrand(BrandDTO brand) throws IllegalArgumentException;
	
	BrandDTO readBrand(Long categoryId) throws IllegalArgumentException;
	
	List<BrandDTO> readBrands() throws IllegalArgumentException;
	
	void updateBrand(BrandDTO dto) throws IllegalArgumentException;
	
	void deleteBrand(BrandDTO brand);
	
	
	// sizes
	SizeDTO createSize(SizeDTO brand) throws IllegalArgumentException;
	
	SizeDTO readSize(Long categoryId) throws IllegalArgumentException;
	
	List<SizeDTO> readSizes() throws IllegalArgumentException;
	
	void updateSize(SizeDTO dto) throws IllegalArgumentException;
	
	void deleteSize(SizeDTO brand);
	
	
	// colors
	ColorDTO createColor(ColorDTO brand) throws IllegalArgumentException;
	
	ColorDTO readColor(Long categoryId) throws IllegalArgumentException;
	
	List<ColorDTO> readColors() throws IllegalArgumentException;
	
	void updateColor(ColorDTO dto) throws IllegalArgumentException;
	
	void deleteColor(ColorDTO brand);
	
	
	// article-types
	ArticleTypeDTO createArticleType(ArticleTypeDTO articleType) throws IllegalArgumentException;
	
	List<ArticleTypeDTO> readArticleTypes() throws IllegalArgumentException;
	
	ArticleTypeDTO readArticleType(Long productNumber) throws IllegalArgumentException;
	
	void updateArticleType(ArticleTypeDTO articleType) throws IllegalArgumentException;
	
	void deleteArticleType(ArticleTypeDTO articleType) throws IllegalArgumentException;

}
