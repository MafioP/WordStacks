package test;

import java.util.Arrays;
import java.util.Random;

public class MatrizGenerator {
	
	public static final int SIZE = 10;
	private static boolean debug = !true;
	
	public static char [][] generateMatriz(int x, int y, String [] wordList){
		char [][] matriz = new char [x][y];
		char [] charWord;
		int [] order = randOrder();
		int [] coords = randOrder();
		
		for (int i=0; i<order.length; i++) {	
			charWord = wordToChar(wordList, order[i]);
			addWord(coords[i], matriz, charWord);
		}
		return matriz;
	}
	
	private static void addWord(int coords, char [][] matriz, char[] word) {
		System.out.println(Arrays.toString(word));
		int orientation = randOrientation();
		System.out.println(orientation);
		int index = 0;
		
		switch (orientation) {
		 case 1://N
			 for (int i = word.length; i > 0; i--) {
				 matriz[i][coords] = word[index++];
				 System.out.println("indexN: " + index);
			 }
			 break;
		 case 2://S
			 for (int i = 0; i < word.length; i++) {
				 matriz[i][coords] = word[index++];
				 System.out.println("indexS: " + index);
			 }
			 break;
		 case 3://E
			 for (int i = coords; i < word.length; i++) {
				 matriz[0][i] = word[index++];
				 System.out.println("indexE: " + index);
			 }
			 break;
		 case 4://O
			 for (int i = coords; i > coords - word.length; i--) {
				 matriz[0][i] = word[index++];
				 System.out.println("indexO: " + index);
			 }
			 break;
			 
		 }
	}
	
	private static char [] wordToChar(String [] wordList, int order) {
		String tempWord;
		tempWord = wordList[order];
		char [] word = new char [tempWord.length()];
		
		for (int i = 0; i < tempWord.length(); i++) {
			word[i] = tempWord.charAt(i);
		}
		return word;
	}
	/**
	 * Generar una orientacion aleatoria a partir de un int
	 * @return
	 */
	private static int randOrientation() {
		int rand = 0;
		rand = (int)Math.floor(Math.random()*4 + 1);
		return rand;
	}
	
	private static int[] randOrder() {
		int[] order = new int [10];
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
				if (debug) System.out.println(Arrays.toString(order));
				count ++;
			}	
		}
		return order;
	}
	
	private static boolean checkIfEmpty(int y, int length, int orientacion, char[][] matriz) {
		boolean empty = false;
		char checkEmpty = '0';
		switch(orientacion) {
		case 2:
			for(int i = y; y <= length; i++) {
				checkEmpty = matriz[0][i];
				if(checkEmpty != '0' ) {
					empty = true;
				}
			}
			break;
		}
		
		return empty;
		
	}
	
	
	private static int randomInt() {
		int value = new Random().nextInt(SIZE);
		if (debug) System.out.println("Next int: " + value);
		return value;
	}
}
