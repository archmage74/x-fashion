package com.xfashion.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.ListDataProvider;
import com.xfashion.client.resources.TextMessages;
import com.xfashion.shared.FilterCellData2;

public abstract class ChooseAttributePopup2<T extends FilterCellData2> {
	
	private TextMessages textMessages = GWT.create(TextMessages.class);
	
	private ListBox itemListBox;

	private DialogBox popup;
	
	ListDataProvider<T> dataProvider;
	
	public ChooseAttributePopup2(ListDataProvider<T> dataProvider) {
		this.dataProvider = dataProvider;
	}

	protected abstract void select(T item);
	
	public void show() {
		if (popup == null) {
			popup = createPopup();
		}
		popup.setModal(true);
		popup.center();
	}
	
	public void hide() {
		if (popup != null && popup.isShowing()) {
			popup.hide();
		}
	}
	
	private DialogBox createPopup() {
		DialogBox db = new DialogBox();
		VerticalPanel vp = new VerticalPanel();
		
		itemListBox = new ListBox();
		itemListBox.setWidth("120px");
		itemListBox.setVisibleItemCount(14);
		for (T item : dataProvider.getList()) {
			itemListBox.addItem(item.getName(), item.getKey());
		}
		itemListBox.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				itemSelected();
			}
		});
		vp.add(itemListBox);
		
		vp.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		Button cancelButton = new Button(textMessages.cancel());
		cancelButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				hide();
			}
		});
		vp.add(cancelButton);
		
		db.add(vp);
		return db;
	}
	
	private void itemSelected() {
		int selectedIndex = itemListBox.getSelectedIndex();
		if (selectedIndex != -1) {
			select(dataProvider.getList().get(selectedIndex));
			hide();
		}
	}

}
