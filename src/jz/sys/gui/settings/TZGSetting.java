package jz.sys.gui.settings;

import jz.sys.Sys;
import jz.sys.utils.validates.TZUValidates;

public class TZGSetting {

	private String key;
	private String value;
	private String defaultValue;
	
	private String group;
	private String type;
	private String[] validates;
	
	public TZGSetting(String group, String key, String type, String defaultValue, String... validates) {
		this.group = group;
		this.key = key;
		this.type = type;
		this.defaultValue = defaultValue;
		this.validates = validates;
	}
	
	public TZGSetting key(String key) {
		this.key = key;
		return this;
	}
	
	public TZGSetting value(String value) {
		if (TZUValidates.valid(value, this.validates)) {
			this.value = value;
		} else {
			Sys.warn("The value [0] for key [1] in type [2] is not valid!", value, this.key, this.type);
		}
		return this;
	}
	
	public String key() {
		return this.key;
	}
	
	public String value() {
		if (this.value == null) return this.defaultValue;
		return this.value;
	}
	
	public String type() {
		return this.type;
	}
	
	public String[] validates() {
		return this.validates;
	}
	
	public String group() {
		return this.group;
	}
	
}
