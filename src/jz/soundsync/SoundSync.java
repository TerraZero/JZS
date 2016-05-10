package jz.soundsync;

import javax.swing.JOptionPane;

import jz.sys.Sys;

public class SoundSync {

	public static final int serverPort = 51937;
	
	public static void mes(String message, Object... tokens) {
		JOptionPane.showMessageDialog(null, Sys.placeholder(message, tokens));
	}
	
}
