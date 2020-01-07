

public class Diccionario {
	
	/**
	 * Almacena todas las palabras de nuestro diccionario
	 * @return
	 */
	private static String[] diccionario() {
		String [] listapalabras = {"ORDENADOR", "MONITOR", "TECLADO", "RATON", "ALTAVOZ", "DISCO", "GRAFICA", "PROGRAMA", "CODIGO", "ALGORITMO", "METODO", "FUNCION", "INTERFAZ", "VARIABLE", "CLASE", "MATRIZ",
				"ARCHIVO","FICHERO","CARPETA","CODIGO","EJECUTAR","ENCRIPTAR","ERROR","HACKER","RED","SISTEMA","USUARIO","WIFI","VIRUS","WEB","DIGITAL"};
		return listapalabras;
	}
	
	/**
	 * Alamacena las palabras de prueba
	 * @return
	 */
	public static String[] listaPrueba() {
		String [] listaprueba = {"AAA", "AAAAAA", "BBBBBBBBB", "CCCC","DDDDD", "EEEEE", "FFFFF", "GGGGG","HHHHH", "IIIII", "JJJJJ"};
		return listaprueba;
	}
	
	/**
	 * Devuelve 10 palabras aleatorias sin repeticion del diccionario
	 * @return
	 */
	public static String[] listaNormal() { // 10 palabras aleatorias de diccionario

		String[] listaNormal = diccionario();
		String[] listaRand = new String[10];
		int[] numsAleatorio = acumularRand(listaNormal.length);
		
		for (int i = 0; i < listaRand.length; i++) {
			listaRand[i] = listaNormal[numsAleatorio[i]];
		}
	
		return listaRand;
	}
	
	/**
	 * Devuelve un int aleatorio entre 0 y x
	 * @param x
	 * @return
	 */
	private static int randInt(int x) {
		int rand = (int) Math.floor(Math.random() * x);
		return rand;
	}
	/**
	 * Devuelve un array de 10 ints aleatorios sin repetirse 
	 * @param x
	 * @return
	 */
	private static int[]acumularRand(int x) {
		int[] acumular = new int[10];
		int num = randInt(x);
		int cont = 0;
		acumular[cont] = num;
		cont++;
		
		while (cont < 10) {
			num = randInt(x);
			boolean exist = false;
			
			for (int i = 0; i < cont; i++) {
				if (acumular[i] == num) {
					exist = true;
					break;
				}
			}
			if (!exist) {
				acumular[cont] = num;
				cont++;
			}
		}
		return acumular;
	}
}

