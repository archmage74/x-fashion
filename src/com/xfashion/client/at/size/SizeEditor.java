package com.xfashion.client.at.size;

import java.util.List;

import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.event.shared.EventBus;
import com.xfashion.client.at.FilterEditor;
import com.xfashion.client.at.size.event.CreateSizeEvent;
import com.xfashion.client.at.size.event.DeleteSizeEvent;
import com.xfashion.client.at.size.event.MoveDownSizeEvent;
import com.xfashion.client.at.size.event.MoveUpSizeEvent;
import com.xfashion.client.at.size.event.UpdateSizeEvent;
import com.xfashion.shared.SizeDTO;

public class SizeEditor extends FilterEditor<SizeDTO> {

	SizePanel sizePanel;
	
	public SizeEditor(SizePanel sizePanel, EventBus adminBus) {
		super(sizePanel, adminBus);
		this.sizePanel = sizePanel;
	}

	@Override
	protected void moveUp(SizeDTO dto, int index) {
		adminBus.fireEvent(new MoveUpSizeEvent(dto, index));
	}

	@Override
	protected void moveDown(SizeDTO dto, int index) {
		adminBus.fireEvent(new MoveDownSizeEvent(dto, index));
	}

	@Override
	protected void delete(SizeDTO dto) {
		adminBus.fireEvent(new DeleteSizeEvent(dto));
	}

	@Override
	protected void createDTO() {
		SizeDTO item = new SizeDTO();
		fillDTOFromPanel(item);
		adminBus.fireEvent(new CreateSizeEvent(item));
	}

	@Override
	public void updateDTO(SizeDTO size) {
		adminBus.fireEvent(new UpdateSizeEvent(size));
	}

	protected void removeColumns() {
		while (sizePanel.getCellTable1().getColumnCount() > 0) {
			sizePanel.getCellTable1().removeColumn(0);
		}
		while (sizePanel.getCellTable2().getColumnCount() > 0) {
			sizePanel.getCellTable2().removeColumn(0);
		}
	}

	@Override
	public void showTools() {
		removeColumns();
		sizePanel.getCellTable1().addColumn(createEditNameColumn("editSize"));
		sizePanel.getCellTable2().addColumn(createEditNameColumn("editSize"));
		List<Column<SizeDTO, ?>> toolColumns = createToolsColumns();
		for (Column<SizeDTO, ?> c : toolColumns) {
			sizePanel.getCellTable1().addColumn(c);
			sizePanel.getCellTable2().addColumn(c);
		}
		sizePanel.getDataProvider().showHidden(true);
		sizePanel.redrawPanel();
		Widget create = createCreatePanel();
		sizePanel.getCreateAnchor().add(create);
	}

}
