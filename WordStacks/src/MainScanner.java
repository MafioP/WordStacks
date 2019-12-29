
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainScanner {
	
	public static String readFile() {
		String filepath = "data.txt";
		String data = "";
		try {
			Scanner fReader = new Scanner(new File(filepath));
			data = fReader.nextLine();
			
			fReader.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		
		if (readInput.length() <=4 && readInput.length() != 2) {
			switch (readInput.length()) {
				case 4:
					if (validateCoords(readInput)) {
						return readInput;
					} else {
						System.out.println("Entrada no valida 4");
					}
					break;
				case 3:
					if (validateClues(readInput)) {
						return readInput;
					} else {
						System.out.println("Entrada no valida 3");
					}
					break;
				default:
					System.out.println("Caso pendiente");
				}
			
		} else {
			System.out.println("Entrada no valida general");
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
						System.out.println(y-l);
						return false;
					}
					break;
				case 'O':
					if (x - l < 0) {
						return false;
					}
					break;
				default:
					break;
			}
		}
		return matcher.matches();
	}
	
	private static boolean validateClues(String input) {
		if (input == null) {
			return false;
		}
		Pattern pattern = Pattern.compile("(LET)|(POS)|(PAL)");
		Matcher matcher = pattern.matcher(input);	
		
		return matcher.matches();
	}
	
}
