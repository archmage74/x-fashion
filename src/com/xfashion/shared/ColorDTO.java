package com.xfashion.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ColorDTO extends FilterCellData<Long> implements IsSerializable {
	
	public static String ICON_PREFIX_COLOR = "color";
	
	
	public ColorDTO() {
		setIconPrefix(ICON_PREFIX_COLOR);
	}
	
	public ColorDTO(String name, int sortIndex) {
		setName(name);
		setSortIndex(sortIndex);
		setIconPrefix(ICON_PREFIX_COLOR);
	}

	public boolean equals(Object o) {
		if (o == null) return false;
		if (!(o instanceof ColorDTO)) return false;
		ColorDTO s = (ColorDTO) o;
		boolean equal = true;
		equal &= attributeEquals(getName(), s.getName());
		equal &= attributeEquals(getSortIndex(), s.getSortIndex());
		return equal;
	}

}
