package jz.sys.module.api;

import jz.sys.module.Module;

public interface SysModule extends Module {

	public default void hookInit() {
		
	}
	
	public default void hookExit(int code) {
		
	}
	
}
