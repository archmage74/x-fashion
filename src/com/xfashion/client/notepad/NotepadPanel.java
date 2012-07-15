package com.xfashion.client.notepad;

import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.xfashion.client.IsMinimizable;
import com.xfashion.client.PanelWidthAnimation;
import com.xfashion.client.Xfashion;
import com.xfashion.client.at.ArticleTable;
import com.xfashion.client.at.ArticleTypeManagement;
import com.xfashion.client.at.ProvidesArticleFilter;
import com.xfashion.client.notepad.event.ClearNotepadEvent;
import com.xfashion.client.notepad.event.DeliveryNoticeUpdatedEvent;
import com.xfashion.client.notepad.event.DeliveryNoticeUpdatedHandler;
import com.xfashion.client.notepad.event.NotepadStartMaximizeEvent;
import com.xfashion.client.notepad.event.NotepadStartMinimizeEvent;
import com.xfashion.client.notepad.event.OpenNotepadEvent;
import com.xfashion.client.notepad.event.OpenNotepadHandler;
import com.xfashion.client.notepad.event.PrintDeliveryNoticeEvent;
import com.xfashion.client.notepad.event.PrintNotepadStickersEvent;
import com.xfashion.client.notepad.event.RecordArticlesEvent;
import com.xfashion.client.notepad.event.RequestIntoStockEvent;
import com.xfashion.client.notepad.event.RequestOpenNotepadEvent;
import com.xfashion.client.notepad.event.RequestSaveNotepadEvent;
import com.xfashion.client.notepad.event.SaveNotepadEvent;
import com.xfashion.client.notepad.event.SaveNotepadHandler;
import com.xfashion.client.resources.ImageResources;
import com.xfashion.client.resources.TextMessages;
import com.xfashion.client.tool.Buttons;
import com.xfashion.shared.ArticleAmountDTO;
import com.xfashion.shared.BarcodeHelper;
import com.xfashion.shared.DeliveryNoticeDTO;
import com.xfashion.shared.NotepadDTO;

public class NotepadPanel implements IsMinimizable, OpenNotepadHandler, SaveNotepadHandler, DeliveryNoticeUpdatedHandler {

	public static final int PANEL_MAX_WIDTH = 550;
	public static final int PANEL_MIN_WIDTH = 25;

	private ProvidesArticleFilter provider;

	protected Panel scrollPanel;
	protected Label headerLabel;
	protected Label notepadInfoLabel1;
	protected Label notepadInfoLabel2;
	private HorizontalPanel headerPanel;

	protected boolean minimized = true;
	protected Image minmaxButton;

	protected TextMessages textMessages;
	protected ImageResources images;
	protected BarcodeHelper barcodeHelper;

	public NotepadPanel(ProvidesArticleFilter provider) {
		textMessages = GWT.create(TextMessages.class);
		images = GWT.<ImageResources> create(ImageResources.class);
		barcodeHelper = new BarcodeHelper();
		this.provider = provider;
		registerForEvents();
	}

	public Panel createPanel(ArticleAmountDataProvider articleTypeProvider) {
		return createPanel(articleTypeProvider, true);
	}

	public Panel createPanel(ArticleAmountDataProvider articleTypeProvider, boolean minimized) {
		Panel articlePanel = createArticlePanel(articleTypeProvider);
		scrollPanel = new SimplePanel();
		scrollPanel.setStyleName("filterPanel");
		if (minimized) {
			this.minimized = minimized;
			setWidth(PANEL_MIN_WIDTH);
		} else {
			this.minimized = minimized;
			setWidth(PANEL_MAX_WIDTH);
		}
		scrollPanel.add(articlePanel);
		return scrollPanel;
	}

	public void minmax() {
		if (isMinimized()) {
			PanelWidthAnimation pwa = new PanelWidthAnimation(this, PANEL_MIN_WIDTH, PANEL_MAX_WIDTH);
			pwa.run(300);
			Xfashion.eventBus.fireEvent(new NotepadStartMaximizeEvent());
		} else {
			PanelWidthAnimation pwa = new PanelWidthAnimation(this, PANEL_MAX_WIDTH, PANEL_MIN_WIDTH);
			pwa.run(300);
			Xfashion.eventBus.fireEvent(new NotepadStartMinimizeEvent());
		}
	}

	public void setWidth(int width) {
		scrollPanel.setWidth(width + "px");
	}

	@Override
	public boolean isMinimized() {
		return minimized;
	}

	@Override
	public void setMinimized(boolean minimized) {
		if (minimized) {
			if (!this.minimized) {
				minmaxButton.setResource(images.iconMaximize());
			}
		} else {
			if (this.minimized) {
				minmaxButton.setResource(images.iconMinimize());
			}
		}
		this.minimized = minimized;
	}

	@Override
	public void onOpenNotepad(OpenNotepadEvent event) {
		NotepadDTO np = event.getNotepad();
		setNotepadInfo(np);
	}

	@Override
	public void onSaveNotepad(SaveNotepadEvent event) {
		NotepadDTO np = event.getNotepad();
		setNotepadInfo(np);
	}

	@Override
	public void onDeliveryNoticeUpdated(DeliveryNoticeUpdatedEvent event) {
		setDeliveryNoticeInfo(event.getDeliveryNotice());
	}
	
	protected Panel createArticlePanel(ArticleAmountDataProvider articleAmountProvider) {
		VerticalPanel panel = new VerticalPanel();

		headerPanel = createHeaderPanel();
		panel.add(headerPanel);

		GetPriceFromArticleAmountStrategy priceStrategy = new GetPriceFromArticleAmountStrategy(articleAmountProvider,
				ArticleTypeManagement.getArticleTypePriceStrategy);
		ArticleTable<ArticleAmountDTO> att = new NotepadArticleTable(provider, priceStrategy);
		Panel atp = att.create(articleAmountProvider);
		panel.add(atp);

		return panel;
	}

