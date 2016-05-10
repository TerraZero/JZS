package jz.sys.net.data.udp;

import java.net.DatagramPacket;

import jz.sys.Sys;
import jz.sys.utils.Strings;

public class UDPSData extends UDPData {
	
	protected String session;
	protected int sessionlength;
	
	protected String ip;
	protected int port;
	protected String data;
	
	public UDPSData(DatagramPacket packet) {
		super(packet);
		this.init();
	}
	
	public UDPSData(UDPData data) {
		super(data.packet);
		this.init();
	}
	
	public UDPSData(String ip, int port, String... params) {
		super(null);
		String session = Sys.session();
		String length = new String(new byte[] {(byte)session.getBytes().length});
		String data = Strings.join("§", params);
		this.data = length + session + data;
		this.ip = ip;
		this.port = port;
	}
	
	protected void init() {
		this.sessionlength = this.packet.getData()[0];
	}
	
	protected int dataOffset() {
		return this.sessionlength + 1;
	}
	
	@Override
	public String ip() {
		if (this.ip == null) {
			return super.ip();
		} else {
			return this.ip;
		}
	}
	
	@Override
	public int port() {
		if (this.port == 0) {
			return super.port();
		} else {
			return this.port;
		}
	}
	
	public byte[] data() {
		byte[] data = new byte[this.packet.getLength() - this.dataOffset()];
		byte[] pData = this.packet.getData();
		
		for (int i = 0; i < data.length; i++) {
			data[i] = pData[this.dataOffset() + i];
		}
		return data;
	}
	
	public String session() {
		return this.session;
	}
	
	public String string() {
		if (this.data == null) {
			this.data = new String(this.data());;
		}
		return this.data;
	}
	
	public String[] args() {
		return this.args("§");
	}
	
	public String[] args(String splitter) {
		return this.string().split(splitter);
	}
	
}
