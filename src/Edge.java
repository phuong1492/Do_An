public class Edge {
	private String id;
	private int source;
	private int destination;
	public boolean exist;

	public Edge(String id, int source, int destination) {
		this.id = id;
		this.source = source;
		this.destination = destination;
		this.exist = false;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public int getSource() {
		return source;
	}

	public int getDestination() {
		return destination;
	}

	public void setSourceVertex(int source) {
		this.source = source;
	}

	public void setDestinationVertex(int destination) {
		this.destination = destination;
	}
}
