package com.dm.hw3;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class ProjectUtils {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		/*
		 * double value = 1.4445; value = Math.round(value * 1000.0) / 1000.0;
		 * System.out.println(value);
		 */
		Map<String, Integer> tm = getLabelIdMap("yeast_undirected_metabolic.txt").labelIdMap;
		for (String str : tm.keySet()) {
			System.out.println(tm.get(str) + "  " + str);
		}
	}

	/*
	 * public static double[][] expandMatrix(double[][] inputM) {
	 * 
	 * for (int i = 0; i < inputM.length; i++) for (int j = 0; j <
	 * inputM.length; j++) for (int k = 0; k < inputM.length; k++) inputM[i][j]
	 * = inputM[i][j] + (inputM[i][k] * inputM[k][j]);
	 * 
	 * return inputM; }
	 */

	public static double[][] addSelfLoop(double[][] inputM) {
		int i = inputM.length;
		int j = inputM.length;

		for (int cnt1 = 1; cnt1 < i; cnt1++) {
			for (int cnt2 = 1; cnt2 < j; cnt2++) {
				if (cnt1 == cnt2) {
					inputM[cnt1][cnt2] = 1.0;
				}
			}
		}
		return inputM;
	}

	public static double[][] getInitialAdjMatrix(String fileName,
			LabelIdMappings labelIdMappings) throws IOException {
		double[][] initialAdjMatrix = new double[labelIdMappings.finalCount][labelIdMappings.finalCount];
		String readLine;
		FileReader fileReader = new FileReader(fileName);
		@SuppressWarnings("resource")
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		String delim = getDelimiter(fileName);
		while ((readLine = bufferedReader.readLine()) != null) {
			String[] tLine = readLine.split(delim);
			initialAdjMatrix[labelIdMappings.labelIdMap.get(tLine[0])][labelIdMappings.labelIdMap
					.get(tLine[1])] = 1;
			initialAdjMatrix[labelIdMappings.labelIdMap.get(tLine[1])][labelIdMappings.labelIdMap
					.get(tLine[0])] = 1;

		}
		return initialAdjMatrix;
	}

	public static double[][] normalizeMatrixColWise(double[][] inputMatrix) {

		int j = inputMatrix[0].length;
		int i = inputMatrix.length;
		for (int c1 = 1; c1 < j; c1++) {
			double sum = 0;
			for (int c2 = 1; c2 < i; c2++) {
				sum = sum + inputMatrix[c2][c1];
			}
			for (int c2 = 1; c2 < i; c2++) {
				double temp = inputMatrix[c2][c1] / sum;
				inputMatrix[c2][c1] = temp;
			}
		}
		return inputMatrix;
	}

	public static double[][] inflateMatrixBy(double[][] inputMatrix,
			double param) {
		for (int j = 1; j < inputMatrix.length; j++) {
			for (int i = 1; i < inputMatrix.length; i++) {
				inputMatrix[i][j] = Math.pow(inputMatrix[i][j], param);
			}
		}
		return inputMatrix;
	}

	public static void printFinalMatrix(double[][] inputM) {

		for (int i = 1; i < inputM.length; i++) {
			for (int j = 1; j < inputM.length; j++) {
				System.out.print(inputM[i][j] + "  ");
			}
			System.out.println("\n");

		}
	}

	public static String getDelimiter(String fileName) throws IOException {

		String readLine;
		FileReader fileReader = new FileReader(fileName);
		@SuppressWarnings("resource")
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		if ((readLine = bufferedReader.readLine()) != null) {
			int index = readLine.indexOf("\t");
			if (index > -1) {
				return "\t";
			}
			return " ";
		}
		return readLine;
	}

	public static LabelIdMappings getLabelIdMap(String fileName)
			throws IOException {
		LabelIdMappings labelIdMappings = new LabelIdMappings();
		String readLine;
		FileReader fileReader = new FileReader(fileName);
		@SuppressWarnings("resource")
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		int cnt = 1;
		while ((readLine = bufferedReader.readLine()) != null) {

			String delim = getDelimiter(fileName);
			String[] tLine = readLine.split(delim);
			for (int i = 0; i < tLine.length; i++) {
				if (!labelIdMappings.labelIdMap.containsKey(tLine[i])) {
					labelIdMappings.labelIdMap.put(tLine[i], cnt);
					labelIdMappings.idLabelMap.put(cnt, tLine[i]);
					cnt++;
				}
			}

		}
		labelIdMappings.finalCount = cnt;
		return labelIdMappings;
	}

	public static void writeFileForPatek(List<Cluster> cS,
			LabelIdMappings labelIdMappings,String fileName) throws IOException {

		File file = new File("pajekCluFiles" + File.separator + fileName+".clu");
		if (!file.exists()) {
			file.delete();
		}
		file.createNewFile();
		FileWriter fw = new FileWriter(file);
		BufferedWriter bw = new BufferedWriter(fw);
		StringBuilder str = new StringBuilder();
		TreeMap<Integer, Integer> indexClusterMap = new TreeMap<Integer, Integer>();
		str.append("*Vertices " + (labelIdMappings.finalCount - 1));
		int cluster = 1;
		for (int i = 0; i < cS.size(); i++) {
			for (int j : cS.get(i).getClusterPoints()) {
				indexClusterMap.put(j, cluster);
			}
			cluster++;
		}

		for (int i : indexClusterMap.keySet()) {
			str.append("\n" + indexClusterMap.get(i));
		}

		bw.write(str.toString());
		bw.close();
		
	}

	public static List<Integer> getAttracterSet(double[][] inputM) {
		List<Integer> attracterSet = new ArrayList<Integer>();

		for (int i = 1; i < inputM.length; i++) {

			for (int j = 1; j < inputM.length; j++) {
				if (i == j & inputM[i][j] != 0) {
					attracterSet.add(i);
				}
			}
		}
		return attracterSet;
	}

	public static double[][] multiply(double[][] m1, double[][] m2) {
		/*
		 * int rowsInA = a.length; int columnsInA = a.length; // same as rows in
		 * B int columnsInB = b.length; double[][] c = new
		 * double[rowsInA][columnsInB]; for (int i = 1; i < rowsInA; i++) { for
		 * (int j = 1; j < columnsInB; j++) { for (int k = 1; k < columnsInA;
		 * k++) { c[i][j] = c[i][j] + a[i][k] * b[k][j]; } } } return c;
		 */
		int len = m1.length;

		double[][] c = new double[len][len];
		for (int i = 1; i < len; i++) {
			for (int j = 1; j < len; j++) {
				for (int k = 1; k < len; k++) {
					c[i][j] = c[i][j] + (m1[i][k] * m2[k][j]);
				}
			}
		}
		return c;
	}

	public static double[][] pruneMatrix(double[][] a) {

		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a.length; j++) {
				if (a[i][j] < 0.00000005) {
					a[i][j] = 0;
				} else if (a[i][j] > 0.9999999) {
					a[i][j] = 1;
				}
			}
		}
		return a;
	}
}