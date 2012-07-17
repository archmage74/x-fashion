package com.xfashion.client.at.bulk;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DecoratedPopupPanel;
import com.google.gwt.user.client.ui.DialogBox;
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
import com.xfashion.client.at.ProvidesArticleFilter;
import com.xfashion.client.brand.ChooseBrandEvent;
import com.xfashion.client.brand.ChooseBrandHandler;
import com.xfashion.client.brand.ShowChooseBrandPopupEvent;
import com.xfashion.client.cat.ChooseCategoryAndStyleEvent;
import com.xfashion.client.cat.ChooseCategoryAndStyleHandler;
import com.xfashion.client.cat.ShowChooseCategoryAndStylePopupEvent;
import com.xfashion.client.color.ChooseColorEvent;
import com.xfashion.client.color.ChooseColorHandler;
import com.xfashion.client.color.ShowChooseColorPopupEvent;
import com.xfashion.client.img.ImageManagementPopup;
import com.xfashion.client.img.ImageUploadService;
import com.xfashion.client.img.ImageUploadServiceAsync;
import com.xfashion.client.resources.ErrorMessages;
import com.xfashion.client.resources.TextMessages;
import com.xfashion.client.size.ChooseSizeEvent;
import com.xfashion.client.size.ChooseSizeHandler;
import com.xfashion.client.size.ShowChooseSizePopupEvent;
import com.xfashion.shared.ArticleTypeDTO;
import com.xfashion.shared.ArticleTypeImageDTO;
import com.xfashion.shared.BrandDTO;
import com.xfashion.shared.CategoryDTO;
import com.xfashion.shared.ColorDTO;
import com.xfashion.shared.SizeDTO;
import com.xfashion.shared.StyleDTO;

