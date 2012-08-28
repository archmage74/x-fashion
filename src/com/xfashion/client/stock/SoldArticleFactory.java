package com.xfashion.client.stock;

import com.xfashion.client.at.IProvideArticleFilter;
import com.xfashion.shared.ArticleTypeDTO;
import com.xfashion.shared.CategoryDTO;
import com.xfashion.shared.ShopDTO;
import com.xfashion.shared.SoldArticleDTO;

public class SoldArticleFactory {

	protected IProvideArticleFilter provider;
	
	public SoldArticleFactory(IProvideArticleFilter provider) {
		this.provider = provider;
	}
	
	public SoldArticleDTO createSoldArticleDTO(ArticleTypeDTO articleType, Integer sellPrice, ShopDTO shop) {
		return createSoldArticleDTO(articleType, sellPrice, shop, 1);
	}
	
	public SoldArticleDTO createSoldArticleDTO(ArticleTypeDTO articleType, Integer sellPrice, ShopDTO shop, int amount) {
		SoldArticleDTO soldArticle = new SoldArticleDTO();
		soldArticle.setArticleTypeKey(articleType.getKey());
		soldArticle.setArticleName(articleType.getName());
		CategoryDTO category = provider.getCategoryProvider().resolveData(articleType.getCategoryKey());
		soldArticle.setCategory(category.getName());
		soldArticle.setStyle(category.getStyleByStyleKey(articleType.getStyleKey()).getName());
		soldArticle.setBrand(provider.getBrandProvider().resolveData(articleType.getBrandKey()).getName());
		soldArticle.setColor(provider.getColorProvider().resolveData(articleType.getColorKey()).getName());
		soldArticle.setSize(provider.getSizeProvider().resolveData(articleType.getSizeKey()).getName());
		soldArticle.setAmount(amount);
		soldArticle.setShopKey(shop.getKeyString());
		soldArticle.setShopName(shop.getName());
		soldArticle.setBuyPrice(articleType.getBuyPrice());
		soldArticle.setSellPrice(sellPrice);
		soldArticle.setOriginalSellPrice(sellPrice);
		return soldArticle;
	}
}
