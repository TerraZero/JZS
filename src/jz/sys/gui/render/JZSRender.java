package jz.sys.gui.render;

import jz.sys.Sys;
import jz.sys.events.JZEHandler;

public class JZSRender {
	
	private static JZSRender instance;
	
	public static JZSRender instance() {
		if (JZSRender.instance == null) {
			JZSRender.instance = new JZSRender();
		}
		return JZSRender.instance;
	}
	
	
	
	public JZEHandler eExit;

	private Thread thread;
	
	private float deltaTime;
	private boolean stop;
	
	private Object sync;
	private Object syncLoop;
	private boolean interrupt;

	
	
	public JZSRender() {
		this.deltaTime = 1000f / 60f;
		this.sync = new Object();
		this.syncLoop = new Object();
		this.interrupt = false;
		
		this.eExit = new JZEHandler("exit");
	}
	
	
	
	public void start() {
		if (this.thread == null || this.stop) {
			this.thread = new Thread(() -> {
				JZSRender.this.loop();
				JZSRender.this.exit();
			});
			
			this.thread.start();
		}
	}
	
	public void exit() {
		this.eExit.fire("exit", this);
	}
	
	public void loop() {
		long nextTime = 0;
		float frameTime = 0;
		long current = System.currentTimeMillis();
		float delta = 0;
		
		while (!this.stop) {
			if (this.interrupt) {
				synchronized (this.sync) {
					try {
						this.sync.wait();
					} catch (InterruptedException e) {
						Sys.error("Loop can not wait!", e);
					}
				}
			}
			synchronized (this.syncLoop) {
				nextTime = System.currentTimeMillis();
				frameTime = nextTime - current;
				current = nextTime;
				
				while (frameTime > 0) {
					delta = (frameTime > this.deltaTime ? this.deltaTime : frameTime);
					this.tickUpdate(delta);
					frameTime -= delta;
				}
				this.tickRender();
			}
		}
	}
	
	public void stop() {
		this.stop = true;
	}
	
	public void interrupt(boolean interrupt) {
		if (this.interrupt != interrupt) {
			this.interrupt = interrupt;
			if (!this.interrupt) {
				synchronized (this.sync) {
					this.sync.notifyAll();
				}
			}
		}
	}
	
	public void tickUpdate(float delta) {
		
	}
	
	public void tickRender() {
		
	}
	
}
