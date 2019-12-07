package test;

import java.util.Arrays;
import java.util.Random;


public class Testing {
	
	public static final int SIZE = 20;
	private static boolean debug = !true;

	public static void main(String[] args) {
		int[] order = new int [SIZE];
		int check, counter = 0;
		int count = 0;
		
		check = randomInt();
		order[count++] = check;
		
		while (count < SIZE) {
			check = randomInt();
			boolean exist = false;
			for (int i = 0; i < count; i++) {
				if (order[i] == check) {
					exist = true;
					break;
				} 
			}
			
			counter ++;
			
			if (!exist) {
				//System.out.println("desigual");
				order[count] = check;
				System.out.println(Arrays.toString(order));
				count ++;
			}
			
		}
		System.out.println("veces que se repite el while: " + counter);
		
	}

	private static int randomInt() {
		int value = new Random().nextInt(SIZE);
		if (debug) System.out.println("Next int: " + value);
		return value;
	}

}
