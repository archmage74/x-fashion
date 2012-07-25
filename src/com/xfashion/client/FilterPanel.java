package com.xfashion.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.cell.client.EditTextCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.ImageResourceCell;
import com.google.gwt.cell.client.SafeHtmlCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.CellPreviewEvent;
import com.xfashion.client.resources.ErrorMessages;
import com.xfashion.client.resources.ImageResources;
import com.xfashion.client.resources.TextMessages;
import com.xfashion.client.tool.Buttons;
import com.xfashion.shared.FilterCellData;

public abstract class FilterPanel<T extends FilterCellData> implements IsMinimizable {

	protected HorizontalPanel headerPanel;
	protected Panel scrollPanel;
	protected Panel tablePanel;

	protected Panel createAnchor;
	protected TextBox createTextBox;

	protected boolean minimized = false;
	protected Image minmaxButton;

	protected boolean editMode = false;

	protected TextMessages textMessages;
	protected ErrorMessages errorMessages;
	protected ImageResources images;

	public FilterPanel() {
		errorMessages = GWT.create(ErrorMessages.class);
		textMessages = GWT.create(TextMessages.class);
		images = GWT.<ImageResources> create(ImageResources.class);
	}

	public abstract Panel createTablePanel();

	public abstract void clearSelection();

	public abstract void hideTools();

	public abstract void showTools();
	
	public abstract FilterDataProvider<T> getDataProvider();
	
	public abstract String getPanelTitle();

	protected abstract void handleSelection(CellPreviewEvent<T> event);

	protected abstract void moveUp(T dto, int index);

	protected abstract void moveDown(T dto, int index);
	
	protected abstract void delete(T dto);
	
	protected abstract void select(T dto);
	
	protected abstract void updateDTO(T dto);
	
	protected abstract void createDTO();
	
	protected abstract void redrawPanel();

	public Panel createPanel() {
		return createPanel(null);
	}

	public Panel createPanel(String[] additionalStyles) {
		tablePanel = createTablePanel();
		scrollPanel = new SimplePanel();
		scrollPanel.setStyleName("filterPanel");
		if (additionalStyles != null) {
			for (String style : additionalStyles) {
				scrollPanel.addStyleName(style);
			}
		}
		scrollPanel.add(tablePanel);
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

		Image toolsButton = Buttons.showTools();
		ClickHandler toolsButtonClickHandler = new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				toggleTools();
			}
		};
		toolsButton.addClickHandler(toolsButtonClickHandler);
		headerPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		headerPanel.add(toolsButton);

		label.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				clearSelection();
			}
		});

		return headerPanel;
	}

	public void toggleTools() {
		if (editMode) {
			editMode = false;
			hideTools();
		} else {
			showTools();
			editMode = true;
		}
	}

	protected List<Column<T, ?>> createToolsColumns() {
		List<Column<T, ?>> columns = new ArrayList<Column<T, ?>>();
		columns.add(createUpColumn());
		columns.add(createDownColumn());
		columns.add(createDeleteColumn());
		return columns;
	}

	protected Column<T, String> createEditNameColumn() {
		return createEditNameColumn(null);
	}
	
	protected Column<T, String> createEditNameColumn(String style) {
		Column<T, String> column = new Column<T, String>(new EditTextCell()) {
			@Override
			public String getValue(T dto) {
				if (dto.getHidden()) {
					return "(" + dto.getName() + ")";
				} else {
					return dto.getName();
				}
			}
		};
		column.setFieldUpdater(new FieldUpdater<T, String>() {
			@Override
			public void update(int index, T dto, String value) {
				dto.setName(value);
				updateDTO(dto);
			}
		});
		if (style != null) {
			column.setCellStyleNames(style);
		} else {
			column.setCellStyleNames("editFilter");
		}
		column.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		return column;
	}

	private Column<T, ImageResource> createUpColumn() {
		Column<T, ImageResource> tool = new Column<T, ImageResource>(new ImageResourceCell()) {
			@Override
			public ImageResource getValue(T object) {
				return images.iconUp();
			}
		};
		tool.setCellStyleNames("buttonTool");
		return tool;
	}

	private Column<T, ImageResource> createDownColumn() {
		Column<T, ImageResource> tool = new Column<T, ImageResource>(new ImageResourceCell()) {
			@Override
			public ImageResource getValue(T object) {
				return images.iconDown();
			}
		};
		tool.setCellStyleNames("buttonTool");
		return tool;
	}

	private Column<T, ImageResource> createDeleteColumn() {
		Column<T, ImageResource> tool = new Column<T, ImageResource>(new ImageResourceCell()) {
			@Override
			public ImageResource getValue(T object) {
				return images.iconDelete();
			}
		};
		tool.setCellStyleNames("buttonTool");
		return tool;
	}

	protected Column<T, SafeHtml> createNameColumn() {
		Column<T, SafeHtml> column = new Column<T, SafeHtml>(new SafeHtmlCell()) {
			@Override
			public SafeHtml getValue(T dto) {
				return renderColumn(dto.getName(), false, dto.isSelected());
			}
		};
		column.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		return column;
	}

	protected Column<T, SafeHtml> createAmountColumn() {
		Column<T, SafeHtml> column = new Column<T, SafeHtml>(new SafeHtmlCell()) {
			@Override
			public SafeHtml getValue(T dto) {
				return renderColumn("" + dto.getArticleAmount(), true, dto.isSelected());
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

	protected SafeHtml renderColumn(String txt, boolean isRight, boolean isSelected) {
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

	protected Widget createCreatePanel() {
		Grid createGrid = new Grid(2, 1);

		createTextBox = new TextBox();
		createTextBox.setMaxLength(12);
		createTextBox.setWidth("140px");
		createGrid.setWidget(0, 0, createTextBox);

		Button createButton = new Button("Anlegen");
		createButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				createDTO();
			}
		});
		createGrid.setWidget(1, 0, createButton);

		return createGrid;
	}

	protected void fillDTOFromPanel(T dto) {
		String name = createTextBox.getText();
		if (name == null || name.length() == 0) {
			Xfashion.eventBus.fireEvent(new ErrorEvent(errorMessages.createAttributeNoName()));
			return;
		}
		dto.setName(name);
	}

	@Override
	public void setWidth(int width) {
		scrollPanel.setWidth(width + "px");
	}
	
	protected List<Widget> createLeftHeaderButtons() {
		return null;
	}

	public Panel getCreateAnchor() {
		return createAnchor;
	}

	public void setCreateAnchor(Panel createAnchor) {
		this.createAnchor = createAnchor;
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

}
