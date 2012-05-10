package com.xfashion.client.color;

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
import com.xfashion.client.SimpleFilterDataProvider;
import com.xfashion.client.FilterPanel;
import com.xfashion.client.PanelMediator;
import com.xfashion.client.ToolPanel;
import com.xfashion.client.Xfashion;
import com.xfashion.client.resources.FilterListResources;
import com.xfashion.shared.ColorDTO;

public class ColorPanel extends FilterPanel<ColorDTO> {

	private MultiSelectionModel<ColorDTO> selectionModel;
	
	public ColorPanel(PanelMediator panelMediator, SimpleFilterDataProvider<ColorDTO> dataProvider) {
		super(panelMediator, dataProvider);
		panelMediator.setColorPanel(this);
	}

	@Override
	public Panel createListPanel() {
		listPanel = new VerticalPanel();

		Panel headerPanel = createHeaderPanel(textMessages.color());
		listPanel.add(headerPanel);

		FilterCell<ColorDTO> filterCell = new FilterCell<ColorDTO>(this, panelMediator);
		cellList = new CellList<ColorDTO>(filterCell, GWT.<FilterListResources> create(FilterListResources.class));
		cellList.setPageSize(38);
		cellList.addRowCountChangeHandler(new RowCountChangeEvent.Handler() {
			@Override
			public void onRowCountChange(RowCountChangeEvent event) {
				if (toolPanel != null) {
					refreshToolsPanel();
				}
			}
		});

		selectionModel = new MultiSelectionModel<ColorDTO>();
		cellList.setSelectionModel(selectionModel);
		selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
			public void onSelectionChange(SelectionChangeEvent event) {
				panelMediator.setSelectedColors(selectionModel.getSelectedSet());
			}
		});

		dataProvider.addDataDisplay(cellList);
		cellList.addStyleName("styleList");
		listPanel.add(cellList);

		setCreateAnchor(new SimplePanel());
		listPanel.add(getCreateAnchor());

		return listPanel;
	}
	
	public void clearSelection() {
		selectionModel.clear();
	}

	@Override
	protected ToolPanel<ColorDTO> createToolPanel() {
		ToolPanel<ColorDTO> tp = new ColorToolPanel(this, panelMediator);
		return tp;
	}

	@Override
	public void delete(ColorDTO color) {
		if (color.getArticleAmount() != null && color.getArticleAmount() > 0) {
			Xfashion.eventBus.fireEvent(new ErrorEvent(errorMessages.colorIsNotEmpty(color.getName())));
			return;
		}
		Xfashion.eventBus.fireEvent(new DeleteColorEvent(color));
	}
	
	@Override
	public void update(ColorDTO color) {
		Xfashion.eventBus.fireEvent(new UpdateColorEvent(color));
		cellList.redraw();
	}

}
