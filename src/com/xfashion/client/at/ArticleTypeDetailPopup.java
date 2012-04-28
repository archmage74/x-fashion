package com.xfashion.client.at;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DecoratedPopupPanel;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.xfashion.client.Formatter;
import com.xfashion.client.PanelMediator;
import com.xfashion.client.img.ImageUploadService;
import com.xfashion.client.img.ImageUploadServiceAsync;
import com.xfashion.client.resources.TextMessages;
import com.xfashion.shared.ArticleTypeDTO;
import com.xfashion.shared.img.ArticleTypeImageDTO;

public class ArticleTypeDetailPopup {

	public static final String PRINT_STICKER_URL = "/pdf/sticker?productNumber=";
	
	private ImageUploadServiceAsync imageUploadService = (ImageUploadServiceAsync) GWT.create(ImageUploadService.class);
	
	private BarcodeHelper barcodeHelper = new BarcodeHelper(); 
	
	private PanelMediator panelMediator;
	
	private DecoratedPopupPanel articleTypeDetailPopup;
	
	private ArticleTypeDTO articleType;
	
	private Label headerLabel;
	private Image image;
	private Label name;
	private Label brand;
	private Label category;
	private Label style;
	private Label size;
	private Label color;
	private Label buyPrice;
	private Label sellPrice;
	private Label productNumber;
	private Anchor printStickerLink;
	
	private ProvidesArticleFilter provider;
	
	private TextMessages textMessages;
	
	public ArticleTypeDetailPopup(PanelMediator panelMediator) {
		this.panelMediator = panelMediator;
		provider = panelMediator.getArticleTypeDatabase();
		panelMediator.setArticleTypeDetailPopup(this);
		textMessages = GWT.create(TextMessages.class);
	}
	
	public void showPopup(ArticleTypeDTO articleType) {
		if (articleType == null) {
			return;
		}
		this.articleType = articleType;
		if (articleTypeDetailPopup == null) {
			articleTypeDetailPopup = createPopup();
		}
		image.setUrl("");
		image.setWidth(400 + "px");
		image.setHeight(400 + "px");
		
		Formatter formatter = Formatter.getInstance();
		
		headerLabel.setText(articleType.getName());
		name.setText(articleType.getName());
		brand.setText(provider.getBrandProvider().resolveData(articleType.getBrandId()).getName());
		category.setText(provider.getCategoryProvider().resolveData(articleType.getCategoryId()).getName());
		style.setText(provider.getStyleProvider().resolveData(articleType.getStyleId()).getName());
		size.setText(provider.getSizeProvider().resolveData(articleType.getSizeId()).getName());
		color.setText(provider.getColorProvider().resolveData(articleType.getColorId()).getName());
		buyPrice.setText(formatter.formatCents(articleType.getBuyPrice()));
		sellPrice.setText(formatter.formatCents(articleType.getSellPrice()));
		productNumber.setText(barcodeHelper.generateEan(articleType));
		printStickerLink.setHref(PRINT_STICKER_URL + articleType.getProductNumber());
		articleTypeDetailPopup.center();
		
		if (articleType.getImageId() != null && articleType.getImageId() > 0) {
			AsyncCallback<ArticleTypeImageDTO> callback = new AsyncCallback<ArticleTypeImageDTO>() {
				@Override
				public void onSuccess(ArticleTypeImageDTO result) {
					image.setUrl(result.getImageUrl() + ArticleTypeImageDTO.IMAGE_OPTIONS_BIG);
				}
				@Override
				public void onFailure(Throwable caught) {
				}
			};
			imageUploadService.readArticleTypeImage(articleType.getImageId(), callback);
		}
	}

