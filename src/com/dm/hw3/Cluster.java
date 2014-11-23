package com.dm.hw3;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
	
	public void expandCluster(double[][] adjMatrix,Set<Integer> partOfClusters){
		
		List <Integer> initialPoints = new ArrayList<Integer>(getClusterPoints());
		for(int i:initialPoints){
			for(int j=0;j<adjMatrix.length;j++){
				if(adjMatrix[i][j]>0.0 && i!=0 && j!=0 && !partOfClusters.contains(j) ){
					addPoint(j);
					partOfClusters.add(j);
				}
			}
		}
		
		
	}
}
