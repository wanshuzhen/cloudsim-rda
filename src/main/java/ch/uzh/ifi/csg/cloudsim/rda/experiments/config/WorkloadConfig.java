package ch.uzh.ifi.csg.cloudsim.rda.experiments.config;

import java.util.ArrayList;

import ch.uzh.ifi.csg.cloudsim.rda.experiments.StochasticDataGenerator;

public class WorkloadConfig {

	private ArrayList<ArrayList<double[]>> inputData = new ArrayList<ArrayList<double[]>>();

	public ArrayList<ArrayList<double[]>> generateWorkload(int cnt,
			int workloadLength) {
		StochasticDataGenerator randomDataGenerator = new StochasticDataGenerator(
				workloadLength);
		for (int i = 0; i < cnt; i++) {

			ArrayList<double[]> workloadData = randomDataGenerator
					.generateData(250, 100, 40, 250, 10, 0.5, 10, 0.5, 75);
			inputData.add(workloadData);

		}
		return inputData;
	}
}