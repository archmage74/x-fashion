package com.xfashion.server.img;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.Order;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.images.ImagesService;
import com.xfashion.shared.img.ArticleTypeImageDTO;

@PersistenceCapable
public class Images {
	
	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;
	
	@Persistent
	@Order(extensions = @Extension(vendorName="datanucleus", key="list-ordering", value="name asc"))
	private List<ArticleTypeImage> articleTypeImages;
	
	public List<ArticleTypeImageDTO> getArticleTypeImageDtos(ImagesService imagesService) {
		List<ArticleTypeImageDTO> dtos = new ArrayList<ArticleTypeImageDTO>(getArticleTypeImages().size());
		for (ArticleTypeImage o : getArticleTypeImages()) {
			ArticleTypeImageDTO dto = o.createDTO(imagesService);
			dtos.add(dto);
		}
		return dtos;
	}

	public Key getKey() {
		return key;
	}

	public List<ArticleTypeImage> getArticleTypeImages() {
		return articleTypeImages;
	}

}
