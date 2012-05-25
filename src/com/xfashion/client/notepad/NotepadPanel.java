package com.xfashion.client.notepad;

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
import com.xfashion.client.PanelMediator;
import com.xfashion.client.PanelWidthAnimation;
import com.xfashion.client.Xfashion;
import com.xfashion.client.at.ArticleTable;
import com.xfashion.client.at.ProvidesArticleFilter;
import com.xfashion.client.notepad.event.ClearNotepadEvent;
import com.xfashion.client.notepad.event.NotepadStartMaximizeEvent;
import com.xfashion.client.notepad.event.NotepadStartMinimizeEvent;
import com.xfashion.client.notepad.event.PrintDeliveryNoticeEvent;
import com.xfashion.client.notepad.event.PrintNotepadStickersEvent;
import com.xfashion.client.notepad.event.RecordArticlesEvent;
import com.xfashion.client.notepad.event.RequestOpenNotepadEvent;
import com.xfashion.client.notepad.event.RequestSaveNotepadEvent;
import com.xfashion.client.resources.ImageResources;
import com.xfashion.client.resources.TextMessages;
import com.xfashion.client.tool.Buttons;
import com.xfashion.shared.notepad.ArticleAmountDTO;

public class NotepadPanel implements IsMinimizable {

	public static final int PANEL_MAX_WIDTH = 550; 
	public static final int PANEL_MIN_WIDTH = 25;
	
	private PanelMediator panelMediator;
	
	private ProvidesArticleFilter provider;
	
	protected Panel scrollPanel;
	
	private HorizontalPanel headerPanel;
	
	protected boolean minimized = true;
	protected Image minmaxButton;

	protected TextMessages textMessages;
	protected ImageResources images;
	
	public NotepadPanel(PanelMediator panelMediator) {
		textMessages = GWT.create(TextMessages.class);
		images = GWT.<ImageResources>create(ImageResources.class);
		this.panelMediator = panelMediator;
		this.provider = panelMediator.getArticleTypeDatabase();
	}
	
	public Panel createPanel(ArticleAmountDataProvider articleTypeProvider) {
		Panel articlePanel = createArticlePanel(articleTypeProvider);
		scrollPanel = new SimplePanel();
		scrollPanel.setStyleName("filterPanel");
		setWidth(PANEL_MIN_WIDTH);
		scrollPanel.add(articlePanel);
		return scrollPanel;
	}
	
	protected Panel createArticlePanel(ArticleAmountDataProvider articleAmountProvider) {

		VerticalPanel panel = new VerticalPanel();

		headerPanel = createHeaderPanel();
		panel.add(headerPanel);
		
		
		ArticleTable<ArticleAmountDTO> att = new CountingArticleTable(provider);
		Panel atp = att.create(articleAmountProvider, panelMediator);
		panel.add(atp);
		
		return panel;
	}
	
	protected HorizontalPanel createHeaderPanel() {
		headerPanel = new HorizontalPanel();
		headerPanel.addStyleName("filterHeader");
		headerPanel.setWidth(PANEL_MAX_WIDTH + "px");
		

		headerPanel.add(createMinmaxButton());
		headerPanel.add(createHeaderLabel());
		
		HorizontalPanel toolPanel = new HorizontalPanel();
		toolPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		toolPanel.add(createClearNotepadIcon());
		toolPanel.add(createOpenNotepadIcon());
		toolPanel.add(createSaveNotepadIcon());
		toolPanel.add(createPrintDeliveryNoticeIcon());
		toolPanel.add(createPrintStickerIcon());
		toolPanel.add(createRecordArticlesIcon());
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
		Label headerLabel = new Label(textMessages.notepadManagementHeader());
		headerLabel.addStyleName("filterLabel attributeFilterLabel");
		return headerLabel;
	}
	
	private Image createClearNotepadIcon() {
		Image icon = Buttons.clearnotepad();
		icon.setTitle(textMessages.clearNotepadHint());
		icon.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Xfashion.eventBus.fireEvent(new ClearNotepadEvent());
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
	
}
