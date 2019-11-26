
public class Diccionario {
	private static String[] diccionario() {
		String [] listapalabras = {"ORDENADOR", "MONITOR", "TECLADO", "RATON", "ALTAVOZ", "DISCO", "GRAFICA", "PROGRAMA", "CODIGO", "ALGORITMO", "METODO", "FUNCION", "INTERFAZ", "VARIABLE", "CLASE", "MATRIZ", "ARCHIVO","FICHERO","CARPETA","CODIGO","EJECUTAR","ENCRIPTAR","ERROR","HACKER","RED","SISTEMA","USUARIO","WIFI","VIRUS","WEB","DIGITAL"};
		return listapalabras;
	}
	public static String[] listaPrueba() {
		String [] listaprueba = {"AAA", "AAAAAA", "BBBBBBBBB", "CCCCC",
				"DDDDD", "EEEEE", "FFFFF", "GGGGG",
				"HHHHH", "IIIII", "JJJJJ"};
		return listaprueba;
	}
	public static String[] listaNormal(String [] listapalabras) { // 10 palabras aleatorias de diccionario
	
		String [] listanormal = diccionario();
		String[] palabras = {""};
		
		for(int i=0;i<10;i++) {
			int rand;
			
			rand = (int)Math.floor(Math.random()*listanormal.length);
			palabras[i] = listanormal[rand];
			
				
			}
			
		
		return palabras;
	}
}
