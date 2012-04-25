package com.xfashion.client.size;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.RowCountChangeEvent;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.xfashion.client.FilterCell;
import com.xfashion.client.FilterDataProvider;
import com.xfashion.client.FilterPanel;
import com.xfashion.client.PanelMediator;
import com.xfashion.client.ToolPanel;
import com.xfashion.client.resources.FilterListResources;
import com.xfashion.shared.SizeDTO;

public class SizePanel extends FilterPanel<SizeDTO> {

	private MultiSelectionModel<SizeDTO> selectionModel;
	
	public SizePanel(PanelMediator panelMediator, FilterDataProvider<SizeDTO> dataProvider) {
		super(panelMediator, dataProvider);
		panelMediator.setSizePanel(this);
	}
	
	public Panel createPanel() {
		VerticalPanel panel = new VerticalPanel();

		Panel headerPanel = createHeaderPanel("Größe");
		panel.add(headerPanel);

		FilterCell<SizeDTO> filterCell = new FilterCell<SizeDTO>(this, panelMediator);
		cellList = new CellList<SizeDTO>(filterCell, GWT.<FilterListResources> create(FilterListResources.class));
		cellList.setPageSize(30);
		cellList.addRowCountChangeHandler(new RowCountChangeEvent.Handler() {
			@Override
			public void onRowCountChange(RowCountChangeEvent event) {
				if (toolPanel != null) {
					refreshToolsPanel();
				}
			}
		});

		selectionModel = new MultiSelectionModel<SizeDTO>();
		cellList.setSelectionModel(selectionModel);
		selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
			public void onSelectionChange(SelectionChangeEvent event) {
				panelMediator.setSelectedSizes(selectionModel.getSelectedSet());
			}
		});

		dataProvider.addDataDisplay(cellList);
		cellList.addStyleName("styleList");
		panel.add(cellList);

		setCreateAnchor(new SimplePanel());
		panel.add(getCreateAnchor());

		return panel;
	}
	
	public void clearSelection() {
		selectionModel.clear();
	}

	@Override
	protected ToolPanel<SizeDTO> createToolPanel() {
		ToolPanel<SizeDTO> tp = new SizeToolPanel(this, panelMediator);
		return tp;
	}

	@Override
	public void delete(SizeDTO size) {
		if (size.getArticleAmount() != null && size.getArticleAmount() > 0) {
			panelMediator.showError(errorMessages.sizeIsNotEmpty(size.getName()));
			return;
		}
		panelMediator.deleteSize(size);
	}
	
	@Override
	public void update(SizeDTO size) {
		panelMediator.updateSize(size);
		cellList.redraw();
	}

}
