package jz.sys.utils;

import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

import jz.sys.utils.functions.D2MapIterator;

public class Lists {
	
	public static<type> void remove(List<type> list, Function<type, Boolean> function) {
		for (Iterator<type> iterator = list.iterator(); iterator.hasNext(); ) {
			if (function.apply(iterator.next())) {
				iterator.remove();
			}
		}
	}
	
	public static<type> void iterate(type[][] map, D2MapIterator<type> function) {
		for (int x = 0; x < map.length; x++) {
			for (int y = 0; y < map[0].length; y++) {
				map[x][y] = function.iterate(x, y, map[x][y]);
			}
		}
	}
	
}
