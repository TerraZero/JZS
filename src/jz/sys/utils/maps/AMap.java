package jz.sys.utils.maps;

public class AMap<type> {

	private type[][] map;
	
	public AMap(type[][] map) {
		this.map = map;
	}
	
	public type[][] map() {
		return this.map;
	}
	
	public type get(int x, int y) {
		return this.map[x][y];
	}
	
	public int width() {
		return this.map.length;
	}
	
	public int height() {
		return this.map[0].length;
	}
	
}
