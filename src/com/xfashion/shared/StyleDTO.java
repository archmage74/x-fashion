package com.xfashion.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class StyleDTO extends FilterCellData implements IsSerializable {
	
	public static String ICON_PREFIX_STYLE = "style";
	
	public StyleDTO() {
		setIconPrefix(ICON_PREFIX_STYLE);
	}
	
	public StyleDTO(String name, int sortIndex) {
		setName(name);
		setSortIndex(sortIndex);
		setIconPrefix(ICON_PREFIX_STYLE);
	}
	
	public boolean equals(Object o) {
		if (o == null) return false;
		if (!(o instanceof StyleDTO)) return false;
		StyleDTO s = (StyleDTO) o;
		boolean equal = true;
		equal &= attributeEquals(getName(), s.getName());
		equal &= attributeEquals(getSortIndex(), s.getSortIndex());
		return equal;
	}

}