	public DecoratedPopupPanel createPopup() {
		DecoratedPopupPanel popup = new DecoratedPopupPanel(true);
		popup.setGlassEnabled(true);
		popup.setAnimationEnabled(true);
		
		VerticalPanel mainPanel = new VerticalPanel();

		Panel headline = createHeadline();
		mainPanel.add(headline);
		
		image = createImage("", 1, 1);
		mainPanel.add(image);

		mainPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		Grid productMatrix = createProductMatrix();
		mainPanel.add(productMatrix);
		
		Grid detailsGrid = createDetailsGrid();

		mainPanel.add(detailsGrid);
		
		popup.add(mainPanel);
		return popup;
	}
	
	private Panel createHeadline() {
		HorizontalPanel hp = new HorizontalPanel();
		hp.setWidth("400px");
		
		headerLabel = new Label();
		headerLabel.setStyleName("dialogHeader");
		hp.add(headerLabel);
		
		hp.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		printStickerLink = new Anchor(textMessages.sticker(), false, "", "xfashion_sticker");
		hp.add(printStickerLink);

		Button deleteArticleButton = new Button(textMessages.deleteArticleTypeButton());
		deleteArticleButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				showDeleteConfirmation();
			}
		}); 
		hp.add(deleteArticleButton);

		return hp;
	}
	
	private Grid createProductMatrix() {
		Grid matrix = new Grid(2, 3);
		matrix.setStyleName("articleCell");
		
		category = new Label();
		category.setTitle(textMessages.category());
		category.setStyleName("articleUpLe");
		name = new Label();
		name.setTitle(textMessages.name());
		name.setStyleName("articleUpCe");
		color = new Label();
		color.setTitle(textMessages.color());
		color.setStyleName("articleUpRi");
		style = new Label();
		style.setTitle(textMessages.style());
		style.setStyleName("articleBoLe");
		brand = new Label();
		brand.setTitle(textMessages.brand());
		brand.setStyleName("articleBoCe");
		size = new Label();
		size.setTitle(textMessages.size());
		size.setStyleName("articleBoRi");
		
		matrix.setWidget(0, 0, category);
		matrix.setWidget(0, 1, name);
		matrix.setWidget(0, 2, color);
		matrix.setWidget(1, 0, style);
		matrix.setWidget(1, 1, brand);
		matrix.setWidget(1, 2, size);
		
		return matrix;
	}
	
	private Grid createDetailsGrid() {
		Grid grid = new Grid(3, 2);
		
		buyPrice = createGridLabelRow(grid, 0, textMessages.buyPrice() + ":");
		sellPrice = createGridLabelRow(grid, 1, textMessages.sellPrice() + ":");
		productNumber = createGridLabelRow(grid, 2, textMessages.ean() + ":");
		
		return grid;
	}
	
	private Label createGridLabelRow(Grid grid, int row, String name) {
		Label label = new Label(name);
		Label content = new Label();
		grid.setWidget(row, 0, label);
		grid.setWidget(row, 1, content);
		return content;
	}
	
	private Image createImage(String url, int width, int height) {
		Image img = new Image(url);
		img.setWidth(width + "px");
		img.setHeight(height + "px");
		return img;
	}
	
	private void showDeleteConfirmation() {
		final DialogBox confirmDelete = new DialogBox();
		DockPanel panel = new DockPanel();

		Label label = new Label(textMessages.confirmDeleteArticle());
		panel.add(label, DockPanel.NORTH);
		
		Button yesButton = new Button(textMessages.yes());
		yesButton.addStyleName("leftDialogButton");
		yesButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				confirmDelete.hide();
				articleTypeDetailPopup.hide();
				panelMediator.deleteArticleType(articleType);
			}
		});
		panel.add(yesButton, DockPanel.WEST);

		panel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		Button noButton = new Button(textMessages.no());
		noButton.addStyleName("rightDialogButton");
		noButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				confirmDelete.hide();
			}
		});
		panel.add(noButton, DockPanel.EAST);
		
		confirmDelete.add(panel);
		confirmDelete.setModal(true);
		confirmDelete.center();
		
	}

}
