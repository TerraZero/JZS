package jz.sys.events;

import java.util.HashMap;
import java.util.Map;

public class JZHandler {

	private Map<String, JZListener> listeners;
	
	public JZHandler() {
		this.listeners = new HashMap<String, JZListener>();
	}
	
	public void add(String name, JZListener listener) {
		this.listeners.put(name, listener);
	}
	
	public void remove(String name) {
		this.listeners.remove(name);
	}
	
	public void fire(String type, Object... params) {
		JZParams p = new JZParams(type, params);
		
		for (JZListener l : this.listeners.values()) {
			l.action(p);
			if (p.consumed()) break;
		}
	}
	
	public Map<String, JZListener> listeners() {
		return this.listeners;
	}
	
}
