package com.xfashion.client;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.SelectionChangeEvent;

public class ColorPanel extends FilterPanel {

	private MultiSelectionModel<ColorCellData> selectionModel;
	
	protected CreatePopup createPopup;
	
	public ColorPanel(PanelMediator panelMediator) {
		super(panelMediator);
		panelMediator.setColorPanel(this);
	}
	
	public Panel createPanel(ListDataProvider<ColorCellData> brandProvider) {
		createPopup = new CreateColorPopup(panelMediator);

		VerticalPanel panel = new VerticalPanel();

		Panel headerPanel = createHeaderPanel("Farbe");
		panel.add(headerPanel);

		ColorCell styleCell = new ColorCell();
		CellList<ColorCellData> styleList = new CellList<ColorCellData>(styleCell, GWT.<StyleListResources> create(StyleListResources.class));
		styleList.setPageSize(30);

		selectionModel = new MultiSelectionModel<ColorCellData>();
		styleList.setSelectionModel(selectionModel);
		selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
			public void onSelectionChange(SelectionChangeEvent event) {
				panelMediator.setSelectedColors(selectionModel.getSelectedSet());
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

	class ColorCell extends AbstractCell<ColorCellData> {
		@Override
		public void render(Context context, ColorCellData style, SafeHtmlBuilder sb) {
			if (style == null) {
				return;
			}
			style.render(sb, panelMediator.getSelectedCategory());
		}
	}

}
