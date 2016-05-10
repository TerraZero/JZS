package jz.sys.gui.settings;

import java.util.ArrayList;
import java.util.List;

import jz.sys.Sys;
import jz.sys.utils.validates.TZUValidates;

public class TZGS {

	private static List<TZGSetting> settings;
	
	static {
		TZGS.init();
	}
	
	public static void init() {
		TZGS.settings = new ArrayList<TZGSetting>();
	}
	
	public static void add(TZGSetting... settings) {
		for (TZGSetting setting : settings) {
			TZGS.settings.add(setting);
		}
	}
	
	public static TZGSetting setting(String key) {
		for (TZGSetting setting : TZGS.settings) {
			if (setting.key().equals(key)) {
				return setting;
			}
		}
		TZGS.warn(key);
		return null;
	}
	
	public static int number(String key) {
		TZGSetting setting = TZGS.setting(key);
		
		if (setting != null) {
			if (TZUValidates.valid(setting.value(), "number")) {
				return Integer.parseInt(setting.value());
			}
		}
		return -1;
	}
	
	public static double decimal(String key) {
		TZGSetting setting = TZGS.setting(key);
		
		if (setting != null) {
			if (TZUValidates.valid(setting.value(), "decimal")) {
				return Double.parseDouble(setting.value());
			}
		}
		return -1.0;
	}
	
	public static String string(String key) {
		TZGSetting setting = TZGS.setting(key);
		
		if (setting != null) {
			return setting.value();
		}
		return null;
	}
	
	public static void set(String key, String value) {
		TZGSetting setting = TZGS.setting(key);
		
		if (setting != null) {
			setting.value(value);
		}
	}
	
	private static void warn(String key) {
		Sys.warn("The setting with key [0] is unknown!", key);
	}
	
}
