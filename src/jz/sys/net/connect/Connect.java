package jz.sys.net.connect;

import java.util.HashMap;
import java.util.Map;

import jz.sys.Sys;
import jz.sys.net.connect.a.ConnectProtocol;
import jz.sys.reflect.helper.InterWrapper;
import jz.sys.reflect.params.Params;

public class Connect {
	
	private static Connect instance;
	
	public static Connect instance() {
		if (Connect.instance == null) {
			Connect.instance = new Connect();
		}
		return Connect.instance;
	}
	
	public static Connecter connect(String protocol, Object... params) {
		return Connect.instance().getConnect(protocol, params);
	}
	
	private Map<String, ConnectProtocol> protocols;
	
	public Connect() {
		this.protocols = new HashMap<String, ConnectProtocol>();
		InterWrapper<ConnectProtocol> inter = new InterWrapper<>(ConnectProtocol.class);
		for (ConnectProtocol prot : inter.create()) {
			this.protocols.put(prot.name(), prot);
		}
	}
	
	public Connecter getConnect(String protocol, Object... params) {
		ConnectProtocol prot = this.getProtocol(protocol);
		if (prot == null) return null;
		Params p = new Params(params);
		return prot.create(p);
	}
	
	public ConnectProtocol getProtocol(String protocol) {
		if (!this.protocols.containsKey(protocol)) {
			Sys.error("Connect protocol [0] not known!", null, protocol);
			return null;
		}
		return this.protocols.get(protocol);
	}
	
}
