package jz.soundsync.client;

import java.awt.Color;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import jz.sys.Sys;

public class SoundSyncClientGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	
	protected JPanel cp;
	public JTextField field;
	
	public SoundSyncClientGUI() {
		this.setTitle("SoundSync - Client");
		this.cp = new JPanel();
		this.cp.setLayout(null);
		this.cp.setBackground(Color.BLACK);
		this.setContentPane(this.cp);
		
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.addComponentListener(new ComponentAdapter() {
			
			@Override
			public void componentResized(ComponentEvent e) {
				componentPosition(cp.getWidth(), cp.getHeight());
			}
			
		});
		this.componentInit();
		this.setSize(640, 480);
		this.setLocationRelativeTo(null);
		this.addWindowListener(new WindowAdapter() {
			
			@Override
			public void windowClosing(WindowEvent e) {
				Sys.exit(0);
			}
			
		});
	}
	
	protected void componentInit() {
		this.field = new JTextField();
		this.field.setBackground(Color.WHITE);
		this.add(this.field);
	}
	
	protected void componentPosition(int width, int height) {
		this.field.setBounds(10, 10, width - 20, 30);
	}

}
