package jz.sys;

import java.util.UUID;

import jz.sys.events.JZEHandler;
import jz.sys.module.Mod;
import jz.sys.module.api.SysModule;

public class Sys {
	
	public static JZEHandler eExit;
	public static JZEHandler eLogging;
	public static JZEHandler eLog;
	public static JZEHandler eWarn;
	public static JZEHandler eError;
	
	private static String session;
	
	static {
		Sys.eExit = new JZEHandler("exit");
		Sys.eLogging = new JZEHandler("logging");
		
		Sys.eLog = new JZEHandler("log", (event) -> {
			String message = event.get(0);
			Object[] tokens = event.get(1);

			System.out.println(Sys.placeholder(message, tokens));
		});
		
		Sys.eWarn = new JZEHandler("warn", (event) -> {
			String message = event.get(0);
			Object[] tokens = event.get(1);
			
			System.out.println("Warning: " + Sys.placeholder(message, tokens));
		});
		
		Sys.eError = new JZEHandler("error", (event) -> {
			String message = event.get(0);
			Exception e = event.get(1);
			Object[] tokens = event.get(2);
			
			System.err.println(Sys.placeholder(message, tokens));
			if (e != null) {
				e.printStackTrace();
			}
		});
		
		Sys.eLogging.subscriber(Sys.eLog, "log");
		Sys.eLogging.subscriber(Sys.eWarn, "warn");
		Sys.eLogging.subscriber(Sys.eError, "error");
	}
	
	public static void init() {
		Mod.get(SysModule.class).foreach((m) -> {
			m.hookInit();
		});
	}

	public static void log(String message, Object... tokens) {
		Sys.eLogging.fire("log", message, tokens);
	}
	
	public static void warn(String message, Object... tokens) {
		Sys.eLogging.fire("warn", message, tokens);
	}
	
	public static void error(String message, Exception e, Object... tokens) {
		Sys.eLogging.fire("error", message, e, tokens);
	}
	
	public static void exit() {
		Sys.exit(0);
	}
	
	public static void exit(int code) {
		Sys.log("System try to exit with code [0]!", code);
		Sys.eExit.fire("exit", code);
		Mod.get(SysModule.class).foreach((m) -> {
			m.hookExit(code);
		});
		Sys.log("System exited with code [0]!", code);
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
	
	public static String session() {
		if (Sys.session == null) {
			Sys.session = UUID.randomUUID().toString();
		}
		return Sys.session;
	}
	
}
