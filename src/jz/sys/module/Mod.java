package jz.sys.module;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Consumer;

import jz.sys.Sys;
import jz.sys.reflect.loader.ReflectFile;
import jz.sys.reflect.loader.ReflectLoader;

public class Mod<type extends Module> {
	
	private static List<ModHandler> modules;
	private static Map<Class<?>, Mod<?>> cache;
	
	static {
		Mod.init();
	}
	
	public static void init() {
		Mod.cache = new HashMap<Class<?>, Mod<?>>();
		
		Mod.modules = new ArrayList<ModHandler>();
		Map<String, ModHandler> map = new HashMap<String, ModHandler>();
		
		Mod.loadMods(map);
		Mod.filterMods(map);
		Mod.dependencieMods(map);
	}
	
	public static void loadMods(Map<String, ModHandler> map) {
		for (ReflectFile file : ReflectLoader.sysloader().load()) {
			if (file.reflect().implement(Module.class)) {
				ModHandler handler = new ModHandler(file.reflect());
				map.put(handler.name(), handler);
			}
		}
	}
	
	public static void filterMods(Map<String, ModHandler> map) {
		for (Iterator<Entry<String, ModHandler>> i = map.entrySet().iterator(); i.hasNext();) {
			Entry<String, ModHandler> entry = i.next();
			ModHandler parent = entry.getValue().getSuper();
			
			while (parent != null) {
				// get the right instance of mod handler
				parent = map.get(parent.name());
				parent.addChild(entry.getValue());
				parent = parent.getSuper();
			}
		}
	}
	
	public static void dependencieMods(Map<String, ModHandler> map) {
		for (Entry<String, ModHandler> entry : map.entrySet()) {
			Mod.dependencieTree(entry.getValue(), map);
		}
	}
	
	public static void dependencieTree(ModHandler handler, Map<String, ModHandler> map) {
		for (String name : handler.dependencies()) {
			if (map.containsKey(name)) {
				Mod.dependencieTree(map.get(name), map);
			} else {
				ModInfo info = handler.info();
				if (info != null && info.optional()) {
					Sys.log("Optional module [0] not loaded because the dependencie [1] can not be resolved", handler.name(), name);
				} else {
					Sys.error("Dependencie [0] can not be resolved for module [1]", null, name, handler.name());
				}
				return;
			}
		}
		if (!handler.isLoaded() && handler.saveLoad()) {
			Mod.modules.add(handler.loaded());
		}
	}
	
	@SuppressWarnings("unchecked")
	public static<type extends Module> Mod<type> get(Class<type> type) {
		Mod<type> loaded = null;
		try {
			loaded = (Mod<type>)Mod.cache.get(type);
		} catch (ClassCastException e) {
			Sys.error("Unexpected exception by loading the mod cache from class [0]", e, type);
		}
		if (loaded == null) {
			loaded = new Mod<type>(type);
			Mod.cache.put(type, loaded);
		}
		return loaded;
	}
	
	public static List<ModHandler> modules() {
		return Mod.modules;
	}
	
	private Class<type> type;
	private List<ModHandler> handlers;
	
	private boolean created;

	private Mod(Class<type> type) {
		this.type = type;
		this.handlers = null;
		this.created = false;
	}
	
	public Mod<type> load() {
		return this.load(false);
	}
	
	public Mod<type> load(boolean force) {
		if (!this.loaded() || force) {
			this.handlers = new ArrayList<ModHandler>();
			
			for (ModHandler handler : Mod.modules()) {
				if (handler.reflect().implement(this.type)) {
					this.handlers.add(handler);
				}
			}
		}
		return this;
	}
	
	public Mod<type> create() {
		return this.create(false);
	}
	
	public Mod<type> create(boolean force) {
		if (!this.created() || force) {
			for (ModHandler handler : this.load().handlers) {
				try {
					handler.reflect().instantiate();
				} catch (Exception e) {
					Sys.error("Mod create cause exception [0] on class [1]", e, e, handler.name());
				}
			}
		}
		return this;
	}
	
	public Mod<type> call(String function, Object... params) {
		for (ModHandler handler : this.create().handlers) {
			try {
				handler.reflect().call(function, params);
			} catch (Exception e) {
				Sys.error("Mod call cause exception [0] by function [1] on class [2]", e, e, function, handler.name());
			}
		}
		return this;
	}
	
	public Mod<type> foreach(Consumer<type> function) {
		for (ModHandler handler : this.create().handlers) {
			function.accept(handler.reflect().getReflect());
		}
		return this;
	}
	
	public Mod<type> each(Consumer<ModHandler> function) {
		for (ModHandler handler : this.create().handlers) {
			function.accept(handler);
		}
		return this;
	}
	
	public Mod<type> clean() {
		this.handlers = null;
		return this;
	}
	
	public boolean created() {
		return this.created;
	}
	
	public boolean loaded() {
		return this.handlers != null;
	}
	
	public List<ModHandler> handlers() {
		return this.load().handlers;
	}
	
}
