package jz.sys.net.data.udp;

import java.net.DatagramPacket;

import jz.sys.net.IP;

public class UDPData {

	protected DatagramPacket packet;
	protected int timeout;
	
	public UDPData(DatagramPacket packet) {
		this.packet = packet;
		this.timeout = -1;
	}
	
	public UDPData(int timeout) {
		this.timeout = timeout;
	}
	
	public boolean timeout() {
		return this.timeout != -1;
	}
	
	public int getTimeout() {
		return this.timeout;
	}
	
	public DatagramPacket packet() {
		return this.packet;
	}
	
	public int port() {
		return this.packet.getPort();
	}
	
	public String ip() {
		return IP.getIPFromInetAddress(this.packet.getAddress());
	}
	
	public byte[] data() {
		byte[] data = new byte[this.packet.getLength()];
		
		for (int i = 0; i < data.length; i++) {
			data[i] = this.packet.getData()[i];
		}
		return data;
	}
	
	public String string() {
		return new String(this.packet.getData(), 0, this.packet.getLength());
	}
	
}
