package com.xfashion.client.promo;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.xfashion.shared.PromoDTO;

public interface PromoServiceAsync {

	void createPromo(PromoDTO dto, AsyncCallback<PromoDTO> callback);

	void readAllPromos(AsyncCallback<List<PromoDTO>> callback);

	void readActivePromos(AsyncCallback<List<PromoDTO>> callback);

	void updatePromo(PromoDTO dto, AsyncCallback<PromoDTO> callback);
	
}
