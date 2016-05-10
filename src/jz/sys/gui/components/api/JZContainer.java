package jz.sys.gui.components.api;

public interface JZContainer<type extends JZContainer<type>> extends JZComponent<type> {

	public default type add(JZComponent<?>... components) {
		for (JZComponent<?> component : components) {
			this.component().add(component.fAdd().component());	
		}
		return this.that();
	}
	
	public default type remove(JZComponent<?>... components) {
		for (JZComponent<?> component : components) {
			this.component().remove(component.fRemove().component());
		}
		return this.that();
	}
	
	public default<comp extends JZComponent<?>> comp child(comp component) {
		this.add(component);
		return component;
	}
	
}
