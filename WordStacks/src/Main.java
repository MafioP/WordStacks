

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;


public class Main {
	
	public static void main(String[] args) {
		int x, y, length;
		int score = 10;
		char orientacion;
		char [][] letras = new char[10][10];
		char [] values = new char[4];
		
		Scanner in = new Scanner(System.in);
		
		if (MainScanner.readFile() != 0) {
			score = MainScanner.readFile();
		}
		
		String [] listaPrueba = Diccionario.listaPrueba();
		String [] listaPalabras = Diccionario.listaNormal();

		System.out.println("Introduzca P o p para seleccionar modo prueba, introduzca otra letra para el modo normal");
		String [] wordList = setMode(listaPrueba, listaPalabras);
		
		Word [] readableWordList = new Word[listaPalabras.length];
		
		while (true) {
		
			while (wordList.length > 0) {
				letras = generateMatriz(wordList);
	
				while (readableWords(wordList, letras, readableWordList)) {
					System.out.println("introduzca coordenadas o solicite una pista");
					
					//lee las coordenadas de la palabra
					String input = MainScanner.readInput(in);
					String coords = "";
					String clues = "";
					while (input == "0") {
						input = MainScanner.readInput(in);
					}
					if (input.length() == 4) {
						coords = input;
					} else if (input.length() == 3) {
						clues = input;
						score = giveClue(clues, readableWordList, score);
					}
					
					values = readValue(coords);
					
					//leer los caracteres y convertirlos a int
					x = Character.getNumericValue(values[0]);
					y = Character.getNumericValue(values[1]);
					orientacion = values[2];
					length = Character.getNumericValue(values[3]);
					
					String wordGuess = getWord(x, y, length, letras, orientacion);
					
					for(int i=0; i<wordList.length; i++) {
						if (wordList[i].equals(wordGuess)) {
							System.out.println(wordGuess + " esta en la lista de palabras");
							List<String> list = new ArrayList<String>(Arrays.asList(wordList));
							list.remove(i);
							wordList = list.toArray(new String[0]);
							score ++;
							System.out.println("Puntuacion: " + score);			
							letras = wordRemove(letras, x, y, length, orientacion); //eliminar la palabra de la tabla
							break;
						}else if (i == wordList.length - 1){
							System.out.println(wordGuess + " no esta en la lista de palabras");
					}
				}
				for (int i = 0; i < readableWordList.length; i++) {
					readableWordList[i] = null;
				}
					
			}
				System.out.println("Se acabaron las palabras legibles");
			
			try {
				PrintWriter pWriter = new PrintWriter(new FileWriter("data.txt", false));
				pWriter.println("Puntuacion final: " + score);
				pWriter.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(MainScanner.readFile());
			}
			System.out.println("Quieres seguir jugando? (escribe si o no)");
			if (MainScanner.readInput(in).equals("si")) {
				System.out.println("Empezando nueva partida...");
			} else {
				break;
			}
		}
	}
	
	private static int giveClue(String clues, Word [] readableWordList, int score) {
		int wordNum = 0;
		for (int i = 0; i < readableWordList.length; i++) {
			if(readableWordList[i] != null) {
				wordNum++;
			}
		}
		int rand = (int)Math.floor(Math.random()*wordNum);
		switch(clues) {
		case "LET":
			System.out.println("La palabra empieza por la letra " + readableWordList[rand].getWord().charAt(0));
			score--;
			break;
		case "POS":
			System.out.println("La palabra empieza en X: " + readableWordList[rand].getX() + " Y: " + readableWordList[rand].getY());
			score -= 2;
			break;
		case "PAL":
			if (score > readableWordList[rand].getWord().length()) {
				System.out.println("Tu palabra es " + readableWordList[rand].getWord());
				score -= readableWordList[rand].getWord().length();
			} else {
				System.out.println("No tienes puntos suficientes para usar esta pista");
			}
			
			break;
		}
		return score;
	}


	private static boolean readableWords(String [] wordList, char [][] letras, Word [] readableWordList) {
		String word;
		int count = 0;
		int x = 0, y = 0;
		boolean exist = false;
		
		for (int j = 0; j < letras[0].length; j++) {
			String columnWord = "";
			
			//Busqueda en direccion sur
			for (int i = 0; i < letras.length; i++) {
				columnWord += letras[i][j];
			}
			
			if ((word = checkword(columnWord, wordList)) != null) {
				x = j;
				y = getPosCoords(word, columnWord);
				//System.out.println("Word X: " + x + " Y: " + y);
				readableWordList[count++] = new Word(word, x, y);

				exist = true;
			}
			columnWord = "";
			//Busqueda en direccion norte
			for (int i = letras.length - 1; i > 0; i--) {
				columnWord += letras[i][j];
			}
			if ((word = checkword(columnWord, wordList)) != null) {
				x = j;
				y = getNegCoords(word, columnWord);
				//System.out.println("Word X: " + x + " Y: " + y);
				readableWordList[count++] = new Word(word, x, y);

				exist = true;
			}
		}
		for (int j = 0; j < letras.length; j++) {
			String rowWord = "";
			//Busqueda en direccion este
			for (int i = 0; i < letras[0].length; i++) {
				rowWord += letras[j][i];
			}
			if ((word = checkword(rowWord, wordList)) != null) {
				y = j;
				x = getPosCoords(word, rowWord);
				//System.out.println("Word X: " + x + " Y: " + y);
				
				readableWordList[count++] = new Word(word, x, y);

				exist = true;
			}
			rowWord = "";
			//Busqueda en direccion oeste
			for (int i = letras[0].length - 1; i > 0; i--) {
				rowWord += letras[j][i];
			}
			if ((word = checkword(rowWord, wordList)) != null) {
				y = j;
				x = getNegCoords(word, rowWord);
				//System.out.println("Word X: " + x + " Y: " + y);
				if (count >= 10) {
					count--;
				}
				readableWordList[count++] = new Word(word, x, y);

				exist = true;
			}
		}
		return exist;
	}


	private static int getPosCoords(String word, String rowWord) {
		int n = 0;
		n = rowWord.indexOf(word);
		/*while((rowWord.charAt(x) != word.charAt(0) && word.charAt(1) != rowWord.charAt(x + 1))) {
			x++;
		}*/
		return n;
	}

	private static int getNegCoords(String word, String columnWord) {
		int n = 0;
		n = columnWord.length() - columnWord.indexOf(word);
		
		/*while ((columnWord.charAt(y) != word.charAt(0) && word.charAt(1) != columnWord.charAt(y + 1))) {
			y++;
		}*/
		return n;
	}

	private static String checkword(String word, String[] wordList) {
		for(int i = 0; i < wordList.length; i++) {
			if(word.contains(wordList[i])) {
				return wordList[i];
			}
		}
		return null;
	}

	private static String getWord(int x, int y, int length, char[][] letras, char orientacion) {
		String word = "";
		
		switch (orientacion) {
		case 'N':
			for (int i=y; i>y-length; i--) {
				word += letras[i][x];	
			}
			break;
		case 'S':
			for (int i = y; i < length + y; i++) {
				word += letras[i][x];	
			}
			break;
		case 'O':
			for (int i=x; i>x-length; i--) {
				word += letras[y][i];	
				}
			break;
		case 'E':
			for (int i=x; i<length+x; i++) {
				word += letras[y][i];	
				}
			break;
		}
		
		return word;
		
	}
	
	private static char [][] wordRemove(char [][] letras, int x, int y, int length, char orientacion) {
		switch (orientacion) {
		case 'N':
			for (int i=y; i>=0; i--) {
				if(i-length>=0) {
					letras[i][x] = letras[i-length][x];
				}else {
					letras[i][x] = '\0';
				}
			}
			break;
		case 'S':
			for (int i=y+length-1; i>=0; i--) {
				if(i-length>=0) {
					letras[i][x] = letras[i-length][x];
				}else {
					letras[i][x] = '\0';
				}
			}
			break;
		case 'O':
			for (int j=y; j>=0; j--) {
				for(int i=x; i>x-length; i--) {
					if(j==0) {
						letras[j][i] = '\0';
					}else {
						letras[j][i]= letras[j-1][i];	
					}
				}
			}
			break;
		case 'E':
			for(int j=y; j>=0; j--) {
				for(int i=x; i<length+x; i++) {
					if(j==0) {
						letras[j][i] = '\0';
					}else {
						letras [j][i]= letras[j-1][i];	
					}
				}
			}
			break;
		}
		newGenerate(letras);
		return letras;
	}
	
	private static void newGenerate(char[][] letras) {
		System.out.println("   0 | 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9");
		for (int i = 0; i<10; i++) {
			System.out.print(i + "|" + " ");
			for (int j = 0; j<10; j++) {
				System.out.print(letras[i][j] + "   ");
			}
			System.out.println("|" + i);
		}
		System.out.println("   0 | 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9");
		
	}

	private static char [] readValue(String coords) {
		char [] values = new char [4];
		for(int i=0; i<coords.length(); i++) {
			values[i] = coords.charAt(i);
		}
		return values;
		
	}

	private static String[] setMode(String[] listaPrueba, String[] listaPalabras) {
		String[] wordList;
		char modo = '\0';
		try {
			modo = (char) System.in.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (modo == 'p' || modo == 'P') {
			wordList = listaPrueba;
		} else {
			wordList = listaPalabras;
		}
		return wordList;
	}
	
	private static char[][] generateMatriz(String[] tipoLista) {
		char [][] matriz = NuevaMatriz.nuevaMatriz (10, 10, tipoLista);
		System.out.println("   0 | 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9");
		for (int i = 0; i<matriz[0].length; i++) {
			System.out.print(i + "|" + " ");
			for (int j = 0; j<matriz.length; j++) {
				System.out.print(matriz[i][j] + "   ");
			}
			System.out.println("|" + i);
		}
		System.out.println("   0 | 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9");
		return matriz;
	}
}

