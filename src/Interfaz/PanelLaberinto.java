package Interfaz;

import javax.swing.*;
import javax.imageio.ImageIO;
import java.awt.*;

public class PanelLaberinto extends JPanel {
    private char[][] solucion;

    public PanelLaberinto() {
    	setPreferredSize(new Dimension(600, 600));
        setBackground(Utilidades.bg.darker());
    }

    public void setSolucion(char[][] solucion) {
        this.solucion = solucion;
        repaint();
    }
    private void drawTexto(Graphics g, String txt, Color c, int posX, int posY, int casilla, float relacion, int ajusteY) {
    	g.setColor(c);
    	g.setFont(new Font("Serif", Font.PLAIN, (int)(casilla*relacion)));
        FontMetrics fm = g.getFontMetrics();
        int x = posX + (casilla - fm.stringWidth(txt)) / 2;
        int y = posY + (casilla-fm.getHeight())/2 + fm.getAscent() + ajusteY;
        g.drawString(txt, x, y);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (solucion == null || solucion.length == 0) return;

        int fila = solucion.length;
        int col = solucion[0].length;

        int tamanio = Math.min(getWidth(), getHeight());
        int casilla = tamanio / Math.max(fila, col);

        int offsetX = (getWidth()-(col*casilla)) / 2;
        int offsetY = (getHeight()-(fila*casilla)) / 2;
        
        for (int i = 0; i < fila; i++) {
            for (int j = 0; j < col; j++) {
                char celda = solucion[i][j];
                
                // Elegir color según el tipo de celda
                switch (celda) {
                    case '#': g.setColor(Utilidades.bg.darker().darker()); break;
                    default:  g.setColor(Utilidades.bg.brighter());
                }
                int posX = offsetX + (j * casilla);
                int posY = offsetY + (i * casilla);
                
                g.fillRect(posX, posY, casilla, casilla);
                
                if(celda=='.') {
                	drawTexto(g, "■", Utilidades.prim, posX, posY, casilla,1.2f, -2);//camino
                	
                	if(i==1&&j==1)//inicio del lab
                		drawTexto(g, "⚐", Utilidades.bg.brighter(), posX, posY, casilla, 0.8f,0);
                	else if(i==fila-2 && j==col-2)//meta
                		drawTexto(g, "⚑", Utilidades.bg.brighter(), posX,posY, casilla, 0.8f,0);
                }
                	
                // Dibujar borde para que se vea la cuadrícula
                g.setColor(Utilidades.bg);
                g.drawRect(posX, posY, casilla, casilla);
            }
        }
    }
}
