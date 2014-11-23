package com.dm.hw3;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MCLAlgorithm {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		MCLAlgorithm mcl = new MCLAlgorithm();
		mcl.runMCLAlgorithm("attweb_net", 2.0);
		// mcl.runMCLAlgorithm("physics_collaboration_net",1.25);
		// mcl.runMCLAlgorithm("yeast_undirected_metabolic.txt", 2, 1.205);

	}

	public OutputObject runMCLAlgorithm(String fileName, double inflateBy)
			throws IOException {

		LabelIdMappings labelIdMappings = ProjectUtils
				.getLabelIdMap("inputFiles" + File.separator + fileName);
		double[][] adjMatrix = ProjectUtils.getInitialAdjMatrix("inputFiles"
				+ File.separator + fileName, labelIdMappings);

		adjMatrix = ProjectUtils.addSelfLoop(adjMatrix);
		adjMatrix = ProjectUtils.normalizeMatrixColWise(adjMatrix);
		double[][] prevMatrix = new double[adjMatrix.length][adjMatrix.length];
		while (isEqual(adjMatrix, prevMatrix) == false) {
			for (int i = 1; i < adjMatrix.length; i++) {
				for (int j = 1; j < adjMatrix.length; j++) {
					prevMatrix[i][j] = adjMatrix[i][j];
				}
			}

			adjMatrix = ProjectUtils.multiply(adjMatrix, adjMatrix);
			adjMatrix = ProjectUtils.inflateMatrixBy(adjMatrix, inflateBy);
			adjMatrix = ProjectUtils.normalizeMatrixColWise(adjMatrix);
			adjMatrix = ProjectUtils.pruneMatrix(adjMatrix);

			// ProjectUtils.printFinalMatrix(adjMatrix);
			// break;

		}

		Set<Integer> partOfClusters = new HashSet<Integer>();
		List<Cluster> clusters = new ArrayList<Cluster>();

		double[][] initMap = ProjectUtils.getInitialAdjMatrix("inputFiles"
				+ File.separator + fileName, labelIdMappings);

		// ProjectUtils.printFinalMatrix(adjMatrix);

		List<Integer> attracterSet = ProjectUtils.getAttracterSet(adjMatrix);
		OutputObject oo = new OutputObject();
		oo.outputStr = oo.outputStr + "\nTotal Attracters found:"
				+ attracterSet.size();
		oo.outputStr = oo.outputStr + "\nHere are the Attracters: "
				+ attracterSet;

		for (int i : attracterSet) {
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

		oo.outputStr = oo.outputStr + "\n" + "The Attracter System is formed as below: (Total Size = "+clusters.size()+")";

		for (Cluster c : clusters) {
			oo.outputStr = oo.outputStr + "\n" + "{ " + c.getId() + " }";
		}

		for (Cluster c : clusters) {
			c.expandCluster(adjMatrix, partOfClusters);
		}

		ProjectUtils.writeFileForPatek(clusters, labelIdMappings, fileName);

		System.out.println(oo.outputStr);

		return oo;

	}

	public boolean isEqual(double[][] m1, double[][] m2) {
		for (int i = 1; i < m1.length; i++) {
			for (int j = 1; j < m1.length; j++) {
				if (m1[i][j] != m2[i][j]) {
					return false;
				}
			}
		}
		return true;
	}

}
