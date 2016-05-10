package jz.soundsync.server;

import java.util.HashMap;
import java.util.Map;

public class UserData {

	protected Map<String, Map<String, Object>> data;
	
	public UserData() {
		this.data = new HashMap<String, Map<String, Object>>();
	}
	
	public Map<String, Object> dataset(String dataset) {
		Map<String, Object> data = this.data.get(dataset);
		if (data == null) {
			data = new HashMap<String, Object>();
			this.data.put(dataset, data);
		}
		return data;
	}
	
	public void set(String dataset, String key, Object value) {
		Map<String, Object> data = this.dataset(dataset);
		data.put(key, value);
	}
	
	public void remove(String dataset, String key) {
		Map<String, Object> data = this.dataset(dataset);
		data.remove(key);
		if (data.isEmpty()) {
			this.data.remove(dataset);
		}
	}
	
	public<type> type get(String dataset, String key) {
		return this.get(dataset, key, null);
	}
	
	@SuppressWarnings("unchecked")
	public<type> type get(String dataset, String key, type fallback) {
		Map<String, Object> data = this.dataset(dataset);
		Object value = data.get(key);
		if (value == null) {
			return fallback;
		} else {
			return (type)value;
		}
	}
	
	public void clear() {
		this.data.clear();
	}
	
}
