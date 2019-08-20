package com.app.modules.common.utils;

public class BusinessUtil {
	public static String removeExtraComma(String text){
		text = text == null ? "" : text;
		String regex = ".*,\\).*";
		return  text.replaceAll(regex, ")");
	}
}