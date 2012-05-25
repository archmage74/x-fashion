package com.xfashion.client.util;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.xfashion.server.util.IdCounterType;

public interface IdCounterServiceAsync {

	void getNewId(IdCounterType type, AsyncCallback<Long> callback);
	
}
