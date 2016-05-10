package jz.soundsync.server.commands;

import jz.soundsync.server.SSCommand;
import jz.soundsync.server.SoundSyncServer;
import jz.soundsync.server.net.SSUDPS;

public class ExitCommand implements SSCommand {

	@Override
	public String command() {
		return "exit";
	}

	@Override
	public void command(SoundSyncServer server, SSUDPS data) {
		server.stop();
	}

	@Override
	public void client(SSUDPS data) {
		
	}
	
	@Override
	public String[] access() {
		return new String[]{"admin"};
	}

}
