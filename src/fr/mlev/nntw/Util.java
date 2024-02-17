package fr.mlev.nntw;

import java.util.List;

public class Util {
	public static double sigmoid(double in) {
	//	in = Math.abs(in);
		return 1 / (1 + Math.exp(-in));
	}

	/**
	 * This function can determine how far or how close a list of point are from the curve what want to obtain.
	 * See https://www.simplilearn.com/tutorials/statistics-tutorial/mean-squared-error
	 * @param data: the answer given by the neural network
	 * @param answers: the answers of those data
	 * @return how far the list of data points are from the list of answers.
	 * 
	 */
	public static Double meanSquareLoss(List<Double> predictedAnswers, List<Double> correctAnswers) {
		double sumSquare = 0;
		for (int i = 0; i < correctAnswers.size(); i++) {
			double error = correctAnswers.get(i) - predictedAnswers.get(i);
			sumSquare += (error * error);
		}
		return sumSquare / (correctAnswers.size());
	}
}
