package jz.sys.utils.maps.amaps;

import jz.sys.utils.Lists;

public class AMapTest {

	public static void main(String[] args) {
		String[][] s = new String[5][5];
		
		Lists.iterate(s, (x, y, v) -> {
			return x + ":" + y;
		});
		
		AMap<String> map = new AMap<String>(s);
		
		AMapPointer<String> p = map.pointer(2, 3);
		System.out.println(p.strict(true).get());
		
		for (int i = 0; i < 10; i++) {
			p.bottom();
		}
		System.out.println(p.get());
		map.setCalc((i) -> {
			return i.value().length();
		});
		
		map.calc(2, 2);
	}
	
}
