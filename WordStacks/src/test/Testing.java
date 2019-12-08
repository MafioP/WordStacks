package test;

import java.util.Arrays;
import java.util.Random;



public class Testing {
	
	public static final int SIZE = 20;
	private static boolean debug = !true;

	public static void main(String[] args) {
		String [] listaPalabras = {"ORDENADOR", "MONITOR", "TECLADO", "RATON", "ALTAVOZ", "DISCO", "GRAFICA", "PROGRAMA", "CODIGO", "ALGORITMO"};
		generateMatriz(listaPalabras);
	}
	private static char[][] generateMatriz(String[] tipoLista) {
		char [][] matriz = MatrizGenerator.generateMatriz (10, 10, tipoLista);
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

	private static int randomInt() {
		int value = new Random().nextInt(SIZE);
		if (debug) System.out.println("Next int: " + value);
		return value;
	}

}
