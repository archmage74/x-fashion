package com.xfashion.client.size;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.CellPreviewEvent;
import com.xfashion.client.ErrorEvent;
import com.xfashion.client.ResizeableIconFilterPanel;
import com.xfashion.client.Xfashion;
import com.xfashion.client.resources.FilterTableResources;
import com.xfashion.client.size.event.ClearSizeSelectionEvent;
import com.xfashion.client.size.event.CreateSizeEvent;
import com.xfashion.client.size.event.DeleteSizeEvent;
import com.xfashion.client.size.event.MoveDownSizeEvent;
import com.xfashion.client.size.event.MoveUpSizeEvent;
import com.xfashion.client.size.event.SelectSizeEvent;
import com.xfashion.client.size.event.UpdateSizeEvent;
import com.xfashion.shared.SizeDTO;

public class SizePanel extends ResizeableIconFilterPanel<SizeDTO> {

	protected SizeDataProvider sizeProvider;

	protected CellTable<SizeDTO> cellTable1;
	protected CellTable<SizeDTO> cellTable2;

	public SizePanel(SizeDataProvider dataProvider) {
		super(dataProvider);
		this.sizeProvider = dataProvider;
	}

	@Override
	public Panel createPanel(String [] styles) {
		Panel panel = super.createPanel(styles);
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

		cellTable1.addColumn(createIconColumn());
		cellTable1.addColumn(createNameColumn());
		cellTable1.addColumn(createAmountColumn());
		cellTable1.addHandler(createSelectHandler(), CellPreviewEvent.getType());
		cellTable1.setStyleName("sizeLeftPanel");
		sizeProvider.getLeftProvider().addDataDisplay(cellTable1);

		cellTable2 = new CellTable<SizeDTO>(35, GWT.<FilterTableResources> create(FilterTableResources.class));
		cellTable2.addColumn(createIconColumn());
		cellTable2.addColumn(createNameColumn());
		cellTable2.addColumn(createAmountColumn());
		cellTable2.addHandler(createSelectHandler(), CellPreviewEvent.getType());
		cellTable2.setStyleName("sizeRightPanel");
		sizeProvider.getRightProvider().addDataDisplay(cellTable2);

		splitSizePanel.add(cellTable1);
		splitSizePanel.add(cellTable2);
		panel.add(splitSizePanel);

		setCreateAnchor(new SimplePanel());
		panel.add(getCreateAnchor());

		return panel;
	}
	
	public String getPanelTitle() {
		return textMessages.size();
	}

	protected ImageResource getAvailableIcon() {
		return images.iconSizeUnselected();
	}

	protected ImageResource getSelectedIcon() {
		return images.iconSizeSelected();
	}

	@Override
	protected void moveUp(SizeDTO dto, int index) {
		Xfashion.eventBus.fireEvent(new MoveUpSizeEvent(dto, index));
	}

	@Override
	protected void moveDown(SizeDTO dto, int index) {
		Xfashion.eventBus.fireEvent(new MoveDownSizeEvent(dto, index));
	}
	
	@Override
	protected void updateDTO(SizeDTO dto) {
		Xfashion.eventBus.fireEvent(new UpdateSizeEvent(dto));
	}
	
	@Override
	protected void select(SizeDTO dto) {
		Xfashion.eventBus.fireEvent(new SelectSizeEvent(dto));
	}

	@Override
	public void clearSelection() {
		Xfashion.eventBus.fireEvent(new ClearSizeSelectionEvent());
	}

	@Override
	public void hideTools() {
		removeAdditionalColumns();
		cellTable1.addColumn(createNameColumn());
		cellTable1.addColumn(createAmountColumn());
		cellTable2.addColumn(createNameColumn());
		cellTable2.addColumn(createAmountColumn());
		redrawPanel();
		createAnchor.clear();
	}

	@Override
	public void showTools() {
		removeAdditionalColumns();
		cellTable1.addColumn(createEditNameColumn("editSize"));
		cellTable2.addColumn(createEditNameColumn("editSize"));
		List<Column<SizeDTO, ?>> toolColumns = createToolsColumns();
		for (Column<SizeDTO, ?> c : toolColumns) {
			cellTable1.addColumn(c);
			cellTable2.addColumn(c);
		}
		redrawPanel();
		Widget create = createCreatePanel();
		createAnchor.add(create);
	}

	private void removeAdditionalColumns() {
		while (cellTable1.getColumnCount() > 1) {
			cellTable1.removeColumn(1);
		}
		while (cellTable2.getColumnCount() > 1) {
			cellTable2.removeColumn(1);
		}
	}

	@Override
	protected void redrawPanel() {
		cellTable1.redraw();
		cellTable2.redraw();
	}

	@Override
	public void delete(SizeDTO size) {
		if (size.getArticleAmount() != null && size.getArticleAmount() > 0) {
			Xfashion.eventBus.fireEvent(new ErrorEvent(errorMessages.sizeIsNotEmpty(size.getName())));
		} else {
			Xfashion.eventBus.fireEvent(new DeleteSizeEvent(size));
		}
	}

	@Override
	protected void createDTO() {
		SizeDTO size = new SizeDTO();
		fillDTOFromPanel(size);
		Xfashion.eventBus.fireEvent(new CreateSizeEvent(size));
	}

	@Override
	protected int getMaxWidth() {
		return 240;	
	}
	
	public SizeDataProvider getDataProvider() {
		return sizeProvider;
	}

}
