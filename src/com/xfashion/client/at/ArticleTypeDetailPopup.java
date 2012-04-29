package com.xfashion.client.at;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
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
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.xfashion.client.Formatter;
import com.xfashion.client.PanelMediator;
import com.xfashion.client.brand.ChooseBrandPopup;
import com.xfashion.client.cat.ChooseCategoryPopup;
import com.xfashion.client.color.ChooseColorPopup;
import com.xfashion.client.img.ImageManagementPopup;
import com.xfashion.client.img.ImageUploadService;
import com.xfashion.client.img.ImageUploadServiceAsync;
import com.xfashion.client.resources.ErrorMessages;
import com.xfashion.client.resources.TextMessages;
import com.xfashion.client.size.ChooseSizePopup;
import com.xfashion.client.style.ChooseStylePopup;
import com.xfashion.shared.ArticleTypeDTO;
import com.xfashion.shared.BrandDTO;
import com.xfashion.shared.CategoryDTO;
import com.xfashion.shared.ColorDTO;
import com.xfashion.shared.SizeDTO;
import com.xfashion.shared.StyleDTO;
import com.xfashion.shared.img.ArticleTypeImageDTO;

public class ArticleTypeDetailPopup {

	public static final String PRINT_STICKER_URL = "/pdf/sticker?productNumber=";
	
	private ImageUploadServiceAsync imageUploadService = (ImageUploadServiceAsync) GWT.create(ImageUploadService.class);
	
	private BarcodeHelper barcodeHelper = new BarcodeHelper(); 
	
	private PanelMediator panelMediator;
	
	private DecoratedPopupPanel articleTypeDetailPopup;
	
	private ArticleTypeDTO articleType;
	private ArticleTypeDTO updatedArticleType;
	
	private boolean editMode = false;

	private Grid productMatrix;
	private Label headerLabel;
	private Image image;
	private Grid detailsGrid;
	private Label name;
	private TextBox nameTextBox;
	private Label brand;
	private Label category;
	private Label style;
	private Label size;
	private Label color;
	private Grid buyPriceGrid;
	private Label buyPrice;
	private TextBox buyPriceTextBox;
	private Grid sellPriceGrid;
	private Label sellPrice;
	private TextBox sellPriceTextBox;
	private Label productNumber;
	
	private Label errorLabel;
	
	private Anchor printStickerLink;
	private Button editArticleButton;
	private Button cancelButton;
	
	private ProvidesArticleFilter provider;
	
	private Formatter formatter = Formatter.getInstance();
	
	private TextMessages textMessages;
	private ErrorMessages errorMessages;
	
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
		this.updatedArticleType = articleType.clone();
		
		if (articleTypeDetailPopup == null) {
			articleTypeDetailPopup = createPopup();
		}
		image.setUrl("");
		image.setWidth(400 + "px");
		image.setHeight(400 + "px");
		
		headerLabel.setText(articleType.getName());
		name.setText(articleType.getName());
		nameTextBox.setValue(articleType.getName());
		nameTextBox.setStyleName("baseInput");
		nameTextBox.setWidth("96px");
		brand.setText(provider.getBrandProvider().resolveData(updatedArticleType.getBrandId()).getName());
		category.setText(provider.getCategoryProvider().resolveData(updatedArticleType.getCategoryId()).getName());
		style.setText(provider.getStyleProvider().resolveData(updatedArticleType.getStyleId()).getName());
		size.setText(provider.getSizeProvider().resolveData(updatedArticleType.getSizeId()).getName());
		color.setText(provider.getColorProvider().resolveData(updatedArticleType.getColorId()).getName());
		buyPrice.setText(formatter.formatCentsToValue(updatedArticleType.getBuyPrice()));
		buyPriceTextBox.setValue(formatter.formatCentsToValue(updatedArticleType.getBuyPrice()));
		buyPriceTextBox.setStyleName("baseInput");
		buyPriceTextBox.setWidth("96px");
		sellPrice.setText(formatter.formatCentsToValue(updatedArticleType.getSellPrice()));
		sellPriceTextBox.setValue(formatter.formatCentsToValue(updatedArticleType.getSellPrice()));
		sellPriceTextBox.setStyleName("baseInput");
		sellPriceTextBox.setWidth("96px");
		productNumber.setText(barcodeHelper.generateEan(updatedArticleType));
		printStickerLink.setHref(PRINT_STICKER_URL + updatedArticleType.getProductNumber());
		articleTypeDetailPopup.center();
		
