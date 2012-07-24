package com.xfashion.client.at;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
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
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.web.bindery.event.shared.HandlerRegistration;
import com.xfashion.client.Formatter;
import com.xfashion.client.Xfashion;
import com.xfashion.client.at.bulk.UpdateArticleTypesEvent;
import com.xfashion.client.at.event.DeleteArticleTypeEvent;
import com.xfashion.client.brand.event.ChooseBrandEvent;
import com.xfashion.client.brand.event.ChooseBrandHandler;
import com.xfashion.client.brand.event.ShowChooseBrandPopupEvent;
import com.xfashion.client.cat.event.ChooseCategoryAndStyleEvent;
import com.xfashion.client.cat.event.ChooseCategoryAndStyleHandler;
import com.xfashion.client.cat.event.ShowChooseCategoryAndStylePopupEvent;
import com.xfashion.client.color.event.ChooseColorEvent;
import com.xfashion.client.color.event.ChooseColorHandler;
import com.xfashion.client.color.event.ShowChooseColorPopupEvent;
import com.xfashion.client.img.ImageManagementPopup;
import com.xfashion.client.img.ImageUploadService;
import com.xfashion.client.img.ImageUploadServiceAsync;
import com.xfashion.client.resources.ErrorMessages;
import com.xfashion.client.resources.TextMessages;
import com.xfashion.client.size.event.ChooseSizeEvent;
import com.xfashion.client.size.event.ChooseSizeHandler;
import com.xfashion.client.size.event.ShowChooseSizePopupEvent;
import com.xfashion.client.user.UserManagement;
import com.xfashion.shared.ArticleTypeDTO;
import com.xfashion.shared.ArticleTypeImageDTO;
import com.xfashion.shared.BarcodeHelper;
import com.xfashion.shared.BrandDTO;
import com.xfashion.shared.CategoryDTO;
import com.xfashion.shared.ColorDTO;
import com.xfashion.shared.PriceChangeDTO;
import com.xfashion.shared.SizeDTO;
import com.xfashion.shared.StyleDTO;
import com.xfashion.shared.UserRole;

