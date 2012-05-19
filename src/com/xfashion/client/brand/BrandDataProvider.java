package com.xfashion.client.brand;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.xfashion.client.ErrorEvent;
import com.xfashion.client.SimpleFilterDataProvider2;
import com.xfashion.client.Xfashion;
import com.xfashion.client.at.ArticleTypeDataProvider;
import com.xfashion.shared.ArticleTypeDTO;
import com.xfashion.shared.BrandDTO;

public class BrandDataProvider extends SimpleFilterDataProvider2<BrandDTO> implements CreateBrandHandler, UpdateBrandHandler, DeleteBrandHandler,
MoveUpBrandHandler, MoveDownBrandHandler, SelectBrandHandler, ClearBrandSelectionHandler, ShowChooseBrandPopupHandler {
	
	public BrandDataProvider(ArticleTypeDataProvider articleProvider) {
		super(articleProvider);
		registerForEvents();
	}

	public String getAttributeContent(ArticleTypeDTO articleType) {
		return articleType.getBrandKey();
	}

	private void registerForEvents() {
		Xfashion.eventBus.addHandler(SelectBrandEvent.TYPE, this);
		Xfashion.eventBus.addHandler(ClearBrandSelectionEvent.TYPE, this);
		Xfashion.eventBus.addHandler(DeleteBrandEvent.TYPE, this);
		Xfashion.eventBus.addHandler(CreateBrandEvent.TYPE, this);
		Xfashion.eventBus.addHandler(UpdateBrandEvent.TYPE, this);
		Xfashion.eventBus.addHandler(MoveUpBrandEvent.TYPE, this);
		Xfashion.eventBus.addHandler(MoveDownBrandEvent.TYPE, this);
		Xfashion.eventBus.addHandler(ShowChooseBrandPopupEvent.TYPE, this);
	}

	@Override
	public void onSelectBrand(SelectBrandEvent event) {
		BrandDTO dto = event.getCellData();
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
	public void onClearBrandSelection(ClearBrandSelectionEvent event) {
		getFilter().clear();
		fireRefreshEvent();
	}

	@Override
	public void onDeleteBrand(DeleteBrandEvent event) {
		final BrandDTO item = event.getCellData();
		for (ArticleTypeDTO at : articleTypeProvider.getList()) {
			if (at.getBrandKey().equals(item.getKey())) {
				Xfashion.eventBus.fireEvent(new ErrorEvent(errorMessages.brandIsNotEmpty(item.getName())));
				return;
			}
		}
		getList().remove(item);
		saveList();
	}

	@Override
	public void onUpdateBrand(UpdateBrandEvent event) {
		final BrandDTO item = event.getCellData();
		if (item == null) {
			saveList();
		} else {
			saveItem(item);
		}
	}

	@Override
	public void onCreateBrand(CreateBrandEvent event) {
		getList().add(event.getCellData());
		saveList();
	}

	@Override
	public void onMoveDownBrand(MoveDownBrandEvent event) {
		moveDown(event.getIndex());
	}
	
	@Override
	public void onMoveUpBrand(MoveUpBrandEvent event) {
		moveDown(event.getIndex() - 1);
	}
	
	@Override
	protected void saveItem(BrandDTO dto) {
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
		articleTypeService.updateBrand(dto, callback);
	}

	@Override
	protected void saveList() {
		AsyncCallback<List<BrandDTO>> callback = new AsyncCallback<List<BrandDTO>>() {
			@Override
			public void onFailure(Throwable caught) {
				Xfashion.fireError("Could not save colors: " + caught.getMessage());
			}
			@Override
			public void onSuccess(List<BrandDTO> result) {
				getList().clear();
				getList().addAll(result);
				fireRefreshEvent();
			}
		};
		articleTypeService.updateBrands(new ArrayList<BrandDTO>(getList()), callback);
	}

	@Override
	public void onShowChooseBrandPopup(ShowChooseBrandPopupEvent event) {
		ChooseBrandPopup brandPopup = new ChooseBrandPopup(this);
		brandPopup.show();
	}
	
	public void readBrands() {
		AsyncCallback<List<BrandDTO>> callback = new AsyncCallback<List<BrandDTO>>() {
			@Override
			public void onFailure(Throwable caught) {
				Xfashion.fireError(caught.getMessage());
			}

			@Override
			public void onSuccess(List<BrandDTO> result) {
				List<BrandDTO> list = getList();
				list.clear();
				list.addAll(result);
				refreshResolver();
				fireRefreshEvent();
			}
		};
		articleTypeService.readBrands(callback);
	}
}
