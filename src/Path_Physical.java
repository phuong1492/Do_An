import java.util.List;


public class Path_Physical {
	private int src;
	
	private int des;
	
	private List<Integer> x;
	
	private List<Integer> y;
	
	public Path_Physical(int s, int d, List<Integer> x, List<Integer> y){
		this.src = s;
		this.des = d;
		this.x = x;
		this.y = y;
	}
	
	public int getSrc() {
		return src;
	}

	public void setSrc(int src) {
		this.src = src;
	}

	public int getDest() {
		return des;
	}

	public void setDest(int dest) {
		this.des = dest;
	}
	public List<Integer> getListX(){
		return x;
	}
	
	public void setListX(List<Integer> x){
		this.x = x;
		
	}
	public List<Integer> getListY(){
		return y;
	}
	
	public void setListY(List<Integer> y){
		this.y = y;
	}
}
