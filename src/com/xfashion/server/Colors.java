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
import com.xfashion.shared.ColorDTO;

@PersistenceCapable
public class Colors {
	
	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;
	
	@Persistent
	@Order(extensions = @Extension(vendorName="datanucleus", key="list-ordering", value="sortIndex asc"))
	private List<Color> colors;
	
	public Colors() {
		this.colors = new ArrayList<Color>();
	}
	
	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}

	public List<ColorDTO> getDtos() {
		List<ColorDTO> dtos = new ArrayList<ColorDTO>(getColors().size());
		for (Color o : getColors()) {
			ColorDTO dto = o.createDTO();
			dtos.add(dto);
		}
		return dtos;
	}

	public void update(List<ColorDTO> dtos) {
		List<Color> toRemove = new ArrayList<Color>(colors);
		List<Color> toAdd = new ArrayList<Color>();
		int idx = 0;
		for (ColorDTO dto : dtos) {
			Color item = findItem(colors, dto);
			if (item == null) {
				item = new Color(dto);
				toAdd.add(item);
			} else {
				toRemove.remove(item);
				item.updateFromDTO(dto);
			}
			item.setSortIndex(idx++);
		}
		for (Color item : toRemove) {
			colors.remove(item);
		}
		this.colors.addAll(toAdd);
	}

	private Color findItem(List<Color> list, ColorDTO dto) {
		for (Color item : list) {
			if (dto.getKey() != null && KeyFactory.stringToKey(dto.getKey()).equals(item.getKey())) {
				return item;
			}
		}
		return null;
	}

	public List<Color> getColors() {
		return colors;
	}

	public void setColors(List<Color> colors) {
		this.colors = colors;
	}

}
