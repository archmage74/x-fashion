package com.xfashion.client.style;

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
import com.xfashion.shared.StyleDTO;

public class StylePanel extends FilterPanel<StyleDTO> {

	private MultiSelectionModel<StyleDTO> selectionModel;
	
	public StylePanel(PanelMediator panelMediator, FilterDataProvider<StyleDTO> dataProvider) {
		super(panelMediator, dataProvider);
		panelMediator.setStylePanel(this);
	}
	
	public Panel createPanel() {
		VerticalPanel panel = new VerticalPanel();

		headerPanel = createHeaderPanel("Stil");
		panel.add(headerPanel);

		FilterCell<StyleDTO> filterCell = new FilterCell<StyleDTO>(this, panelMediator);
		cellList = new CellList<StyleDTO>(filterCell, GWT.<FilterListResources> create(FilterListResources.class));
		cellList.setPageSize(30);
		cellList.addRowCountChangeHandler(new RowCountChangeEvent.Handler() {
			@Override
			public void onRowCountChange(RowCountChangeEvent event) {
				if (toolPanel != null) {
					refreshToolsPanel();
				}
			}
		});

		selectionModel = new MultiSelectionModel<StyleDTO>();
		cellList.setSelectionModel(selectionModel);
		selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
			public void onSelectionChange(SelectionChangeEvent event) {
				panelMediator.setSelectedStyles(selectionModel.getSelectedSet());
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
	protected ToolPanel<StyleDTO> createToolPanel() {
		ToolPanel<StyleDTO> tp = new StyleToolPanel(this, panelMediator);
		return tp;
	}

	@Override
	public void delete(StyleDTO item) {
		if (item.getArticleAmount() != null && item.getArticleAmount() > 0) {
			Xfashion.eventBus.fireEvent(new ErrorEvent(errorMessages.styleIsNotEmpty(item.getName())));
			return;
		}
		panelMediator.deleteStyle(item);
	}

	@Override
	public void update(StyleDTO item) {
		panelMediator.updateStyle(item);
		getDataProvider().refresh();
	}

}
