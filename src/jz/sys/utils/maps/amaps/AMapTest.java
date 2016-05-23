package jz.sys.utils.maps.amaps;

import jz.sys.utils.Lists;

public class AMapTest {

	public static void main(String[] args) {
		String[][] s = new String[5][5];
		
		Lists.iterate(s, (x, y, v) -> {
			if (y == 3 && x != 0) {
				return "10";
			}
			return "1";
		});
		
		final AMap<String> map = new AMap<String>(s);
		
		map.setCalc((item) -> {
			System.out.println(item);
			return Integer.parseInt(item);
		});
		
		AMapPath<String> path = map.path(2, 2, 4, 4);
		
		path.each((node) -> {
			System.out.println(node.x + ":" + node.y);
		});
		System.out.println(path.cost());
	}
	
}
