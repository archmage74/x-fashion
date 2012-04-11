package com.xfashion.client;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.xfashion.shared.CategoryDTO;

public class BrandPanel {

	private PanelMediator panelMediator;

	private CategoryDTO selectedCategory;

	private Panel brandHeaderPanel;

	private String headerStyle;

	private CreateBrandPopup addBrandPopup;

	public BrandPanel(PanelMediator panelMediator) {
		this.panelMediator = panelMediator;
		panelMediator.setBrandPanel(this);
	}

	public Panel createPanel(ListDataProvider<BrandCellData> brandProvider) {
		addBrandPopup = new CreateBrandPopup(panelMediator);

		VerticalPanel panel = new VerticalPanel();

		brandHeaderPanel = new HorizontalPanel();
		brandHeaderPanel.addStyleName("filterHeader");
		Label label = new Label("Marke");
		label.addStyleName("filterLabel");
		brandHeaderPanel.add(label);
		Button addBrandButton = new Button("+");
		brandHeaderPanel.add(addBrandButton);
		addBrandButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				addBrandPopup.showForCategory();
			}
		});
		panel.add(brandHeaderPanel);

		BrandCell styleCell = new BrandCell();
		CellList<BrandCellData> styleList = new CellList<BrandCellData>(styleCell, GWT.<StyleListResources> create(StyleListResources.class));
		styleList.setPageSize(30);

		final MultiSelectionModel<BrandCellData> selectionModel = new MultiSelectionModel<BrandCellData>();
		styleList.setSelectionModel(selectionModel);
		selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
			public void onSelectionChange(SelectionChangeEvent event) {
				panelMediator.setSelectedBrands(selectionModel.getSelectedSet());
			}
		});
		// clearButton.addClickHandler(new ClickHandler() {
		// @Override
		// public void onClick(ClickEvent event) {
		// selectionModel.clear();
		// }
		// });

		brandProvider.addDataDisplay(styleList);
		styleList.addStyleName("styleList");
		panel.add(styleList);

		return panel;
	}

	public void setHeaderStyle(String style) {
		if (headerStyle != null) {
			brandHeaderPanel.removeStyleName(headerStyle);
		}
		headerStyle = style;
		if (headerStyle != null) {
			brandHeaderPanel.addStyleName(headerStyle);
		}
	}

	public CategoryDTO getSelectedCategory() {
		return selectedCategory;
	}

	public void setSelectedCategory(CategoryDTO selectedCategory) {
		this.selectedCategory = selectedCategory;
	}

	class BrandCell extends AbstractCell<BrandCellData> {
		@Override
		public void render(Context context, BrandCellData style, SafeHtmlBuilder sb) {
			if (style == null) {
				return;
			}
			style.render(sb);
		}
	}

}
