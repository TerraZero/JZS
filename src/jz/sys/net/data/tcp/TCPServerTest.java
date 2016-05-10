package jz.sys.net.data.tcp;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import jz.sys.Sys;

public class TCPServerTest {
	
	public static TCPServer s;
	public static TCPClient c;

	public static void main(String[] args) {
		JFrame f = new JFrame();
		JPanel cp = new JPanel();
		cp.setLayout(null);
		cp.setBackground(Color.BLACK);
		
		JTextField port = new JTextField();
		port.setBounds(10, 10, 620, 20);
		cp.add(port);
		port.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				port.setEnabled(false);
				int p = Integer.parseInt(port.getText());
				TCPServerTest.s = TCPServer.create("test", p);
				s.bind();
				Thread t = new Thread() {
					
					@Override
					public void run() {
						String client = s.accept();
						JOptionPane.showMessageDialog(null, "Client " + client);
						c = s.client(client);
					}
					
				};
				t.start();
			}
			
		});
		
		JButton send = new JButton("Send");
		send.setBounds(10, 40, 200, 40);
		cp.add(send);
		send.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				c.send("hallo");
			}
			
		});
		
		f.setSize(640, 480);
		f.setLocationRelativeTo(null);
		f.setContentPane(cp);
		
		f.addWindowListener(new WindowAdapter() {
			
			@Override
			public void windowClosing(WindowEvent e) {
				Sys.exit(0);
			}
			
		});
		f.setVisible(true);
	}
	
}
