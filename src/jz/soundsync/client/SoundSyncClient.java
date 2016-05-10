package jz.soundsync.client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import jz.soundsync.SoundSync;
import jz.sys.Sys;
import jz.sys.net.IP;
import jz.sys.net.data.udp.UDPData;
import jz.sys.net.data.udp.UDPSData;
import jz.sys.net.data.udp.UDPSocket;

public class SoundSyncClient {

	public static void main(String[] args) {
		SoundSyncClient c = new SoundSyncClient();
	}
	
	protected UDPSocket udp;
	protected SoundSyncClientGUI gui;
	
	public SoundSyncClient() {
		this.udp = UDPSocket.create("soundsyncclient", 0);
		this.udp.bind();
		this.gui = new SoundSyncClientGUI();
		this.gui.setVisible(true);
		
		this.gui.field.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String text = gui.field.getText();
				gui.field.setText("");
				String[] data = text.split(",");
				UDPSData udps = new UDPSData(IP.getIP(), SoundSync.serverPort, data);
				udp.send(udps);
			}
			
		});
		Thread d = new Thread() {
			
			@Override
			public void run() {
				do {
					UDPData data = udp.receive();
					if (!data.timeout()) {
						SoundSync.mes(data.string());
					}
				} while (!this.isInterrupted());
			}
			
		};
		d.start();
		Sys.eExit.add("exit thread", (params) -> {
			d.interrupt();
		});
	}
	
}
