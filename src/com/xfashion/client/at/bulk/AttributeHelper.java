package com.xfashion.client.at.bulk;

import java.util.Iterator;
import java.util.List;

import com.xfashion.shared.ArticleTypeDTO;

public class AttributeHelper {

	public <T> T extractSourceAttribute(List<ArticleTypeDTO> articleTypes, AttributeAccessor<T> accessor) {
		if (articleTypes == null || articleTypes.size() == 0) {
			return null;
		}

		Iterator<ArticleTypeDTO> iterator = articleTypes.iterator();
		T key = accessor.getAttribute(iterator.next());
		if (key == null) {
			throw new RuntimeException("Found non-initialized articleType");
		}

		while (iterator.hasNext()) {
			T nextKey = accessor.getAttribute(iterator.next());
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

}
