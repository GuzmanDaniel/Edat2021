/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package lineales.estaticas;

/**
 *
 * @author Daniel Guzman FAI-1430 / Fabian Sepulveda FAI-2714
 */

public class Cola {

    //Atributos:
    private static final int TAM = 10;
    private int fin;
    private int frente;
    private Object[] arreglo;

    //Constructor:
    public Cola() {
        this.arreglo = new Object[this.TAM];
        this.fin = 0;
        this.frente = 0;
    }

    public boolean poner(Object nuevoElem) {
        boolean exito = false;
        
        if ((this.fin + 1) % this.TAM != this.frente) {
            //Si la cola no est� llena entonces pone el nuevo elemento en la ubicacion de fin.
            //Y se corre de lugar el puntero fin.
            this.arreglo[this.fin] = nuevoElem;
            this.fin = (this.fin + 1) % this.TAM;
            exito = true;
        }
        return exito;
    }

    public boolean sacar() {
        boolean exito = true;
        
        if (this.esVacia()) {
            //La cola est� vac�a, reporta error.
            exito = false;
        } else {
            //Al menos hay 1 elemento: avanza frente (de manera circular).
            this.arreglo[this.frente] = null;
            this.frente = (this.frente + 1) % this.TAM;
        }
        
        return exito;
    }

    public Object obtenerFrente() {
        Object elem;
        if (this.esVacia()) {
            //Si la cola est� vac�a entonces el frente es nulo.
            elem = null;
        } else {
            //Si no, accedo al elemento que est� en el frente de la cola.
            elem = this.arreglo[this.frente];
        }
        return elem;
    }

    public boolean esVacia() {
        boolean exito = false;
        
        if (this.frente == this.fin) {
            //Si frente y fin es igual entonces la cola est� vac�a.
            exito = true;
        }
        return exito;
    }

    public void vaciar() {
        this.fin = this.frente;
    }

    public Cola clone() {
        Cola nuevaCola = new Cola();
        int aux = this.frente;
        
        while (aux != this.fin) {
            //Mientras sea distinto el frente y fin, a la nueva cola se le pone los elementos de la cola original.
            //Y aumenta la variable aux para moverse dentro del arreglo.
            nuevaCola.arreglo[aux] = this.arreglo[aux];
            aux = (aux + 1) % this.TAM;
        }
        //Los punteros fin y frente se los asigna al final de la repetitiva.
        nuevaCola.fin = this.fin;
        nuevaCola.frente = this.frente;
        
        return nuevaCola;
    }

    @Override
    public String toString() {
        String s;
        int aux = this.frente;
        
        if (this.fin == this.frente) {
            //Si la cola est� vac�a, imprime "Cola Vac�a".
            s = "Cola Vac�a";
        } else {
            //Si no, imprime "[".
            s = "[";
            
            while (aux != this.fin) {
                //Mientras sea distinto el frente y fin, se le agrega el elemento a la cadena "s".
                //Y aumenta la variable aux para moverse dentro del arreglo.
                s += this.arreglo[aux];
                aux = (aux + 1) % this.TAM;
                if (aux != this.fin) {
                    //Mientras sea distinto el frente y fin, tambi�n imprime una "," para separar elementos.
                    s += ",";
                }
            }
            //Por �ltimo imprime "]" para cerrar la cadena "s".
            s += "]";
        }
        
        return s;
    }
    
	public String toStringTransparente() {
		// muestra la estructura interna de la cola
		// en cola est�tica, muestra el arreglo
		String rep = "[ ";
		for(int i = 0; i < arreglo.length; i++) {
			if(arreglo[i] != null) {
				rep += arreglo[i].toString() + "  ";
			}
			else {
				rep += "_  ";
			}
		}
		rep += "] frente: " + frente + " fin: " + fin;
		return rep;
	}
}