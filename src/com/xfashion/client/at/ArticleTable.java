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
import com.xfashion.client.Xfashion;
import com.xfashion.client.db.RequestShowArticleTypeDetailsEvent;
import com.xfashion.client.resources.FilterTableResources;
import com.xfashion.client.resources.TextMessages;
import com.xfashion.shared.ArticleTypeDTO;
import com.xfashion.shared.BrandDTO;
import com.xfashion.shared.CategoryDTO;
import com.xfashion.shared.ColorDTO;
import com.xfashion.shared.SizeDTO;
import com.xfashion.shared.StyleDTO;

public abstract class ArticleTable<T> {

	private ProvidesArticleFilter provider;
	
	protected ArticleDataProvider<T> articleProvider;

	protected TextMessages textMessages;
	
	public ArticleTable(ProvidesArticleFilter provider) {
		textMessages = GWT.create(TextMessages.class);
		this.provider = provider;
	}
	
	protected abstract void addNavColumns(CellTable<T> cellTable);

	public Panel create(final ArticleDataProvider<T> ap) {
		articleProvider = ap; 

		CellTable<T> cellTable = new CellTable<T>(10000, GWT.<FilterTableResources> create(FilterTableResources.class));

		Column<T, SafeHtml> image = new Column<T, SafeHtml>(new SafeHtmlCell()) {
			@Override
			public SafeHtml getValue(T a) {
				ArticleTypeDTO at = ap.retrieveArticleType(a);
				StringBuffer imageHtml = new StringBuffer();
				imageHtml.append("<img class=\"articleIconImage\" ");
				if (at.getImageKey() != null) {
					imageHtml.append("src=\"/img/showimage?id=");
					imageHtml.append("" + at.getImageKey());
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
				CategoryDTO category = provider.getCategoryProvider().resolveData(at.getCategoryKey());
				String name = "...";
				if (category != null) {
					name = category.getName();
				}
				sb.appendEscaped(name);
				sb.appendHtmlConstant("</div>");
				sb.appendHtmlConstant("<div class=\"articleBoLe\">");
				BrandDTO brand = provider.getBrandProvider().resolveData(at.getBrandKey());
				name = "...";
				if (brand != null) {
					name = brand.getName();
				}
				sb.appendEscaped(name);
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
				StyleDTO dto = provider.getCategoryProvider().resolveStyle(at.getStyleKey());
				String name = "...";
				if (dto != null) {
					name = dto.getName();
				}
				html.appendEscaped(name);
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
				ColorDTO color = provider.getColorProvider().resolveData(at.getColorKey());
				String name = "...";
				if (color != null) {
					name = color.getName();
				}
				sb.appendEscaped(name);
				sb.appendHtmlConstant("</div>");
				sb.appendHtmlConstant("<div class=\"articleBoLe\">");
				SizeDTO size = provider.getSizeProvider().resolveData(at.getSizeKey());
				name = "...";
				if (size != null) {
					name = size.getName();
				}
				sb.appendEscaped(name);
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
					Xfashion.eventBus.fireEvent(new RequestShowArticleTypeDetailsEvent(at));
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
