package com.xfashion.client;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.xfashion.shared.BrandDTO;
import com.xfashion.shared.CategoryDTO;
import com.xfashion.shared.StyleDTO;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("articleType")
public interface ArticleTypeService extends RemoteService {
	
	void createCategories() throws IllegalArgumentException;
	
	List<CategoryDTO> readCategories() throws IllegalArgumentException;
	
	List<StyleDTO> readStyles() throws IllegalArgumentException;
	
	void addStyle(StyleDTO style) throws IllegalArgumentException;

	List<BrandDTO> readBrands() throws IllegalArgumentException;
	
	void addBrand(BrandDTO brand) throws IllegalArgumentException;
	
	void saveCategory(CategoryDTO category) throws IllegalArgumentException;

}
