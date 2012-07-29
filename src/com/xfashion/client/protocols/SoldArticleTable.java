package com.xfashion.client.protocols;

import com.google.gwt.cell.client.SafeHtmlCell;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.xfashion.client.Formatter;
import com.xfashion.client.at.ArticleTable;
import com.xfashion.client.at.GetSellPriceFromSoldArticleStrategy;
import com.xfashion.client.at.ProvidesArticleFilter;
import com.xfashion.shared.SoldArticleDTO;

public class SoldArticleTable extends ArticleTable<SoldArticleDTO> {

	protected Formatter formatter;

	public SoldArticleTable(ProvidesArticleFilter provider, GetSellPriceFromSoldArticleStrategy getPriceStrategy) {
		super(provider, getPriceStrategy);
		this.formatter = Formatter.getInstance();
	}

	protected void addNavColumns(CellTable<SoldArticleDTO> cellTable) {
		cellTable.addColumn(createOriginalSellPriceColumn());
		cellTable.addColumn(createSellDateColumn());
	}

	private Column<SoldArticleDTO, String> createSellDateColumn() {
		Column<SoldArticleDTO, String> amount = new Column<SoldArticleDTO, String>(new TextCell()) {
			@Override
			public String getValue(SoldArticleDTO sa) {
				return textMessages.sellStatisticDate(sa.getSellDate());
			}
		};
		amount.setCellStyleNames("articleSellDate");
		return amount;
	}

	protected Column<SoldArticleDTO, SafeHtml> createOriginalSellPriceColumn() {
		Column<SoldArticleDTO, SafeHtml> price = new Column<SoldArticleDTO, SafeHtml>(new SafeHtmlCell()) {
			@Override
			public SafeHtml getValue(SoldArticleDTO sa) {
				SafeHtmlBuilder sb = new SafeHtmlBuilder();
				String styles = concatStyles("articlePrice originalPrice", getAdditionalPriceStyles(sa));
				sb.appendHtmlConstant("<div class=\"" + styles + "\">");
				if (sa.getPromoKey() != null) {
					sb.appendEscaped("(");
					sb.appendEscaped(formatter.formatCentsToCurrency(sa.getOriginalSellPrice()));
					sb.appendEscaped(")");
				}
				sb.appendHtmlConstant("</div>");
				return sb.toSafeHtml();
			}
		};
		return price;
	}

}
