package com.xfashion.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.HasSelectionHandlers;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.xfashion.client.resources.TextMessages;
import com.xfashion.shared.FilterCellData;

public abstract class ChooseAttributePopup<T extends FilterCellData<?>> implements HasSelectionHandlers<T>  {
	
	private TextMessages textMessages = GWT.create(TextMessages.class);
	
	private List<SelectionHandler<T>> selectionHandlers = null;
	
	private ListBox itemListBox;

	private DialogBox popup;
	
	protected PanelMediator panelMediator;
	
	public ChooseAttributePopup(PanelMediator panelMediator) {
		this.panelMediator = panelMediator;
	}
	
	public abstract FilterPanel<T> getPanel();
	
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
		for (T item : getPanel().getDataProvider().getList()) {
			itemListBox.addItem(item.getName());
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
			T item = getPanel().getDataProvider().getList().get(selectedIndex);
			SelectionEvent.fire(this, item);
			hide();
		}
	}

	@Override
	public void fireEvent(GwtEvent<?> event) {
		if (event instanceof SelectionEvent<?>) {
			@SuppressWarnings("unchecked")
			SelectionEvent<T> e = (SelectionEvent<T>) event;
			if (selectionHandlers != null) {
				for (SelectionHandler<T> h : selectionHandlers) {
					h.onSelection(e);
				}
			}
		}
	}

	@Override
	public HandlerRegistration addSelectionHandler(final SelectionHandler<T> handler) {
		if (selectionHandlers == null) {
			selectionHandlers = new ArrayList<SelectionHandler<T>>();
		}
		selectionHandlers.add(handler);
		return new HandlerRegistration() {
			@Override
			public void removeHandler() {
				selectionHandlers.remove(handler);
			}
		};
	}
	
}
