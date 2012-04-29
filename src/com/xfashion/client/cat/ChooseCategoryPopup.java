package com.xfashion.client.cat;

import com.xfashion.client.ChooseAttributePopup;
import com.xfashion.client.FilterPanel;
import com.xfashion.client.PanelMediator;
import com.xfashion.shared.CategoryDTO;

public class ChooseCategoryPopup extends ChooseAttributePopup<CategoryDTO> {

	public ChooseCategoryPopup(PanelMediator panelMediator) {
		super(panelMediator);
	}

	@Override
	public FilterPanel<CategoryDTO> getPanel() {
		return panelMediator.getCategoryPanel();
	}

}
