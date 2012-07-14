package com.xfashion.client.promo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Panel;
import com.xfashion.client.Xfashion;
import com.xfashion.client.promo.event.ActivatePromoEvent;
import com.xfashion.client.promo.event.ActivatePromoHandler;
import com.xfashion.client.promo.event.CreatePromoEvent;
import com.xfashion.client.promo.event.CreatePromoHandler;
import com.xfashion.client.promo.event.DeactivatePromoEvent;
import com.xfashion.client.promo.event.DeactivatePromoHandler;
import com.xfashion.client.promo.event.OpenCreatePromoPopupEvent;
import com.xfashion.client.promo.event.OpenCreatePromoPopupHandler;
import com.xfashion.client.promo.event.PrintPromosEvent;
import com.xfashion.client.promo.event.PrintPromosHandler;
import com.xfashion.client.resources.Urls;
import com.xfashion.shared.PromoDTO;

public class PromoManagement implements ActivatePromoHandler, DeactivatePromoHandler, OpenCreatePromoPopupHandler, CreatePromoHandler,
		PrintPromosHandler {

	private PromoServiceAsync promoService = (PromoServiceAsync) GWT.create(PromoService.class);

	protected PromoPanel promoPanel;

	protected Panel panel;
	
	protected Urls urls;

	public PromoManagement() {
		promoPanel = new PromoPanel();

		urls = GWT.create(Urls.class);
		
		registerForEvents();
	}

	public Panel getPanel() {
		if (panel == null) {
			panel = promoPanel.createPanel();
			readPromos();
		}
		return panel;
	}

	@Override
	public void onActivatePromo(ActivatePromoEvent event) {
		PromoDTO promo = event.getPromo();
		promo.setActivated(true);
		savePromo(promo);
	}

	@Override
	public void onDeactivatePromo(DeactivatePromoEvent event) {
		PromoDTO promo = event.getPromo();
		promo.setActivated(false);
		savePromo(promo);
	}

	private void savePromo(PromoDTO promo) {
		AsyncCallback<PromoDTO> callback = new AsyncCallback<PromoDTO>() {
			@Override
			public void onSuccess(PromoDTO result) {
				readPromos();
			}

			@Override
			public void onFailure(Throwable caught) {
				Xfashion.fireError(caught.getMessage());
			}
		};
		promoService.updatePromo(promo, callback);
	}
	
	@Override
	public void onOpenCreatePromoPopup(OpenCreatePromoPopupEvent event) {
		CreatePromoPopup popup = new CreatePromoPopup();
		popup.show();
	}

	@Override
	public void onCreatePromo(CreatePromoEvent event) {
		AsyncCallback<PromoDTO> callback = new AsyncCallback<PromoDTO>() {
			@Override
			public void onSuccess(PromoDTO result) {
				readPromos();
			}

			@Override
			public void onFailure(Throwable caught) {
				Xfashion.fireError(caught.getMessage());
			}
		};
		promoService.createPromo(event.getPromo(), callback);
	}
	
	@Override
	public void onPrintPromos(PrintPromosEvent event) {
		String promoKey = event.getPromo().getKey();
		Integer amount = event.getAmount();
		String url = urls.printPromoStickerUrl(promoKey, amount);
		Window.open(url, "_blank", "");
	}

	private void readPromos() {
		AsyncCallback<List<PromoDTO>> callback = new AsyncCallback<List<PromoDTO>>() {
			@Override
			public void onSuccess(List<PromoDTO> result) {
				showPromos(new ArrayList<PromoDTO>(result));
			}
			@Override
			public void onFailure(Throwable caught) {
				Xfashion.fireError(caught.getMessage());
			}
		};
		promoService.readAllPromos(callback);

	}
	
	private void showPromos(List<PromoDTO> promos) {
		promos.add(new PromoDTO(-1, null, null));
		promos.add(new PromoDTO(null, -1, null));
		PromoListComparator promoListComparator = new PromoListComparator();
		Collections.sort(promos, promoListComparator);
		promoPanel.setPromos(promos);
	}

	private void registerForEvents() {
		Xfashion.eventBus.addHandler(ActivatePromoEvent.TYPE, this);
		Xfashion.eventBus.addHandler(DeactivatePromoEvent.TYPE, this);
		Xfashion.eventBus.addHandler(OpenCreatePromoPopupEvent.TYPE, this);
		Xfashion.eventBus.addHandler(CreatePromoEvent.TYPE, this);
		Xfashion.eventBus.addHandler(PrintPromosEvent.TYPE, this);
	}

}
