package com.xfashion.client.size;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.xfashion.client.CreatePopup;
import com.xfashion.client.FilterDataProvider;
import com.xfashion.client.FilterPanel;
import com.xfashion.client.PanelMediator;
import com.xfashion.client.ToolPanel;
import com.xfashion.client.resources.FilterListResources;

public class SizePanel extends FilterPanel<SizeCellData> {

	private MultiSelectionModel<SizeCellData> selectionModel;
	
	protected CreatePopup createPopup;
	
	public SizePanel(PanelMediator panelMediator, FilterDataProvider<SizeCellData> dataProvider) {
		super(panelMediator, dataProvider);
		panelMediator.setSizePanel(this);
	}
	
	public Panel createPanel() {
		createPopup = new CreateSizePopup(panelMediator);

		VerticalPanel panel = new VerticalPanel();

		Panel headerPanel = createHeaderPanel("Größe");
		panel.add(headerPanel);

		SizeCell styleCell = new SizeCell();
		cellList = new CellList<SizeCellData>(styleCell, GWT.<FilterListResources> create(FilterListResources.class));
		cellList.setPageSize(30);

		selectionModel = new MultiSelectionModel<SizeCellData>();
		cellList.setSelectionModel(selectionModel);
		selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
			public void onSelectionChange(SelectionChangeEvent event) {
				panelMediator.setSelectedSizes(selectionModel.getSelectedSet());
			}
		});

		dataProvider.addDataDisplay(cellList);
		cellList.addStyleName("styleList");
		panel.add(cellList);

		Panel createAnchor = new SimplePanel();
		panel.add(createAnchor);

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

	@Override
	protected ToolPanel<SizeCellData> createToolPanel() {
		// TODO
		return null;
	}

	@Override
	public void delete(SizeCellData item) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void edit(SizeCellData item) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void moveUp(SizeCellData item) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void moveDown(SizeCellData item) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(SizeCellData item) {
		// TODO Auto-generated method stub
		
	}

}
