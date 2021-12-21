package com.innogram.utils;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import com.google.gson.Gson;
import com.google.gson.JsonParser;

public class ParseUtils {
	private static Gson gson = new Gson();

	public static <T extends Object> T parseVOFromReqeust(HttpServletRequest request, Class<T> clazz) {
		JsonParser parser = new JsonParser();
		try {
			BufferedReader br = request.getReader();
			String line = null;
			StringBuffer sb = new StringBuffer();
			while((line = br.readLine()) != null) {
				sb.append(line);
			}
			return gson.fromJson(parser.parse(StringUtils.safeToString(sb)), clazz);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
