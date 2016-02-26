package jz.sys.net.tcp;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import jz.sys.Logging;
import jz.sys.Sys;
import jz.sys.net.IP;

public class TCPClient implements Logging {
	
	private static Map<String, TCPClient> sockets;
	
	static {
		TCPClient.sockets = new HashMap<String, TCPClient>();
		
		Sys.eExit.add("tcpclient", (params) -> {
			TCPClient.clear();
		});
	}
	
	public static void clear() {
		for (String name : TCPClient.sockets.keySet().toArray(new String[TCPClient.sockets.size()])) {
			try {
				TCPClient.close(name);
			} catch (Exception e) {
				Sys.error("Unknown error by clearing TCP Socket [0]", e, name);
			}
		}
	}
	
	public static TCPClient get(String name) {
		if (TCPClient.sockets.containsKey(name)) {
			return TCPClient.sockets.get(name);
		} else {
			Sys.error("TCP Socket [0] could not be found!", null, name);
			return null;
		}
	}
	
	public static boolean close(String name) {
		if (TCPClient.get(name).disconnect()) {
			TCPClient.sockets.remove(name);
			return true;
		}
		return false;
	}
	
	public static TCPClient create(String name, int port) {
		if (TCPClient.sockets.containsKey(name)) {
			Sys.error("TCP Socket [0] already be created!", null, name);
			return null;
		} else {
			return new TCPClient(name, port);
		}
	}
	
	public static TCPClient create(String name, Socket client) {
		if (TCPClient.sockets.containsKey(name)) {
			Sys.error("TCP Socket [0] already be created!", null, name);
			return null;
		} else {
			return new TCPClient(name, client);
		}
	}
	
	// properties
	private String name;
	private int port;
	
	private String cIP;
	private int cPort;
	
	// IO
	private Socket socket;
	private InputStream in;
	private OutputStream out;
	
	// IO properties
	private int timeout;

	private TCPClient(String name, int port) {
		this.name = name;
		this.port = port;
		this.cIP = null;
		this.cPort = -1;
		TCPClient.sockets.put(name, this);
	}
	
	private TCPClient(String name, Socket client) {
		this.name = name;
		this.port = client.getLocalPort();
		this.cPort = IP.getConnectionPort(client);
		this.cIP = IP.getConnectionIP(client);
		this.socket = client;
		try {
			this.in = client.getInputStream();
			this.out = client.getOutputStream();
		} catch (IOException e) {
			this.exception(e);
			this.error("Can not adapt TCP Client Socket [0]", this.name);
		}
		TCPClient.sockets.put(name, this);
	}
	
	public void setConnection(String cIP, int cPort) {
		this.cIP = cIP;
		this.cPort = cPort;
	}
	
	public boolean bind() {
		if (this.binded()) {
			this.error("TCP Socket [0] is already be binded!", null, this.name);
			return false;
		}
		if (!this.readyBind()) {
			this.error("TCP Socket [0] is not ready to bind!", null, this.name);
			return false;
		}
		
		try {
			this.socket = new Socket();
			this.socket.bind(IP.getSocketAddress(this.port));
			this.port = this.socket.getLocalPort();
			this.log("TCP Socket [0] bind on port [1]", this.name, this.port);
		} catch (IOException e) {
			this.exception(e);
			return false;
		}
		return true;
	}
	
	public boolean connect() {
		if (this.connected()) {
			this.error("TCP Socket [0] is already be connected!", null, this.name);
			return false;
		}
		if (!this.readyConnect()) {
			this.error("TCP Socket [0] is not ready to connect!", null, this.name);
			return false;
		}
		
		try {
			this.socket.connect(IP.getSocketAddress(this.cIP, this.cPort), this.timeout);
			this.in = this.socket.getInputStream();
			this.out = this.socket.getOutputStream();
			this.log("TCP Socket [0] connect to ip [1] and port [2] with port [3]", this.name, this.cIP, this.cPort, this.port);
		} catch (IOException e) {
			this.exception(e);
			return false;
		}
		return true;
	}
	
	public boolean disconnect() {
		if (!this.binded()) {
			this.error("TCP Socket [0] are not connected or binded!", null, this.name);
			return false;
		}
		try {
			this.in.close();
			this.in = null;
			this.out.close();
			this.out = null;
			this.socket.close();
			this.socket = null;
			this.log("TCP Socket [0] are disconnected!", this.name);
		} catch (IOException e) {
			this.exception(e);
			return false;
		}
		return true;
	}
	
	public boolean establish() {
		return this.establish(true);
	}

	public boolean establish(boolean force) {
		if (this.bind() || force) {
			return this.connect();
		}
		return false;
	}
	
	public boolean readyBind() {
		return this.socket == null;
	}
	
	public boolean readyConnect() {
		return this.binded() && this.cIP != null && cPort != -1;
	}
	
	public boolean binded() {
		return this.socket != null && this.socket.isBound();
	}
	
	public boolean connected() {
		return this.socket != null && this.socket.isConnected();
	}
	
	public boolean established() {
		return this.binded() && this.connected();
	}
	
	public String name() {
		return this.name;
	}
	
	public int port() {
		return this.port;
	}
	
	public String cIP() {
		return this.cIP;
	}
	
	public int cPort() {
		return this.cPort;
	}
	
	public int timeout() {
		return this.timeout;
	}
	
	public void timeout(int timeout) {
		this.timeout = timeout;
	}
	
	public boolean send(byte[] data, int length) {
		try {
			this.out.write(length);
			this.out.write(data, 0, length);
		} catch (IOException e) {
			this.exception(e);
			return false;
		}
		return true;
	}
	
	public boolean send(String data) {
		return this.send(data.getBytes(), data.getBytes().length);
	}
	
	public TCPData receive() {
		try {
			int length = this.in.read();
			byte[] data = new byte[length];
			
			this.in.read(data);
			return new TCPData(data);
		} catch (IOException e) {
			this.exception(e);
		}
		return null;
	}
	
	public void exception(Exception e) {
		Sys.error("TCP Socket [0] with port [1] has an exception!", e, this.name, this.port);
	}
	
}
