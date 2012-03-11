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

	static class CategoryCell extends AbstractCell<String> {
		@Override
		public void render(Context context, String category, SafeHtmlBuilder sb) {
			if (category == null) {
				return;
			}
			sb.appendHtmlConstant("<div style=\"height:32px; margin-left:3px; margin-right:3px; font-size:20px;\">");
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
			sb.appendHtmlConstant("<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
			sb.appendHtmlConstant("<tr>");
			sb.appendHtmlConstant("<td width=\"30px\" align=\"center\" rowspan=\"3\">IMG</td>");
			sb.appendHtmlConstant("<td width=\"100px\" align=\"center\" style=\"border-bottom:1px solid silver;\">");
			sb.appendEscaped(articleType.getName());
			sb.appendHtmlConstant("</td><td width=\"100px\" align=\"center\" style=\"border-bottom:1px solid silver; border-left:1px solid silver; border-right:1px solid silver\">");
			sb.appendEscaped(articleType.getBrand());
			sb.appendHtmlConstant("</td><td width=\"100px\" align=\"center\" style=\"border-bottom:1px solid silver;\">");
			sb.appendEscaped(articleType.getCategory());
			sb.appendHtmlConstant("</td></tr>");
			sb.appendHtmlConstant("<tr><td align=\"center\">");
			sb.appendEscaped(articleType.getStyle());
			sb.appendHtmlConstant("</td><td align=\"center\" style=\"border-left:1px solid silver; border-right:1px solid silver\">");
			sb.appendEscaped(articleType.getSize());
			sb.appendHtmlConstant("</td><td align=\"center\">");
			sb.appendEscaped(articleType.getColor());
			sb.appendHtmlConstant("</td></tr></table>");
		}
	}

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		final Panel mainPanel = new HorizontalPanel();

		ArticleTypeDatabase articleTypeDatabase = new ArticleTypeDatabase();

		mainPanel.add(createCategoryPanel(articleTypeDatabase));
		mainPanel.add(createStylePanel(articleTypeDatabase));
		mainPanel.add(createArticleTypeList(articleTypeDatabase));

		RootPanel.get("mainPanelContainer").add(mainPanel);

	}

	private Panel createCategoryPanel(final ArticleTypeDatabase articleTypeDatabase) {
		VerticalPanel panel = new VerticalPanel();

		HorizontalPanel headerPanel = new HorizontalPanel();
		headerPanel.addStyleName("filterHeader");
		Button clearButton = new Button("clr");
		headerPanel.add(clearButton);
		Label label = new Label("Kategorie");
		label.addStyleName("filterLabel");
		headerPanel.add(label);
		panel.add(headerPanel);

		CategoryCell categoryCell = new CategoryCell();
		final CellList<String> categoryList = new CellList<String>(categoryCell, GWT.<CategoryListResources> create(CategoryListResources.class));
		categoryList.setPageSize(30);
		categoryList.setKeyboardPagingPolicy(KeyboardPagingPolicy.INCREASE_RANGE);
		categoryList.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.BOUND_TO_SELECTION);

		final SingleSelectionModel<String> selectionModel = new SingleSelectionModel<String>();
		categoryList.setSelectionModel(selectionModel);
		selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
			public void onSelectionChange(SelectionChangeEvent event) {
				articleTypeDatabase.setCategoryFilter(selectionModel.getSelectedObject());
			}
		});
		clearButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				articleTypeDatabase.setCategoryFilter(null);
				if (selectionModel.getSelectedObject() != null) {
					selectionModel.setSelected(selectionModel.getSelectedObject(), false);
				}
			}
		});
		articleTypeDatabase.addCategoryDisplay(categoryList);

		categoryList.addStyleName("categoryList");
		panel.add(categoryList);

		return panel;
	}

	private Panel createStylePanel(final ArticleTypeDatabase articleTypeDatabase) {
		VerticalPanel panel = new VerticalPanel();

		HorizontalPanel headerPanel = new HorizontalPanel();
		headerPanel.addStyleName("filterHeader");
		Button clearButton = new Button("clr");
		headerPanel.add(clearButton);
		Label label = new Label("Style");
		label.addStyleName("filterLabel");
		headerPanel.add(label);
		panel.add(headerPanel);

		StyleCell styleCell = new StyleCell();
		CellList<String> styleList = new CellList<String>(styleCell, GWT.<StyleListResources> create(StyleListResources.class));
		styleList.setPageSize(30);
		styleList.setKeyboardPagingPolicy(KeyboardPagingPolicy.INCREASE_RANGE);
		styleList.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.BOUND_TO_SELECTION);

		final SingleSelectionModel<String> selectionModel = new SingleSelectionModel<String>();
		styleList.setSelectionModel(selectionModel);
		selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
			public void onSelectionChange(SelectionChangeEvent event) {
				articleTypeDatabase.setStyleFilter(selectionModel.getSelectedObject());
			}
		});
		clearButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				articleTypeDatabase.setStyleFilter(null);
				if (selectionModel.getSelectedObject() != null) {
					selectionModel.setSelected(selectionModel.getSelectedObject(), false);
				}
			}
		});

		articleTypeDatabase.addStyleDisplay(styleList);
		styleList.addStyleName("styleList");
		panel.add(styleList);

		return panel;
	}

	Label popupLabel;
	DecoratedPopupPanel popup;

	private Panel createArticleTypeList(ArticleTypeDatabase articleTypeDatabase) {
		VerticalPanel panel = new VerticalPanel();

		HorizontalPanel headerPanel = new HorizontalPanel();
		headerPanel.addStyleName("filterHeader");
		panel.add(headerPanel);
		Label label = new Label("Artikel");
		label.addStyleName("filterLabel");
		headerPanel.add(label);
		panel.add(headerPanel);

		ArticleTypeCell articleTypeCell = new ArticleTypeCell();
		CellList<ArticleType> articleTypeList = new CellList<ArticleType>(articleTypeCell, GWT.<StyleListResources> create(StyleListResources.class));
		articleTypeList.setPageSize(30);
		articleTypeList.setKeyboardPagingPolicy(KeyboardPagingPolicy.INCREASE_RANGE);
		articleTypeList.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.BOUND_TO_SELECTION);
		final SingleSelectionModel<Object> selectionModel = new SingleSelectionModel<Object>();
		articleTypeList.setSelectionModel(selectionModel);
		selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
			public void onSelectionChange(SelectionChangeEvent event) {
				popupLabel.setText(selectionModel.getSelectedObject().toString());
				popup.show();
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

		popupLabel = new Label();
		popup = new DecoratedPopupPanel(true);
		popup.add(popupLabel);

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
