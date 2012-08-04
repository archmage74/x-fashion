package com.xfashion.client.removed;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.xfashion.client.Formatter;
import com.xfashion.client.Xfashion;
import com.xfashion.client.at.ArticleTypeManagement;
import com.xfashion.client.at.IProvideArticleFilter;
import com.xfashion.client.notepad.GetPriceFromArticleAmountStrategy;
import com.xfashion.client.protocols.event.AddMoreAddedArticlesEvent;
import com.xfashion.client.resources.TextMessages;
import com.xfashion.client.user.UserService;
import com.xfashion.client.user.UserServiceAsync;
import com.xfashion.shared.RemovedArticleDTO;

public class RemovedArticlePanel {

	public static final String REMOVED_ARTICLE_PANEL_WIDTH = "700px";

	protected UserServiceAsync userService = (UserServiceAsync) GWT.create(UserService.class);

	protected IProvideArticleFilter filterProvider;

	protected Panel scrollPanel;

	protected Button addMoreRemovedArticlesButton;
	protected HorizontalPanel headerPanel;

	protected TextMessages textMessages;
	protected Formatter formatter;

	public RemovedArticlePanel(IProvideArticleFilter filterProvider) {
		this.textMessages = GWT.create(TextMessages.class);
		this.formatter = Formatter.getInstance();
		this.filterProvider = filterProvider;
	}

	public Panel createPanel(RemovedArticleDataProvider removedArticleProvider) {
		VerticalPanel panel = new VerticalPanel();
		panel.add(createHeaderPanel());
		panel.add(createRemovedArticlePanel(removedArticleProvider));
		return panel;
	}

	public void enableAddMoreRemovedArticles() {
		addMoreRemovedArticlesButton.setEnabled(true);
	}

	public void disableAddMoreRemovedArticles() {
		addMoreRemovedArticlesButton.setEnabled(false);
	}

	protected Panel createRemovedArticlePanel(RemovedArticleDataProvider removedArticleProvider) {
		VerticalPanel vp = new VerticalPanel();

		GetPriceFromArticleAmountStrategy<RemovedArticleDTO> priceStrategy = new GetPriceFromArticleAmountStrategy<RemovedArticleDTO>(
				removedArticleProvider, ArticleTypeManagement.getArticleTypePriceStrategy);
		RemovedArticleTable rat = new RemovedArticleTable(filterProvider, priceStrategy);
		Panel atp = rat.create(removedArticleProvider);
		vp.add(atp);
		
		vp.add(createAddMoreRemovedArticlesButton());

		SimplePanel panel = new SimplePanel();
		panel.setStyleName("filterPanel");
		panel.setWidth(REMOVED_ARTICLE_PANEL_WIDTH);
		panel.add(vp);

		return panel;
	}

	private Widget createAddMoreRemovedArticlesButton() {
		addMoreRemovedArticlesButton = new Button(textMessages.addMore());
		addMoreRemovedArticlesButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Xfashion.eventBus.fireEvent(new AddMoreAddedArticlesEvent());
			}
		});
		return addMoreRemovedArticlesButton;
	}

	protected HorizontalPanel createHeaderPanel() {
		headerPanel = new HorizontalPanel();
		headerPanel.addStyleName("filterHeader");
		headerPanel.setWidth(REMOVED_ARTICLE_PANEL_WIDTH + "px");

		headerPanel.add(createHeaderLabel());

		HorizontalPanel toolPanel = new HorizontalPanel();
		toolPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		headerPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		headerPanel.add(toolPanel);

		return headerPanel;
	}

	private Label createHeaderLabel() {
		Label headerLabel = new Label(textMessages.removedArticleHeader());
		headerLabel.addStyleName("filterLabel");
		return headerLabel;
	}

}
