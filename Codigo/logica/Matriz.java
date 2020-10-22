package logica;

import java.util.ArrayList;

public class Matriz {

	private Celda matriz[][];
	
	// Crea una matriz con 9 celdas todas con valor 0.
	public Matriz() {
		matriz = new Celda[3][3];
		for(int i= 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				matriz[i][j] = new Celda(0);
			}
		}
	}
	
	// Devuelve la celda(i,j) de la matriz. (i=fila, j=columna)
	public Celda getCelda(int i, int j) {
		return matriz[i][j];
	}
	
	// Comprueba que en la matriz no esten repetidos los números.
	// Devuelve true si los números no están repetidos, false en caso contrario.
	public boolean comprobar() {
		boolean estado = true;
		ArrayList<Celda> celdas = new ArrayList<Celda>();
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				if(celdas.indexOf(new Celda(matriz[i][j].getValor())) == -1) {
					celdas.add(matriz[i][j]);
				}
				else {
					// Si la celda está repetida, la marca para luego identificarla en la GUI.
					celdas.get(celdas.indexOf(new Celda(matriz[i][j].getValor()))).setRepetida(true); // Esto lo hago para marcar también la celda que se agregó a la lista.
					matriz[i][j].setRepetida(true);
					estado = false;
				}
			}
		}
		return estado;
	}
}
