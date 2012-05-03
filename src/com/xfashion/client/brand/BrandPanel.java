package com.xfashion.client.brand;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.RowCountChangeEvent;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.xfashion.client.ErrorEvent;
import com.xfashion.client.FilterCell;
import com.xfashion.client.FilterDataProvider;
import com.xfashion.client.FilterPanel;
import com.xfashion.client.PanelMediator;
import com.xfashion.client.ToolPanel;
import com.xfashion.client.Xfashion;
import com.xfashion.client.resources.FilterListResources;
import com.xfashion.shared.BrandDTO;

public class BrandPanel extends FilterPanel<BrandDTO> {

	private MultiSelectionModel<BrandDTO> selectionModel;
	
	public BrandPanel(PanelMediator panelMediator, FilterDataProvider<BrandDTO> dataProvider) {
		super(panelMediator, dataProvider);
		panelMediator.setBrandPanel(this);
	}
	
	@Override
	public Panel createPanel() {
		VerticalPanel panel = new VerticalPanel();

		Panel headerPanel = createHeaderPanel("Marke");
		panel.add(headerPanel);

		FilterCell<BrandDTO> filterCell = new FilterCell<BrandDTO>(this, panelMediator);
		cellList = new CellList<BrandDTO>(filterCell, GWT.<FilterListResources> create(FilterListResources.class));
		cellList.setPageSize(38);
		cellList.addRowCountChangeHandler(new RowCountChangeEvent.Handler() {
			@Override
			public void onRowCountChange(RowCountChangeEvent event) {
				if (toolPanel != null) {
					refreshToolsPanel();
				}
			}
		});

		selectionModel = new MultiSelectionModel<BrandDTO>();
		cellList.setSelectionModel(selectionModel);
		selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
			public void onSelectionChange(SelectionChangeEvent event) {
				panelMediator.setSelectedBrands(selectionModel.getSelectedSet());
			}
		});

		getDataProvider().addDataDisplay(cellList);
		cellList.addStyleName("styleList");
		panel.add(cellList);

		setCreateAnchor(new SimplePanel());
		panel.add(getCreateAnchor());

		return panel;
	}

	protected ToolPanel<BrandDTO> createToolPanel() {
		ToolPanel<BrandDTO> tp = new BrandToolPanel(this, panelMediator);
		return tp;
	}

	public void clearSelection() {
		selectionModel.clear();
	}
	
	@Override
	public void delete(BrandDTO brand) {
		if (brand.getArticleAmount() != null && brand.getArticleAmount() > 0) {
			Xfashion.eventBus.fireEvent(new ErrorEvent(errorMessages.brandIsNotEmpty(brand.getName())));
			return;
		}
		panelMediator.deleteBrand(brand);
	}
	
	@Override
	public void update(BrandDTO brand) {
		panelMediator.updateBrand(brand);
		cellList.redraw();
	}

}
