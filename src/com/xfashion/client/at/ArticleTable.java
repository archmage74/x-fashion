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
import com.xfashion.client.at.event.RequestShowArticleTypeDetailsEvent;
import com.xfashion.client.at.price.IGetPriceStrategy;
import com.xfashion.client.resources.FilterTableResources;
import com.xfashion.client.resources.TextMessages;
import com.xfashion.shared.ArticleTypeDTO;
import com.xfashion.shared.BrandDTO;
import com.xfashion.shared.CategoryDTO;
import com.xfashion.shared.ColorDTO;
import com.xfashion.shared.SizeDTO;
import com.xfashion.shared.StyleDTO;

public abstract class ArticleTable<T> {

	private IProvideArticleFilter provider;
	
	protected ArticleDataProvider<T> articleProvider;
	
	protected CellTable<T> cellTable;
	protected ScrollPanel articleTypePanel;
	
	protected TextMessages textMessages;
	protected Formatter formatter;
	
	public ArticleTable(IProvideArticleFilter provider) {
		this.textMessages = GWT.create(TextMessages.class);
		this.formatter = Formatter.getInstance();
		this.provider = provider;
	}
	
	protected abstract void addNavColumns(CellTable<T> cellTable);
	
	protected abstract IGetPriceStrategy<T> currentPriceStrategy();
	
	public ArticleDataProvider<T> getArticleProvider() {
		return articleProvider;
	}

	public CellTable<T> getCellTable() {
		return cellTable;
	}

	public ScrollPanel getArticleTypePanel() {
		return articleTypePanel;
	}

	public Panel create(final ArticleDataProvider<T> ap) {
		articleProvider = ap; 

		cellTable = new CellTable<T>(10000, GWT.<FilterTableResources> create(FilterTableResources.class));

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

		articleTypePanel = new ScrollPanel(cellTable);
		articleTypePanel.setHeight("500px");
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
				String priceString = textMessages.unknownPrice();
				if (a != null) {
					Integer price = currentPriceStrategy().getPrice(a);
					if (price != null) {
						priceString = formatter.formatCentsToCurrency(price);
					}
				}
				sb.appendEscaped(priceString);
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
				ColorDTO color = null;
				if (at != null) {
					color = provider.getColorProvider().resolveData(at.getColorKey());
				}
				String name = textMessages.unknownColor();
				if (color != null) {
					name = color.getName();
				}
				sb.appendEscaped(name);
				sb.appendHtmlConstant("</div>");
				styles = concatStyles("articleBoLe", getAdditionalMatrixStyles(a));
				sb.appendHtmlConstant("<div class=\"" + styles + "\">");
				SizeDTO size = null;
				if (at != null) {
					size = provider.getSizeProvider().resolveData(at.getSizeKey());
				}
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
				String name = textMessages.unknownName();
				if (at != null && at.getName() != null) {
					name = at.getName();
				}
				sb.append("<div class=\"" + styles + "\"");
				if (name.length() > 14) {
					sb.append(" style=\"font-size: 10px;\"");
				}
				sb.append(">");
				html.appendHtmlConstant(sb.toString());
				html.appendEscaped(name);
				html.appendHtmlConstant("</div>");
				styles = concatStyles("articleBoCe", getAdditionalMatrixStyles(a));
				html.appendHtmlConstant("<div class=\"" + styles + "\">");
				StyleDTO style = null;
				if (at != null) {
					style = provider.getCategoryProvider().resolveStyle(at.getStyleKey());
				}
				name = textMessages.unknownStyle();
				if (style != null) {
					name = style.getName();
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
				CategoryDTO category = null;
				if (at != null) {
					category = provider.getCategoryProvider().resolveData(at.getCategoryKey());
				}
				String name = textMessages.unknownCategory();
				if (category != null) {
					name = category.getName();
				}
				sb.appendEscaped(name);
				sb.appendHtmlConstant("</div>");
				styles = concatStyles("articleBoLe", getAdditionalMatrixStyles(a));
				sb.appendHtmlConstant("<div class=\"" + styles + "\">");
				BrandDTO brand = null;
				if (at != null) {
					brand = provider.getBrandProvider().resolveData(at.getBrandKey());
				}
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
				if (at != null && at.getImageKey() != null) {
					imageHtml.append("src=\"");
					imageHtml.append(at.getImageUrl());
					imageHtml.append("=s48");
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
