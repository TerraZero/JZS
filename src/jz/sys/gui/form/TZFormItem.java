package jz.sys.gui.form;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JComponent;

import jz.sys.gui.components.JZPanel;
import jz.sys.gui.components.api.JZComponent;

public abstract class TZFormItem<type extends TZFormItem<type>> implements JZComponent<TZFormItem<type>> {

	protected JZPanel panel;
	private String[] validates;
	
	public TZFormItem(String name, String ui) {
		this.panel = new JZPanel().name(name).ui(ui);
		this.panel.addComponentListener(new ComponentAdapter() {
			
			@Override
			public void componentResized(ComponentEvent e) {
				TZFormItem.this.arrange();
			}

		});
	}
	
	public JZPanel panel() {
		return this.panel;
	}
	
	@SuppressWarnings("unchecked")
	public type validates(String... validates) {
		this.validates = validates;
		return (type)this;
	}
	
	public String[] validates() {
		return this.validates;
	}
	
	public abstract String value();
	
	public abstract type value(String value);
	
	@Override
	public JComponent component() {
		return this.panel;
	}
	
	@Override
	public String name() {
		return this.panel.name();
	}
	
	@Override
	public String ui() {
		return this.panel.ui();
	}
	
	@Override
	public TZFormItem<type> name(String name) {
		this.panel.name(name);
		return this.that();
	}
	
	@Override
	public TZFormItem<type> ui(String ui) {
		this.panel.ui(ui);
		return this.that();
	}
	
	public void arrange() {
		
	}
	
	public int width() {
		return this.panel.getWidth();
	}
	
	public int height() {
		return this.panel.getHeight();
	}
	
}