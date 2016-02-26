package jz.sys.net.tcp;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import jz.sys.Logging;
import jz.sys.Sys;

public class TCPServer implements Logging {
	
	private static Map<String, TCPServer> sockets;
	
	static {
		TCPServer.sockets = new HashMap<String, TCPServer>();
		
		Sys.eExit.add("tcpserver", (params) -> {
			TCPServer.clear();
		});
	}
	
	public static TCPServer create(String name, int port) {
		if (TCPServer.sockets.containsKey(name)) {
			Sys.error("TCP ServerSocket [0] are already be created!", null, name);
			return null;
		} else {
			return new TCPServer(name, port);
		}
	}
	
	public static TCPServer get(String name) {
		if (TCPServer.sockets.containsKey(name)) {
			return TCPServer.sockets.get(name);
		} else {
			Sys.error("TCP ServerSocket [0] could not be found!", null, name);
			return null;
		}
	}
	
	public static boolean close(String name) {
		if (TCPServer.get(name).unbind()) {
			TCPServer.sockets.remove(name);
			return true;
		}
		return false;
	}
	
	public static void clear() {
		for (String name : TCPServer.sockets.keySet().toArray(new String[TCPServer.sockets.size()])) {
			try {
				TCPServer.close(name);
			} catch (Exception e) {
				Sys.error("Unknown error by clearing TCP ServerSocket [0]", e, name);
			}
		}
	}

	// properties
	private String name;
	private int port;
	private int count;
	
	// IO
	private ServerSocket socket;
	
	// IO properties
	private Map<String, TCPClient> clients;
	
	private TCPServer(String name, int port) {
		this.name = name;
		this.count = 0;
		this.port = port;
		this.clients = new HashMap<String, TCPClient>();
		TCPServer.sockets.put(name, this);
	}
	
	public boolean bind() {
		if (this.binded()) {
			this.error("TCP ServerSocket [0] are already be binded!", this.name);
			return false;
		}
		
		try {
			this.socket = new ServerSocket();
			this.socket.setReuseAddress(true);
			this.socket.bind(new InetSocketAddress(this.port));
		} catch (IOException e) {
			this.exception(e);
			return false;
		}
		return true;
	}
	
	public boolean unbind() {
		if (!this.binded()) {
			this.error("TCP ServerSocket [0] is not binded!", this.name);
			return false;
		}
		
		try {
			this.clientsClear();
			this.socket.close();
			this.socket = null;
			this.log("TCP Server Socket [0] are disconnect!", this.name);
		} catch (IOException e) {
			this.exception(e);
			return false;
		}
		return true;
	}
	
	public String accept() {
		if (!this.binded()) {
			this.error("TCP ServerSocket [0] is not binded!", this.name);
			return null;
		}
		
		try {
			Socket client = this.socket.accept();
			this.count++;
			TCPClient c = TCPClient.create(this.getClientName(), client);
			this.clients.put(c.name(), c);
			return c.name();
		} catch (IOException e) {
			this.exception(e);
		}
		return null;
	}
	
	public Map<String, TCPClient> clients() {
		return this.clients;
	}
	
	public TCPClient client(String name) {
		return this.clients.get(name);
	}
	
	public boolean clientClose(String name) {
		if (this.clients.containsKey(name)) {
			TCPClient.close(name);
			this.clients.remove(name);
		}
		return false;
	}
	
	public void clientsClear() {
		for (String name : this.clients.keySet().toArray(new String[this.clients.size()])) {
			this.clientClose(name);
		}
	}
	
	public String getClientName() {
		return this.name + "-client-" + this.count;
	}
	
	public boolean binded() {
		return this.socket != null;
	}
	
	public void exception(Exception e) {
		Sys.error("TCP ServerSocket [0] with port [1] has an exception!", e, this.name, this.port);
	}
	
}
