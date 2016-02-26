package jz.sys.events;

import jz.sys.reflect.params.Params;

public class JZParams extends Params {
	
	private String type;
	private boolean consumed;

	public JZParams(String type, Object... params) {
		super(params);
		this.type = type;
		this.consumed = false;
	}
	
	public String type() {
		return this.type;
	}
	
	public void consume() {
		this.consumed = true;
	}

	public boolean consumed() {
		return this.consumed;
	}
	
}
