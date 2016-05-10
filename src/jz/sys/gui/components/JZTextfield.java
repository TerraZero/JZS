package jz.sys.gui.components;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JTextField;

import jz.sys.gui.components.api.JZComponent;

public class JZTextfield extends JTextField implements JZComponent<JZTextfield> {

	private static final long serialVersionUID = 1L;

	private String name;
	private String ui;

	@Override
	public JZTextfield init() {
		this.setBackground(Color.WHITE);
		this.setForeground(Color.BLACK);
	
		this.ui = "Textfield";
		return this;
	}

	@Override
	public JZTextfield name(String name) {
		this.name = name;
		return this;
	}

	@Override
	public String name() {
		return this.name;
	}

	@Override
	public JZTextfield ui(String ui) {
		this.ui = ui;
		return this;
	}

	@Override
	public String ui() {
		return this.ui;
	}

	@Override
	public String type() {
		return "textfield";
	}
	
	@Override
	protected void paintBorder(Graphics g) {
		// remove all borders
	}
	
	public String value() {
		return this.getText();
	}
	
	public JZTextfield value(String value) {
		this.setText(value);
		return this;
	}

}
