package jz.soundsync.server.commands;

import jz.soundsync.server.SSCommand;
import jz.soundsync.server.SoundSyncUser;
import jz.soundsync.server.SoundSyncServer;
import jz.soundsync.server.net.SSUDPS;

public class LoginCommand implements SSCommand {

	@Override
	public String command() {
		return "login";
	}

	@Override
	public void command(SoundSyncServer server, SSUDPS data) {
		for (SoundSyncUser c : server.clients()) {
			if (c.name() != null && c.name().equals(data.args()[1])) {
				server.respond(data, this.command(), "name in use");
				return;
			}
		}
		
		data.client().login(data.args()[1], data.session());
		server.respond(data, this.command(), "success");
	}
	
	@Override
	public void client(SSUDPS data) {
		
	}
	
	@Override
	public String[] access() {
		return null;
	}

}
