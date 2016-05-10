package jz.sys.gui.form;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;

import jz.sys.events.JZEHandler;
import jz.sys.gui.components.JZScroll;
import jz.sys.gui.components.api.JZComponent;

public class TZForm implements JZComponent<TZForm> {
	
	public JZEHandler eForm;
	public JZEHandler eSubmit;
	public JZEHandler eValidate;
	
	private List<TZFormItem<?>> items;
	private JZScroll panel;
	private int margin;
	
	public TZForm(String name, String ui) {
		this.panel = new JZScroll().name(name).ui(ui);
		this.panel.setBackground(Color.CYAN);
		
		this.eForm = new JZEHandler("form");
		this.eSubmit = new JZEHandler("submit");
		this.eValidate = new JZEHandler("validate");
		
		this.eForm.subscriber(this.eSubmit, "submit");
		this.eForm.subscriber(this.eValidate, "validate");
		
		this.panel.addComponentListener(new ComponentAdapter() {
			
			@Override
			public void componentResized(ComponentEvent e) {
				TZForm.this.arrange();
			}
			
		});
		
		this.margin = 10;
		
		this.items = new ArrayList<TZFormItem<?>>();
		this.init();
	}
	
	public int margin() {
		return this.margin;
	}
	
	public TZForm margin(int margin) {
		this.margin = margin;
		return this;
	}
	
	public TZForm arrange() {
		int w = this.width() - this.margin() * 2;
		int h = this.margin();
		
		for (TZFormItem<?> item : this.items()) {
			int ih = item.height();
			
			item.position(this.margin(), h).size(w, ih);
			
			h += ih + this.margin();
		}
		this.panel.panel().size(200, 2000);
		return this;
	}
	
	public List<TZFormItem<?>> items() {
		return this.items;
	}
	
	public TZForm item(TZFormItem<?> item) {
		this.items.add(item);
		this.panel.panel().add(item);
		return this;
	}
	
	public TZFormItem<?> item(String name) {
		for (TZFormItem<?> item : this.items()) {
			if (item.name().equals(name)) {
				return item;
			}
		}
		return null;
	}

	@Override
	public TZForm name(String name) {
		this.panel.name(name);
		return this;
	}

	@Override
	public String name() {
		return this.panel.name();
	}

	@Override
	public TZForm ui(String ui) {
		this.panel.ui(ui);
		return this;
	}

	@Override
	public String ui() {
		return this.panel.ui();
	}

	@Override
	public String type() {
		return "form";
	}
	
	public int width() {
		return this.panel.getWidth();
	}
	
	public int height() {
		return this.panel.getHeight();
	}
	
	@Override
	public JComponent component() {
		return this.panel;
	}
	
	public TZForm submit() {
		this.validate();
		this.formSubmit();
		return this;
	}
	
	public TZForm validate() {
		this.formValidate();
		return this;
	}
	
	public TZForm formSubmit() {
		this.eForm.fire("submit", this);
		return this;
	}
	
	public TZForm formValidate() {
		this.eForm.fire("validate", this);
		return this;
	}
	
}
