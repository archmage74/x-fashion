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
@RemoteServiceRelativePath("articleType")
public interface ArticleTypeService extends RemoteService {
	
	// categories
	void createCategories() throws IllegalArgumentException;
	
	CategoryDTO createCategory(CategoryDTO category);
	
	List<CategoryDTO> readCategories() throws IllegalArgumentException;
	
	void updateCategory(CategoryDTO category) throws IllegalArgumentException;
	
	void deleteCategory(CategoryDTO category);

	
	// styles
	StyleDTO createStyle(StyleDTO style) throws IllegalArgumentException;

	List<StyleDTO> readStyles() throws IllegalArgumentException;

	void updateStyle(StyleDTO dto) throws IllegalArgumentException;

	void deleteStyle(StyleDTO brand);
	
	
	// brands
	BrandDTO createBrand(BrandDTO brand) throws IllegalArgumentException;

	List<BrandDTO> readBrands() throws IllegalArgumentException;
	
	void updateBrand(BrandDTO dto) throws IllegalArgumentException;

	void deleteBrand(BrandDTO brand);
	
	
	// sizes
	SizeDTO createSize(SizeDTO brand) throws IllegalArgumentException;
	
	List<SizeDTO> readSizes() throws IllegalArgumentException;
	
	void updateSize(SizeDTO dto) throws IllegalArgumentException;

	void deleteSize(SizeDTO brand);
	
	
	// colors
	ColorDTO createColor(ColorDTO brand) throws IllegalArgumentException;
	
	List<ColorDTO> readColors() throws IllegalArgumentException;
	
	void updateColor(ColorDTO dto) throws IllegalArgumentException;

	void deleteColor(ColorDTO brand);
	
	
	// article-types
	List<ArticleTypeDTO> readArticleTypes() throws IllegalArgumentException;
	
	void addArticleType(ArticleTypeDTO articleType) throws IllegalArgumentException;

}
