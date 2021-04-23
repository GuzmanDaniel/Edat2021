/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jerarquicas.dinamicas;

import lineales.dinamicas.Lista;
import lineales.dinamicas.Cola;

/**
 *
 * @author Daniel Guzman FAI-1430 / Fabian Sepulveda FAI-2714
 */

public class ArbolBin {

    //Atributo
    private NodoArbol raiz;

    //Constructor
    public ArbolBin() {
        this.raiz = null;
    }

    public boolean insertar(Object elemNuevo, Object elemPadre, char lugar) {
        //Inserta en el �rbol elemNuevo con informacion de elemPadre y lugar,
        //Donde lugar puede ser "I" (Izquierda) o "D" (Derecha) para saber
        //En que posici�n va el elemNuevo
        boolean exito = true;

        if (this.raiz == null) {
            //Si el �rbol est� vac�o, entonces el nuevo elemento va a ser la raiz
            this.raiz = new NodoArbol(elemNuevo, null, null);
        } else {
            //Si no esta vac�o, se busca el padre
            NodoArbol nodoPadre = obtenerNodo(this.raiz, elemPadre);
            if (nodoPadre != null) {
                if ((lugar == 'I') && (nodoPadre.getIzquierdo() == null)) {
                    //Si el padre existe y no tiene HI, se lo agrega
                    nodoPadre.setIzquierdo(new NodoArbol(elemNuevo, null, null));
                } else {
                    if ((lugar == 'D') && (nodoPadre.getDerecho() == null)) {
                        //Si el padre existe y no tiene HD, se lo agrega
                        nodoPadre.setDerecho(new NodoArbol(elemNuevo, null, null));
                    } else {
                        //Si el padre no existe o ya tiene ese hijo, da error
                        exito = false;
                    }
                }
            } else {
                exito = false;
            }
        }
        return exito;
    }

    private NodoArbol obtenerNodo(NodoArbol n, Object buscado) {
        //M�todo privado que busca un elemento y devuelve el nodo que lo contiene.
        //Si no se encuentra buscado, devuelve null
        NodoArbol resultado = null;

        if (n != null) {
            if (n.getElem().equals(buscado)) {
                //Si el buscado es n, lo devuelve
                resultado = n;
            } else {
                //No es el buscado: busca primero en el HI
                resultado = obtenerNodo(n.getIzquierdo(), buscado);
                //Si no lo encuentra en el HI, busca en HD
                if (resultado == null) {
                    resultado = obtenerNodo(n.getDerecho(), buscado);
                }
            }
        }
        return resultado;
    }

    public boolean esVacio() {
        //Devuelve true si el �rbol esta vac�o o false si no est� vac�o
    	
    	// si arbol vac�o, raiz es null. Si no vac�o, hay ra�z y no es null.
        return raiz == null;
    }

    public int altura() {
        return alturaAux(this.raiz);
    }

    private int alturaAux(NodoArbol n) {
        //Retorna la altura del subarbol definido por nodo.
        
        //Caso base 1: nodo es null, salta el if.
        int altura = -1;
        if(n != null){
            //Caso recursivo: sumar 1 a la mayor altura de los hijos.
            altura = Math.max(alturaAux(n.getIzquierdo()), alturaAux(n.getDerecho())) + 1;
        }
        return altura;
    }

    public int nivel(Object elemNuevo) {
        //M�todo p�blico para hallar el nivel del elemNuevo ingresado por el usuario
        //Llama el m�todo nivelAux para poder buscarlo
        return nivelAux(this.raiz, elemNuevo);
    }

    private int nivelAux(NodoArbol n, Object elemento) {
        //M�todo privado para hallar el nivel del �rbol, con raiz como par�metro
        //Y elemento es lo que ingresa el usuario
        int nivel;
        if (n == null) {
            //Si el nodo ingresado es nulo entonces nivel es -1
            nivel = -1;
        } else if (n.getElem().equals(elemento)) {
            //Si el elemento de n es igual al elemento pasado por par�metro entonces
            //Nivel tiene valor 0
            nivel = 0;
        } else {
            //Si todav�a no encuentra el elemento entonces lo busca por la rama izquierda (HI)
            nivel = nivelAux(n.getIzquierdo(), elemento);
            if (nivel == -1) {
                //Si el nivel es -1 entonces lo busca por la rama derecha (HD)
                nivel = nivelAux(n.getDerecho(), elemento);
            }
            if (nivel != -1) {
                //Si nivel es distinto de -1, entonces lo incrementa en 1
                nivel++;
            }
        }

        return nivel;
    }

    public Object padre(Object elemento) {
        return padreRecursivo(elemento, this.raiz);
    }

    private Object padreRecursivo(Object elemento, NodoArbol n) {
        Object padre = null;
        if (n != null) {
        	// si n es padre de elemento, ya sea hijo izquierdo o derecho
            if ((n.getIzquierdo() != null && n.getIzquierdo().getElem().equals(elemento))
                    || (n.getDerecho() != null && n.getDerecho().getElem().equals(elemento))) {
                padre = n.getElem();
            } else {
                if (n.getElem().equals(elemento)) {
                    padre = null;
                } else {
                    padre = padreRecursivo(elemento, n.getIzquierdo());
                    if (padre == null) {
                        padre = padreRecursivo(elemento, n.getDerecho());
                    }
                }
            }
        }
        return padre;
    }

