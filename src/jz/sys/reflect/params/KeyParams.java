package jz.sys.reflect.params;

import java.util.HashMap;
import java.util.Map;

import jz.sys.Sys;

public class KeyParams {

	private Map<String, Object> params;
	
	public KeyParams() {
		this.params = new HashMap<String, Object>();
	}
	
	public KeyParams set(String name, Object param) {
		this.params.put(name, param);
		return this;
	}
	
	@SuppressWarnings("unchecked")
	public<type> type get(String name) {
		try {
			return (type)this.params.get(name);
		} catch (ClassCastException e) {
			Sys.error("KeyParam [0] could not be cast!", e, name);
			return null;
		}
	}
	
	public boolean is(String name) {
		return this.params.containsKey(name); 
	}
	
}
