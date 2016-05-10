package jz.sys.reflect.params;

import java.lang.reflect.Array;

import jz.sys.Sys;
import jz.sys.reflect.Reflect;
import jz.sys.utils.Objects;

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
	
	public Params set(int index, Object data) {
		this.params[index] = data;
		return this;
	}
	
	public Params append(Object... appends) {
		this.params = Objects.append(this.params, appends);
		return this;
	}
	
	public Params prepend(Object... prepends) {
		this.params = Objects.prepend(this.params, prepends);
		return this;
	}
	
	@SuppressWarnings("unchecked")
	public<type> type[] getVarArgs(int index, Class<type> var) {
		if (this.params.length <= index) return null;
		int length = this.params.length - index;
		type[] vars = (type[])Array.newInstance(var, length);
		
		int i = 0;
		try {
			for (; i < length; i++) {
				vars[i] = (type)this.params[index + i];
			}
		} catch (ClassCastException | ArrayStoreException e) {
			Sys.error("Param [0] could not be cast to class [1], cancel get var args!", e, index + i, var);
			return null;
		}
		return vars;
	}
	
	public Reflect reflect(int index) {
		return new Reflect(this.params[index]);
	}
	
}
