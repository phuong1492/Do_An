import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
public class test {
	public List<Node> LISTNODE;
	public List<Path_Physical> Path;

	public void GetValue() throws IOException {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(
					"/home/phuong-hoang/Desktop/Data/Archive/duongvl6.txt"));
			LISTNODE = readPosition(br);
			Path = readPathPhy(br, LISTNODE);
		} catch (FileNotFoundException exc) {
			System.out.println("File not found!");
			return;
		}
	}
	
	private static List<Node> readPosition(BufferedReader br)
			throws IOException {
		final List<Node> node = new ArrayList<Node>();
		String line = " ";
		br.readLine(); // read this P=[
		//System.out.print(line);
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
			//System.out.println("Node" + s + " " + x + " " + y + " " + display);
			// System.out.println("Line: "+line);
		}
		br.readLine(); // read this ];
		return node;
	}
	private static List<Path_Physical> readPathPhy(BufferedReader br, List<Node> nodes)
			throws IOException {
		final List<Path_Physical> b = new ArrayList<Path_Physical>();
		String line = " ";
		line = br.readLine();
		//System.out.println(line);
		while (!line.equals("]")) {
			List<Integer> m = new ArrayList<Integer>();
			List<Integer> n = new ArrayList<Integer>();
			List<Node> node = new ArrayList<Node>();
			String coordinates = line.substring(0, line.length() - 1);
			int a = coordinates.indexOf(" ");
			int splitpoint = coordinates.indexOf(',');
			//int dis = coordinates.indexOf('|');
			String strig = coordinates.substring(a+1);
			// doc diem dau
			int x = Integer.parseInt(coordinates.substring(0, splitpoint));
			// diem cuoi
			int y = Integer.parseInt(coordinates.substring(splitpoint+1, a));
			 for (String item: strig.split("\\|")) {
			        node.add(nodes.get(Integer.parseInt(item)));
			    }
			 for (int i = 0; i < node.size(); i++) {
				m.add(node.get(i).getX());
				n.add(node.get(i).getY());
			}
			b.add(new Path_Physical(x,y,m,n));
			line = br.readLine();
		}
		br.readLine(); // read this ];
		return b;
	}
	}
