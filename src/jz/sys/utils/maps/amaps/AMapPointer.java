package jz.sys.utils.maps.amaps;

public class AMapPointer<type> {

	private AMap<type> map;
	
	private int x;
	private int y;
	
	private boolean strict;
	
	public AMapPointer(AMap<type> map) {
		this.map = map;
		this.x = 0;
		this.y = 0;
		this.strict = false;
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
		if (this.strict && !this.valid(x, y)) return this;
		this.x = x;
		this.y = y;
		return this;
	}
	
	public AMapPointer<type> top() {
		if (this.strict && !this.valid(this.x, this.y - 1)) return this;
		this.y--;
		return this;
	}
	
	public AMapPointer<type> right() {
		if (this.strict && !this.valid(this.x + 1, this.y)) return this;
		this.x++;
		return this;
	}
	
	public AMapPointer<type> bottom() {
		if (this.strict && !this.valid(this.x, this.y + 1)) return this;
		this.y++;
		return this;
	}
	
	public AMapPointer<type> left() {
		if (this.strict && !this.valid(this.x - 1, this.y)) return this;
		this.x--;
		return this;
	}
	
	public AMapPointer<type> strict(boolean strict) {
		this.strict = strict;
		return this;
	}
	
	public boolean strict() {
		return this.strict;
	}
	
	public boolean valid() {
		 return this.valid(this.x, this.y);
	}
	
	public boolean valid(int x, int y) {
		return this.map.valid(x, y);
	}
	
}
