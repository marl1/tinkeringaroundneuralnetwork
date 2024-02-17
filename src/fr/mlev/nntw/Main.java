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
		Network network = new Network();
		
		List<List<Integer>> data = new ArrayList<List<Integer>>();
		data.add(Arrays.asList(185, 80)); // 185cm, 80kg = male
		data.add(Arrays.asList(175, 78)); // male
		data.add(Arrays.asList(190, 89)); // male
		data.add(Arrays.asList(150, 52)); // female
		data.add(Arrays.asList(162, 59)); // female
		data.add(Arrays.asList(152, 47)); // female
		List<Double> answers = Arrays.asList(1.0, 1.0,1.0,0.0,0.0, 0.0);
		network.train(data, answers);
		DecimalFormat df = new DecimalFormat("#.##%");
		System.out.println("Is it a man ? 185cm, 90kg.");
		Double prediction = network.predict(185, 90); // height, weight to predict if it's a female or a male
		System.out.println(df.format(prediction) + " chances it's a man.");
		
		System.out.println("Is it a man ? 145cm, 50kg.");
		prediction = network.predict(145, 50); // height, weight to predict if it's a female or a male
		System.out.println(df.format(prediction) + " chances it's a man.");
		
		System.out.println("Is it a man ? 150cm, 58kg.");
		prediction = network.predict(150, 58); // height, weight to predict if it's a female or a male
		System.out.println(df.format(prediction) + " chances it's a man.");
		
		System.out.println("Is it a man ? 176cm, 75kg.");
		prediction = network.predict(176, 75); // height, weight to predict if it's a female or a male
		System.out.println(df.format(prediction) + " chances it's a man.");
	}
	
}
