package jz.sys.events;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jz.sys.utils.Lists;

public class JZEHandler {

	private String name;
	private JZEListener defaultListener;
	private Map<String, JZEListener> listeners;
	private List<JZESubscriber> subscribers;
	
	public JZEHandler(String name) {
		this.name = name;
		this.listeners = new HashMap<String, JZEListener>();
		this.subscribers = new ArrayList<JZESubscriber>();
		this.defaultListener = null;
	}
	
	public JZEHandler(String name, JZEListener defaultListener) {
		this(name);
		this.defaultListener = defaultListener;
	}
	
	public String name() {
		return this.name;
	}
	
	public JZEHandler add(String name, JZEListener listener) {
		this.listeners.put(name, listener);
		return this;
	}
	
	public JZEHandler remove(String name) {
		this.listeners.remove(name);
		return this;
	}
	
	public JZEHandler fire(String type, Object... params) {
		this.fire(new JZEvent(type, params));
		return this;
	}
	
	public JZEHandler fire(JZEvent event) {
		if (this.isDefault()) {
			this.defaultListener.action(event);
		} else {
			for (JZEListener l : this.listeners.values()) {
				l.action(event);
				if (event.consumed()) break;
			}
		}
		
		if (!event.consumed()) {
			for (JZESubscriber subscriber : this.subscribers) {
				if (event.propagation()) subscriber.fire(event);
			}
		}
		return this;
	}
	
	public Map<String, JZEListener> listeners() {
		return this.listeners;
	}
	
	public List<JZESubscriber> subscribers() {
		return this.subscribers;
	}
	
	public JZEHandler subscriber(JZEHandler handler, String... fire) {
		this.subscriber(new JZESubscriber(handler, fire));
		return this;
	}

	public JZEHandler subscriber(JZESubscriber subscriber) {
		this.subscribers.add(subscriber);
		return this;
	}
	
	public JZEHandler removeSubscriber(String name) {
		Lists.remove(this.subscribers, (subscriber) -> {
			return subscriber.handler().name().equals(name);
		});
		return this;
	}
	
	public JZEHandler setDefault(JZEListener defaultListener) {
		this.defaultListener = defaultListener;
		return this;
	}
	
	public JZEListener getDefault() {
		return this.defaultListener;
	}
	
	public boolean isDefault() {
		return this.listeners.isEmpty() && this.defaultListener != null;
	}
	
	/**
	 * Remove all listeners on this handler.
	 */
	public JZEHandler reset() {
		this.reset(false);
		return this;
	}
	
	/**
	 * Remove all listeners on this handler.
	 * @param resetsubscriber - By TRUE remove all listeners from subscribers
	 */
	public JZEHandler reset(boolean resetsubscriber) {
		this.listeners.clear();
		
		if (resetsubscriber) {
			for (JZESubscriber subscriber : this.subscribers) {
				subscriber.handler().reset(true);
			}
		}
		return this;
	}
	
	/**
	 * Remove all listeners and subscriber on this handler.
	 */
	public JZEHandler clear() {
		this.listeners.clear();
		this.subscribers.clear();
		return this;
	}
	
}
