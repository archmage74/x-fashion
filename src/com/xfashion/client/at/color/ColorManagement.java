package com.xfashion.client.at.color;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Panel;
import com.google.web.bindery.event.shared.EventBus;
import com.xfashion.client.Xfashion;
import com.xfashion.client.at.ArticleFilterProvider;
import com.xfashion.client.at.ArticleTypeDataProvider;
import com.xfashion.client.at.color.event.CreateColorEvent;
import com.xfashion.client.at.color.event.CreateColorHandler;
import com.xfashion.client.at.color.event.DeleteColorEvent;
import com.xfashion.client.at.color.event.DeleteColorHandler;
import com.xfashion.client.at.color.event.MoveDownColorEvent;
import com.xfashion.client.at.color.event.MoveDownColorHandler;
import com.xfashion.client.at.color.event.MoveUpColorEvent;
import com.xfashion.client.at.color.event.MoveUpColorHandler;
import com.xfashion.client.at.color.event.ShowChooseColorPopupEvent;
import com.xfashion.client.at.color.event.ShowChooseColorPopupHandler;
import com.xfashion.client.at.color.event.UpdateColorEvent;
import com.xfashion.client.at.color.event.UpdateColorHandler;
import com.xfashion.client.resources.ErrorMessages;
import com.xfashion.shared.ArticleTypeDTO;
import com.xfashion.shared.ColorDTO;

public class ColorManagement implements CreateColorHandler, UpdateColorHandler, DeleteColorHandler, MoveUpColorHandler, MoveDownColorHandler,
		ShowChooseColorPopupHandler {

	protected EventBus adminBus;
	
	private ColorPanel adminColorPanel;
	private Panel adminPanel;
	protected List<ColorPanel> colorPanels;
	
	protected ArticleTypeDataProvider articleTypeProvider;
	protected ColorDataProvider colorProvider;
	
	protected ErrorMessages errorMessages;

	
	public ColorManagement(ArticleTypeDataProvider articleTypeProvider, EventBus adminBus) {
		this.adminBus = adminBus;
		this.articleTypeProvider = articleTypeProvider;
		this.colorProvider = new ColorDataProvider(articleTypeProvider, adminBus); 
		this.adminColorPanel = new ColorPanel(this.colorProvider, adminBus);
		new ColorEditor(this.adminColorPanel, adminBus);
		colorPanels = new ArrayList<ColorPanel>();

		this.errorMessages = GWT.create(ErrorMessages.class);
		
		registerForEvents();
	}
	
	public ColorDataProvider getColorProvider() {
		return colorProvider;
	}

	public void init() {
		colorProvider.readColors();
	}
	
	public Panel getAdminPanel() {
		if (adminPanel == null) {
			adminPanel = adminColorPanel.createAdminPanel();
		}
		return adminPanel;
	}
	
	public ColorPanel createColorPanel(ArticleFilterProvider filterProvider, EventBus eventBus) {
		ColorDataProvider provider = new ColorDataProvider(articleTypeProvider, eventBus); 
		filterProvider.setColorProvider(provider);
		provider.setAllItems(this.colorProvider.getAllItems());
		ColorPanel colorPanel = new ColorPanel(provider, eventBus);
		colorPanels.add(colorPanel);
		return colorPanel;
	}

	@Override
	public void onDeleteColor(DeleteColorEvent event) {
		final ColorDTO item = event.getCellData();
		for (ArticleTypeDTO at : articleTypeProvider.getList()) {
			if (at.getColorKey().equals(item.getKey())) {
				Xfashion.fireError(errorMessages.colorIsNotEmpty(item.getName()));
				return;
			}
		}
		item.setHidden(!item.getHidden());
		colorProvider.saveList();
	}

	@Override
	public void onUpdateColor(UpdateColorEvent event) {
		final ColorDTO item = event.getCellData();
		if (item == null) {
			colorProvider.saveList();
		} else {
			colorProvider.saveItem(item);
		}
	}

	@Override
	public void onCreateColor(CreateColorEvent event) {
		colorProvider.getAllItems().add(event.getCellData());
		colorProvider.saveList();
	}

	@Override
	public void onMoveDownColor(MoveDownColorEvent event) {
		colorProvider.moveDown(event.getIndex());
	}

	@Override
	public void onMoveUpColor(MoveUpColorEvent event) {
		colorProvider.moveDown(event.getIndex() - 1);
	}

	@Override
	public void onShowChooseColorPopup(ShowChooseColorPopupEvent event) {
		ChooseColorPopup colorPopup = new ChooseColorPopup(colorProvider);
		colorPopup.show();
	}

	private void registerForEvents() {
		adminBus.addHandler(DeleteColorEvent.TYPE, this);
		adminBus.addHandler(CreateColorEvent.TYPE, this);
		adminBus.addHandler(UpdateColorEvent.TYPE, this);
		adminBus.addHandler(MoveUpColorEvent.TYPE, this);
		adminBus.addHandler(MoveDownColorEvent.TYPE, this);
		adminBus.addHandler(ShowChooseColorPopupEvent.TYPE, this);
	}

}
