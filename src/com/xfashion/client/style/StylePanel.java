package com.xfashion.client.style;

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

public class StylePanel extends FilterPanel {

	private MultiSelectionModel<StyleCellData> selectionModel;
	
	protected CreatePopup createPopup;

	public StylePanel(PanelMediator panelMediator) {
		super(panelMediator);
		panelMediator.setStylePanel(this);
	}
	
	public Panel createPanel(ListDataProvider<StyleCellData> styleProvider) {
		createPopup = new CreateStylePopup(panelMediator);
		
		VerticalPanel panel = new VerticalPanel();

		headerPanel = createHeaderPanel("Stil");
		panel.add(headerPanel);

		StyleCell styleCell = new StyleCell();
		final CellList<StyleCellData> styleList = new CellList<StyleCellData>(styleCell, GWT.<FilterListResources> create(FilterListResources.class));
		styleList.setPageSize(30);

		selectionModel = new MultiSelectionModel<StyleCellData>();
		styleList.setSelectionModel(selectionModel);
		selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
			public void onSelectionChange(SelectionChangeEvent event) {
				panelMediator.setSelectedStyles(selectionModel.getSelectedSet());
			}
		});

		styleProvider.addDataDisplay(styleList);
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

	class StyleCell extends AbstractCell<StyleCellData> {
		@Override
		public void render(Context context, StyleCellData style, SafeHtmlBuilder sb) {
			if (style == null) {
				return;
			}
			style.render(sb, panelMediator.getSelectedCategory());
		}
	}

}
