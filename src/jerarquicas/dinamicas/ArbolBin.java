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
 * @author DanielPatricio
 */
public class ArbolBin {

    //Atributo
    private NodoArbol raiz;

    //Constructor
    public ArbolBin() {
        this.raiz = null;
    }

    public boolean insertar(Object elemNuevo, Object elemPadre, char lugar) {
        //Inserta en el Ã�rbol elemNuevo con informacion de elemPadre y lugar,
        //Donde lugar puede ser "I" (Izquierda) o "D" (Derecha) para saber
        //En que posiciÃ³n va el elemNuevo
        boolean exito = true;

        if (this.raiz == null) {
            //Si el Ã¡rbol esta vacÃ­o, entonces el nuevo elemento va a ser la raiz
            this.raiz = new NodoArbol(elemNuevo, null, null);
        } else {
            //Si no esta vacÃ­o, se busca el padre
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
        //MÃ©todo privado que busca un elemento y devuelve el nodo que lo contiene.
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
        //Devuelve true si el Ã�rbol esta vacÃ­o o false si no estÃ¡ vacÃ­o
        boolean res = false;
        if (this.raiz == null) {
            res = true;
        }
        return res;
    }

    public int altura() {
        //MÃ©todo publico para hallar la altura del Ã¡rbol.
        int alt = -1;
        if (this.raiz != null) {
            //Si la raÃ­z no es nula, entonces llama el mÃ©todo alturaAux
            alt = alturaAux(0, this.raiz);
        }
        return alt;
    }

    private int alturaAux(int i, NodoArbol n) {
        //MÃ©todo privado para hallar la altura, precondiciÃ³n: hay un nodo (raiz)
        int res, alt1 = 0, alt2 = 0;
        if ((n.getDerecho() == null) && (n.getIzquierdo() == null)) {
            //Si el nodo es una hoja, devuelve el valor de i que entra como parÃ¡metro
            res = i;
        } else {
            //Si no es una hoja, entonces incrementa en 1 el valor i
            i++;
            if (n.getIzquierdo() != null) {
                //Si tiene un HI entonces vuelve a llamar al mÃ©todo con el HI como parÃ¡metro
                alt1 = alturaAux(i, n.getIzquierdo());
            }
            if (n.getDerecho() != null) {
                //Si tiene un HD entonces vuelve a llamar al mÃ©todo con el HD como parÃ¡metro
                alt2 = alturaAux(i, n.getDerecho());
            }
            if (alt1 >= alt2) {
                //Si la rama Izquierda (alt1) es mayor que la rama Derecha (alt2) del Ã¡rbol
                //Entonces a res se le pone el valor de alt1
                res = alt1;
            } else {
                //En caso contrario, a res se le pone el valor de alt2
                res = alt2;
            }
        }
        return res;
    }

    public int nivel(Object elemNuevo) {
        //MÃ©todo pÃºblico para hallar el nivel del elemNuevo ingresado por el usuario
        //Llama el mÃ©todo nivelAux para poder buscarlo
        return nivelAux(this.raiz, elemNuevo);
    }

    private int nivelAux(NodoArbol n, Object elemento) {
        //MÃ©todo privado para hallar el nivel del Ã�rbol, con raiz como parÃ¡metro
        //Y elemento es lo que ingresa el usuario
        int nivel;
        if (n == null) {
            //Si el nodo ingresado es nulo entonces nivel es -1
            nivel = -1;
        } else if (n.getElem().equals(elemento)) {
            //Si el elemento de n es igual al elemento pasado por parÃ¡metro entonces
            //Nivel tiene valor 0
            nivel = 0;
        } else {
            //Si todavÃ­a no encuentra el elemento entonces lo busca por la rama izquierda (HI)
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
        //MÃ©todo pÃºblico para vaciar el Ã¡rbol poniendo null a la raiz
        this.raiz = null;
    }

    public ArbolBin clone() {
        //MÃ©todo pÃºblico para clonar el Ã¡rbol
        ArbolBin nuevo = new ArbolBin();
        //Llama al mÃ©todo clonarAux para clonar el Ã¡rbol nuevo
        nuevo.raiz = clonarAux(this.raiz);
        return nuevo;
    }

    private NodoArbol clonarAux(NodoArbol aux) {
        //MÃ©todo privado para clonar el Ã¡rbol
        NodoArbol hijo = null;
        if (aux != null) {
            //El nodo hijo es creado con el elemento de aux, y enlazado con el HI y HD correspondiente
            //Haciendo uso del mismo mÃ©todo
            hijo = new NodoArbol(aux.getElem(), clonarAux(aux.getIzquierdo()), clonarAux(aux.getDerecho()));
        }
        return hijo;
    }

    @Override
    public String toString() {
        //MÃ©todo publico para mostrar el contenido del Ã¡rbol
        String cadena = "Ã�rbol vacÃ­o";
        //Llama al mÃ©todo privado toStringAux para trabajarlo mÃ¡s a fondo
        cadena = toStringAux(this.raiz);
        cadena += toStringSubArbol(raiz, "", 'r');
        return cadena;
    }
    
	private String toStringSubArbol(NodoArbol nodo, String prefijo, char lado) {
		// imprime el arbol en recorrido pre-orden, agregando espacios
		// según la profundidad
		String rep = "";
		
		if(nodo != null) {
			rep = prefijo;
			
			if(lado == 'd') {
				rep += "|";
				prefijo += " ";
			}
			// agregar el elemento del nodo actual
			rep += "------" + lado + " " + nodo.getElem().toString() + "\n";
		
			// agregar el resto del árbol
			rep += toStringSubArbol(nodo.getIzquierdo(), prefijo + "      |", 'i');
			rep += toStringSubArbol(nodo.getDerecho(), prefijo + "      ", 'd');
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

    public Lista listarPreOrden() {
        //MÃ©todo publico para listar los elementos del Ã¡rbol en preorden
        Lista listaPreOrden = new Lista();
        //Llama al mÃ©todo privado preordenAux para trabajarlo
        preordenAux(this.raiz, listaPreOrden);
        //Invierto la lista.
        listaPreOrden.invertir();
        
        return listaPreOrden;
    }

    private Lista preordenAux(NodoArbol nodo, Lista lista) {
        if (nodo != null) {
            //Ingresa el elemento a la lista pasada por parÃ¡metro.
            lista.insertar(nodo.getElem(), 1);
            //Recorre sus hijos en preorden.
            preordenAux(nodo.getIzquierdo(), lista);
            preordenAux(nodo.getDerecho(), lista);
        }
        return lista;
    }

    public Lista listarInOrden() {
        //MÃ©todo publico para listar los elementos del Ã¡rbol en inorden
        Lista listaInOrden = new Lista();
        //Llama al mÃ©todo privado inordenAux para trabajarlo
        inordenAux(this.raiz, listaInOrden);
        //Invierto la lista.
        listaInOrden.invertir();
        
        return listaInOrden;
    }

    private Lista inordenAux(NodoArbol nodo, Lista lista) {
        if (nodo != null) {
            //Recorre primero el HI
            inordenAux(nodo.getIzquierdo(), lista);
            //Ingresa el elemento a la lista pasada por parÃ¡metro
            lista.insertar(nodo.getElem(), 1);
            //Recorre por Ãºltimo el HD
            inordenAux(nodo.getDerecho(), lista);
        }
        return lista;
    }

    public Lista listarPosOrden() {
        //MÃ©todo publico para listar los elementos del Ã¡rbol en posorden
        Lista listaPosOrden = new Lista();
        //Llama al mÃ©todo privado posordenAux para trabajarlo
        posordenAux(this.raiz, listaPosOrden);
        //Invierto la lista
        listaPosOrden.invertir();
        
        return listaPosOrden;
    }

    private Lista posordenAux(NodoArbol nodo, Lista lista) {
        if (nodo != null) {
            //Recorre primero el HI
            posordenAux(nodo.getIzquierdo(), lista);
            //DespuÃºes el HD
            posordenAux(nodo.getDerecho(), lista);
            //Ingresa el elemento a la lista pasada por parÃ¡metro
            lista.insertar(nodo.getElem(), 1);
        }
        return lista;
    }

    public Lista listarPorNivel() {
		// retorna una lista con los elemetos ordenados en recorrido por nivel
		
		Cola cola = new Cola();
		Lista lista = new Lista();
		
		// si raiz == null no hay elementos y retorna lista vacÃ­a
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
        Lista nuevaLista = new Lista();
        
        fronteraAux(this.raiz, nuevaLista);
        
        nuevaLista.invertir();
        
        return nuevaLista;
    }

    private void fronteraAux(NodoArbol n, Lista lista) {

        if (n != null) {
            if ((n.getIzquierdo() == null) && (n.getDerecho() == null)) {
                lista.insertar(n.getElem(), 1);
            } else {
                if (n.getIzquierdo() != null) {
                    fronteraAux(n.getIzquierdo(), lista);
                    if(n.getDerecho() != null){
                        fronteraAux(n.getDerecho(), lista);
                    }
                }else{
                    fronteraAux(n.getDerecho(), lista);
                }
            }
        }
    }
}
