package com.xfashion.client.color;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.xfashion.client.ErrorEvent;
import com.xfashion.client.SimpleFilterDataProvider2;
import com.xfashion.client.Xfashion;
import com.xfashion.client.at.ArticleTypeDataProvider;
import com.xfashion.shared.ArticleTypeDTO;
import com.xfashion.shared.ColorDTO;

public class ColorDataProvider extends SimpleFilterDataProvider2<ColorDTO> implements CreateColorHandler, UpdateColorHandler, DeleteColorHandler,
		MoveUpColorHandler, MoveDownColorHandler, SelectColorHandler, ClearColorSelectionHandler, ShowChooseColorPopupHandler {

	public ColorDataProvider(ArticleTypeDataProvider articleProvider) {
		super(articleProvider);
		registerForEvents();
	}

	public String getAttributeContent(ArticleTypeDTO articleType) {
		return articleType.getColorKey();
	}

	private void registerForEvents() {
		Xfashion.eventBus.addHandler(SelectColorEvent.TYPE, this);
		Xfashion.eventBus.addHandler(ClearColorSelectionEvent.TYPE, this);
		Xfashion.eventBus.addHandler(DeleteColorEvent.TYPE, this);
		Xfashion.eventBus.addHandler(CreateColorEvent.TYPE, this);
		Xfashion.eventBus.addHandler(UpdateColorEvent.TYPE, this);
		Xfashion.eventBus.addHandler(MoveUpColorEvent.TYPE, this);
		Xfashion.eventBus.addHandler(MoveDownColorEvent.TYPE, this);
		Xfashion.eventBus.addHandler(ShowChooseColorPopupEvent.TYPE, this);
	}

	@Override
	public void onSelectColor(SelectColorEvent event) {
		ColorDTO dto = event.getCellData();
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
	public void onClearColorSelection(ClearColorSelectionEvent event) {
		getFilter().clear();
		fireRefreshEvent();
	}

	@Override
	public void onDeleteColor(DeleteColorEvent event) {
		final ColorDTO item = event.getCellData();
		for (ArticleTypeDTO at : articleTypeProvider.getList()) {
			if (at.getColorKey().equals(item.getKey())) {
				Xfashion.eventBus.fireEvent(new ErrorEvent(errorMessages.colorIsNotEmpty(item.getName())));
				return;
			}
		}
		getList().remove(item);
		saveList();
	}

	@Override
	public void onUpdateColor(UpdateColorEvent event) {
		final ColorDTO item = event.getCellData();
		if (item == null) {
			saveList();
		} else {
			saveItem(item);
		}
	}

	@Override
	public void onCreateColor(CreateColorEvent event) {
		getList().add(event.getCellData());
		saveList();

	}

	@Override
	public void onMoveDownColor(MoveDownColorEvent event) {
		moveDown(event.getIndex());
	}
	
	@Override
	public void onMoveUpColor(MoveUpColorEvent event) {
		moveDown(event.getIndex() - 1);
	}
	
	public void moveDown(int idx) {
		if (idx < 0) {
			return;
		}
		if (idx + 1 >= getList().size()) {
			return;
		}
		ColorDTO item = getList().remove(idx);
		getList().add(idx + 1, item);
		saveList();
	}

	@Override
	protected void saveItem(ColorDTO dto) {
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
		articleTypeService.updateColor(dto, callback);
	}

	@Override
	protected void saveList() {
		AsyncCallback<List<ColorDTO>> callback = new AsyncCallback<List<ColorDTO>>() {
			@Override
			public void onFailure(Throwable caught) {
				Xfashion.fireError("Could not save colors: " + caught.getMessage());
			}

			@Override
			public void onSuccess(List<ColorDTO> result) {
				getList().clear();
				getList().addAll(result);
				fireRefreshEvent();
			}
		};
		articleTypeService.updateColors(new ArrayList<ColorDTO>(getList()), callback);
	}


	@Override
	public void onShowChooseColorPopup(ShowChooseColorPopupEvent event) {
		ChooseColorPopup colorPopup = new ChooseColorPopup(this);
		colorPopup.show();
	}
}
