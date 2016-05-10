package jz.soundsync.server;

import java.util.ArrayList;
import java.util.List;

import jz.soundsync.SoundSync;
import jz.soundsync.server.net.SSUDPS;
import jz.sys.Sys;
import jz.sys.net.data.udp.UDPData;
import jz.sys.net.data.udp.UDPSData;
import jz.sys.net.data.udp.UDPSocket;
import jz.sys.reflect.helper.InterWrapper;

public class SoundSyncServer implements Runnable {
	
	public static void main(String[] args) {
		SoundSyncServer s = new SoundSyncServer("TerraZero");
		s.start();
	}
	
	private UDPSocket udp;
	private List<SoundSyncUser> clients;
	private Thread server;
	
	private List<SSCommand> commands;
	private boolean run;
	private String admin;
	
	public SoundSyncServer(String admin) {
		this.admin = admin;
		this.udp = UDPSocket.create("soundsyncserver", SoundSync.serverPort);
		this.clients = new ArrayList<SoundSyncUser>();
		this.server = new Thread(this);
		
		Sys.eExit.add("SoundSyncServer", (params) -> {
			SoundSyncServer.this.close();
		});
	}
	
	public String admin() {
		return this.admin;
	}
	
	public void start() {
		this.server.start();
		Sys.log("SoundSync - Server start!");
	}

	@Override
	public void run() {
		try {
			this.init();
			 do {
				 this.executeData(this.udp.receive());
			 } while (this.run);
		} catch (Exception e) {
			Sys.error("Server Thread has an unexpected error!", e);
		}
		Sys.exit(0);
	}
	
	public void executeData(UDPData data) {
		SSUDPS exe = new SSUDPS(data);
		exe.client(this.getClient(data.ip(), data.port()));
		this.execute(exe);
	}
	
	public void execute(SSUDPS data) {
		SSCommand command = this.command(data.args()[0]);
		if (command == null) {
			Sys.error("No such command [0]!", null, data.args()[0]);
			this.respond(data, "server", "error", Sys.placeholder("No such command [0]!", data.args()[0]));
		} else {
			System.out.println(data.client().name());
			System.out.println(this.admin());
			if (data.client().name() != null && data.client().name().equals(this.admin()) || data.access(command.access())) {
				Sys.log("Execute command [0] by ip [1] and port [2]", data.args()[0], data.ip(), data.port());
				command.command(this, data);
			} else {
				if (data.expiredSession()) {
					Sys.warn("User have a expired session!");
					this.respond(data, "server", "expired");
				} else {
					Sys.warn("User have not the permission to execute the command [0]!", data.args()[0]);
					this.respond(data, "server", "warn", Sys.placeholder("User have not the permission to execute the command [0]!", data.args()[0]));
				}
			}
		}
	}
	
	public SSCommand command(String command) {
		for (SSCommand c : this.commands) {
			if (c.command().equals(command)) {
				return c;
			}
		}
		return null;
	}
	
	public void respond(UDPSData data, String... args) {
		UDPSData answer = new UDPSData(data.ip(), data.port(), args);
		this.udp.send(answer);
	}
	
	public SoundSyncUser getClient(String ip, int port) {
		for (SoundSyncUser c : this.clients) {
			if (c.ip().equals(ip) && c.port() == port) return c;
		}
		SoundSyncUser client = new SoundSyncUser(ip, port);
		this.clients.add(client);
		return client;
	}
	
	public void init() {
		this.run = true;
		
		InterWrapper<SSCommand> commands = new InterWrapper<SSCommand>(SSCommand.class);
		commands.create();
		this.commands = commands.inters();
		
		this.udp.timeout(0);
		this.udp.bind();
	}
	
	public void stop() {
		this.run = false;
		Sys.log("Try to stop the server!");
		for (SoundSyncUser u : this.clients) {
			this.udp.send(new SSUDPS(u.ip(), u.port(), "server", "close"));
		}
	}
	
	public void close() {
		Sys.log("Server closed!");
	}
	
	public List<SoundSyncUser> clients() {
		return this.clients;
	}
	
}
