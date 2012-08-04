package com.xfashion.client.at.popup;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.xfashion.client.Formatter;
import com.xfashion.client.at.IProvideArticleFilter;
import com.xfashion.client.img.ImageUploadService;
import com.xfashion.client.img.ImageUploadServiceAsync;
import com.xfashion.client.resources.TextMessages;
import com.xfashion.shared.ArticleTypeDTO;
import com.xfashion.shared.BarcodeHelper;
import com.xfashion.shared.BrandDTO;
import com.xfashion.shared.CategoryDTO;
import com.xfashion.shared.ColorDTO;
import com.xfashion.shared.SizeDTO;
import com.xfashion.shared.StyleDTO;

public abstract class ArticleTypePopup {

	public static final String PRINT_STICKER_URL = "/pdf/sticker?productNumber=";

	protected ImageUploadServiceAsync imageUploadService = (ImageUploadServiceAsync) GWT.create(ImageUploadService.class);

	protected DialogBox articleTypeDetailPopup;
	protected Anchor printStickerLink;
	protected Grid productMatrix;
	protected Image image;
	protected Label nameLabel;
	protected Label brandLabel;
	protected Label categoryLabel;
	protected Label styleLabel;
	protected Label sizeLabel;
	protected Label colorLabel;
	protected Grid detailsGrid;
	protected Label productNumber;

	protected ArticleTypeDTO articleType;
	protected IProvideArticleFilter provider;

	protected BarcodeHelper barcodeHelper = new BarcodeHelper();
	protected Formatter formatter;
	protected TextMessages textMessages;

	public ArticleTypePopup(IProvideArticleFilter provider) {
		super();
		this.provider = provider;
		this.formatter = Formatter.getInstance();
		this.textMessages = GWT.create(TextMessages.class);
	}

	protected abstract Grid createDetailsGrid();

	protected abstract Panel createNavPanel();
	
	protected abstract void updateDetails();
	
	public DialogBox createPopup() {
		articleTypeDetailPopup = new DialogBox(true);
		articleTypeDetailPopup.setGlassEnabled(true);
		articleTypeDetailPopup.setAnimationEnabled(true);

		VerticalPanel mainPanel = new VerticalPanel();

		mainPanel.add(createTopBar());
		
		mainPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		image = createArticleImage();
		mainPanel.add(image);

		productMatrix = createProductMatrix();
		mainPanel.add(productMatrix);

		detailsGrid = createDetailsGrid();
		mainPanel.add(detailsGrid);
		
		Panel navPanel = createNavPanel();
		mainPanel.add(navPanel);

		articleTypeDetailPopup.add(mainPanel);
		articleTypeDetailPopup.setPopupPosition(500, 50);

		postCreate();
		
		return articleTypeDetailPopup;
	}
	
	protected Panel createTopBar() {
		HorizontalPanel hp = new HorizontalPanel();
		hp.setWidth("400px");
		
		hp.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		productNumber = new Label(textMessages.ean() + ":");
		hp.add(productNumber);
		
		hp.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		printStickerLink = new Anchor(textMessages.sticker(), false, "", "xfashion_sticker");
		hp.add(printStickerLink);

		return hp;
	}

	public void showPopup(ArticleTypeDTO articleType) {
		if (articleType == null) {
			return;
		}
		this.articleType = articleType;

		if (articleTypeDetailPopup == null) {
			createPopup();
		}

		updateHeader();
		updateTopBar();
		updateImage();
		updateProductMatrix();
		updateDetails();
		updateNavPanel();
		
		postUpdate();
		
		articleTypeDetailPopup.show();
	}

	public void hide() {
		if (articleTypeDetailPopup != null && articleTypeDetailPopup.isShowing()) {
			articleTypeDetailPopup.hide();
		}
	}

	protected void updateTopBar() {
		productNumber.setText(textMessages.ean() + ": " + barcodeHelper.generateArticleEan(this.articleType));
		printStickerLink.setHref(PRINT_STICKER_URL + this.articleType.getProductNumber());
	}

