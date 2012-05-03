package com.xfashion.client.notepad;

import java.util.Comparator;

import com.xfashion.shared.ArticleTypeDTO;

public class NotepadArticleComparator implements Comparator<ArticleTypeDTO> {

	@Override
	public int compare(ArticleTypeDTO o1, ArticleTypeDTO o2) {
		if (o1.getProductNumber() < o2.getProductNumber()) return 0;
		if (o1.getProductNumber() < o2.getProductNumber()) return -1;
		return 1;
	}

}
