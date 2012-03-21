package com.xfashion.client;

import com.google.gwt.user.client.ui.DecoratedPopupPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;

public class ArticleTypeDetailPopup {
	
	DecoratedPopupPanel popup;
	
	Image image;
	Label name;
	Label brand;
	Label category;
	Label style;
	Label size;
	Label color;
	
	public ArticleTypeDetailPopup() {
		popup = new DecoratedPopupPanel(true);
		popup.setGlassEnabled(true);
		popup.setAnimationEnabled(true);
		HorizontalPanel panel = new HorizontalPanel();
		
		image = createImage("", 1, 1);
		panel.add(image);
		
		Grid grid = new Grid(6, 2);
		grid.setWidget(0, 0, createLabel("Name:"));
		name = createLabel("");
		grid.setWidget(0, 1, name);
		grid.setWidget(1, 0, createLabel("Marke:"));
		brand = createLabel("");
		grid.setWidget(1, 1, brand);
		grid.setWidget(2, 0, createLabel("Kategorie:"));
		category = createLabel("");
		grid.setWidget(2, 1, category);
		grid.setWidget(3, 0, createLabel("Style:"));
		style = createLabel("");
		grid.setWidget(3, 1, style);
		grid.setWidget(4, 0, createLabel("Größe:"));
		size = createLabel("");
		grid.setWidget(4, 1, size);
		grid.setWidget(5, 0, createLabel("Farbe:"));
		color = createLabel("");
		grid.setWidget(5, 1, color);
		panel.add(grid);
		
		popup.add(panel);
	}
	
	private Label createLabel(String text) {
		Label label = new Label(text);
		return label;
	}
	
	private Image createImage(String url, int width, int height) {
		Image img = new Image(url);
		img.setWidth(width + "px");
		img.setHeight(height + "px");
		return img;
	}

	public void showPopup(ArticleType articleType) {
		if (articleType == null) {
			articleType = new ArticleType();
		}
		image.setUrl("Jeans2.jpg");
		image.setWidth(500 + "px");
		image.setHeight(602 + "px");
		
		name.setText(articleType.getName());
		brand.setText(articleType.getBrand());
		category.setText(articleType.getCategory());
		style.setText(articleType.getStyle());
		size.setText(articleType.getSize());
		color.setText(articleType.getColor());
		popup.center();
	}
}
