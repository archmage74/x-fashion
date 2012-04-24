package com.xfashion.client;

import java.util.Set;

import com.google.gwt.core.client.GWT;
import com.xfashion.client.at.ArticleTypeDatabase;
import com.xfashion.client.at.ArticleTypeDetailPopup;
import com.xfashion.client.at.ArticleTypePanel;
import com.xfashion.client.at.CreateArticleTypePopup;
import com.xfashion.client.brand.BrandPanel;
import com.xfashion.client.cat.CategoryPanel;
import com.xfashion.client.color.ColorPanel;
import com.xfashion.client.resources.ErrorMessages;
import com.xfashion.client.size.SizePanel;
import com.xfashion.client.style.StylePanel;
import com.xfashion.shared.ArticleTypeDTO;
import com.xfashion.shared.BrandDTO;
import com.xfashion.shared.CategoryDTO;
import com.xfashion.shared.ColorDTO;
import com.xfashion.shared.SizeDTO;
import com.xfashion.shared.StyleDTO;

public class PanelMediator implements ApplicationLoadListener, ApplicationErrorListener {

	private Xfashion xfashion;

	private CategoryPanel categoryPanel;
	private StylePanel stylePanel;
	private BrandPanel brandPanel;
	private SizePanel sizePanel;
	private ColorPanel colorPanel;
	private ArticleTypePanel articleTypePanel;

	private CreateArticleTypePopup createArticleTypePopup;
	private ArticleTypeDetailPopup articleTypeDetailPopup;

	private ErrorMessages errorMessages;
	private ErrorPopup errorPopup;
	
	private ArticleTypeDatabase articleTypeDatabase;

	public PanelMediator() {
		errorMessages = GWT.create(ErrorMessages.class);
		errorPopup = new ErrorPopup();
	}
	
	public void createCategories() {
		articleTypeDatabase.createCategories();
	}

	public void setHeaderColor(String color) {
		color = null; // override color setting of header
		categoryPanel.setHeaderColor(color);
		stylePanel.setHeaderColor(color);
		brandPanel.setHeaderColor(color);
		sizePanel.setHeaderColor(color);
		colorPanel.setHeaderColor(color);
		articleTypePanel.setHeaderColor(color);
	}

	public void resetHeaderColor() {
		setHeaderColor(null);
	}
	
	public CategoryDTO getSelectedCategory() {
		return articleTypeDatabase.getCategoryFilter();
	}
	
	public void setSelectedCategory(CategoryDTO selectedCategory) {
		articleTypeDatabase.setCategoryFilter(selectedCategory);
		articleTypeDatabase.updateProviders();
	}

	public void setSelectedStyles(Set<StyleDTO> styles) {
		Set<String> styleFilter = articleTypeDatabase.getStyleFilter();
		styleFilter.clear();
		for (StyleDTO s : styles) {
			styleFilter.add(s.getName());
		}
		articleTypeDatabase.applyFilters();
		articleTypeDatabase.updateProviders();
	}

	public void setSelectedBrands(Set<BrandDTO> brands) {
		Set<String> brandFilter = articleTypeDatabase.getBrandFilter();
		brandFilter.clear();
		for (BrandDTO s : brands) {
			brandFilter.add(s.getName());
		}
		articleTypeDatabase.applyFilters();
		articleTypeDatabase.updateProviders();
	}
	
	public void setSelectedSizes(Set<SizeDTO> sizes) {
		Set<String> sizeFilter = articleTypeDatabase.getSizeFilter();
		sizeFilter.clear();
		for (SizeDTO s : sizes) {
			sizeFilter.add(s.getName());
		}
		articleTypeDatabase.applyFilters();
		articleTypeDatabase.updateProviders();
	}

	public void setSelectedColors(Set<ColorDTO> colors) {
		Set<String> colorFilter = articleTypeDatabase.getColorFilter();
		colorFilter.clear();
		for (ColorDTO s : colors) {
			colorFilter.add(s.getName());
		}
		articleTypeDatabase.applyFilters();
		articleTypeDatabase.updateProviders();
	}
	
	public Xfashion getXfashion() {
		return xfashion;
	}

	public void setXfashion(Xfashion xfashion) {
		this.xfashion = xfashion;
	}

	public CategoryPanel getCategoryPanel() {
		return categoryPanel;
	}

	public void setCategoryPanel(CategoryPanel categoryPanel) {
		this.categoryPanel = categoryPanel;
	}

	public ArticleTypeDatabase getArticleTypeDatabase() {
		return articleTypeDatabase;
	}

	public void setArticleTypeDatabase(ArticleTypeDatabase articleTypeDatabase) {
		this.articleTypeDatabase = articleTypeDatabase;
	}

	public StylePanel getStylePanel() {
		return stylePanel;
	}

