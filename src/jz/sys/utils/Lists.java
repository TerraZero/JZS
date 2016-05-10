package jz.sys.utils;

import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

public class Lists {
	
	public static<type> void remove(List<type> list, Function<type, Boolean> function) {
		for (Iterator<type> iterator = list.iterator(); iterator.hasNext(); ) {
			if (function.apply(iterator.next())) {
				iterator.remove();
			}
		}
	}
	
}
