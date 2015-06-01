import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Paint;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.*;

@SuppressWarnings("serial")
public class DrawGraph extends JPanel {
	private static final int PREF_W = 1300;
	private static final int PREF_H = 768;
	// private static final int BORDER_GAP = 30;
	private static final Color GRAPH_COLOR = Color.blue;
	private static final Color GRAPH_COLOR_new = Color.red;
	private static final Color GRAPH_POINT_COLOR = Color.red;
	private static final Stroke GRAPH_STROKE = new BasicStroke(3f);
	private static final int GRAPH_POINT_WIDTH = 8;
	private List<Node> nodes;
	private List<Edge> edges;
	private List<Path_Physical> path_mid;
	private boolean edges_new[][];
	private int num_edges_new = 0;
	private float old_cost, cost;
	private int flag = 0;

	public DrawGraph(List<Node> nodes, List<Edge> edges, boolean edges_new[][],
			float old_cost, float cost, int n, List<Node> nodes_mid,
			List<Path_Physical> path_mid, int flag) {
		this.nodes = nodes;
		this.edges = edges;
		this.edges_new = edges_new;
		this.old_cost = old_cost;
		this.cost = cost;
		this.num_edges_new = n;
		this.path_mid = path_mid;
		this.flag = flag;
		BufferedImage myImage = null;
		try {
			String url = new String("/home/phuong-hoang/Desktop/Data/Archive/anh2.jpg");
			
			if (flag == 1) {
				url = "/home/phuong-hoang/Desktop/Data/Archive/anh6.jpg";
			}
			myImage = ImageIO.read(new File(url));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JFrame frame = new JFrame("Topology result");
		frame.setContentPane(new DrawGraph(myImage));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(this);
		frame.pack();
		frame.setLayout(null);
		frame.setLocationByPlatform(true);
		repaint();
		JButton button = new JButton("Result");
		button.setSize(new Dimension(150, 30));
		button.setBounds(1030, 10, 150, 30);
		frame.getContentPane().add(button);
		frame.revalidate();
		frame.repaint();
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					open_file();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		frame.setVisible(true);
	}


	private Image image;

	public DrawGraph(Image image) {
		this.image = image;
	}

	@Override
	protected void paintComponent(Graphics g) {
		g.drawImage(image, 0, 0, 1024, 784, this);
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		List<Point> graphPoints = new ArrayList<Point>();
		if (nodes == null) {
			return;
		}
		for (int i = 0; i < nodes.size(); i++) {
			graphPoints
					.add(new Point(nodes.get(i).getX(), nodes.get(i).getY()));
		}
		// In duong lam viec
		Stroke oldStroke = g2.getStroke();
		g2.setColor(GRAPH_COLOR);
		g2.setStroke(GRAPH_STROKE);
		if (edges == null)
			return;
		for (int i = 0; i < edges.size(); i++) {
			if(flag == 1){
// In Du Lieu Bach Khoa
			for (int j = 0; j < path_mid.size(); j++) {
				if (nodes.get(edges.get(i).getSource()).getId() == path_mid.get(j).getSrc()
						&& nodes.get(edges.get(i).getDestination()).getId() == path_mid.get(j).getDest()) {
					path_mid.get(j).getListX().add(0, nodes.get(edges.get(i).getSource()).getX());
					path_mid.get(j).getListY().add(0, nodes.get(edges.get(i).getSource()).getY());
					path_mid.get(j).getListX().add(nodes.get(edges.get(i).getDestination()).getX());
					path_mid.get(j).getListY().add(nodes.get(edges.get(i).getDestination()).getY());
					g2.drawPolyline(
							convertIntArray(path_mid.get(j).getListX()),
							convertIntArray(path_mid.get(j).getListY()),
							path_mid.get(j).getListX().size());
					edges.get(i).exist = true;
				}
			}
		}
			else
			//In duong thang
			g2.drawLine(nodes.get(edges.get(i).getSource()).getX(),nodes.get(edges.get(i).getSource()).getY(),
				 nodes.get(edges.get(i).getDestination()).getX(),nodes.get(edges.get(i).getDestination()).getY());
		}
		//In duong backup
		g2.setStroke(oldStroke);
		g2.setColor(GRAPH_COLOR_new);
		g2.setStroke(GRAPH_STROKE);
		for (int i = 0; i < nodes.size(); i++) {
			for (int j = 0; j < nodes.size(); j++) {
				if (edges_new[i][j]) {
// In du lieu bach khoa
					if(flag == 1 ){
					for (int k = 0; k < path_mid.size(); k++) {
						if (nodes.get(i).getId() == path_mid.get(k).getSrc()
								&& nodes.get(j).getId() == path_mid.get(k).getDest()) {
							path_mid.get(k).getListX().add(0, nodes.get(i).getX());
							path_mid.get(k).getListY().add(0, nodes.get(i).getY());
							path_mid.get(k).getListX().add(nodes.get(j).getX());
							path_mid.get(k).getListY().add(nodes.get(j).getY());
							g2.drawPolyline(
									convertIntArray(path_mid.get(k).getListX()),
									convertIntArray(path_mid.get(k).getListY()),
									path_mid.get(k).getListX().size());
							}
						}
					}
					else
// In du lieu thuong
					g2.drawLine(nodes.get(i).getX(), nodes.get(i).getY(), nodes
							.get(j).getX(), nodes.get(j).getY());
				}
			}
		}
		g2.setStroke(oldStroke);
		g2.setColor(GRAPH_POINT_COLOR);
		for (int i = 0; i < graphPoints.size(); i++) {
			if (nodes.get(i).getDis() != 0) {
				int x = graphPoints.get(i).x - GRAPH_POINT_WIDTH / 2;
				int y = graphPoints.get(i).y - GRAPH_POINT_WIDTH / 2;
				int ovalW = GRAPH_POINT_WIDTH;
				int ovalH = GRAPH_POINT_WIDTH;
				g2.fillOval(x, y, ovalH, ovalW);
				g2.drawString("Node "+nodes.get(i).getId(), x, y-5);
			}
		}

		// In thong so
		g2.setFont(new Font("default", Font.BOLD, 11));
		g2.setColor(Color.black);
		g2.drawString("Number of nodes:", 1030, 50);
		g2.setColor(Color.red);
		g2.drawString(" "+nodes.size(), 1140, 50);
		g2.setColor(Color.black);
		g2.drawString("Number of initial links:", 1030, 70);
		// System.out.println(num_edges_new);
		g2.setColor(Color.red);
		g2.drawString("" + edges.size(), 1180, 70);
		g2.setColor(Color.black);
		g2.drawString("Initial network cost: ", 1030, 90);
		g2.setColor(Color.red);
		g2.drawString(" " + old_cost, 1030, 110);
		g2.setColor(Color.black);
		g2.drawString("Number of addition links: ", 1030, 130);
		g2.setColor(Color.red);
		g2.drawString("" + num_edges_new, 1190, 130);
		g2.setColor(Color.black);
		g2.drawString("Additional network cost: ", 1030, 150);
		g2.setColor(Color.red);
		g2.drawString(" " + (cost - old_cost), 1030, 170);
		g2.setColor(Color.black);
		g2.drawString("Total network cost: ", 1030, 190);
		g2.setColor(Color.red);
		g2.drawString(" " + cost, 1030, 210);
		// ve diem voi ham fillOval(toa do x, toa do y, kich thuoc, kick thuoc)
	}

	private int[] convertIntArray(List<Integer> integers) {
		{
			int[] ret = new int[integers.size()];
			for (int i = 0; i < ret.length; i++) {
				ret[i] = integers.get(i).intValue();
			}
			return ret;
		}

	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(PREF_W, PREF_H);
	}

	private void open_file() throws IOException {
		File file = new File(
				"/home/phuong-hoang/Desktop/Data/result/OutFile.txt");
		Desktop dt = Desktop.getDesktop();
		dt.open(file);
	}

	public void run(
//			final List<Node> nodes, final List<Edge> edges,
//			final boolean edges_new[][], final float old_cost,
//			final float cost, final int n, final List<Node> node_mid,
//			final List<Path_Physical> path_mid
			) {
			SwingUtilities.invokeLater(new Runnable() {
			@SuppressWarnings("deprecation")
			public void run() {
			}
		});
	}

	public static void main(String[] args) {
		// new DrawGraph();

	}
}