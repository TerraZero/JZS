package jz.sys.gui.components;

import java.awt.Color;

import javax.swing.JPanel;

import jz.sys.gui.components.api.JZContainer;

public class JZPanel extends JPanel implements JZContainer<JZPanel> {

	private static final long serialVersionUID = 1L;
	
	private String name;
	private String ui;
	
	public JZPanel() {
		this.init();
	}
	
	@Override
	public JZPanel init() {
		this.setLayout(null);
		this.setBackground(Color.BLACK);
		
		this.ui = "Panel";
		return this;
	}

	@Override
	public JZPanel name(String name) {
		this.name = name;
		return this;
	}

	@Override
	public String name() {
		return this.name;
	}

	@Override
	public String type() {
		return "panel";
	}
	
	@Override
	public String toString() {
		return "[" + this.type().toUpperCase() + "] " + this.name + " (" + super.toString() + ")"; 
	}

	@Override
	public String ui() {
		return this.ui;
	}

	@Override
	public JZPanel ui(String ui) {
		this.ui = ui;
		return this;
	}

}
