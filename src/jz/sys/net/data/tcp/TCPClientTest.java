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
import jz.sys.net.IP;

public class TCPClientTest {
	
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
				c = TCPClient.create("test", p);
			}
			
		});
		
		JTextField cport = new JTextField();
		cport.setBounds(10, 40, 620, 20);
		cp.add(cport);
		cport.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				cport.setEnabled(false);
				int p = Integer.parseInt(cport.getText());
				c.setConnection(IP.getIP(), p);
				if (c.establish()) {
					JOptionPane.showMessageDialog(null, "Established");
				} else {
					JOptionPane.showMessageDialog(null, "not Established");
				}
			}
			
		});
		
		JButton rec = new JButton("Receive");
		rec.setBounds(10, 70, 200, 40);
		cp.add(rec);
		rec.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, c.receive().string());
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
