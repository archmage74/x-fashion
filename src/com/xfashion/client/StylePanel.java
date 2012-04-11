package com.xfashion.client;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.SelectionChangeEvent;

public class StylePanel {

	private PanelMediator panelMediator;

	private Panel styleHeaderPanel; 
	
	private String headerStyle;
	
	private CreateStylePopup createStylePopup;

	public StylePanel(PanelMediator panelMediator) {
		this.panelMediator = panelMediator;
		panelMediator.setStylePanel(this);
	}
	
	public Panel createPanel(ListDataProvider<StyleCellData> styleProvider) {
		createStylePopup = new CreateStylePopup(panelMediator);
		
		VerticalPanel panel = new VerticalPanel();

		styleHeaderPanel = new HorizontalPanel();
		styleHeaderPanel.addStyleName("filterHeader");
		Label label = new Label("Stil");
		label.addStyleName("filterLabel");
		styleHeaderPanel.add(label);
		Button addStyleButton = new Button("+");
		styleHeaderPanel.add(addStyleButton);
		panel.add(styleHeaderPanel);

		StyleCell styleCell = new StyleCell();
		final CellList<StyleCellData> styleList = new CellList<StyleCellData>(styleCell, GWT.<StyleListResources> create(StyleListResources.class));
		styleList.setPageSize(30);

		final MultiSelectionModel<StyleCellData> selectionModel = new MultiSelectionModel<StyleCellData>();
		styleList.setSelectionModel(selectionModel);
		selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
			public void onSelectionChange(SelectionChangeEvent event) {
				panelMediator.setSelectedStyles(selectionModel.getSelectedSet());
			}
		});
//		clearButton.addClickHandler(new ClickHandler() {
//			@Override
//			public void onClick(ClickEvent event) {
//				selectionModel.clear();
//			}
//		});

		addStyleButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				createStylePopup.showForCategory();
			}
		});
		label.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				selectionModel.clear();
				// panelMediator.setSelectedStyles(selectionModel.getSelectedSet());
			}
		});

		styleProvider.addDataDisplay(styleList);
		styleList.addStyleName("styleList");
		panel.add(styleList);

		return panel;
	}

	public void setHeaderStyle(String style) {
		if (headerStyle != null) {
			styleHeaderPanel.removeStyleName(headerStyle);
		}
		headerStyle = style;
		if (headerStyle != null) {
			styleHeaderPanel.addStyleName(headerStyle);
		}
	}

	public PanelMediator getPanelMediator() {
		return panelMediator;
	}

	class StyleCell extends AbstractCell<StyleCellData> {
		@Override
		public void render(Context context, StyleCellData style, SafeHtmlBuilder sb) {
			if (style == null) {
				return;
			}
			style.render(sb);
		}
	}

}
