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
import com.xfashion.shared.BrandDTO;

@PersistenceCapable
public class Brands {
	
	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;
	
	@Persistent
	@Order(extensions = @Extension(vendorName="datanucleus", key="list-ordering", value="sortIndex asc"))
	private List<Brand> brands;
	
	public Brands() {
		this.brands = new ArrayList<Brand>();
	}
	
	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}

	public List<BrandDTO> getDtos() {
		List<BrandDTO> dtos = new ArrayList<BrandDTO>(getBrands().size());
		for (Brand o : getBrands()) {
			BrandDTO dto = o.createDTO();
			dtos.add(dto);
		}
		return dtos;
	}

	public void update(List<BrandDTO> dtos) {
		List<Brand> toRemove = new ArrayList<Brand>(brands);
		List<Brand> toAdd = new ArrayList<Brand>();
		int idx = 0;
		for (BrandDTO dto : dtos) {
			Brand item = findItem(brands, dto);
			if (item == null) {
				item = new Brand(dto);
				toAdd.add(item);
			} else {
				toRemove.remove(item);
				item.updateFromDTO(dto);
			}
			item.setSortIndex(idx++);
		}
		for (Brand item : toRemove) {
			brands.remove(item);
		}
		this.brands.addAll(toAdd);
	}

	private Brand findItem(List<Brand> list, BrandDTO dto) {
		for (Brand item : list) {
			if (dto.getKey() != null && KeyFactory.stringToKey(dto.getKey()).equals(item.getKey())) {
				return item;
			}
		}
		return null;
	}

	public List<Brand> getBrands() {
		return brands;
	}

	public void setBrands(List<Brand> brands) {
		this.brands = brands;
	}

}
