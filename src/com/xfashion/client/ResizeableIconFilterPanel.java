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
import com.google.web.bindery.event.shared.EventBus;
import com.xfashion.client.at.event.MaximizeAllFilterPanelsEvent;
import com.xfashion.client.at.event.MaximizeAllFilterPanelsHandler;
import com.xfashion.client.at.event.MinimizeAllFilterPanelsEvent;
import com.xfashion.client.at.event.MinimizeAllFilterPanelsHandler;
import com.xfashion.client.resources.FilterTableResources;
import com.xfashion.shared.FilterCellData;

public abstract class ResizeableIconFilterPanel<T extends FilterCellData> extends SimpleFilterPanel<T> implements MinimizeAllFilterPanelsHandler,
		MaximizeAllFilterPanelsHandler {

	public static final int PANEL_MAX_WIDTH = 160;
	public static final int PANEL_MIN_WIDTH = 22;

	public ResizeableIconFilterPanel(FilterDataProvider<T> dataProvider, EventBus eventBus) {
		super(dataProvider, eventBus);

		registerForEvents();
	}

	protected abstract ImageResource getSelectedIcon();

	protected abstract ImageResource getAvailableIcon();

	@Override
	public Panel createTablePanel() {
		VerticalPanel panel = new VerticalPanel();
		Panel headerPanel = createHeaderPanel(getPanelTitle());
		panel.add(headerPanel);

		cellTable = new CellTable<T>(35, GWT.<FilterTableResources> create(FilterTableResources.class));

		createColumns();
		cellTable.addHandler(createSelectHandler(), CellPreviewEvent.getType());
		cellTable.setStyleName("simpleFilterTable");
		dataProvider.addDataDisplay(cellTable);

		panel.add(cellTable);

		setCreateAnchor(new SimplePanel());
		panel.add(getCreateAnchor());

		return panel;
	}

	@Override
	public void createColumns() {
		cellTable.addColumn(createIconColumn());
		cellTable.addColumn(createNameColumn());
		cellTable.addColumn(createAmountColumn());
	}

	protected ImageResource getNotAvailableIcon() {
		// return images.iconNotAvailable();
		return getAvailableIcon();
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

	@Override
	public void onMaximizeAllFilterPanels(MaximizeAllFilterPanelsEvent event) {
		maximize();
	}

	@Override
	public void onMinimizeAllFilterPanels(MinimizeAllFilterPanelsEvent event) {
		minimize();
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

	private void registerForEvents() {
		eventBus.addHandler(MinimizeAllFilterPanelsEvent.TYPE, this);
		eventBus.addHandler(MaximizeAllFilterPanelsEvent.TYPE, this);
	}

}
