package com.xfashion.server.img;

import java.util.List;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.Order;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable
public class Images {
	
	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;
	
	@Persistent
	@Order(extensions = @Extension(vendorName="datanucleus", key="list-ordering", value="name asc"))
	private List<ArticleTypeImage> articleTypeImages;
	
	public Key getKey() {
		return key;
	}

	public List<ArticleTypeImage> getArticleTypeImages() {
		return articleTypeImages;
	}

}
