package jz.sys.utils.maps.amaps;

public class AMapRange {

	private AMapNode start;
	private int range;
	private AMapList list;
	
	public AMapRange(AMapNode node, int range, AMapList list) {
		this.start = node;
		this.range = range;
		this.list = list;
	}
	
	public AMapNode start() {
		return this.start;
	}
	
	public int range() {
		return this.range;
	}
	
	public AMapList list() {
		return this.list;
	}
	
}
