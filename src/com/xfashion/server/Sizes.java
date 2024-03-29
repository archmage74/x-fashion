package com.xfashion.server;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.Order;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.xfashion.shared.SizeDTO;

@PersistenceCapable
public class Sizes {
	
	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;
	
	@Persistent
	@Order(extensions = @Extension(vendorName="datanucleus", key="list-ordering", value="sortIndex asc"))
	private List<Size> sizes;
	
	public Sizes() {
		sizes = new ArrayList<Size>();
	}
	
	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}

	public List<Size> getSizes() {
		return sizes;
	}

	public List<SizeDTO> getDtos() {
		List<SizeDTO> dtos = new ArrayList<SizeDTO>(getSizes().size());
		for (Size o : getSizes()) {
			SizeDTO dto = o.createDTO();
			dtos.add(dto);
		}
		return dtos;
	}

	public void setSizes(List<Size> sizes) {
		this.sizes = sizes;
	}

	public void update(List<SizeDTO> dtos) {
		List<Size> toRemove = new ArrayList<Size>(sizes);
		List<Size> toAdd = new ArrayList<Size>();
		int idx = 0;
		for (SizeDTO dto : dtos) {
			Size item = findItem(sizes, dto);
			if (item == null) {
				item = new Size(dto);
				toAdd.add(item);
			} else {
				toRemove.remove(item);
				item.updateFromDTO(dto);
			}
			item.setSortIndex(idx++);
		}
		for (Size item : toRemove) {
			sizes.remove(item);
		}
		this.sizes.addAll(toAdd);
	}

	private Size findItem(List<Size> list, SizeDTO dto) {
		for (Size item : list) {
			if (dto.getKey() != null && KeyFactory.stringToKey(dto.getKey()).equals(item.getKey())) {
				return item;
			}
		}
		return null;
	}
	
}
