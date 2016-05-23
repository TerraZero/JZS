package jz.sys.utils.maps.amaps;

import java.util.function.Consumer;

public class AMapPath<type> {

	private AMapNode target;
	private AMap<type> map;
	
	public AMapPath(AMapNode target, AMap<type> map) {
		this.target = target;
		this.map = map;
	}
	
	public AMapNode target() {
		return this.target;
	}
	
	public AMapPath<type> each(Consumer<AMapNode> consumer) {
		AMapNode node = this.target;
		
		do {
			consumer.accept(node);
			node = node.predecessor;
		} while (node != null);
		return this;
	}
	
	public int cost() {
		return this.target.g;
	}
 	
}
