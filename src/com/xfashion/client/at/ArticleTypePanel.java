package com.xfashion.client.at;

import java.util.List;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.SuggestOracle;
import com.google.gwt.user.client.ui.SuggestOracle.Suggestion;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.NoSelectionModel;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.xfashion.client.PanelMediator;
import com.xfashion.client.resources.ErrorMessages;
import com.xfashion.client.resources.FilterListResources;
import com.xfashion.shared.ArticleTypeDTO;
import com.xfashion.shared.SizeDTO;

public class ArticleTypePanel {

	private ArticleTypeDTO selectedArticleType;

	private ArticleTypeCell cell;
	
	private SuggestBox nameSuggestBox;
	
	private ArticleTypeDetailPopup articleTypeDetailPopup;

	private CreateArticleTypePopup createArticleTypePopup;

	private ErrorMessages errorMessages;
	
	private PanelMediator panelMediator;
	
	private Panel headerPanel;
	
	private ProvidesArticleFilter provider;
	
	public ArticleTypePanel(PanelMediator panelMediator) {
//		super(panelMediator, dataProvider);
		this.panelMediator = panelMediator;
		provider = panelMediator.getArticleTypeDatabase();
		errorMessages = GWT.create(ErrorMessages.class);
		panelMediator.setArticleTypePanel(this);
	}

	public Panel createPanel(ListDataProvider<ArticleTypeDTO> articleTypeProvider, MultiWordSuggestOracle nameOracle) {

		articleTypeDetailPopup = new ArticleTypeDetailPopup(panelMediator);
		createArticleTypePopup = new CreateArticleTypePopup(panelMediator);

		VerticalPanel panel = new VerticalPanel();

		headerPanel = new HorizontalPanel();
		headerPanel.addStyleName("filterHeader");
//		Label articleTypeLabel = new Label("Artikel");
//		articleTypeLabel.addStyleName("filterLabel");
//		headerPanel.add(articleTypeLabel);
		
		nameSuggestBox = new SuggestBox(nameOracle);
		headerPanel.add(nameSuggestBox);
		nameSuggestBox.setStyleName("nameSuggestBox");
		
		SelectionHandler<SuggestOracle.Suggestion> nameSelectionHandler = new SelectionHandler<SuggestOracle.Suggestion>() {
			@Override
			public void onSelection(SelectionEvent<Suggestion> event) {
				String selectedName = event.getSelectedItem().getReplacementString();
				panelMediator.setSelectedName(selectedName);
			}
		};
		nameSuggestBox.addSelectionHandler(nameSelectionHandler);
		KeyUpHandler nameValueChangeHandler = new KeyUpHandler() {
			@Override
			public void onKeyUp(KeyUpEvent event) {
				String name = nameSuggestBox.getValue();
				if (name == null || name.length() == 0) {
					panelMediator.setSelectedName(null);
				}
			}
		};
		nameSuggestBox.addKeyUpHandler(nameValueChangeHandler);
		
		Button deleteNameFilterButton = new Button("x");
		deleteNameFilterButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				nameSuggestBox.setText("");
				panelMediator.setSelectedName(null);
			}
		});
		deleteNameFilterButton.setStyleName("clearNameFilterButton");
		headerPanel.add(deleteNameFilterButton);
		
		Button addArticleButton = new Button("+");
		addArticleButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				addArticle();
			}
		});
		headerPanel.add(addArticleButton);
		panel.add(headerPanel);
		setHeaderColor(null);

		cell = new ArticleTypeCell();
		final CellList<ArticleTypeDTO> articleTypeList = new CellList<ArticleTypeDTO>(cell, GWT.<FilterListResources> create(FilterListResources.class));
		articleTypeList.setPageSize(1000);

		final NoSelectionModel<ArticleTypeDTO> articleTypeSelectionModel = new NoSelectionModel<ArticleTypeDTO>();
		articleTypeList.setSelectionModel(articleTypeSelectionModel);
		articleTypeSelectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
			public void onSelectionChange(SelectionChangeEvent event) {
				articleTypeDetailPopup.showPopup((ArticleTypeDTO) articleTypeSelectionModel.getLastSelectedObject());
			}
		});
		articleTypeProvider.addDataDisplay(articleTypeList);

		ScrollPanel articleTypePanel = new ScrollPanel(articleTypeList);
		articleTypePanel.setSize("500px", "700px");
		panel.add(articleTypePanel);

		return panel;
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
			panelMediator.showError(errorMessages.articleCreateNoCategory());
		} else if (articleType.getBrandId() == null) {
			panelMediator.showError(errorMessages.articleCreateNoBrand());
		} else if (articleType.getStyleId() == null) {
			panelMediator.showError(errorMessages.articleCreateNoStyle());
		} else if (sizes.size() == 0) {
			panelMediator.showError(errorMessages.articleCreateNoSize());
		} else if (articleType.getColorId() == null) {
			panelMediator.showError(errorMessages.articleCreateNoColor());
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

	class ArticleTypeCell extends AbstractCell<ArticleTypeDTO> {
		@Override
		public void render(Context context, ArticleTypeDTO articleType, SafeHtmlBuilder sb) {
			if (articleType == null) {
				return;
			}
			sb.appendHtmlConstant("<table class=\"articleCell\">");
			sb.appendHtmlConstant("<tr>");
			StringBuffer imageHtml = new StringBuffer();
			
			imageHtml.append("<td class=\"articleIconTd\" rowspan=\"2\"><img class=\"articleIconImage\" ");
			if (articleType.getImageId() != null) {
				imageHtml.append("src=\"/img/showimage?id=");
				imageHtml.append("" + articleType.getImageId());
				imageHtml.append("&options=s48-c");
				imageHtml.append("\"");
			}
			imageHtml.append("/></td>");
			sb.appendHtmlConstant(imageHtml.toString());
			
			sb.appendHtmlConstant("<td class=\"articleUpLe\">");
			sb.appendEscaped("" + provider.getCategoryProvider().resolveData(articleType.getCategoryId()).getName());
			sb.appendHtmlConstant("</td><td class=\"articleUpCe\">");
			sb.appendEscaped("" + articleType.getName());
			sb.appendHtmlConstant("</td><td class=\"articleUpRi\">");
			sb.appendEscaped("" + provider.getColorProvider().resolveData(articleType.getColorId()).getName());
			sb.appendHtmlConstant("</td><td class=\"articlePrice\" rowspan=\"2\">");
			String price = NumberFormat.getCurrencyFormat("EUR").format(((double) articleType.getSellPrice()) / 100);
			sb.appendEscaped(price);
			sb.appendHtmlConstant("</td></tr><tr>");
			sb.appendHtmlConstant("<td class=\"articleBoLe\">");
			sb.appendEscaped("" + provider.getStyleProvider().resolveData(articleType.getStyleId()).getName());
			sb.appendHtmlConstant("</td><td class=\"articleBoCe\">");
			sb.appendEscaped("" + provider.getBrandProvider().resolveData(articleType.getBrandId()).getName());
			sb.appendHtmlConstant("</td><td class=\"articleBoRi\">");
			sb.appendEscaped("" + provider.getSizeProvider().resolveData(articleType.getSizeId()).getName());
			sb.appendHtmlConstant("</td></tr></table>");
		}
	}

}