public class ArticleTypeDetailPopup implements CloseHandler<PopupPanel>, ChooseBrandHandler, ChooseSizeHandler, ChooseColorHandler,
		ChooseCategoryAndStyleHandler {

	public static final String PRINT_STICKER_URL = "/pdf/sticker?productNumber=";

	private ImageUploadServiceAsync imageUploadService = (ImageUploadServiceAsync) GWT.create(ImageUploadService.class);

	private BarcodeHelper barcodeHelper = new BarcodeHelper();

	private DecoratedPopupPanel articleTypeDetailPopup;

	private ArticleTypeDTO articleType;
	private ArticleTypeDTO updatedArticleType;
	private PriceChangeDTO priceChange;

	private boolean editMode = false;

	private Grid productMatrix;
	private Label headerLabel;
	private Image image;
	private Grid detailsGrid;
	private Label nameLabel;
	private TextBox nameTextBox;
	private Label brandLabel;
	private Label categoryLabel;
	private Label styleLabel;
	private Label sizeLabel;
	private Label colorLabel;
	private Grid buyPriceGrid;
	private Label buyPrice;
	private TextBox buyPriceTextBox;
	private Grid sellPriceAtGrid;
	private Label sellPriceAt;
	private TextBox sellPriceAtTextBox;
	private Grid sellPriceDeGrid;
	private Label sellPriceDe;
	private TextBox sellPriceDeTextBox;
	private Label productNumber;

	private Label errorLabel;

	private Anchor printStickerLink;
	private Button editArticleButton;
	private Button cancelButton;

	private ProvidesArticleFilter provider;

	protected PriceChangeDetector priceChangeDetector;
	private Formatter formatter;
	private TextMessages textMessages;
	private ErrorMessages errorMessages;

	protected List<HandlerRegistration> handlerRegistrations = new ArrayList<HandlerRegistration>();

	public ArticleTypeDetailPopup(ProvidesArticleFilter provider) {
		this.provider = provider;
		this.formatter = Formatter.getInstance();
		this.textMessages = GWT.create(TextMessages.class);
		this.priceChangeDetector = PriceChangeDetector.getInstance();
		registerForEvents();
	}

	private void registerForEvents() {
		handlerRegistrations.add(Xfashion.eventBus.addHandler(ChooseBrandEvent.TYPE, this));
		handlerRegistrations.add(Xfashion.eventBus.addHandler(ChooseColorEvent.TYPE, this));
		handlerRegistrations.add(Xfashion.eventBus.addHandler(ChooseSizeEvent.TYPE, this));
		handlerRegistrations.add(Xfashion.eventBus.addHandler(ChooseCategoryAndStyleEvent.TYPE, this));
	}

	@SuppressWarnings("unused")
	private void unregisterForEvents() {
		for (HandlerRegistration reg : handlerRegistrations) {
			reg.removeHandler();
		}
	}

	public void showPopup(ArticleTypeDTO articleType) {
		if (articleType == null) {
			return;
		}
		this.articleType = articleType;
		this.updatedArticleType = articleType.clone();

		if (articleTypeDetailPopup == null) {
			articleTypeDetailPopup = createPopup();
		}
		image.setUrl("");
		image.setWidth(400 + "px");
		image.setHeight(400 + "px");

		headerLabel.setText(articleType.getName());
		nameLabel.setText(articleType.getName());
		nameTextBox.setValue(articleType.getName());
		nameTextBox.setStyleName("baseInput");
		nameTextBox.setWidth("96px");
		brandLabel.setText(resolveBrand());
		categoryLabel.setText(resolveCategory());
		styleLabel.setText(resolveStyleName());
		sizeLabel.setText(resolveSize());
		colorLabel.setText(resolveColor());
		buyPrice.setText(formatter.formatCentsToValue(updatedArticleType.getBuyPrice()));
		buyPriceTextBox.setValue(formatter.formatCentsToValue(updatedArticleType.getBuyPrice()));
		buyPriceTextBox.setStyleName("baseInput");
		buyPriceTextBox.setWidth("96px");
		if (updatedArticleType.getSellPriceAt() != null) {
			sellPriceAt.setText(formatter.formatCentsToValue(updatedArticleType.getSellPriceAt()));
			sellPriceAtTextBox.setValue(formatter.formatCentsToValue(updatedArticleType.getSellPriceAt()));
		} else {
			sellPriceAt.setText(textMessages.unknownPrice());
			sellPriceAtTextBox.setValue("");
		}
		sellPriceAtTextBox.setStyleName("baseInput");
		sellPriceAtTextBox.setWidth("96px");
		if (updatedArticleType.getSellPriceDe() != null) {
			sellPriceDe.setText(formatter.formatCentsToValue(updatedArticleType.getSellPriceDe()));
			sellPriceDeTextBox.setValue(formatter.formatCentsToValue(updatedArticleType.getSellPriceDe()));
		} else {
			sellPriceDe.setText(textMessages.unknownPrice());
			sellPriceDeTextBox.setValue("");
		}
		sellPriceDeTextBox.setStyleName("baseInput");
		sellPriceDeTextBox.setWidth("96px");
		productNumber.setText(barcodeHelper.generateArticleEan(updatedArticleType));
		printStickerLink.setHref(PRINT_STICKER_URL + updatedArticleType.getProductNumber());
		articleTypeDetailPopup.setPopupPosition(500, 50);
		articleTypeDetailPopup.show();

		updateImage();
	}

	private String resolveColor() {
		ColorDTO color = provider.getColorProvider().resolveData(updatedArticleType.getColorKey());
		if (color == null) {
			return textMessages.unknownColor();
		} else {
			return color.getName();
		}
	}

	private String resolveSize() {
		SizeDTO size = provider.getSizeProvider().resolveData(updatedArticleType.getSizeKey());
		if (size == null) {
			return textMessages.unknownSize();
		} else {
			return size.getName();
		}
	}

	private String resolveCategory() {
		CategoryDTO category = provider.getCategoryProvider().resolveData(updatedArticleType.getCategoryKey());
		if (category == null) {
			return textMessages.unknownCategory();
		} else {
			return category.getName();
		}
	}

	private String resolveBrand() {
		BrandDTO brand = provider.getBrandProvider().resolveData(updatedArticleType.getBrandKey());
		if (brand == null) {
			return textMessages.unknownBrand();
		} else {
			return brand.getName();
		}
	}

	private String resolveStyleName() {
		StyleDTO style = provider.getCategoryProvider().resolveStyle(updatedArticleType.getStyleKey());
		if (style == null) {
			return textMessages.unknownStyle();
		} else {
			return style.getName();
		}
	}

	private void updateImage() {
		if (updatedArticleType.getImageKey() != null) {
			AsyncCallback<ArticleTypeImageDTO> callback = new AsyncCallback<ArticleTypeImageDTO>() {
				@Override
				public void onSuccess(ArticleTypeImageDTO result) {
					image.setUrl(result.getImageUrl() + ArticleTypeImageDTO.IMAGE_OPTIONS_BIG);
				}

				@Override
				public void onFailure(Throwable caught) {
				}
			};
			imageUploadService.readArticleTypeImage(updatedArticleType.getImageKey(), callback);
		}
	}

	public void hide() {
		if (editMode) {
			editMode = false;
			resetEditArticleType();
		}
		if (articleTypeDetailPopup != null && articleTypeDetailPopup.isShowing()) {
			articleTypeDetailPopup.hide();
		}
	}

	public DecoratedPopupPanel createPopup() {
		DecoratedPopupPanel popup = new DecoratedPopupPanel(true);
		popup.addCloseHandler(this);
		popup.setGlassEnabled(true);
		popup.setAnimationEnabled(true);

		VerticalPanel mainPanel = new VerticalPanel();

		Panel headline = createHeadline();
		mainPanel.add(headline);

		image = createArticleImage();
		mainPanel.add(image);

		mainPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		productMatrix = createProductMatrix();
		mainPanel.add(productMatrix);

		detailsGrid = createDetailsGrid();
		mainPanel.add(detailsGrid);

		errorLabel = new Label();
		mainPanel.add(errorLabel);

		Panel navPanel = createNavPanel();
		mainPanel.add(navPanel);

		popup.add(mainPanel);
		return popup;
	}

	private Panel createHeadline() {
		HorizontalPanel hp = new HorizontalPanel();
		hp.setWidth("400px");
		hp.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);

		headerLabel = new Label();
		headerLabel.setStyleName("dialogHeader");
		hp.add(headerLabel);

		hp.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		printStickerLink = new Anchor(textMessages.sticker(), false, "", "xfashion_sticker");
		hp.add(printStickerLink);

		return hp;
	}

	private Image createArticleImage() {
		Image img = createImage("", 1, 1);
		img.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (editMode) {
					ImageManagementPopup imagePopup = new ImageManagementPopup();
					imagePopup.addSelectionHandler(new SelectionHandler<ArticleTypeImageDTO>() {
						public void onSelection(SelectionEvent<ArticleTypeImageDTO> event) {
							ArticleTypeImageDTO selected = event.getSelectedItem();
							updatedArticleType.setImageKey(selected.getKey());
							updateImage();
						}
					});
					imagePopup.show();
				}
			}
		});
		return img;
	}

	private Grid createProductMatrix() {
		Grid matrix = new Grid(2, 3);
		matrix.setStyleName("articleCell");

		categoryLabel = createCategoryLabel();
		nameLabel = new Label();
		nameLabel.setTitle(textMessages.name());
		nameLabel.setStyleName("articleUpCe");
		nameTextBox = new TextBox();
		colorLabel = createColorLabel();
		styleLabel = createStyleLabel();
		brandLabel = createBrandLabel();
		sizeLabel = createSizeLabel();

		matrix.setWidget(0, 0, categoryLabel);
		matrix.setWidget(0, 1, nameLabel);
		matrix.setWidget(0, 2, colorLabel);
		matrix.setWidget(1, 0, styleLabel);
		matrix.setWidget(1, 1, brandLabel);
		matrix.setWidget(1, 2, sizeLabel);

		return matrix;
	}

	private Label createCategoryLabel() {
		final Label label = new Label();
		label.setTitle(textMessages.category());
		label.setStyleName("articleUpLe");
		label.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (editMode) {
					Xfashion.eventBus.fireEvent(new ShowChooseCategoryAndStylePopupEvent());
				}
			}
		});
		return label;
	}

	private Label createStyleLabel() {
		final Label label = new Label();
		label.setTitle(textMessages.style());
		label.setStyleName("articleBoLe");
		label.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (editMode) {
					Xfashion.eventBus.fireEvent(new ShowChooseCategoryAndStylePopupEvent());
				}
			}
		});
		return label;
	}

	private Label createBrandLabel() {
		final Label label = new Label();
		label.setTitle(textMessages.brand());
		label.setStyleName("articleBoCe");
		label.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (editMode) {
					Xfashion.eventBus.fireEvent(new ShowChooseBrandPopupEvent());
				}
			}
		});
		return label;
	}

	private Label createSizeLabel() {
		final Label label = new Label();
		label.setTitle(textMessages.size());
		label.setStyleName("articleBoRi");
		label.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (editMode) {
					Xfashion.eventBus.fireEvent(new ShowChooseSizePopupEvent());
				}
			}
		});
		return label;
	}

	private Label createColorLabel() {
		final Label label = new Label();
		label.setTitle(textMessages.color());
		label.setStyleName("articleUpRi");
		label.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (editMode) {
					Xfashion.eventBus.fireEvent(new ShowChooseColorPopupEvent());
				}
			}
		});
		return label;
	}

	@Override
	public void onChooseBrand(ChooseBrandEvent event) {
		if (updatedArticleType != null) {
			BrandDTO selected = event.getCellData();
			updatedArticleType.setBrandKey(selected.getKey());
			brandLabel.setText(selected.getName());
		}
	}

	@Override
	public void onChooseSize(ChooseSizeEvent event) {
		if (updatedArticleType != null) {
			SizeDTO selected = event.getCellData();
			updatedArticleType.setSizeKey(selected.getKey());
			sizeLabel.setText(selected.getName());
		}
	}

	@Override
	public void onChooseColor(ChooseColorEvent event) {
		if (updatedArticleType != null) {
			ColorDTO selected = event.getCellData();
			updatedArticleType.setColorKey(selected.getKey());
			colorLabel.setText(selected.getName());
		}
	}
	
	@Override
	public void onChooseCategoryAndStyle(ChooseCategoryAndStyleEvent event) {
		if (updatedArticleType != null) {
			CategoryDTO newCategory = event.getCategory();
			StyleDTO newStyle = event.getStyle();
			updatedArticleType.setCategoryKey(newCategory.getKey());
			categoryLabel.setText(newCategory.getName());
			updatedArticleType.setStyleKey(newStyle.getKey());
			styleLabel.setText(newStyle.getName());
		}
	}

	private Grid createDetailsGrid() {
		Grid grid = new Grid(4, 2);

		buyPriceGrid = new Grid(1, 2);
		Label buyPriceLabel = new Label(textMessages.buyPrice() + ":");
		grid.setWidget(0, 0, buyPriceLabel);
		buyPrice = createGridLabelRow(buyPriceGrid, 0, textMessages.currencySign());
		buyPriceTextBox = new TextBox();
		buyPriceTextBox.setWidth("50px");
		grid.setWidget(0, 1, buyPriceGrid);

		sellPriceAtGrid = new Grid(1, 2);
		Label sellPriceAtLabel = new Label(textMessages.sellPriceAt() + ":");
		grid.setWidget(1, 0, sellPriceAtLabel);
		sellPriceAt = createGridLabelRow(sellPriceAtGrid, 0, textMessages.currencySign());
		sellPriceAtTextBox = new TextBox();
		sellPriceAtTextBox.setWidth("50px");
		grid.setWidget(1, 1, sellPriceAtGrid);

		sellPriceDeGrid = new Grid(1, 2);
		Label sellPriceDeLabel = new Label(textMessages.sellPriceDe() + ":");
		grid.setWidget(2, 0, sellPriceDeLabel);
		sellPriceDe = createGridLabelRow(sellPriceDeGrid, 0, textMessages.currencySign());
		sellPriceDeTextBox = new TextBox();
		sellPriceDeTextBox.setWidth("50px");
		grid.setWidget(2, 1, sellPriceDeGrid);

		productNumber = createGridLabelRow(grid, 3, textMessages.ean() + ":");

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

	private Panel createNavPanel() {
		HorizontalPanel hp = new HorizontalPanel();

		if (UserManagement.hasRole(UserRole.ADMIN, UserRole.DEVELOPER)) {
			editArticleButton = new Button(textMessages.edit());
			editArticleButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					editArticleType();
				}
			});
			hp.add(editArticleButton);
	
			hp.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
			Button deleteArticleButton = new Button(textMessages.deleteArticleTypeButton());
			deleteArticleButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					showDeleteConfirmation();
				}
			});
			hp.add(deleteArticleButton);
		}
		
		hp.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		cancelButton = new Button(textMessages.close());
		cancelButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				hide();
			}
		});
		hp.add(cancelButton);

		return hp;
	}

	private void editArticleType() {
		if (editMode) {
			try {
				updateArticleType();
				if (updatedArticleType.getBuyPrice() != null) {
					buyPrice.setText(formatter.formatCentsToValue(updatedArticleType.getBuyPrice()));
				} else {
					buyPrice.setText("");
				}
				if (updatedArticleType.getSellPriceAt() != null) {
					sellPriceAt.setText(formatter.formatCentsToValue(updatedArticleType.getSellPriceAt()));
				} else {
					sellPriceAt.setText("");
				}
				if (updatedArticleType.getSellPriceDe() != null) {
					sellPriceDe.setText(formatter.formatCentsToValue(updatedArticleType.getSellPriceDe()));
				} else {
					sellPriceDe.setText("");
				}
				Xfashion.eventBus.fireEvent(new UpdateArticleTypesEvent(articleType, priceChange));
				resetEditArticleType();
			} catch (CreateArticleException e) {
				errorLabel.setText(e.getMessage());
			}
		} else {
			articleTypeDetailPopup.setAutoHideEnabled(false);
			editArticleButton.setText(textMessages.save());
			cancelButton.setText(textMessages.cancel());
			productMatrix.setWidget(0, 1, nameTextBox);
			buyPriceGrid.setWidget(0, 1, buyPriceTextBox);
			sellPriceAtGrid.setWidget(0, 1, sellPriceAtTextBox);
			sellPriceDeGrid.setWidget(0, 1, sellPriceDeTextBox);
			editMode = true;
		}
	}

	private void updateArticleType() throws CreateArticleException {
		try {
			Integer price = formatter.parseEurToCents(sellPriceAtTextBox.getText());
			updatedArticleType.setSellPriceAt(price);
		} catch (Exception e) {
			throw new CreateArticleException(errorMessages.invalidPrice());
		}
		try {
			Integer price = formatter.parseEurToCents(sellPriceDeTextBox.getText());
			updatedArticleType.setSellPriceDe(price);
		} catch (Exception e) {
			throw new CreateArticleException(errorMessages.invalidPrice());
		}
		try {
			Integer price = formatter.parseEurToCents(buyPriceTextBox.getText());
			updatedArticleType.setBuyPrice(price);
		} catch (Exception e) {
			throw new CreateArticleException(errorMessages.invalidPrice());
		}
		if (nameTextBox.getText() == null || "".equals(nameTextBox.getText())) {
			throw new CreateArticleException(errorMessages.invalidName());
		} else {
			updatedArticleType.setName(nameTextBox.getText());
		}
		
		priceChange = priceChangeDetector.detectSellPriceChange(articleType, updatedArticleType);
		
		articleType.setCategoryKey(updatedArticleType.getCategoryKey());
		articleType.setBrandKey(updatedArticleType.getBrandKey());
		articleType.setStyleKey(updatedArticleType.getStyleKey());
		articleType.setSizeKey(updatedArticleType.getSizeKey());
		articleType.setColorKey(updatedArticleType.getColorKey());
		articleType.setName(updatedArticleType.getName());
		articleType.setBuyPrice(updatedArticleType.getBuyPrice());
		articleType.setSellPriceAt(updatedArticleType.getSellPriceAt());
		articleType.setSellPriceDe(updatedArticleType.getSellPriceDe());
		articleType.setImageKey(updatedArticleType.getImageKey());
	}

	private void resetEditArticleType() {
		articleTypeDetailPopup.setAutoHideEnabled(true);
		editArticleButton.setText(textMessages.edit());
		cancelButton.setText(textMessages.close());
		productMatrix.setWidget(0, 1, nameLabel);
		buyPriceGrid.setWidget(0, 1, buyPrice);
		sellPriceAtGrid.setWidget(0, 1, sellPriceAt);
		sellPriceDeGrid.setWidget(0, 1, sellPriceDe);
		editMode = false;
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
				Xfashion.eventBus.fireEvent(new DeleteArticleTypeEvent(articleType));
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

	@Override
	public void onClose(CloseEvent<PopupPanel> event) {

	}

}
