package com.xfashion.client.promo;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.xfashion.shared.PromoDTO;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("promoService")
public interface PromoService extends RemoteService {
	
	PromoDTO createPromo(PromoDTO dto);
	
	List<PromoDTO> readAllPromos();
	
	List<PromoDTO> readActivePromos();
	
	PromoDTO updatePromo(PromoDTO dto);
	
}
