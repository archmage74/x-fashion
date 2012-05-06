package com.xfashion.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.xfashion.client.at.ArticleTypeDetailPopup;
import com.xfashion.client.at.ArticleTypePanel;
import com.xfashion.client.at.CreateArticleTypePopup;
import com.xfashion.client.brand.BrandPanel;
import com.xfashion.client.cat.CategoryPanel;
import com.xfashion.client.color.ColorPanel;
import com.xfashion.client.db.ArticleTypeDatabase;
import com.xfashion.client.size.SizeDataProvider;
import com.xfashion.client.size.SizePanel;
import com.xfashion.client.style.StylePanel;
import com.xfashion.shared.ArticleTypeDTO;
import com.xfashion.shared.BrandDTO;
import com.xfashion.shared.CategoryDTO;
import com.xfashion.shared.ColorDTO;
import com.xfashion.shared.SizeDTO;
import com.xfashion.shared.StyleDTO;

public class PanelMediator {
	
	private CategoryPanel categoryPanel;
	private StylePanel stylePanel;
	private BrandPanel brandPanel;
	private SizePanel sizePanel;
	private ColorPanel colorPanel;
	private ArticleTypePanel articleTypePanel;
	
	private CreateArticleTypePopup createArticleTypePopup;
	private ArticleTypeDetailPopup articleTypeDetailPopup;
	
	private ArticleTypeDatabase articleTypeDatabase;
	
	public PanelMediator() {
	}
	
	public void createCategories() {
		articleTypeDatabase.createCategories();
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
	
	public ArticleTypeDatabase getArticleTypeDatabase() {
		return articleTypeDatabase;
	}

	public void setArticleTypeDatabase(ArticleTypeDatabase articleTypeDatabase) {
		this.articleTypeDatabase = articleTypeDatabase;
	}

	public CategoryPanel getCategoryPanel() {
		return categoryPanel;
	}

	public void setCategoryPanel(CategoryPanel categoryPanel) {
		this.categoryPanel = categoryPanel;
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
	
	public List<SizeDTO> getSelectedSizes() {
		ArrayList<SizeDTO> list = new ArrayList<SizeDTO>();
		Set<Long> sizes = articleTypeDatabase.getSizeFilter();
		SizeDataProvider sizeProvider = articleTypeDatabase.getSizeProvider();
		for (Long id : sizes) {
			list.add(sizeProvider.resolveData(id));
		}
		return list;
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
		
		Set<Long> colors = articleTypeDatabase.getColorFilter();
		if (colors.size() == 1) {
			at.setColorId(colors.iterator().next());
		}
		
		return at;
	}
	
	public void createArticleType(ArticleTypeDTO articleType) {
		articleTypeDatabase.createArticleType(articleType);
	}
	
	public void updateArticleType(ArticleTypeDTO articleType) {
		articleTypeDatabase.updateArticleType(articleType);
	}
	
	public void deleteArticleType(ArticleTypeDTO articleType) {
		articleTypeDatabase.deleteArticleType(articleType);
	}

}
