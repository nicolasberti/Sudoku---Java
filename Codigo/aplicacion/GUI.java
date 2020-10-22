package aplicacion;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import logica.Celda;
import logica.Sudoku;
import logica.EntidadGrafica;

/*
 * OBS GENERAL: En todos los ciclos for cuando interactúo con el Sudoku,  i,j hacen referencia a las fila y columna respectivamente del Sudoku.
 * 				mientras que fila,colum hacen referencia a las fila y columna respectivamente de las matrices internas del Sudoku.
 * 
 */

public class GUI extends JFrame {

	private JPanel contentPane;
	private Cronometro cronometro;
	public boolean activo = false;
	
	// Label del cronometro
	private JLabel hora_izquierda;
	private JLabel hora_derecha;
	private JLabel minutos_izquierda;
	private JLabel minutos_derecha;
	private JLabel segundos_izquierda;
	private JLabel segundos_derecha;
	private Sudoku juego;
	
	private ArrayList<JLabel> labelCeldas; // Lista que contiene todas las labels de las celdas

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI frame = new GUI();
					frame.setVisible(true);
					frame.setResizable(false);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GUI() {
		labelCeldas = new ArrayList<JLabel>();
		setTitle("Sudoku - Nicol\u00E1s Berti");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 751, 733);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.setBackground(Color.WHITE);
		
		JPanel panel_juego = new JPanel();
		
		panel_juego.setBounds(5, 5, 720, 598);
		contentPane.add(panel_juego);
		panel_juego.setLayout(new GridLayout(3, 3, 0, 0));
		panel_juego.setBackground(Color.WHITE);
		
		JPanel panel_control = new JPanel();
		panel_control.setBounds(5, 614, 720, 80);
		contentPane.add(panel_control);
		panel_control.setBackground(Color.WHITE);
		
		JLabel labelTime = new JLabel();
		labelTime.setBounds(10, 38, 70, 20);
		Image time = new ImageIcon(GUI.class.getResource("/img/time.png")).getImage();
		ImageIcon time2 =new ImageIcon(time.getScaledInstance(70, 20, Image.SCALE_SMOOTH));
		panel_control.setLayout(null);
		labelTime.setIcon(time2);
		panel_control.add(labelTime);
		
		JLabel dosPuntos = new JLabel("");
		dosPuntos.setBounds(133, 44, 12, 14);
		dosPuntos.setIcon(new ImageIcon(GUI.class.getResource("/img/dosPuntos.png")));
		panel_control.add(dosPuntos);
		
		JLabel dosPuntos_1 = new JLabel("");
		dosPuntos_1.setBounds(184, 44, 12, 14);
		dosPuntos_1.setIcon(new ImageIcon(GUI.class.getResource("/img/dosPuntos.png")));
		panel_control.add(dosPuntos_1);
		
