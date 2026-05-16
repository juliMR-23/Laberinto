package Logica;

public class UtilidadesMatriz {
    
    // asegurar que las modificaciones no alteren el mapa original, copia profunda
    public static char[][] clonar(char[][] original) {
        if (original == null) return null;
        
        char[][] copia = new char[original.length][original[0].length];
        for (int i = 0; i < original.length; i++) {
            copia[i]=original[i].clone();
        }
        return copia;
    }
}