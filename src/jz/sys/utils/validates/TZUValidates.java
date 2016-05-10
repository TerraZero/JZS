package jz.sys.utils.validates;

import java.util.HashMap;
import java.util.Map;

import jz.sys.Sys;
import jz.sys.reflect.helper.InterWrapper;
import jz.sys.utils.validates.api.TZUValidate;

public class TZUValidates {

	private static Map<String, TZUValidate> validates;
	
	static {
		TZUValidates.init();
	}
	
	public static void init() {
		TZUValidates.validates = new HashMap<String, TZUValidate>();
		for (TZUValidate validate : new InterWrapper<TZUValidate>(TZUValidate.class).create()) {
			TZUValidates.validates.put(validate.name(), validate);
		}
	}
	
	public static boolean valid(String value, String... validates) {
		for (String validate : validates) {
			TZUValidate v = TZUValidates.validates.get(validate);
			if (v == null) {
				Sys.warn("Validate type [0] not defined!", validate);
			} else {
				if (!v.validate(value)) return false; 
			}
		}
		return true;
	}
	
}
