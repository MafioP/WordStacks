import java.util.Scanner;

public class MainScanner {
	public static String readInput() {
		String invalidInput = "0";
		String readCoords;
		Scanner in = new Scanner(System.in);
		readCoords = in.next();
		
		if(readCoords.length() <=4 && readCoords.length() != 2) {
			switch(readCoords.length()) {
			case 4:
				try {
					int x = Character.getNumericValue(readCoords.charAt(0));
					int y = Character.getNumericValue(readCoords.charAt(1));
					char o = readCoords.charAt(2);
					int l = Character.getNumericValue(readCoords.charAt(3));
					System.out.println("valid");
					return readCoords;
				}catch (Exception e) {
					e.printStackTrace();
					return invalidInput;
				}
			}
			
		} else {
			System.out.println("Entrada no valida");
		}

		return invalidInput;
	}
}
