package com.xfashion.client.at.brand;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Panel;
import com.google.web.bindery.event.shared.EventBus;
import com.xfashion.client.Xfashion;
import com.xfashion.client.at.ArticleFilterProvider;
import com.xfashion.client.at.ArticleTypeDataProvider;
import com.xfashion.client.at.brand.event.CreateBrandEvent;
import com.xfashion.client.at.brand.event.CreateBrandHandler;
import com.xfashion.client.at.brand.event.DeleteBrandEvent;
import com.xfashion.client.at.brand.event.DeleteBrandHandler;
import com.xfashion.client.at.brand.event.MoveDownBrandEvent;
import com.xfashion.client.at.brand.event.MoveDownBrandHandler;
import com.xfashion.client.at.brand.event.MoveUpBrandEvent;
import com.xfashion.client.at.brand.event.MoveUpBrandHandler;
import com.xfashion.client.at.brand.event.ShowChooseBrandPopupEvent;
import com.xfashion.client.at.brand.event.ShowChooseBrandPopupHandler;
import com.xfashion.client.at.brand.event.UpdateBrandEvent;
import com.xfashion.client.at.brand.event.UpdateBrandHandler;
import com.xfashion.client.resources.ErrorMessages;
import com.xfashion.shared.ArticleTypeDTO;
import com.xfashion.shared.BrandDTO;

public class BrandManagement implements CreateBrandHandler, UpdateBrandHandler, DeleteBrandHandler, MoveUpBrandHandler, MoveDownBrandHandler,
		ShowChooseBrandPopupHandler {

	protected EventBus adminBus;
	
	protected BrandPanel adminBrandPanel;
	protected Panel adminPanel;
	protected List<BrandPanel> brandPanels;
	
	protected ArticleTypeDataProvider articleTypeProvider;
	protected BrandDataProvider brandProvider;
	
	protected ErrorMessages errorMessages;
	
	public BrandManagement(ArticleTypeDataProvider articleTypeProvider, EventBus adminBus) {
		this.adminBus = adminBus;
		this.articleTypeProvider = articleTypeProvider;
		this.brandProvider = new BrandDataProvider(articleTypeProvider, adminBus); 
		this.adminBrandPanel = new BrandPanel(this.brandProvider, adminBus);
		new BrandEditor(this.adminBrandPanel, adminBus);
		brandPanels = new ArrayList<BrandPanel>();
		
		this.errorMessages = GWT.create(ErrorMessages.class);
		
		registerForEvents();
	}
	
	public BrandDataProvider getBrandProvider() {
		return brandProvider;
	}

	public void init() {
		brandProvider.readBrands();
	}
	
	public Panel getAdminPanel() {
		if (adminPanel == null) {
			adminPanel = adminBrandPanel.createAdminPanel();
		}
		return adminPanel;
	}
	
	public BrandPanel createBrandPanel(ArticleFilterProvider filterProvider, EventBus eventBus) {
		BrandDataProvider provider = new BrandDataProvider(articleTypeProvider, eventBus);
		filterProvider.setBrandProvider(provider);
		provider.setAllItems(this.brandProvider.getAllItems());
		BrandPanel brandPanel = new BrandPanel(provider, eventBus);
		brandPanels.add(brandPanel);
		return brandPanel;
	}

	@Override
	public void onDeleteBrand(DeleteBrandEvent event) {
		final BrandDTO item = event.getCellData();
		for (ArticleTypeDTO at : articleTypeProvider.getList()) {
			if (at.getBrandKey().equals(item.getKey())) {
				Xfashion.fireError(errorMessages.brandIsNotEmpty(item.getName()));
				return;
			}
		}
		item.setHidden(!item.getHidden());
		brandProvider.saveList();
	}

	@Override
	public void onUpdateBrand(UpdateBrandEvent event) {
		final BrandDTO item = event.getCellData();
		if (item == null) {
			brandProvider.saveList();
		} else {
			brandProvider.saveItem(item);
		}
	}

	@Override
	public void onCreateBrand(CreateBrandEvent event) {
		brandProvider.getAllItems().add(event.getCellData());
		brandProvider.saveList();
	}

	@Override
	public void onMoveDownBrand(MoveDownBrandEvent event) {
		brandProvider.moveDown(event.getIndex());
	}

	@Override
	public void onMoveUpBrand(MoveUpBrandEvent event) {
		brandProvider.moveDown(event.getIndex() - 1);
	}

	@Override
	public void onShowChooseBrandPopup(ShowChooseBrandPopupEvent event) {
		ChooseBrandPopup brandPopup = new ChooseBrandPopup(brandProvider);
		brandPopup.show();
	}

	private void registerForEvents() {
		adminBus.addHandler(DeleteBrandEvent.TYPE, this);
		adminBus.addHandler(CreateBrandEvent.TYPE, this);
		adminBus.addHandler(UpdateBrandEvent.TYPE, this);
		adminBus.addHandler(MoveUpBrandEvent.TYPE, this);
		adminBus.addHandler(MoveDownBrandEvent.TYPE, this);
		Xfashion.eventBus.addHandler(ShowChooseBrandPopupEvent.TYPE, this);
	}

}
