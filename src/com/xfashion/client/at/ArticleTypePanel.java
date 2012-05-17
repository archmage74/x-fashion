package com.xfashion.client.at;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.SuggestOracle;
import com.google.gwt.user.client.ui.SuggestOracle.Suggestion;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.xfashion.client.ErrorEvent;
import com.xfashion.client.PanelMediator;
import com.xfashion.client.Xfashion;
import com.xfashion.client.name.NameFilterEvent;
import com.xfashion.client.resources.ErrorMessages;
import com.xfashion.client.resources.TextMessages;
import com.xfashion.shared.ArticleTypeDTO;
import com.xfashion.shared.SizeDTO;

public class ArticleTypePanel {

	private ArticleTypeDTO selectedArticleType;

	private SuggestBox nameSuggestBox;
	
	private CreateArticleTypePopup createArticleTypePopup;

	private ErrorMessages errorMessages;
	
	private PanelMediator panelMediator;
	
	private HorizontalPanel headerPanel;
	
	private ProvidesArticleFilter provider;
	
	private TextMessages textMessages;
	
	public ArticleTypePanel(PanelMediator panelMediator) {
		this.panelMediator = panelMediator;
		textMessages = GWT.create(TextMessages.class);
		provider = panelMediator.getArticleTypeDatabase();
		errorMessages = GWT.create(ErrorMessages.class);
		panelMediator.setArticleTypePanel(this);
	}
	
	public Panel createPanel(ArticleTypeDataProvider articleTypeProvider, MultiWordSuggestOracle nameOracle) {

		createArticleTypePopup = new CreateArticleTypePopup(panelMediator);

		VerticalPanel panel = new VerticalPanel();

		headerPanel = createHeaderPanel(nameOracle);
		panel.add(headerPanel);
		
		ArticleTypeTable att = new ArticleTypeTable(provider);
		Panel atp = att.create(articleTypeProvider, panelMediator);
		panel.add(atp);
		
		return panel;
	}
	
	private HorizontalPanel createHeaderPanel(MultiWordSuggestOracle nameOracle) {
		headerPanel = new HorizontalPanel();
		headerPanel.addStyleName("filterHeader");
		nameSuggestBox = new SuggestBox(nameOracle);
		headerPanel.add(nameSuggestBox);
		nameSuggestBox.setStyleName("nameSuggestBox");
		
		SelectionHandler<SuggestOracle.Suggestion> nameSelectionHandler = new SelectionHandler<SuggestOracle.Suggestion>() {
			@Override
			public void onSelection(SelectionEvent<Suggestion> event) {
				String selectedName = event.getSelectedItem().getReplacementString();
				Xfashion.eventBus.fireEvent(new NameFilterEvent(selectedName));
			}
		};
		nameSuggestBox.addSelectionHandler(nameSelectionHandler);
		KeyUpHandler nameValueChangeHandler = new KeyUpHandler() {
			@Override
			public void onKeyUp(KeyUpEvent event) {
				String name = nameSuggestBox.getValue();
				if (name == null || name.length() == 0) {
					Xfashion.eventBus.fireEvent(new NameFilterEvent(null));
				}
			}
		};
		nameSuggestBox.addKeyUpHandler(nameValueChangeHandler);
		
		headerPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		Button deleteNameFilterButton = new Button(textMessages.clearNameSuggestFilter());
		deleteNameFilterButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				nameSuggestBox.setText("");
				Xfashion.eventBus.fireEvent(new NameFilterEvent(null));
			}
		});
		deleteNameFilterButton.setStyleName("clearNameFilterButton");
		headerPanel.add(deleteNameFilterButton);
		
		headerPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		Button addArticleButton = new Button(textMessages.createArticle());
		addArticleButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				addArticle();
			}
		});
		headerPanel.add(addArticleButton);
		setHeaderColor(null);
		return headerPanel;
	}
	
	public void setHeaderColor(String color) {
		if (color != null) {
			headerPanel.getElement().getStyle().setBackgroundColor(color);
		} else {
			headerPanel.getElement().getStyle().setBackgroundColor("#777");
		}
	}

	private void addArticle() {
		ArticleTypeDTO articleType = panelMediator.createArticleTypeFromSelection();
		List<SizeDTO> sizes = panelMediator.getSelectedSizes();
		if (articleType.getCategoryId() == null) {
			Xfashion.eventBus.fireEvent(new ErrorEvent(errorMessages.articleCreateNoCategory()));
		} else if (articleType.getBrandKey() == null) {
			Xfashion.eventBus.fireEvent(new ErrorEvent(errorMessages.articleCreateNoBrand()));
		} else if (articleType.getStyleKey() == null) {
			Xfashion.eventBus.fireEvent(new ErrorEvent(errorMessages.articleCreateNoStyle()));
		} else if (sizes.size() == 0) {
			Xfashion.eventBus.fireEvent(new ErrorEvent(errorMessages.articleCreateNoSize()));
		} else if (articleType.getColorKey() == null) {
			Xfashion.eventBus.fireEvent(new ErrorEvent(errorMessages.articleCreateNoColor()));
		} else {
			createArticleTypePopup.showForPrefilledArticleType(articleType, sizes);
		}
	}

	public ArticleTypeDTO getSelectedCategory() {
		return selectedArticleType;
	}

	public void setSelectedCategory(ArticleTypeDTO selectedArticleType) {
		this.selectedArticleType = selectedArticleType;
	}

	public PanelMediator getPanelMediator() {
		return panelMediator;
	}

}
