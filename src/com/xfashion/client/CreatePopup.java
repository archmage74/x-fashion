package com.xfashion.client;

public abstract class CreatePopup {

	private PanelMediator panelMediator = null;

	public CreatePopup(PanelMediator panelMediator) {
		this.panelMediator = panelMediator;
	}
	
	public PanelMediator getPanelMediator() {
		return panelMediator;
	}
	
	public abstract void show();
}