    public void vaciar() {
        // M�todo p�blico para vaciar el �rbol poniendo null a la raiz
        this.raiz = null;
        // El garbage collector se lleva la ra�z dejando a sus hijos
        // sin referencia. Luego estos hijos son reclamados dejando 
        // a sus hijos sin referencia. As� recursivamente todos los
        // nodos del �rbol son reclamados.
    }

    public ArbolBin clone() {
        //M�todo p�blico para clonar el �rbol
        ArbolBin nuevo = new ArbolBin();
        //Llama al m�todo clonarAux para clonar el �rbol nuevo
        nuevo.raiz = clonarAux(this.raiz);
        return nuevo;
    }

    private NodoArbol clonarAux(NodoArbol aux) {
        //M�todo privado para clonar el �rbol
        NodoArbol hijo = null;
        if (aux != null) {
            //El nodo hijo es creado con el elemento de aux, y enlazado con el HI y HD correspondiente
            //Haciendo uso del mismo m�todo
            hijo = new NodoArbol(aux.getElem(), clonarAux(aux.getIzquierdo()), clonarAux(aux.getDerecho()));
        }
        return hijo;
    }

    @Override
    public String toString() {
        //M�todo publico para mostrar el contenido del �rbol
        String cadena = "�rbol vac�o";
        //Llama al m�todo privado toStringAux para trabajarlo m�s a fondo
        cadena = toStringAux(this.raiz);
        // a�adir una representaci�n gr�fica del �rbol
        cadena += toStringSubArbol(raiz, "", 'r');
        return cadena;
    }
    
	private String toStringSubArbol(NodoArbol nodo, String prefijo, char lugar) {
		// imprime el arbol en recorrido pre-orden, agregando espacios
		// seg�n la profundidad
		String rep = "";
		
		if(nodo != null) {
			rep = prefijo;
			
			if(lugar == 'D') {
				rep += "|";
				prefijo += " ";
			}
			// agregar el elemento del nodo actual
			rep += "------" + lugar + " " + nodo.getElem().toString() + "\n";
		
			// agregar el resto del árbol
			rep += toStringSubArbol(nodo.getIzquierdo(), prefijo + "      |", 'I');
			rep += toStringSubArbol(nodo.getDerecho(), prefijo + "      ", 'D');
		}
		return rep;
	}

    private String toStringAux(NodoArbol n) {
        String cadena1 = "";
        if (n != null) {
            if ((n.getIzquierdo() != null) && (n.getDerecho() != null)) {
                //Si n no es nulo y tiene HI y HD entonces a cadena1 se le pone el padre
                //Y sus respectivos hijos junto con un salto de linea
                cadena1 = "Padre: " + n.getElem() + " HI: " + n.getIzquierdo().getElem() + " HD: " + n.getDerecho().getElem() + "\n";
            } else if ((n.getIzquierdo() == null) && (n.getDerecho() != null)) {
                //Si n no es nulo y solo tiene HD entonces a cadena1 se le pone el padre
                //Y el HD junto con un salto de linea
                cadena1 = "Padre: " + n.getElem() + " HI: -  HD: " + n.getDerecho().getElem() + "\n";
            } else if ((n.getIzquierdo() != null) && (n.getDerecho() == null)) {
                //Su n no es nulo y solo tiene HI entonces a cadena 1 se le pone el padre
                //Y el HI junto con un salto de linea
                cadena1 = "Padre: " + n.getElem() + " HI: " + n.getIzquierdo().getElem() + " HD: -" + "\n";
            } else {
                //Si n no es nulo y no tiene hijos entonces a cadena1 se le pone el padre
                //Junto con un salto de linea
                cadena1 = "Padre: " + n.getElem() + " HI: -  HD: -" + "\n";
            }
            //Recorre la rama Izquierda del Ã¡rbol
            cadena1 += toStringAux(n.getIzquierdo());
            //Recorre la rama Derecha del Ã¡rbol
            cadena1 += toStringAux(n.getDerecho());
        }
        return cadena1;
    }

	// en los m�todos de recorridos se usa insertar(elem, 1) en listas
	// porque es de O(1) dada la implementaci�n de lista con cabecera y
    // por lo tanto m�s eficiente que insertar() general que es de O(n).
	// esta forma de insertar tiene el inconveniente de que el orden de la lista
	// es invertido, por lo que antes de retornar la lista debe ser invertida.
	// En total cada elemento es visitado 2 veces, la primera al recorrer el 
	// �rbol y la segunda al invertir la lista, siendo los m�todos de O(2n) = O(n)
	
	// Si en cambio se inserta por el final, por la regla del producto, 
	// tenemos O(n) por recorrer cada elemento y O(n) del insertar general 
	// para cada elemento, dando O(n * n) = O(n^2)
    
