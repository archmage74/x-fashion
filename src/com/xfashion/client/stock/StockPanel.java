package com.xfashion.client.stock;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.xfashion.client.Xfashion;
import com.xfashion.client.at.ArticleTable;
import com.xfashion.client.at.ArticleTypeManagement;
import com.xfashion.client.brand.BrandPanel;
import com.xfashion.client.cat.CategoryPanel;
import com.xfashion.client.color.ColorPanel;
import com.xfashion.client.db.ArticleTypeDatabase;
import com.xfashion.client.name.NamePanel;
import com.xfashion.client.notepad.ArticleAmountDataProvider;
import com.xfashion.client.notepad.GetPriceFromArticleAmountStrategy;
import com.xfashion.client.notepad.NotepadPanel;
import com.xfashion.client.notepad.event.NotepadStartMaximizeEvent;
import com.xfashion.client.notepad.event.NotepadStartMaximizeHandler;
import com.xfashion.client.notepad.event.NotepadStartMinimizeEvent;
import com.xfashion.client.notepad.event.NotepadStartMinimizeHandler;
import com.xfashion.client.resources.ImageResources;
import com.xfashion.client.resources.TextMessages;
import com.xfashion.client.size.SizePanel;
import com.xfashion.client.stock.event.RequestOpenSellPopupEvent;
import com.xfashion.client.style.StylePanel;
import com.xfashion.client.tool.Buttons;
import com.xfashion.shared.ArticleAmountDTO;

public class StockPanel implements NotepadStartMinimizeHandler, NotepadStartMaximizeHandler {

	public static final int PANEL_MAX_WIDTH = 550;
	public static final int PANEL_MIN_WIDTH = 25;

	private ArticleTypeDatabase articleTypeDatabase;

	protected HorizontalPanel headerPanel;
	protected HorizontalPanel panel;
	protected ScrollPanel scrollPanel;

	protected CategoryPanel categoryPanel;
	protected BrandPanel brandPanel;
	protected StylePanel stylePanel;
	protected SizePanel sizePanel;
	protected ColorPanel colorPanel;
	protected NamePanel namePanel;

	protected boolean minimized = false;

	protected TextMessages textMessages;
	protected ImageResources images;

	public StockPanel(ArticleTypeDatabase articleTypeDatabase) {
		this.textMessages = GWT.create(TextMessages.class);
		this.images = GWT.<ImageResources> create(ImageResources.class);
		this.articleTypeDatabase = articleTypeDatabase;

		registerForEvents();
	}

	public Panel createPanel(ArticleAmountDataProvider stockProvider, ArticleAmountDataProvider notepadArticleProvider) {
		if (panel == null) {
			panel = new HorizontalPanel();
			panel.add(createCategoryPanel());
			panel.add(createBrandPanel());
			panel.add(createStylePanel());
			panel.add(createNamePanel());
			panel.add(createColorPanel());
			panel.add(createSizePanel());
			panel.add(createArticlePanel(stockProvider));
			NotepadPanel notepadPanel = new NotepadPanel(articleTypeDatabase);
			panel.add(notepadPanel.createPanel(notepadArticleProvider, true));
		}
		return panel;
	}

	public void setWidth(int width) {
		scrollPanel.setWidth(width + "px");
	}

	@Override
	public void onNotepadStartMaximize(NotepadStartMaximizeEvent event) {
		if (panel != null) {
			brandPanel.minimize();
			stylePanel.minimize();
			sizePanel.minimize();
			colorPanel.minimize();
			namePanel.minimize();
		}
	}

	@Override
	public void onNotepadStartMinimize(NotepadStartMinimizeEvent event) {
		if (panel != null) {
			brandPanel.maximize();
			stylePanel.maximize();
			sizePanel.maximize();
			colorPanel.maximize();
			namePanel.maximize();
		}
	}

	private Widget createCategoryPanel() {
		categoryPanel = new CategoryPanel(articleTypeDatabase.getCategoryProvider());
		return categoryPanel.createPanel();
	}

	private Widget createBrandPanel() {
		brandPanel = new BrandPanel(articleTypeDatabase.getBrandProvider());
		return brandPanel.createPanel();
	}

	private Widget createStylePanel() {
		stylePanel = new StylePanel(articleTypeDatabase.getCategoryProvider().getStyleProvider());
		return stylePanel.createPanel();
	}

	private Widget createColorPanel() {
		colorPanel = new ColorPanel(articleTypeDatabase.getColorProvider());
		return colorPanel.createPanel();
	}

	private Widget createNamePanel() {
		namePanel = new NamePanel(articleTypeDatabase.getNameProvider());
		return namePanel.createPanel();
	}

	private Widget createSizePanel() {
		sizePanel = new SizePanel(articleTypeDatabase.getSizeProvider());
		return sizePanel.createPanel(new String[] { "sizePanel" });
	}

	protected Panel createArticlePanel(ArticleAmountDataProvider articleAmountProvider) {

		VerticalPanel articlePanel = new VerticalPanel();

		headerPanel = createHeaderPanel();
		articlePanel.add(headerPanel);

		GetPriceFromArticleAmountStrategy priceStrategy = new GetPriceFromArticleAmountStrategy(articleAmountProvider,
				ArticleTypeManagement.getArticleTypePriceStrategy);
		ArticleTable<ArticleAmountDTO> att = new StockArticleTable(articleTypeDatabase, priceStrategy);
		Panel atp = att.create(articleAmountProvider);
		articlePanel.add(atp);

		scrollPanel = new ScrollPanel();
		scrollPanel.setStyleName("filterPanel");
		setWidth(PANEL_MAX_WIDTH);
		scrollPanel.add(articlePanel);

		return articlePanel;
	}

	protected HorizontalPanel createHeaderPanel() {
		headerPanel = new HorizontalPanel();
		headerPanel.addStyleName("filterHeader");
		headerPanel.setWidth(PANEL_MAX_WIDTH + "px");

		headerPanel.add(createHeaderLabel());

		HorizontalPanel toolPanel = new HorizontalPanel();
		toolPanel.add(createSellIcon());
		toolPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		headerPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		headerPanel.add(toolPanel);

		return headerPanel;
	}

	private Label createHeaderLabel() {
		Label headerLabel = new Label(textMessages.stockManagementHeader());
		headerLabel.addStyleName("filterLabel attributeFilterLabel");
		return headerLabel;
	}

	private Image createSellIcon() {
		Image icon = Buttons.sell();
		icon.setTitle(textMessages.sellHint());
		icon.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Xfashion.eventBus.fireEvent(new RequestOpenSellPopupEvent());
			}
		});
		return icon;
	}

	private void registerForEvents() {
		Xfashion.eventBus.addHandler(NotepadStartMaximizeEvent.TYPE, this);
		Xfashion.eventBus.addHandler(NotepadStartMinimizeEvent.TYPE, this);
	}

}
