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

public class Pila {

    //Atributos:
    private Object[] arreglo;
    private int tope;
    private static final int TAM = 10;

    //Constructor:
    public Pila() {
        this.arreglo = new Object[TAM];
        this.tope = -1;
    }

    public boolean apilar(Object nuevoElem) {
        boolean exito;
        if (this.tope + 1 >= this.TAM) {
            //Error pila llena.
            exito = false;
        } else {
            //Pone el elemento en el tope de la pila e incrementa tope.
            this.tope++;
            this.arreglo[tope] = nuevoElem;
            exito = true;
        }
        return exito;
    }

    public boolean desapilar() {
        boolean exito;
        if (this.tope <= -1) {
            //Error pila vacía.
            exito = false;
        } else {
            //Pone null en la celda que libera y decrementa el tope.
            this.arreglo[tope] = null;
            this.tope--;
            exito = true;
        }

        return exito;
    }

    public Object obtenerTope() {
        Object elem;

        if (tope == -1) {
            //Pila vacía, devuelve null.
            elem = null;
        } else {
            //Devuelve el elemento que está en el tope de la pila.
            elem = this.arreglo[tope];
        }

        return elem;
    }

    public boolean esVacia() {
        boolean vacio;

        if (this.tope <= -1) { //Pregunta si está vacío el tope o no.
            vacio = true;
        } else {
            vacio = false;
        }

        return vacio;
    }

    public void vaciar() {

        while (!esVacia()) { //tope != -1
            //Pone null en la celda donde está el tope y luego decrementa el tope.
            this.arreglo[tope] = null;
            this.tope--;
        }
    }

    public Pila clone() {
        Pila nuevaPila = new Pila();
        int i;
        for (i = 0; i <= this.tope; i++) {
            //Copia el tope a la nueva pila y el contenido de la celda.
            nuevaPila.tope = i;
            nuevaPila.arreglo[i] = this.arreglo[i];
        }
        return nuevaPila;
    }

    @Override
    public String toString() {
        String s = "[";

        for (int i = 0; i <= this.tope; i++) {

            if (i == 0) {
                //Añade el primer contenido de la celda de la pila al string s.
                s += this.arreglo[i].toString();
            } else {
                //Añade la "," y además el contenido de las siguientes celdas al string s.
                s = s + "," + this.arreglo[i].toString();
            }
        }
        s += "]";

        return s;
    }
}
