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
import com.xfashion.client.at.brand.event.SelectBrandEvent;
import com.xfashion.client.at.brand.event.SelectBrandHandler;
import com.xfashion.shared.ArticleTypeDTO;
import com.xfashion.shared.BrandDTO;

public class BrandDataProvider extends SimpleFilterDataProvider<BrandDTO> implements SelectBrandHandler, ClearBrandSelectionHandler {

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
		toggleSelect(dto.getKey());
	}

	@Override
	public void onClearBrandSelection(ClearBrandSelectionEvent event) {
		getFilter().clear();
		fireRefreshEvent();
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
	public void saveList() {
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
	}

}
