package jz.soundsync.server.net;

import java.net.DatagramPacket;

import jz.soundsync.server.SoundSyncUser;
import jz.sys.net.data.udp.UDPData;
import jz.sys.net.data.udp.UDPSData;
import jz.sys.utils.Strings;

public class SSUDPS extends UDPSData {
	
	protected SoundSyncUser client;

	public SSUDPS(DatagramPacket packet) {
		super(packet);
	}
	
	public SSUDPS(UDPData data) {
		super(data);
	}
	
	public SSUDPS(String ip, int port, String... params) {
		super(ip, port, params);
	}
	
	public void client(SoundSyncUser client) {
		this.client = client;
	}
	
	public SoundSyncUser client() {
		return this.client;
	}
	
	public boolean access(String[] permissions) {
		if (permissions == null) return true;
		return this.testSession() && this.client.perm(permissions);
	}
	
	public boolean testSession() {
		return this.client != null && this.session() != null && this.session().equals(this.client.session());
	}
	
	public boolean expiredSession() {
		return this.client() != null && this.session() != null && !this.session().equals(this.client().session());
	}

}
