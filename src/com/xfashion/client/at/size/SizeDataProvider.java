package com.xfashion.client.at.size;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.view.client.HasData;
import com.google.gwt.view.client.ListDataProvider;
import com.google.web.bindery.event.shared.EventBus;
import com.xfashion.client.SimpleFilterDataProvider;
import com.xfashion.client.Xfashion;
import com.xfashion.client.at.ArticleTypeDataProvider;
import com.xfashion.client.at.size.event.ClearSizeSelectionEvent;
import com.xfashion.client.at.size.event.ClearSizeSelectionHandler;
import com.xfashion.client.at.size.event.SelectSizeEvent;
import com.xfashion.client.at.size.event.SelectSizeHandler;
import com.xfashion.shared.ArticleTypeDTO;
import com.xfashion.shared.SizeDTO;

public class SizeDataProvider extends SimpleFilterDataProvider<SizeDTO> implements SelectSizeHandler, ClearSizeSelectionHandler {

	protected ListDataProvider<SizeDTO> leftProvider;
	protected ListDataProvider<SizeDTO> rightProvider;

	protected SplitList<SizeDTO> sizeList;

	public SizeDataProvider(ArticleTypeDataProvider articleTypeProvider, EventBus eventBus) {
		super(articleTypeProvider, eventBus);
		sizeList = new SplitList<SizeDTO>();
		leftProvider = new ListDataProvider<SizeDTO>();
		leftProvider.setList(sizeList.getFirst());
		rightProvider = new ListDataProvider<SizeDTO>();
		rightProvider.setList(sizeList.getSecond());

		registerForEvents();
	}

	public String getAttributeContent(ArticleTypeDTO articleType) {
		return articleType.getSizeKey();
	}

	@Override
	public void onSelectSize(SelectSizeEvent event) {
		SizeDTO dto = event.getCellData();
		if (dto.isSelected()) {
			getFilter().remove(dto.getKey());
			dto.setSelected(false);
		} else {
			getFilter().add(dto.getKey());
			dto.setSelected(true);
		}
		fireRefreshEvent();
	}

	@Override
	public void onClearSizeSelection(ClearSizeSelectionEvent event) {
		getFilter().clear();
		fireRefreshEvent();
	}

	public void moveDown(int idx) {
		if (idx < 0) {
			return;
		}
		if (idx + 1 >= sizeList.size()) {
			return;
		}
		SizeDTO item = sizeList.remove(idx);
		sizeList.add(idx + 1, item);
		saveList();
	}

	@Override
	public void showHidden(boolean showHidden) {
		super.showHidden(showHidden);
		sizeList.clear();
		sizeList.addAll(getProviderList());
		refresh();
	}

	public List<SizeDTO> getAllItems() {
		return sizeList;
	}
	
	@Override
	public void setAllItems(List<SizeDTO> listToWrap) {
		super.setAllItems(listToWrap);
		sizeList.clear();
		sizeList.addAll(getProviderList());
	}

	@Override
	public void refresh() {
		leftProvider.flush();
		leftProvider.refresh();
		rightProvider.flush();
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

	public ListDataProvider<SizeDTO> getLeftProvider() {
		return leftProvider;
	}

	public ListDataProvider<SizeDTO> getRightProvider() {
		return rightProvider;
	}

	public void readSizes() {
		AsyncCallback<List<SizeDTO>> callback = new AsyncCallback<List<SizeDTO>>() {
			@Override
			public void onFailure(Throwable caught) {
				Xfashion.fireError(caught.getMessage());
			}

			@Override
			public void onSuccess(List<SizeDTO> result) {
				storeSizes(result);
			}
		};
		articleTypeService.readSizes(callback);
	}

	@Override
	protected void saveItem(SizeDTO dto) {
		AsyncCallback<Void> callback = new AsyncCallback<Void>() {
			@Override
			public void onFailure(Throwable caught) {
				Xfashion.fireError(caught.getMessage());
			}

			@Override
			public void onSuccess(Void result) {
				fireRefreshEvent();
			}
		};
		articleTypeService.updateSize(dto, callback);
	}

	@Override
	protected void saveList() {
		AsyncCallback<List<SizeDTO>> callback = new AsyncCallback<List<SizeDTO>>() {
			@Override
			public void onFailure(Throwable caught) {
				Xfashion.fireError("Could not save sizes: " + caught.getMessage());
			}

			@Override
			public void onSuccess(List<SizeDTO> result) {
				storeSizes(result);
			}
		};
		articleTypeService.updateSizes(new ArrayList<SizeDTO>(sizeList), callback);
	}

	private void storeSizes(List<SizeDTO> result) {
		if (result == null) {
			result = new ArrayList<SizeDTO>();
		}
		setAllItems(result);
		fireRefreshEvent();
	}

	private void registerForEvents() {
		eventBus.addHandler(SelectSizeEvent.TYPE, this);
		eventBus.addHandler(ClearSizeSelectionEvent.TYPE, this);
	}

}
