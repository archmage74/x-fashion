package com.xfashion.client.size;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.view.client.HasData;
import com.google.gwt.view.client.ListDataProvider;
import com.xfashion.client.ErrorEvent;
import com.xfashion.client.SimpleFilterDataProvider2;
import com.xfashion.client.Xfashion;
import com.xfashion.client.at.ArticleTypeDataProvider;
import com.xfashion.shared.ArticleTypeDTO;
import com.xfashion.shared.SizeDTO;

public class SizeDataProvider extends SimpleFilterDataProvider2<SizeDTO> implements SelectSizeHandler, ClearSizeSelectionHandler, DeleteSizeHandler,
		CreateSizeHandler, UpdateSizeHandler, MoveDownSizeHandler, MoveUpSizeHandler, ShowChooseSizePopupHandler {

	protected ListDataProvider<SizeDTO> leftProvider;
	protected ListDataProvider<SizeDTO> rightProvider;

	protected SplitList<SizeDTO> sizeList;

	public SizeDataProvider(ArticleTypeDataProvider articleProvider) {
		super(articleProvider);
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
		Xfashion.eventBus.addHandler(DeleteSizeEvent.TYPE, this);
		Xfashion.eventBus.addHandler(CreateSizeEvent.TYPE, this);
		Xfashion.eventBus.addHandler(UpdateSizeEvent.TYPE, this);
		Xfashion.eventBus.addHandler(MoveUpSizeEvent.TYPE, this);
		Xfashion.eventBus.addHandler(MoveDownSizeEvent.TYPE, this);
		Xfashion.eventBus.addHandler(ShowChooseSizePopupEvent.TYPE, this);
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

	@Override
	public void onCreateSize(CreateSizeEvent event) {
		sizeList.add(event.getCellData());
		saveList();
	}

	@Override
	public void onUpdateSize(UpdateSizeEvent event) {
		final SizeDTO size = event.getCellData();
		if (size == null) {
			saveList();
		} else {
			saveItem(size);
		}
	}
	
	@Override
	public void onMoveDownSize(MoveDownSizeEvent event) {
		int idx = sizeList.indexOf(event.getCellData()); // do not take index of event in case of split panels
		moveDown(idx);
	}
	
	@Override
	public void onMoveUpSize(MoveUpSizeEvent event) {
		int idx = sizeList.indexOf(event.getCellData()); // do not take index of event in case of split panels
		moveDown(idx - 1);
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
	public void onDeleteSize(DeleteSizeEvent event) {
		final SizeDTO size = event.getCellData();
		for (ArticleTypeDTO at : articleTypeProvider.getList()) {
			if (at.getSizeKey().equals(size.getKey())) {
				Xfashion.eventBus.fireEvent(new ErrorEvent(errorMessages.sizeIsNotEmpty(size.getName())));
				return;
			}
		}
		sizeList.remove(size);
		saveList();
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
				sizeList.clear();
				sizeList.addAll(result);
				fireRefreshEvent();
			}
		};
		articleTypeService.updateSizes(new ArrayList<SizeDTO>(sizeList), callback);
	}

	@Override
	public List<SizeDTO> getList() {
		return sizeList;
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


	@Override
	public void onShowChooseSizePopup(ShowChooseSizePopupEvent event) {
		ChooseSizePopup sizePopup = new ChooseSizePopup(this);
		sizePopup.show();
	}
}
