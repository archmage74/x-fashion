package com.xfashion.client.protocols;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.xfashion.client.Formatter;
import com.xfashion.client.MainPanel;
import com.xfashion.client.Xfashion;
import com.xfashion.client.at.ArticleTable;
import com.xfashion.client.at.ArticleTypeManagement;
import com.xfashion.client.at.IProvideArticleFilter;
import com.xfashion.client.at.price.GetSellPriceFromSoldArticleStrategy;
import com.xfashion.client.event.ContentPanelResizeEvent;
import com.xfashion.client.event.ContentPanelResizeHandler;
import com.xfashion.client.notepad.GetPriceFromArticleAmountStrategy;
import com.xfashion.client.protocols.event.AddMoreAddedArticlesEvent;
import com.xfashion.client.protocols.event.AddMoreSoldArticlesEvent;
import com.xfashion.client.protocols.event.ShowSellStatisticEvent;
import com.xfashion.client.resources.ImageResources;
import com.xfashion.client.resources.TextMessages;
import com.xfashion.client.user.UserManagement;
import com.xfashion.client.user.UserService;
import com.xfashion.client.user.UserServiceAsync;
import com.xfashion.shared.AddedArticleDTO;
import com.xfashion.shared.ShopDTO;
import com.xfashion.shared.SoldArticleDTO;
import com.xfashion.shared.UserDTO;
import com.xfashion.shared.UserRole;

public class ProtocolsPanel implements ContentPanelResizeHandler {

	public static final String SOLD_ARTICLE_PANEL_WIDTH = "800px";
	public static final String ADDED_ARTICLE_PANEL_WIDTH = "700px";

	protected UserServiceAsync userService = (UserServiceAsync) GWT.create(UserService.class);

	protected List<ShopDTO> knownShops;
	protected IProvideArticleFilter filterProvider;

	protected Panel soldArticlePanel;
	protected Panel addedArticlePanel;
	
	protected ListBox shopListBox;
	protected Button addMoreSoldArticlesButton;
	protected Button addMoreAddedArticlesButton;
	protected HorizontalPanel headerPanel;

	protected TextMessages textMessages;
	protected ImageResources images;
	protected Formatter formatter;

	public ProtocolsPanel(IProvideArticleFilter filterProvider) {
		this.knownShops = new ArrayList<ShopDTO>();
		this.textMessages = GWT.create(TextMessages.class);
		this.images = GWT.<ImageResources> create(ImageResources.class);
		this.formatter = Formatter.getInstance();
		
		this.filterProvider = filterProvider;
		
		registerForEvents();
	}

	public Panel createPanel(SoldArticleDataProvider soldArticleProvider, AddedArticleDataProvider addedArticleProvider) {
		VerticalPanel panel = new VerticalPanel();
		panel.add(createHeaderPanel());
		if (UserManagement.hasRole(UserRole.ADMIN, UserRole.DEVELOPER)) {
			panel.add(createShopList());
		}
		panel.add(createProtocolPanels(soldArticleProvider, addedArticleProvider));
		return panel;
	}

	public void setUsers(Collection<UserDTO> users) {
		shopListBox.clear();
		knownShops.clear();
		shopListBox.addItem(textMessages.allShops());
		knownShops.add(new ShopDTO());
		for (UserDTO user : users) {
			if (!UserService.ROOT_USERNAME.equals(user.getUsername())) {
				shopListBox.addItem(user.getShop().getShortName());
				knownShops.add(user.getShop());
			}
		}
	}

	public void enableAddMoreSoldArticles() {
		addMoreSoldArticlesButton.setEnabled(true);
	}

	public void disableAddMoreSoldArticles() {
		addMoreSoldArticlesButton.setEnabled(false);
	}

	public void enableAddMoreAddedArticles() {
		addMoreAddedArticlesButton.setEnabled(true);
	}

	public void disableAddMoreAddedArticles() {
		addMoreAddedArticlesButton.setEnabled(false);
	}

