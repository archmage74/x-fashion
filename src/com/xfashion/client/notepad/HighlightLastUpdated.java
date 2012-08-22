package com.xfashion.client.notepad;

import java.util.Date;

import com.xfashion.shared.ArticleAmountDTO;

public class HighlightLastUpdated {

	public static final long HIGHLIGHT_TIME = 5000;

	protected String lastUpdatedArticleTypeKey = null;
	protected Date lastUpdatedTime = null;

	public String getLastUpdatedArticleTypeKey() {
		return lastUpdatedArticleTypeKey;
	}

	public void setLastUpdatedArticleTypeKey(String lastUpdatedArticleTypeKey) {
		this.lastUpdatedArticleTypeKey = lastUpdatedArticleTypeKey;
		this.lastUpdatedTime = new Date();
	}

	public boolean isLastUpdated(ArticleAmountDTO articleAmount) {
		Date now = new Date();
		if (lastUpdatedTime != null && lastUpdatedArticleTypeKey != null && lastUpdatedTime.getTime() + HIGHLIGHT_TIME > now.getTime()
				&& lastUpdatedArticleTypeKey.equals(articleAmount.getArticleTypeKey())) {
			return true;
		} else {
			return false;
		}
	}

}
