package logica;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Random;

public class Sudoku {

	private Matriz[][] matrices;
	
	/*
	 *  Cuando se presiona el botón 'Iniciar juego' se crea un nuevo tablero de Sudoku
	 *	OBS: El tablero en principio se encuentra vacío, después se tiene que utilizar el método iniciar(ruta del archivo de texto)
	 *		 para cargarlo con un Sudoku válido.
	 */
	public Sudoku() {
		matrices = new Matriz[3][3];
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				matrices[i][j] = new Matriz();
			}
		}
		// Cambiar permitiendo que puedas seleccionar el archivo con el estado inicial
		
	}
	
	// Obtiene la matriz en el índice (i,j) (i=fila, j=columna)
	public Matriz getMatriz(int i, int j) {
		return matrices[i][j];
	}
	
	// Inicia el Sudoku a partir de un archivo de texto.
	// Devuelve true si pudo ser inicializado, false en caso contrario.
	public boolean iniciar(String ruta) {
		return leerArchivo(ruta);
	}
	
	// Comprueba si el Sudoku es correcto. Devuelve true si lo es, falso en caso contrario.
	// OBS: se utiliza este método para comprobar que el archivo de texto sea correcto. Es decir, tenga un Sudoku correcto.
	public boolean comprobar() {
		boolean estado = true;
		
		/*
		 * OBS: Hago un recorrido exhaustivo y no corto los ciclos for cuando estado=false tal que necesito marcar todas las celdas que estén repetidas (celda.setRepetida(true)) para mostrarlas graficamente que lo están.
		 */
		
		// Comprueba si en el Sudoku las columnas están repetidas
		for(int i =0; i < 3; i++){
			for(int fila = 0; fila < 3; fila++) {
				ArrayList<Celda> celdas = new ArrayList<Celda>();
				for(int j = 0; j < 3; j++){
					for(int colum = 0; colum < 3; colum++){
						if(celdas.indexOf(matrices[i][j].getCelda(fila, colum)) == -1) {
							celdas.add(matrices[i][j].getCelda(fila, colum));
						}
						else {
							// Si la celda está repetida, la marca para luego identificarla en la GUI.
							celdas.get(celdas.indexOf(matrices[i][j].getCelda(fila, colum))).setRepetida(true); // Esto lo hago para marcar también la celda que se agregó a la lista.
							matrices[i][j].getCelda(fila, colum).setRepetida(true);
							estado = false;
						}
					}
				}
			}
		}
		
		// Comprueba si en el Sudoku las filas están repetidas
		for(int j = 0; j < 3; j++) {
			for(int colum = 0; colum < 3; colum++) {
				ArrayList<Celda> celdas = new ArrayList<Celda>();
				for(int i = 0; i < 3; i++) {
					for(int fila = 0; fila < 3; fila++) {
						if(celdas.indexOf(matrices[i][j].getCelda(fila, colum)) == -1) {
							celdas.add(matrices[i][j].getCelda(fila, colum));
						}
						else {
							// Si la celda está repetida, la marca para luego identificarla en la GUI.
							celdas.get(celdas.indexOf(matrices[i][j].getCelda(fila, colum))).setRepetida(true); // Esto lo hago para marcar también la celda que se agregó a la lista.
							matrices[i][j].getCelda(fila, colum).setRepetida(true);
							estado = false;
						}
					}
				}
			}
		}
		
		// Comprueba si en la matriz interna están repetidos los elementos.
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				if(matrices[i][j].comprobar() == false) {
					estado = false;
				}
			}
		}
				
		return estado;
	}
	
	// Métodos privados internos
	private boolean leerArchivo(String ruta) {
		boolean leyo = true;
		File archivo = null;
	    FileReader fr = null;
	    BufferedReader br = null;
	    int i = 0;
	    int j = 0;
	    int columna = 0;
	    int fila = 0;
        String linea;
        char carac;
        int carac_int;
	    try {
	         archivo = new File (ruta);
	         fr = new FileReader (archivo);
	         br = new BufferedReader(fr);
	         while((linea=br.readLine())!=null && leyo == true) {
	        	 for(int indice = 0; indice < linea.length() && leyo == true; indice++) {
	        		 carac = linea.charAt(indice);
	        		 if(carac != ' ') {
	        			 if(isNumeric(String.valueOf(carac)) == false) // Comprueba si lo que leyó es un número. Si no lo es, el Sudoku es inválido.
	        				 leyo = false;
	        			 else {
		        			 carac_int = Integer.parseInt(String.valueOf(carac));  
		        			 if(carac_int > 9 || carac_int < 1) // Comprueba si el número que leyó está en [1,9]. Si no lo está, el Sudoku es inválido.
		        				 leyo = false;
		        			 else {
			        			 matrices[i][j].getCelda(fila, columna).setValor(carac_int);
			        			 matrices[i][j].getCelda(fila, columna).proteger(); // En principio protege todas las celdas hasta desproteger con las que se interactua en el Sudoku
			        			 columna++;
			        			 if(columna == 3) {
			        				 j++;
			        				 columna=0;
			        			 }
		        			 }
	        			 }
	        		}
	        	 }
	        	 fila++;
	        	 j = 0;
		         if(fila == 3) {
		        	i++;
		        	fila = 0;
		         }
		     }
	         if(this.comprobar() == true) {
	        	 eliminarNumeros(); 		// si el archivo de texto tenía un sudoku válido, se eliminan algunos números del sudoku para empezar a jugar
	         }
	         else {							// Como el archivo de texto no es válido, setea todas las celdas en 0.
		         borrarValores();
		         leyo = false;
	         }
	         fr.close();
	    } catch(Exception e){ leyo = false; }
	    return leyo;
	}
		
	private void borrarValores() {
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				for(int fila = 0; fila < 3; fila++) {
					for(int colum = 0; colum < 3; colum++) {
						matrices[i][j].getCelda(fila, colum).setValor(0);
					}
				}
			}
		}
	}
	
	private void eliminarNumeros() {
		// Elimina de 1 a 6 numeros en una matriz interna.
		Random rnd = new Random(7);
		Random rnd2 = new Random(5);
		int aEliminar = 0;
		int elimina = 0;
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				aEliminar = rnd.nextInt();
				for(int fila = 0; fila < 3; fila++) {
					for(int colum = 0; colum < 3; colum++) {
						elimina = rnd2.nextInt();
						if(elimina < 2  && aEliminar != 0) {
							matrices[i][j].getCelda(fila, colum).setValor(0);
							matrices[i][j].getCelda(fila, colum).getEntidadGrafica().setTipo(1); // Al eliminar números significa que se puede interactuar con ellos, por ende, actualizarlos. Entonces el tipo es 1 (para mostrarlos en azul y diferencias de los preterminados que no se pueden actualizar)
							matrices[i][j].getCelda(fila,colum).desproteger();
							aEliminar--;
						}
					}
				}
			}
		}
	}
	
	// Devuelve true si la cadena ingresada es un número, false en caso contrario.
	private boolean isNumeric(String s) {  
		return s != null && s.matches("[-+]?\\d*\\.?\\d+");  
	} 
	
	
	// Fin métodos privados
}
