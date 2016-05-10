package jz.sys.gui.components;

import java.awt.Color;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JScrollPane;

import jz.sys.gui.components.api.JZComponent;
import jz.sys.gui.components.api.JZContainer;

public class JZScroll extends JScrollPane implements JZContainer<JZScroll> {
	
	private static final long serialVersionUID = 1L;
	
	private String name;
	private String ui;
	private JZPanel panel;
	
	public JZScroll() {
		super();
		this.panel = new JZPanel().name(this.name() + ":panel").ui(this.ui() + ":panel");
		this.setViewportView(this.panel);
		this.addComponentListener(new ComponentAdapter() {
			
			@Override
			public void componentResized(ComponentEvent e) {
				JZScroll.this.arrange();
			}
			
		});
	}
	
	public void arrange() {
		this.panel.size(this.getWidth(), this.getHeight());
	}

	@Override
	public JZScroll name(String name) {
		this.name = name;
		return this;
	}

	@Override
	public String name() {
		return this.name;
	}

	@Override
	public JZScroll ui(String ui) {
		this.ui = ui;
		return this;
	}

	@Override
	public String ui() {
		return this.ui;
	}

	@Override
	public String type() {
		return "scroll";
	}
	
	public JZScroll add(JZComponent<?> component) {
		this.panel.add(component);
		return this;
	}
	
	public JZScroll remove(JZComponent<?> component) {
		this.panel.remove(component);
		return this;
	}
		
	@Override
	public void setBackground(Color bg) {
		if (this.panel == null) {
			super.setBackground(bg);
		} else {
			this.panel.setBackground(bg);
		}
	}
	
	@Override
	public Color getBackground() {
		if (this.panel == null) {
			return super.getBackground();
		}
		return this.panel.getBackground();
	}
	
	public JZPanel panel() {
		return this.panel;
	}

}
