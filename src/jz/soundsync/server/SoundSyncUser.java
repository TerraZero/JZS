package jz.soundsync.server;

import java.util.ArrayList;
import java.util.List;

import jz.sys.net.data.udp.UDPData;

public class SoundSyncUser {

	protected String ip;
	protected int port;
	protected String session;
	protected List<String> permissions;
	
	protected String name;
	protected UserData data;
 	
	public SoundSyncUser(String ip, int port) {
		this.ip = ip;
		this.port = port;
		this.init();
	}
	
	public SoundSyncUser(UDPData data) {
		this.ip = data.ip();
		this.port = data.port();
		this.init();
	}
	
	protected void init() {
		this.permissions = new ArrayList<String>();
		this.data = new UserData();
	}
	
	public void session(String session) {
		this.session = session;
	}
	
	public String session() {
		return this.session;
	}
	
	public String ip() {
		return this.ip;
	}
	
	public int port() {
		return this.port;
	}
	
	public void addPerm(String perm) {
		this.permissions.add(perm);
	}
	
	public void removePerm(String perm) {
		this.permissions.remove(perm);
	}
	
	public boolean perm(String... permissions) {
		if (permissions == null) return true;
		
		for (String p : permissions) {
			if (!this.testPerm(p)) return false; 
		}
		return true;
	}
	
	protected boolean testPerm(String perm) {
		for (String p : this.permissions) {
			if (p.equals(perm)) return true;
		}
		return false;
	}
	
	public String name() {
		return this.name;
	}
	
	public void login(String name, String session) {
		this.addPerm("login");
		this.name = name;
		this.session = session;
	}
	
	public void logout() {
		this.permissions.clear();
		this.data.clear();
		this.name = null;
		this.session = null;
	}
	
	public UserData data() {
		return this.data;
	}
	
}
