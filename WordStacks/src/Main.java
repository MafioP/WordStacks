import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;


public class Main {
	
	public static void main(String[] args) {
		int x, y, length;
		int score = 10, maxScore;
		char orientacion;
		char [][] letras = new char [10][10];
		char [] values = new char[4];
		Scanner in = new Scanner(System.in);
		
		String [] wordList; 
		String [] listaPrueba = Diccionario.listaPrueba();
		String [] listaPalabras = Diccionario.listaNormal();

		System.out.println("Introduzca P o p para seleccionar modo prueba, introduzca otra letra para el modo normal");
		wordList = setMode(listaPrueba, listaPalabras);
		while(wordList.length > 0) {
		
			letras = generateMatriz(wordList);
			
			while (readableWords(wordList)) {
				System.out.println("introduzca las coordenadas de la palabra de la forma x, y, orientacion, longitud. Por ejemplo 42N7");
				
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
				}
				values = readValue(coords);
				System.out.println("coordenadas" + coords);
				
				//leer los caracteres y convertirlos a int
				x = Character.getNumericValue(values[0]);
				y = Character.getNumericValue(values[1]);
				orientacion = values[2];
				length = Character.getNumericValue(values[3]);
				
				String wordGuess = getWord(x, y, length, letras, orientacion);
				
				for(int i=0; i<wordList.length; i++) {
					if (wordList[i].equals(wordGuess)) {
						List<String> list = new ArrayList<String>(Arrays.asList(wordList));
						list.remove(i);
						wordList = list.toArray(new String[0]);
						System.out.println("WordList length: " + wordList.length); 
						score ++;
						System.out.println("Score: " + score);
						letras = wordRemove(letras, x, y, length, orientacion); //eliminar la palabra de la tabla
						break;
					}else {
						//TODO
				}
			}
			System.out.println(wordGuess);
		}
		
		try {
			PrintWriter pWriter = new PrintWriter(new FileWriter("data.txt", true));
			pWriter.println("Puntuacion: " + score);
			pWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(MainScanner.readFile());
		}
	}

	private static boolean readableWords(String[] wordList) {
		
		
		// TODO Auto-generated method stub
		return false;
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
