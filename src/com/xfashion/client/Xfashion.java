package com.xfashion.client;

import java.util.ArrayList;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.cellview.client.HasKeyboardPagingPolicy.KeyboardPagingPolicy;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DecoratedPopupPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.NoSelectionModel;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Xfashion implements EntryPoint {
	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	// private static final String SERVER_ERROR = "An error occurred while " +
	// "attempting to contact the server. Please check your network "
	// + "connection and try again.";

	/**
	 * Create a remote service proxy to talk to the server-side Greeting
	 * service.
	 */
	// private final GreetingServiceAsync greetingService =
	// GWT.create(GreetingService.class);

	private Panel mainPanel;
	private String mainPanelStyle;
	
	HorizontalPanel categoryHeaderPanel;
	HorizontalPanel styleHeaderPanel;
	HorizontalPanel brandHeaderPanel;
	HorizontalPanel sizeHeaderPanel;
	HorizontalPanel colorHeaderPanel;
	HorizontalPanel articleHeaderPanel;
	
	private static String selectedCategory = "";
	
	static class CategoryCell extends AbstractCell<String> {
		@Override
		public void render(Context context, String category, SafeHtmlBuilder sb) {
			if (category == null) {
				return;
			}
			System.out.println("sel: " + selectedCategory);
			String categoryId = "categoryUnselected";
			if (category.equals("Herrenhose") && category.equals(selectedCategory)) {
				categoryId = "categoryMaleTrousersSelected";
			} else if (category.equals("Damenhose") && category.equals(selectedCategory)) {
				categoryId = "categoryFemaleTrousersSelected";
			} else if (category.equals("Herrenoberteil") && category.equals(selectedCategory)) {
				categoryId = "categoryMaleTopSelected";
			} else if (category.equals("Damenoberteil") && category.equals(selectedCategory)) {
				categoryId = "categoryFemaleTopSelected";
			} else if (category.equals("Kleider") && category.equals(selectedCategory)) {
				categoryId = "categorySkirtSelected";
			} else if (category.equals("Strumpfwaren") && category.equals(selectedCategory)) {
				categoryId = "categoryStockingsSelected";
			} else if (category.equals("Gürtel") && category.equals(selectedCategory)) {
				categoryId = "categoryBeltSelected";
			} else if (category.equals("Bademode") && category.equals(selectedCategory)) {
				categoryId = "categoryBathingSelected";
			} else if (category.equals("Accessoirs") && category.equals(selectedCategory)) {
				categoryId = "categoryAccessoriesSelected";
			}

			sb.appendHtmlConstant("<div id=\"" + categoryId + "\">");
//			sb.appendHtmlConstant("<div class=\"" + categoryClass + "\"style=\"color: " + color + " height:32px; margin-left:3px; margin-right:3px; font-size:20px; \">");
			sb.appendHtmlConstant(category);
			sb.appendHtmlConstant("</div>");
		}
	}

	static class StyleCell extends AbstractCell<String> {
		@Override
		public void render(Context context, String category, SafeHtmlBuilder sb) {
			if (category == null) {
				return;
			}
			sb.appendHtmlConstant("<div style=\"height:20px; margin-left:3px; margin-right:3px; font-size:12px;\">");
			sb.appendHtmlConstant(category);
			sb.appendHtmlConstant("</div>");
		}
	}

	static class ArticleTypeCell extends AbstractCell<ArticleType> {
		@Override
		public void render(Context context, ArticleType articleType, SafeHtmlBuilder sb) {
			if (articleType == null) {
				return;
			}
			sb.appendHtmlConstant("<table class=\"articleCell\">");
			sb.appendHtmlConstant("<tr>");
			sb.appendHtmlConstant("<td class=\"articleIconTd\" rowspan=\"2\"><img class=\"articleIconImage\" src=\"trouserIcon.png\" /></td>");
			sb.appendHtmlConstant("<td class=\"articleUpLe\">");
			sb.appendEscaped(articleType.getCategory());
			sb.appendHtmlConstant("</td><td class=\"articleUpCe\">");
			sb.appendEscaped(articleType.getName());
			sb.appendHtmlConstant("</td><td class=\"articleUpRi\">");
			sb.appendEscaped(articleType.getColor());
			sb.appendHtmlConstant("</td></tr><tr>");
			sb.appendHtmlConstant("<td class=\"articleBoLe\">");
			sb.appendEscaped(articleType.getStyle());
			sb.appendHtmlConstant("</td><td class=\"articleBoCe\">");
			sb.appendEscaped(articleType.getBrand());
			sb.appendHtmlConstant("</td><td class=\"articleBoRi\">");
			sb.appendEscaped(articleType.getSize());
			sb.appendHtmlConstant("</td></tr></table>");
		}
	}

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		mainPanel = new HorizontalPanel();

		ArticleTypeDatabase articleTypeDatabase = new ArticleTypeDatabase();

		mainPanel.add(createCategoryPanel(articleTypeDatabase));
		mainPanel.add(createStylePanel(articleTypeDatabase));
		mainPanel.add(createBrandPanel(articleTypeDatabase));
		mainPanel.add(createSizePanel(articleTypeDatabase));
		mainPanel.add(createColorPanel(articleTypeDatabase));
		mainPanel.add(createArticleTypeList(articleTypeDatabase));

		RootPanel.get("mainPanelContainer").add(mainPanel);

	}

	private void setHeaderStyle(String style) {
		if (mainPanelStyle != null) {
			categoryHeaderPanel.removeStyleName(mainPanelStyle);
			styleHeaderPanel.removeStyleName(mainPanelStyle);
			brandHeaderPanel.removeStyleName(mainPanelStyle);
			sizeHeaderPanel.removeStyleName(mainPanelStyle);
			colorHeaderPanel.removeStyleName(mainPanelStyle);
			articleHeaderPanel.removeStyleName(mainPanelStyle);
		}
		mainPanelStyle = style;
		if (mainPanelStyle != null) {
			categoryHeaderPanel.addStyleName(style);
			styleHeaderPanel.addStyleName(style);
			brandHeaderPanel.addStyleName(style);
			sizeHeaderPanel.addStyleName(style);
			colorHeaderPanel.addStyleName(style);
			articleHeaderPanel.addStyleName(style);
		}
	}
	
	private Panel createCategoryPanel(final ArticleTypeDatabase articleTypeDatabase) {
		VerticalPanel panel = new VerticalPanel();

		categoryHeaderPanel = new HorizontalPanel();
		categoryHeaderPanel.addStyleName("filterHeader");
		Label categoryLabel = new Label("Kategorie");
		categoryLabel.addStyleName("filterLabel");
		categoryHeaderPanel.add(categoryLabel);
		panel.add(categoryHeaderPanel);

		CategoryCell categoryCell = new CategoryCell();
		final CellList<String> categoryList = new CellList<String>(categoryCell, GWT.<CategoryListResources> create(CategoryListResources.class));
		categoryList.setPageSize(30);
//		categoryList.setKeyboardPagingPolicy(KeyboardPagingPolicy.INCREASE_RANGE);
//		categoryList.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.BOUND_TO_SELECTION);

		final SingleSelectionModel<String> selectionModel = new SingleSelectionModel<String>();
		categoryList.setSelectionModel(selectionModel);
		selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
			public void onSelectionChange(SelectionChangeEvent event) {
				selectedCategory = selectionModel.getSelectedObject();
				if (selectionModel.getSelectedObject() != null) {
					if (selectionModel.getSelectedObject().equals("Damenhose")) {
						setHeaderStyle("categoryFemaleTrousers");
					} else if (selectionModel.getSelectedObject().equals("Herrenhose")) {
						setHeaderStyle("categoryMaleTrousers");
					} else if (selectionModel.getSelectedObject().equals("Damenoberteil")) {
						setHeaderStyle("categoryFemaleTop");
					} else if (selectionModel.getSelectedObject().equals("Herrenoberteil")) {
						setHeaderStyle("categoryMaleTop");
					} else if (selectionModel.getSelectedObject().equals("Kleider")) {
						setHeaderStyle("categorySkirt");
					} else if (selectionModel.getSelectedObject().equals("Strumpfwaren")) {
						setHeaderStyle("categoryStockings");
					} else if (selectionModel.getSelectedObject().equals("Gürtel")) {
						setHeaderStyle("categoryBelt");
					} else if (selectionModel.getSelectedObject().equals("Bademode")) {
						setHeaderStyle("categoryBathing");
					} else if (selectionModel.getSelectedObject().equals("Accessoirs")) {
						setHeaderStyle("categoryAccessories");
					}
				}
				articleTypeDatabase.setCategoryFilter(selectionModel.getSelectedObject());
			}
		});
		articleTypeDatabase.addCategoryDisplay(categoryList);

		categoryList.addStyleName("categoryList");
		panel.add(categoryList);

		return panel;
	}

	private Panel createStylePanel(final ArticleTypeDatabase articleTypeDatabase) {
		VerticalPanel panel = new VerticalPanel();

		styleHeaderPanel = new HorizontalPanel();
		styleHeaderPanel.addStyleName("filterHeader");
		Button clearButton = new Button("clr");
		styleHeaderPanel.add(clearButton);
		Label label = new Label("Style");
		label.addStyleName("filterLabel");
		styleHeaderPanel.add(label);
		panel.add(styleHeaderPanel);

		StyleCell styleCell = new StyleCell();
		CellList<String> styleList = new CellList<String>(styleCell, GWT.<StyleListResources> create(StyleListResources.class));
		styleList.setPageSize(30);
//		styleList.setKeyboardPagingPolicy(KeyboardPagingPolicy.INCREASE_RANGE);
//		styleList.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.BOUND_TO_SELECTION);

		final MultiSelectionModel<String> selectionModel = new MultiSelectionModel<String>();
		styleList.setSelectionModel(selectionModel);
		selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
			public void onSelectionChange(SelectionChangeEvent event) {
				articleTypeDatabase.setStyleFilter(selectionModel.getSelectedSet());
			}
		});
		clearButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				selectionModel.clear();
			}
		});

		articleTypeDatabase.addStyleDisplay(styleList);
		styleList.addStyleName("styleList");
		panel.add(styleList);

		return panel;
	}

	private Panel createBrandPanel(final ArticleTypeDatabase articleTypeDatabase) {
		VerticalPanel panel = new VerticalPanel();

		brandHeaderPanel = new HorizontalPanel();
		brandHeaderPanel.addStyleName("filterHeader");
		Button clearButton = new Button("clr");
		brandHeaderPanel.add(clearButton);
		Label label = new Label("Marke");
		label.addStyleName("filterLabel");
		brandHeaderPanel.add(label);
		panel.add(brandHeaderPanel);

		StyleCell styleCell = new StyleCell();
		CellList<String> styleList = new CellList<String>(styleCell, GWT.<StyleListResources> create(StyleListResources.class));
		styleList.setPageSize(30);
//		styleList.setKeyboardPagingPolicy(KeyboardPagingPolicy.INCREASE_RANGE);
//		styleList.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.BOUND_TO_SELECTION);

		final MultiSelectionModel<String> selectionModel = new MultiSelectionModel<String>();
		styleList.setSelectionModel(selectionModel);
		selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
			public void onSelectionChange(SelectionChangeEvent event) {
				articleTypeDatabase.setBrandFilter(selectionModel.getSelectedSet());
			}
		});
		clearButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				selectionModel.clear();
			}
		});

		articleTypeDatabase.addBrandDisplay(styleList);
		styleList.addStyleName("styleList");
		panel.add(styleList);

		return panel;
	}

	private Panel createColorPanel(final ArticleTypeDatabase articleTypeDatabase) {
		VerticalPanel panel = new VerticalPanel();

		colorHeaderPanel = new HorizontalPanel();
		colorHeaderPanel.addStyleName("filterHeader");
		Button clearButton = new Button("clr");
		colorHeaderPanel.add(clearButton);
		Label label = new Label("Farbe");
		label.addStyleName("filterLabel");
		colorHeaderPanel.add(label);
		panel.add(colorHeaderPanel);

		StyleCell styleCell = new StyleCell();
		CellList<String> styleList = new CellList<String>(styleCell, GWT.<StyleListResources> create(StyleListResources.class));
		styleList.setPageSize(30);
//		styleList.setKeyboardPagingPolicy(KeyboardPagingPolicy.INCREASE_RANGE);
//		styleList.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.BOUND_TO_SELECTION);

		final MultiSelectionModel<String> selectionModel = new MultiSelectionModel<String>();
		styleList.setSelectionModel(selectionModel);
		selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
			public void onSelectionChange(SelectionChangeEvent event) {
				articleTypeDatabase.setColorFilter(selectionModel.getSelectedSet());
			}
		});
		clearButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				selectionModel.clear();
			}
		});

		articleTypeDatabase.addColorDisplay(styleList);
		styleList.addStyleName("styleList");
		panel.add(styleList);

		return panel;
	}

	private Panel createSizePanel(final ArticleTypeDatabase articleTypeDatabase) {
		VerticalPanel panel = new VerticalPanel();

		sizeHeaderPanel = new HorizontalPanel();
		sizeHeaderPanel.addStyleName("filterHeader");
		// Button clearButton = new Button("clr");
		// sizeHeaderPanel.add(clearButton);
		Label label = new Label("Größe");
		label.addStyleName("filterLabel");
		sizeHeaderPanel.add(label);
		panel.add(sizeHeaderPanel);

		HorizontalPanel cellListPanel = new HorizontalPanel();
		panel.add(cellListPanel);
		
		StyleCell styleCell = new StyleCell();
		CellList<String> styleList = new CellList<String>(styleCell, GWT.<StyleListResources> create(StyleListResources.class));
		styleList.setPageSize(30);

		final MultiSelectionModel<String> selectionModel = new MultiSelectionModel<String>();
		styleList.setSelectionModel(selectionModel);
		selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
			public void onSelectionChange(SelectionChangeEvent event) {
				articleTypeDatabase.setSizeFilter(selectionModel.getSelectedSet());
			}
		});
		cellListPanel.add(styleList);
		articleTypeDatabase.addSizeDisplay(styleList);
		styleList.addStyleName("sizeList");

		
		styleList = new CellList<String>(styleCell, GWT.<StyleListResources> create(StyleListResources.class));
		styleList.setPageSize(30);
		final MultiSelectionModel<String> selectionModel2 = new MultiSelectionModel<String>();
		styleList.setSelectionModel(selectionModel2);
		selectionModel2.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
			public void onSelectionChange(SelectionChangeEvent event) {
				articleTypeDatabase.setSizeFilter(selectionModel2.getSelectedSet());
			}
		});
		cellListPanel.add(styleList);
		articleTypeDatabase.addSizeDisplay(styleList);
		styleList.addStyleName("sizeList");

		
		styleList = new CellList<String>(styleCell, GWT.<StyleListResources> create(StyleListResources.class));
		styleList.setPageSize(30);
		final MultiSelectionModel<String> selectionModel3 = new MultiSelectionModel<String>();
		styleList.setSelectionModel(selectionModel3);
		selectionModel3.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
			public void onSelectionChange(SelectionChangeEvent event) {
				articleTypeDatabase.setSizeFilter(selectionModel3.getSelectedSet());
			}
		});
		articleTypeDatabase.addSizeDisplay(styleList);
		styleList.addStyleName("sizeList");
		cellListPanel.add(styleList);

		return panel;
	}

	ArticleTypeDetailPopup articleTypeDetailPopup;
	NoSelectionModel<ArticleType> articleTypeSelectionModel;
	CellList<ArticleType> articleTypeList;

	private Panel createArticleTypeList(ArticleTypeDatabase articleTypeDatabase) {
		VerticalPanel panel = new VerticalPanel();

		articleHeaderPanel = new HorizontalPanel();
		articleHeaderPanel.addStyleName("filterHeader");
		panel.add(articleHeaderPanel);
		Label label = new Label("Artikel");
		label.addStyleName("filterLabel");
		articleHeaderPanel.add(label);
		panel.add(articleHeaderPanel);

		ArticleTypeCell articleTypeCell = new ArticleTypeCell();
		articleTypeList = new CellList<ArticleType>(articleTypeCell, GWT.<StyleListResources> create(StyleListResources.class));
		articleTypeList.setPageSize(1000);
		// articleTypeList.setKeyboardPagingPolicy(KeyboardPagingPolicy.INCREASE_RANGE);
		// articleTypeList.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.BOUND_TO_SELECTION);
		articleTypeSelectionModel = new NoSelectionModel<ArticleType>();
		articleTypeList.setSelectionModel(articleTypeSelectionModel);
		articleTypeSelectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
			public void onSelectionChange(SelectionChangeEvent event) {
				articleTypeDetailPopup.showPopup(articleTypeSelectionModel.getLastSelectedObject());
			}
		});

		articleTypeDatabase.addArticleTypeDisplay(articleTypeList);
		// articleTypeList.addStyleName("articleTypeList");

		ScrollPanel articlePanel = new ScrollPanel(articleTypeList);
		// articlePanel.set
		articlePanel.setSize("400px", "600px");
		panel.add(articlePanel);
		// panel.addStyleName("scrollable");
		panel.addStyleName("articleTypeList");
		articleTypeDetailPopup = new ArticleTypeDetailPopup();

		return panel;
	}
	
}
// Button b1 = new Button("button 1");
// mainPanel.add(b1);
// Button b2 = new Button("button 2");
// mainPanel.add(b2);
// Button b3 = new Button("button 3");
// mainPanel.add(b3);

