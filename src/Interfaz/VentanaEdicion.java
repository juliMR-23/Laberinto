package Interfaz;

import Logica.Laberinto_primerSolucion;
import Logica.UtilidadesMatriz;
import Logica.GeneradorLaberintos;
import Logica.Laberinto;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class VentanaEdicion extends JFrame {

	private char[][] mapaEdicion;
	private PanelLaberinto panelLaberinto;
	private JFrame ventanaInicio;
	private int alto, ancho;
	private char herramientaActual = '#'; // por defecto pinta paredes
	private boolean buscarTodaSolucion;

	public VentanaEdicion(int alto, int ancho, JFrame ventanaInicio, boolean buscarTodaSolucion) {
		this.alto = alto;
		this.ancho = ancho;
		this.ventanaInicio = ventanaInicio;
		this.buscarTodaSolucion = buscarTodaSolucion;

		// 1. Generamos el lienzo vacío con el nuevo método
		this.mapaEdicion = GeneradorLaberintos.obtenerPorDimension(alto, ancho);

		setTitle("Diseñador de Laberintos");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);

		JPanel panelPrincipal = new JPanel(new BorderLayout());
		panelPrincipal.setBackground(Utilidades.bg);

		// panel superior (herramientas, pa elegir si pintar o borrar)
		JPanel panelHerramientas = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
		panelHerramientas.setBackground(Utilidades.bg.darker());

		JButton btnPared = Utilidades.crearBoton("Pinta muros", Utilidades.prim);
		JButton btnBorrador = Utilidades.crearBoton("Borrador", Utilidades.bg.brighter());

		btnPared.setPreferredSize(new Dimension(140, 36));
		btnBorrador.setPreferredSize(new Dimension(140, 36));

		btnPared.addActionListener(e -> herramientaActual = '#');
		btnBorrador.addActionListener(e -> herramientaActual = ' ');

		panelHerramientas.add(btnPared);
		panelHerramientas.add(btnBorrador);

		// tablero donde dibuja
		panelLaberinto = new PanelLaberinto();
		panelLaberinto.setSolucion(mapaEdicion);

		// escuchar al mouse pa pintar con clic o arrastrando
		MouseAdapter mousePintor = new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				editarCasilla(e.getPoint());
			}

			@Override
			public void mouseDragged(MouseEvent e) {
				editarCasilla(e.getPoint());
			}
		};
		panelLaberinto.addMouseListener(mousePintor);
		panelLaberinto.addMouseMotionListener(mousePintor);

		// panel de acciones abajo
		JPanel panelAcciones = new JPanel(new GridLayout(1, 2, 10, 0));
		panelAcciones.setBackground(Utilidades.bg.darker());
		panelAcciones.setBorder(BorderFactory.createEmptyBorder(10, 15, 12, 15));

		JButton btnRegresar = Utilidades.crearBoton("⌂ Volver a inicio", Utilidades.bg.brighter().brighter());
		JButton btnResolver = Utilidades.crearBoton("Resolver", Utilidades.prim);

		btnRegresar.addActionListener(e -> {
			ventanaInicio.setVisible(true);
			dispose();
		});
		btnResolver.addActionListener(e -> ejecutarResolucion());

		panelAcciones.add(btnRegresar);
		panelAcciones.add(btnResolver);

		// juntar todo
		panelPrincipal.add(panelHerramientas, BorderLayout.NORTH);
		panelPrincipal.add(panelLaberinto, BorderLayout.CENTER);
		panelPrincipal.add(panelAcciones, BorderLayout.SOUTH);

		setContentPane(panelPrincipal);
		pack();
		setLocationRelativeTo(null);
	}

	// convierte en coordenadas de la matriz
	private void editarCasilla(Point p) {
		int filas = mapaEdicion.length;
		int columnas = mapaEdicion[0].length;

		int tamañoDisponible = Math.min(panelLaberinto.getWidth(), panelLaberinto.getHeight());
		int casilla = tamañoDisponible / Math.max(filas, columnas);

		int offsetX = (panelLaberinto.getWidth() - (columnas * casilla)) / 2;
		int offsetY = (panelLaberinto.getHeight() - (filas * casilla)) / 2;

		// coordenada X,Y del mouse a índices de la matriz
		int j = (p.x - offsetX) / casilla;
		int i = (p.y - offsetY) / casilla;

		// validar que no modifica los bordes ni fuera de rango
		if (i > 0 && i < filas - 1 && j > 0 && j < columnas - 1) {

			// no permite tapar inicio ni meta
			if ((i == 1 && j == 1) || (i == filas - 2 && j == columnas - 2))
				return;

			mapaEdicion[i][j] = herramientaActual;
			panelLaberinto.repaint(); // actualizar
		}
	}

	// manda el laberinto dibujado a las clases lógicas
	private void ejecutarResolucion() {
		char[][][] soluciones=null;
		char[][] mapaCopia = UtilidadesMatriz.clonar(mapaEdicion);//apuntador distinto pa no dañar lo creado
		if (buscarTodaSolucion) {
			Laberinto.resolver(mapaCopia);
			soluciones = Laberinto.getSoluciones();
		} else if (Laberinto_primerSolucion.resolver(mapaCopia)) {
			soluciones = new char[][][]{ Laberinto_primerSolucion.getLab() };
		}
		
		if (soluciones != null && soluciones.length > 0) {
	        new VentanaSoluciones(soluciones, ventanaInicio).setVisible(true);
	        dispose();
	    }else
	    	Utilidades.mostrarMensaje(this, "El laberinto creado no tiene solución");
	}
}