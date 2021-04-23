/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package lineales.dinamicas;

/**
 *
 * @author Daniel Guzman FAI-1430 / Fabian Sepulveda FAI-2714
 */

public class Cola {

    //Atributos:
    private Nodo frente;
    private Nodo fin;

    //Constructor:
    public Cola() {
        this.frente = null;
        this.fin = null;
    }

    public boolean poner(Object nuevoElem) {
        //Nuevo Nodo..
        Nodo nuevo = new Nodo(nuevoElem, null);
        
        if (esVacia()) {
            //Si la cola está vacía entonces tanto el fin como el frente tendrá el nodo nuevo con el elemento.
            this.fin = nuevo;
            this.frente = nuevo;
        } else {
            //Si no, setea el último elemento al nuevo elemento y además a fin estará apuntado al nodo nuevo. 
            this.fin.setEnlace(nuevo);
            this.fin = nuevo;
        }
        //Retorna siempre true porque nunca se llena..
        return true;
    }

    public boolean sacar() {
        boolean exito = true;

        if (this.frente == null) {
            //La cola esta vacía, reporta error..
            exito = false;
        } else {
            //Al menos hay un elemento
            //Quita el pimer elemento y actualiza frente (y fin si queda vacía)
            this.frente = this.frente.getEnlace();
            
            if (this.frente == null) {
                this.fin = null;
            }
        }
        
        return exito;
    }

    public Object obtenerFrente() {
        Object elem;

        if (esVacia()) {
            //Si la cola está vacía entonces el elemento es null. 
            elem = null;
        } else {
            //Si no, accedo el elemento del frente de la cola.
            elem = this.frente.getElem();
        }
        
        return elem;
    }

    public boolean esVacia() {
        boolean exito = false;

        if ((this.frente == null) && (this.fin == null)) {
            exito = true;
        }
        
        return exito;
    }

    public void vaciar() {
        this.fin = null;
        this.frente = null;
    }

    public Cola clone() {
        Cola clon = new Cola();
        
        if (!esVacia()) {
            //Si la cola no está vacía entonces creo un nodo "aux1" que apunte al frente de la cola.
            //Además al frente de la cola clon se le crea un nuevo nodo con el elemento del aux1.
            //También a aux1 avanza al siguiente enlace y se crea un nuevo nodo "aux2" que apunte al frente de la cola clon.
            Nodo aux1 = this.frente;
            clon.frente = new Nodo(aux1.getElem(), null);
            aux1 = aux1.getEnlace();
            Nodo aux2 = clon.frente;
            
            while (aux1 != null) {
                //Mientras aux1 no esté vacía entonces a aux2 le setea un nuevo nodo con el elemento de aux1.
                //Y tanto a aux2 y aux1 avanza al siguiente enlace.
                aux2.setEnlace(new Nodo(aux1.getElem(), null));
                aux2 = aux2.getEnlace();
                aux1 = aux1.getEnlace();
            }
            //El puntero fin se le asigna al final de la repetitiva.
            clon.fin = aux2;
        }

        return clon;
    }

    @Override
    public String toString() {
        String s = "";

        if (esVacia()) {
            //Si la cola está vacía, imprime "Cola Vacía".
            s = "Cola Vacía";
        } else {
            //Si no, se crea un nuevo nodo que apunte al frente de la cola e imprime "[".
            Nodo aux = this.frente;
            s = "[";

            while (aux != null) {
                //Mientras aux sea distinto de null entonces se le agrega el elemento a la cadena "s".
                //Y avanza al siguiente nodo.
                s += aux.getElem().toString();
                aux = aux.getEnlace();

                if (aux != null) {
                    s += ",";
                } else {
                    s += "]";
                }
            }
        }
        
        return s;
    }
    
    public String toStringTransparente() {
    	// imprime la estructura interna de la cola, en Cola din�mica
    	// es indistinguible de toString()
    	return this.toString();
    }
}