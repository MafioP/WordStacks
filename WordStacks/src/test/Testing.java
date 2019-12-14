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
		MatrizGenerator.printMatrix(matriz);
		return matriz;
	}
	private static int randomInt() {
		int value = new Random().nextInt(SIZE);
		if (debug) System.out.println("Next int: " + value);
		return value;
	}

}
