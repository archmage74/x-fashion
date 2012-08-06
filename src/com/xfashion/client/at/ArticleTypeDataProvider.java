package com.xfashion.client.at;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.xfashion.client.Xfashion;
import com.xfashion.client.at.bulk.UpdateArticleTypesEvent;
import com.xfashion.client.at.bulk.UpdateArticleTypesHandler;
import com.xfashion.client.at.event.ArticlesLoadedEvent;
import com.xfashion.client.at.event.CreateArticleTypeEvent;
import com.xfashion.client.at.event.CreateArticleTypeHandler;
import com.xfashion.client.at.event.DeleteArticleTypeEvent;
import com.xfashion.client.at.event.DeleteArticleTypeHandler;
import com.xfashion.client.at.event.RefreshFilterEvent;
import com.xfashion.client.at.sort.IArticleTypeComparator;
import com.xfashion.shared.ArticleTypeDTO;
import com.xfashion.shared.PriceChangeDTO;

public class ArticleTypeDataProvider extends ArticleDataProvider<ArticleTypeDTO> implements CreateArticleTypeHandler, UpdateArticleTypesHandler,
		DeleteArticleTypeHandler {

	protected ArticleTypeServiceAsync articleTypeService = (ArticleTypeServiceAsync) GWT.create(ArticleTypeService.class);

	protected HashMap<String, ArticleTypeDTO> idToItem;

	protected HashMap<Long, ArticleTypeDTO> productNumberToItem;

	protected IArticleTypeComparator sortStrategy;

	public ArticleTypeDataProvider() {
		idToItem = new HashMap<String, ArticleTypeDTO>();
		productNumberToItem = new HashMap<Long, ArticleTypeDTO>();
		
		registerForEvents();
	}

	public IArticleTypeComparator getSortStrategy() {
		return sortStrategy;
	}

	public void setSortStrategy(IArticleTypeComparator sortStrategy) {
		this.sortStrategy = sortStrategy;
	}

	public Collection<ArticleTypeDTO> getAllArticleTypes() {
		return idToItem.values();
	}

	@Override
	public ArticleTypeDTO retrieveArticleType(ArticleTypeDTO item) {
		return item;
	}

	@Override
	public ArticleTypeDTO retrieveArticleType(Long productNumber) {
		return productNumberToItem.get(productNumber);
	}

	@Override
	public void onCreateArticleType(final CreateArticleTypeEvent event) {
		createArticleType(event.getArticleType());
	}
	
	@Override
	public void onUpdateArticleTypes(UpdateArticleTypesEvent event) {
		for (ArticleTypeDTO at : event.getArticleTypes()) {
			updateArticleType(at);
		}
		createPriceChanges(event.getPriceChanges());
	}

	@Override
	public void onDeleteArticleType(DeleteArticleTypeEvent event) {
		deleteArticleType(event.getArticleType());
	}
	
	public ArticleTypeDTO resolveData(String key) {
		return idToItem.get(key);
	}

	public void readArticleTypes() {
		setIsLoading(true);
		AsyncCallback<Set<ArticleTypeDTO>> callback = new AsyncCallback<Set<ArticleTypeDTO>>() {
			@Override
			public void onFailure(Throwable caught) {
				Xfashion.fireError(caught.getMessage());
			}

			@Override
			public void onSuccess(Set<ArticleTypeDTO> result) {
				storeArticles(result);
				Xfashion.eventBus.fireEvent(new ArticlesLoadedEvent());
				Xfashion.eventBus.fireEvent(new RefreshFilterEvent());
			}
		};
		articleTypeService.readArticleTypes(callback);
	}

	public void applyFilters(IFilterArticle filter) {
		List<ArticleTypeDTO> articles = new ArrayList<ArticleTypeDTO>(getAllArticleTypes());
		filter.applyFilters(articles);
		setList(articles);
		sortArticles();
	}
	
	protected void createArticleType(final ArticleTypeDTO articleType) {
		AsyncCallback<ArticleTypeDTO> callback = new AsyncCallback<ArticleTypeDTO>() {
			@Override
			public void onFailure(Throwable caught) {
				Xfashion.fireError(caught.getMessage());
			}

			@Override
			public void onSuccess(ArticleTypeDTO result) {
				addArticleType(result);
				Xfashion.eventBus.fireEvent(new RefreshFilterEvent());
			}
		};
		articleTypeService.createArticleType(articleType, callback);
	}

	protected void addArticleType(ArticleTypeDTO articleType) {
		addFastArticleTypeWithoutSort(articleType);
		sortArticles();
		Xfashion.eventBus.fireEvent(new RefreshFilterEvent());
	}

	protected void createPriceChanges(Collection<PriceChangeDTO> priceChanges) {
		AsyncCallback<Void> callback = new AsyncCallback<Void>() {
			@Override
			public void onFailure(Throwable caught) {
				Xfashion.fireError(caught.getMessage());
			}
			@Override
			public void onSuccess(Void result) {
				
			}
		};
		articleTypeService.createPriceChanges(priceChanges, callback);
	}
		
	protected void storeArticles(Set<ArticleTypeDTO> result) {
		getList().clear();
		idToItem.clear();
		productNumberToItem.clear();
		for (ArticleTypeDTO a : result) {
			addFastArticleTypeWithoutSort(a);
		}
		sortArticles();
		setIsLoading(false);
	}

	protected void updateArticleType(final ArticleTypeDTO articleType) {
		AsyncCallback<Void> callback = new AsyncCallback<Void>() {
			@Override
			public void onFailure(Throwable caught) {
				Xfashion.fireError(caught.getMessage());
			}
			@Override
			public void onSuccess(Void result) {
				sortArticles();
				Xfashion.eventBus.fireEvent(new RefreshFilterEvent());
			}
		};
		articleTypeService.updateArticleType(articleType, callback);
	}

	protected void deleteArticleType(final ArticleTypeDTO articleType) {
		AsyncCallback<Void> callback = new AsyncCallback<Void>() {
			@Override
			public void onFailure(Throwable caught) {
				Xfashion.fireError(caught.getMessage());
			}

			@Override
			public void onSuccess(Void result) {
				removeArticleType(articleType);
			}
		};
		articleTypeService.deleteArticleType(articleType, callback);
	}

	private void addFastArticleTypeWithoutSort(ArticleTypeDTO articleType) {
		idToItem.put(articleType.getKey(), articleType);
		productNumberToItem.put(articleType.getProductNumber(), articleType);
		getList().add(articleType);
	}

	private void removeArticleType(ArticleTypeDTO articleType) {
		idToItem.remove(articleType.getKey());
		productNumberToItem.remove(articleType).getProductNumber();
		for (ArticleTypeDTO a : getList()) {
			if (a.getKey().equals(articleType.getKey())) {
				getList().remove(a);
				break;
			}
		}
		Xfashion.eventBus.fireEvent(new RefreshFilterEvent());
	}
	
	private void sortArticles() {
		if (sortStrategy != null) {
			Collections.sort(getList(), sortStrategy);
		}
	}

	private void registerForEvents() {
		Xfashion.eventBus.addHandler(CreateArticleTypeEvent.TYPE, this);
		Xfashion.eventBus.addHandler(UpdateArticleTypesEvent.TYPE, this);
		Xfashion.eventBus.addHandler(DeleteArticleTypeEvent.TYPE, this);
	}

}
