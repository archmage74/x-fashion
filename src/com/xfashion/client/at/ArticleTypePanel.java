package com.xfashion.client.at;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.SuggestOracle;
import com.google.gwt.user.client.ui.SuggestOracle.Suggestion;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.xfashion.client.ErrorEvent;
import com.xfashion.client.Xfashion;
import com.xfashion.client.at.bulk.BulkEditArticleTypePopup;
import com.xfashion.client.at.name.NameFilterEvent;
import com.xfashion.client.at.size.SizeDataProvider;
import com.xfashion.client.db.ArticleTypeDatabase;
import com.xfashion.client.resources.ErrorMessages;
import com.xfashion.client.resources.TextMessages;
import com.xfashion.client.tool.Buttons;
import com.xfashion.shared.ArticleTypeDTO;
import com.xfashion.shared.CategoryDTO;
import com.xfashion.shared.SizeDTO;

public class ArticleTypePanel {

	private ArticleTypeDTO selectedArticleType;

	private SuggestBox nameSuggestBox;
	
	private CreateArticleTypePopup createArticleTypePopup;

	private ErrorMessages errorMessages;
	
	private HorizontalPanel headerPanel;
	
	private ArticleTypeDatabase articleTypeDatabase;
	
	private TextMessages textMessages;
	
	public ArticleTypePanel(ArticleTypeDatabase filterProvider) {
		this.textMessages = GWT.create(TextMessages.class);
		this.articleTypeDatabase = filterProvider;
		this.errorMessages = GWT.create(ErrorMessages.class);
	}
	
	public Panel createPanel(ArticleTypeDataProvider articleTypeProvider, MultiWordSuggestOracle nameOracle) {

		createArticleTypePopup = new CreateArticleTypePopup(articleTypeDatabase);

		VerticalPanel panel = new VerticalPanel();
		panel.setWidth("580px");
		
		headerPanel = createHeaderPanel(nameOracle);
		panel.add(headerPanel);
		
		ArticleTypeTable articleTypeTable = new ArticleTypeTable(articleTypeDatabase, ArticleTypeManagement.getArticleTypePriceStrategy);
		Panel atp = articleTypeTable.create(articleTypeProvider);
		panel.add(atp);
		
		return panel;
	}
	
	private HorizontalPanel createHeaderPanel(MultiWordSuggestOracle nameOracle) {
		headerPanel = new HorizontalPanel();
		headerPanel.addStyleName("filterHeader");
		
		HorizontalPanel nameSuggestPanel = new HorizontalPanel();

		nameSuggestBox = new SuggestBox(nameOracle);
		nameSuggestBox.setStyleName("nameSuggestBox");
		
		SelectionHandler<SuggestOracle.Suggestion> nameSelectionHandler = new SelectionHandler<SuggestOracle.Suggestion>() {
			@Override
			public void onSelection(SelectionEvent<Suggestion> event) {
				String selectedName = event.getSelectedItem().getReplacementString();
				Xfashion.eventBus.fireEvent(new NameFilterEvent(selectedName));
			}
		};
		nameSuggestBox.addSelectionHandler(nameSelectionHandler);
		KeyUpHandler nameValueChangeHandler = new KeyUpHandler() {
			@Override
			public void onKeyUp(KeyUpEvent event) {
				String name = nameSuggestBox.getValue();
				if (name == null || name.length() == 0) {
					Xfashion.eventBus.fireEvent(new NameFilterEvent(null));
				}
			}
		};
		nameSuggestBox.addKeyUpHandler(nameValueChangeHandler);
		nameSuggestPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		nameSuggestPanel.add(nameSuggestBox);
		
		Button deleteNameFilterButton = new Button(textMessages.clearNameSuggestFilter());
		deleteNameFilterButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				nameSuggestBox.setText("");
				Xfashion.eventBus.fireEvent(new NameFilterEvent(null));
			}
		});
		deleteNameFilterButton.setStyleName("clearNameFilterButton");
		nameSuggestPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		nameSuggestPanel.add(deleteNameFilterButton);
		
		headerPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		headerPanel.add(nameSuggestPanel);
		
		headerPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		Button addArticleButton = new Button(textMessages.createArticle());
		addArticleButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				addArticle();
			}
		});
		headerPanel.add(addArticleButton);

		headerPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		headerPanel.add(createEditBulkButton());

		return headerPanel;
	}

	private Image createEditBulkButton() {
		Image editBulkButton = Buttons.editBulk();
		ClickHandler editBulkButtonClickHandler = new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				BulkEditArticleTypePopup bulkEditPopup = new BulkEditArticleTypePopup(articleTypeDatabase);
				bulkEditPopup.showPopup(articleTypeDatabase.getArticleTypeProvider().getList());
			}
		};
		editBulkButton.addClickHandler(editBulkButtonClickHandler);
		return editBulkButton;
	}
	
	private void addArticle() {
		ArticleTypeDTO articleType = createArticleTypeFromSelection();
		List<SizeDTO> sizes = getSelectedSizes();
		if (articleType.getCategoryKey() == null) {
			Xfashion.eventBus.fireEvent(new ErrorEvent(errorMessages.articleCreateNoCategory()));
		} else if (articleType.getBrandKey() == null) {
			Xfashion.eventBus.fireEvent(new ErrorEvent(errorMessages.articleCreateNoBrand()));
		} else if (articleType.getStyleKey() == null) {
			Xfashion.eventBus.fireEvent(new ErrorEvent(errorMessages.articleCreateNoStyle()));
		} else if (sizes.size() == 0) {
			Xfashion.eventBus.fireEvent(new ErrorEvent(errorMessages.articleCreateNoSize()));
		} else if (articleType.getColorKey() == null) {
			Xfashion.eventBus.fireEvent(new ErrorEvent(errorMessages.articleCreateNoColor()));
		} else {
			createArticleTypePopup.showForPrefilledArticleType(articleType, sizes);
		}
	}

	private ArticleTypeDTO createArticleTypeFromSelection() {
		ArticleTypeDTO at = new ArticleTypeDTO();
		at.setUsed(false);

		CategoryDTO category = articleTypeDatabase.getCategoryProvider().getCategoryFilter();
		if (category != null) {
			at.setCategoryKey(category.getKey());
		}

		Set<String> styles = articleTypeDatabase.getCategoryProvider().getStyleFilter();
		if (styles.size() == 1) {
			at.setStyleKey(styles.iterator().next());
		}
		
		Set<String> brands = articleTypeDatabase.getBrandProvider().getFilter();
		if (brands.size() == 1) {
			at.setBrandKey(brands.iterator().next());
		}
		
		Set<String> colors = articleTypeDatabase.getColorProvider().getFilter();
		if (colors.size() == 1) {
			at.setColorKey(colors.iterator().next());
		}
		
		return at;
	}
	
	private List<SizeDTO> getSelectedSizes() {
		ArrayList<SizeDTO> list = new ArrayList<SizeDTO>();
		Set<String> sizes = articleTypeDatabase.getSizeProvider().getFilter();
		SizeDataProvider sizeProvider = articleTypeDatabase.getSizeProvider();
		for (String id : sizes) {
			list.add(sizeProvider.resolveData(id));
		}
		return list;
	}
	
	public ArticleTypeDTO getSelectedCategory() {
		return selectedArticleType;
	}

	public void setSelectedCategory(ArticleTypeDTO selectedArticleType) {
		this.selectedArticleType = selectedArticleType;
	}

}
