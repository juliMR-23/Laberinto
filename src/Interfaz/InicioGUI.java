package Interfaz;

import javax.swing.*;

import Exceptions.InvalidNumException;
import Logica.Laberinto_primerSolucion;
import java.awt.*;

public class InicioGUI extends JFrame {

	private static int verificaDimension(String input, String tipo) throws InvalidNumException {
		if (!input.trim().matches("\\d+")) // Solo números positivos
			throw new InvalidNumException("Ingrese un número entero positivo");

		int dim = Integer.parseInt(input.trim());
		if (dim <= 3)
			throw new InvalidNumException("Cantidad de " + tipo + " debe ser mayor a 3");
		if (dim > 30)
			throw new InvalidNumException("Cantidad de " + tipo + " debe ser menor a 30");
		return dim;
	}

	private void abrirEditorLaberinto(boolean buscarTodaSolucion) {
		String input = mostrarInputDialog(this, "Ingrese el número de filas:");// pide las dimensiones primero
		if (input == null)
			return;

		try {
			int alto = verificaDimension(input, "filas");

			input = mostrarInputDialog(this, "Ingrese el número de columnas:");
			if (input == null)
				return;
			int ancho = verificaDimension(input, "columnas");

			if (alto > ancho * 2 || ancho > alto * 2)
			    throw new InvalidNumException("La proporción máxima es 1:2");

			VentanaEdicion ventana = new VentanaEdicion(alto, ancho, this, buscarTodaSolucion);
			ventana.setVisible(true);
			setVisible(false);

		} catch (InvalidNumException ex) {
			Utilidades.mostrarMensaje(this, ex.getMessage());
		}
	}

	public InicioGUI() {
		setTitle("Backtracking Laberinto");
		setSize(500, 380);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);

		JPanel panelPrincipal = new JPanel(new BorderLayout());
		panelPrincipal.setBackground(Utilidades.bg);
		panelPrincipal.setBorder(BorderFactory.createEmptyBorder(35, 50, 30, 50));

		JLabel titulo = new JLabel("⚐ Laberinto ⚐", SwingConstants.CENTER);
		titulo.setFont(new Font("Serif", Font.BOLD, 42));
		titulo.setForeground(Utilidades.txt);

		JLabel subtitulo = new JLabel("Backtracking", SwingConstants.CENTER);
		subtitulo.setFont(new Font("Arial", Font.PLAIN, 20));
		subtitulo.setForeground(Utilidades.prim);

		JPanel panelTitulos = new JPanel(new GridLayout(2, 1, 0, 8));
		panelTitulos.setOpaque(false);
		panelTitulos.add(titulo);
		panelTitulos.add(subtitulo);

		// Botón: Ver Una Solución
		JButton btnUnaSolucion = Utilidades.crearBoton("Ver Una Solución", Utilidades.prim);
		btnUnaSolucion.addActionListener(e -> abrirEditorLaberinto(false));

		// Botón: Ver Todas las Soluciones
		JButton btnTodasSoluciones = Utilidades.crearBoton("Ver Todas las Soluciones", Utilidades.prim.darker());
		btnTodasSoluciones.addActionListener(e -> abrirEditorLaberinto(true));

		// Panel de botones (apilados vert)
		JPanel panelBotones = new JPanel(new GridLayout(2, 1, 0, 12));
		panelBotones.setOpaque(false);

		panelBotones.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
		panelBotones.add(btnUnaSolucion);
		panelBotones.add(btnTodasSoluciones);

		panelPrincipal.add(panelTitulos, BorderLayout.CENTER);
		panelPrincipal.add(panelBotones, BorderLayout.SOUTH);
		setContentPane(panelPrincipal);
	}

	private static String mostrarInputDialog(JFrame parent, String mensaje) {
		JDialog dialog = new JDialog(parent, "Laberinto", true);
		dialog.setSize(420, 230);
		dialog.setLocationRelativeTo(parent);
		dialog.setResizable(false);

		JPanel panel = new JPanel(new BorderLayout(0, 0));
		panel.setBackground(Utilidades.bg);
		panel.setBorder(BorderFactory.createEmptyBorder(28, 35, 22, 35));

		JLabel lbl = new JLabel(mensaje, SwingConstants.CENTER);
		lbl.setFont(new Font("Arial", Font.PLAIN, 15));
		lbl.setForeground(Utilidades.txt);

		JTextField campo = new JTextField();
		campo.setFont(new Font("Arial", Font.BOLD, 22));
		campo.setBackground(Utilidades.bg.brighter());
		campo.setForeground(Color.WHITE);
		campo.setCaretColor(Color.WHITE);
		campo.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Utilidades.prim, 2),
				BorderFactory.createEmptyBorder(8, 12, 8, 12)));
		campo.setHorizontalAlignment(JTextField.CENTER);
		campo.setPreferredSize(new Dimension(0, 50));

		JPanel panelCampo = new JPanel(new BorderLayout());
		panelCampo.setOpaque(false);
		panelCampo.setBorder(BorderFactory.createEmptyBorder(14, 0, 0, 0));
		panelCampo.add(campo, BorderLayout.CENTER);

		JPanel panelBotones = new JPanel(new GridLayout(1, 2, 12, 0));
		panelBotones.setOpaque(false);
		panelBotones.setBorder(BorderFactory.createEmptyBorder(16, 0, 0, 0));

		String[] resultado = { null };

		JButton btnOk = Utilidades.crearBoton("Aceptar", Utilidades.prim);
		JButton btnCancelar = Utilidades.crearBoton("Cancelar", Utilidades.bg.brighter().brighter());

		btnOk.addActionListener(e -> {
			resultado[0] = campo.getText();
			dialog.dispose();
		});
		btnCancelar.addActionListener(e -> dialog.dispose());
		campo.addActionListener(e -> {
			resultado[0] = campo.getText();
			dialog.dispose();
		});

		panelBotones.add(btnOk);
		panelBotones.add(btnCancelar);

		panel.add(lbl, BorderLayout.NORTH);
		panel.add(panelCampo, BorderLayout.CENTER);
		panel.add(panelBotones, BorderLayout.SOUTH);

		dialog.setContentPane(panel);
		dialog.setVisible(true);
		return resultado[0];
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new InicioGUI().setVisible(true));
	}
}