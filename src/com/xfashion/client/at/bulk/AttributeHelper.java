package com.xfashion.client.at.bulk;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.xfashion.shared.ArticleTypeDTO;
import com.xfashion.shared.PriceChangeDTO;

public class AttributeHelper {

	public <T> T extractSourceAttribute(List<ArticleTypeDTO> articleTypes, AttributeAccessor<T> accessor) {
		if (articleTypes == null || articleTypes.size() == 0) {
			return null;
		}

		Iterator<ArticleTypeDTO> iterator = articleTypes.iterator();
		T key = accessor.getAttribute(iterator.next());

		while (iterator.hasNext()) {
			T nextKey = accessor.getAttribute(iterator.next());
			if (key == null && nextKey != null) {
				return null;
			}
			if (key != null && nextKey == null) {
				return null;
			}
			if (key == null && nextKey == null) {
				break;
			}
			if (!key.equals(nextKey)) {
				return null;
			}
		}
		return key;
	}

	public <T> void saveAttribute(List<ArticleTypeDTO> articleTypes, AttributeAccessor<T> accessor, T value) {
		if (value != null) {
			for (ArticleTypeDTO at : articleTypes) {
				accessor.setAttribute(at, value);
			}
		}
	}

	public <T> void saveAttribute(List<ArticleTypeDTO> articleTypes, Map<String, PriceChangeDTO> priceChanges, PriceAttributeAccessor accessor,
			Integer value) {
		if (value != null) {
			for (ArticleTypeDTO at : articleTypes) {
				Integer oldValue = accessor.getAttribute(at);
				if (oldValue != null && !oldValue.equals(value)) {
					PriceChangeDTO priceChange = priceChanges.get(at.getKey());
					if (priceChange == null) {
						priceChange = new PriceChangeDTO();
						priceChange.setArticleTypeKey(at.getKey());
						priceChanges.put(at.getKey(), priceChange);
					}
					accessor.setOldPrice(priceChange, oldValue);
				}
				accessor.setAttribute(at, value);
			}
		}
	}

}
