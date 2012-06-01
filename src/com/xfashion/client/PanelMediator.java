package com.xfashion.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
import com.xfashion.shared.CategoryDTO;
import com.xfashion.shared.SizeDTO;

public class PanelMediator {
	
	private CategoryPanel categoryPanel;
	private StylePanel stylePanel;
	private BrandPanel brandPanel;
	private SizePanel sizePanel;
	private ColorPanel colorPanel;
	private ArticleTypePanel articleTypePanel;
	
	private CreateArticleTypePopup createArticleTypePopup;
	
	private ArticleTypeDatabase articleTypeDatabase;
	
	public PanelMediator() {
	}
	
	public CategoryDTO getSelectedCategory() {
		return articleTypeDatabase.getCategoryProvider().getCategoryFilter();
	}
	
	public void setSelectedCategory(CategoryDTO selectedCategory) {
		articleTypeDatabase.setCategoryFilter(selectedCategory);
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

	public List<SizeDTO> getSelectedSizes() {
		ArrayList<SizeDTO> list = new ArrayList<SizeDTO>();
		Set<String> sizes = articleTypeDatabase.getSizeProvider().getFilter();
		SizeDataProvider sizeProvider = articleTypeDatabase.getSizeProvider();
		for (String id : sizes) {
			list.add(sizeProvider.resolveData(id));
		}
		return list;
	}
	
	public ArticleTypeDTO createArticleTypeFromSelection() {
		ArticleTypeDTO at = new ArticleTypeDTO();

		CategoryDTO category = articleTypeDatabase.getCategoryProvider().getCategoryFilter();
		if (category != null) {
			at.setCategoryKey(category.getKey());
		}

		Set<String> styles = articleTypeDatabase.getCategoryProvider().getStyleFilter();
		if (styles.size() == 1) {
			at.setStyleKey(styles.iterator().next());
		}
		
		Set<String> brands = articleTypeDatabase.getBrandProvider().getFilter();
		if (brands.size() == 1) {
			at.setBrandKey(brands.iterator().next());
		}
		
		Set<String> colors = articleTypeDatabase.getColorProvider().getFilter();
		if (colors.size() == 1) {
			at.setColorKey(colors.iterator().next());
		}
		
		return at;
	}
	
	public void createArticleType(ArticleTypeDTO articleType) {
		articleTypeDatabase.createArticleType(articleType);
	}
	
}
