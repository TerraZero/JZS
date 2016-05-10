package jz.soundsync.server;

import jz.soundsync.server.net.SSUDPS;

public interface SSCommand {

	public String command();
	
	public void command(SoundSyncServer server, SSUDPS data);
	
	public void client(SSUDPS data);
	
	public default String[] access() {
		return new String[]{"login"};
	}
	
}
