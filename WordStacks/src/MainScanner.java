
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainScanner {
	
	/**
	 * Lee el archivo data y devuelve la puntuacion
	 * @return
	 */
	public static int readFile() {
		String filepath = "data.txt";
		int data = 0;
		try {
			Scanner fReader = new Scanner(new File(filepath));
			data = fReader.nextInt();
			
			fReader.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("No hay un archivo de puntuaciones anteriores, se creara uno nuevo");
		}
		return data;
	}
	
	
	/**
	 * lee la entrada y comprueba si es valida para ser usada en el resto del programa
	 * @return
	 */
	public static String readInput(Scanner in) {
		String invalidInput = "0";
		String readInput;
		readInput = in.next();
		
		if (readInput.length() <=4) {
			switch (readInput.length()) {
				case 4:
					if (validateCoords(readInput)) {
						return readInput;
					} else {
						System.out.println("Coordenadas no validas");
					}
					break;
				case 3:
					if (validateClues(readInput)) {
						return readInput;
					} else {
						System.out.println("Pista no valida");
					}
					break;
				case 2:
					return readInput;
				default:
					System.out.println("Caso pendiente");
				}
			
		} else {
			System.out.println("Entrada no valida");
		}
		

		return invalidInput;
	}
	
	/**
	 * compara el input con el pattern (int,int,char,int) y devuelve true si es valido
	 * @param input
	 * @return
	 */
	private static boolean validateCoords(String input) {
		if (input == null) {
			return false;
		}
		Pattern pattern = Pattern.compile("\\d\\d[NSEO]\\d");
		Matcher matcher = pattern.matcher(input);
		if (matcher.matches()) {
			int x = Character.getNumericValue(input.charAt(0));
			int y = Character.getNumericValue(input.charAt(1));
			int l = Character.getNumericValue(input.charAt(3));
			char o = input.charAt(2);
			switch (o) {
				case 'N':
					if (y - l < -1) {
						return false;
					}
					break;
				case 'O':
					if (x + 1 - l < 0) {
						return false;
					}
					break;
				default:
					break;
			}
		}
		return matcher.matches();
	}
	
	/**
	 * Verifica si la solicitud de una pista es valida
	 * @param input
	 * @return
	 */
	private static boolean validateClues(String input) {
		if (input == null) {
			return false;
		}
		Pattern pattern = Pattern.compile("(LET)|(POS)|(PAL)");
		Matcher matcher = pattern.matcher(input);	
		
		return matcher.matches();
	}
	
}
