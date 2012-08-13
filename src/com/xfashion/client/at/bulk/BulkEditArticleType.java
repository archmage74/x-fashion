package com.xfashion.client.at.bulk;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import com.xfashion.shared.ArticleTypeDTO;
import com.xfashion.shared.PriceChangeDTO;

public class BulkEditArticleType {

	List<ArticleTypeDTO> bulk;

	private String sourceName;
	private String sourceCategoryKey;
	private String sourceStyleKey;
	private String sourceBrandKey;
	private String sourceSizeKey;
	private String sourceColorKey;
	private Integer sourceBuyPrice;
	private Integer sourceSellPriceAt;
	private Integer sourceSellPriceDe;
	private String sourceImageKey;
	private String sourceImageUrl;

	private String targetName;
	private String targetCategoryKey;
	private String targetStyleKey;
	private String targetBrandKey;
	private String targetSizeKey;
	private String targetColorKey;
	private Integer targetBuyPrice;
	private Integer targetSellPriceAt;
	private Integer targetSellPriceDe;
	private String targetImageKey;
	private String targetImageUrl;

	public BulkEditArticleType(List<ArticleTypeDTO> articleTypes) {
		extractSources(articleTypes);
	}

	public String getTargetName() {
		return targetName;
	}

	public void setTargetName(String targetName) {
		this.targetName = targetName;
	}

	public String getTargetCategoryKey() {
		return targetCategoryKey;
	}

	public void setTargetCategoryKey(String targetCategoryKey) {
		this.targetCategoryKey = targetCategoryKey;
	}

	public String getTargetStyleKey() {
		return targetStyleKey;
	}

	public void setTargetStyleKey(String targetStyleKey) {
		this.targetStyleKey = targetStyleKey;
	}

	public String getTargetBrandKey() {
		return targetBrandKey;
	}

	public void setTargetBrandKey(String targetBrandKey) {
		this.targetBrandKey = targetBrandKey;
	}

	public String getTargetSizeKey() {
		return targetSizeKey;
	}

	public void setTargetSizeKey(String targetSizeKey) {
		this.targetSizeKey = targetSizeKey;
	}

	public String getTargetColorKey() {
		return targetColorKey;
	}

	public void setTargetColorKey(String targetColorKey) {
		this.targetColorKey = targetColorKey;
	}

	public Integer getTargetBuyPrice() {
		return targetBuyPrice;
	}

	public void setTargetBuyPrice(Integer targetBuyPrice) {
		this.targetBuyPrice = targetBuyPrice;
	}

	public Integer getTargetSellPriceAt() {
		return targetSellPriceAt;
	}

	public void setTargetSellPriceAt(Integer targetSellPriceAt) {
		this.targetSellPriceAt = targetSellPriceAt;
	}

	public Integer getTargetSellPriceDe() {
		return targetSellPriceDe;
	}

	public void setTargetSellPriceDe(Integer targetSellPriceDe) {
		this.targetSellPriceDe = targetSellPriceDe;
	}

	public String getTargetImageKey() {
		return targetImageKey;
	}

	public void setTargetImageKey(String targetImageKey) {
		this.targetImageKey = targetImageKey;
	}

	public String getTargetImageUrl() {
		return targetImageUrl;
	}

	public void setTargetImageUrl(String targetImageUrl) {
		this.targetImageUrl = targetImageUrl;
	}

	public String getSourceName() {
		return sourceName;
	}

	public String getSourceCategoryKey() {
		return sourceCategoryKey;
	}

	public String getSourceStyleKey() {
		return sourceStyleKey;
	}

	public String getSourceBrandKey() {
		return sourceBrandKey;
	}

	public String getSourceSizeKey() {
		return sourceSizeKey;
	}

	public String getSourceColorKey() {
		return sourceColorKey;
	}

	public Integer getSourceBuyPrice() {
		return sourceBuyPrice;
	}

	public Integer getSourceSellPriceAt() {
		return sourceSellPriceAt;
	}

	public Integer getSourceSellPriceDe() {
		return sourceSellPriceDe;
	}

	public String getSourceImageKey() {
		return sourceImageKey;
	}

	public String getSourceImageUrl() {
		return sourceImageUrl;
	}

	private void extractSources(List<ArticleTypeDTO> articleTypes) {
		AttributeHelper helper = new AttributeHelper();
		sourceBrandKey = helper.extractSourceAttribute(articleTypes, new BrandKeyAccessor());
		sourceBuyPrice = helper.extractSourceAttribute(articleTypes, new BuyPriceAccessor());
		sourceCategoryKey = helper.extractSourceAttribute(articleTypes, new CategoryKeyAccessor());
		sourceColorKey = helper.extractSourceAttribute(articleTypes, new ColorKeyAccessor());
		sourceImageKey = helper.extractSourceAttribute(articleTypes, new ImageKeyAccessor());
		sourceImageUrl = helper.extractSourceAttribute(articleTypes, new ImageUrlAccessor());
		sourceName = helper.extractSourceAttribute(articleTypes, new NameAccessor());
		sourceSellPriceAt = helper.extractSourceAttribute(articleTypes, new SellPriceAtAccessor());
		sourceSellPriceDe = helper.extractSourceAttribute(articleTypes, new SellPriceDeAccessor());
		sourceSizeKey = helper.extractSourceAttribute(articleTypes, new SizeKeyAccessor());
		sourceStyleKey = helper.extractSourceAttribute(articleTypes, new StyleKeyAccessor());
	}

	public Collection<PriceChangeDTO> applyChanges(List<ArticleTypeDTO> articleTypes) {
		HashMap<String, PriceChangeDTO> priceChanges = new HashMap<String, PriceChangeDTO>();
		AttributeHelper helper = new AttributeHelper();
		helper.saveAttribute(articleTypes, new BrandKeyAccessor(), targetBrandKey);
		helper.saveAttribute(articleTypes, new BuyPriceAccessor(), targetBuyPrice);
		helper.saveAttribute(articleTypes, new CategoryKeyAccessor(), targetCategoryKey);
		helper.saveAttribute(articleTypes, new ColorKeyAccessor(), targetColorKey);
		helper.saveAttribute(articleTypes, new ImageKeyAccessor(), targetImageKey);
		helper.saveAttribute(articleTypes, new ImageUrlAccessor(), targetImageUrl);
		helper.saveAttribute(articleTypes, new NameAccessor(), targetName);
		helper.saveAttribute(articleTypes, priceChanges, new SellPriceAtAccessor(), targetSellPriceAt);
		helper.saveAttribute(articleTypes, priceChanges, new SellPriceDeAccessor(), targetSellPriceDe);
		helper.saveAttribute(articleTypes, new SizeKeyAccessor(), targetSizeKey);
		helper.saveAttribute(articleTypes, new StyleKeyAccessor(), targetStyleKey);
		return new ArrayList<PriceChangeDTO>(priceChanges.values());
	}
	
}
