package com.xfashion.client;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.NoSelectionModel;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.xfashion.shared.ArticleTypeDTO;

public class ArticleTypePanel extends FilterPanel {

	private ArticleTypeDTO selectedArticleType;

	private ArticleTypeCell cell;

	private ArticleTypeDetailPopup articleTypeDetailPopup;

	private CreateArticleTypePopup createArticleTypePopup;

	public ArticleTypePanel(PanelMediator panelMediator) {
		super(panelMediator);
		panelMediator.setArticleTypePanel(this);
	}

	public Panel createPanel(ListDataProvider<ArticleTypeDTO> articleTypeProvider) {
		articleTypeDetailPopup = new ArticleTypeDetailPopup(panelMediator);
		createArticleTypePopup = new CreateArticleTypePopup(panelMediator);

		VerticalPanel panel = new VerticalPanel();

		headerPanel = new HorizontalPanel();
		headerPanel.addStyleName("filterHeader");
		Label articleTypeLabel = new Label("Artikel");
		articleTypeLabel.addStyleName("filterLabel");
		headerPanel.add(articleTypeLabel);
		Button addStyleButton = new Button("+");
		addStyleButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				addArticle();
			}
		});
		headerPanel.add(addStyleButton);
		panel.add(headerPanel);
		setHeaderColor(null);

		cell = new ArticleTypeCell();
		final CellList<ArticleTypeDTO> articleTypeList = new CellList<ArticleTypeDTO>(cell, GWT.<StyleListResources> create(StyleListResources.class));
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

	private void addArticle() {
		ArticleTypeDTO articleType = panelMediator.createArticleTypeFromSelection();
		if (articleType.getCategory() == null) {
			panelMediator.showError("Keine Kategorie ausgewählt!");
		} else if (articleType.getBrand() == null) {
			panelMediator.showError("Keine Marke ausgewählt");
		} else if (articleType.getStyle() == null) {
			panelMediator.showError("Kein Stil ausgewählt");
		} else if (articleType.getSize() == null) {
			panelMediator.showError("Keine Größe ausgewählt");
		} else if (articleType.getColor() == null) {
			panelMediator.showError("Keine Farbe ausgewählt");
		} else {
			createArticleTypePopup.showForPrefilledArticleType(articleType);
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
			sb.appendHtmlConstant("<td class=\"articleIconTd\" rowspan=\"2\"><img class=\"articleIconImage\" src=\"trouserIcon.png\" /></td>");
			sb.appendHtmlConstant("<td class=\"articleUpLe\">");
			sb.appendEscaped("" + articleType.getCategory());
			sb.appendHtmlConstant("</td><td class=\"articleUpCe\">");
			sb.appendEscaped("" + articleType.getName());
			sb.appendHtmlConstant("</td><td class=\"articleUpRi\">");
			sb.appendEscaped("" + articleType.getColor());
			sb.appendHtmlConstant("</td><td class=\"articlePrice\" rowspan=\"2\">");
			String price = NumberFormat.getCurrencyFormat("EUR").format(((double) articleType.getPrice()) / 100);
			sb.appendEscaped(price);
			sb.appendHtmlConstant("</td></tr><tr>");
			sb.appendHtmlConstant("<td class=\"articleBoLe\">");
			sb.appendEscaped("" + articleType.getStyle());
			sb.appendHtmlConstant("</td><td class=\"articleBoCe\">");
			sb.appendEscaped("" + articleType.getBrand());
			sb.appendHtmlConstant("</td><td class=\"articleBoRi\">");
			sb.appendEscaped("" + articleType.getSize());
			sb.appendHtmlConstant("</td></tr></table>");
		}
	}

	public void showCreatePopup() {
		ArticleTypeDTO articleType = panelMediator.createArticleTypeFromSelection();
		createArticleTypePopup.showForPrefilledArticleType(articleType);
	}

	public void clearSelection() {

	}

}
