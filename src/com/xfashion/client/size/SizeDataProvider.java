package com.xfashion.client.size;

import java.util.List;

import com.google.gwt.view.client.HasData;
import com.google.gwt.view.client.ListDataProvider;
import com.xfashion.client.SimpleFilterDataProvider;
import com.xfashion.client.Xfashion;
import com.xfashion.client.db.RefreshFilterEvent;
import com.xfashion.shared.ArticleTypeDTO;
import com.xfashion.shared.SizeDTO;

public class SizeDataProvider extends SimpleFilterDataProvider<SizeDTO> implements SelectSizeHandler, ClearSizeSelectionHandler, DeleteSizeHandler {

	protected ListDataProvider<SizeDTO> leftProvider;
	protected ListDataProvider<SizeDTO> rightProvider;

	protected SplitList<SizeDTO> sizeList;

	public SizeDataProvider() {
		super();
		sizeList = new SplitList<SizeDTO>();
		leftProvider = new ListDataProvider<SizeDTO>();
		leftProvider.setList(sizeList.getFirst());
		rightProvider = new ListDataProvider<SizeDTO>();
		rightProvider.setList(sizeList.getSecond());

		registerForEvents();
	}

	private void registerForEvents() {
		Xfashion.eventBus.addHandler(SelectSizeEvent.TYPE, this);
		Xfashion.eventBus.addHandler(ClearSizeSelectionEvent.TYPE, this);
		// Xfashion.eventBus.addHandler(DeleteSizeEvent.TYPE, this);
	}

	public Long getAttributeContent(ArticleTypeDTO articleType) {
		return articleType.getSizeId();
	}

	@Override
	public void onSelectSize(SelectSizeEvent event) {
		SizeDTO dto = event.getCellData();
		if (dto.isSelected()) {
			getFilter().remove(dto.getId());
			dto.setSelected(false);
		} else {
			getFilter().add(dto.getId());
			dto.setSelected(true);
		}
		fireRefreshEvent();
	}

	@Override
	public void onClearSizeSelection(ClearSizeSelectionEvent event) {
		getFilter().clear();
		fireRefreshEvent();
	}

	@Override
	public void onDeleteSize(DeleteSizeEvent event) {
		sizeList.remove(event.getCellData());
		fireRefreshEvent();
	}

	private void fireRefreshEvent() {
		Xfashion.eventBus.fireEvent(new RefreshFilterEvent());
	}

	@Override
	public List<SizeDTO> getList() {
		return sizeList;
	}

	@Override
	public void refresh() {
		leftProvider.refresh();
		rightProvider.refresh();
	}

	@Override
	public void flush() {
		leftProvider.flush();
		rightProvider.flush();
	}

	@Override
	public void addDataDisplay(HasData<SizeDTO> display) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setList(List<SizeDTO> listToWrap) {
		sizeList.clear();
		sizeList.addAll(listToWrap);
	}

	public ListDataProvider<SizeDTO> getLeftProvider() {
		return leftProvider;
	}

	public ListDataProvider<SizeDTO> getRightProvider() {
		return rightProvider;
	}

}
