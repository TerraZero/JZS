package jz.sys.utils.validates;

import jz.sys.utils.validates.api.TZUValidate;

public class TZUValidPosNumber implements TZUValidate {

	@Override
	public String name() {
		return "posnumber";
	}

	@Override
	public boolean validate(String value) {
		try {
			return Integer.parseInt(value) >= 0;
		} catch (NumberFormatException e) {
			return false;
		}
	}

}
