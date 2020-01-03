
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
		int score = 10;
		char orientacion;
		char [][] letras = new char[10][10];
		char [] values = new char[4];
		
		Scanner in = new Scanner(System.in);
		
		if (MainScanner.readFile() != 0) {
			score = MainScanner.readFile();
			System.out.println("La puntuacion anteriormente guardada es: " + score);
		}
		
		String [] listaPrueba = Diccionario.listaPrueba();
		String [] listaPalabras = Diccionario.listaNormal();

		System.out.println("Introduzca P o p para seleccionar modo prueba, introduzca otra letra para el modo normal");
		String [] wordList = setMode(listaPrueba, listaPalabras);
		
		Word [] readableWordList = new Word[wordList.length];
		
		while (true) { 
		
			while (wordList.length > 0) { //Mientras queden palabras en la lista el juego continua
				letras = generateMatriz(wordList); 
				boolean clueGive = false;
	
				while (readableWords(wordList, letras, readableWordList)) { //Mientras haya palabras legibles en la tabla se repite el bucle
					System.out.println("Introduzca coordenadas o solicite una pista");
					
					//lee las coordenadas de la palabra
					String input = MainScanner.readInput(in); //Lee la entrada del scanner que verifica si es valida
					String coords = "";
					String clues = "";
					while (input == "0") {
						input = MainScanner.readInput(in); //Si la entrada no es valida la vuelve a pedir
					}
					if (input.length() == 4) { //Si la entrada son coordenadas se ejecuta el codigo de coordenadas y sino el de pistas
						coords = input;
						values = readValue(coords);
					
						//leer los caracteres y convertirlos a int
						x = Character.getNumericValue(values[0]);
						y = Character.getNumericValue(values[1]);
						orientacion = values[2];
						length = Character.getNumericValue(values[3]);
						
						String wordGuess = getWord(x, y, length, letras, orientacion);
						
						for(int i=0; i<wordList.length; i++) { //Se compara la palabra encontrada a partir de las coordenadas con la lista de palabras
							if (wordList[i].equals(wordGuess)) {
								System.out.println(wordGuess + " esta en la lista de palabras");
								List<String> list = new ArrayList<String>(Arrays.asList(wordList)); //Eliminar la palabra encontrada de la lista
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
						for (int i = 0; i < readableWordList.length; i++) { //Vaciar todas las palabras legibles para evitar errores
							readableWordList[i] = null;
						}
					} else if (input.length() == 3) {
						clues = input;
						score = giveClue(clues, readableWordList, score); //Al pedir una pista se restan los puntos necesarios
						System.out.println("Puntuacion: " + score);
					}
				}
				
				System.out.println("Se acabaron las palabras legibles");
				try {
					PrintWriter pWriter = new PrintWriter(new FileWriter("data.txt", false)); //Sobreescribe la puntuacion obtenida en el archivo data
					pWriter.println(score);
					pWriter.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			System.out.println("Quieres seguir jugando? (escribe si o no)"); 
			if (MainScanner.readInput(in).equals("si")) { //Si el jugador quiere volver a empezar la partida se repite el primer while y sino se sale del bucle
				System.out.println("Empezando nueva partida...");
			} else {
				System.out.println("Cerrando el juego");
				break;
			}
		}
	}
	/**
	 * Escribe en consola la pista solicitada y actualiza la puntuacion segun esta
	 * @param clues
	 * @param readableWordList
	 * @param score
	 * @return nueva puntuacion
	 */
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

	/**
	 * Busca en las cuatro orientaciones posibles y compara cada linea de la busqueda con la lista de palabras
	 * @param wordList
	 * @param letras
	 * @param readableWordList
	 * @return
	 */
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
				if (count >= 10) {
					count--;
				}
				readableWordList[count++] = new Word(word, x, y);

				exist = true;
			}
		}
		return exist;
	}

	/**
	 * 
	 * @param word
	 * @param rowWord
	 * @return
	 */
	private static int getPosCoords(String word, String rowWord) {
		int n = rowWord.indexOf(word);
		return n;
	}
	
	/**
	 * 
	 * @param word
	 * @param columnWord
	 * @return
	 */
	private static int getNegCoords(String word, String columnWord) {
		int n = columnWord.length() - columnWord.indexOf(word);
		return n;
	}

	/**
	 * Comprueba si la linea leida en "readableWords" esta en la lista de palabras
	 * @param word
	 * @param wordList
	 * @return
	 */
	private static String checkword(String word, String[] wordList) {
		for(int i = 0; i < wordList.length; i++) {
			if(word.contains(wordList[i])) {
				return wordList[i];
			}
		}
		return null;
	}
	/**
	 * A partir de las coordenadas devuelve la palabra encontrada
	 * @param x
	 * @param y
	 * @param length
	 * @param letras
	 * @param orientacion
	 * @return
	 */
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
	
	/**
	 * Elimina la palabra de la lista y hace que las letras que queden por encima bajen hasta que no puedan mas
	 * @param letras
	 * @param x
	 * @param y
	 * @param length
	 * @param orientacion
	 * @return
	 */
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
	
	/**
	 * Vuelve a escribir la matriz en la consola
	 * @param letras
	 */
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
	
	/**
	 * Transforma el string de coordenadas en 4 chars
	 * @param coords
	 * @return
	 */
	private static char [] readValue(String coords) {
		char [] values = new char [4];
		for(int i=0; i<coords.length(); i++) {
			values[i] = coords.charAt(i);
		}
		return values;
	}
	
	/**
	 * Dependiendo del modo devuelve listaPrueva o listaPalabras
	 * @param listaPrueba
	 * @param listaPalabras
	 * @return
	 */
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
	
	/**
	 * Genera y escribe la matriz inicial en consola
	 * @param listType
	 * @return
	 */
	private static char[][] generateMatriz(String[] listType) {
		char [][] matriz = NuevaMatriz.nuevaMatriz (10, 10, listType);
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

