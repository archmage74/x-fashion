package com.xfashion.client.cat;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.xfashion.client.Xfashion;
import com.xfashion.client.resources.TextMessages;
import com.xfashion.shared.CategoryDTO;
import com.xfashion.shared.StyleDTO;

public class ChooseCategoryAndStylePopup {

	private TextMessages textMessages = GWT.create(TextMessages.class);

	private ListBox categoryListBox;
	private ListBox styleListBox;
	private CategoryDTO selectedCategory = null;

	private DialogBox popup;

	CategoryDataProvider dataProvider;

	public ChooseCategoryAndStylePopup(CategoryDataProvider dataProvider) {
		this.dataProvider = dataProvider;
	}

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
		HorizontalPanel hp = new HorizontalPanel();

		VerticalPanel vp1 = new VerticalPanel();
		vp1.add(createCategoryList());
		vp1.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		vp1.add(createOkButton());
		hp.add(vp1);

		VerticalPanel vp2 = new VerticalPanel();
		vp2.add(createStyleList());
		vp2.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		vp2.add(createCancelButton());
		hp.add(vp2);

		db.add(hp);
		return db;
	}

	private ListBox createCategoryList() {
		categoryListBox = new ListBox();
		categoryListBox.setWidth("120px");
		categoryListBox.setVisibleItemCount(14);
		for (CategoryDTO item : dataProvider.getList()) {
			categoryListBox.addItem(item.getName(), item.getKey());
		}
		categoryListBox.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				selectCategory();
			}
		});
		return categoryListBox;
	}

	private void selectCategory() {
		int idx = categoryListBox.getSelectedIndex();
		if (idx != -1) {
			selectedCategory = dataProvider.getList().get(idx);
		} else {
			selectedCategory = null;
		}
		fillStyleList();
	}

	private Button createOkButton() {
		Button okButton = new Button(textMessages.select());
		okButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				itemsSelected();
			}
		});
		return okButton;
	}

	private ListBox createStyleList() {
		styleListBox = new ListBox();
		styleListBox.setWidth("120px");
		styleListBox.setVisibleItemCount(14);
		return styleListBox;
	}

	private void fillStyleList() {
		styleListBox.clear();
		if (selectedCategory != null) {
			for (StyleDTO item : selectedCategory.getStyles()) {
				styleListBox.addItem(item.getName(), item.getKey());
			}
		}
	}

	private Button createCancelButton() {
		Button cancelButton = new Button(textMessages.cancel());
		cancelButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				hide();
			}
		});
		return cancelButton;
	}

	private void itemsSelected() {
		int categoryIdx = categoryListBox.getSelectedIndex();
		int styleIdx = styleListBox.getSelectedIndex();
		if (categoryIdx != -1 && styleIdx != -1) {
			CategoryDTO category = dataProvider.getList().get(categoryIdx);
			StyleDTO style = selectedCategory.getStyles().get(styleIdx);
			select(category, style);
			hide();
		}
	}

	protected void select(CategoryDTO category, StyleDTO style) {
		Xfashion.eventBus.fireEvent(new ChooseCategoryAndStyleEvent(category, style));
	}

}
