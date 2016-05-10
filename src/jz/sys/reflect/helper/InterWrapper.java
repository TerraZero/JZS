package jz.sys.reflect.helper;

import java.util.ArrayList;
import java.util.List;

import jz.sys.Sys;
import jz.sys.reflect.Reflect;
import jz.sys.reflect.loader.ReflectFile;
import jz.sys.reflect.loader.ReflectLoader;

public class InterWrapper<type> {

	private boolean created;
	private List<Reflect> reflects;
	
	public InterWrapper(Class<type> inter) {
		this.created = false;
		this.reflects = new ArrayList<Reflect>();
		
		for (ReflectFile file : ReflectLoader.sysloader().load()) {
			if (file.reflect().implement(inter)) {
				this.reflects.add(file.reflect());
			}
		}
	}
	
	public List<type> create() {
		if (this.created) {
			Sys.warn("Already be created!");
			return this.inters();
		}
		for (Reflect reflect : this.reflects) {
			try {
				reflect.instantiate();
			} catch (Exception e) {
				Sys.error("Can not create!", e);
			}
		}
		this.created = true;
		return this.inters();
	}
	
	public boolean created() {
		return this.created;
	}
	
	public List<Reflect> reflects() {
		return this.reflects;
	}
	
	public List<type> inters() {
		if (!this.created) {
			Sys.warn("Interfaces are not created!");
			return null;
		}
		List<type> inters = new ArrayList<type>(this.reflects.size());
		
		for (Reflect reflect : this.reflects) {
			inters.add(reflect.getReflect());
		}
		return inters;
	}
	
}
