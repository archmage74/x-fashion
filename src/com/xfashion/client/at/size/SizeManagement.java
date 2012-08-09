package com.xfashion.client.at.size;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Panel;
import com.google.web.bindery.event.shared.EventBus;
import com.xfashion.client.Xfashion;
import com.xfashion.client.at.ArticleFilterProvider;
import com.xfashion.client.at.ArticleTypeDataProvider;
import com.xfashion.client.at.size.event.CreateSizeEvent;
import com.xfashion.client.at.size.event.CreateSizeHandler;
import com.xfashion.client.at.size.event.DeleteSizeEvent;
import com.xfashion.client.at.size.event.DeleteSizeHandler;
import com.xfashion.client.at.size.event.MoveDownSizeEvent;
import com.xfashion.client.at.size.event.MoveDownSizeHandler;
import com.xfashion.client.at.size.event.MoveUpSizeEvent;
import com.xfashion.client.at.size.event.MoveUpSizeHandler;
import com.xfashion.client.at.size.event.ShowChooseSizePopupEvent;
import com.xfashion.client.at.size.event.ShowChooseSizePopupHandler;
import com.xfashion.client.at.size.event.UpdateSizeEvent;
import com.xfashion.client.at.size.event.UpdateSizeHandler;
import com.xfashion.client.resources.ErrorMessages;
import com.xfashion.shared.ArticleTypeDTO;
import com.xfashion.shared.SizeDTO;

public class SizeManagement implements CreateSizeHandler, UpdateSizeHandler, DeleteSizeHandler, MoveUpSizeHandler, MoveDownSizeHandler,
		ShowChooseSizePopupHandler {

	protected EventBus adminBus;
	
	protected SizePanel adminSizePanel;
	protected Panel adminPanel;
	protected List<SizePanel> sizePanels;
	
	protected ArticleTypeDataProvider articleTypeProvider;
	protected SizeDataProvider sizeProvider;
	
	protected ErrorMessages errorMessages;

	
	public SizeManagement(ArticleTypeDataProvider articleTypeProvider, EventBus adminBus) {
		this.adminBus = adminBus;
		this.articleTypeProvider = articleTypeProvider;
		this.sizeProvider = new SizeDataProvider(articleTypeProvider, adminBus); 
		this.adminSizePanel = new SizePanel(this.sizeProvider, adminBus);
		new SizeEditor(this.adminSizePanel, adminBus);
		sizePanels = new ArrayList<SizePanel>();
		
		this.errorMessages = GWT.create(ErrorMessages.class);
		
		registerForEvents();
	}
	
	public SizeDataProvider getSizeProvider() {
		return sizeProvider;
	}

	public void init() {
		sizeProvider.readSizes();
	}
	
	public Panel getAdminPanel() {
		if (adminPanel == null) {
			adminPanel = adminSizePanel.createAdminPanel();
		}
		return adminPanel;
	}
	
	public SizePanel createSizePanel(ArticleFilterProvider filterProvider, EventBus eventBus) {
		SizeDataProvider provider = new SizeDataProvider(articleTypeProvider, eventBus); 
		filterProvider.setSizeProvider(provider);
		provider.setAllItems(this.sizeProvider.getAllItems());
		SizePanel sizePanel = new SizePanel(provider, eventBus);
		sizePanels.add(sizePanel);
		return sizePanel;
	}

	@Override
	public void onDeleteSize(DeleteSizeEvent event) {
		final SizeDTO item = event.getCellData();
		for (ArticleTypeDTO at : articleTypeProvider.getList()) {
			if (at.getSizeKey().equals(item.getKey())) {
				Xfashion.fireError(errorMessages.sizeIsNotEmpty(item.getName()));
				return;
			}
		}
		item.setHidden(!item.getHidden());
		sizeProvider.saveList();
	}

	@Override
	public void onUpdateSize(UpdateSizeEvent event) {
		final SizeDTO item = event.getCellData();
		if (item == null) {
			sizeProvider.saveList();
		} else {
			sizeProvider.saveItem(item);
		}
	}

	@Override
	public void onCreateSize(CreateSizeEvent event) {
		sizeProvider.getAllItems().add(event.getCellData());
		sizeProvider.saveList();
	}

	@Override
	public void onMoveDownSize(MoveDownSizeEvent event) {
		sizeProvider.moveDown(event.getIndex());
	}

	@Override
	public void onMoveUpSize(MoveUpSizeEvent event) {
		sizeProvider.moveDown(event.getIndex() - 1);
	}

	@Override
	public void onShowChooseSizePopup(ShowChooseSizePopupEvent event) {
		ChooseSizePopup sizePopup = new ChooseSizePopup(sizeProvider);
		sizePopup.show();
	}

	private void registerForEvents() {
		adminBus.addHandler(DeleteSizeEvent.TYPE, this);
		adminBus.addHandler(CreateSizeEvent.TYPE, this);
		adminBus.addHandler(UpdateSizeEvent.TYPE, this);
		adminBus.addHandler(MoveUpSizeEvent.TYPE, this);
		adminBus.addHandler(MoveDownSizeEvent.TYPE, this);

		Xfashion.eventBus.addHandler(ShowChooseSizePopupEvent.TYPE, this);
	}

}
