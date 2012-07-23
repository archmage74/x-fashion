package com.xfashion.client.at;

import com.google.gwt.cell.client.SafeHtmlCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.view.client.CellPreviewEvent;
import com.xfashion.client.Formatter;
import com.xfashion.client.Xfashion;
import com.xfashion.client.db.event.RequestShowArticleTypeDetailsEvent;
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
	protected IGetPriceStrategy<T> getPriceStrategy;

	protected TextMessages textMessages;
	protected Formatter formatter;
	
	public ArticleTable(ProvidesArticleFilter provider, IGetPriceStrategy<T> getPriceStrategy) {
		this.textMessages = GWT.create(TextMessages.class);
		this.formatter = Formatter.getInstance();
		this.provider = provider;
		this.getPriceStrategy = getPriceStrategy;
	}
	
	protected abstract void addNavColumns(CellTable<T> cellTable);

	public Panel create(final ArticleDataProvider<T> ap) {
		articleProvider = ap; 

		CellTable<T> cellTable = new CellTable<T>(10000, GWT.<FilterTableResources> create(FilterTableResources.class));

		cellTable.addColumn(createImageColumn(ap));
		cellTable.addColumn(createCategoryStyleColumn(ap));
		cellTable.addColumn(createNameBrandColumn(ap));
		cellTable.addColumn(createColorSizeColumn(ap));
		
		cellTable.addColumn(createPriceColumn(ap));

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

	protected String getAdditionalMatrixStyles(T a) {
		return null;
	}
	
	protected String getAdditionalPriceStyles(T a) {
		return null;
	}
	
	protected Column<T, SafeHtml> createPriceColumn(final ArticleDataProvider<T> ap) {
		Column<T, SafeHtml> price = new Column<T, SafeHtml>(new SafeHtmlCell()) {
			@Override
			public SafeHtml getValue(T a) {
				SafeHtmlBuilder sb = new SafeHtmlBuilder();
				String styles = concatStyles("articlePrice", getAdditionalPriceStyles(a));
				sb.appendHtmlConstant("<div class=\"" + styles + "\">");
				sb.appendEscaped(formatter.formatCentsToCurrency(getPriceStrategy.getPrice(a)));
				sb.appendHtmlConstant("</div>");
				return sb.toSafeHtml();
			}
		};
		return price;
	}

	private Column<T, SafeHtml> createColorSizeColumn(final ArticleDataProvider<T> ap) {
		Column<T, SafeHtml> colorSize = new Column<T, SafeHtml>(new SafeHtmlCell()) {
			@Override
			public SafeHtml getValue(T a) {
				ArticleTypeDTO at = ap.retrieveArticleType(a);
				SafeHtmlBuilder sb = new SafeHtmlBuilder();
				String styles = concatStyles("articleUpLe", getAdditionalMatrixStyles(a));
				sb.appendHtmlConstant("<div class=\"" + styles + "\">");
				ColorDTO color = provider.getColorProvider().resolveData(at.getColorKey());
				String name = textMessages.unknownColor();
				if (color != null) {
					name = color.getName();
				}
				sb.appendEscaped(name);
				sb.appendHtmlConstant("</div>");
				styles = concatStyles("articleBoLe", getAdditionalMatrixStyles(a));
				sb.appendHtmlConstant("<div class=\"" + styles + "\">");
				SizeDTO size = provider.getSizeProvider().resolveData(at.getSizeKey());
				name = textMessages.unknownSize();
				if (size != null) {
					name = size.getName();
				}
				sb.appendEscaped(name);
				sb.appendHtmlConstant("</div>");
				return sb.toSafeHtml();
			}
		};
		return colorSize;
	}

	private Column<T, SafeHtml> createNameBrandColumn(final ArticleDataProvider<T> ap) {
		Column<T, SafeHtml> nameBrand = new Column<T, SafeHtml>(new SafeHtmlCell()) {
			@Override
			public SafeHtml getValue(T a) {
				ArticleTypeDTO at = ap.retrieveArticleType(a);
				SafeHtmlBuilder html = new SafeHtmlBuilder();
				StringBuffer sb = new StringBuffer();
				String styles = concatStyles("articleUpCe", getAdditionalMatrixStyles(a));
				sb.append("<div class=\"" + styles + "\"");
				if (at.getName().length() > 14) {
					sb.append(" style=\"font-size: 10px;\"");
				}
				sb.append(">");
				html.appendHtmlConstant(sb.toString());
				html.appendEscaped(at.getName());
				html.appendHtmlConstant("</div>");
				styles = concatStyles("articleBoCe", getAdditionalMatrixStyles(a));
				html.appendHtmlConstant("<div class=\"" + styles + "\">");
				StyleDTO dto = provider.getCategoryProvider().resolveStyle(at.getStyleKey());
				String name = textMessages.unknownStyle();
				if (dto != null) {
					name = dto.getName();
				}
				html.appendEscaped(name);
				html.appendHtmlConstant("</div>");
				return html.toSafeHtml();
			}
		};
		return nameBrand;
	}

	private Column<T, SafeHtml> createCategoryStyleColumn(final ArticleDataProvider<T> ap) {
		Column<T, SafeHtml> categoryStyle = new Column<T, SafeHtml>(new SafeHtmlCell()) {
			@Override
			public SafeHtml getValue(T a) {
				ArticleTypeDTO at = ap.retrieveArticleType(a);
				SafeHtmlBuilder sb = new SafeHtmlBuilder();
				String styles = concatStyles("articleUpLe", getAdditionalMatrixStyles(a));
				sb.appendHtmlConstant("<div class=\"" + styles + "\">");
				CategoryDTO category = provider.getCategoryProvider().resolveData(at.getCategoryKey());
				String name = textMessages.unknownCategory();
				if (category != null) {
					name = category.getName();
				}
				sb.appendEscaped(name);
				sb.appendHtmlConstant("</div>");
				styles = concatStyles("articleBoLe", getAdditionalMatrixStyles(a));
				sb.appendHtmlConstant("<div class=\"" + styles + "\">");
				BrandDTO brand = provider.getBrandProvider().resolveData(at.getBrandKey());
				name = textMessages.unknownBrand();
				if (brand != null) {
					name = brand.getName();
				}
				sb.appendEscaped(name);
				sb.appendHtmlConstant("</div>");
				return sb.toSafeHtml();
			}
		};
		return categoryStyle;
	}

	private Column<T, SafeHtml> createImageColumn(final ArticleDataProvider<T> ap) {
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
		return image;
	}

	protected String concatStyles(String baseStyles, String additionalStyles) {
		StringBuffer sb = new StringBuffer(baseStyles);
		if (additionalStyles != null && additionalStyles.length() > 0) {
			sb.append(" ").append(additionalStyles);
		}
		return sb.toString();
	}
	
}
