package fr.mlev.nntw;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fr.mlev.nntw.model.Network;

/**
 * Based on tutorial from
 * https://www.infoworld.com/article/3685569/how-to-build-a-neural-network-in-java.html
 * 
 * Given the height and the weight, the neural network must try to determine if the person is more likely
 * to be a male or a female.
 *
 */
public class Main {

	public static void main(String[] args) {
		System.out.println("lol");
		Network network = new Network(3);
		Double[] data1 = {30.0, 500.0, 10.0}; //30cm, 500g, not acidic=cucumber
		Double[] data2 = {29.0, 480.0, 12.0};
		Double[] data3 = {31.0, 510.0, 9.0};
		Double[] data3b = {15.0, 250.0, 45.0}; // half cucumber
		Double[] data4 = {5.0, 6.0, 75.0}; //5cm, 6g, quite acidic=pickle
		Double[] data5 = {5.0, 5.0, 77.0};
		Double[] data6 = {4.0, 3.0, 78.0};
		Double[] data7 = {3.0, 2.0, 76.0};
		
		List<Double[]> data = List.of(data1, data2, data3, data3b, data4, data5, data6, data7);
		List<Double> answers = Arrays.asList(1.0, 1.0, 1.0, 0.5 ,0.0, 0.0, 0.0, 0.0);
		network.train(data, answers);
		DecimalFormat df = new DecimalFormat("#.###########%");
		System.out.print("Is it a pickle? 31cm, 490g, not acidic ");
		Double[] q1 = {31.0, 490.0, 8.0}; //181cm, 82kg, voix très grave
		System.out.println(df.format(network.interrogate(q1)));
		
		System.out.print("Is it a pickle? 29cm, 480g, not acidic ");		
		Double[] q2 = {29.0, 480.0, 12.0};
		System.out.println(df.format(network.interrogate(q2)));
		
		System.out.print("Is it a pickle? 30cm, 510g, not acidic ");		
		Double[] q3 = {30.0, 510.0, 10.0};
		System.out.println(df.format(network.interrogate(q3)));
		
		System.out.print("Is it a pickle? 15cm, 240g, midly acidic ");		
		Double[] q4 = {15.0, 240.0, 52.0};
		System.out.println(df.format(network.interrogate(q4)));
		
		System.out.print("Is it a pickle? 3cm, 5g, acidic ");		
		Double[] q5 = {3.0, 5.0, 72.0};
		System.out.println(df.format(network.interrogate(q5)));

		System.out.print("Is it a pickle? 4cm, 5g, acidic ");		
		Double[] q6 = {4.0, 5.0, 70.0};
		System.out.println(df.format(network.interrogate(q6)));
		
	}
	
}
