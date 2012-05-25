package com.xfashion.client.util;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.xfashion.server.util.IdCounterType;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("idCounter")
public interface IdCounterService extends RemoteService {
	
	Long getNewId(IdCounterType type);

}