    public Lista listarPreOrden() {
        //M�todo p�blico para listar los elementos del �rbol en preorden
        Lista listaPreOrden = new Lista();
        //Llama al m�todo privado preordenAux para trabajarlo
        preordenAux(this.raiz, listaPreOrden);
        //Invierto la lista.
        listaPreOrden.invertir();
        
        return listaPreOrden;
    }

    private Lista preordenAux(NodoArbol nodo, Lista lista) {
        if (nodo != null) {
            //Ingresa el elemento a la lista pasada por par�metro (el padre).
            lista.insertar(nodo.getElem(), 1);
            //Recorre sus hijos en preorden.
            preordenAux(nodo.getIzquierdo(), lista);
            preordenAux(nodo.getDerecho(), lista);
        }
        return lista;
    }

    public Lista listarInOrden() {
        //M�todo p�blico para listar los elementos del �rbol en inorden
        Lista listaInOrden = new Lista();
        //Llama al m�todo privado inordenAux para trabajarlo
        inordenAux(this.raiz, listaInOrden);
        //Invierto la lista.
        listaInOrden.invertir();
        
        return listaInOrden;
    }

    private Lista inordenAux(NodoArbol nodo, Lista lista) {
        if (nodo != null) {
            //Recorre primero el HI
            inordenAux(nodo.getIzquierdo(), lista);
            //Ingresa el elemento a la lista pasada por par�metro (padre)
            lista.insertar(nodo.getElem(), 1);
            //Recorre por �ltimo el HD
            inordenAux(nodo.getDerecho(), lista);
        }
        return lista;
    }

    public Lista listarPosOrden() {
        //M�todo publico para listar los elementos del �rbol en posorden
        Lista listaPosOrden = new Lista();
        //Llama al m�todo privado posordenAux para trabajarlo
        posordenAux(this.raiz, listaPosOrden);
        //Invierto la lista
        listaPosOrden.invertir();
        
        return listaPosOrden;
    }

    private Lista posordenAux(NodoArbol nodo, Lista lista) {
        if (nodo != null) {
            //Recorre primero el HI
            posordenAux(nodo.getIzquierdo(), lista);
            //Despu�s el HD
            posordenAux(nodo.getDerecho(), lista);
            //Ingresa el elemento a la lista pasada por par�metro (padre)
            lista.insertar(nodo.getElem(), 1);
        }
        return lista;
    }

    public Lista listarPorNivel() {
		// retorna una lista con los elemetos ordenados en recorrido por nivel
		
		Cola cola = new Cola();
		Lista lista = new Lista();
		
		// si raiz == null no hay elementos y retorna lista vac�a
		if(raiz != null) {
			cola.poner(raiz);
			
			while(!cola.esVacia()) {
				
				// visitar siguiente nodo
				NodoArbol nodo = (NodoArbol) cola.obtenerFrente();
				cola.sacar();
				lista.insertar(nodo.getElem(), 1);
				
				// poner a los hijos del nodo en la cola
				NodoArbol izquierdo = nodo.getIzquierdo();
				NodoArbol derecho = nodo.getDerecho();
				if(izquierdo != null) cola.poner(izquierdo);
				if(derecho != null) cola.poner(derecho);
			}
		}
		
		lista.invertir();
		return lista;
	}

    public Lista obtenerAncestros(Object elemento) {
        Lista lista = new Lista();
        obtenerAncestrosAux(this.raiz, lista, elemento);
        // dependiendo de la salida que se desea se invierte la lista o no.
        lista.invertir();
        return lista;
    }

    private boolean obtenerAncestrosAux(NodoArbol nodo, Lista lista, Object elemento) {
        boolean encontrado = false;
        if (nodo != null) {
            if (nodo.getElem().equals(elemento)) {
                encontrado = true;
                //Se encuentra a la izquierda o a la derecha del nodo actual.
            } else if (obtenerAncestrosAux(nodo.getIzquierdo(), lista, elemento) || obtenerAncestrosAux(nodo.getDerecho(), lista, elemento)) {
                // verifica si se encuentra a la izquierda, aprovecho el corto circuito para en caso de que este, no se busque
                // caso contrario se ejecutara el siguiente llamado recursivo
                lista.insertar(nodo.getElem(), 1);
                encontrado = true;
            }
        }
        return encontrado;
    }

	public Lista frontera() {
		// retorna una lista con los nodos hoja del arbol
		
		Lista lista = new Lista();
		
		listarHojas(raiz, lista);
		lista.invertirInPlace();
		
		return lista;
	}
	
	private void listarHojas(NodoArbol nodo, Lista lista) {
		// agrega los nodos hoja del arbol en nodo a la lista
		
		// caso base 1: nodo es null
		
		if(nodo != null) {
			NodoArbol izquierdo = nodo.getIzquierdo();
			NodoArbol derecho = nodo.getDerecho();
			
			// caso base 2: nodo hoja encontrado!!!
			if(izquierdo == null && derecho == null) {
				lista.insertar(nodo.getElem(), 1);
			}
			else {
				listarHojas(izquierdo, lista);
				listarHojas(derecho, lista);
			}
		}
	}
}
