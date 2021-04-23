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

public class Lista {

    private Nodo cabecera;
    private int longitud = 0;

    public Lista() {
        this.cabecera = null;
        this.longitud = 0;
    }

    public boolean insertar(Object nuevoElem, int pos) {
        //Inserta el elemento nuevo en la posicion pos.
        //Detecta y reporta error posicion invalida.
        boolean exito = true;

        if ((pos < 1) || (pos > this.longitud() + 1)) {
            exito = false;
        } else {
            if (pos == 1) {
                //Crea un nuevo nodo y se enlaza en la cabecera y aumenta la longitud de la lista en 1.
                this.cabecera = new Nodo(nuevoElem, this.cabecera);
                this.longitud++;
            } else {
                //Avanza hazta el ultimo elemento en posicion pos-1.
                Nodo aux = encontrarNodo(pos - 1);
                //Crea el nodo y lo enlaza y despues aumenta la longitud de la lista en 1.
                Nodo nuevo = new Nodo(nuevoElem, aux.getEnlace());
                aux.setEnlace(nuevo);
                this.longitud++;
            }
        }
        return exito;
    }

    public boolean eliminar(int pos) {
        boolean exito = true;

        // verificar posición válida
        if ((pos < 1) || (pos > this.longitud())) {
            exito = false;
        } else {
            if (pos == 1) {        
            	// caso especial eliminar la cabecera
                this.cabecera = this.cabecera.getEnlace();
                this.longitud--;
            } else {
            	// caso general, encontrar el nodo a quitar y quitarlo
                Nodo aux = encontrarNodo(pos - 1);
                aux.setEnlace(aux.getEnlace().getEnlace());
                this.longitud--;
            }
        }
        return exito;
    }

    public Object recuperar(int pos) {
        Object elem;
        
        // verificar posición válida
        if ((pos < 1) || (pos > this.longitud())) {
            elem = null;
        } else {
        	// encontrar el elemento
            Nodo aux = encontrarNodo(pos);
            elem = aux.getElem();
        }
        return elem;
    }

	private Nodo encontrarNodo(int posicion) {
		// retorna el nodo en la posición dada
		// precondición: el nodo se encuentra en la lista
		Nodo aux = cabecera;
		for(int i = 1; i < posicion; i++) {
			aux = aux.getEnlace();
		}
		return aux;
	}
    
	public int localizar(Object elem) {
		Nodo aux = cabecera;
		boolean encontrado = false;
		int pos = 0;
		// termina al alcanzar final de la lista o encontrar el elemento
		while(aux != null && !encontrado) {
			pos++;
			// comparar con elemento actual
			encontrado = aux.getElem().equals(elem);
			// avanzar
			aux = aux.getEnlace();
		}
		// !encontrado significa salir del while porque
		// se recorrió toda la lista sin encontrar elem
		if(!encontrado) pos = -1;

		return pos;
	}

    public void vaciar() {
        this.cabecera = null;
        this.longitud = 0;
    }

    public boolean esVacia() {
        boolean vacia = false;
        if (this.cabecera == null) {
            vacia = true;
        }
        return vacia;
    }
    
    public Lista clone() {
        Lista nuevaLista = new Lista();
        nuevaLista.longitud = this.longitud;

        if (!esVacia()) {
        	// punteros auxiliares para recorrer lista y clon
            Nodo aux1 = this.cabecera;
            Nodo aux2;
            // copiar el primer nodo y avanzar
            nuevaLista.cabecera = new Nodo(aux1.getElem(), null);
            aux2 = nuevaLista.cabecera;
            aux1 = aux1.getEnlace();
            // copiar el resto de la lista
            while (aux1 != null) {
            	// copiar
                aux2.setEnlace(new Nodo(aux1.getElem(), null));
                // avanzar
                aux1 = aux1.getEnlace();
                aux2 = aux2.getEnlace();
            }
        }
        return nuevaLista;
    }

    public int longitud() {
        return this.longitud;
    }

    @Override
    public String toString() {
        String s;

        if (this.cabecera == null) {
            s = "Lista vacía";
        } else {
            Nodo aux = this.cabecera;
            s = "[";

            while (aux != null) {
                s += aux.getElem();
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

    public Lista obtenerMultiplos(int num) {
        Lista nuevaLista = new Lista();
        Nodo aux = this.cabecera;
        Nodo aux1 = null;
        int lon = this.longitud(), i = 1, pos = 0;
        while (i <= lon) {
            if (i % num == 0) {
                pos++;
                if (pos == 1) {
                    nuevaLista.cabecera = new Nodo(aux.getElem(), null);
                    aux1 = nuevaLista.cabecera;
                } else {
                    aux1.setEnlace(new Nodo(aux.getElem(), null));
                    aux1 = aux1.getEnlace();
                }
            }
            i++;
            aux = aux.getEnlace();
        }
        return nuevaLista;
    }

	public void eliminarApariciones(Object elem) {
		// elimina todas las apariciones de elem en la lista
		
		// agregar un nodo extra elimina casos especiales como
		// eliminar varios elementos al inicio de la lista
		cabecera = new Nodo(null, cabecera);
		
		Nodo aux = cabecera;
		Nodo visitado;
		while(aux != null && (visitado = aux.getEnlace()) != null) {
			if(visitado.getElem().equals(elem)) {
				// saltar el nodo para quitarlo de la lista
				aux.setEnlace(visitado.getEnlace());
				longitud--;
			}
			else {
				aux = aux.getEnlace();
			}
		}
		
		// finalmente quitar el nodo extra
		cabecera = cabecera.getEnlace();
	}
    
    public void invertir() {
        Nodo anterior = this.cabecera;
        invertirAux(this.cabecera);
        if (anterior != null) {
            anterior.setEnlace(null);
        }
    }

    private void invertirAux(Nodo nodo1) {
        if (nodo1 != null) {
            this.cabecera = nodo1;
            invertirAux(nodo1.getEnlace());
            if (nodo1.getEnlace() != null) {
                nodo1.getEnlace().setEnlace(nodo1);
            }
        }
    }
    
	public Lista invertida() {
		// retorna la inversa de esta lista
		Nodo aux = cabecera;
		Lista invertida = new Lista();
		invertida.longitud = this.longitud;
		while(aux != null) {
			// agregar el siguiente elemento
			invertida.cabecera = new Nodo(aux.getElem(), invertida.cabecera);
			
			// avanzar
			aux = aux.getEnlace();
		}
		return invertida;
	}
	
	public void invertirInPlace() {
		// invierte esta lista
		
		if(cabecera != null) {
			
			// nueva cabecera de la lista invertida
			Nodo nuevaCabecera = cabecera;
			cabecera = cabecera.getEnlace();
			nuevaCabecera.setEnlace(null);
			
			while(cabecera != null) {
				
				// sacar el siguiente elemento de la lista
				Nodo siguiente = cabecera;
				cabecera = cabecera.getEnlace();
				
				// enganchar a la lista invertida
				siguiente.setEnlace(nuevaCabecera);
				nuevaCabecera = siguiente;
			}
			cabecera = nuevaCabecera;
		}
	}

}