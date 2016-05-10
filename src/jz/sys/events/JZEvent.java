package jz.sys.events;

import jz.sys.reflect.params.Params;

public class JZEvent extends Params {
	
	private String type;
	private boolean consumed;
	private boolean propagation;

	public JZEvent(String type, Object... params) {
		super(params);
		this.type = type;
		this.consumed = false;
		this.propagation = true;
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
	
	public void stopPropagation() {
		this.propagation = false;
	}
	
	public boolean propagation() {
		return this.propagation;
	}
	
	public void cancel() {
		this.consume();
		this.stopPropagation();
	}
	
}
