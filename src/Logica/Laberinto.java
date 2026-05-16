package Logica;

import java.util.Arrays;

public class Laberinto {

	private static final char LIBRE = ' ';
	private static final char MURO = '#';
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
		buscarCamino(1, 1);
	}

	private static void buscarCamino(int fila, int col) {

		// Si no es una casilla libre, no seguimos
		if (lab[fila][col] != LIBRE) {
			return;
		}
		
		// Caso base: llegamos a la salida
		if (fila == alto - 2 && col == ancho - 2) {
			lab[fila][col] = CAMINO;
			
			char[][] copia = UtilidadesMatriz.clonar(lab);
	        
			soluciones = Arrays.copyOf(soluciones, soluciones.length + 1);
			soluciones[soluciones.length - 1] = copia;
			
			lab[fila][col] = LIBRE;
			return;
		}

		

		// Marcamos como parte del camino
		lab[fila][col] = CAMINO;

		// Movimientos:
		// derecha, abajo, izquierda, arriba
		int[] df = { 0, 1, 0, -1 };
		int[] dc = { 1, 0, -1, 0 };

		// Backtracking
		for (int i = 0; i < 4; i++) {
			buscarCamino(fila+df[i], col+dc[i]);
		}

		lab[fila][col] = LIBRE;
		return;
	}


	public static char[][][] getSoluciones() {
		return soluciones;
	}
}