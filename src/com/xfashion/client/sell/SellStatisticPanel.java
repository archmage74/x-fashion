package com.xfashion.client.sell;

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
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.xfashion.client.Formatter;
import com.xfashion.client.Xfashion;
import com.xfashion.client.resources.ImageResources;
import com.xfashion.client.resources.TextMessages;
import com.xfashion.client.sell.event.AddMoreSoldArticlesEvent;
import com.xfashion.client.sell.event.ShowSellStatisticEvent;
import com.xfashion.client.user.UserService;
import com.xfashion.client.user.UserServiceAsync;
import com.xfashion.shared.ShopDTO;
import com.xfashion.shared.SoldArticleDTO;
import com.xfashion.shared.UserDTO;

public class SellStatisticPanel {

	public static final int PANEL_WIDTH = 550; 

	protected UserServiceAsync userService = (UserServiceAsync) GWT.create(UserService.class);

	protected List<ShopDTO> knownShops;
	
	protected Panel scrollPanel;
	
	protected ListBox shopListBox;
	protected ListBox soldArticlesListBox; 
	protected HorizontalPanel headerPanel;
	
	protected TextMessages textMessages;
	protected ImageResources images;
	protected Formatter formatter;
	
	public SellStatisticPanel() {
		knownShops = new ArrayList<ShopDTO>();
		
		textMessages = GWT.create(TextMessages.class);
		images = GWT.<ImageResources>create(ImageResources.class);
		formatter = new Formatter();
	}
	
	public Panel createPanel() {
		Panel articlePanel = createArticlePanel();
		scrollPanel = new SimplePanel();
		scrollPanel.setStyleName("filterPanel");
		setWidth(PANEL_WIDTH);
		scrollPanel.add(articlePanel);
		return scrollPanel;
	}
	
	public void setWidth(int width) {
		scrollPanel.setWidth(width + "px");
	}

	public void clearSoldArticles() {
		soldArticlesListBox.clear();
	}
	
	public void addSoldArticles(List<SoldArticleDTO> result) {
		for (SoldArticleDTO soldArticle : result) {
			soldArticlesListBox.addItem(createSoldArticleEntry(soldArticle));
		}
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
	
	public Integer getNumberOfShownSoldArticles() {
		return soldArticlesListBox.getItemCount();
	}
	
	protected Panel createArticlePanel() {

		VerticalPanel panel = new VerticalPanel();

		panel.add(createHeaderPanel());
		panel.add(createShopList());
		panel.add(createSoldArticleList());
		panel.add(createAddMoreButton());
		
		return panel;
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

	private Widget createAddMoreButton() {
		Button addMoreButton = new Button(textMessages.addMoreSoldArticles());
		addMoreButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Xfashion.eventBus.fireEvent(new AddMoreSoldArticlesEvent());
			}
		});
		return addMoreButton;
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
		headerPanel.setWidth(PANEL_WIDTH + "px");

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
	
	private Widget createSoldArticleList() {
		soldArticlesListBox = new ListBox();
		soldArticlesListBox.setVisibleItemCount(30);
		return soldArticlesListBox;
	}
	
	private String createSoldArticleEntry(SoldArticleDTO sa) {
		String sellPrice = formatter.formatCentsToCurrency(sa.getSellPrice()); 
		return textMessages.sellStatisticListBoxLine(sa.getAmount(), sa.getArticleName(), sa.getSellDate(), sellPrice);
	}
	
}
