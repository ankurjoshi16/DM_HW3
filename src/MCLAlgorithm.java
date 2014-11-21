import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MCLAlgorithm {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		MCLAlgorithm mcl = new MCLAlgorithm();
		mcl.runMCLAlgorithm("attweb_net.txt", 2, 2);
		// String asj = "ankur		joshi";
		// System.out.println(asj.indexOf("\t"));

		/*
		 * double td[][] = new
		 * double[][]{{1,1,0,0,0,1,1,0,0,1,0,0},{1,1,1,0,1,0,0,0,0,0,0,0},
		 * {0,1,1,1,1,0,0,0,0,0,0,0},{0,0,1,1,0,0,0,1,1,0,1,0},
		 * {0,1,1,0,1,0,1,1,0,0,0,0},{1,0,0,0,0,1,0,0,0,1,0,0},
		 * {1,0,0,0,1,0,1,0,0,1,0,0},{0,0,0,1,1,0,0,1,0,0,1,0},
		 * {0,0,0,1,0,0,0,0,1,0,1,1},{1,0,0,0,0,1,1,0,0,1,0,0},
		 * {0,0,0,1,0,0,0,1,1,0,1,1},{0,0,0,0,0,0,0,0,1,0,1,1}, };
		 */

		// td = ProjectUtils.normalizeMatrixColWise(td);
		// ProjectUtils.printFinalMatrix(td);
	}

	public void runMCLAlgorithm(String fileName, int expandBy, double inflateBy)
			throws IOException {

		LabelIdMappings labelIdMappings = ProjectUtils.getLabelIdMap(fileName);
		double[][] adjMatrix = ProjectUtils.getInitialAdjMatrix(fileName,
				labelIdMappings);

		adjMatrix = ProjectUtils.addSelfLoop(adjMatrix);
		adjMatrix = ProjectUtils.normalizeMatrixColWise(adjMatrix);
		// ProjectUtils.printFinalMatrix(adjMatrix);

		double[][] prevMatrix = new double[adjMatrix.length][adjMatrix[0].length];
		int cnt = 0;
		while (isEqual(adjMatrix, prevMatrix) == false) {
			for (int i = 0; i < adjMatrix.length; i++) {
				for (int j = 0; j < adjMatrix.length; j++) {
					prevMatrix[i][j] = adjMatrix[i][j];
				}
			}

			adjMatrix = ProjectUtils.multiply(adjMatrix, adjMatrix);
			// adjMatrix = ProjectUtils.inflateMatrixBy(adjMatrix, inflateBy);
			adjMatrix = ProjectUtils.inflateMatrixBy(adjMatrix, inflateBy);
			adjMatrix = ProjectUtils.normalizeMatrixColWise(adjMatrix);
			// ProjectUtils.printFinalMatrix(adjMatrix);
			// break;
			// System.out.println("Previous Matrix was: ");
			// ProjectUtils.printFinalMatrix(prevMatrix);
			// System.out.println("Final matrix is:");

		}
		// ProjectUtils.printFinalMatrix(adjMatrix);
		ProjectUtils.writeFileForPatek(adjMatrix, labelIdMappings);
		System.out.println(ProjectUtils.getAttracterSet(adjMatrix));
		System.out.println(ProjectUtils.getAttracterSet(adjMatrix).size());

		List<Cluster> clusters = new ArrayList<Cluster>();

		List<Integer> attrSet = new ArrayList<Integer>(ProjectUtils.getAttracterSet(adjMatrix));
		
		
		double[] [] initMap = ProjectUtils.getInitialAdjMatrix(fileName, labelIdMappings);
		for(int i:attrSet){
			for(int j:attrSet){
				if(i==j){
					continue;
				}
				else if(initMap[i][j]>0.0){
					System.out.println("yup" + i + "  "+j);
				}
				else{
					System.out.println("No Match");
				}
			}
		}
		
		for (int i : ProjectUtils.getAttracterSet(adjMatrix)) {
			Cluster c = new Cluster(i);
			clusters.add(c);
		}

		boolean flag = true;
		while (flag) {
			flag = false;
			int size = clusters.size();
			for (int i = 0; i < size ; i++) {
				for (int j = 0; j < size; j++) {
					System.out.println("i : " +i +"  j: "+j + " size: "+ size);
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
		
		System.out.println(clusters.size());
		ProjectUtils.writeFileForPatek(adjMatrix, labelIdMappings);

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
