
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
	
	private static int counter = 0;
	
	public static char [][] generateMatriz(int numRows, int numColumns, String [] wordList) {
		if (numColumns <= 0 || numRows <= 0) {
			throw new RuntimeException("Error: dimensiones invalidas");
		}
		int numWords = wordList.length;
		final char [][] matriz = new char [numRows][numColumns];
		char [] charWord;
		int [] order = randOrder(numWords);
		final int maxAtemps = 10;
		
		for (int i = 0; i < numWords; i++) {	
			charWord = wordToChar(wordList[order[i]]);
			int atemps = 0;
			while (addWord(matriz, randomInt(numColumns), charWord) == 0) {
				log("WordCheck failed");
				if (atemps++ == maxAtemps) {
					log("Max attemps reached");
					if (counter++ < maxAtemps) {
						log("Restart generate");
						return generateMatriz(numColumns, numRows, wordList);
					} else {
						System.out.println("Error general");
						System.exit(-1);
					}
				}
			}
				
			for (int j = 0; j < numWords; j++) {
				gravity(matriz, numRows);
			}
			if (debug) {
				System.out.println("Counter: " + counter);
				printMatrix(matriz);
			}
		}
		return matriz;
	}
	
	private static void gravity(char[][] matriz, int numRows) {
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
	 * @param colIndex numero aleatorio que indica una posicion de columna donde
	 * empezaria a colocar la palabra
	 * @param word palabra formada por chars
	 */
	private static int addWord(char [][] matriz, int colIndex, char[] word) {
		Orientation orientation = Orientation.random();
		log("Orientation = " + orientation.getName());
		int numRows = matriz.length;
		int numColumns = matriz[0].length;
		int wordLength = word.length;
		final int maxRandom = 50;
		int currentRandom = 0;
		
		switch (orientation) {
		 case N: 
		 case S: 
			 int maxRowIndex = getMaxRowIndex(matriz, wordLength, colIndex);
			 while (wordLength >= maxRowIndex) {
				 colIndex = randomInt(numRows);
				 maxRowIndex = getMaxRowIndex(matriz, wordLength, colIndex);
				 if (currentRandom++ == maxRandom) {
					 return 0;
				 }
			 }
			 for (int i = 0; i < wordLength; i++) {
				 assignLetter(matriz, i, colIndex, 
						 orientation == Orientation.N ? 
								 word[wordLength - i - 1] : 
								 word[i]);
			 }
			 break;
		 case E:
			 int maxColumnIndex = getMaxColumnIndex(matriz, colIndex, numColumns);
			 while (colIndex + wordLength >= maxColumnIndex) {
				 colIndex = randomInt(numColumns);
				 maxColumnIndex = getMaxColumnIndex(matriz, colIndex, numColumns);
				 if (currentRandom++ == maxRandom) {
					 return 0;
				 }
			 }
			 for (int i = colIndex; i < colIndex + wordLength; i++) {
				 assignLetter(matriz, 0, i, word[i - colIndex]);
			 }
			 break;
		 case W:
			 int minColumnIndex = getMinColumnIndex(matriz, colIndex);
			 while (colIndex - wordLength <= minColumnIndex) {
				 colIndex = randomInt(numColumns);
				 minColumnIndex = getMinColumnIndex(matriz, colIndex);
				 if (currentRandom++ == maxRandom) {
					 return 0;
				 }
			 }
			 for (int i = colIndex; i > colIndex - word.length; i--) {
				 assignLetter(matriz, 0, i, word[colIndex - i]);
			 }
			 break;
		 }
		return 1;
	}

	private static void assignLetter(char[][] matriz, int rowIndex, int colIndex, char letter) {
		log("Row: " + rowIndex + ", Col: " + colIndex + ", Letter: " + letter);
		if (matriz[rowIndex][colIndex] != '\0') {
			 System.out.println("Conflicto en " + matriz[rowIndex][colIndex] + ", Row: " + rowIndex + ", Col: " + colIndex);
			 System.exit(-1);
		 }
		 matriz[rowIndex][colIndex] = letter;
		 
	}
	
	private static int getMaxRowIndex(char[][] matriz, int wordLength, int colIndex) {
		for (int i = 0; i < wordLength; i++) {
			if (matriz[i][colIndex] != '\0') {
				return i;
			}
		}
		return matriz.length;
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
			if (matriz[0][i] != '\0') {
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
		int check;
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
			
			if (!exist) {
				order[count] = check;
				log(Arrays.toString(order));
				count ++;
			}	
		}
		return order;
	}
	
	private static int randomInt(int size) {
		int value = new Random().nextInt(size);
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

