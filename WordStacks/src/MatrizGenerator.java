
public class MatrizGenerator {
	public static char [][] generateMatriz(int x, int y, String [] wordList){
		char [][] matriz = new char [x][y];
		for (int i=0; i<wordList.length; i++) {
			wordToChar(wordList);
		}
		return matriz;
	}
	
	private char [][] wordToChar(String [] wordList){
		char [][] word = new char [10][10];
		
		return word;
	}
	/**
	 * Generar una orientacion aleatoria a partir de un int
	 * @return
	 */
	private int randOrientation() {
		int rand = 0;
		rand = (int)Math.floor(Math.random()*4 + 1);
		return rand;
	}
	
	private int[] randOrder() {
		int[] order = new int [10];
		int check = 0;
		for(int i=0; i<10; i++) {
			check = (int)Math.floor(Math.random()*11);
			for (int j=0; j<10; j++) {
				if (check != order[j]) {
					check = order[i];
				}
			}
		}
		return order;
	}
}
