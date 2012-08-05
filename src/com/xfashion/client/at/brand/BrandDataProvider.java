package com.xfashion.client.at.brand;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.web.bindery.event.shared.EventBus;
import com.xfashion.client.SimpleFilterDataProvider;
import com.xfashion.client.Xfashion;
import com.xfashion.client.at.ArticleTypeDataProvider;
import com.xfashion.client.at.brand.event.BrandsLoadedEvent;
import com.xfashion.client.at.brand.event.ClearBrandSelectionEvent;
import com.xfashion.client.at.brand.event.ClearBrandSelectionHandler;
import com.xfashion.client.at.brand.event.CreateBrandEvent;
import com.xfashion.client.at.brand.event.CreateBrandHandler;
import com.xfashion.client.at.brand.event.DeleteBrandEvent;
import com.xfashion.client.at.brand.event.DeleteBrandHandler;
import com.xfashion.client.at.brand.event.MoveDownBrandEvent;
import com.xfashion.client.at.brand.event.MoveDownBrandHandler;
import com.xfashion.client.at.brand.event.MoveUpBrandEvent;
import com.xfashion.client.at.brand.event.MoveUpBrandHandler;
import com.xfashion.client.at.brand.event.SelectBrandEvent;
import com.xfashion.client.at.brand.event.SelectBrandHandler;
import com.xfashion.client.at.brand.event.ShowChooseBrandPopupEvent;
import com.xfashion.client.at.brand.event.ShowChooseBrandPopupHandler;
import com.xfashion.client.at.brand.event.UpdateBrandEvent;
import com.xfashion.client.at.brand.event.UpdateBrandHandler;
import com.xfashion.shared.ArticleTypeDTO;
import com.xfashion.shared.BrandDTO;

public class BrandDataProvider extends SimpleFilterDataProvider<BrandDTO> implements CreateBrandHandler, UpdateBrandHandler, DeleteBrandHandler,
		MoveUpBrandHandler, MoveDownBrandHandler, SelectBrandHandler, ClearBrandSelectionHandler, ShowChooseBrandPopupHandler {

	public BrandDataProvider(ArticleTypeDataProvider articleTypeProvider, EventBus eventBus) {
		super(articleTypeProvider, eventBus);
		registerForEvents();
	}

	public String getAttributeContent(ArticleTypeDTO articleType) {
		return articleType.getBrandKey();
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
				Xfashion.fireError(errorMessages.brandIsNotEmpty(item.getName()));
				return;
			}
		}
		item.setHidden(!item.getHidden());
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
		getAllItems().add(event.getCellData());
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
				storeBrands(result);
			}
		};
		articleTypeService.readBrands(callback);
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
				storeBrands(result);
			}
		};
		articleTypeService.updateBrands(new ArrayList<BrandDTO>(getAllItems()), callback);
	}

	private void storeBrands(List<BrandDTO> result) {
		setAllItems(result);
		fireRefreshEvent();
		eventBus.fireEvent(new BrandsLoadedEvent());
	}

	private void registerForEvents() {
		eventBus.addHandler(SelectBrandEvent.TYPE, this);
		eventBus.addHandler(ClearBrandSelectionEvent.TYPE, this);
		
		Xfashion.eventBus.addHandler(DeleteBrandEvent.TYPE, this);
		Xfashion.eventBus.addHandler(CreateBrandEvent.TYPE, this);
		Xfashion.eventBus.addHandler(UpdateBrandEvent.TYPE, this);
		Xfashion.eventBus.addHandler(MoveUpBrandEvent.TYPE, this);
		Xfashion.eventBus.addHandler(MoveDownBrandEvent.TYPE, this);
		Xfashion.eventBus.addHandler(ShowChooseBrandPopupEvent.TYPE, this);
	}

}
