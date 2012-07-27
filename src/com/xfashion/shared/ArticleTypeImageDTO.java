package com.xfashion.shared;


public class ArticleTypeImageDTO extends DTO {
	
	public static final String IMAGE_OPTIONS_BIG = "=s400-c";
	
	public static final String IMAGE_OPTIONS_ICON = "=s64-c";

	private String name;
	
	private String blobKey;
	
	private String imageUrl;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getBlobKey() {
		return blobKey;
	}
	
	public void setBlobKey(String blobKey) {
		this.blobKey = blobKey;
	}
	
	public String getImageUrl() {
		return imageUrl;
	}
	
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

}