public class BulkEditArticleTypePopup implements CloseHandler<PopupPanel>, ChooseBrandHandler, ChooseSizeHandler, ChooseColorHandler,
		ChooseCategoryAndStyleHandler {

	private ImageUploadServiceAsync imageUploadService = (ImageUploadServiceAsync) GWT.create(ImageUploadService.class);

	private DecoratedPopupPanel articleTypeDetailPopup;

	private List<ArticleTypeDTO> articleTypes;
	private BulkEditArticleType bulk;

	private Grid productMatrix;
	private Image image;
	private Grid detailsGrid;
	private TextBox nameTextBox;
	private Label brandLabel;
	private Label categoryLabel;
	private Label styleLabel;
	private Label sizeLabel;
	private Label colorLabel;
	private Grid buyPriceGrid;
	private TextBox buyPriceTextBox;
	private Grid sellPriceAtGrid;
	private TextBox sellPriceAtTextBox;
	private Grid sellPriceDeGrid;
	private TextBox sellPriceDeTextBox;

	private ProvidesArticleFilter provider;

	private Formatter formatter = Formatter.getInstance();

	private TextMessages textMessages;
	private ErrorMessages errorMessages;

	protected List<HandlerRegistration> handlerRegistrations = new ArrayList<HandlerRegistration>();

	public BulkEditArticleTypePopup(ProvidesArticleFilter provider) {
		this.provider = provider;
		this.textMessages = GWT.create(TextMessages.class);
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

	public void showPopup(List<ArticleTypeDTO> articleTypes) {
		if (articleTypes == null) {
			return;
		}
		this.articleTypes = articleTypes;
		bulk = new BulkEditArticleType(articleTypes);

		if (articleTypeDetailPopup == null) {
			articleTypeDetailPopup = createPopup();
		}

		image.setUrl("");
		image.setWidth(400 + "px");
		image.setHeight(400 + "px");

		if (bulk.getSourceName() == null) {
			nameTextBox.setValue(textMessages.bulkMultipleAttributes());
		} else {
			nameTextBox.setValue(bulk.getSourceName());
		}

		if (bulk.getSourceBrandKey() == null) {
			brandLabel.setText(textMessages.bulkMultipleAttributes());
		} else {
			brandLabel.setText(provider.getBrandProvider().resolveData(bulk.getSourceBrandKey()).getName());
		}

		if (bulk.getSourceCategoryKey() == null) {
			categoryLabel.setText(textMessages.bulkMultipleAttributes());
		} else {
			categoryLabel.setText(provider.getCategoryProvider().resolveData(bulk.getSourceCategoryKey()).getName());
		}

		if (bulk.getSourceStyleKey() == null) {
			styleLabel.setText(textMessages.bulkMultipleAttributes());
		} else {
			styleLabel.setText(provider.getCategoryProvider().resolveStyle(bulk.getSourceStyleKey()).getName());
		}

		if (bulk.getSourceSizeKey() == null) {
			sizeLabel.setText(textMessages.bulkMultipleAttributes());
		} else {
			sizeLabel.setText(provider.getSizeProvider().resolveData(bulk.getSourceSizeKey()).getName());
		}

		if (bulk.getSourceColorKey() == null) {
			colorLabel.setText(textMessages.bulkMultipleAttributes());
		} else {
			colorLabel.setText(provider.getColorProvider().resolveData(bulk.getSourceColorKey()).getName());
		}

		if (bulk.getSourceBuyPrice() == null) {
			buyPriceTextBox.setValue(textMessages.bulkMultipleAttributes());
		} else {
			buyPriceTextBox.setValue(formatter.formatCentsToValue(bulk.getSourceBuyPrice()));
		}

		if (bulk.getSourceSellPriceAt() == null) {
			sellPriceAtTextBox.setValue(textMessages.bulkMultipleAttributes());
		} else {
			sellPriceAtTextBox.setValue(formatter.formatCentsToValue(bulk.getSourceSellPriceAt()));
		}

		if (bulk.getSourceSellPriceDe() == null) {
			sellPriceDeTextBox.setValue(textMessages.bulkMultipleAttributes());
		} else {
			sellPriceDeTextBox.setValue(formatter.formatCentsToValue(bulk.getSourceSellPriceDe()));
		}

		articleTypeDetailPopup.setPopupPosition(500, 50);
		articleTypeDetailPopup.show();

		updateImage();
	}

	private void updateImage() {
		String imageKey = null;
		if (bulk.getTargetImageKey() != null) {
			imageKey = bulk.getTargetImageKey();
		} else {
			imageKey = bulk.getSourceImageKey();
		}
		if (imageKey != null) {
			AsyncCallback<ArticleTypeImageDTO> callback = new AsyncCallback<ArticleTypeImageDTO>() {
				@Override
				public void onSuccess(ArticleTypeImageDTO result) {
					image.setUrl(result.getImageUrl() + ArticleTypeImageDTO.IMAGE_OPTIONS_BIG);
				}

				@Override
				public void onFailure(Throwable caught) {
					Xfashion.fireError(caught.getMessage());
				}
			};
			imageUploadService.readArticleTypeImage(imageKey, callback);
		}
	}

	public void hide() {
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

		Panel navPanel = createNavPanel();
		mainPanel.add(navPanel);

		popup.add(mainPanel);
		return popup;
	}

	private Panel createHeadline() {
		HorizontalPanel hp = new HorizontalPanel();
		hp.setWidth("400px");
		hp.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);

		Label headerLabel = new Label(textMessages.bulkEdit());
		headerLabel.setStyleName("dialogHeader");
		hp.add(headerLabel);

		hp.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);

		hp.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);

		return hp;
	}

	private Image createArticleImage() {
		Image img = createImage("", 1, 1);
		img.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				ImageManagementPopup imagePopup = new ImageManagementPopup();
				imagePopup.addSelectionHandler(new SelectionHandler<ArticleTypeImageDTO>() {
					public void onSelection(SelectionEvent<ArticleTypeImageDTO> event) {
						ArticleTypeImageDTO selected = event.getSelectedItem();
						bulk.setTargetImageKey(selected.getKey());
						updateImage();
					}
				});
				imagePopup.show();
			}
		});
		return img;
	}

	private Grid createProductMatrix() {
		Grid matrix = new Grid(2, 3);
		matrix.setStyleName("articleCell");

		categoryLabel = createCategoryLabel();
		nameTextBox = new TextBox();
		nameTextBox.setStyleName("baseInput");
		nameTextBox.setWidth("96px");
		nameTextBox.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				onChangeName();
			}
		});
		colorLabel = createColorLabel();
		styleLabel = createStyleLabel();
		brandLabel = createBrandLabel();
		sizeLabel = createSizeLabel();

		matrix.setWidget(0, 0, categoryLabel);
		matrix.setWidget(0, 1, nameTextBox);
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
				Xfashion.eventBus.fireEvent(new ShowChooseCategoryAndStylePopupEvent());
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
				Xfashion.eventBus.fireEvent(new ShowChooseCategoryAndStylePopupEvent());
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
				Xfashion.eventBus.fireEvent(new ShowChooseBrandPopupEvent());
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
				Xfashion.eventBus.fireEvent(new ShowChooseSizePopupEvent());
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
				Xfashion.eventBus.fireEvent(new ShowChooseColorPopupEvent());
			}
		});
		return label;
	}

	@Override
	public void onChooseBrand(ChooseBrandEvent event) {
		if (bulk != null) {
			BrandDTO selected = event.getCellData();
			bulk.setTargetBrandKey(selected.getKey());
			brandLabel.setText(selected.getName());
			brandLabel.addStyleName("editBulkChanged");
		}
	}

	@Override
	public void onChooseSize(ChooseSizeEvent event) {
		if (bulk != null) {
			SizeDTO selected = event.getCellData();
			bulk.setTargetSizeKey(selected.getKey());
			sizeLabel.setText(selected.getName());
			sizeLabel.addStyleName("editBulkChanged");
		}
	}

	@Override
	public void onChooseColor(ChooseColorEvent event) {
		if (bulk != null) {
			ColorDTO selected = event.getCellData();
			bulk.setTargetColorKey(selected.getKey());
			colorLabel.setText(selected.getName());
			colorLabel.addStyleName("editBulkChanged");
		}
	}

	@Override
	public void onChooseCategoryAndStyle(ChooseCategoryAndStyleEvent event) {
		if (bulk != null) {
			CategoryDTO newCategory = event.getCategory();
			StyleDTO newStyle = event.getStyle();
			bulk.setTargetCategoryKey(newCategory.getKey());
			categoryLabel.setText(newCategory.getName());
			categoryLabel.addStyleName("editBulkChanged");
			bulk.setTargetStyleKey(newStyle.getKey());
			styleLabel.setText(newStyle.getName());
			styleLabel.addStyleName("editBulkChanged");
		}
	}

	public void onChangeBuyPrice() {
		try {
			Integer price = formatter.parseEurToCents(buyPriceTextBox.getText());
			bulk.setTargetBuyPrice(price);
			buyPriceTextBox.addStyleName("editBulkChanged");
		} catch (NumberFormatException e) {
			Xfashion.fireError(errorMessages.invalidPrice());
		}
	}

	public void onChangeSellPriceAt() {
		try {
			Integer price = formatter.parseEurToCents(sellPriceAtTextBox.getText());
			bulk.setTargetSellPriceAt(price);
			sellPriceAtTextBox.addStyleName("editBulkChanged");
		} catch (NumberFormatException e) {
			Xfashion.fireError(errorMessages.invalidPrice());
		}
	}

	public void onChangeSellPriceDe() {
		try {
			Integer price = formatter.parseEurToCents(sellPriceAtTextBox.getText());
			bulk.setTargetSellPriceDe(price);
			sellPriceDeTextBox.addStyleName("editBulkChanged");
		} catch (NumberFormatException e) {
			Xfashion.fireError(errorMessages.invalidPrice());
		}
	}

	public void onChangeName() {
		bulk.setTargetName(nameTextBox.getValue());
		nameTextBox.addStyleName("editBulkChanged");
	}

	private Grid createDetailsGrid() {
		Grid grid = new Grid(4, 2);

		buyPriceGrid = new Grid(1, 2);
		Label buyPriceLabel = new Label(textMessages.buyPrice() + ":");
		grid.setWidget(0, 0, buyPriceLabel);
		buyPriceTextBox = createGridTextBoxRow(buyPriceGrid, 0, textMessages.currencySign());
		buyPriceTextBox.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				onChangeBuyPrice();
			}
		});
		grid.setWidget(0, 1, buyPriceGrid);

		sellPriceAtGrid = new Grid(1, 2);
		Label sellPriceAtLabel = new Label(textMessages.sellPriceAt() + ":");
		grid.setWidget(1, 0, sellPriceAtLabel);
		sellPriceAtTextBox = createGridTextBoxRow(sellPriceAtGrid, 0, textMessages.currencySign());
		sellPriceAtTextBox.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				onChangeSellPriceAt();
			}
		});
		grid.setWidget(1, 1, sellPriceAtGrid);

		sellPriceDeGrid = new Grid(1, 2);
		Label sellPriceLabel = new Label(textMessages.sellPriceDe() + ":");
		grid.setWidget(2, 0, sellPriceLabel);
		sellPriceDeTextBox = createGridTextBoxRow(sellPriceDeGrid, 0, textMessages.currencySign());
		sellPriceDeTextBox.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				onChangeSellPriceDe();
			}
		});
		grid.setWidget(2, 1, sellPriceDeGrid);

		return grid;
	}

	private TextBox createGridTextBoxRow(Grid grid, int row, String name) {
		Label label = new Label(name);
		TextBox content = new TextBox();
		content.setStyleName("baseInput");
		content.setWidth("96px");
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

		Button saveButton = new Button(textMessages.save());
		saveButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				showSaveConfirmation();
			}
		});
		hp.add(saveButton);

		hp.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		Button cancelButton = new Button(textMessages.close());
		cancelButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				hide();
			}
		});
		hp.add(cancelButton);

		return hp;
	}

	private void saveArticleType() {
		validate();
		bulk.applyChanges(articleTypes);
		Xfashion.eventBus.fireEvent(new UpdateArticleTypesEvent(articleTypes));
		hide();
	}

	private void validate() {
		if (bulk.getTargetName() == "") {
			Xfashion.fireError(errorMessages.invalidName());
		}
	}

	private void showSaveConfirmation() {
		final DialogBox confirmDelete = new DialogBox();
		VerticalPanel panel = new VerticalPanel();

		Label label = new Label(textMessages.bulkEditConfirmation(articleTypes.size()));
		panel.add(label);

		HorizontalPanel hp = new HorizontalPanel();
		panel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);

		Button yesButton = new Button(textMessages.yes());
		yesButton.addStyleName("leftDialogButton");
		yesButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				confirmDelete.hide();
				saveArticleType();
			}
		});
		hp.add(yesButton);

		Button noButton = new Button(textMessages.no());
		noButton.addStyleName("rightDialogButton");
		noButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				confirmDelete.hide();
			}
		});
		hp.add(noButton);
		panel.add(hp);

		confirmDelete.add(panel);
		confirmDelete.setModal(true);
		confirmDelete.center();
	}

	@Override
	public void onClose(CloseEvent<PopupPanel> event) {

	}

}
