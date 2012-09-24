package com.xfashion.client.at;

import com.google.gwt.cell.client.SafeHtmlCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.view.client.CellPreviewEvent;
import com.xfashion.client.Formatter;
import com.xfashion.client.Xfashion;
import com.xfashion.client.at.event.RequestShowArticleTypeDetailsEvent;
import com.xfashion.client.at.price.IGetPriceStrategy;
import com.xfashion.client.at.render.ArticleTableMatrixTemplates;
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
	
	protected ArticleTableMatrixTemplates matrixTemplates;
	
	public ArticleTable(IProvideArticleFilter provider) {
		this.textMessages = GWT.create(TextMessages.class);
		this.formatter = Formatter.getInstance();
		this.provider = provider;
		this.matrixTemplates = GWT.create(ArticleTableMatrixTemplates.class);
	}
	
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

	public ScrollPanel create(final ArticleDataProvider<T> ap) {
		articleProvider = ap; 

		cellTable = new CellTable<T>(10000, GWT.<FilterTableResources> create(FilterTableResources.class));

		cellTable.addColumn(createImageColumn(ap));
		cellTable.addColumn(createPlaceHolderLineColumn());
		cellTable.addColumn(createCBSColumn(ap));
		cellTable.addColumn(createNCSColumn(ap));
		
		cellTable.addColumn(createPriceColumn(ap));

		CellPreviewEvent.Handler<T> cellPreviewHandler = new CellPreviewEvent.Handler<T>() {
			@Override
			public void onCellPreview(CellPreviewEvent<T> event) {
				ArticleTypeDTO at = ap.retrieveArticleType(event.getValue());
				if ("click".equals(event.getNativeEvent().getType()) && event.getColumn() < 4) {
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
	
	abstract protected Column<T, T> createPriceColumn(final ArticleDataProvider<T> ap);

	protected String formatPriceString(Integer price) {
		if (price != null) {
			return formatter.centsToCurrency(price);
		} else {
			return textMessages.unknownPrice();
		}
	}
	
	protected String resolveCategory(T a) {
		ArticleTypeDTO at = articleProvider.retrieveArticleType(a);
		CategoryDTO category = null;
		if (at != null) {
			category = provider.getCategoryProvider().resolveData(at.getCategoryKey());
		}
		String name = textMessages.unknownCategory();
		if (category != null) {
			name = category.getName();
		}
		return name;
	}

	protected String resolveStyle(T a) {
		ArticleTypeDTO at = articleProvider.retrieveArticleType(a);
		StyleDTO style = null;
		if (at != null) {
			style = provider.getCategoryProvider().resolveStyle(at.getStyleKey());
		}
		String name = textMessages.unknownStyle();
		if (style != null) {
			name = style.getName();
		}
		return name;
	}

	protected String resolveBrand(T a) {
		ArticleTypeDTO at = articleProvider.retrieveArticleType(a);
		BrandDTO brand = null;
		if (at != null) {
			brand = provider.getBrandProvider().resolveData(at.getBrandKey());
		}
		String name = textMessages.unknownBrand();
		if (brand != null) {
			name = brand.getName();
		}
		return name;
	}

	protected String resolveName(T a) {
		ArticleTypeDTO at = articleProvider.retrieveArticleType(a);
		String name = textMessages.unknownName();
		if (at != null && at.getName() != null) {
			name = at.getName();
		}
		return name;
	}

	protected String resolveColor(T a) {
		ArticleTypeDTO at = articleProvider.retrieveArticleType(a);
		ColorDTO color = null;
		if (at != null) {
			color = provider.getColorProvider().resolveData(at.getColorKey());
		}
		String name = textMessages.unknownCategory();
		if (color != null) {
			name = color.getName();
		}
		return name;
	}

	protected String resolveSize(T a) {
		ArticleTypeDTO at = articleProvider.retrieveArticleType(a);
		SizeDTO size = null;
		if (at != null) {
			size = provider.getSizeProvider().resolveData(at.getSizeKey());
		}
		String name = textMessages.unknownCategory();
		if (size!= null) {
			name = size.getName();
		}
		return name;
	}

	protected Column<T, SafeHtml> createCBSColumn(final ArticleDataProvider<T> ap) {
		Column<T, SafeHtml> colorSize = new Column<T, SafeHtml>(new SafeHtmlCell()) {
			@Override
			public SafeHtml getValue(T a) {
				String category = resolveCategory(a);
				String brand = resolveBrand(a);
				String style = resolveStyle(a);
				return matrixTemplates.cbsColumn(category, brand, style);
			}
		};
		return colorSize;
	}
	
	private Column<T, SafeHtml> createPlaceHolderLineColumn() {
		Column<T, SafeHtml> colorSize = new Column<T, SafeHtml>(new SafeHtmlCell()) {
			@Override
			public SafeHtml getValue(T a) {
				return matrixTemplates.placeHolderLineColumn();
			}
		};
		return colorSize;
	}

	protected Column<T, SafeHtml> createNCSColumn(final ArticleDataProvider<T> ap) {
		Column<T, SafeHtml> colorSize = new Column<T, SafeHtml>(new SafeHtmlCell()) {
			@Override
			public SafeHtml getValue(T a) {
				String name = resolveName(a);
				String color = resolveColor(a);
				String size = resolveSize(a);
				return matrixTemplates.ncsColumn(name, color, size);
			}
		};
		return colorSize;
	}

	protected Column<T, SafeHtml> createImageColumn(final ArticleDataProvider<T> ap) {
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

