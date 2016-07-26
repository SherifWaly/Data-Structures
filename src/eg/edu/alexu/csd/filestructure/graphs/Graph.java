package eg.edu.alexu.csd.filestructure.graphs;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;

public class Graph implements IGraph {
	private BufferedReader reader;
	private ArrayList<ArrayList<Node>>edge;
	private ArrayList<ArrayList<Integer>>adj;
	private int numOfVertices = 0;
	private int numOfEdges = 0;
	private ArrayList<Integer>vertices;
	private ArrayList<Integer>order;
	public Graph(){
		adj = new ArrayList<ArrayList<Integer>>();
		edge = new ArrayList<ArrayList<Node>>();
		vertices = new ArrayList<Integer>();
	}
	public void readGraph(File file) {
		adj.clear();
		try {
			reader = new BufferedReader(new FileReader(file));
			String firstLine = reader.readLine();
			String []ve = firstLine.split(" +");
			int v = Integer.parseInt(ve[0]);
			int e = Integer.parseInt(ve[1]);
			numOfVertices = v;
			numOfEdges = e;
			adj.clear();
			edge.clear();
			vertices.clear();
			for(int i = 0 ; i < v ; i++){
				vertices.add(i);
				edge.add(new ArrayList<Node>());
				adj.add(new ArrayList<Integer>());
			}
			for(int i = 0 ; i < e ; i++){
				String line = reader.readLine();
				String []edgeComponent = line.split(" +");
				int x = Integer.parseInt(edgeComponent[0]);
				int y = Integer.parseInt(edgeComponent[1]);
				int w = Integer.parseInt(edgeComponent[2]);
				adj.get(x).add(y);
				edge.get(x).add(new Node(y,w));
			}
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	@Override
	public int size() {
		return numOfEdges;
	}

	@Override
	public ArrayList<Integer> getVertices() {
		return vertices;
	}

	@Override
	public ArrayList<Integer> getNeighbors(int v) {
		return adj.get(v);
	}

	@Override
	public void runDijkstra(int src, int[] distances) {
		order = new ArrayList<Integer>();
		//distances = new int[numOfVertices];
		Arrays.fill(distances, Integer.MAX_VALUE/2);
		distances[src] = 0;
		PriorityQueue<Node> q = new PriorityQueue<Node>();
		q.add(new Node(src,0));
		while(!q.isEmpty()){
			Node p = q.poll();
			if(distances[p.to] < p.weight){
				continue;
			}
			order.add(p.to);
			ArrayList<Node>list = edge.get(p.to);
			for(Node x : list){
				if(distances[x.to] > p.weight + x.weight){
					distances[x.to] = p.weight + x.weight;
					q.add(new Node(x.to,distances[x.to]));
				}
			}
		}
	}

	@Override
	public ArrayList<Integer> getDijkstraProcessedOrder() {
		return order;
	}

	@Override
	public boolean runBellmanFord(int src, int[] distances) {
		order = new ArrayList<Integer>();
		//distances = new int[numOfVertices];
		Arrays.fill(distances, Integer.MAX_VALUE/2);
		distances[src] = 0;
		for(int i = 0 ; i < numOfVertices-1 ; i++){
			for(int j = 0 ; j < numOfVertices ; j++){
				ArrayList<Node>list = edge.get(j);
				for(Node x : list){
					distances[x.to] = Math.min(distances[x.to], distances[j] + x.weight);
				}
			}
		}
		for(int j = 0 ; j < numOfVertices ; j++){
			ArrayList<Node>list = edge.get(j);
			for(Node x : list){
				if(distances[x.to] > distances[j] + x.weight){
					return false;
				}
			}
		}
		return true;
	}
	class Node implements Comparable<Node>{
		int to;
		Integer weight;
		public Node(int to,int weight){
			this.to = to;
			this.weight = weight;
		}
		@Override
		public int compareTo(Node o) {
			return weight.compareTo(o.weight);
		}
	}
}
