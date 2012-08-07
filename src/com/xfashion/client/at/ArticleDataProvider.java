package com.xfashion.client.at;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import com.google.gwt.view.client.HasData;
import com.google.gwt.view.client.ListDataProvider;
import com.xfashion.shared.ArticleTypeDTO;

public abstract class ArticleDataProvider<T> extends ListDataProvider<T> {

	protected Set<HasData<T>> cellTables = new HashSet<HasData<T>>();
	
	protected boolean isLoading = false;
	
	public abstract ArticleTypeDTO retrieveArticleType(T item);

	public abstract ArticleTypeDTO retrieveArticleType(Long productNumber);

	public void setIsLoading(boolean isLoading) {
		if (this.isLoading == isLoading) {
			return;
		}
		if (isLoading) {
			for (HasData<T> hd : cellTables) {
				super.removeDataDisplay(hd);
			}
		} else {
			for (HasData<T> hd : cellTables) {
				super.addDataDisplay(hd);
			}
		}
		this.isLoading = isLoading;
	}
	
	@Override 
	public void addDataDisplay(HasData<T> display) {
		cellTables.add(display);
		if (!isLoading) {
			super.addDataDisplay(display);
		}
	}
	
	@Override
	public Set<HasData<T>> getDataDisplays() {
		return new HashSet<HasData<T>>(cellTables);
	}
	
	@Override 
	public void removeDataDisplay(HasData<T> display) {
		cellTables.remove(display);
	}
	
	public Collection<String> getVisibleArticleNames() {
		Set<String> names = new HashSet<String>();
		for (T a : getList()) {
			ArticleTypeDTO articleType = retrieveArticleType(a);
			names.add(articleType.getName());
		}
		return names;
	}

}