	protected HorizontalPanel createHeaderPanel() {
		headerPanel = new HorizontalPanel();
		headerPanel.addStyleName("filterHeader");
		headerPanel.setWidth(PANEL_MAX_WIDTH + "px");

		headerPanel.add(createMinmaxButton());
		headerPanel.add(createHeaderLabel());
		headerPanel.add(createNotepadInfoPanel());

		HorizontalPanel toolPanel = new HorizontalPanel();
		toolPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		toolPanel.add(createClearNotepadIcon());
		toolPanel.add(createOpenNotepadIcon());
		toolPanel.add(createSaveNotepadIcon());
		toolPanel.add(createPrintDeliveryNoticeIcon());
		toolPanel.add(createPrintStickerIcon());
		toolPanel.add(createRecordArticlesIcon());
		toolPanel.add(createIntoStockIcon());
		headerPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		headerPanel.add(toolPanel);

		return headerPanel;
	}

	private Image createMinmaxButton() {
		minmaxButton = new Image();
		if (isMinimized()) {
			minmaxButton.setResource(images.iconMaximize());
		} else {
			minmaxButton.setResource(images.iconMinimize());
		}
		minmaxButton.setStyleName("buttonMinMax");
		ClickHandler minmaxClickHandler = new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				minmax();
			}
		};
		minmaxButton.addClickHandler(minmaxClickHandler);
		return minmaxButton;
	}

	private Label createHeaderLabel() {
		headerLabel = new Label(textMessages.notepadManagementHeader());
		headerLabel.addStyleName("filterLabel attributeFilterLabel");
		return headerLabel;
	}

	private Panel createNotepadInfoPanel() {
		VerticalPanel vp = new VerticalPanel();
		notepadInfoLabel1 = new Label();
		notepadInfoLabel1.setStyleName("notepadInfo");
		vp.add(notepadInfoLabel1);
		notepadInfoLabel2 = new Label();
		notepadInfoLabel2.setStyleName("notepadInfo");
		vp.add(notepadInfoLabel2);
		return vp;
	}

	private Image createClearNotepadIcon() {
		Image icon = Buttons.clearnotepad();
		icon.setTitle(textMessages.clearNotepadHint());
		icon.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				clearNotepad();
			}
		});
		return icon;
	}

	private Image createOpenNotepadIcon() {
		Image icon = Buttons.open();
		icon.setTitle(textMessages.openNotepadHint());
		icon.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Xfashion.eventBus.fireEvent(new RequestOpenNotepadEvent());
			}
		});
		return icon;
	}

	private Image createSaveNotepadIcon() {
		Image icon = Buttons.save();
		icon.setTitle(textMessages.saveNotepadHint());
		icon.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Xfashion.eventBus.fireEvent(new RequestSaveNotepadEvent());
			}
		});
		return icon;
	}

	private Image createPrintDeliveryNoticeIcon() {
		Image icon = Buttons.deliverynotice();
		icon.setTitle(textMessages.printDeliveryNoticeHint());
		icon.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Xfashion.eventBus.fireEvent(new PrintDeliveryNoticeEvent());
			}
		});
		return icon;
	}

	private Image createPrintStickerIcon() {
		Image icon = Buttons.printsticker();
		icon.setTitle(textMessages.printStickersHint());
		icon.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Xfashion.eventBus.fireEvent(new PrintNotepadStickersEvent());
			}
		});
		return icon;
	}

	private Image createRecordArticlesIcon() {
		Image icon = Buttons.scanbarcode();
		icon.setTitle(textMessages.recordArticlesHint());
		icon.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Xfashion.eventBus.fireEvent(new RecordArticlesEvent());
			}
		});
		return icon;
	}

	private Image createIntoStockIcon() {
		Image icon = Buttons.intostock();
		icon.setTitle(textMessages.intoStockHint());
		icon.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Xfashion.eventBus.fireEvent(new RequestIntoStockEvent());
			}
		});
		return icon;
	}

	private void clearNotepad() {
		Xfashion.eventBus.fireEvent(new ClearNotepadEvent());
		clearHeaderInfo();
	}

	private void setNotepadInfo(NotepadDTO np) {
		setHeaderInfo(textMessages.notepadTypeNotepad(), textMessages.notepadInfo1(np.getName()), textMessages.notepadInfo2(np.getCreationDate()));
	}

	private void setDeliveryNoticeInfo(DeliveryNoticeDTO dn) {
		String deliveryNoticeEan = barcodeHelper.generateDeliveryNoticeEan(dn.getId());
		String shopName = dn.getTargetShop().getShortName();
		Date creationDate = dn.getNotepad().getCreationDate();
		setHeaderInfo(textMessages.notepadTypeDeliveryNotice(), textMessages.deliveryNoticeInfo1(deliveryNoticeEan),
				textMessages.deliveryNoticeInfo2(shopName, creationDate));
	}

	private void clearHeaderInfo() {
		setHeaderInfo(textMessages.notepadTypeNotepad(), "", "");
	}

	private void setHeaderInfo(String type, String info1, String info2) {
		headerLabel.setText(type);
		notepadInfoLabel1.setText(info1);
		notepadInfoLabel2.setText(info2);
	}

	private void registerForEvents() {
		Xfashion.eventBus.addHandler(OpenNotepadEvent.TYPE, this);
		Xfashion.eventBus.addHandler(SaveNotepadEvent.TYPE, this);
		Xfashion.eventBus.addHandler(DeliveryNoticeUpdatedEvent.TYPE, this);
	}
}
