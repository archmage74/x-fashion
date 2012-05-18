package com.xfashion.client.at;

import com.google.gwt.cell.client.NumberCell;
import com.google.gwt.cell.client.SafeHtmlCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.view.client.CellPreviewEvent;
import com.xfashion.client.PanelMediator;
import com.xfashion.client.resources.FilterTableResources;
import com.xfashion.client.resources.TextMessages;
import com.xfashion.shared.ArticleTypeDTO;

public abstract class ArticleTable<T> {

	private ProvidesArticleFilter provider;
	
	protected ArticleDataProvider<T> articleProvider;

	private ArticleTypeDetailPopup articleTypeDetailPopup;

	protected TextMessages textMessages;
	
	public ArticleTable(ProvidesArticleFilter provider) {
		textMessages = GWT.create(TextMessages.class);
		this.provider = provider;
	}
	
	protected abstract void addNavColumns(CellTable<T> cellTable);

	public Panel create(final ArticleDataProvider<T> ap, PanelMediator panelMediator) {
		articleProvider = ap; 
		articleTypeDetailPopup = new ArticleTypeDetailPopup(panelMediator);

		CellTable<T> cellTable = new CellTable<T>(10000, GWT.<FilterTableResources> create(FilterTableResources.class));

		Column<T, SafeHtml> image = new Column<T, SafeHtml>(new SafeHtmlCell()) {
			@Override
			public SafeHtml getValue(T a) {
				ArticleTypeDTO at = ap.retrieveArticleType(a);
				StringBuffer imageHtml = new StringBuffer();
				imageHtml.append("<img class=\"articleIconImage\" ");
				if (at.getImageId() != null) {
					imageHtml.append("src=\"/img/showimage?id=");
					imageHtml.append("" + at.getImageId());
					imageHtml.append("&options=s48-c");
					imageHtml.append("\"");
				}
				imageHtml.append("/>");
				SafeHtmlBuilder sb = new SafeHtmlBuilder();
				sb.appendHtmlConstant(imageHtml.toString());
				return sb.toSafeHtml();
			}
		};
		cellTable.addColumn(image);

		Column<T, SafeHtml> categoryStyle = new Column<T, SafeHtml>(new SafeHtmlCell()) {
			@Override
			public SafeHtml getValue(T a) {
				ArticleTypeDTO at = ap.retrieveArticleType(a);
				SafeHtmlBuilder sb = new SafeHtmlBuilder();
				sb.appendHtmlConstant("<div class=\"articleUpLe\">");
				sb.appendEscaped(provider.getCategoryProvider().resolveData(at.getCategoryKey()).getName());
				sb.appendHtmlConstant("</div>");
				sb.appendHtmlConstant("<div class=\"articleBoLe\">");
				sb.appendEscaped(provider.getBrandProvider().resolveData(at.getBrandKey()).getName());
				sb.appendHtmlConstant("</div>");
				return sb.toSafeHtml();
			}
		};
		cellTable.addColumn(categoryStyle);

		Column<T, SafeHtml> nameBrand = new Column<T, SafeHtml>(new SafeHtmlCell()) {
			@Override
			public SafeHtml getValue(T a) {
				ArticleTypeDTO at = ap.retrieveArticleType(a);
				SafeHtmlBuilder html = new SafeHtmlBuilder();
				StringBuffer sb = new StringBuffer();
				sb.append("<div class=\"articleUpCe\"");
				if (at.getName().length() > 14) {
					sb.append(" style=\"font-size: 10px;\"");
				}
				sb.append(">");
				html.appendHtmlConstant(sb.toString());
				html.appendEscaped(at.getName());
				html.appendHtmlConstant("</div>");
				html.appendHtmlConstant("<div class=\"articleBoCe\">");
				html.appendEscaped(provider.getCategoryProvider().resolveStyle(at.getStyleKey()).getName());
				html.appendHtmlConstant("</div>");
				return html.toSafeHtml();
			}
		};
		cellTable.addColumn(nameBrand);

		Column<T, SafeHtml> colorSize = new Column<T, SafeHtml>(new SafeHtmlCell()) {
			@Override
			public SafeHtml getValue(T a) {
				ArticleTypeDTO at = ap.retrieveArticleType(a);
				SafeHtmlBuilder sb = new SafeHtmlBuilder();
				sb.appendHtmlConstant("<div class=\"articleUpLe\">");
				sb.appendEscaped(provider.getColorProvider().resolveData(at.getColorKey()).getName());
				sb.appendHtmlConstant("</div>");
				sb.appendHtmlConstant("<div class=\"articleBoLe\">");
				sb.appendEscaped(provider.getSizeProvider().resolveData(at.getSizeKey()).getName());
				sb.appendHtmlConstant("</div>");
				return sb.toSafeHtml();
			}
		};
		cellTable.addColumn(colorSize);

		NumberFormat priceFormat = NumberFormat.getCurrencyFormat("EUR");
		Column<T, Number> price = new Column<T, Number>(new NumberCell(priceFormat)) {
			@Override
			public Number getValue(T a) {
				ArticleTypeDTO at = ap.retrieveArticleType(a);
				return at.getSellPrice() / 100.0;
			}
		};
		price.setCellStyleNames("articlePrice");
		cellTable.addColumn(price);

		addNavColumns(cellTable);

		CellPreviewEvent.Handler<T> cellPreviewHandler = new CellPreviewEvent.Handler<T>() {
			@Override
			public void onCellPreview(CellPreviewEvent<T> event) {
				ArticleTypeDTO at = ap.retrieveArticleType(event.getValue());
				if ("click".equals(event.getNativeEvent().getType()) && event.getColumn() < 5) {
					articleTypeDetailPopup.showPopup(at);
				}
			}
		};
		cellTable.addHandler(cellPreviewHandler, CellPreviewEvent.getType());

		articleProvider.addDataDisplay(cellTable);

		ScrollPanel articleTypePanel = new ScrollPanel(cellTable);
		articleTypePanel.setHeight("750px");
		return articleTypePanel;
	}

}
