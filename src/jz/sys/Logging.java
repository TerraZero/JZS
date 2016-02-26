package jz.sys;

public interface Logging {

	public default void log(String message, Object... tokens) {
		Sys.log(message, tokens);
	}
	
	public default void error(String message, Object... tokens) {
		Sys.error(message, null, tokens);
	}
	
	public void exception(Exception e);
	
}