	protected void updateNavPanel() {
		
	}
	
	protected void updateProductMatrix() {
		updateName();
		brandLabel.setText(resolveBrand());
		categoryLabel.setText(resolveCategory());
		styleLabel.setText(resolveStyleName());
		sizeLabel.setText(resolveSize());
		colorLabel.setText(resolveColor());
	}

	protected void updateName() {
		nameLabel.setText(this.articleType.getName());
	}

	protected void postUpdate() {
		
	}
	
	protected void postCreate() {
		
	}
	
	protected void updateImage() {
		image.setUrl(this.articleType.getImageUrl() + "=s400");
	}

	protected void updateHeader() {
		articleTypeDetailPopup.setText(this.articleType.getName());
	}

	protected Grid createProductMatrix() {
		Grid matrix = new Grid(2, 3);
		matrix.setStyleName("articleCell");

		matrix.setWidget(0, 0, createCategoryLabel());
		matrix.setWidget(0, 1, createNameWidget());
		matrix.setWidget(0, 2, createColorLabel());
		matrix.setWidget(1, 0, createStyleLabel());
		matrix.setWidget(1, 1, createBrandLabel());
		matrix.setWidget(1, 2, createSizeLabel());

		return matrix;
	}

	protected Widget createNameWidget() {
		nameLabel = new Label();
		nameLabel.setTitle(textMessages.name());
		nameLabel.setStyleName("articleUpCe");
		return nameLabel;
	}

	protected Label createCategoryLabel() {
		categoryLabel = new Label();
		categoryLabel.setTitle(textMessages.category());
		categoryLabel.setStyleName("articleUpLe");
		return categoryLabel;
	}

	protected Label createStyleLabel() {
		styleLabel = new Label();
		styleLabel.setTitle(textMessages.style());
		styleLabel.setStyleName("articleBoLe");
		return styleLabel;
	}

	protected Label createBrandLabel() {
		brandLabel = new Label();
		brandLabel.setTitle(textMessages.brand());
		brandLabel.setStyleName("articleBoCe");
		return brandLabel;
	}

	protected Label createSizeLabel() {
		sizeLabel = new Label();
		sizeLabel.setTitle(textMessages.size());
		sizeLabel.setStyleName("articleBoRi");
		return sizeLabel;
	}

	protected Label createColorLabel() {
		colorLabel = new Label();
		colorLabel.setTitle(textMessages.color());
		colorLabel.setStyleName("articleUpRi");
		return colorLabel;
	}

	protected String resolveColor() {
		ColorDTO color = provider.getColorProvider().resolveData(articleType.getColorKey());
		if (color == null) {
			return textMessages.unknownColor();
		} else {
			return color.getName();
		}
	}

	protected String resolveSize() {
		SizeDTO size = provider.getSizeProvider().resolveData(articleType.getSizeKey());
		if (size == null) {
			return textMessages.unknownSize();
		} else {
			return size.getName();
		}
	}

	protected String resolveCategory() {
		CategoryDTO category = provider.getCategoryProvider().resolveData(articleType.getCategoryKey());
		if (category == null) {
			return textMessages.unknownCategory();
		} else {
			return category.getName();
		}
	}

	protected String resolveBrand() {
		BrandDTO brand = provider.getBrandProvider().resolveData(articleType.getBrandKey());
		if (brand == null) {
			return textMessages.unknownBrand();
		} else {
			return brand.getName();
		}
	}

	protected String resolveStyleName() {
		StyleDTO style = provider.getCategoryProvider().resolveStyle(articleType.getStyleKey());
		if (style == null) {
			return textMessages.unknownStyle();
		} else {
			return style.getName();
		}
	}

	protected Image createArticleImage() {
		Image img = new Image("");
		img.setWidth(400 + "px");
		img.setHeight(400 + "px");
		return img;
	}

}
