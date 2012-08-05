package com.xfashion.client.at.category;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Panel;
import com.google.web.bindery.event.shared.EventBus;
import com.xfashion.client.Xfashion;
import com.xfashion.client.at.ArticleFilterProvider;
import com.xfashion.client.at.ArticleTypeDataProvider;
import com.xfashion.client.at.category.event.CreateCategoryEvent;
import com.xfashion.client.at.category.event.CreateCategoryHandler;
import com.xfashion.client.at.category.event.DeleteCategoryEvent;
import com.xfashion.client.at.category.event.DeleteCategoryHandler;
import com.xfashion.client.at.category.event.MoveDownCategoryEvent;
import com.xfashion.client.at.category.event.MoveDownCategoryHandler;
import com.xfashion.client.at.category.event.MoveUpCategoryEvent;
import com.xfashion.client.at.category.event.MoveUpCategoryHandler;
import com.xfashion.client.at.category.event.ShowChooseCategoryAndStylePopupEvent;
import com.xfashion.client.at.category.event.ShowChooseCategoryAndStylePopupHandler;
import com.xfashion.client.at.category.event.UpdateCategoryEvent;
import com.xfashion.client.at.category.event.UpdateCategoryHandler;
import com.xfashion.client.at.style.StyleDataProvider;
import com.xfashion.client.at.style.StyleEditor;
import com.xfashion.client.at.style.StylePanel;
import com.xfashion.client.at.style.event.CreateStyleEvent;
import com.xfashion.client.at.style.event.CreateStyleHandler;
import com.xfashion.client.at.style.event.DeleteStyleEvent;
import com.xfashion.client.at.style.event.DeleteStyleHandler;
import com.xfashion.client.at.style.event.MoveDownStyleEvent;
import com.xfashion.client.at.style.event.MoveDownStyleHandler;
import com.xfashion.client.at.style.event.MoveUpStyleEvent;
import com.xfashion.client.at.style.event.MoveUpStyleHandler;
import com.xfashion.client.at.style.event.UpdateStyleEvent;
import com.xfashion.client.at.style.event.UpdateStyleHandler;
import com.xfashion.client.resources.ErrorMessages;
import com.xfashion.shared.ArticleTypeDTO;
import com.xfashion.shared.CategoryDTO;
import com.xfashion.shared.StyleDTO;

