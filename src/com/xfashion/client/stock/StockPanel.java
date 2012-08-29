package com.xfashion.client.stock;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.event.shared.EventBus;
import com.xfashion.client.MainPanel;
import com.xfashion.client.Xfashion;
import com.xfashion.client.at.ArticleFilterProvider;
import com.xfashion.client.at.ArticleTable;
import com.xfashion.client.at.ArticleTypeManagement;
import com.xfashion.client.at.brand.BrandPanel;
import com.xfashion.client.at.category.CategoryPanel;
import com.xfashion.client.at.color.ColorPanel;
import com.xfashion.client.at.name.NameDataProvider;
import com.xfashion.client.at.name.NamePanel;
import com.xfashion.client.at.size.SizePanel;
import com.xfashion.client.at.style.StylePanel;
import com.xfashion.client.event.ContentPanelResizeEvent;
import com.xfashion.client.event.ContentPanelResizeHandler;
import com.xfashion.client.notepad.ArticleAmountDataProvider;
import com.xfashion.client.notepad.GetPriceFromArticleAmountStrategy;
import com.xfashion.client.notepad.event.NotepadStartMaximizeEvent;
import com.xfashion.client.notepad.event.NotepadStartMaximizeHandler;
import com.xfashion.client.notepad.event.NotepadStartMinimizeEvent;
import com.xfashion.client.notepad.event.NotepadStartMinimizeHandler;
import com.xfashion.client.resources.ImageResources;
import com.xfashion.client.resources.TextMessages;
import com.xfashion.client.stock.event.LoadStockEvent;
import com.xfashion.client.user.SelectUserListBox;
import com.xfashion.client.user.UserManagement;
import com.xfashion.shared.ArticleAmountDTO;
import com.xfashion.shared.UserDTO;
import com.xfashion.shared.UserRole;

public class StockPanel implements NotepadStartMinimizeHandler, NotepadStartMaximizeHandler, ContentPanelResizeHandler {

	public static final int PANEL_MAX_WIDTH = 350;
	public static final int PANEL_MIN_WIDTH = 25;

	protected EventBus stockBus;
	
	private ArticleFilterProvider stockFilterProvider;

	protected HorizontalPanel headerPanel;
	protected HorizontalPanel panel;
	protected Panel scrollPanel;

	protected CategoryPanel categoryPanel;
	protected BrandPanel brandPanel;
	protected StylePanel stylePanel;
	protected SizePanel sizePanel;
	protected ColorPanel colorPanel;
	protected NamePanel namePanel;

	protected boolean minimized = false;

	protected TextMessages textMessages;
	protected ImageResources images;

	public StockPanel(StockFilterProvider stockFilterProvider, EventBus stockBus) {
		this.stockBus = stockBus;
		this.textMessages = GWT.create(TextMessages.class);
		this.images = GWT.<ImageResources> create(ImageResources.class);
		this.stockFilterProvider = stockFilterProvider;

		registerForEvents();
	}

	public CategoryPanel getCategoryPanel() {
		return categoryPanel;
	}

	public BrandPanel getBrandPanel() {
		return brandPanel;
	}

	public StylePanel getStylePanel() {
		return stylePanel;
	}

	public SizePanel getSizePanel() {
		return sizePanel;
	}

	public ColorPanel getColorPanel() {
		return colorPanel;
	}

	public NamePanel getNamePanel() {
		return namePanel;
	}

