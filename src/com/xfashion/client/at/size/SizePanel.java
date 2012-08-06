package com.xfashion.client.at.size;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.CellPreviewEvent;
import com.google.web.bindery.event.shared.EventBus;
import com.xfashion.client.ResizeableIconFilterPanel;
import com.xfashion.client.at.size.event.ClearSizeSelectionEvent;
import com.xfashion.client.at.size.event.SelectSizeEvent;
import com.xfashion.client.resources.FilterTableResources;
import com.xfashion.shared.SizeDTO;

public class SizePanel extends ResizeableIconFilterPanel<SizeDTO> {

	protected SizeDataProvider sizeProvider;

	protected CellTable<SizeDTO> cellTable1;
	protected CellTable<SizeDTO> cellTable2;

	public SizePanel(SizeDataProvider dataProvider, EventBus eventBus) {
		super(dataProvider, eventBus);
		this.sizeProvider = dataProvider;
	}
	
	public SizeDataProvider getDataProvider() {
		return sizeProvider;
	}

	public CellTable<SizeDTO> getCellTable1() {
		return cellTable1;
	}

	public CellTable<SizeDTO> getCellTable2() {
		return cellTable2;
	}

	public String getPanelTitle() {
		return textMessages.size();
	}

	@Override
	public Panel createAdminPanel(String [] styles) {
		Panel panel = super.createAdminPanel(styles);
		panel.setWidth(getMaxWidth() + "px");
		return panel;
	}
	
	@Override
	public Panel createTablePanel() {
		VerticalPanel panel = new VerticalPanel();
		Panel headerPanel = createHeaderPanel(getPanelTitle());
		panel.add(headerPanel);

		HorizontalPanel splitSizePanel = new HorizontalPanel();

		cellTable1 = new CellTable<SizeDTO>(35, GWT.<FilterTableResources> create(FilterTableResources.class));
		cellTable1.addHandler(createSelectHandler(), CellPreviewEvent.getType());
		cellTable1.setStyleName("sizeLeftPanel");
		sizeProvider.getLeftProvider().addDataDisplay(cellTable1);

		cellTable2 = new CellTable<SizeDTO>(35, GWT.<FilterTableResources> create(FilterTableResources.class));
		cellTable2.addHandler(createSelectHandler(), CellPreviewEvent.getType());
		cellTable2.setStyleName("sizeRightPanel");
		sizeProvider.getRightProvider().addDataDisplay(cellTable2);

		createColumns();

		splitSizePanel.add(cellTable1);
		splitSizePanel.add(cellTable2);
		panel.add(splitSizePanel);

		setCreateAnchor(new SimplePanel());
		panel.add(getCreateAnchor());

		return panel;
	}

	@Override
	public void createColumns() {
		cellTable1.addColumn(createIconColumn());
		cellTable1.addColumn(createNameColumn());
		cellTable1.addColumn(createAmountColumn());
		cellTable2.addColumn(createIconColumn());
		cellTable2.addColumn(createNameColumn());
		cellTable2.addColumn(createAmountColumn());
	}
	
	@Override
	public void select(SizeDTO dto) {
		eventBus.fireEvent(new SelectSizeEvent(dto));
	}
	
	protected ImageResource getAvailableIcon() {
		return images.iconSizeUnselected();
	}

	protected ImageResource getSelectedIcon() {
		return images.iconSizeSelected();
	}

	@Override
	public void clearSelection() {
		eventBus.fireEvent(new ClearSizeSelectionEvent());
	}

	@Override
	public void redrawPanel() {
		cellTable1.redraw();
		cellTable2.redraw();
	}

	@Override
	protected int getMaxWidth() {
		return 240;	
	}

}
