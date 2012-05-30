package com.xfashion.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.SimpleEventBus;
import com.xfashion.client.menu.MenuPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Xfashion implements EntryPoint {

	public static EventBus eventBus = new SimpleEventBus();
	
	public static void fireError(String errorMessage) {
		eventBus.fireEvent(new ErrorEvent(errorMessage));
	}
	
	public void onModuleLoad() {
		
		MainPanel mainPanel = new MainPanel();

		MenuPanel menuPanel = new MenuPanel(mainPanel);
		menuPanel.addMenuPanel();
		
		mainPanel.showUserProfilePanel();
	}

}