	public Panel createPanel(ArticleTypeManagement articleTypeManagement, ArticleAmountDataProvider stockProvider, ArticleAmountDataProvider notepadArticleProvider) {
		if (panel == null) {
			panel = new HorizontalPanel();
			panel.add(createCategoryPanel(articleTypeManagement));
			panel.add(createBrandPanel(articleTypeManagement));
			panel.add(createStylePanel(articleTypeManagement, categoryPanel));
			panel.add(createNamePanel());
			panel.add(createColorPanel(articleTypeManagement));
			panel.add(createSizePanel(articleTypeManagement));
			panel.add(createArticlePanel(stockProvider));
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

	private Widget createCategoryPanel(ArticleTypeManagement articleTypeManagement) {
		categoryPanel = articleTypeManagement.getCategoryManagement().createCategoryPanel(stockFilterProvider, stockBus);
		return categoryPanel.createAdminPanel();
	}

	private Widget createBrandPanel(ArticleTypeManagement articleTypeManagement) {
		brandPanel = articleTypeManagement.getBrandManagement().createBrandPanel(stockFilterProvider, stockBus);
		return brandPanel.createAdminPanel();
	}

	private Widget createStylePanel(ArticleTypeManagement articleTypeManagement, CategoryPanel categoryPanel) {
		stylePanel = articleTypeManagement.getCategoryManagement().createStylePanel(categoryPanel);
		return stylePanel.createAdminPanel();
	}

	private Widget createColorPanel(ArticleTypeManagement articleTypeManagement) {
		colorPanel = articleTypeManagement.getColorManagement().createColorPanel(stockFilterProvider, stockBus);
		return colorPanel.createAdminPanel();
	}

	private Widget createNamePanel() {
		NameDataProvider nameProvider = new NameDataProvider(stockFilterProvider.getArticleTypeProvider(), stockFilterProvider, stockBus);
		stockFilterProvider.setNameProvider(nameProvider);
		namePanel = new NamePanel(nameProvider, stockBus);
		return namePanel.createPanel();
	}

	private Widget createSizePanel(ArticleTypeManagement articleTypeManagement) {
		sizePanel = articleTypeManagement.getSizeManagement().createSizePanel(stockFilterProvider, stockBus);
		return sizePanel.createAdminPanel(new String[] { "sizePanel" });
	}

	protected Panel createArticlePanel(ArticleAmountDataProvider articleAmountProvider) {

		VerticalPanel articlePanel = new VerticalPanel();

		headerPanel = createHeaderPanel();
		articlePanel.add(headerPanel);

		GetPriceFromArticleAmountStrategy<ArticleAmountDTO> priceStrategy = new GetPriceFromArticleAmountStrategy<ArticleAmountDTO>(
				articleAmountProvider, ArticleTypeManagement.getArticleTypePriceStrategy);
		ArticleTable<ArticleAmountDTO> att = new StockArticleTable(stockFilterProvider, priceStrategy);
		scrollPanel = att.create(articleAmountProvider);
		articlePanel.add(scrollPanel);

		setWidth(PANEL_MAX_WIDTH);
		setHeight(MainPanel.contentPanelHeight);

		return articlePanel;
	}

	@Override
	public void onContentPanelResize(ContentPanelResizeEvent event) {
		setHeight(event.getHeight());
	}
	
	public void setHeight(int height) {
		if (scrollPanel != null) {
			int panelHeight = height - 40;
			scrollPanel.setHeight(panelHeight + "px");
		}
	}

	protected HorizontalPanel createHeaderPanel() {
		headerPanel = new HorizontalPanel();
		headerPanel.addStyleName("filterHeader");
		headerPanel.setWidth(PANEL_MAX_WIDTH + "px");

		headerPanel.add(createHeaderLabel());

		HorizontalPanel toolPanel = new HorizontalPanel();
		toolPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		if (UserManagement.hasRole(UserRole.ADMIN, UserRole.DEVELOPER)) {
			SelectUserListBox shopListBox = createSelectUserListBox();
			toolPanel.add(shopListBox);
		}
		headerPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		headerPanel.add(toolPanel);

		return headerPanel;
	}

	private SelectUserListBox createSelectUserListBox() {
		SelectUserListBox shopListBox = new SelectUserListBox(UserManagement.user);
		shopListBox.setVisibleItemCount(1);
		shopListBox.addSelectionHandler(new SelectionHandler<UserDTO>() {
			@Override
			public void onSelection(SelectionEvent<UserDTO> event) {
				stockBus.fireEvent(new LoadStockEvent(event.getSelectedItem()));
			}
		});
		shopListBox.init();
		return shopListBox;
	}
	
	private Label createHeaderLabel() {
		Label headerLabel = new Label(textMessages.stockManagementHeader());
		headerLabel.addStyleName("filterLabel attributeFilterLabel");
		return headerLabel;
	}

	private void registerForEvents() {
		stockBus.addHandler(NotepadStartMaximizeEvent.TYPE, this);
		stockBus.addHandler(NotepadStartMinimizeEvent.TYPE, this);
		Xfashion.eventBus.addHandler(ContentPanelResizeEvent.TYPE, this);
	}

}
