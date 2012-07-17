package com.xfashion.client.name;

import com.google.gwt.cell.client.SafeHtmlCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.CellPreviewEvent;
import com.google.gwt.view.client.ListDataProvider;
import com.xfashion.client.IsMinimizable;
import com.xfashion.client.PanelWidthAnimation;
import com.xfashion.client.Xfashion;
import com.xfashion.client.resources.FilterTableResources;
import com.xfashion.client.resources.ImageResources;
import com.xfashion.client.resources.TextMessages;

public class NamePanel implements IsMinimizable, NameFilterHandler {

	protected Panel listPanel;
	protected Panel scrollPanel;
	protected Panel headerPanel;
	protected Image minmaxButton;
	
	protected boolean minimized = false;
	
	protected ListDataProvider<String> dataProvider;
	
	protected String selectedName = null;
	
	protected TextMessages textMessages;
	protected ImageResources images;
	
	public NamePanel(ListDataProvider<String> dataProvider) {
		this.dataProvider = dataProvider;
		textMessages = GWT.create(TextMessages.class);
		images = GWT.create(ImageResources.class);
		
		registerForEvents();
	}
	
	public String getSelectedName() {
		return selectedName;
	}

	public void setSelectedName(String selectedName) {
		this.selectedName = selectedName;
	}
	
	public Panel createPanel() {
		createListPanel();
		scrollPanel = new SimplePanel();
		scrollPanel.setStyleName("filterPanel");
		scrollPanel.add(listPanel);
		return scrollPanel;
	}
		
	public Panel createListPanel() {
		listPanel = new VerticalPanel();

		Panel headerPanel = createHeaderPanel();
		listPanel.add(headerPanel);

		CellTable<String> cellTable = new CellTable<String>(35, GWT.<FilterTableResources> create(FilterTableResources.class));

		Column<String, SafeHtml> price = new Column<String, SafeHtml>(new SafeHtmlCell()) {
			@Override
			public SafeHtml getValue(String s) {
				SafeHtmlBuilder sb = new SafeHtmlBuilder();
				StringBuffer b = new StringBuffer();
				b.append("<div style=\"outline:none;\"");
				if (s.equals(selectedName)) {
					b.append(" class=\"filterRowSelected\"");
				}
				b.append(">");
				sb.appendHtmlConstant(b.toString());
				sb.appendEscaped(s);
				sb.appendHtmlConstant("</div>");
				return sb.toSafeHtml();
			}
		};
		cellTable.addColumn(price);

		CellPreviewEvent.Handler<String> cellPreviewHandler = new CellPreviewEvent.Handler<String>() {
			@Override
			public void onCellPreview(CellPreviewEvent<String> event) {
				if ("click".equals(event.getNativeEvent().getType())) {
					select(event.getValue());
				}
			}
		};
		cellTable.addHandler(cellPreviewHandler, CellPreviewEvent.getType());

		cellTable.setWidth("160px");
		dataProvider.addDataDisplay(cellTable);
		listPanel.add(cellTable);
		
		ScrollPanel sp = new ScrollPanel();
		sp.add(listPanel);
		
		return sp;
	}
	
	@Override
	public void onNameFilter(NameFilterEvent event) {
		selectedName = event.getName();
	}
	
	public void clearSelection() {
		select(null);
	}

	public boolean isMinimized() {
		return minimized;
	}

	public void setMinimized(boolean minimized) {
		if (minimized) {
			if (!this.minimized) {
				minmaxButton.setResource(images.iconMaximize());
			}
		} else {
			if (this.minimized) {
				minmaxButton.setResource(images.iconMinimize());
			}
		}
		this.minimized = minimized;
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
			PanelWidthAnimation pwa = new PanelWidthAnimation(this, 130, 25);
			pwa.run(300);
		}
	}
	
	public void maximize() {
		if (isMinimized()) {
			PanelWidthAnimation pwa = new PanelWidthAnimation(this, 25, 130);
			pwa.run(300);
		}
	}
	
	@Override
	public void setWidth(int width) {
		scrollPanel.setWidth(width + "px");
	}

	protected Panel createHeaderPanel() {
		headerPanel = new HorizontalPanel();
		headerPanel.addStyleName("filterHeader");
		headerPanel.setWidth("160px");

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
		
		headerPanel.add(minmaxButton);

		Label label = new Label(textMessages.name());
		label.addStyleName("filterLabel attributeFilterLabel");
		headerPanel.add(label);
		label.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				clearSelection();
			}
		});

		return headerPanel;
	}

	protected void select(String name) {
		Xfashion.eventBus.fireEvent(new NameFilterEvent(name));		
	}

	private void registerForEvents() {
		Xfashion.eventBus.addHandler(NameFilterEvent.TYPE, this);
	}
	
}
