package com.xfashion.shared.notepad;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ArticleAmountDTO implements IsSerializable {

	protected Long productNumber;
	
	protected Integer amount;

	public ArticleAmountDTO() {
		
	}
	
	public ArticleAmountDTO(Long productNumber, Integer amount) {
		this.productNumber = productNumber;
		this.amount = amount;
	}
	
	public Long getProductNumber() {
		return productNumber;
	}

	public void setProductNumber(Long productNumber) {
		this.productNumber = productNumber;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	
	public void increaseAmount() {
		this.amount ++;
	}

	public void increaseAmount(Integer amount) {
		this.amount += amount;
	}

	public void decreaseAmount(Integer amount) {
		this.amount -= amount;
	}

}
