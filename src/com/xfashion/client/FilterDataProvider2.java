package com.xfashion.client;

import java.util.HashMap;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.view.client.ListDataProvider;
import com.xfashion.client.at.ArticleTypeDataProvider;
import com.xfashion.client.db.ArticleTypeService;
import com.xfashion.client.db.ArticleTypeServiceAsync;
import com.xfashion.client.db.RefreshFilterEvent;
import com.xfashion.client.resources.ErrorMessages;
import com.xfashion.shared.ArticleTypeDTO;
import com.xfashion.shared.FilterCellData2;

public abstract class FilterDataProvider2<T extends FilterCellData2> extends ListDataProvider<T> {

	protected ArticleTypeServiceAsync articleTypeService = (ArticleTypeServiceAsync) GWT.create(ArticleTypeService.class);

	protected HashMap<String, T> idToItem;
	
	protected ArticleTypeDataProvider articleTypeProvider;
	
	protected ErrorMessages errorMessages;
	
	protected abstract String getAttributeContent(ArticleTypeDTO articleType);

	public abstract List<ArticleTypeDTO> applyFilter(List<ArticleTypeDTO> articleTypes);
	
	public FilterDataProvider2(ArticleTypeDataProvider articleTypeProvider) {
		this.articleTypeProvider = articleTypeProvider;
		errorMessages = GWT.create(ErrorMessages.class);
		idToItem = new HashMap<String, T>();
	}
	
	protected void fireRefreshEvent() {
		Xfashion.eventBus.fireEvent(new RefreshFilterEvent());
	}

	public T resolveData(String id) {
		return idToItem.get(id);
	}
	
	public void refreshResolver() {
		refreshResolver(getList());
	}
	
	protected void refreshResolver(List<T> list) {
		idToItem.clear();
		for (T item : list) {
			idToItem.put(item.getKey(), item);
		}
	}
	
	@Override
	public void setList(List<T> listToWrap) {
		refreshResolver(listToWrap);
		super.setList(listToWrap);
	}
	
}
