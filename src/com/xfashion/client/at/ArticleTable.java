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
import com.xfashion.client.resources.ArticleTableResources;
import com.xfashion.client.resources.TextMessages;
import com.xfashion.shared.ArticleTypeDTO;

public abstract class ArticleTable {

	private ProvidesArticleFilter provider;

	private ArticleTypeDetailPopup articleTypeDetailPopup;

	protected TextMessages textMessages;
	
	public ArticleTable(ProvidesArticleFilter provider) {
		textMessages = GWT.create(TextMessages.class);
		this.provider = provider;
	}
	
	protected abstract void addNavColumns(CellTable<ArticleTypeDTO> cellTable);

	public Panel create(ArticleTypeDataProvider articleTypeProvider, PanelMediator panelMediator) {

		articleTypeDetailPopup = new ArticleTypeDetailPopup(panelMediator);

		CellTable<ArticleTypeDTO> cellTable = new CellTable<ArticleTypeDTO>(10000, GWT.<ArticleTableResources> create(ArticleTableResources.class));

		Column<ArticleTypeDTO, SafeHtml> image = new Column<ArticleTypeDTO, SafeHtml>(new SafeHtmlCell()) {
			@Override
			public SafeHtml getValue(ArticleTypeDTO at) {
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

		Column<ArticleTypeDTO, SafeHtml> categoryStyle = new Column<ArticleTypeDTO, SafeHtml>(new SafeHtmlCell()) {
			@Override
			public SafeHtml getValue(ArticleTypeDTO at) {
				SafeHtmlBuilder sb = new SafeHtmlBuilder();
				sb.appendHtmlConstant("<div class=\"articleUpLe\">");
				sb.appendEscaped(provider.getCategoryProvider().resolveData(at.getCategoryId()).getName());
				sb.appendHtmlConstant("</div>");
				sb.appendHtmlConstant("<div class=\"articleBoLe\">");
				sb.appendEscaped(provider.getBrandProvider().resolveData(at.getBrandId()).getName());
				sb.appendHtmlConstant("</div>");
				return sb.toSafeHtml();
			}
		};
		cellTable.addColumn(categoryStyle);

		Column<ArticleTypeDTO, SafeHtml> nameBrand = new Column<ArticleTypeDTO, SafeHtml>(new SafeHtmlCell()) {
			@Override
			public SafeHtml getValue(ArticleTypeDTO at) {
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
				html.appendEscaped(provider.getBrandProvider().resolveData(at.getBrandId()).getName());
				html.appendHtmlConstant("</div>");
				return html.toSafeHtml();
			}
		};
		cellTable.addColumn(nameBrand);

		Column<ArticleTypeDTO, SafeHtml> colorSize = new Column<ArticleTypeDTO, SafeHtml>(new SafeHtmlCell()) {
			@Override
			public SafeHtml getValue(ArticleTypeDTO at) {
				SafeHtmlBuilder sb = new SafeHtmlBuilder();
				sb.appendHtmlConstant("<div class=\"articleUpLe\">");
				sb.appendEscaped(provider.getColorProvider().resolveData(at.getColorId()).getName());
				sb.appendHtmlConstant("</div>");
				sb.appendHtmlConstant("<div class=\"articleBoLe\">");
				sb.appendEscaped(provider.getSizeProvider().resolveData(at.getSizeId()).getName());
				sb.appendHtmlConstant("</div>");
				return sb.toSafeHtml();
			}
		};
		cellTable.addColumn(colorSize);

		NumberFormat priceFormat = NumberFormat.getCurrencyFormat("EUR");
		Column<ArticleTypeDTO, Number> price = new Column<ArticleTypeDTO, Number>(new NumberCell(priceFormat)) {
			@Override
			public Number getValue(ArticleTypeDTO at) {
				return at.getBuyPrice() / 100.0;
			}
		};
		price.setCellStyleNames("articlePrice");
		cellTable.addColumn(price);

		addNavColumns(cellTable);

		CellPreviewEvent.Handler<ArticleTypeDTO> cellPreviewHandler = new CellPreviewEvent.Handler<ArticleTypeDTO>() {
			@Override
			public void onCellPreview(CellPreviewEvent<ArticleTypeDTO> event) {
				if ("click".equals(event.getNativeEvent().getType()) && event.getColumn() != 5) {
					articleTypeDetailPopup.showPopup(event.getValue());
				}
			}
		};
		cellTable.addHandler(cellPreviewHandler, CellPreviewEvent.getType());

		articleTypeProvider.addDataDisplay(cellTable);

		ScrollPanel articleTypePanel = new ScrollPanel(cellTable);
		articleTypePanel.setHeight("800px");
		return articleTypePanel;
	}

}
