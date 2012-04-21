package com.xfashion.client.brand;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.xfashion.client.CreatePopup;
import com.xfashion.client.FilterPanel;
import com.xfashion.client.PanelMediator;
import com.xfashion.client.resources.FilterListResources;

public class BrandPanel extends FilterPanel {

	private MultiSelectionModel<BrandCellData> selectionModel;
	
	protected CreatePopup createPopup;
	
	public BrandPanel(PanelMediator panelMediator) {
		super(panelMediator);
		panelMediator.setBrandPanel(this);
	}
	
	public Panel createPanel(ListDataProvider<BrandCellData> brandProvider) {
		createPopup = new CreateBrandPopup(panelMediator);

		VerticalPanel panel = new VerticalPanel();

		Panel headerPanel = createHeaderPanel("Marke");
		panel.add(headerPanel);

		BrandCell styleCell = new BrandCell();
		CellList<BrandCellData> styleList = new CellList<BrandCellData>(styleCell, GWT.<FilterListResources> create(FilterListResources.class));
		styleList.setPageSize(30);

		selectionModel = new MultiSelectionModel<BrandCellData>();
		styleList.setSelectionModel(selectionModel);
		selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
			public void onSelectionChange(SelectionChangeEvent event) {
				panelMediator.setSelectedBrands(selectionModel.getSelectedSet());
			}
		});

		brandProvider.addDataDisplay(styleList);
		styleList.addStyleName("styleList");
		panel.add(styleList);

		return panel;
	}
	
	public void showCreatePopup() {
		createPopup.show();
	}

	public void clearSelection() {
		selectionModel.clear();
	}

	class BrandCell extends AbstractCell<BrandCellData> {
		@Override
		public void render(Context context, BrandCellData style, SafeHtmlBuilder sb) {
			if (style == null) {
				return;
			}
			style.render(sb, panelMediator.getSelectedCategory());
		}
	}

}
