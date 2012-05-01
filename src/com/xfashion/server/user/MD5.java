package com.xfashion.server.user;

import com.google.gwt.util.tools.shared.Md5Utils;

public class MD5 {

	public static String getMD5Hash(String input) {
		byte[] bytes = input.getBytes();
		byte[] digest = Md5Utils.getMd5Digest(bytes);

		StringBuffer hexString = new StringBuffer();
		for (int i=0;i<digest.length;i++) {
		    hexString.append(Integer.toHexString(0xFF & digest[i]));
		}
		String hash = hexString.toString();

		return hash;
	}
}
