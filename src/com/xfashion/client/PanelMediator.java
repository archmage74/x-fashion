package com.xfashion.client;

import java.util.Set;

import com.xfashion.shared.ArticleTypeDTO;
import com.xfashion.shared.CategoryDTO;

public class PanelMediator {

	private Xfashion xfashion;

	private CategoryPanel categoryPanel;
	private StylePanel stylePanel;
	private BrandPanel brandPanel;
	private ArticleTypePanel articleTypePanel;

	private CreateStylePopup createStylePopup;
	private CreateBrandPopup createBrandPopup;
	private CreateArticleTypePopup createArticleTypePopup;
	private ArticleTypeDetailPopup articleTypeDetailPopup;
	
	private ArticleTypeDatabase articleTypeDatabase;

	public void createCategories() {
		articleTypeDatabase.createCategories();
	}

	void setHeaderStyle(String style) {
		categoryPanel.setHeaderStyle(style);
		stylePanel.setHeaderStyle(style);
		brandPanel.setHeaderStyle(style);
		articleTypePanel.setHeaderStyle(style);
		xfashion.setHeaderStyle(style);
	}

	public void addStyle(String style) {
		articleTypeDatabase.addStyle(style);
	}

	public void addBrand(String brand) {
		articleTypeDatabase.addBrand(brand);
	}

	public void setSelectedCategory(CategoryDTO selectedCategory) {
		articleTypeDatabase.setCategoryFilter(selectedCategory);
		// articleTypeDatabase.setStyleFilter(new HashSet<String>());
	}

	public void setSelectedStyles(Set<StyleCellData> styles) {
		Set<String> styleFilter = articleTypeDatabase.getStyleFilter();
		styleFilter.clear();
		for (StyleCellData s : styles) {
			styleFilter.add(s.getName());
		}
		articleTypeDatabase.applyFilters();
		articleTypeDatabase.updateStyleProvider();
	}

	public void setSelectedBrands(Set<BrandCellData> brands) {
		Set<String> brandFilter = articleTypeDatabase.getBrandFilter();
		brandFilter.clear();
		for (BrandCellData s : brands) {
			brandFilter.add(s.getName());
		}
		articleTypeDatabase.applyFilters();
		articleTypeDatabase.updateBrandProvider();
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

	public CreateStylePopup getCreateStylePopup() {
		return createStylePopup;
	}

	public void setCreateStylePopup(CreateStylePopup createStylePopup) {
		this.createStylePopup = createStylePopup;
	}

	public CreateBrandPopup getCreateBrandPopup() {
		return createBrandPopup;
	}

	public void setCreateBrandPopup(CreateBrandPopup createBrandPopup) {
		this.createBrandPopup = createBrandPopup;
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
		
		at.setSize("M");
		at.setColor("Schwarz");
		
		return at;
	}
	
	public void addArticleType(ArticleTypeDTO articleType) {
		articleTypeDatabase.addArticleType(articleType);
	}
}
