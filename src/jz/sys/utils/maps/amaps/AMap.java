package jz.sys.utils.maps.amaps;

import java.util.ArrayList;
import java.util.List;

public class AMap<type> {

	private type[][] map;
	
	private AMapCalc<type> calc;
	
	public AMap(type[][] map) {
		this.map = map;
		this.calc = null;
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
	
	public boolean valid(int x, int y) {
		return x >= 0 && y >= 0 && x < this.width() && y < this.height();
	}
	
	public AMapNode node(int x, int y) {
		return new AMapNode(x, y);
	}
	
	public AMapPointer<type> pointer() {
		return this.pointer(0, 0);
	}
	
	public AMapPointer<type> pointer(int x, int y) {
		return new AMapPointer<type>(this).pos(x, y);
	}
	
	public AMap<type> setCalc(AMapCalc<type> calc) {
		this.calc = calc;
		return this;
	}
	
	public AMapCalc<type> getCalc() {
		return this.calc;
	}
	
	public AMapPath<type> calc(int x, int y, int tx, int ty) {
		AMapPath<type> path = new AMapPath<type>();
		AMapList open = new AMapList();
		AMapList close = new AMapList();
		
		open.add(new AMapNode(x, y), 0);
		
		do {
			AMapNode node = open.pop();
			
			if (node.x == tx && node.y == ty) {
				return path;
			}
			close.add(node);
			
			// right
			if (this.valid(node.x + 1, node.y)) {
				AMapNode r = this.node(node.x + 1, node.y);
				
			}
		} while (open.length() != 0);
		
		return path;
	}
	
	public AMapNode[] nodeSuccessor(AMapNode node) {
		AMapNode[] successors = new AMapNode[4];
		
		// top
		if (this.valid(node.x, node.y - 1)) {
			successors[0] = this.node(node.x, node.y - 1); 
		}
		
		// right
		if (this.valid(node.x + 1, node.y)) {
			
		}
		
		return successors;
	}
	
}
