package com.xfashion.client.at.render;

import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.EventTarget;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.xfashion.client.at.ArticleDataProvider;
import com.xfashion.shared.ArticleTypeDTO;

public abstract class TwoButtonsCell<T> extends AbstractArticleTableCell<T> {

	protected ArticleDataProvider<T> provider;
	
	public TwoButtonsCell(ArticleDataProvider<T> provider) {
		super("click");
		this.provider = provider;
	}

	abstract protected void buttonOnePressed(ArticleTypeDTO articleType);
	
	abstract protected String buttonOneText();
	
	abstract protected void buttonTwoPressed(ArticleTypeDTO articleType);
	
	abstract protected String buttonTwoText();
	
	@Override
	public void onBrowserEvent(Context context, Element parent, T value, NativeEvent event, ValueUpdater<T> valueUpdater) {
		super.onBrowserEvent(context, parent, value, event, valueUpdater);
		if ("click".equals(event.getType())) {
			EventTarget eventTarget = event.getEventTarget();
			if (!Element.is(eventTarget)) {
				return;
			}
			if (parent.getChild(2).isOrHasChild(Element.as(eventTarget))) {
				if (parent.getChild(2).getChild(0).isOrHasChild(Element.as(eventTarget))) {
					ArticleTypeDTO at = provider.retrieveArticleType(value);
					buttonOnePressed(at);
				} 
				if (parent.getChild(2).getChild(1).isOrHasChild(Element.as(eventTarget))) {
					ArticleTypeDTO at = provider.retrieveArticleType(value);
					buttonTwoPressed(at);
				}
			}
		}
	}

	@Override
	public void render(Context context, T data, SafeHtmlBuilder sb) {
		sb.append(matrixTemplates.twoButtonsCell(buttonOneText(), buttonTwoText()));
	}

}
