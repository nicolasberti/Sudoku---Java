package logica;

import javax.swing.ImageIcon;

import aplicacion.GUI;

public class EntidadGrafica {

	private ImageIcon grafico;
	private String[] imagenes;
	private int indice; // hace referencia al índice del gráfico en el string de imagenes.
	
	/*
	 * Tipos: 
	 * 0: amarilla 		(Cuando la celda está bloqueada y se inicia el juego con esos números)
	 * 1: azul	   		(Cuando se interactúa con la celda)
	 * 2: azul con rojo (Cuando la celda está incumpliendo las reglas del juego)
	 * 3: cronometro    (Entidad gráfica de los números del cronometro)
	 */
	public EntidadGrafica(int tipo) {
		this.grafico = new ImageIcon();
		this.indice = 0;
		if(tipo == 0)
			this.imagenes = new String[] { "/img/0_c.png","/img/1_c.png","/img/2_c.png","/img/3_c.png","/img/4_c.png","/img/5_c.png","/img/6_c.png","/img/7_c.png","/img/8_c.png","/img/9_c.png"};
		else if(tipo == 1)
			this.imagenes = new String[] { "/img/0_escrito.png","/img/1_escrito.png","/img/2_escrito.png","/img/3_escrito.png","/img/4_escrito.png","/img/5_escrito.png","/img/6_escrito.png","/img/7_escrito.png","/img/8_escrito.png","/img/9_escrito.png"};
		else if(tipo == 2)
			this.imagenes = new String[] { "/img/0_incorrecto.png","/img/1_incorrecto.png","/img/2_incorrecto.png","/img/3_incorrecto.png","/img/4_incorrecto.png","/img/5_incorrecto.png","/img/6_incorrecto.png","/img/7_incorrecto.png","/img/8_incorrecto.png","/img/9_incorrecto.png"};
		else if(tipo == 3)
			this.imagenes = new String[] { "/img/0.png","/img/1.png","/img/2.png","/img/3.png","/img/4.png","/img/5.png","/img/6.png","/img/7.png","/img/8.png","/img/9.png"};
		else
			imagenes = null;
		grafico.setImage(new ImageIcon(GUI.class.getResource(imagenes[0])).getImage());
	}
	
	// Devuelve el gráfico
	public ImageIcon getEntidadGrafica() {
		return grafico; 
	}
	
	// Setea el tipo del gráfico. Descriptos arriba del constructor.
	public void setTipo(int tipo){
		if(tipo == 0)
			this.imagenes = new String[] { "/img/0_c.png","/img/1_c.png","/img/2_c.png","/img/3_c.png","/img/4_c.png","/img/5_c.png","/img/6_c.png","/img/7_c.png","/img/8_c.png","/img/9_c.png"};
		else if(tipo == 1)
			this.imagenes = new String[] { "/img/0_escrito.png","/img/1_escrito.png","/img/2_escrito.png","/img/3_escrito.png","/img/4_escrito.png","/img/5_escrito.png","/img/6_escrito.png","/img/7_escrito.png","/img/8_escrito.png","/img/9_escrito.png"};
		else if(tipo == 2)
			this.imagenes = new String[] { "/img/0_incorrecto.png","/img/1_incorrecto.png","/img/2_incorrecto.png","/img/3_incorrecto.png","/img/4_incorrecto.png","/img/5_incorrecto.png","/img/6_incorrecto.png","/img/7_incorrecto.png","/img/8_incorrecto.png","/img/9_incorrecto.png"};
		else if(tipo == 3)
			this.imagenes = new String[] { "/img/0.png","/img/1.png","/img/2.png","/img/3.png","/img/4.png","/img/5.png","/img/6.png","/img/7.png","/img/8.png","/img/9.png"};
		else
			imagenes = null;
		grafico.setImage(new ImageIcon(GUI.class.getResource(imagenes[indice])).getImage());
	}
	
	// Actualiza el índice de la EntidadGrafica.
	public void actualizar(int indice) {
		if(indice < imagenes.length) {
			grafico.setImage(new ImageIcon(GUI.class.getResource(imagenes[indice])).getImage());
			this.indice = indice;
		}
	}
}
