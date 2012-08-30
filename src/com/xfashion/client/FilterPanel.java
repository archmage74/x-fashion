package com.xfashion.client;

import java.util.List;

import com.google.gwt.cell.client.SafeHtmlCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.CellPreviewEvent;
import com.google.web.bindery.event.shared.EventBus;
import com.xfashion.client.at.FilterEditor;
import com.xfashion.client.resources.ErrorMessages;
import com.xfashion.client.resources.ImageResources;
import com.xfashion.client.resources.TextMessages;
import com.xfashion.shared.FilterCellData;

public abstract class FilterPanel<T extends FilterCellData> implements IsMinimizable {

	protected EventBus eventBus;

	protected HorizontalPanel headerPanel;
	protected Panel scrollPanel;
	protected Panel tablePanel;

	protected Panel createAnchor;
	protected Panel toolsAnchor;

	protected boolean minimized = false;
	protected Image minmaxButton;

	protected boolean editMode = false;
	protected FilterEditor<T> filterEditor;

	protected TextMessages textMessages;
	protected ErrorMessages errorMessages;
	protected ImageResources images;

	public FilterPanel(EventBus eventBus) {
		this(eventBus, null);
	}
	
	public FilterPanel(EventBus eventBus, FilterEditor<T> filterEditor) {
		this.filterEditor = filterEditor;
		this.eventBus = eventBus;
		this.errorMessages = GWT.create(ErrorMessages.class);
		this.textMessages = GWT.create(TextMessages.class);
		this.images = GWT.<ImageResources> create(ImageResources.class);
	}

	public abstract Panel createTablePanel();

	public abstract void clearSelection();

	public abstract FilterDataProvider<T> getDataProvider();
	
	public abstract String getPanelTitle();

	public abstract void select(T dto);
	
	public abstract void redrawPanel();

	public EventBus getEventBus() {
		return eventBus;
	}

	public FilterEditor<T> getFilterEditor() {
		return filterEditor;
	}

	public void setFilterEditor(FilterEditor<T> filterEditor) {
		this.filterEditor = filterEditor;
	}

	public Panel getCreateAnchor() {
		return createAnchor;
	}

	protected void setCreateAnchor(Panel createAnchor) {
		this.createAnchor = createAnchor;
	}

	public Panel getToolsAnchor() {
		return toolsAnchor;
	}

	public Panel getTablePanel() {
		return tablePanel;
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

	protected void handleSelection(CellPreviewEvent<T> event) {
		if (filterEditor != null) {
			filterEditor.handleSelection(event);
		} else {
			select(event.getValue());
		}
	}

	public Panel createAdminPanel() {
		return createAdminPanel(null);
	}

	public Panel createAdminPanel(String[] additionalStyles) {
		tablePanel = createTablePanel();
		scrollPanel = new SimplePanel();
		scrollPanel.setStyleName("filterPanel");
		if (additionalStyles != null) {
			for (String style : additionalStyles) {
				scrollPanel.addStyleName(style);
			}
		}
		scrollPanel.add(tablePanel);

		if (filterEditor != null) {
			filterEditor.init();
		}
		
		return scrollPanel;
	}
	
	protected Panel createHeaderPanel(String title) {
		headerPanel = new HorizontalPanel();
		headerPanel.addStyleName("filterHeader");

		List<Widget> buttons = createLeftHeaderButtons();
		if (buttons != null) {
			for (Widget w : buttons) {
				headerPanel.add(w);
			}
		}

		Label label = new Label(title);
		label.addStyleName("filterLabel attributeFilterLabel");
		headerPanel.add(label);

		toolsAnchor = new SimplePanel();
		headerPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		headerPanel.add(toolsAnchor);
		
		label.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				clearSelection();
			}
		});

		return headerPanel;
	}
	
	protected Column<T, SafeHtml> createNameColumn() {
		Column<T, SafeHtml> column = new Column<T, SafeHtml>(new SafeHtmlCell()) {
			@Override
			public SafeHtml getValue(T dto) {
				return renderColumn(dto, dto.getName(), false);
			}
		};
		column.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		return column;
	}

	protected Column<T, SafeHtml> createAmountColumn() {
		Column<T, SafeHtml> column = new Column<T, SafeHtml>(new SafeHtmlCell()) {
			@Override
			public SafeHtml getValue(T dto) {
				return renderColumn(dto, "" + dto.getArticleAmount(), true);
			}
		};
		column.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		return column;
	}

	protected CellPreviewEvent.Handler<T> createSelectHandler() {
		CellPreviewEvent.Handler<T> cellPreviewHandler = new CellPreviewEvent.Handler<T>() {
			@Override
			public void onCellPreview(CellPreviewEvent<T> event) {
				if ("click".equals(event.getNativeEvent().getType())) {
					handleSelection(event);
				}
			}
		};
		return cellPreviewHandler;
	}

	protected SafeHtml renderColumn(T dto, String txt, boolean isRight) {
		boolean isSelected = getDataProvider().getFilter().contains(dto.getKey()); 
		SafeHtmlBuilder sb = new SafeHtmlBuilder();
		StringBuffer b = new StringBuffer();
		b.append("<div style=\"outline:none;\"");
		if (isSelected) {
			if (isRight) {
				b.append(" class=\"filterRowSelected filterRightRowSelected\"");
			} else {
				b.append(" class=\"filterRowSelected filterMiddleRowSelected\"");
			}
		} else {
			if (isRight) {
				b.append(" class=\"filterRowUnselected filterRightRowUnselected\"");
			} else {
				b.append(" class=\"filterRowUnselected filterMiddleRowUnselected\"");
			}
		}
		b.append(">");
		sb.appendHtmlConstant(b.toString());
		sb.appendEscaped(txt);
		sb.appendHtmlConstant("</div>");
		return sb.toSafeHtml();
	}

	@Override
	public void setWidth(int width) {
		scrollPanel.setWidth(width + "px");
	}
	
	protected List<Widget> createLeftHeaderButtons() {
		return null;
	}

}
