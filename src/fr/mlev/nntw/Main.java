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
		Double[] data1 = {180.0, 80.0, 10.0}; //180cm, 80kg, voix grave
		Double[] data2 = {190.0, 85.0, 12.0};
		Double[] data3 = {170.0, 76.0, 9.0};
		Double[] data3b = {173.0, 77.0, 25.0};
		Double[] data4 = {150.0, 46.0, 60.0};
		Double[] data5 = {149.0, 46.0, 65.0};
		Double[] data6 = {151.0, 50.0, 62.0};
		Double[] data7 = {145.0, 42.0, 69.0};
		
		List<Double[]> data = List.of(data1, data2, data3, data3b, data4, data5, data6, data7);
		List<Double> answers = Arrays.asList(1.0, 1.0,1.0,1.0,0.0,0.0, 0.0, 0.0);
		network.train(data, answers);
		DecimalFormat df = new DecimalFormat("#.#####%");
		System.out.print("Is it a man ? 181cm, 82kg, voix très grave. ");
		Double[] test1 = {181.0, 82.0, 8.0}; //181cm, 82kg, voix très grave
		System.out.println(df.format(network.interrogate(test1)));
		
		System.out.print("Is it a man ? 171cm, 78kg, voix assez grave. ");		
		Double[] test2 = {171.0, 78.0, 12.0};
		System.out.println(df.format(network.interrogate(test2)));
		
		System.out.print("Is it a man ? 160cm, 58kg, voix très légèrement aigue. ");		
		Double[] test3 = {147.0, 58.0, 55.0};
		System.out.println(df.format(network.interrogate(test3)));
		
		System.out.print("Is it a man ? 147cm, 51kg, voix un peu aigue. ");		
		Double[] test4 = {147.0, 51.0, 62.0};
		System.out.println(df.format(network.interrogate(test4)));
		
	}
	
}
