package jz.sys.utils.maps.amaps;

public class AMap<type> {

	private type[][] map;
	
	private AMapCalc<type> calc;
	
	public AMap(type[][] map) {
		this.setMap(map);
		this.calc = null;
	}
	
	public AMap<type> setMap(type[][] map) {
		this.map = map;
		return this;
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
	
	public AMapPath<type> path(int x, int y, int tx, int ty) {
		AMapList open = new AMapList();
		AMapList close = new AMapList();
		
		AMapNode target = new AMapNode(tx, ty);
		
		open.add(new AMapNode(x, y), 0);
		do {
			AMapNode node = open.pop();
			
			if (node.is(target)) {
				return new AMapPath<type>(node);
			}
			close.add(node);
			
			AMapNode[] successors = this.nodeSuccessor(node);
			this.calcSuccessors(successors, open, close, node, target);
		} while (open.length() != 0);
		
		return null;
	}
	
	public AMapRange range(int x, int y, int range) {
		AMapList open = new AMapList();
		AMapList close = new AMapList();
		AMapNode start = new AMapNode(x, y);
		
		open.add(start, 0);
		do {
			AMapNode node = open.pop();
			
			if (this.g(node) > range) continue;
			close.add(node);
			
			AMapNode[] successors = this.nodeSuccessor(node);
			this.calcSuccessors(successors, open, close, node, null);
		} while (open.length() != 0);
		
		return new AMapRange(start, range, close);
	}
	
	public void calcSuccessors(AMapNode[] successors, AMapList open, AMapList close, AMapNode node, AMapNode target) {
		for (AMapNode successor : successors) {
			if (successor == null || close.contain(successor)) continue;
			
			int dg = this.g(node) + this.calc.cost(this.get(successor));
			
			if (open.contain(successor) && dg >= open.entry(successor).g) continue;
			
			successor.predecessor = node;
			successor.g = dg;
			
			int f = dg;
			if (target != null) {
				f += this.h(successor, target);
			}
			
			if (open.contain(successor)) {
				open.entry(successor).f = f;
			} else {
				successor.f = f;
				open.add(successor);
			}
		}
	}
	
	public AMapNode[] nodeSuccessor(AMapNode node) {
		AMapNode[] successors = new AMapNode[4];
		
		// top
		if (this.valid(node.x, node.y - 1)) {
			successors[0] = this.node(node.x, node.y - 1); 
		}
		
		// right
		if (this.valid(node.x + 1, node.y)) {
			successors[1] = this.node(node.x + 1, node.y); 
		}
		
		// bottom
		if (this.valid(node.x, node.y + 1)) {
			successors[2] = this.node(node.x, node.y + 1); 
		}
		
		// left
		if (this.valid(node.x - 1, node.y)) {
			successors[3] = this.node(node.x - 1, node.y); 
		}
		
		return successors;
	}
	
	public type get(AMapNode node) {
		return this.map[node.x][node.y];
	}
	
	public int h(AMapNode node, AMapNode target) {
		return Math.abs(node.x - target.x) + Math.abs(node.y - target.y);
	}
	
	public int g(AMapNode node) {
		int g = 0;
		
		while (node.predecessor != null) {
			g += this.calc.cost(this.get(node));
			node = node.predecessor;
		}
		return g;
	}
	
}
