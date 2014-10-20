package net.wisedream.hiapk.util;

import java.util.LinkedList;
import java.util.List;


public class StrTools {
	
	public static String getcut(String content, String begin, String end,int isback) {
		if(content==null)
			return "";
		content=isback>0?getrev(content):content;
		begin=isback>0?getrev(begin):begin;
		end=isback>0?getrev(end):end;
		String temp = begin.length()==0?content:(content.indexOf(begin) >= 0?content.substring(content.indexOf(begin) + begin.length()):"");
		String news= end.length() == 0?temp:((end.length()>0&&temp.indexOf(end)>=0)?news = temp.substring(0, temp.indexOf(end)):"");
		return isback>0?getrev(news):news;
	}
	
	public static String getcuturl(String content, String begin, String end,int isback) {
		content=isback>0?getrev(content):content;
		begin=isback>0?getrev(begin):begin;
		end=isback>0?getrev(end):end;
		String temp = begin.length()==0?content:(content.indexOf(begin) >= 0?content.substring(content.indexOf(begin) + begin.length()):"");
		String news= end.length() == 0?temp:((end.length()>0&&temp.indexOf(end)>=0)?news = temp.substring(0, temp.indexOf(end)):"");
		return (isback>0?getrev(news):news).trim().replaceAll("&amp","&");
	}
	
	public static String getrev(String str)
	{
		return new StringBuffer(str).reverse().toString();
	}
	/**
	 * 从cnt中截取循环截取begin与end之间的字符串
	 * @param cnt
	 * @param begin
	 * @param end
	 * @return
	 */
	public static List<String> getCutAll(String cnt, String begin, String end){
		List<String> result=new LinkedList<String>();
		String tmp=StrTools.getcut(cnt, begin, end, 0);
		while(!tmp.equals("")){
			result.add(tmp);
			cnt=StrTools.getcut(cnt, tmp, "", 0);
			tmp=StrTools.getcut(cnt, begin, end, 0);
		}
		return result;
	}
	public static boolean isEmpty(String str)
	{
		return str==null||str.trim().isEmpty();
	}
}
