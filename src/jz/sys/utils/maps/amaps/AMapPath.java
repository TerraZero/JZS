package jz.sys.utils.maps.amaps;

import java.util.ArrayList;
import java.util.List;

public class AMapPath<type> {

	private List<AMapNode<type>> path;
	
	public AMapPath() {
		this.path = new ArrayList<AMapNode<type>>();
	}
	
	public List<AMapNode<type>> path() {
		return this.path;
	}
	
	public AMapPath<type> reset() {
		this.path.clear();
		return this;
	}
	
	public AMapNode<type> get(int position) {
		return this.path.get(position);
	}
	
	public type value(int position) {
		return this.path.get(position).value();
	}
	
	public AMapPath<type> add(AMapNode<type> item) {
		this.path.add(item);
		return this;
	}
	
	public int length() {
		return this.path.size();
	}
 	
}
