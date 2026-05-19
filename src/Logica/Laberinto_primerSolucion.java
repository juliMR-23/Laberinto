package Logica;
public class Laberinto_primerSolucion {

	private static final char LIBRE = ' ';
	// private static final char MURO = '#';
	private static final char CAMINO = '.';

	private static int alto;
	private static int ancho;
	private static boolean encontrado;

	private static char[][] lab;

	public static boolean resolver(char[][] l) {
		lab = l;
		if (lab[1][1] != LIBRE)// verifica que inicio no está tapado
			return false;

		alto = lab.length;
		ancho = lab[0].length;

		encontrado = false;

		lab[1][1] = CAMINO;// marca como parte del camino (siempre se cumple porque ahí inicia)
		buscarCamino(1, 1);
		return encontrado;
	}

	private static void buscarCamino(int fila, int col) {

		// Caso base: llegamos a la salida
		if (fila == alto - 2 && col == ancho - 2) {// final del laberinto
			encontrado = true;
			// ya encontró solución, flag= true y no se vuelve a modificar el lab
		}

		// Movimientos:
		// derecha, abajo, izquierda, arriba
		int[] df = { 0, 1, 0, -1 };
		int[] dc = { 1, 0, -1, 0 };

		// Backtracking
		int i = 0;
		while (i < 4 && !encontrado) {

			int nuevaFila = fila + df[i];
			int nuevaCol = col + dc[i];
			if (lab[nuevaFila][nuevaCol] == LIBRE && !encontrado) {// primero verificamos si está libre

				lab[nuevaFila][nuevaCol] = CAMINO;// marcamos en el camino actual

				buscarCamino(nuevaFila, nuevaCol);// recursividad

				if (!encontrado) {
					lab[nuevaFila][nuevaCol] = LIBRE;
				} // si no ha encontrado, deshace para seguir buscando caminos
			}
			i++;
		}
	}
	
	public static char[][] getLab() {
		return lab;
	}
}
