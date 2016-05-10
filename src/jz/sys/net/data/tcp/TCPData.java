package jz.sys.net.data.tcp;

public class TCPData {

	private byte[] data;
	
	public TCPData(byte[] data) {
		this.data = data;
	}
	
	public byte[] data() {
		return this.data;
	}
	
	public String string() {
		return new String(this.data, 0, this.data.length);
	}
	
}
