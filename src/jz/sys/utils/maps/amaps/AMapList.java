package jz.sys.utils.maps.amaps;

import java.util.ArrayList;
import java.util.List;

public class AMapList {

	private List<AMapNode> nodes;
	
	public AMapList() {
		this.nodes = new ArrayList<AMapNode>();
	}
	
	public List<AMapNode> nodes() {
		return this.nodes;
	}
	
	public AMapList add(AMapNode node) {
		this.nodes.add(node);
		return this;
	}
	
	public AMapList add(AMapNode node, int f) {
		node.f = f;
		this.nodes.add(node);
		return this;
	}
	
	public AMapNode pop() {
		int pos = 0;
		int f = -1;
		int i = 0;
		
		for (AMapNode node : this.nodes) {
			if (f == -1 || node.f < f) {
				f = node.f;
				pos = i; 
			}
			i++;
		}
		
		AMapNode node = this.nodes.get(pos);
		this.nodes.remove(pos);
		return node;
	}
	
	public int length() {
		return this.nodes.size();
	}
	
	public AMapNode entry(AMapNode instance) {
		for (AMapNode node : this.nodes) {
			if (node.is(instance)) {
				return node;
			}
		}
		return null;
	}
	
	public boolean contain(AMapNode node) {
		for (AMapNode i : this.nodes) {
			if (i.x == node.x && i.y == node.y) return true;
		}
		return false;
	}
	
}
