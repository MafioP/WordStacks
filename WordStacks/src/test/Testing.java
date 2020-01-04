package test;

public class Testing {

	public static void main(String[] args) {
		String [] listaPalabras = {"ORDENADOR", "MONITOR", "TECLADO", "RATON", "ALTAVOZ", "DISCO", "GRAFICA", "PROGRAMA", "CODIGO", "ALGORITMO"};
		generateMatriz(listaPalabras);
	}
	private static char[][] generateMatriz(String[] tipoLista) {
		char [][] matriz = MatrizGenerator.generateMatriz (10, 10, tipoLista);
		MatrizGenerator.printMatrix(matriz);
		return matriz;
	}

}
