import java.util.*;

public class Graph {
	final public int MAX = 1000;
	private int v, e;
	public List<Node> node;
	public List<Edge> edge;
	private boolean connected[][];
	public float distance[][];
	public float weight[][];
	public float cost;
	public boolean new_edge[][];
	public ArrayList<ArrayList<Edge>> list_srg;
	public Graph(int v, int e, List<Node> node, List<Edge> edge,
			float distance[][],ArrayList<ArrayList<Edge>> list_srg) {
		// Only allow positive number of vertices
		if (v > 0) {
			this.node = node;
			this.edge = edge;
			this.e = 0;
			this.v = v;
			this.distance = distance;
			weight = new float[node.size()][node.size()];
			// Connected array is automatically initialized with “false”
			connected = new boolean[v][v];
			cost = Cost();
			new_edge = new boolean[v][v];
			this.list_srg = list_srg;
		}
	}

	public boolean add(Node u, Node v) {
		if (!isValidNode(u) || !isValidNode(v) || (u == v) || contains(u, v))
			return false;
		connected[u.getId()][v.getId()] = true;
		connected[v.getId()][u.getId()] = true;
		this.e++;
		return true;
	}

	public boolean remove(Node u, Node v) {
		if (!isValidNode(u) || !isValidNode(v) || (u == v) || !contains(u, v))
			return false;
		connected[u.getId()][v.getId()] = false;
		connected[v.getId()][u.getId()] = false;
		this.e--;
		return true;
	}

	public List<Node> adj(Node u) {
		if (!isValidNode(u))
			return null;
		LinkedList<Node> adjList = new LinkedList<Node>();
		for (int i = 0; i < this.node.size(); i++)
			if (connected[u.getId()][i])
				adjList.add(node.get(i));
		return (List<Node>) Collections.unmodifiableList(adjList);
	}

	public boolean contains(Node u, Node v) {
		if (!isValidNode(u) || !isValidNode(v))
			return false;
		else
			return connected[u.getId()][v.getId()];
	}

	public int v() {
		return v;
	}

	public int e() {
		return e;
	}

