package fr.mlev.nntw;

import java.util.List;
import java.util.function.DoubleFunction;

public class Util {
	public static double sigmoid(double in) {
	//	in = Math.abs(in);
		return 1 / (1 + Math.exp(-in));
	}
	
	
	//from https://stackoverflow.com/questions/40657622/neural-network-activation-function
	public static double relu(double in) {
		DoubleFunction<Double> relu = x -> Math.max(0, x);
		DoubleFunction<Double> reluDer = x -> {
		    if (x < 0)
		        return 0.0;
		    return 1.0;
		};
		return reluDer.apply(in);
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
