package test;

import java.util.Arrays;
import java.util.Random;

public class MatrizGenerator {
	
	enum Orientation {
		N("North"), 
		E("East"), 
		S("South"), 
		W("West");
		
		String name;
		
		Orientation(String name) {
			this.name = name;
		}
		
		public String getName() {
			return name;
		}
		
		static Orientation random() {
			return values()[new Random().nextInt(values().length)];
		}
	}
	
	private static boolean debug = true;
	
	public static char [][] generateMatriz(int numColumns, int numRows, String [] wordList) {
		if (numColumns <= 0 || numRows <= 0) {
			throw new RuntimeException("Error: dimensiones invalidas");
		}
		int numWords = wordList.length;
		char [][] matriz = new char [numRows][numColumns];
		char [] charWord;
		int [] order = randOrder(numWords);
		int [] colIndexArray = randOrder(numWords);
		
		for (int i = 0; i < numWords; i++) {	
			charWord = wordToChar(wordList[order[i]]);
			addWord(matriz, colIndexArray[i], charWord);
			gravity(matriz, colIndexArray[i], numRows);
			gravity(matriz, colIndexArray[i], numRows);
			gravity(matriz, colIndexArray[i], numRows);
			gravity(matriz, colIndexArray[i], numRows);
			gravity(matriz, colIndexArray[i], numRows);
			gravity(matriz, colIndexArray[i], numRows);
			gravity(matriz, colIndexArray[i], numRows);
			gravity(matriz, colIndexArray[i], numRows);
			gravity(matriz, colIndexArray[i], numRows);
			gravity(matriz, colIndexArray[i], numRows);
			if (debug) {
				printMatrix(matriz);
			}
			
		}
		return matriz;
	}
	
	private static void gravity(char[][] matriz, int coords, int numRows) {
		int numColumns = matriz[0].length;
		for (int i = 0; i < numRows - 1; i++) {
			for (int j = 0; j < numColumns; j++) {
				if (matriz[i + 1][j] == '\0') {
					matriz[i + 1][j] = matriz[i][j];
					matriz[i][j] = '\0';
				}
			}
		}
		
	}
	/**
	 * 
	 * @param matriz 
	 * @param colIndex numero aleatorio que idica la columna
	 * @param word palabra formada por chars
	 */
	private static void addWord(char [][] matriz, int colIndex, char[] word) {
		Orientation orientation = Orientation.random();
		log("Orientation = " + orientation.getName());
		int numColumns = matriz[0].length;
		int wordLength = word.length;
		
		switch (orientation) {
		 case N: 
			 int minRowIndex = getMinRowIndex(matriz, colIndex);
			 while (colIndex - wordLength < minRowIndex) {
				 colIndex = randomInt(numColumns);
			 }
			 for (int i = wordLength - 1; i >= minRowIndex; i--) {
				 matriz[i][colIndex] = word[wordLength - i - 1];
				 log("i: " + i + " j: "+ colIndex);
			 }
			 break;
		 case S: 
			 int maxRowIndex = getMaxRowIndex(matriz, colIndex, numColumns);
			 while (colIndex + wordLength > maxRowIndex) {
				 colIndex = randomInt(numColumns);
			 }
			 for (int i = 0; i < wordLength; i++) {
				 matriz[i][colIndex] = word[i];
				 log("i: " + i + "j: "+ colIndex);
			 }
			 break;
		 case E:
			 int maxColumnIndex = getMaxColumnIndex(matriz, colIndex, numColumns);
			 while (colIndex + wordLength > maxColumnIndex) {
				 colIndex = randomInt(numColumns);
			 }
			 for (int i = colIndex; i < colIndex + wordLength; i++) {
				 matriz[0][i] = word[i - colIndex];
				 log("i: " + i);
			 }
			 break;
		 case W:
			 int minColumnIndex = getMinColumnIndex(matriz, colIndex);
			 while (colIndex - wordLength < minColumnIndex) {
				 colIndex = randomInt(numColumns);
			 }
			 for (int i = colIndex; i > colIndex - word.length; i--) {
				 matriz[0][i] = word[colIndex - i];
				 log("i: " + i);
			 }
			 break;
		 }
		
	}
	


	
	private static int getMinRowIndex(char[][] matriz, int colIndex) {
		for (int i = colIndex; i > 0; i--) {
			if(matriz[i][0] != '\0') {
				return i;
			}
		}
		return 0;
	}
	
	private static int getMaxRowIndex(char[][] matriz, int colIndex, int numColumns) {
		for (int i = colIndex; i < numColumns; i++) {
			if (matriz[i][0] != '\0') {
				return i;
			}
		}
		return numColumns;
	}
	
	private static int getMaxColumnIndex(char [][] matriz, int index, int numColumns) {
		for (int i = index; i < numColumns; i++) {
			if (matriz[0][i] != '\0') {
				return i;
			}
		}
		return numColumns;
	}
	
	private static int getMinColumnIndex(char[][] matriz, int colIndex) {
		for (int i = colIndex; i > 0; i--) {
			if(matriz[0][i] != '\0') {
				return i;
			}
		}
		return 0;
	}
	
	
	private static char [] wordToChar(String word) {
		char[] charArray = word.toCharArray();
		log("Word: " + word + " : " + Arrays.toString(charArray));
		return charArray;
	}
		
	private static int[] randOrder(int size) {
		int[] order = new int [size];
		int check, counter = 0;
		int count = 0;
		
		check = randomInt(size);
		order[count++] = check;
		
		while (count < size) {
			check = randomInt(size);
			boolean exist = false;
			for (int i = 0; i < count; i++) {
				if (order[i] == check) {
					exist = true;
					break;
				} 
			}
			counter ++;
			
			if (!exist) {
				order[count] = check;
				if (debug) System.out.println(Arrays.toString(order));
				count ++;
			}	
		}
		return order;
	}
	
	private static boolean checkIfEmpty(int x, int y, int length, char[][] matriz) {
		boolean empty = false;
		return empty;
		
	}
	
	
	private static int randomInt(int size) {
		int value = new Random().nextInt(size);
		//log("Next int: " + value);
		return value;
	}
	
	private static void log(String message) {
		if (debug) {
			System.out.println(message);
		}
	}

	public static void printMatrix(char[][] matriz) {
		System.out.println("   0 | 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9");
		for (int i = 0; i<10; i++) {
			System.out.print(i + "|" + " ");
			for (int j = 0; j<10; j++) {
				System.out.print(matriz[i][j] + "   ");
			}
			System.out.println("|" + i);
		}
		System.out.println("   0 | 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9");
		System.out.println();
	}
}
