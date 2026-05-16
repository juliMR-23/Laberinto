package Interfaz;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class Utilidades {
	public static final Color bg = new Color(42, 54, 74);
	public static final Color txt = new Color(240, 244, 248);
	public static final Color alt = new Color(160, 174, 192);
	public static final Color prim = new Color(56, 189, 248);
	
	public static JButton crearBoton(String texto, Color colorFondo) {
        JButton btn = new JButton(texto) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getModel().isEnabled()? (getModel().isPressed()
                        ? colorFondo.darker()
                        : getModel().isRollover() ? colorFondo.brighter() : colorFondo): colorFondo.darker().darker());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 18, 18);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btn.setFont(new Font("Arial", Font.BOLD, 14));
        btn.setForeground(Color.WHITE);
        btn.setBackground(colorFondo);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setOpaque(false);
        btn.setPreferredSize(new Dimension(200, 46));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }
	
	public static void mostrarMensaje(JFrame parent, String mensaje) {
		JDialog dialog = new JDialog(parent, "Laberinto", true);
		dialog.setSize(360, 180);
		dialog.setLocationRelativeTo(parent);
		dialog.setResizable(false);

		JPanel panel = new JPanel(new BorderLayout());
		panel.setBackground(Utilidades.bg);
		panel.setBorder(BorderFactory.createEmptyBorder(28, 30, 22, 30));

		JLabel lbl = new JLabel(mensaje, SwingConstants.CENTER);
		lbl.setFont(new Font("Arial", Font.PLAIN, 14));
		lbl.setForeground(Utilidades.txt);

		JButton btnOk = Utilidades.crearBoton("Aceptar", Utilidades.prim);
		btnOk.addActionListener(e -> dialog.dispose());

		JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.CENTER));
		panelBoton.setOpaque(false);
		panelBoton.add(btnOk);

		panel.add(lbl, BorderLayout.CENTER);
		panel.add(panelBoton, BorderLayout.SOUTH);

		dialog.setContentPane(panel);
		dialog.setVisible(true);
	}
}