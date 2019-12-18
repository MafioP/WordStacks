import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainScanner {
	
	/**
	 * lee la entrada y comprueba si es valida para ser usada en el resto del programa
	 * @return
	 */
	public static String readInput() {
		String invalidInput = "0";
		String readInput;
		Scanner in = new Scanner(System.in);
		readInput = in.next();
		
		if (readInput.length() <=4 && readInput.length() != 2) {
			switch (readInput.length()) {
				case 4:
					if (validate(readInput)) {
						return readInput;
					} else {
						System.out.println("Entrada no valida");
					}
					break;
				case 3:
					if (validateClues(readInput)) {
						return readInput;
					} else {
						System.out.println("Entrada no valida");
					}
					break;
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
	private static boolean validate(String input) {
		if (input == null) {
			return false;
		}
		Pattern pattern = Pattern.compile("\\d\\d([NSEO]|[nseo])\\d");
		Matcher matcher = pattern.matcher(input);
		
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