public class CategoryManagement implements CreateStyleHandler, UpdateStyleHandler, DeleteStyleHandler,
MoveDownStyleHandler, MoveUpStyleHandler, CreateCategoryHandler, UpdateCategoryHandler,
DeleteCategoryHandler, MoveUpCategoryHandler, MoveDownCategoryHandler, ShowChooseCategoryAndStylePopupHandler {

	protected EventBus adminBus;

	protected CategoryPanel adminCategoryPanel;
	protected Panel adminCategoryView;
	protected StylePanel adminStylePanel;
	protected Panel adminStyleView;
	
	protected List<CategoryPanel> categoryPanels;
	protected List<StylePanel> stylePanels;
	
	protected ArticleTypeDataProvider articleTypeProvider;
	protected CategoryDataProvider categoryProvider;
	
	protected ErrorMessages errorMessages;

	
	public CategoryManagement(ArticleTypeDataProvider articleTypeProvider, EventBus adminBus) {
		this.adminBus = adminBus;
		this.articleTypeProvider = articleTypeProvider;
		this.categoryProvider = new CategoryDataProvider(articleTypeProvider, adminBus); 
		
		this.adminCategoryPanel = new CategoryPanel(this.categoryProvider, adminBus);
		new CategoryEditor(this.adminCategoryPanel, adminBus);
		this.adminStylePanel = new StylePanel(this.categoryProvider.getStyleProvider(), adminBus);
		new StyleEditor(adminStylePanel, adminBus);
		
		this.categoryPanels = new ArrayList<CategoryPanel>();
		this.stylePanels = new ArrayList<StylePanel>();
		
		this.errorMessages = GWT.create(ErrorMessages.class);
		
		registerForEvents();
	}
	
	public CategoryDataProvider getCategoryProvider() {
		return categoryProvider;
	}

	public void init() {
		categoryProvider.readCategories();
	}
	
	public Panel getCategoryAdminPanel() {
		if (adminCategoryView == null) {
			adminCategoryView = adminCategoryPanel.createAdminPanel();
		}
		return adminCategoryView;
	}

	public Panel getStyleAdminPanel() {
		if (adminStyleView == null) {
			adminStyleView = adminStylePanel.createAdminPanel();
		}
		return adminStyleView;
	}
	
	public CategoryPanel createCategoryPanel(ArticleFilterProvider filterProvider, EventBus eventBus) {
		CategoryDataProvider provider = new CategoryDataProvider(articleTypeProvider, eventBus);
		filterProvider.setCategoryProvider(provider);
		provider.setAllItems(this.categoryProvider.getAllItems());
		CategoryPanel categoryPanel = new CategoryPanel(provider, eventBus);
		categoryPanels.add(categoryPanel);
		return categoryPanel;
	}

	public StylePanel createStylePanel(EventBus eventBus) {
		StyleDataProvider provider = new StyleDataProvider(articleTypeProvider, eventBus);
		StylePanel stylePanel = new StylePanel(provider, eventBus);
		stylePanels.add(stylePanel);
		return stylePanel;
	}

	@Override
	public void onDeleteCategory(DeleteCategoryEvent event) {
		final CategoryDTO item = event.getCellData();
		for (ArticleTypeDTO at : articleTypeProvider.getList()) {
			if (at.getCategoryKey().equals(item.getKey())) {
				Xfashion.fireError(errorMessages.brandIsNotEmpty(item.getName()));
				return;
			}
		}
		item.setHidden(!item.getHidden());
		categoryProvider.saveItem(item);
	}

	@Override
	public void onUpdateCategory(UpdateCategoryEvent event) {
		final CategoryDTO item = event.getCellData();
		if (item != null) {
			categoryProvider.saveList();
		} else {
			categoryProvider.saveItem(item);
		}
	}

	@Override
	public void onCreateCategory(CreateCategoryEvent event) {
		categoryProvider.getAllItems().add(event.getCellData());
		categoryProvider.saveList();
	}

	@Override
	public void onMoveDownCategory(MoveDownCategoryEvent event) {
		categoryProvider.moveDownCategory(event.getIndex());
	}

	@Override
	public void onMoveUpCategory(MoveUpCategoryEvent event) {
		categoryProvider.moveDownCategory(event.getIndex() - 1);
	}

	@Override
	public void onShowChooseCategoryAndStylePopup(ShowChooseCategoryAndStylePopupEvent event) {
		ChooseCategoryAndStylePopup popup = new ChooseCategoryAndStylePopup(categoryProvider);
		popup.show();
	}

	@Override
	public void onCreateStyle(CreateStyleEvent event) {
		final StyleDTO style = event.getCellData();
		if (categoryProvider.getCategoryFilter() != null) {
			CategoryDTO category = categoryProvider.getCategoryFilter();
			category.getStyles().add(style);
			categoryProvider.saveItem(category);
		} else {
			Xfashion.fireError(errorMessages.noCategorySelected());
		}
	}

	@Override
	public void onUpdateStyle(UpdateStyleEvent event) {
		categoryProvider.saveItem(categoryProvider.getCategoryFilter());
	}

	@Override
	public void onMoveDownStyle(MoveDownStyleEvent event) {
		moveDownStyle(event.getIndex());
	}

	@Override
	public void onMoveUpStyle(MoveUpStyleEvent event) {
		moveDownStyle(event.getIndex() - 1);
	}

	public void moveDownStyle(int idx) {
		List<StyleDTO> styleList = categoryProvider.getCategoryFilter().getStyles();
		if (idx < 0) {
			return;
		}
		if (idx + 1 >= styleList.size()) {
			return;
		}
		StyleDTO item = styleList.remove(idx);
		styleList.add(idx + 1, item);
		categoryProvider.saveItem(categoryProvider.getCategoryFilter());
	}

	@Override
	public void onDeleteStyle(DeleteStyleEvent event) {
		final StyleDTO style = event.getCellData();
		CategoryDTO category = categoryProvider.getCategoryFilter();
		style.setHidden(style.getHidden());
		categoryProvider.saveItem(category);
	}

	private void registerForEvents() {
		adminBus.addHandler(CreateCategoryEvent.TYPE, this);
		adminBus.addHandler(UpdateCategoryEvent.TYPE, this);
		adminBus.addHandler(DeleteCategoryEvent.TYPE, this);
		adminBus.addHandler(MoveUpCategoryEvent.TYPE, this);
		adminBus.addHandler(MoveDownCategoryEvent.TYPE, this);
		adminBus.addHandler(CreateStyleEvent.TYPE, this);
		adminBus.addHandler(UpdateStyleEvent.TYPE, this);
		adminBus.addHandler(DeleteStyleEvent.TYPE, this);
		adminBus.addHandler(MoveUpStyleEvent.TYPE, this);
		adminBus.addHandler(MoveDownStyleEvent.TYPE, this);
		adminBus.addHandler(ShowChooseCategoryAndStylePopupEvent.TYPE, this);
	}

}
