package jz.sys;

import jz.sys.events.JZHandler;

public class Sys {
	
	public static JZHandler eExit;
	
	static {
		Sys.eExit = new JZHandler();
	}

	public static void log(String message, Object... tokens) {
		System.out.println(Sys.placeholder(message, tokens));
	}
	
	public static void warn(String message, Object... tokens) {
		System.out.println("Warning: " + Sys.placeholder(message, tokens));
	}
	
	public static void error(String message, Exception e, Object... tokens) {
		System.err.println(Sys.placeholder(message, tokens));
		if (e != null) {
			e.printStackTrace();
		}
	}
	
	public static void exit(int code) {
		Sys.log("System exited with code [0]!", code);
		Sys.eExit.fire("exit", code);
		System.exit(code);
	}
	
	public static String placeholder(String message, Object... tokens) {
		String[] strings = new String[tokens.length];
		
		for (int i = 0; i < strings.length; i++) {
			if (tokens[i] != null) {
				strings[i] = tokens[i].toString();
			} else {
				strings[i] = null;
			}
		}
		return Sys.placeholder(message, strings);
	}
	
	public static String placeholder(String message, String... tokens) {
		if (tokens != null) {
			for (int i = 0; i < tokens.length; i++) {
				message = message.replaceAll("\\[" + i + "\\]", "'" + tokens[i] + "'");
			}
		}
		return message;
	}
	
}