		updateImage();
	}
	
	private void updateImage() {
		if (updatedArticleType.getImageId() != null && updatedArticleType.getImageId() > 0) {
			AsyncCallback<ArticleTypeImageDTO> callback = new AsyncCallback<ArticleTypeImageDTO>() {
				@Override
				public void onSuccess(ArticleTypeImageDTO result) {
					image.setUrl(result.getImageUrl() + ArticleTypeImageDTO.IMAGE_OPTIONS_BIG);
				}
				@Override
				public void onFailure(Throwable caught) {
				}
			};
			imageUploadService.readArticleTypeImage(updatedArticleType.getImageId(), callback);
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
							updatedArticleType.setImageId(selected.getId());
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
		
		category = createCategoryLabel();
		name = new Label();
		name.setTitle(textMessages.name());
		name.setStyleName("articleUpCe");
		nameTextBox = new TextBox();
		color = createColorLabel();
		style = createStyleLabel();
		brand = createBrandLabel();
		size = createSizeLabel();
		
		matrix.setWidget(0, 0, category);
		matrix.setWidget(0, 1, name);
		matrix.setWidget(0, 2, color);
		matrix.setWidget(1, 0, style);
		matrix.setWidget(1, 1, brand);
		matrix.setWidget(1, 2, size);
		
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
					ChooseCategoryPopup chooseCategoryPopup = new ChooseCategoryPopup(panelMediator);
					chooseCategoryPopup.addSelectionHandler(new SelectionHandler<CategoryDTO>() {
						public void onSelection(SelectionEvent<CategoryDTO> event) {
							CategoryDTO selected = event.getSelectedItem();
							updatedArticleType.setCategoryId(selected.getId());
							label.setText(selected.getName());
						}
					});
					chooseCategoryPopup.show();
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
					ChooseStylePopup choosePopup = new ChooseStylePopup(panelMediator);
					choosePopup.addSelectionHandler(new SelectionHandler<StyleDTO>() {
						public void onSelection(SelectionEvent<StyleDTO> event) {
							StyleDTO selected = event.getSelectedItem();
							updatedArticleType.setStyleId(selected.getId());
							label.setText(selected.getName());
						}
					});
					choosePopup.show();
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
					ChooseBrandPopup choosePopup = new ChooseBrandPopup(panelMediator);
					choosePopup.addSelectionHandler(new SelectionHandler<BrandDTO>() {
						public void onSelection(SelectionEvent<BrandDTO> event) {
							BrandDTO selected = event.getSelectedItem();
							updatedArticleType.setBrandId(selected.getId());
							label.setText(selected.getName());
						}
					});
					choosePopup.show();
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
					ChooseSizePopup choosePopup = new ChooseSizePopup(panelMediator);
					choosePopup.addSelectionHandler(new SelectionHandler<SizeDTO>() {
						public void onSelection(SelectionEvent<SizeDTO> event) {
							SizeDTO selected = event.getSelectedItem();
							updatedArticleType.setSizeId(selected.getId());
							label.setText(selected.getName());
						}
					});
					choosePopup.show();
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
					ChooseColorPopup choosePopup = new ChooseColorPopup(panelMediator);
					choosePopup.addSelectionHandler(new SelectionHandler<ColorDTO>() {
						public void onSelection(SelectionEvent<ColorDTO> event) {
							ColorDTO selected = event.getSelectedItem();
							updatedArticleType.setColorId(selected.getId());
							label.setText(selected.getName());
						}
					});
					choosePopup.show();
				}
			}
		});
		return label;
	}
	
	private Grid createDetailsGrid() {
		Grid grid = new Grid(3, 2);
		
		buyPriceGrid = new Grid(1, 2);
		Label buyPriceLabel = new Label(textMessages.buyPrice() + ":");
		grid.setWidget(0, 0, buyPriceLabel);
		buyPrice = createGridLabelRow(buyPriceGrid, 0, textMessages.currencySign());
		buyPriceTextBox = new TextBox();
		buyPriceTextBox.setWidth("50px");
		grid.setWidget(0, 1, buyPriceGrid);
		
		sellPriceGrid = new Grid(1, 2);
		Label sellPriceLabel = new Label(textMessages.sellPrice() + ":");
		grid.setWidget(1, 0, sellPriceLabel);
		sellPrice = createGridLabelRow(sellPriceGrid, 0, textMessages.currencySign());
		sellPriceTextBox = new TextBox();
		sellPriceTextBox.setWidth("50px");
		grid.setWidget(1, 1, sellPriceGrid);

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
	
	private Panel createNavPanel() {
		HorizontalPanel hp = new HorizontalPanel();
		
		editArticleButton = new Button(textMessages.edit());
		editArticleButton.addClickHandler(new ClickHandler()  {
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
				buyPrice.setText(formatter.formatCentsToValue(updatedArticleType.getBuyPrice()));
				sellPrice.setText(formatter.formatCentsToValue(updatedArticleType.getSellPrice()));
				panelMediator.updateArticleType(articleType);
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
			sellPriceGrid.setWidget(0, 1, sellPriceTextBox);
			editMode = true;
		}
	}
	
	private void updateArticleType() throws CreateArticleException {
		try {
			Integer price = formatter.parseEurToCents(sellPriceTextBox.getText());
			updatedArticleType.setSellPrice(price);
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
			throw  new CreateArticleException(errorMessages.invalidName());
		} else {
			updatedArticleType.setName(nameTextBox.getText());
		}
		articleType.setCategoryId(updatedArticleType.getCategoryId());
		articleType.setBrandId(updatedArticleType.getBrandId());
		articleType.setStyleId(updatedArticleType.getStyleId());
		articleType.setSizeId(updatedArticleType.getSizeId());
		articleType.setColorId(updatedArticleType.getColorId());
		articleType.setName(updatedArticleType.getName());
		articleType.setBuyPrice(updatedArticleType.getBuyPrice());
		articleType.setSellPrice(updatedArticleType.getSellPrice());
		articleType.setImageId(updatedArticleType.getImageId());
	}
	
	private void resetEditArticleType() {
		articleTypeDetailPopup.setAutoHideEnabled(true);
		editArticleButton.setText(textMessages.edit());
		cancelButton.setText(textMessages.close());
		productMatrix.setWidget(0, 1, name);
		buyPriceGrid.setWidget(0, 1, buyPrice);
		sellPriceGrid.setWidget(0, 1, sellPrice);
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
