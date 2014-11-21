import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MCLAlgorithm {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		MCLAlgorithm mcl = new MCLAlgorithm();
		mcl.runMCLAlgorithm("attweb_net.txt", 2,1.35 );
	
	}

	public void runMCLAlgorithm(String fileName, int expandBy, double inflateBy)
			throws IOException {

		LabelIdMappings labelIdMappings = ProjectUtils.getLabelIdMap(fileName);
		double[][] adjMatrix = ProjectUtils.getInitialAdjMatrix(fileName,
				labelIdMappings);

		adjMatrix = ProjectUtils.addSelfLoop(adjMatrix);
		adjMatrix = ProjectUtils.normalizeMatrixColWise(adjMatrix);
		double[][] prevMatrix = new double[adjMatrix.length][adjMatrix[0].length];
		while (isEqual(adjMatrix, prevMatrix) == false) {
			for (int i = 0; i < adjMatrix.length; i++) {
				for (int j = 0; j < adjMatrix.length; j++) {
					prevMatrix[i][j] = adjMatrix[i][j];
				}
			}

			adjMatrix = ProjectUtils.multiply(adjMatrix, adjMatrix);
			adjMatrix = ProjectUtils.inflateMatrixBy(adjMatrix, inflateBy);
			adjMatrix = ProjectUtils.normalizeMatrixColWise(adjMatrix);
			

		}
		ProjectUtils.printFinalMatrix(adjMatrix);
		

		Set<Integer> partOfClusters = new HashSet<Integer>();
		List<Cluster> clusters = new ArrayList<Cluster>();

		double[][] initMap = ProjectUtils.getInitialAdjMatrix(fileName,
				labelIdMappings);

		for (int i : ProjectUtils.getAttracterSet(adjMatrix)) {
			Cluster c = new Cluster(i);
			clusters.add(c);
			partOfClusters.add(i);
		}

		boolean flag = true;
		while (flag) {
			flag = false;
			int size = clusters.size();
			for (int i = 0; i < size; i++) {
				for (int j = 0; j < size; j++) {
					if (i == j) {
						continue;
					} else if (clusters.get(i).isMergePossible(clusters.get(j),
							initMap)) {
						clusters.get(i).getClusterPoints()
								.addAll(clusters.get(j).getClusterPoints());
						clusters.remove(j);
						size = clusters.size();
						flag = true;
						break;
					}
				}
			}

		}

		for (Cluster c : clusters) {
			c.expandCluster(adjMatrix, partOfClusters);
		}

		System.out.println(clusters.size());
		ProjectUtils.writeFileForPatek(clusters, labelIdMappings);

	}

	public boolean isEqual(double[][] m1, double[][] m2) {
		for (int i = 0; i < m1.length; i++) {
			for (int j = 0; j < m1.length; j++) {
				if (m1[i][j] != m2[i][j]) {
					return false;
				}
			}
		}
		return true;
	}

}
