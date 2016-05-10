package jz.sys.utils;

public final class Objects {

	public static Object[] append(Object[] array, Object... appends) {
		if (appends == null) return array;
		Object[] a = new Object[array.length + appends.length];
		
		for (int i = 0; i < array.length; i++) {
			a[i] = array[i];
		}
		for (int i = 0; i < appends.length; i++) {
			a[i + array.length] = appends[i];
		}
		return a;
	}
	
	public static Object[] prepend(Object[] array, Object... prepends) {
		if (prepends == null) return array;
		Object[] a = new Object[array.length + prepends.length];
		
		for (int i = 0; i < prepends.length; i++) {
			a[i] = prepends[i];
		}
		for (int i = 0; i < array.length; i++) {
			a[i + prepends.length] = array[i];
		}
		return a;
	}
	
}
