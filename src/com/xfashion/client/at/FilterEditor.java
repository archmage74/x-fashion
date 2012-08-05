package com.xfashion.client.at;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.cell.client.EditTextCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.ImageResourceCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.CellPreviewEvent;
import com.google.web.bindery.event.shared.EventBus;
import com.xfashion.client.SimpleFilterPanel;
import com.xfashion.client.Xfashion;
import com.xfashion.client.resources.ErrorMessages;
import com.xfashion.client.resources.ImageResources;
import com.xfashion.client.resources.TextMessages;
import com.xfashion.client.tool.Buttons;
import com.xfashion.client.user.UserManagement;
import com.xfashion.shared.FilterCellData;
import com.xfashion.shared.UserRole;

public abstract class FilterEditor<T extends FilterCellData> {

	protected boolean editMode = false;
	protected EventBus adminBus;

	protected SimpleFilterPanel<T> filterPanel;

	protected TextBox createTextBox;
	
	protected ImageResources images;
	protected TextMessages textMessages;
	protected ErrorMessages errorMessages;
	
	public FilterEditor(SimpleFilterPanel<T> filterPanel, EventBus adminBus) {
		this.adminBus = adminBus;
		this.filterPanel = filterPanel;
		this.filterPanel.setFilterEditor(this);
		this.images = GWT.create(ImageResources.class);
		this.textMessages = GWT.create(TextMessages.class);
		this.errorMessages = GWT.create(ErrorMessages.class);
	}
	
	protected abstract void moveUp(T dto, int index);

	protected abstract void moveDown(T dto, int index);
	
	protected abstract void delete(T dto);
	
	protected abstract void updateDTO(T dto);
	
	protected abstract void createDTO();
	
	public SimpleFilterPanel<T> getFilterPanel() {
		return filterPanel;
	}

	public void setFilterPanel(SimpleFilterPanel<T> filterPanel) {
		this.filterPanel = filterPanel;
	}

	public void init() {
		if (UserManagement.hasRole(UserRole.ADMIN, UserRole.DEVELOPER)) {
			Image toolsButton = Buttons.showTools();
			ClickHandler toolsButtonClickHandler = new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					toggleTools();
				}
			};
			toolsButton.addClickHandler(toolsButtonClickHandler);
			filterPanel.getToolsAnchor().add(toolsButton);
		}
	}
	
	public boolean isEditMode() {
		return editMode;
	}

	public void handleSelection(CellPreviewEvent<T> event) {
		if (editMode && event.getColumn() > 0) {
			switch (event.getColumn()) {
			case 1:
				moveUp(event.getValue(), event.getIndex());
				break;
			case 2:
				moveDown(event.getValue(), event.getIndex());
				break;
			case 3:
				delete(event.getValue());
				break;
			}
		} else {
			filterPanel.select(event.getValue());
		}
	}

	public void hideTools() {
		removeColumns();
		
		filterPanel.createColumns();
		filterPanel.getDataProvider().showHidden(false);
		filterPanel.redrawPanel();
		filterPanel.getCreateAnchor().clear();
	}

	public void showTools() {
		removeColumns();
		filterPanel.getCellTable().addColumn(createEditNameColumn());
		List<Column<T, ?>> toolColumns = createToolsColumns();
		for (Column<T, ?> c : toolColumns) {
			filterPanel.getCellTable().addColumn(c);
		}
		filterPanel.getDataProvider().showHidden(true);
		filterPanel.redrawPanel();
		Widget create = createCreatePanel();
		filterPanel.getCreateAnchor().add(create);
	}

	private void removeColumns() {
		CellTable<T> cellTable = filterPanel.getCellTable();
		while (cellTable.getColumnCount() > 0) {
			cellTable.removeColumn(0);
		}
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

	protected void fillDTOFromPanel(T dto) {
		String name = createTextBox.getText();
		if (name == null || name.length() == 0) {
			Xfashion.fireError(errorMessages.createAttributeNoName());
			return;
		}
		dto.setName(name);
	}

	protected Widget createCreatePanel() {
		Grid createGrid = new Grid(2, 1);

		createTextBox = new TextBox();
		createTextBox.setMaxLength(12);
		createTextBox.setWidth("140px");
		createGrid.setWidget(0, 0, createTextBox);

		Button createButton = new Button(textMessages.create());
		createButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				createDTO();
			}
		});
		createGrid.setWidget(1, 0, createButton);

		return createGrid;
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

}
