package jz.sys.gui.components;

import java.awt.Component;

import javax.swing.JFrame;

import jz.sys.gui.components.api.JZComponent;
import jz.sys.gui.components.api.JZContainer;

public class JZFrame extends JFrame implements JZContainer<JZFrame> {

	private static final long serialVersionUID = 1L;
	
	private JZPanel cp;
	private String name;
	private String ui;
	
	public JZFrame() {
		this.init();
	}

	public JZFrame(String name) {
		this.init().name = name;
	}
	
	@Override
	public JZFrame init() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.cp = new JZPanel();
		this.setContentPane(this.cp);
		
		this.setTitle(this.name);
		
		this.ui = "Frame";
		return this;
	}
	
	public String name() {
		return this.name;
	}
	
	@Override
	public JZFrame size(int width, int height) {
		this.setSize(width, height);
		this.setLocationRelativeTo(null);
		return this;
	}

	public JZFrame open() {
		this.setVisible(true);
		return this;
	}
	
	public JZFrame close() {
		this.setVisible(false);
		return this;
	}
	
	public JZFrame title(String title) {
		this.setTitle(title);
		return this;
	}
	
	@Override
	public JZFrame position(int x, int y) {
		this.setLocation(x, y);
		return this;
	}
	
	public JZFrame position(Component component) {
		this.setLocationRelativeTo(component);
		return this;
	}

	@Override
	public JZFrame name(String name) {
		this.name = name;
		return this;
	}

	@Override
	public String type() {
		return "frame";
	}
	
	@Override
	public String toString() {
		return "[" + this.type().toUpperCase() + "] " + this.name + " (" + super.toString() + ")"; 
	}

	@Override
	public JZFrame add(JZComponent<?>... components) {
		for (JZComponent<?> component : components) {
			this.add(component.component());	
		}
		return this;
	}

	@Override
	public JZFrame remove(JZComponent<?>... components) {
		for (JZComponent<?> component : components) {
			this.remove(component.component());
		}
		return this;
	}

	@Override
	public JZFrame ui(String ui) {
		this.ui = ui;
		return this;
	}

	@Override
	public String ui() {
		return this.ui;
	}
	
}
