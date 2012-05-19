package com.xfashion.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.cell.client.ImageResourceCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.CellPreviewEvent;
import com.google.gwt.view.client.ListDataProvider;
import com.xfashion.client.resources.FilterTableResources;
import com.xfashion.shared.FilterCellData2;

public abstract class ResizeableIconFilterPanel<T extends FilterCellData2> extends SimpleFilterPanel<T> {

	public static final int PANEL_MAX_WIDTH = 160;
	public static final int PANEL_MIN_WIDTH = 22;

	public ResizeableIconFilterPanel(ListDataProvider<T> dataProvider) {
		super(dataProvider);
	}

	protected abstract ImageResource getSelectedIcon();

	protected abstract ImageResource getAvailableIcon();

	@Override
	public Panel createTablePanel() {
		VerticalPanel panel = new VerticalPanel();
		Panel headerPanel = createHeaderPanel(getPanelTitle());
		panel.add(headerPanel);

		cellTable = new CellTable<T>(35, GWT.<FilterTableResources> create(FilterTableResources.class));

		cellTable.addColumn(createIconColumn());
		cellTable.addColumn(createNameColumn());
		cellTable.addColumn(createAmountColumn());
		cellTable.addHandler(createSelectHandler(), CellPreviewEvent.getType());
		cellTable.setStyleName("simpleFilterTable");
		dataProvider.addDataDisplay(cellTable);

		panel.add(cellTable);

		setCreateAnchor(new SimplePanel());
		panel.add(getCreateAnchor());

		return panel;
	}

	protected Column<T, ImageResource> createIconColumn() {
		Column<T, ImageResource> column = new Column<T, ImageResource>(new ImageResourceCell()) {
			@Override
			public ImageResource getValue(T dto) {
				if (dto.getArticleAmount() > 0) {
					if (dto.isSelected()) {
						return getSelectedIcon();
					} else {
						return getAvailableIcon();
					}

				} else {
					return getNotAvailableIcon();
				}
			}
		};
		column.setCellStyleNames("filterRowIcon");
		return column;
	}

	@Override
	protected List<Widget> createLeftHeaderButtons() { 
		minmaxButton = new Image();
		if (isMinimized()) {
			minmaxButton.setResource(images.iconMaximize());
		} else {
			minmaxButton.setResource(images.iconMinimize());
		}
		minmaxButton.setStyleName("buttonMinMax");
		ClickHandler minmaxClickHandler = new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				minmax();
			}
		};
		minmaxButton.addClickHandler(minmaxClickHandler);
		
		List<Widget> buttons = new ArrayList<Widget>();
		buttons.add(minmaxButton);
		return buttons;
	}

	public void minmax() {
		if (isMinimized()) {
			maximize();
		} else {
			minimize();
		}
	}

	public void minimize() {
		if (!isMinimized()) {
			PanelWidthAnimation pwa = new PanelWidthAnimation(this, getMaxWidth(), getMinWidth());
			pwa.run(300);
		}
	}

	public void maximize() {
		if (isMinimized()) {
			PanelWidthAnimation pwa = new PanelWidthAnimation(this, getMinWidth(), getMaxWidth());
			pwa.run(300);
		}
	}

	protected int getMinWidth() {
		return PANEL_MIN_WIDTH;		
	}
	
	protected int getMaxWidth() {
		return PANEL_MAX_WIDTH;
	}
	
}
