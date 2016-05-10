package jz.sys.events;

import jz.sys.utils.Strings;

public class JZESubscriber {

	private JZEHandler handler;
	private String[] fire;
	
	public JZESubscriber(JZEHandler handler, String... fire) {
		this.fire = fire;
		this.handler = handler;
	}
	
	public void fire(JZEvent params) {
		if (Strings.isIntern(params.type(), this.fire)) {
			this.handler.fire(params);
		}
	}
	
	public String[] fire() {
		return this.fire;
	}
	
	public JZEHandler handler() {
		return this.handler;
	}
	
}
