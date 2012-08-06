package com.xfashion.client.user;

import java.util.Comparator;

import com.xfashion.shared.UserDTO;

public class UserByShortName implements Comparator<UserDTO> {

	@Override
	public int compare(UserDTO o1, UserDTO o2) {
		if (o1 == o2) {
			return 0;
		}
		if (o1 == null || o1.getShop() == null || o1.getShop().getShortName() == null) {
			return -1;
		}
		if (o2 == null || o2.getShop() == null || o2.getShop().getShortName() == null) {
			return 1;
		}
		return o1.getShop().getShortName().compareTo(o2.getShop().getShortName());
	}
	
}
