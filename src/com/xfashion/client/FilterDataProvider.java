package com.xfashion.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.view.client.ListDataProvider;
import com.xfashion.client.at.ArticleTypeDataProvider;
import com.xfashion.client.at.ArticleTypeService;
import com.xfashion.client.at.ArticleTypeServiceAsync;
import com.xfashion.client.at.event.RefreshFilterEvent;
import com.xfashion.client.resources.ErrorMessages;
import com.xfashion.shared.ArticleTypeDTO;
import com.xfashion.shared.FilterCellData;

public abstract class FilterDataProvider<T extends FilterCellData> extends ListDataProvider<T> {

	protected ArticleTypeServiceAsync articleTypeService = (ArticleTypeServiceAsync) GWT.create(ArticleTypeService.class);

	protected HashMap<String, T> idToItem;
	
	protected List<T> allItems;
	
	protected boolean filterHidden = true;
	
	protected ArticleTypeDataProvider articleTypeProvider;
	
	protected ErrorMessages errorMessages;
	
	/**
	 * retrieves the key of the filter-attribute type T of the given articleType
	 * @param articleType object to get the filter-attribute-key from
	 * @return key of the filter-attribute
	 */
	protected abstract String getAttributeContent(ArticleTypeDTO articleType);

	public abstract List<ArticleTypeDTO> applyFilter(List<ArticleTypeDTO> articleTypes);

	public FilterDataProvider(ArticleTypeDataProvider articleTypeProvider) {
		this.articleTypeProvider = articleTypeProvider;
		this.errorMessages = GWT.create(ErrorMessages.class);
		this.idToItem = new HashMap<String, T>();
		this.allItems = new ArrayList<T>();
	}
	
	@Deprecated
	public List<T> getList() {
		return super.getList();
	}

	protected List<T> getProviderList() {
		return super.getList();
	}
	
	@Deprecated
	public void setList(List<T> listToWrap) {
		super.setList(listToWrap);
	}
	
	protected void setProviderList(List<T> listToWrap) {
		if (getProviderList() == null) {
			super.setList(new ArrayList<T>());
		}
		refreshResolver(listToWrap);
		getProviderList().clear();
		if (filterHidden) {
			for (T item : listToWrap) {
				if (!item.getHidden()) {
					getProviderList().add(item);
				}
			}
		} else {
			getProviderList().addAll(listToWrap);
		}
	}
	
	public List<T> getAllItems() {
		return allItems;
	}

	public void setAllItems(List<T> allItems) {
		this.allItems.clear();
		this.allItems.addAll(allItems);
		setProviderList(this.allItems);
	}

	public T resolveData(String id) {
		return idToItem.get(id);
	}
	
	public void refreshResolver() {
		refreshResolver(getAllItems());
	}
	
	public void showHidden(boolean showHidden) {
		filterHidden = !showHidden;
		setProviderList(allItems);
	}
	
	protected void refreshResolver(List<T> list) {
		idToItem.clear();
		for (T item : list) {
			idToItem.put(item.getKey(), item);
		}
	}
	
	protected void fireRefreshEvent() {
		Xfashion.eventBus.fireEvent(new RefreshFilterEvent());
	}

}
