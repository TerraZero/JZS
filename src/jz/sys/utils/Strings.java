package jz.sys.utils;

import jz.sys.Sys;

public final class Strings {

	public static String join(String glue, String... strings) {
		StringBuilder sb = new StringBuilder();
		for (String s : strings) {
			sb.append(s).append(glue);
		}
		sb.setLength(sb.length() - glue.length());
		return sb.toString();
	}
	
	public static void print(String... strings) {
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		for (int i = 0; i < strings.length; i++) {
			sb.append("[").append(i).append("], ");
		}
		sb.setLength(sb.length() - 2);
		sb.append("}");
		Sys.log(sb.toString(), (Object[])strings);
	}
	
	public static boolean isIntern(String intern, String... hashtag) {
		for (String s : hashtag) {
			if (s.equals(intern)) return true;
		}
		return false;
	}
	
	public static String[] append(String[] array, String... appends) {
		if (appends == null) return array;
		String[] a = new String[array.length + appends.length];
		
		for (int i = 0; i < array.length; i++) {
			a[i] = array[i];
		}
		for (int i = 0; i < appends.length; i++) {
			a[i + array.length] = appends[i];
		}
		return a;
	}
	
	public static String[] prepend(String[] array, String... prepends) {
		if (prepends == null) return array;
		String[] a = new String[array.length + prepends.length];
		
		for (int i = 0; i < prepends.length; i++) {
			a[i] = prepends[i];
		}
		for (int i = 0; i < array.length; i++) {
			a[i + prepends.length] = array[i];
		}
		return a;
	}
	
}