		JButton boton_validar = new JButton("Validar juego");
		boton_validar.setVisible(false);
		boton_validar.setBounds(532, 1, 162, 25);
		boton_validar.setFont(new Font("Tahoma", Font.BOLD, 14));
		boton_validar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(activo) {
					// Si el juego es correcto, bloquea todas las celdas para que no se puedan cambiar.
					if(juego.comprobar() == true) {
						cronometro.desactivar();
						JLabel mensaje = new JLabel();
						mensaje.setIcon(new ImageIcon(GUI.class.getResource("/img/bailando.gif")));
						JOptionPane.showMessageDialog(null, mensaje);
						boton_validar.setVisible(false);
						Iterator<JLabel> it = labelCeldas.iterator();
						// Bloquea todas las celdas
						for(int i = 0; i < 3; i++) {
							for(int j = 0; j < 3; j++) {
								for(int fila = 0; fila < 3; fila++) {
									for(int colum = 0; colum < 3; colum++) {
										//OBS: No uso el it.hasNext() tal que las labels son las mismas que las iteraciones de los for anidados.
										JLabel label = it.next();
										Celda celda = juego.getMatriz(i, j).getCelda(fila, colum);
										celda.proteger();
										// En el caso que haya algunas que salgan en rojo, se ponen en azul.
										if(celda.getRepetida()) {
											celda.getEntidadGrafica().setTipo(1);
										}
										// Se actualizan las imagenes
										actualizarIcono(celda, label);
									}
								}
							}
						}
					} else {
					// Juego incorrecto:
						
						/*
						 * Se muestran graficamente las celdas que están incumpliendo las reglas del juego.
						 * OBS: No uso el it.hasNext() tal que las labels son las mismas que las iteraciones de los for anidados.
						 */
						Iterator<JLabel> it = labelCeldas.iterator();
						for(int i = 0; i < 3; i++) {
							for(int j = 0; j < 3; j++) {
								for(int fila = 0; fila < 3; fila++) {
									for(int colum = 0; colum < 3; colum++) {
										JLabel label = it.next();
										Celda celda = juego.getMatriz(i, j).getCelda(fila, colum);
										// No solo actualiza las que están repetidas, si no las que no lo están también por si quedaron en rojo.
										if(celda.getProtegida() == false) {
											actualizarIcono(celda, label);
										}
										// Tal que el jugador volverá a interactuar con el Sudoku, considero que todas las celdas no están repetidas hasta que las vuelva a comprobar.
										// OBS: Se seguirán marcando en rojo las que anteriormente estaban repetidas hasta que las actualice o compruebe nuevamente el Sudoku.
										celda.setRepetida(false);
									}
								}
							}
						}
						JOptionPane.showMessageDialog(null, "El juego no está bien. En rojo se muestran las celdas que no están correctas.", "INCOMPLETO", 0);
					}
				}
			}
		});
		panel_control.add(boton_validar);
		
		JButton boton_iniciar = new JButton("Iniciar juego");
		boton_iniciar.setBounds(532, 33, 162, 25);
		boton_iniciar.setFont(new Font("Tahoma", Font.BOLD, 14));
		boton_iniciar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!activo) {
					JFileChooser fileChooser = new JFileChooser();
					FileFilter filter = new FileNameExtensionFilter("TXT file", new String[] {"txt"});
			        boolean rutaValida = false;
			        boolean botonCancel = false;
			        JOptionPane.showMessageDialog(null, "A continuación deberas ingresar un archivo de texto que contenga el estado inicial del Sudoku");
			        while(!rutaValida && !botonCancel) {
						fileChooser.setFileFilter(filter);
						fileChooser.addChoosableFileFilter(filter);
				        int result = fileChooser.showSaveDialog(fileChooser);
			        	if (result == JFileChooser.CANCEL_OPTION) {
			        	    botonCancel = true;
			        	} else {
			        		juego = new Sudoku(); // Se inicia el juego.
			        		rutaValida = juego.iniciar(fileChooser.getSelectedFile().getAbsolutePath());
			        		if(!rutaValida)
			        			JOptionPane.showMessageDialog(null, "El archivo no es válido. Por favor, intentalo nuevamente.", "ERROR", 0);
			        	}
			        }
			        if(rutaValida) {
			        	// El cronometro se pone visible
			        	hora_derecha.setVisible(true);
			    		hora_izquierda.setVisible(true);
			    		minutos_derecha.setVisible(true);
			    		minutos_izquierda.setVisible(true);
			    		segundos_derecha.setVisible(true);
			    		segundos_izquierda.setVisible(true);
			    		labelTime.setVisible(true);
			    		dosPuntos.setVisible(true);
			    		dosPuntos_1.setVisible(true);
			    		
						cronometro = new Cronometro();
						cronometro.activar();
						activo = true;
						boton_validar.setVisible(true);
						boton_iniciar.setVisible(false);
						
						// Creación de paneles
						for(int i = 0; i < 3; i++) {
							for(int j = 0; j < 3; j++) {
								JPanel panelAgregado = new JPanel();
								panelAgregado.setBorder(new LineBorder(Color.BLACK, 2));
								panelAgregado.setLayout(new GridLayout(3, 3, 0, 0));
								panel_juego.add(panelAgregado);
								// Creación de celdas  (JLabel)
								for(int fila = 0; fila < 3; fila++) {
									for(int colum = 0; colum < 3; colum++) {
										JLabel numero = new JLabel();
										Celda celda = juego.getMatriz(i, j).getCelda(fila, colum);
										numero.setBounds(numero.getX(), numero.getY(), 9, 7);
										actualizarIcono(celda, numero);
										numero.addMouseListener(new MouseAdapter() {
											@Override
											public void mouseClicked(MouseEvent e) {
												if(celda.actualizar()) {
													actualizarIcono(celda, numero);
												}
											}
										});
										panelAgregado.setBackground(Color.WHITE);
										panelAgregado.add(numero);
										labelCeldas.add(numero);
									}
								}
								// Fin celdas
							}
						}
						// Fin paneles 
					}
				}
			}
		});
		panel_control.add(boton_iniciar);
		
		segundos_derecha = new JLabel("");
		segundos_derecha.setBounds(218, 44, 12, 14);
		segundos_derecha.setIcon(new ImageIcon(new EntidadGrafica(3).getEntidadGrafica().getImage()));
		panel_control.add(segundos_derecha);
		
		segundos_izquierda = new JLabel("");
		segundos_izquierda.setBounds(201, 45, 7, 13);
		segundos_izquierda.setIcon(new ImageIcon(new EntidadGrafica(3).getEntidadGrafica().getImage()));
		panel_control.add(segundos_izquierda);
		
		minutos_derecha = new JLabel("");
		minutos_derecha.setBounds(167, 45, 7, 13);
		minutos_derecha.setIcon(new ImageIcon(new EntidadGrafica(3).getEntidadGrafica().getImage()));
		panel_control.add(minutos_derecha);
		
		minutos_izquierda = new JLabel("");
		minutos_izquierda.setBounds(150, 45, 7, 13);
		minutos_izquierda.setIcon(new ImageIcon(new EntidadGrafica(3).getEntidadGrafica().getImage()));
		panel_control.add(minutos_izquierda);
		
		hora_derecha = new JLabel("");
		hora_derecha.setBounds(120, 45, 7, 13);
		hora_derecha.setIcon(new ImageIcon(new EntidadGrafica(3).getEntidadGrafica().getImage()));
		panel_control.add(hora_derecha);
		
		hora_izquierda = new JLabel("");
		hora_izquierda.setBounds(107, 45, 7, 13);
		hora_izquierda.setIcon(new ImageIcon(new EntidadGrafica(3).getEntidadGrafica().getImage()));
		panel_control.add(hora_izquierda);
		
		// Al inicio, el cronometro no es visible. Cuando empieza el juego sí
		hora_derecha.setVisible(false);
		hora_izquierda.setVisible(false);
		minutos_derecha.setVisible(false);
		minutos_izquierda.setVisible(false);
		segundos_derecha.setVisible(false);
		segundos_izquierda.setVisible(false);
		labelTime.setVisible(false);
		dosPuntos.setVisible(false);
		dosPuntos_1.setVisible(false);
		
	
	}

	// Actualiza el icono de una celda. Se pasa como parametro dicha celda y la label asociada a la celda.
	private void actualizarIcono(Celda celda, JLabel label) {
		Image img1 = celda.getEntidadGrafica().getEntidadGrafica().getImage();
		ImageIcon img2 =new ImageIcon(img1.getScaledInstance(32, 32, Image.SCALE_SMOOTH));
		label.setIcon(img2);
	}
	
	// Clase cronometro
	private class Cronometro extends Thread {

		private EntidadGrafica[] grafico; // Respectivamente, grafico[0]=hora izquierda, grafico[1]=hora derecha, etc..
		private boolean activo = false;
		private int hora;
		private int minuto;
		private int segundo;
		
		public Cronometro() {
			super();
			hora = minuto = segundo = 0;
			grafico = new EntidadGrafica[6];
			for(int i = 0; i < 6; i++)
				grafico[i] = new EntidadGrafica(3); // creo la entidad grafica de tipo 3 (imagenes del cronometro)
		}
		
		public void activar() {
			activo = true; 
			start();
		}
		public void desactivar() {
			activo = false;
			stop();
		}
		
		public void run() {
			try {
				while(activo) {
					Thread.sleep(1000);
					segundo++;
					if(segundo == 60) {
						minuto++;
						segundo = 0;
						if(minuto == 60) {
							minuto=0;
							hora++;
							if(hora==24) {
								hora=0;
							}
						}
					}
					// Actualiza las entidades gráficas
					grafico[0].actualizar(hora/10);
					grafico[1].actualizar(hora%10);
					grafico[2].actualizar(minuto/10);
					grafico[3].actualizar(minuto%10);
					grafico[4].actualizar(segundo/10);
					grafico[5].actualizar(segundo%10);
					hora_izquierda.setIcon(new ImageIcon(grafico[0].getEntidadGrafica().getImage()));
					hora_derecha.setIcon(new ImageIcon(grafico[1].getEntidadGrafica().getImage()));
					minutos_izquierda.setIcon(new ImageIcon(grafico[2].getEntidadGrafica().getImage()));
					minutos_derecha.setIcon(new ImageIcon(grafico[3].getEntidadGrafica().getImage()));
					segundos_izquierda.setIcon(new ImageIcon(grafico[4].getEntidadGrafica().getImage()));
					segundos_derecha.setIcon(new ImageIcon(grafico[5].getEntidadGrafica().getImage()));
				}
			} catch(Exception e) { System.out.println("error del cronometro"); }
		}
	}
	
}
