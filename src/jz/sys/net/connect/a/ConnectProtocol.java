package jz.sys.net.connect.a;

import jz.sys.net.connect.Connecter;
import jz.sys.reflect.params.Params;

public interface ConnectProtocol {

	public String name();
	
	public Connecter create(Params params);
	
}
