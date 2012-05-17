package com.xfashion.client.size;

import java.util.List;

import com.google.gwt.cell.client.EditTextCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.SafeHtmlCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.CellPreviewEvent;
import com.xfashion.client.ErrorEvent;
import com.xfashion.client.FilterPanel2;
import com.xfashion.client.PanelMediator;
import com.xfashion.client.Xfashion;
import com.xfashion.client.resources.FilterTableResources;
import com.xfashion.shared.SizeDTO;

public class SizePanel extends FilterPanel2<SizeDTO> {

	protected SizeDataProvider sizeProvider;

	protected CellTable<SizeDTO> cellTable1;
	protected CellTable<SizeDTO> cellTable2;

	public SizePanel(PanelMediator panelMediator, SizeDataProvider dataProvider) {
		super(panelMediator, dataProvider);
		this.sizeProvider = dataProvider;
		panelMediator.setSizePanel(this);
	}

	public Panel createTablePanel() {
		VerticalPanel panel = new VerticalPanel();
		Panel headerPanel = createHeaderPanel(textMessages.size());
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

	private CellPreviewEvent.Handler<SizeDTO> createSelectHandler() {
		CellPreviewEvent.Handler<SizeDTO> cellPreviewHandler = new CellPreviewEvent.Handler<SizeDTO>() {
			@Override
			public void onCellPreview(CellPreviewEvent<SizeDTO> event) {
				if ("click".equals(event.getNativeEvent().getType())) {
					handleSelection(event);
				}
			}
		};
		return cellPreviewHandler;
	}

	private void handleSelection(CellPreviewEvent<SizeDTO> event) {
		if (editMode && event.getColumn() > 0) {
			switch (event.getColumn()) {
			case 2:
				Xfashion.eventBus.fireEvent(new MoveUpSizeEvent(event.getValue(), event.getIndex()));
				break;
			case 3:
				Xfashion.eventBus.fireEvent(new MoveDownSizeEvent(event.getValue(), event.getIndex()));
				break;
			case 4:
				Xfashion.eventBus.fireEvent(new DeleteSizeEvent(event.getValue()));
				break;
			}
		} else {
			event.getIndex();
			select(event.getValue());
		}
	}

	protected ImageResource getAvailableIcon() {
		return images.iconSizeUnselected();
	}

	protected ImageResource getSelectedIcon() {
		return images.iconSizeSelected();
	}

	private Column<SizeDTO, SafeHtml> createNameColumn() {
		Column<SizeDTO, SafeHtml> column = new Column<SizeDTO, SafeHtml>(new SafeHtmlCell()) {
			@Override
			public SafeHtml getValue(SizeDTO dto) {
				return renderColumn(dto.getName(), false, dto.isSelected());
			}
		};
		column.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		return column;
	}

	private Column<SizeDTO, String> createEditNameColumn() {
		Column<SizeDTO, String> column = new Column<SizeDTO, String>(new EditTextCell()) {
			@Override
			public String getValue(SizeDTO dto) {
				return dto.getName();
			}
		};
		column.setFieldUpdater(new FieldUpdater<SizeDTO, String>() {
			@Override
			public void update(int index, SizeDTO dto, String value) {
				dto.setName(value);
				Xfashion.eventBus.fireEvent(new UpdateSizeEvent(dto));
			}
		});
		column.setCellStyleNames("editSize");
		column.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		return column;
	}

	private Column<SizeDTO, SafeHtml> createAmountColumn() {
		Column<SizeDTO, SafeHtml> column = new Column<SizeDTO, SafeHtml>(new SafeHtmlCell()) {
			@Override
			public SafeHtml getValue(SizeDTO dto) {
				return renderColumn("" + dto.getArticleAmount(), true, dto.isSelected());
			}
		};
		column.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		return column;
	}

	private SafeHtml renderColumn(String txt, boolean isRight, boolean isSelected) {
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

	private void select(SizeDTO dto) {
		Xfashion.eventBus.fireEvent(new SelectSizeEvent(dto));
	}

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
		redrawTables();
		createAnchor.clear();
	}

	@Override
	public void showTools() {
		removeAdditionalColumns();
		cellTable1.addColumn(createEditNameColumn());
		cellTable2.addColumn(createEditNameColumn());
		List<Column<SizeDTO, ?>> toolColumns = createToolsColumns();
		for (Column<SizeDTO, ?> c : toolColumns) {
			cellTable1.addColumn(c);
			cellTable2.addColumn(c);
		}
		redrawTables();
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

	private void redrawTables() {
		cellTable1.redraw();
		cellTable2.redraw();
	}

	public void delete(SizeDTO size) {
		if (size.getArticleAmount() != null && size.getArticleAmount() > 0) {
			Xfashion.eventBus.fireEvent(new ErrorEvent(errorMessages.sizeIsNotEmpty(size.getName())));
		} else {
			Xfashion.eventBus.fireEvent(new DeleteSizeEvent(size));
		}
	}

	public void update(SizeDTO size) {
		Xfashion.eventBus.fireEvent(new UpdateSizeEvent(size));
	}

	@Override
	protected void createDTOFromPanel() {
		SizeDTO size = new SizeDTO();
		fillDTOFromPanel(size);
		Xfashion.eventBus.fireEvent(new CreateSizeEvent(size));
	}

}
