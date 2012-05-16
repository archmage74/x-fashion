package com.xfashion.client.size;

import java.util.List;

import com.google.gwt.cell.client.SafeHtmlCell;
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

		cellTable1 = new CellTable<SizeDTO>(35);
		cellTable1.addColumn(createNameColumn());
		cellTable1.addColumn(createAmountColumn());
		cellTable1.addHandler(createSelectHandler(), CellPreviewEvent.getType());
		cellTable1.setWidth("80px");
		sizeProvider.getLeftProvider().addDataDisplay(cellTable1);

		cellTable2 = new CellTable<SizeDTO>(35);
		cellTable2.addColumn(createNameColumn());
		cellTable2.addColumn(createAmountColumn());
		cellTable2.addHandler(createSelectHandler(), CellPreviewEvent.getType());
		cellTable2.setWidth("80px");
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
			case 1:
				break;
			case 2:
				break;
			case 3:
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

	private Column<SizeDTO, SafeHtml> createNameColumn() {
		Column<SizeDTO, SafeHtml> column = new Column<SizeDTO, SafeHtml>(new SafeHtmlCell()) {
			@Override
			public SafeHtml getValue(SizeDTO dto) {
				return renderColumn(dto.getName(), dto.isSelected());
			}
		};
		column.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		return column;
	}

	private Column<SizeDTO, SafeHtml> createAmountColumn() {
		Column<SizeDTO, SafeHtml> column = new Column<SizeDTO, SafeHtml>(new SafeHtmlCell()) {
			@Override
			public SafeHtml getValue(SizeDTO dto) {
				return renderColumn("" + dto.getArticleAmount(), dto.isSelected());
			}
		};
		column.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		return column;
	}

	private SafeHtml renderColumn(String txt, boolean isSelected) {
		SafeHtmlBuilder sb = new SafeHtmlBuilder();
		StringBuffer b = new StringBuffer();
		b.append("<div style=\"outline:none;\"");
		if (isSelected) {
			b.append(" class=\"filterRowSelected\"");
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
		cellTable1.addColumn(createAmountColumn());
		cellTable2.addColumn(createAmountColumn());
		redrawTables();
		createAnchor.clear();
	}

	@Override
	public void showTools() {
		removeAdditionalColumns();
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

	@Override
	public void delete(SizeDTO size) {
		if (size.getArticleAmount() != null && size.getArticleAmount() > 0) {
			Xfashion.eventBus.fireEvent(new ErrorEvent(errorMessages.sizeIsNotEmpty(size.getName())));
			return;
		}
		Xfashion.eventBus.fireEvent(new DeleteSizeEvent(size));
	}

	@Override
	public void update(SizeDTO size) {
		Xfashion.eventBus.fireEvent(new UpdateSizeEvent(size));
		cellTable1.redraw();
		cellTable2.redraw();
	}

	@Override
	protected void createDTOFromPanel() {
		SizeDTO size = new SizeDTO();
		fillDTOFromPanel(size);
		Xfashion.eventBus.fireEvent(new CreateSizeEvent(size));
	}

}
