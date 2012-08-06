package com.xfashion.client.at;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.view.client.HasData;
import com.google.gwt.view.client.ListDataProvider;
import com.xfashion.shared.ArticleTypeDTO;

public abstract class ArticleDataProvider<T> extends ListDataProvider<T> {

	protected CellTable<T> cellTable;
	
	public abstract ArticleTypeDTO retrieveArticleType(T item);

	public abstract ArticleTypeDTO retrieveArticleType(Long productNumber);

	public CellTable<T> getCellTable() {
		return cellTable;
	}

	public void setCellTable(CellTable<T> cellTable) {
		setIsLoading(true);
		this.cellTable = cellTable;
		setIsLoading(false);
	}
	
	public void setIsLoading(boolean isLoading) {
		if (isLoading) {
			if (getDataDisplays().size() == 1) {
				super.removeDataDisplay(cellTable);
				cellTable.setVisibleRangeAndClearData(cellTable.getVisibleRange(), true);
			}
		} else {
			if (getDataDisplays().size() == 0 && cellTable != null) {
				super.addDataDisplay(cellTable);
			}
		}
	}
	
	public Collection<String> getVisibleArticleNames() {
		Set<String> names = new HashSet<String>();
		for (T a : getList()) {
			ArticleTypeDTO articleType = retrieveArticleType(a);
			names.add(articleType.getName());
		}
		return names;
	}

	@Override 
	public void addDataDisplay(HasData<T> display) {
		throw new UnsupportedOperationException("addDataDisplay is not supported by ArticleDataProviders, use setCellTable() instead");
	}
}
