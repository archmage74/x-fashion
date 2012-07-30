package com.xfashion.client.at;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.event.shared.HandlerRegistration;
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
import com.xfashion.client.dialog.YesNoCallback;
import com.xfashion.client.dialog.YesNoPopup;
import com.xfashion.client.img.ImageManagementPopup;
import com.xfashion.client.resources.ErrorMessages;
import com.xfashion.client.size.event.ChooseSizeEvent;
import com.xfashion.client.size.event.ChooseSizeHandler;
import com.xfashion.client.size.event.ShowChooseSizePopupEvent;
import com.xfashion.shared.ArticleTypeDTO;
import com.xfashion.shared.ArticleTypeImageDTO;
import com.xfashion.shared.BrandDTO;
import com.xfashion.shared.CategoryDTO;
import com.xfashion.shared.ColorDTO;
import com.xfashion.shared.PriceChangeDTO;
import com.xfashion.shared.SizeDTO;
import com.xfashion.shared.StyleDTO;

public class EditArticleTypePopup extends ArticleTypePopup implements ChooseBrandHandler, ChooseSizeHandler, ChooseColorHandler,
		ChooseCategoryAndStyleHandler {

	protected ArticleTypeDTO updatedArticleType;
	protected PriceChangeDTO priceChange;

	protected TextBox nameTextBox;
	protected TextBox buyPriceTextBox;
	protected TextBox sellPriceAtTextBox;
	protected TextBox sellPriceDeTextBox;
	protected Button editArticleButton;

	protected PriceChangeDetector priceChangeDetector;
	protected ErrorMessages errorMessages;

	protected List<HandlerRegistration> handlerRegistrations = new ArrayList<HandlerRegistration>();

	public EditArticleTypePopup(ProvidesArticleFilter provider) {
		super(provider);
		this.errorMessages = GWT.create(ErrorMessages.class);
		this.priceChangeDetector = PriceChangeDetector.getInstance();
		registerForEvents();
	}

	@Override
	public void showPopup(ArticleTypeDTO articleType) {
		this.updatedArticleType = articleType.clone();
		super.showPopup(articleType);
	}

	@Override
	protected void updateDetails() {
		buyPriceTextBox.setValue(formatter.formatCentsToValue(updatedArticleType.getBuyPrice()));
		buyPriceTextBox.setStyleName("baseInput");
		buyPriceTextBox.setWidth("96px");
		if (updatedArticleType.getSellPriceAt() != null) {
			sellPriceAtTextBox.setValue(formatter.formatCentsToValue(updatedArticleType.getSellPriceAt()));
		} else {
			sellPriceAtTextBox.setValue("");
		}
		sellPriceAtTextBox.setStyleName("baseInput");
		sellPriceAtTextBox.setWidth("96px");
		if (updatedArticleType.getSellPriceDe() != null) {
			sellPriceDeTextBox.setValue(formatter.formatCentsToValue(updatedArticleType.getSellPriceDe()));
		} else {
			sellPriceDeTextBox.setValue("");
		}
		sellPriceDeTextBox.setStyleName("baseInput");
		sellPriceDeTextBox.setWidth("96px");
	}

	@Override
	protected void updateName() {
		nameTextBox.setValue(this.articleType.getName());
		nameTextBox.setStyleName("baseInput");
		nameTextBox.setWidth("96px");
	}

	@Override
	protected void postCreate() {
		articleTypeDetailPopup.setAutoHideEnabled(false);
	}

	@Override
	protected Widget createNameWidget() {
		nameTextBox = new TextBox();
		return nameTextBox;
	}
	
	@Override
	protected Image createArticleImage() {
		Image image = super.createArticleImage();
		image.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
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
		});
		return image;
	}

	@Override
	protected Label createCategoryLabel() {
		final Label label = super.createCategoryLabel();
		label.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Xfashion.eventBus.fireEvent(new ShowChooseCategoryAndStylePopupEvent());
			}
		});
		return label;
	}

	@Override
	protected Label createStyleLabel() {
		final Label label = super.createStyleLabel();
		label.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Xfashion.eventBus.fireEvent(new ShowChooseCategoryAndStylePopupEvent());
			}
		});
		return label;
	}

	@Override
	protected Label createBrandLabel() {
		final Label label = super.createBrandLabel();
		label.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Xfashion.eventBus.fireEvent(new ShowChooseBrandPopupEvent());
			}
		});
		return label;
	}

	@Override
	protected Label createSizeLabel() {
		final Label label = super.createSizeLabel();
		label.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Xfashion.eventBus.fireEvent(new ShowChooseSizePopupEvent());
			}
		});
		return label;
	}

	@Override
	protected Label createColorLabel() {
		final Label label = super.createColorLabel();
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

	protected Grid createDetailsGrid() {
		Grid grid = new Grid(4, 2);

		Grid buyPriceGrid = new Grid(1, 2);
		Label buyPriceLabel = new Label(textMessages.buyPrice() + ":");
		grid.setWidget(0, 0, buyPriceLabel);
		buyPriceTextBox = createGridTextBoxRow(buyPriceGrid, 0, textMessages.currencySign());
		grid.setWidget(0, 1, buyPriceGrid);

		Grid sellPriceAtGrid = new Grid(1, 2);
		Label sellPriceAtLabel = new Label(textMessages.sellPriceAt() + ":");
		grid.setWidget(1, 0, sellPriceAtLabel);
		sellPriceAtTextBox = createGridTextBoxRow(sellPriceAtGrid, 0, textMessages.currencySign());
		grid.setWidget(1, 1, sellPriceAtGrid);

		Grid sellPriceDeGrid = new Grid(1, 2);
		Label sellPriceDeLabel = new Label(textMessages.sellPriceDe() + ":");
		grid.setWidget(2, 0, sellPriceDeLabel);
		sellPriceDeTextBox = createGridTextBoxRow(sellPriceDeGrid, 0, textMessages.currencySign());
		grid.setWidget(2, 1, sellPriceDeGrid);

		productNumber = createGridLabelRow(grid, 3, textMessages.ean() + ":");

		return grid;
	}

	protected Panel createNavPanel() {
		HorizontalPanel hp = new HorizontalPanel();

		editArticleButton = new Button(textMessages.save());
		editArticleButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				editArticleType();
			}
		});
		hp.add(editArticleButton);

		hp.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		Button deleteArticleButton = new Button(textMessages.deleteArticleTypeButton());
		deleteArticleButton.setEnabled(false); // TODO
		deleteArticleButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				showDeleteConfirmation();
			}
		});
		hp.add(deleteArticleButton);

		hp.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		Button cancelButton = createCancelButton();
		hp.add(cancelButton);

		return hp;
	}

	protected Button createCancelButton() {
		Button cancelButton = new Button(textMessages.cancel());
		cancelButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				hide();
			}
		});
		return cancelButton;
	}

	private Label createGridLabelRow(Grid grid, int row, String name) {
		Label label = new Label(name);
		Label content = new Label();
		grid.setWidget(row, 0, label);
		grid.setWidget(row, 1, content);
		return content;
	}

	private TextBox createGridTextBoxRow(Grid grid, int row, String name) {
		Label label = new Label(name);
		TextBox content = new TextBox();
		content.setWidth("50px");
		grid.setWidget(row, 0, label);
		grid.setWidget(row, 1, content);
		return content;
	}

	private void editArticleType() {
		try {
			updateArticleType();
			Xfashion.eventBus.fireEvent(new UpdateArticleTypesEvent(articleType, priceChange));
			hide();
		} catch (CreateArticleException e) {
			Xfashion.fireError(e.getMessage());
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

	private void showDeleteConfirmation() {
		YesNoPopup confirmDelete = new YesNoPopup(textMessages.confirmDeleteArticle(), new YesNoCallback() {
			@Override
			public void yes() {
				articleTypeDetailPopup.hide();
				Xfashion.eventBus.fireEvent(new DeleteArticleTypeEvent(articleType));
			}

			@Override
			public void no() {
			}
		});
		confirmDelete.show();
	}

	private void registerForEvents() {
		handlerRegistrations.add(Xfashion.eventBus.addHandler(ChooseBrandEvent.TYPE, this));
		handlerRegistrations.add(Xfashion.eventBus.addHandler(ChooseColorEvent.TYPE, this));
		handlerRegistrations.add(Xfashion.eventBus.addHandler(ChooseSizeEvent.TYPE, this));
		handlerRegistrations.add(Xfashion.eventBus.addHandler(ChooseCategoryAndStyleEvent.TYPE, this));
	}

}
