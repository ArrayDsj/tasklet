package net.wisedream.hiapk.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class Uri {

	public static String encode(String uri) {
		return encode(uri, "UTF-8");
	}
	
	public static String encode(String uri, String charset){
		try {
			String str = URLEncoder.encode(uri, charset);
			return str.replace("+", "%20");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e.getCause());
		}
	}
}
