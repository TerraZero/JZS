package jz.sys.module;

import java.util.ArrayList;
import java.util.List;

import jz.sys.reflect.Reflect;

public class ModHandler {
	
	private Reflect reflect;
	private ModInfo info;
	private boolean loaded;
	
	private List<ModHandler> childs; 

	public ModHandler(Reflect reflect) {
		this.reflect = reflect;
		this.info = null;
		this.loaded = false;
		this.childs = new ArrayList<ModHandler>();
	}
	
	public Reflect reflect() {
		return this.reflect;
	}
	
	public String name() {
		ModInfo info = this.info();
		if (info == null) {
			return this.reflect.name();
		}
		return info.name();
	}
	
	public String[] dependencies() {
		ModInfo info = this.info();
		if (info == null) {
			return new String[0];
		}
		return info.dependencies();
	}
	
	public List<ModHandler> childs() {
		return this.childs;
	}
	
	public void addChild(ModHandler child) {
		this.childs.add(child);
	}
	
	public ModInfo info() {
		if (this.info == null && this.reflect.hasAnnotation(ModInfo.class)) {
			this.info = this.reflect.annotation(ModInfo.class);
		}
		return this.info;
	}
	
	public ModHandler loaded() {
		this.loaded = true;
		return this;
	}
	
	public boolean isLoaded() {
		return this.loaded;
	}
	
	public boolean saveLoad() {
		return this.childs.isEmpty();
	}
	
	public ModHandler getSuper() {
		Reflect reflect = this.reflect.getSuper();
		if (reflect == null || !reflect.is(Module.class)) return null;
		return new ModHandler(reflect);
	}
	
}
