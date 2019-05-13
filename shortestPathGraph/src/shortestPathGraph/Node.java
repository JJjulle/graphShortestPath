package shortestPathGraph;

import java.util.ArrayList;

public class Node {
	
	private String data;
	private ArrayList<Node> adjacent = new ArrayList<Node>();
	private ArrayList<Integer> costs = new ArrayList<Integer>();
	private int id;
	public Node(String data, Node adjacent, Integer costs, int id) {
		super();
		this.data = data;
		this.adjacent.add(adjacent);
		this.costs.add(costs);
		this.id = id;
	}
	public Node(String data, int id) {
		super();
		this.data = data;
		this.id = id;
	}
	
	public void addAdjecent(Node adjacent, Integer costs) {
		this.adjacent.add(adjacent);
		this.costs.add(costs);
		
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public ArrayList<Node> getAdjacent() {
		return adjacent;
	}
	public void setAdjacent(ArrayList<Node> adjacent) {
		this.adjacent = adjacent;
	}
	public ArrayList<Integer> getCosts() {
		return costs;
	}
	public void setCosts(ArrayList<Integer> costs) {
		this.costs = costs;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getInfo() {
		String res = this.data;
		res = res + "\nadjacent       cost";
		for(int i = 0; i < this.adjacent.size(); i++) {
			res = res + "\n"+this.adjacent.get(i).data+"      "+this.costs.get(i);
		}
		
		
		
		return res;
	}
	
	
	

}