	// dijkstra
	public List<Node> dijkstra(Node src, Node dest) {
		List<Node> workpath1 = new ArrayList<Node>();
		int workpath[] = new int[node.size()];
		float minDistances[] = new float[MAX];
		boolean check[] = new boolean[MAX];

		check[src.getId()] = true;
		// khoi tao mang khoang cach
		for (int i = 0; i < node.size(); i++) {
			// workpath[i] = i;
			if (connected[src.getId()][node.get(i).getId()]) {
				minDistances[i] = distance[src.getId()][node.get(i).getId()];
				// System.out.println(i + " : " +
				// minDistances[node.get(i).getId()]);
			} else {
				if (node.get(i) != src) {
					minDistances[i] = Float.MAX_VALUE;
					// System.out.println(i + " : MAX");
				} else {
					minDistances[node.get(i).getId()] = 0;
				}
			}
			if (node.get(i) != src)
				check[node.get(i).getId()] = false;
			// System.out.println("Node"+node.get(i).getId() + " "
			// +check[node.get(i).getId()]);
		}

		List<Node> adj1 = adj(src);
		// System.out.println(adj1);
		for (int i = 0; i < adj1.size(); i++) {
			workpath[adj1.get(i).getId()] = src.getId();
			// System.out.println( adj1.get(i).getId() +">>>" +
			// workpath[adj1.get(i).getId()]);
		}
		minDistances[src.getId()] = 0;

		for (int i = 0; i < node.size(); i++) {
			float min = Float.MAX_VALUE;
			int minV = -1;
			for (int w = 0; w < node.size(); w++) {
				if (minDistances[node.get(w).getId()] < min
						&& !check[node.get(w).getId()]) {
					minV = node.get(w).getId();
					min = minDistances[node.get(w).getId()];
				}
			}

			if (minV == -1)
				break;
			check[minV] = true;
			// Them v vao trong workpath
			for (int w = 0; w < node.size(); w++) {
				if (connected[node.get(minV).getId()][node.get(w).getId()]) {
					if (minDistances[node.get(w).getId()] > minDistances[node
							.get(minV).getId()]
							+ distance[node.get(minV).getId()][node.get(w)
									.getId()]) {
						minDistances[node.get(w).getId()] = minDistances[node
								.get(minV).getId()]
								+ distance[node.get(minV).getId()][node.get(w)
										.getId()];
						workpath[node.get(w).getId()] = node.get(minV).getId();
					}
				}
			}
		}
		// System.out.println("sadkjshdkjashsakjdhsajdhajka");
		while (dest != src) {
			workpath1.add(dest);
			// System.out.print(dest + "-");
			dest = node.get(workpath[dest.getId()]);
		}
		// for (int i = 0; i < node.size(); i++) {
		// System.out.println("Parent of " + node.get(i).getId() + ": " +
		// workpath[node.get(i).getId()]);
		// }
		workpath1.add(src);

		for (int i = 0; i < node.size(); i++) {
			for (int j = 0; j < node.size(); j++) {
				weight[i][j] = distance[i][j];
			}
		}
		//Loai bo duong lam viec va nguy co chung
		for (int i = 0; i <= workpath1.size() - 2; i++) {
			// System.out.println(i);
			weight[workpath1.get(i).getId()][workpath1.get(i + 1).getId()] = Float.MAX_VALUE;
			weight[workpath1.get(i + 1).getId()][workpath1.get(i).getId()] = Float.MAX_VALUE;
			for (int j = 0; j < list_srg.size(); j++) {
				for (int k = 0; k < list_srg.get(j).size(); k++) {
//					System.out.println(workpath1.get(i+1).getId() + "-" + workpath1.get(i).getId()
//							+ "_ " + list_srg.get(j).get(k).getSource() + "-" + list_srg.get(j).get(k).getDestination());
//					System.out.println("Nguy co chung : "+ list_srg.get(j).get(k).getSource() + "-" + list_srg.get(j).get(k).getDestination());
					if(workpath1.get(i+1).getId() == list_srg.get(j).get(k).getSource() && 
							workpath1.get(i).getId() == list_srg.get(j).get(k).getDestination()){
							for (int b = 0; b < list_srg.get(j).size(); b++) {
								weight[list_srg.get(j).get(b).getSource()][list_srg.get(j).get(b).getDestination()] = Float.MAX_VALUE;
								weight[list_srg.get(j).get(b).getDestination()][list_srg.get(j).get(b).getSource()] = Float.MAX_VALUE;
						}
					}
				}
			}
		}
		
		for (int i = 0; i < node.size(); i++) {
			for (int j = 0; j < node.size(); j++) {
				if (weight[i][j] != Float.MAX_VALUE && connected[i][j]) {
					weight[i][j] = 0;
					weight[j][i] = 0;
				}
			}
		}
		return workpath1;
	}
// Dijkstra backup	
	public List<Node> dijktra_backup(Node src, Node dst, float[][] distances){
		List<Node> backup_path = new ArrayList<Node>();
		boolean[] mark = new boolean[node.size()];
		float[] minDis = new float[node.size()];
		int[] parent = new int[node.size()];
		
		
		// khoi tao
		for (int i = 0; i < node.size(); i++){
			minDis[node.get(i).getId()] = distances[src.getId()][node.get(i).getId()];
			mark[node.get(i).getId()] = false;
			if(distances[src.getId()][node.get(i).getId()] != Float.MAX_VALUE)
				parent[node.get(i).getId()] = src.getId();
			else
				parent[node.get(i).getId()] = node.get(i).getId();
		}
		
		//
		mark[src.getId()] = true;
		for(int i = 0; i < node.size(); i++) {
			float min = Float.MAX_VALUE;
			int minV = -1;
			for (int w = 0; w < node.size(); w++) {
				if (minDis[node.get(w).getId()] < min
						&& !mark[node.get(w).getId()]) {
					minV = node.get(w).getId();
					min = minDis[node.get(w).getId()];
				}
			}

			if (minV == -1 || minV == dst.getId())
				break;
			mark[minV] = true;
			
			for (int j = 0; j < node.size(); j++) {
				if(distances[minV][node.get(j).getId()]!= Float.MAX_VALUE &&
						minDis[node.get(j).getId()] > minDis[minV] + distances[node.get(j).getId()][minV]){
					minDis[node.get(j).getId()] = minDis[minV] + distances[node.get(j).getId()][minV];
					parent[node.get(j).getId()] = minV;
				}
			}
		}
	
		while (dst != src) {
			backup_path.add(dst);
			//System.out.print(dst + "-");
			dst = node.get(parent[dst.getId()]);
		}
		backup_path.add(src);
		
		//Update lai ma tran distance
		
		for (int i = 0; i <= backup_path.size() - 2; i++) {
			// System.out.println(i);
//			weight[workpath1.get(i).getId()][workpath1.get(i + 1).getId()] = Float.MAX_VALUE;
//			weight[workpath1.get(i + 1).getId()][workpath1.get(i).getId()] = Float.MAX_VALUE;
			if(connected[backup_path.get(i).getId()][backup_path.get(i+1).getId()] == false){
				connected[backup_path.get(i).getId()][backup_path.get(i+1).getId()] = true;
				cost = cost + distances[backup_path.get(i).getId()][backup_path.get(i+1).getId()];
				//System.out.println(backup_path.get(i).getId()+"--"+backup_path.get(i+1).getId());
				new_edge[backup_path.get(i).getId()][backup_path.get(i+1).getId()] = true;
				new_edge[backup_path.get(i+1).getId()][backup_path.get(i).getId()] = true;
			}
			// System.out.println(workpath1.get(i).getId() + " " +
			// workpath1.get(i+1).getId() + " "+
			// weight[workpath1.get(i).getId()][workpath1.get(i+1).getId()]);
		}
		
		return backup_path;
	}

	
	public void displayGraph() {
		System.out.println("****GRAPH*****");
		System.out.println("Number of vertices: " + this.v);
		System.out.println("Number of edges: " + this.e);
		System.out.println("Connection - Matrix");
		for (int i = 0; i < this.node.size(); i++) {
			for (int j = 0; j < this.node.size(); j++)
				System.out.print(connected[i][j] ? "1 " : "0 ");
			System.out.println();
		}
		System.out.println("***************");
	}

	public void print_distance() {
		for (int i = 0; i < node.size(); i++) {
			for (int j = 0; j < node.size(); j++) {
				System.out.printf("%.2f \t",distance[i][j]);
			}
			System.out.println();
		}
	}
	public void print_distance_new() {
		for (int i = 0; i < node.size(); i++) {
			for (int j = 0; j < node.size(); j++) {
				System.out.printf("%.2f \t", weight[i][j]);
			}
			System.out.println();
		}
	}
	private boolean isValidNode(Node u) {
		return (u.getId() >= 0) && (u.getId() <= this.v - 1);
	}

	public float Cost() {
		float s = 0;
		for (int i = 0; i < edge.size(); i++) {
			s += distance[edge.get(i).getSource()][edge.get(i).getDestination()];
		}
		return s;
	}
}