// final Button sendButton = new Button("Send");
// final TextBox nameField = new TextBox();
// nameField.setText("GWT User");
// final Label errorLabel = new Label();
//
// // We can add style names to widgets
// sendButton.addStyleName("sendButton");
//
// // Add the nameField and sendButton to the RootPanel
// // Use RootPanel.get() to get the entire body element
// RootPanel.get("nameFieldContainer").add(nameField);
// RootPanel.get("sendButtonContainer").add(sendButton);
// RootPanel.get("errorLabelContainer").add(errorLabel);
//
// // Focus the cursor on the name field when the app loads
// nameField.setFocus(true);
// nameField.selectAll();
//
// // Create the popup dialog box
// final DialogBox dialogBox = new DialogBox();
// dialogBox.setText("Remote Procedure Call");
// dialogBox.setAnimationEnabled(true);
// final Button closeButton = new Button("Close");
// // We can set the id of a widget by accessing its Element
// closeButton.getElement().setId("closeButton");
// final Label textToServerLabel = new Label();
// final HTML serverResponseLabel = new HTML();
// VerticalPanel dialogVPanel = new VerticalPanel();
// dialogVPanel.addStyleName("dialogVPanel");
// dialogVPanel.add(new HTML("<b>Sending name to the server:</b>"));
// dialogVPanel.add(textToServerLabel);
// dialogVPanel.add(new HTML("<br><b>Server replies:</b>"));
// dialogVPanel.add(serverResponseLabel);
// dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
// dialogVPanel.add(closeButton);
// dialogBox.setWidget(dialogVPanel);
//
// // Add a handler to close the DialogBox
// closeButton.addClickHandler(new ClickHandler() {
// public void onClick(ClickEvent event) {
// dialogBox.hide();
// sendButton.setEnabled(true);
// sendButton.setFocus(true);
// }
// });
//
// // Create a handler for the sendButton and nameField
// class MyHandler implements ClickHandler, KeyUpHandler {
// /**
// * Fired when the user clicks on the sendButton.
// */
// public void onClick(ClickEvent event) {
// sendNameToServer();
// }
//
// /**
// * Fired when the user types in the nameField.
// */
// public void onKeyUp(KeyUpEvent event) {
// if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
// sendNameToServer();
// }
// }
//
// /**
// * Send the name from the nameField to the server and wait for a
// response.
// */
// private void sendNameToServer() {
// // First, we validate the input.
// errorLabel.setText("");
// String textToServer = nameField.getText();
// if (!FieldVerifier.isValidName(textToServer)) {
// errorLabel.setText("Please enter at least four characters");
// return;
// }
//
// // Then, we send the input to the server.
// sendButton.setEnabled(false);
// textToServerLabel.setText(textToServer);
// serverResponseLabel.setText("");
// greetingService.greetServer(textToServer,
// new AsyncCallback<String>() {
// public void onFailure(Throwable caught) {
// // Show the RPC error message to the user
// dialogBox
// .setText("Remote Procedure Call - Failure");
// serverResponseLabel
// .addStyleName("serverResponseLabelError");
// serverResponseLabel.setHTML(SERVER_ERROR);
// dialogBox.center();
// closeButton.setFocus(true);
// }
//
// public void onSuccess(String result) {
// dialogBox.setText("Remote Procedure Call");
// serverResponseLabel
// .removeStyleName("serverResponseLabelError");
// serverResponseLabel.setHTML(result);
// dialogBox.center();
// closeButton.setFocus(true);
// }
// });
// }
// }
//
// // Add a handler to send the name to the server
// MyHandler handler = new MyHandler();
// sendButton.addClickHandler(handler);
// nameField.addKeyUpHandler(handler);
