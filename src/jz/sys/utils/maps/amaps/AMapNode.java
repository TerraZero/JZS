package jz.sys.utils.maps.amaps;

public class AMapNode {

	public int x;
	public int y;
	
	public int g;
	public int f;
	
	public AMapNode predecessor;
	
	public AMapNode(int x, int y) {
		this.x = x;
		this.y = y;
		
		this.g = 0;
		this.f = 0;
		
		this.predecessor = null;
	}
	
	public boolean is(AMapNode node) {
		return node.x == this.x && node.y == this.y;
	}
	
}
