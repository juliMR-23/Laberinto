package Logica;

import java.util.Arrays;

public class Laberinto {

	private static final char LIBRE = ' ';
	//private static final char MURO = '#';
	private static final char CAMINO = '.';

	private static int alto;
	private static int ancho;

	private static char[][] lab;
	private static char[][][] soluciones;

	public static void resolver(char[][] l) {
		lab = l;
		alto = lab.length;
		ancho = lab[0].length;
		soluciones=new char[0][][];
		if(lab[1][1]!=LIBRE)
			return;
		lab[1][1]=CAMINO;
		buscarCamino(1, 1);
	}

	private static void buscarCamino(int fila, int col) {
		
		// Caso base: llegamos a la salida
		if (fila == alto - 2 && col == ancho - 2) {
			char[][] copia = UtilidadesMatriz.clonar(lab);//apunta a un objeto distinto para no sobrescribir
	        
			soluciones = Arrays.copyOf(soluciones, soluciones.length + 1);
			soluciones[soluciones.length - 1] = copia;//guardar en la lista de soluciones
			
			return;
		}

		// Movimientos:
		// derecha, abajo, izquierda, arriba
		int[] df = { 0, 1, 0, -1 };
		int[] dc = { 1, 0, -1, 0 };

		// Backtracking
		for (int i = 0; i < 4; i++) {
			int nuevaFila=fila+df[i];
			int nuevaCol=col+dc[i];
			
			if (lab[nuevaFila][nuevaCol] == LIBRE) {//ver si es válido
				lab[nuevaFila][nuevaCol] = CAMINO;//elegir
				buscarCamino(nuevaFila, nuevaCol);//recursión
				lab[nuevaFila][nuevaCol] = LIBRE;//descartar
			}
		}
	}


	public static char[][][] getSoluciones() {
		return soluciones;
	}
}