	public void setStylePanel(StylePanel stylePanel) {
		this.stylePanel = stylePanel;
	}

	public BrandPanel getBrandPanel() {
		return brandPanel;
	}

	public void setBrandPanel(BrandPanel brandPanel) {
		this.brandPanel = brandPanel;
	}

	public ArticleTypePanel getArticleTypePanel() {
		return articleTypePanel;
	}

	public void setArticleTypePanel(ArticleTypePanel articleTypePanel) {
		this.articleTypePanel = articleTypePanel;
	}

	public CreateArticleTypePopup getCreateArticleTypePopup() {
		return createArticleTypePopup;
	}

	public void setCreateArticleTypePopup(CreateArticleTypePopup createArticleTypePopup) {
		this.createArticleTypePopup = createArticleTypePopup;
	}

	public ArticleTypeDetailPopup getArticleTypeDetailPopup() {
		return articleTypeDetailPopup;
	}
	
	public void setArticleTypeDetailPopup(ArticleTypeDetailPopup articleTypeDetailPopup) {
		this.articleTypeDetailPopup = articleTypeDetailPopup;
	}

	public ArticleTypeDTO createArticleTypeFromSelection() {
		ArticleTypeDTO at = new ArticleTypeDTO();

		CategoryDTO category = articleTypeDatabase.getCategoryFilter();
		if (category != null) {
			at.setCategory(category.getName());
		}

		Set<String> styles = articleTypeDatabase.getStyleFilter();
		if (styles.size() == 1) {
			at.setStyle(styles.iterator().next());
		}
		
		Set<String> brands = articleTypeDatabase.getBrandFilter();
		if (brands.size() == 1) {
			at.setBrand(brands.iterator().next());
		}
		
		Set<String> sizes = articleTypeDatabase.getSizeFilter();
		if (sizes.size() == 1) {
			at.setSize(sizes.iterator().next());
		}

		Set<String> colors = articleTypeDatabase.getColorFilter();
		if (colors.size() == 1) {
			at.setColor(colors.iterator().next());
		}
		
		return at;
	}
	
	public void addArticleType(ArticleTypeDTO articleType) {
		articleTypeDatabase.addArticleType(articleType);
	}

	public SizePanel getSizePanel() {
		return sizePanel;
	}

	public void setSizePanel(SizePanel sizePanel) {
		this.sizePanel = sizePanel;
	}

	public ColorPanel getColorPanel() {
		return colorPanel;
	}

	public void setColorPanel(ColorPanel colorPanel) {
		this.colorPanel = colorPanel;
	}

	public void showError(String errorMessage) {
		errorPopup.showPopup(errorMessage);
	}

	@Override
	public void applicationLoaded() {
		
	}

	@Override
	public void error(String error) {
		showError(error);
	}
	
	public void setSelectedName(String name) {
		articleTypeDatabase.setNameFilter(name);
	}

	public void createCategory(CategoryDTO category) {
		articleTypeDatabase.createCategory(category);
	}
	
	public void updateCategory(CategoryDTO category) {
		articleTypeDatabase.updateCategory(category);
	}
	
	public void deleteCategory(CategoryDTO category) {
		if (articleTypeDatabase.doesCategoryHaveArticles(category)) {
			showError(errorMessages.categoryIsNotEmpty(category.getName()));
			return;
		}
		if (category.equals(articleTypeDatabase.getCategoryFilter())) {
			this.setSelectedCategory(null);
		}
		articleTypeDatabase.deleteCategory(category);
	}

	public void createBrand(BrandDTO brand) {
		articleTypeDatabase.createBrand(brand);
	}
	
	public void updateBrand(BrandDTO brand) {
		articleTypeDatabase.updateBrand(brand);
	}
	
	public void deleteBrand(BrandDTO item) {
		articleTypeDatabase.deleteBrand(item);
	}
	
	public void createStyle(StyleDTO style) {
		articleTypeDatabase.createStyle(style);
	}

	public void updateStyle(StyleDTO style) {
		articleTypeDatabase.updateStyle(style);
	}
	
	public void deleteStyle(StyleDTO item) {
		articleTypeDatabase.deleteStyle(item);
	}
	
	public void createSize(SizeDTO size) {
		articleTypeDatabase.createSize(size);
	}
	
	public void updateSize(SizeDTO size) {
		articleTypeDatabase.updateSize(size);
	}
	
	public void deleteSize(SizeDTO item) {
		articleTypeDatabase.deleteSize(item);
	}
	
	public void createColor(ColorDTO color) {
		articleTypeDatabase.createColor(color);
	}
	
	public void updateColor(ColorDTO size) {
		articleTypeDatabase.updateColor(size);
	}
	
	public void deleteColor(ColorDTO item) {
		articleTypeDatabase.deleteColor(item);
	}
	
}
