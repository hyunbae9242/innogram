package com.innogram.utils;

public class StringUtils {
	public static boolean isBlank(String str) {
		if(str != null) {
			if(!"".equals(str)) {
				return false;
			}
		}
		return true;
	}
}
