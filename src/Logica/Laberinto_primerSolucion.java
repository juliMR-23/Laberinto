package Logica;
public class Laberinto_primerSolucion {

	private static final char LIBRE = ' ';
	private static final char MURO = '#';
	private static final char CAMINO = '.';
	private static final char IMPOSIBLE = 'I';

	private static int alto;
	private static int ancho;

	private static char[][] lab;
    
    public static boolean resolver(char[][] l)
    {
    	lab=l;
    	alto=lab.length;
    	ancho=lab[0].length;
    	return buscarCamino(1, 1);
    }

    private static boolean buscarCamino(int fila, int col) {

        // Caso base: llegamos a la salida
        if (fila == alto - 2 && col == ancho - 2) {
            lab[fila][col] = CAMINO;
            return true;
        }

        // Si no es una casilla libre, no seguimos
        if (lab[fila][col] != LIBRE) {
            return false;
        }

        // Marcamos como parte del camino
        lab[fila][col] = CAMINO;

        // Movimientos:
        // derecha, abajo, izquierda, arriba
        int[] df = {0, 1, 0, -1};
        int[] dc = {1, 0, -1, 0};

        // Backtracking
        for (int i = 0; i < 4; i++) {

            int nuevaFila = fila + df[i];
            int nuevaCol = col + dc[i];

            if (buscarCamino(nuevaFila, nuevaCol)) {
                return true;
            }
        }

        // Si no funcionó, marcamos como imposible
        lab[fila][col] = IMPOSIBLE;

        return false;
    }

	public static char[][] getLab() {
		return lab;
	}
    
}
