package jz.sys.utils.maps;

public class AMapPointer<type> {

	private AMap<type> map;
	
	private int x;
	private int y;
	
	public AMapPointer(AMap<type> map) {
		this.map = map;
		this.x = 0;
		this.y = 0;
	}
	
	public int x() {
		return this.x;
	}
	
	public int y() {
		return this.y;
	}
	
	public AMap<type> map() {
		return this.map;
	}
	
	public type get() {
		return this.map.get(this.x, this.y);
	}
	
	public AMapPointer<type> pos(int x, int y) {
		this.x = x;
		this.y = y;
		return this;
	}
	
	public AMapPointer<type> top() {
		this.y--;
		return this;
	}
	
	public AMapPointer<type> right() {
		this.x++;
		return this;
	}
	
	public AMapPointer<type> bottom() {
		this.y++;
		return this;
	}
	
	public AMapPointer<type> left() {
		this.x--;
		return this;
	}
	
}
