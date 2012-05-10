package com.xfashion.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class SizeDTO extends FilterCellData<Long> implements IsSerializable {
	
	public static String ICON_PREFIX_SIZE = "size";
	
	public SizeDTO() {
		setIconPrefix(ICON_PREFIX_SIZE);
	}
	
	public SizeDTO(String name, int sortIndex) {
		setName(name);
		setSortIndex(sortIndex);
		setIconPrefix(ICON_PREFIX_SIZE);
	}
	
	public boolean equals(Object o) {
		if (o == null) return false;
		if (!(o instanceof SizeDTO)) return false;
		SizeDTO s = (SizeDTO) o;
		boolean equal = true;
		equal &= attributeEquals(getName(), s.getName());
		equal &= attributeEquals(getSortIndex(), s.getSortIndex());
		return equal;
	}

}
