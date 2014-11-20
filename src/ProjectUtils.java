import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ProjectUtils {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public static Map<Integer, List<Integer>> returnInitialAdjMap(
			String fileName) throws IOException {
		Map<Integer, List<Integer>> initialMap = new LinkedHashMap<Integer, List<Integer>>();
		String readLine;
		FileReader fileReader = new FileReader(fileName);
		@SuppressWarnings("resource")
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		while ((readLine = bufferedReader.readLine()) != null) {
			String[] tLine = readLine.split(" ");
			Integer tempI = Integer.parseInt(tLine[0]);
			if (initialMap.containsKey(tempI)) {
				initialMap.get(tempI).add(Integer.parseInt(tLine[1]));
			} else {
				List<Integer> newEntry = new ArrayList<Integer>();
				newEntry.add(Integer.parseInt(tLine[1]));
				initialMap.put(tempI, newEntry);
			}
		}
		return initialMap;
	}

	public static Double[][] selfMultiplyMatrix(Double[][] inputM) {

		int i = inputM.length;
		int j = inputM[0].length;

		for (int cnt1 = 0; cnt1 < i; cnt1++) {
			for (int cnt2 = 0; cnt2 < j; cnt2++) {
				inputM[cnt1][cnt2] = inputM[cnt1][cnt2] * inputM[cnt1][cnt2];
			}
		}
		return inputM;
	}

	public static Double[][] addSelfLoop(Double[][] inputM) {
		int i = inputM.length;
		int j = inputM[0].length;

		for (int cnt1 = 0; cnt1 < i; cnt1++) {
			for (int cnt2 = 0; cnt2 < j; cnt2++) {
				if (cnt1 == cnt2) {
					inputM[cnt1][cnt2] = 1.0;
				}
			}
		}
		return inputM;
	}

	public static Double[][] getInitialAdjMatrix(String FileName)
			throws IOException {

		Map<Integer, List<Integer>> initialMap = returnInitialAdjMap(FileName);
		List<Integer> keySet = new ArrayList<Integer>(initialMap.keySet());
		Double[][] initialAdjMatrix = new Double[keySet.size()][keySet.size()];
		for (Integer i : keySet) {
			List<Integer> connections = initialMap.get(i);
			for (Integer j : connections) {
				initialAdjMatrix[i][j] = 1.0;
			}
		}
		return initialAdjMatrix;
	}
	
	public static Double[][] normalizeMatrixColWise(Double[][] inputMatrix){
	
		
		return null;
	}
}
