import java.io.IOException;
import java.util.Scanner;

public class Main{
	public static void main(String[] args) {
		char modo = 0;
		int x, y, length;
		char orientacion;
		char [][] letras = new char [10][10];
		String coords;
		char [] values = new char[4];
		Scanner in = new Scanner(System.in);
		String [] listaPrueba = {"AAA", "AAAAAA", "BBBBBBBBB", "COCK",
				"DDDDD", "EEEEE", "FFFFF", "GGGGG",
				"HHHHH", "IIIII", "JJJJJ"};
		String [] listaPalabras = {"AAA", "AAAAAA", "BBBBBBBBB", "CCCCC",
				"DDDDD", "EEEEE", "FFFFF", "GGGGG",
				"HHHHH", "IIIII", "JJJJJ"};
		
		letras = init(modo, listaPrueba, listaPalabras);
		
		while(listaPalabras.length>0 || listaPrueba.length>0) {
			System.out.println("introduzca las coordenadas de la palabra de la forma x, y, orientacion, longitud. Por ejemplo 42N7");
			
			coords = in.next();
			values = readValue(coords);
			
			
			//leer los caracteres y convertirlos a int si es necesario
			x = Character.getNumericValue(values[0]);
			y = Character.getNumericValue(values[1]);
			orientacion = values[2];
			length = Character.getNumericValue(values[3]);
			
			
			
			System.out.println(letras[y][x]);
		}
		
	}



	private static char [] readValue(String coords) {
		char [] values = new char [4];
		for(int i=0; i<coords.length(); i++) {
			values[i] = coords.charAt(i);
		}
		return values;
		
	}

	private static char [][] init(char modo, String[] listaPrueba, String[] listaPalabras) {
		char [][] letras = new char [10][10];
		System.out.println("Introduzca P o p para seleccionar modo prueba, introduzca otra letra para el modo normal");
		try {
			modo = (char) System.in.read();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(modo == 'p' || modo == 'P') {
			letras = generateMatriz(listaPrueba);
		}else {
			letras = generateMatriz(listaPalabras);
		}
		return letras;
	}

	private static char[][] generateMatriz(String[] tipoLista) {
		char [][] matriz = NuevaMatriz.nuevaMatriz (10, 10, tipoLista);
		System.out.println("   0 | 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9");
		for (int i = 0; i<10; i++) {
			System.out.print(i + "|" + " ");
			for (int j = 0; j<10; j++) {
				System.out.print(matriz[i][j] + "   ");
			}
			System.out.println("|" + i);
		}
		System.out.println("   0 | 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9");
		return matriz;
	}
}