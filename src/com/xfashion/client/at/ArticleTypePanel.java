package com.xfashion.client.at;

import java.util.List;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.ButtonCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.NumberCell;
import com.google.gwt.cell.client.SafeHtmlCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.SuggestOracle;
import com.google.gwt.user.client.ui.SuggestOracle.Suggestion;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.CellPreviewEvent;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.NoSelectionModel;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.xfashion.client.ErrorEvent;
import com.xfashion.client.PanelMediator;
import com.xfashion.client.Xfashion;
import com.xfashion.client.resources.ArticleTableResources;
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

		headerPanel = createHeaderPanel(nameOracle);
		panel.add(headerPanel);
		
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
				SafeHtmlBuilder sb = new SafeHtmlBuilder();
				sb.appendHtmlConstant("<div class=\"articleUpCe\">");
				sb.appendEscaped(at.getName());
				sb.appendHtmlConstant("</div>");
				sb.appendHtmlConstant("<div class=\"articleBoCe\">");
				sb.appendEscaped(provider.getBrandProvider().resolveData(at.getBrandId()).getName());
				sb.appendHtmlConstant("</div>");
				return sb.toSafeHtml();
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
		
		Column<ArticleTypeDTO, String> notepadButton = new Column<ArticleTypeDTO, String>(new ButtonCell()) {
			@Override
			public String getValue(ArticleTypeDTO at) {
				return "=>";
			}
		};
		cellTable.addColumn(notepadButton);
		notepadButton.setFieldUpdater(new FieldUpdater<ArticleTypeDTO, String>() {
			@Override
			public void update(int index, ArticleTypeDTO at, String value) {
				Xfashion.eventBus.fireEvent(new AddArticleEvent(at));
			}
		});

//		final NoSelectionModel<ArticleTypeDTO> articleTypeSelectionModel = new NoSelectionModel<ArticleTypeDTO>();
//		cellTable.setSelectionModel(articleTypeSelectionModel);
//		articleTypeSelectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
//			public void onSelectionChange(SelectionChangeEvent event) {
//				if (!ignoreNextSelection)
//					articleTypeDetailPopup.showPopup((ArticleTypeDTO) articleTypeSelectionModel.getLastSelectedObject());
//			}
//		});

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
		panel.add(cellTable);
		
		return panel;
	}
	
	private Panel createHeaderPanel(MultiWordSuggestOracle nameOracle) {
		headerPanel = new HorizontalPanel();
		headerPanel.addStyleName("filterHeader");
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
		setHeaderColor(null);
		return headerPanel;
	}
	
	public Panel createPanel2(ListDataProvider<ArticleTypeDTO> articleTypeProvider, MultiWordSuggestOracle nameOracle) {

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
			Xfashion.eventBus.fireEvent(new ErrorEvent(errorMessages.articleCreateNoCategory()));
		} else if (articleType.getBrandId() == null) {
			Xfashion.eventBus.fireEvent(new ErrorEvent(errorMessages.articleCreateNoBrand()));
		} else if (articleType.getStyleId() == null) {
			Xfashion.eventBus.fireEvent(new ErrorEvent(errorMessages.articleCreateNoStyle()));
		} else if (sizes.size() == 0) {
			Xfashion.eventBus.fireEvent(new ErrorEvent(errorMessages.articleCreateNoSize()));
		} else if (articleType.getColorId() == null) {
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
			sb.appendHtmlConstant("</td><td class=\"articlePrice\" rowspan=\"2\">");
			sb.appendHtmlConstant("<input type=\"button\" value=\"=>\" />");
			sb.appendHtmlConstant("</td></tr></table>");
		}
	}

}
