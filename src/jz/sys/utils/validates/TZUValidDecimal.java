package jz.sys.utils.validates;

import jz.sys.utils.validates.api.TZUValidate;

public class TZUValidDecimal implements TZUValidate {

	@Override
	public String name() {
		return "decimal";
	}

	@Override
	public boolean validate(String value) {
		try {
			Double.parseDouble(value);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

}
