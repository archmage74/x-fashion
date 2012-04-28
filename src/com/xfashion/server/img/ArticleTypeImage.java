package com.xfashion.server.img;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.utils.SystemProperty;
import com.google.gwt.user.client.rpc.IsSerializable;
import com.xfashion.shared.img.ArticleTypeImageDTO;

@PersistenceCapable
public class ArticleTypeImage implements IsSerializable {

	private static final boolean PRODUCTION_MODE = SystemProperty.environment.value() == SystemProperty.Environment.Value.Production;

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;

	@Persistent
	private String name;

	private String blobKey;

	public ArticleTypeImage() {

	}

	public ArticleTypeImage(ArticleTypeImageDTO dto) {
		updateFromDTO(dto);
		setId(dto.getId());

	}

	public void updateFromDTO(ArticleTypeImageDTO dto) {
		setName(dto.getName());
		setBlobKey(dto.getBlobKey());
	}

	public ArticleTypeImageDTO createDTO(ImagesService imagesService) {
		ArticleTypeImageDTO dto = new ArticleTypeImageDTO();
		dto.setId(getId());
		dto.setName(getName());
		dto.setBlobKey(getBlobKey());
		if (imagesService != null) {
			BlobKey key = new BlobKey(getBlobKey());
			String url = imagesService.getServingUrl(key);
			if (!PRODUCTION_MODE) {
				url = url.replaceAll("http://0.0.0.0:8888", "http://127.0.0.1:8888");
			}
			dto.setImageUrl(url);
		}
		return dto;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

}