	protected Panel createProtocolPanels(SoldArticleDataProvider soldArticleProvider, AddedArticleDataProvider addedArticleProvider) {
		HorizontalPanel hp = new HorizontalPanel();
		hp.add(createSoldArticlePanel(soldArticleProvider));
		hp.add(createAddedArticlePanel(addedArticleProvider));
		setHeight(MainPanel.contentPanelHeight);
		return hp;
	}

	protected Panel createSoldArticlePanel(SoldArticleDataProvider soldArticleProvider) {
		VerticalPanel vp = new VerticalPanel();

		ArticleTable<SoldArticleDTO> att = new SoldArticleTable(filterProvider, new GetSellPriceFromSoldArticleStrategy());
		soldArticlePanel = att.create(soldArticleProvider);
		vp.add(soldArticlePanel);
		vp.add(createAddMoreSoldArticlesButton());

		soldArticlePanel.setStyleName("filterPanel");
		soldArticlePanel.setWidth(SOLD_ARTICLE_PANEL_WIDTH);

		return vp;
	}

	protected Panel createAddedArticlePanel(AddedArticleDataProvider addedArticleProvider) {
		VerticalPanel vp = new VerticalPanel();

		GetPriceFromArticleAmountStrategy<AddedArticleDTO> priceStrategy = new GetPriceFromArticleAmountStrategy<AddedArticleDTO>(
				addedArticleProvider, ArticleTypeManagement.getArticleTypePriceStrategy);
		ArticleTable<AddedArticleDTO> att = new AddedArticleTable(filterProvider, priceStrategy);
		addedArticlePanel = att.create(addedArticleProvider);
		vp.add(addedArticlePanel);
		
		vp.add(createAddMoreAddedArticlesButton());

		addedArticlePanel.setStyleName("filterPanel");
		addedArticlePanel.setWidth(ADDED_ARTICLE_PANEL_WIDTH);

		return vp;
	}

	@Override
	public void onContentPanelResize(ContentPanelResizeEvent event) {
		setHeight(event.getHeight());
	}
	
	public void setHeight(int height) {
		int panelHeight = height - 100;
		if (addedArticlePanel != null) {
			addedArticlePanel.setHeight(panelHeight + "px");
		}
		if (soldArticlePanel != null) {
			soldArticlePanel.setHeight(panelHeight + "px");
		}
	}

	private Widget createShopList() {
		shopListBox = new ListBox();
		shopListBox.setVisibleItemCount(1);
		shopListBox.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				selectShop(shopListBox.getSelectedIndex());
			}
		});
		return shopListBox;
	}

	private Widget createAddMoreSoldArticlesButton() {
		addMoreSoldArticlesButton = new Button(textMessages.addMore());
		addMoreSoldArticlesButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Xfashion.eventBus.fireEvent(new AddMoreSoldArticlesEvent());
			}
		});
		return addMoreSoldArticlesButton;
	}

	private Widget createAddMoreAddedArticlesButton() {
		addMoreAddedArticlesButton = new Button(textMessages.addMore());
		addMoreAddedArticlesButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Xfashion.eventBus.fireEvent(new AddMoreAddedArticlesEvent());
			}
		});
		return addMoreAddedArticlesButton;
	}

	private void selectShop(int shopIndex) {
		if (shopIndex == 0) {
			Xfashion.eventBus.fireEvent(new ShowSellStatisticEvent(null));
		} else {
			Xfashion.eventBus.fireEvent(new ShowSellStatisticEvent(knownShops.get(shopIndex)));
		}
	}

	protected HorizontalPanel createHeaderPanel() {
		headerPanel = new HorizontalPanel();
		headerPanel.addStyleName("filterHeader");
		headerPanel.setWidth(SOLD_ARTICLE_PANEL_WIDTH + "px");

		headerPanel.add(createHeaderLabel());

		HorizontalPanel toolPanel = new HorizontalPanel();
		toolPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		headerPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		headerPanel.add(toolPanel);

		return headerPanel;
	}

	private Label createHeaderLabel() {
		Label headerLabel = new Label(textMessages.sellStatisticHeader());
		headerLabel.addStyleName("filterLabel attributeFilterLabel");
		return headerLabel;
	}

	private void registerForEvents() {
		Xfashion.eventBus.addHandler(ContentPanelResizeEvent.TYPE, this);
	}

}
