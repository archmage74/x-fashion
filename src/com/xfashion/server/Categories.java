package com.xfashion.server;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.Order;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.xfashion.shared.CategoryDTO;

@PersistenceCapable
public class Categories {
	
	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;
	
	@Persistent
	@Order(extensions = @Extension(vendorName="datanucleus", key="list-ordering", value="sortIndex asc"))
	private List<Category> categories;
	
	
	private transient HashSet<Integer> freeCategoryNumbersCache = null;
	
	public Categories() {
		this.categories = new ArrayList<Category>();
	}
	
	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}

	public List<CategoryDTO> getDtos() {
		List<CategoryDTO> dtos = new ArrayList<CategoryDTO>(getCategories().size());
		for (Category o : getCategories()) {
			CategoryDTO dto = o.createDTO();
			dtos.add(dto);
		}
		return dtos;
	}

	public void update(List<CategoryDTO> dtos) {
		List<Category> toRemove = new ArrayList<Category>(categories);
		List<Category> toAdd = new ArrayList<Category>();
		int idx = 0;
		for (CategoryDTO dto : dtos) {
			Category item = findItem(categories, dto);
			if (item == null) {
				item = new Category(dto);
				if (item.getCategoryNumber() == null) {
					item.setCategoryNumber(getFreeCategoryNumber());
				}
				toAdd.add(item);
			} else {
				toRemove.remove(item);
				item.updateFromDTO(dto);
			}
			item.setSortIndex(idx++);
		}
		for (Category item : toRemove) {
			categories.remove(item);
		}
		this.categories.addAll(toAdd);
	}

	private Integer getFreeCategoryNumber() {
		if (freeCategoryNumbersCache == null) {
			freeCategoryNumbersCache = new HashSet<Integer>();
			for (int i = 0; i < 20; i++) {
				freeCategoryNumbersCache.add(i);
			}
			for (Category c : getCategories()) {
				freeCategoryNumbersCache.remove(c.getCategoryNumber());
			}
		}
		if (freeCategoryNumbersCache.size() == 0) {
			throw new RuntimeException("No free category numbers.");
		}
		Iterator<Integer> iterator = freeCategoryNumbersCache.iterator();
		Integer free = iterator.next();
		iterator.remove();
		return free;
	}

	private Category findItem(List<Category> list, CategoryDTO dto) {
		for (Category item : list) {
			if (dto.getKey() != null && KeyFactory.stringToKey(dto.getKey()).equals(item.getKey())) {
				return item;
			}
		}
		return null;
	}

	public List<Category> getCategories() {
		return categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}

}
