package jz.sys.net.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Map;

import jz.sys.Logging;
import jz.sys.Sys;
import jz.sys.net.IP;

public class UDPSocket implements Logging {
	
	private static Map<String, UDPSocket> sockets;
	
	static {
		UDPSocket.sockets = new HashMap<String, UDPSocket>();
		
		Sys.eExit.add("udpsocket", (params) -> {
			UDPSocket.clear();
		});
	}
	
	public static UDPSocket create(String name, int port) {
		if (UDPSocket.sockets.containsKey(name)) {
			Sys.error("UDP Socket [0] already be created!", null, name);
			return null;
		} else {
			return new UDPSocket(name, port);
		}
	}

	public static UDPSocket get(String name) {
		if (UDPSocket.sockets.containsKey(name)) {
			return UDPSocket.sockets.get(name);
		} else {
			Sys.error("UDP Socket [0] could not be found!", null, name);
			return null;
		}
	}
	
	public static boolean close(String name) {
		if (UDPSocket.get(name).unbind()) {
			UDPSocket.sockets.remove(name);
			return true;
		}
		return false;
	}
	
	public static void clear() {
		for (String name : UDPSocket.sockets.keySet().toArray(new String[UDPSocket.sockets.size()])) {
			try {
				UDPSocket.close(name);
			} catch (Exception e) {
				Sys.error("Unknown error by clearing UDP Socket [0]", e, name);
			}
		}
	}
	
	// properties
	private String name;
	private int port;
	
	// IO
	private DatagramSocket socket;
	private DatagramPacket in;
	private DatagramPacket out;
	
	// IO properties
	private byte[] buffer;
	private int timeout;
	
	private UDPSocket(String name, int port) {
		this.name = name;
		this.port = port;
		this.buffer(4000);
		this.timeout(30);
		UDPSocket.sockets.put(name, this);
	}
	
	public boolean bind() {
		if (this.binded()) {
			this.error("UDP Socket [0] is already be binded!", null, this.name);
			return false;
		}
		
		try {
			this.socket = new DatagramSocket(port);
			this.port = this.socket.getLocalPort();
			this.socket.setSoTimeout(this.timeout);
		} catch (SocketException e) {
			this.exception(e);
			return false;
		}
		
		return true;
	}
	
	public boolean binded() {
		return this.socket != null;
	}
	
	public boolean unbind() {
		if (!this.binded()) {
			this.error("UDP Socket [0] is not binded", null, this.name);
			return false;
		}
		
		try {
			this.socket.disconnect();
			this.socket.close();
			this.socket = null;
		} catch (Exception e) {
			this.exception(e);
		}
		return true;
	}
	
	public void buffer(int buffer) {
		this.buffer = new byte[buffer];
		this.in = new DatagramPacket(this.buffer, this.buffer.length);
		this.out = new DatagramPacket(this.buffer, this.buffer.length);
	}
	
	public int buffer() {
		return this.buffer.length;
	}
	
	public boolean send(byte[] data, int offset, int length, String ip, int port) {
		this.out.setData(data, offset, length);
		return this.send(this.out, ip, port);
	}
	
	public boolean send(String message, String ip, int port) {
		return this.send(message.getBytes(), 0, message.getBytes().length, ip, port);
	}
	
	public boolean send(DatagramPacket packet, String ip, int port) {
		packet.setAddress(IP.getInetAddressFromIP(ip));
		packet.setPort(port);
		try {
			this.socket.send(packet);
		} catch (IOException e) {
			this.exception(e);
			return false;
		}
		return true;
	}
	
	public UDPData receive() {
		try {
			this.socket.receive(this.in);
		} catch (IOException e) {
			this.exception(e);
			return null;
		}
		return new UDPData(this.in);
	}
	
	public void timeout(int timeout) {
		this.timeout = timeout;
		
		if (this.socket == null) return;
		
		try {
			this.socket.setSoTimeout(timeout);
		} catch (SocketException e) {
			this.exception(e);
		}
	}
	
	public int timeout() {
		try {
			return this.socket.getSoTimeout();
		} catch (SocketException e) {
			this.exception(e);
			return -2;
		}
	}

	public void exception(Exception e) {
		Sys.error("UDP Socket [0] with port [1] has an exception!", e, this.name, this.port);
	}
	
}
