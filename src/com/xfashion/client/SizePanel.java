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

public class SizePanel extends FilterPanel {

	private MultiSelectionModel<SizeCellData> selectionModel;
	
	protected CreatePopup createPopup;
	
	public SizePanel(PanelMediator panelMediator) {
		super(panelMediator);
		panelMediator.setSizePanel(this);
	}
	
	public Panel createPanel(ListDataProvider<SizeCellData> brandProvider) {
		createPopup = new CreateSizePopup(panelMediator);

		VerticalPanel panel = new VerticalPanel();

		Panel headerPanel = createHeaderPanel("Größe");
		panel.add(headerPanel);

		SizeCell styleCell = new SizeCell();
		CellList<SizeCellData> styleList = new CellList<SizeCellData>(styleCell, GWT.<StyleListResources> create(StyleListResources.class));
		styleList.setPageSize(30);

		selectionModel = new MultiSelectionModel<SizeCellData>();
		styleList.setSelectionModel(selectionModel);
		selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
			public void onSelectionChange(SelectionChangeEvent event) {
				panelMediator.setSelectedSizes(selectionModel.getSelectedSet());
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

	class SizeCell extends AbstractCell<SizeCellData> {
		@Override
		public void render(Context context, SizeCellData style, SafeHtmlBuilder sb) {
			if (style == null) {
				return;
			}
			style.render(sb, panelMediator.getSelectedCategory());
		}
	}

}
