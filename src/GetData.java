import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GetData {
	public int N_NODE;
	public List<Node> LISTNODE;
	public float[][] DISTANCE;
	public List<Edge> EDGE;
	public List<Request> REQUEST;
	public ArrayList<ArrayList<Edge>> SRG;

	public void setEmptyAll() {
		N_NODE = 0;
		DISTANCE = new float[100][100];
	}

	// tao du lieu tu file
	public void SetAllValue(String a) {
		try {
			BufferedReader br = null;
			try {
				br = new BufferedReader(new FileReader(
						"/home/phuong-hoang/Desktop/Data/Archive/"+a+".txt"));
			} catch (FileNotFoundException exc) {
				System.out.println("File not found!");
				return;
			}
			N_NODE = readNumber(br.readLine());
			LISTNODE = readPosition(br, N_NODE);
			EDGE = readLink(br, N_NODE);
			REQUEST = readRequests(br);
			SRG = readSRG(br);
//			for (int i = 0; i < SRG.size(); i++) {
//				for (int j = 0; j < SRG.get(i).size(); j++) {
//					System.out.print(SRG.get(i).get(j).getDestination()+ " ");
//				}
//				System.out.println();
//			}
			DISTANCE = creatMatrixDistance(N_NODE, LISTNODE);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// so not
	private int readNumber(String tmp) {
		tmp = tmp.replace(";", "");
		String tmp2[] = tmp.split("=");
		// System.out.println(tmp2[1].trim());
		return Integer.parseInt(tmp2[1].trim());
	}

	// list cac node doc tu file
	private static List<Node> readPosition(BufferedReader br, int length)
			throws IOException {
		final List<Node> node = new ArrayList<Node>();
		String line = " ";
		br.readLine(); // read this P=[
		System.out.print(line);
		line = br.readLine();
		while (!line.equals("]")) {
			String coordinates = line.substring(0, line.length() - 1);
			int a = coordinates.indexOf(" ");
			int splitpoint = coordinates.indexOf(',');
			int dis = coordinates.indexOf('|');
			int s = Integer.parseInt(coordinates.substring(0, a));
			int x = Integer.parseInt(coordinates.substring(a + 1, splitpoint));
			int y = Integer.parseInt(coordinates.substring(splitpoint + 1, dis));
			int display = Integer.parseInt(coordinates.substring(dis + 1));
			node.add(new Node(x, y, s, display));
			line = br.readLine();
			System.out.println("Node" + s + " " + x + " " + y + " " + display);
			// System.out.println("Line: "+line);
		}
		br.readLine(); // read this ];
		return node;
	}

	private static List<Edge> readLink(BufferedReader br, int length)
			throws IOException {
		List<Edge> edge = new ArrayList<Edge>();
		String line = " ";
		// br.readLine(); // read this P=[
		System.out.print(line);
		line = br.readLine();
		while (!line.equals("]")) {
			String coordinates = line.substring(0, line.length() - 1);
			int splitpoint = coordinates.indexOf(',');
			int a = coordinates.indexOf(" ");
			String s = coordinates.substring(0, a);
			int x = Integer.parseInt(coordinates.substring(a + 1, splitpoint));
			int y = Integer.parseInt(coordinates.substring(splitpoint + 1));
			// System.out.println(y);
			edge.add(new Edge(s, x, y));
			line = br.readLine();
			// System.out.println("Line: "+line);
		}
		br.readLine(); // read this ];
		return edge;
	}

	public void Print_List() {
		for (int i = 0; i < LISTNODE.size(); i++) {
			System.out.println(LISTNODE.get(i).getX() + " "
					+ LISTNODE.get(i).getY() + " " + LISTNODE.get(i).getId());
		}
	}

	public void Print_Link() {
		for (int i = 0; i < EDGE.size(); i++) {
			System.out.println(EDGE.get(i).getSource() + " "
					+ EDGE.get(i).getDestination());
		}
	}

	// ma tran khoang cach
	private static float[][] creatMatrixDistance(int n, List<Node> l) {
		float[][] matrix = new float[100][100];
		int i, j;
		for (i = 0; i < n; i++) {
			for (j = 0; j < n; j++) {
				matrix[i][j] = (float) Math.hypot((double) (l.get(i).getX() - l
						.get(j).getX()), (double) (l.get(i).getY() - l.get(j)
						.getY()));
			}
		}
		for (i = 0; i < n; i++) {
			for (j = 0; j < n; j++) {
				System.out.printf(" %f\t", matrix[i][j]);
			}
			System.out.println();
		}
		return matrix;
	}

	private static List<Request> readRequests(BufferedReader br)
			throws IOException {
		List<Request> request = new ArrayList<Request>();
		String line = " ";
		// br.readLine(); // read this R=[
		System.out.print(line);
		line = br.readLine();
		while (!line.equals("]")) {
			String coordinates = line.substring(0, line.length() - 1);
			int splitpoint = coordinates.indexOf(',');
			int a = coordinates.indexOf(" ");
			// String s = coordinates.substring(0, a);
			int x = Integer.parseInt(coordinates.substring(a + 1, splitpoint));
			int y = Integer.parseInt(coordinates.substring(splitpoint + 1));
			// System.out.println(y);
			Request re = new Request(x, y);
			request.add(re);
			line = br.readLine();
			// System.out.println("Line: "+line);
		}
		for (int i = 0; i < request.size(); i++) {
			System.out.println(request.get(i).getSrc() + " "
					+ request.get(i).getDest());
		}
		br.readLine(); // read this ];
		return request;
	}
	private static ArrayList<ArrayList<Edge>> readSRG(BufferedReader br)
			throws IOException {
		ArrayList<ArrayList<Edge>> list_edge = new ArrayList<ArrayList<Edge>>();
		String line = " ";
		// br.readLine(); // read this R=[
		line = br.readLine();
		System.out.println(" Read SRG: ");
		while (!line.equals("]")) {
			ArrayList<Edge> edge = new ArrayList<Edge>();
			line = br.readLine();
			System.out.println(line);
			while(!line.equals("}")){
				String coordinates = line.substring(0, line.length() - 1);
				int splitpoint = coordinates.indexOf(',');
			// String s = coordinates.substring(0, a);
				int x = Integer.parseInt(coordinates.substring(0, splitpoint));
				int y = Integer.parseInt(coordinates.substring(splitpoint + 1));
				System.out.println(x + "-" + y);
			// System.out.println(y);
				Edge ed  = new Edge("", x, y);
				edge.add(ed);
				line = br.readLine();
			//	System.out.println(line);
				}
			//	System.out.println(line);
				list_edge.add(edge);
//				line = br.readLine();
				line = br.readLine();
//				System.out.println(line);
		}
//		for (int i = 0; i < request.size(); i++) {
//			System.out.println(request.get(i).getSrc() + " "
//					+ request.get(i).getDest());
			br.readLine(); // read this ];
		return list_edge;
	}

	public static class Request {

		private int src;

		private int dest;

		public Request(int src, int dest) {
			this.dest = dest;
			this.src = src;
		}

		public int getSrc() {
			return src;
		}

		public void setSrc(int src) {
			this.src = src;
		}

		public int getDest() {
			return dest;
		}

		public void setDest(int dest) {
			this.dest = dest;
		}

		public void String() {
			System.out.println("Working path from " + src + " to " + dest);
		}
	}
}
