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
		Set<Long> styleFilter = articleTypeDatabase.getStyleFilter();
		styleFilter.clear();
		for (StyleDTO s : styles) {
			styleFilter.add(s.getId());
		}
		articleTypeDatabase.applyFilters();
		articleTypeDatabase.updateProviders();
	}

	public void setSelectedBrands(Set<BrandDTO> brands) {
		Set<Long> brandFilter = articleTypeDatabase.getBrandFilter();
		brandFilter.clear();
		for (BrandDTO s : brands) {
			brandFilter.add(s.getId());
		}
		articleTypeDatabase.applyFilters();
		articleTypeDatabase.updateProviders();
	}
	
	public void setSelectedSizes(Set<SizeDTO> sizes) {
		Set<Long> sizeFilter = articleTypeDatabase.getSizeFilter();
		sizeFilter.clear();
		for (SizeDTO s : sizes) {
			sizeFilter.add(s.getId());
		}
		articleTypeDatabase.applyFilters();
		articleTypeDatabase.updateProviders();
	}

	public void setSelectedColors(Set<ColorDTO> colors) {
		Set<Long> colorFilter = articleTypeDatabase.getColorFilter();
		colorFilter.clear();
		for (ColorDTO s : colors) {
			colorFilter.add(s.getId());
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
			at.setCategoryId(category.getId());
		}

		Set<Long> styles = articleTypeDatabase.getStyleFilter();
		if (styles.size() == 1) {
			at.setStyleId(styles.iterator().next());
		}
		
		Set<Long> brands = articleTypeDatabase.getBrandFilter();
		if (brands.size() == 1) {
			at.setBrandId(brands.iterator().next());
		}
		
		Set<Long> sizes = articleTypeDatabase.getSizeFilter();
		if (sizes.size() == 1) {
			at.setSizeId(sizes.iterator().next());
		}

		Set<Long> colors = articleTypeDatabase.getColorFilter();
		if (colors.size() == 1) {
			at.setColorId(colors.iterator().next());
		}
		
		return at;
	}
	
	public void createArticleType(ArticleTypeDTO articleType) {
		articleTypeDatabase.createArticleType(articleType);
	}
	
	public void deleteArticleType(ArticleTypeDTO articleType) {
		articleTypeDatabase.deleteArticleType(articleType);
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
		MainPanel mp = new MainPanel();
		mp.addMainPanel(this);
		mp.addNavPanel(this);
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
