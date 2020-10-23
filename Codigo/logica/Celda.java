package logica;

public class Celda {

	private int valor;
	private boolean protegida; // celda protegida: no se puede actualizar su valor mediante el m�todo actualizar()
	private boolean repetida; // true si la celda est� repetida dentro de la matriz, fila y/o columna.
	private EntidadGrafica grafico; // gr�fico de la celda para mostrar en la GUI
	
	// Crea una celda con un valor
	public Celda(int valor) {
		this.valor = valor; 
		protegida = false;
		repetida = false; // OBS: El valor 0 no se considera si est� repetido.
		grafico = new EntidadGrafica(0); // En principio, crea una celda con el gr�fico amarillo (tipo 0)
	}
	
	// Devuelve el gr�fico de la celda
	public EntidadGrafica getEntidadGrafica() { return grafico; }
	
	// Obtiene el valor de una celda
	public int getValor() { return valor; }
	
	// Establece el valor de una celda.
	// (En el caso de la GUI, se utiliza unicamente cuando el sudoku se inicia, por lo cual pone la imagen que no se podr� cambiar)
	public void setValor(int valor) {
		this.valor = valor;
		grafico.actualizar(valor); // Actualiza el gr�fico de la celda por el valor de la celda
	}
	
	// Actualiza el valor de la celda incrementandolo en 1. (Si el valor llega a 9 se establece en 1)
	// Devuelve true si pudo ser actualizado, false en caso contrario.
	public boolean actualizar() {
		boolean actualizo = false;
		if(!protegida) {
			if(valor == 9)
				valor = 1;
			else 
				valor++;
			grafico.actualizar(valor); // Actualiza la entidad gr�fica
			actualizo = true;
		}
		return actualizo;
	}
	
	// Bloquea a la celda.
	public void proteger() {
		protegida = true;
	}
	
	// Desbloquea a la celda.
	public void desproteger() {
		protegida = false;
	}
	
	// Devuelve verdadero si la celda est� protegida, falso en caso contrario.
	public boolean getProtegida() {
		return protegida;
	}
	
	// Setea si la celda est� repetida.
	public void setRepetida(boolean esta) {
		if(esta == true)
			grafico.setTipo(2); // Setea el tipo de gr�fico a 2, el cual es el azul con rojo (indica que est� incumpliendo una regla del juego)
		else
			grafico.setTipo(1); // Setea el tipo del gr�fico a 1, el cual es el azul solo.
		this.repetida = esta;
	}
	
	// Devuelve verdadero si est� repetida, falso en caso contrario.
	public boolean getRepetida() { 
		return repetida; 
	}
	
	public boolean equals(Object celda) {
		return valor == ((Celda)celda).getValor();
	}
	
	
}
