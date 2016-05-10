package jz.sys.gui.components.api;

import javax.swing.JComponent;

public interface JZComponent<type extends JZComponent<type>> {
	
	public default type init() {
		return this.that();
	}

	public default type size(int width, int height) {
		this.component().setSize(width, height);
		return this.that();
	}
	
	public default type position(int x, int y) {
		this.component().setLocation(x, y);
		return this.that();
	}
	
	public type name(String name);
	
	public String name();
	
	public type ui(String ui);
	
	public String ui();
	
	public String type();
	
	public default JComponent component() {
		return (JComponent)this;
	}
	
	
	
	@SuppressWarnings("unchecked")
	public default type parent(JZContainer<?> parent) {
		parent.add(this);
		return (type)this;
	}
	
	@SuppressWarnings("unchecked")
	public default type that() {
		return (type)this;
	}
	
	
	
	public default JZComponent<?> fAdd() {
		return this;
	}
	
	public default JZComponent<?> fRemove() {
		return this;
	}
	
}
