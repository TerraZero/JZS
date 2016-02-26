package jz.sys.reflect.params;

import jz.sys.Sys;
import jz.sys.reflect.Reflect;

public class Params {

	private Object[] params;
	
	public Params(Object... params) {
		this.params = params;
	}
	
	public int length() {
		return this.params.length;
	}
	
	@SuppressWarnings("unchecked")
	public<type> type get(int index) {
		try {
			return (type)this.params[index];
		} catch (ClassCastException e) {
			Sys.error("Param [0] could not be cast!", e, index);
			return null;
		}
	}
	
	public Reflect reflect(int index) {
		return new Reflect(this.params[index]);
	}
	
}
