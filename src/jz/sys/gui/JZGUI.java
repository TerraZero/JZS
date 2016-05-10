package jz.sys.gui;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import jz.sys.Sys;
import jz.sys.gui.components.JZFrame;
import jz.sys.gui.components.JZPanel;
import jz.sys.gui.components.JZTextfield;
import jz.sys.gui.form.TZForm;
import jz.sys.gui.form.items.FormTextfield;
import jz.sys.module.api.SysModule;

public class JZGUI implements SysModule {
	
	public static void main(String[] args) {
		Sys.init();
	}
	
	private static Map<String, JZFrame> frames;

	static {
		System.setProperty("sun.java2d.opengl", "true");
		
		JZGUI.frames = new HashMap<String, JZFrame>();
	}
	
	public static JZFrame getFrame(String name) {
		JZFrame frame = JZGUI.frames.get(name);
		
		if (frame == null) {
			frame = new JZFrame(name);
			JZGUI.frames.put(name, frame);
		}
		return frame;
	}
	
	@Override
	public void hookInit() {
		JZFrame root = JZGUI.getFrame("root");
		root.title("Root").ui("Game").size(840, 640).position(null).open();
		
		new JZPanel().position(50, 50).size(50, 50).parent(root).setBackground(Color.CYAN);
		new JZTextfield().position(100, 100).size(200, 30).parent(root).value("hallo");
		
		TZForm form = new TZForm("Test", "test");
		form.item(new FormTextfield("test", "etsd").size(20, 30).value("testman")).position(200, 200).size(200, 200).parent(root);
	}
	
	@Override
	public void hookExit(int code) {
		for (Entry<String, JZFrame> entry : JZGUI.frames.entrySet()) {
			entry.getValue().close();
		}
	}
	
}
