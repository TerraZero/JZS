package jz.sys.utils.validates;

import jz.sys.utils.validates.api.TZUValidate;

public class TZUValidNumber implements TZUValidate {

	@Override
	public String name() {
		return "number";
	}

	@Override
	public boolean validate(String value) {
		try {
			Integer.parseInt(value);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

}
