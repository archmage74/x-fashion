package com.xfashion.client.style;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.RowCountChangeEvent;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.xfashion.client.CreatePopup;
import com.xfashion.client.FilterDataProvider;
import com.xfashion.client.FilterPanel;
import com.xfashion.client.PanelMediator;
import com.xfashion.client.ToolPanel;
import com.xfashion.client.resources.FilterListResources;
import com.xfashion.shared.StyleDTO;

public class StylePanel extends FilterPanel<StyleDTO> {

	private MultiSelectionModel<StyleDTO> selectionModel;
	
	protected CreatePopup createPopup;

	public StylePanel(PanelMediator panelMediator, FilterDataProvider<StyleDTO> dataProvider) {
		super(panelMediator, dataProvider);
		panelMediator.setStylePanel(this);
	}
	
	public Panel createPanel() {
		VerticalPanel panel = new VerticalPanel();

		headerPanel = createHeaderPanel("Stil");
		panel.add(headerPanel);

		StyleCell styleCell = new StyleCell();
		cellList = new CellList<StyleDTO>(styleCell, GWT.<FilterListResources> create(FilterListResources.class));
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
	
	public void showCreatePopup() {
		createPopup.show();
	}
	
	public void clearSelection() {
		selectionModel.clear();
	}

	class StyleCell extends AbstractCell<StyleDTO> {
		@Override
		public void render(Context context, StyleDTO style, SafeHtmlBuilder sb) {
			if (style == null) {
				return;
			}
			style.render(sb, panelMediator.getSelectedCategory());
		}
	}

	@Override
	protected ToolPanel<StyleDTO> createToolPanel() {
		ToolPanel<StyleDTO> tp = new StyleToolPanel(this, panelMediator);
		return tp;
	}

	@Override
	public void delete(StyleDTO item) {
		panelMediator.deleteStyle(item);
	}

	@Override
	public void update(StyleDTO item) {
		panelMediator.updateStyle(item);
	}

}
