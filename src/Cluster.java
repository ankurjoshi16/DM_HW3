import java.util.ArrayList;
import java.util.List;

public class Cluster {

	private List<Integer> clusterPoints;

	public Cluster(Integer i) {
		clusterPoints = new ArrayList<Integer>();
		clusterPoints.add(i);
	}

	public List<Integer> getClusterPoints() {
		return clusterPoints;
	}

	public void setClusterPoints(List<Integer> clusterPoint) {
		this.clusterPoints = clusterPoint;
	}

	public void addPoint(Integer i) {
		clusterPoints.add(i);
	}

	public boolean isMergePossible(Cluster c, double[][] initialM) {

		for (int i : this.getClusterPoints()) {
			for (int j : c.getClusterPoints()) {
				if (initialM[i][j] == 1) {
					return true;
				}
			}
		}
		return false;
	}

}
