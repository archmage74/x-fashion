package com.xfashion.client.at.bulk;

import com.xfashion.shared.PriceChangeDTO;

public interface PriceAttributeAccessor extends AttributeAccessor<Integer> {
	
	void setOldPrice(PriceChangeDTO priceChange, Integer value);
	
}
