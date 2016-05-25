package jz.sys.utils.maps.amaps;

import java.util.function.Consumer;

public class AMapPath<type> {

	private AMapNode target;
	
	public AMapPath(AMapNode target) {
		this.target = target;
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
