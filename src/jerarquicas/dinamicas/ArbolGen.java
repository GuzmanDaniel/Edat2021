package jerarquicas.dinamicas;

import lineales.dinamicas.Lista;
import lineales.dinamicas.Cola;


public class ArbolGen {

    //Atributo:
    private NodoGen raiz;

    //Constructor:
    public ArbolGen() {
        this.raiz = null;
    }

    public boolean insertar(Object elemNuevo, Object elemPadre) {
        boolean exito = true;
       
        if (esVacio()) { 
            //Si es vacio entonces elemNuevo va a ser la raiz del Árbol.
            this.raiz = new NodoGen(elemNuevo, null, null);
        } else {
            //Si no es vacio entonces verifico si el elemPadre (pasado por parámetro) existe dentro del Árbol.
            NodoGen nodoPadre = obtenerNodo(this.raiz, elemPadre);
            if (nodoPadre != null) {
                //En caso de que exista dicho padre entonces verifico si tiene HEI.
                if (nodoPadre.getHijoIzquierdo() == null) {
                    //Si no tiene HEI entonces elemNuevo va a ser el HEI del nodoPadre.
                    nodoPadre.setHijoIzquierdo(new NodoGen(elemNuevo, null, null));
                } else {
                    //En caso de que tenga el HEI entonces creo un nodo llamado hijoIzquierdo para poder moverme a partir
                    //De ahi y verificar si tiene hermanos derechos.
                    NodoGen hijoIzquierdo = nodoPadre.getHijoIzquierdo();
                    while (hijoIzquierdo.getHermanoDerecho() != null) {
                        //Voy al ultimo hermano derecho par poder insertar elemNuevo como nuevo hermano derecho
                        hijoIzquierdo = hijoIzquierdo.getHermanoDerecho();
                    }
                    hijoIzquierdo.setHermanoDerecho(new NodoGen(elemNuevo, null, null));
                }
            } else {
                //Si no existe el nodoPadre entonces devuelve false.
                exito = false;
            }
        }
        return exito;
    }

    private NodoGen obtenerNodo(NodoGen n, Object buscado) {
        NodoGen resultado = null;

        if (n != null) {
            //Si n no es nulo entonces arranca el método.            
            if (n.getElem().equals(buscado)) {
                //Caso base: si el elemento de n es igual al buscado entonces resultado va a tener el nodo n. 
                resultado = n;
            } else {
                //Caso recursivo: llamo recursivamente al método con el HEI de n.
                resultado = obtenerNodo(n.getHijoIzquierdo(), buscado);
                if (resultado == null) {
                    //En caso de que resulado sigue null entonces verifico con el hermano derecho de n.
                    resultado = obtenerNodo(n.getHermanoDerecho(), buscado);
                }
            }
        }
        return resultado;
    }

    public boolean pertenece(Object elemento) {
        boolean exito;

        if (esVacio()) {
            //Si es vacio entonces devuelve falso.
            exito = false;
        } else {
            //Si no es vacio llama al método perteneceAux.
            exito = perteneceAux(this.raiz, elemento);
        }
        return exito;
    }

    private boolean perteneceAux(NodoGen n, Object elemento) {
        boolean exito = false;

        if (n != null) {
            //Si n no es null entonces arranca el método.
            if (n.getElem().equals(elemento)) {
                //Caso base: si el elemento de n es igual al elemento buscado entonces exito se cambia a true.
                exito = true;
            } else {
                //Caso recursivo: si el elemento de n no es igual al elemento buscado entonces llama recursivamente
                //Al método con el HEI de n.
                exito = perteneceAux(n.getHijoIzquierdo(), elemento);
                if (exito == false) {
                    //Si exito sigue dando falso entonces llama recursivamente el método con el hermano derecho de n.
                    exito = perteneceAux(n.getHermanoDerecho(), elemento);
                }
            }
        }
        return exito;
    }

    public Lista ancestros(Object elemento) {
        Lista nuevaLista = new Lista();

        if (!esVacio()) {
            //Si no es vacio entonces llama al método ancestrosAux.
            ancestrosAux(this.raiz, this.raiz.getElem(), elemento, nuevaLista);
        }
        //Cuando termine de hacer la tarea el método ancestrosAux entonces
        //Elimina de la lista el primer elemento (vendría a ser el elemento dado por parámetro). 
        nuevaLista.eliminar(1);
        return nuevaLista;
    }

    private void ancestrosAux(NodoGen n, Object padre, Object elemento, Lista ls) {
        if (n != null) {
            //Si n no es null entonces arranca el método
            if (n.getElem().equals(elemento)) {
                //Caso base: si el elemento de n es igual al elemento pasado por parametro entonces lo inserta en la lista.
                ls.insertar(elemento, ls.longitud() + 1);
            } else {
                //Caso recursivo: llama recursivamente al método con el HEI de n.
                ancestrosAux(n.getHijoIzquierdo(), n.getElem(), elemento, ls);
                if (ls.esVacia()) {
                    //En caso de que la lista siga vacia entonces llama recursivamente al método con el hermano derecho de n.
                    ancestrosAux(n.getHermanoDerecho(), padre, elemento, ls);
                } else {
                    //Si no esta vacia la lista entonces inserta el elemento de n en la lista.
                    ls.insertar(n.getElem(), ls.longitud() + 1);
                }
            }
        }
    }

    public boolean esVacio() {
        boolean vacio;
        if (this.raiz == null) {
            //Si la raiz es null entonces vacio es true.
            vacio = true;
        } else {
            //Caso contrario, vacio es false.
            vacio = false;
        }
        return vacio;
    }

    public int altura() {
        int alt = 0;
        //Llama al método alturaAux.
        alt = alturaAux(this.raiz);
        return alt;
    }

    private int alturaAux(NodoGen n) {
        int aux, res = -1;
        if (n != null) {
            //Si n no es null entonces arranca el método creando un nodo hijo con el valor de n.
            NodoGen hijo = n;
            while (hijo != null) {
                //Mientras hijo no es null entonces llama recursivamente al método con HEI de n y luego le suma 1.
                aux = alturaAux(hijo.getHijoIzquierdo()) + 1;
                if (aux > res) {
                    //Si aux es mayor que res entonces res va a tener el valor de aux.
                    res = aux;
                }
                //El nodo hijo va a moverse al siguiente hermano derecho.
                hijo = hijo.getHermanoDerecho();
            }
        }
        return res;
    }

    public int nivel(Object elemento) {
        int niv;
        //Creo un nodo llamado aux1 para verificar si existe el nodo que contiene el elemento pasado por parametro.
        NodoGen aux1 = obtenerNodo(this.raiz, elemento);
        if ((esVacio()) || (aux1 == null)) {
            //En caso de que la raiz esté vacia o si no existe tal elemento, entonces niv va a tener el valor -1.
            niv = -1;
        } else {
            //En caso contrario llama al método nivelAux.
            niv = nivelAux(this.raiz, elemento);
        }
        return niv;
    }

    private int nivelAux(NodoGen n, Object elemento) {
        int niv1 = -1;

        if(n.getElem().equals(elemento)) {
            //Caso base: si el elemento de n es igual al elemento pasado por parametro entonces niv1 va a tener
            //El valor 0.
            niv1 = 0;
        } else {
            //Caso recursivo: pregunto si n tiene HEI.
            if (n.getHijoIzquierdo() != null) {
                //En caso de tenerlo entonces llama recursivamente el método con el HEI de n.
                niv1 = nivelAux(n.getHijoIzquierdo(), elemento);
                if (niv1 == -1) {
                    //Si niv1 sigue estando con valor -1 entonces creo un nodo aux con el hermano derecho del HEI de n.
                    NodoGen aux = n.getHijoIzquierdo().getHermanoDerecho();
                    while (aux != null) {
                        //Mientras aux sea distinto de null entonces llama recursivamente al método con aux de parametro.
                        niv1 = nivelAux(aux, elemento);
                        if(niv1 > -1){
                            //Si niv1 es mayor a -1 entonces niv1 se incrementa en 1 y ademas pone null al nodo aux para
                            //Terminar la repetitiva while.
                            niv1++;
                            aux = null;
                        }else{
                            //En caso contrario se mueve al siguiente hermano derecho.
                            aux = aux.getHermanoDerecho();
                        }
                    }
                }else{
                    //En caso de que el elemento se encuentra en un HEI del árbol entonces se incrementa en 1.
                    niv1++;
                }
            }
        }    
        return niv1;
    }

    public Object padre(Object elemento) {
        Object elemPadre;
        //Creo un nodo llamado aux para verificar si existe el nodo que contiene el elemento pasado por parametro.
        NodoGen aux = obtenerNodo(this.raiz, elemento);
        if ((esVacio()) || (aux == null)) {
            //En caso de que la raiz esté vacia o si no existe tal elemento, entonces elemPadre va a tener el valor null.
            elemPadre = null;
        } else {
            //En caso contrario, verifico si la raiz es igual al elemento. Si es igual entonces elemPadre va a
            //Tener el valor null. Si no es igual entonces llama al método padreAux.
            if (this.raiz.getElem().equals(elemento)) {
                elemPadre = null;
            } else {
                elemPadre = padreAux(this.raiz, elemento).getElem();
            }
        }
        return elemPadre;
    }

    private NodoGen padreAux(NodoGen n, Object elemento) {
        NodoGen padre = null;
        if (n != null) {
            //Si n no es null entonces arranca el método creando un nodo llamado aux con el valor del HEI de n.
            NodoGen aux = n.getHijoIzquierdo();
            while (aux != null) {
                //Mientras aux no sea null entonces pregunto si aux es igual al elemento.
                if (aux.getElem().equals(elemento)) {
                    //Si es igual entonces el nodo padre va a tener el nodo n y además pone a aux en null para terminar
                    //La repetitiva while.
                    padre = n;
                    aux = null;
                } else {
                    //En caso de que no sea igual entonces se mueve al siguiente hermano derecho de aux.
                    aux = aux.getHermanoDerecho();
                }
            }
            //Al terminar la rapetitiva pregunto si padre sigue null. En caso afirmativo entonces llama primero recursivamente
            //Al método con el HEI de n y luego con el hermano derecho de n.
            if (padre == null) {
                padre = padreAux(n.getHijoIzquierdo(), elemento);
            }
            if (padre == null) {
                padre = padreAux(n.getHermanoDerecho(), elemento);
            }
        }

        return padre;
    }

    public Lista listarPreorden() {
        Lista salida = new Lista();
        //Llama al método listarPreordenAux.
        listarPreordenAux(this.raiz, salida);
        return salida;
    }

    private void listarPreordenAux(NodoGen n, Lista ls) {
        if (n != null) {
            //Si n no es null entonces arranca el método insertando a la lista el elemento de n.
            ls.insertar(n.getElem(), ls.longitud()+1);
            
            if (n.getHijoIzquierdo() != null) {
                //Después pregunto si tiene HEI, en caso afirmativo llama recursivamente al método con el HEI de n.
                listarPreordenAux(n.getHijoIzquierdo(), ls);
            }
            if (n.getHijoIzquierdo() != null) {
                //Después creo un nodo llamado hijo con el valor del hermano derecho del HEI de n.
                NodoGen hijo = n.getHijoIzquierdo().getHermanoDerecho();
                while (hijo != null) {
                    //Mientras hijo no sea null entonces llamo recursivamente al método con el nodo hijo
                    //Y despues me muevo al siguiente hijo derecho de n.
                    listarPreordenAux(hijo, ls);
                    hijo = hijo.getHermanoDerecho();
                }
            }
        }
    }

    public Lista listarInorden() {
        Lista salida = new Lista();
        //Llamo al método listarInordenAux.
        listarInordenAux(this.raiz, salida);
        return salida;
    }

    private void listarInordenAux(NodoGen n, Lista ls) {
        if (n != null) {
            //Si n no es null entonces empieza el método preguntando si existe un HEI de n.
            if (n.getHijoIzquierdo() != null) {
                //En caso afirmativo, llama recursivamente al método con el valor del HEI de n.
                listarInordenAux(n.getHijoIzquierdo(), ls);
            }
            //Después inserto en la lista el elemento de n.
            ls.insertar(n.getElem(), ls.longitud() + 1);

            if (n.getHijoIzquierdo() != null) {
                //Por último creo un nodo llamado hijo con el nodo del hermano derecho del HEI de n.
                NodoGen hijo = n.getHijoIzquierdo().getHermanoDerecho();
                while (hijo != null) {
                    //Mientras hijo no sea null entonces llamo recursivamente al método con el nodo hijo
                    //Y después se mueve al siguiente hermano derecho.
                    listarInordenAux(hijo, ls);
                    hijo = hijo.getHermanoDerecho();
                }
            }
        }
    }

    public Lista listarPosorden() {
        Lista salida = new Lista();
        //Llamo al método listarPosordenAux.
        listarPosordenAux(this.raiz, salida);
        return salida;
    }

    private void listarPosordenAux(NodoGen n, Lista ls) {
        if (n != null) {
            //Si n no es null entonces empieza el método preguntando si existe un HEI de n.
            if (n.getHijoIzquierdo() != null) {
                //En caso afirmativo, llama recursivamente al método con el valor del HEI de n.
                listarPosordenAux(n.getHijoIzquierdo(), ls);
            }

            if (n.getHijoIzquierdo() != null) {
                //Después creo un nodo llamado hijo con el nodo del hermano derecho del HEI de n.
                NodoGen hijo = n.getHijoIzquierdo().getHermanoDerecho();
                while (hijo != null) {
                    //Mientras hijo no sea null entonces llamo recursivamente al método con el nodo hijo
                    //Y después se mueve al siguiente hermano derecho.
                    listarPosordenAux(hijo, ls);
                    hijo = hijo.getHermanoDerecho();
                }
            }
            //Por último inserto en la lista el elemento de n.
            ls.insertar(n.getElem(), ls.longitud() + 1);
        }
    }

    public Lista listarNiveles() {
        Cola nuevaCola = new Cola();
        Lista ls = new Lista();
        NodoGen n, hijo;
        //Al crear la cola, el primer elemento colocado es la raiz.
        nuevaCola.poner(this.raiz);
        while (!nuevaCola.esVacia()) {
            //Mientas la cola no esté vacia, al nodo n se le asigna el nodo que esta al frente de la cola.
            //Y despues le saca dicho nodo a la cola.
            n = (NodoGen) nuevaCola.obtenerFrente();
            nuevaCola.sacar();
            //Además le inserta a la lista el elemento del nodo n.
            ls.insertar(n.getElem(), ls.longitud() + 1);
            if (n.getHijoIzquierdo() != null) { 
                //Por último si el nodo n tiene HEI entonces 
                //Al nodo hijo se le asigna dicho nodo y a medida de que hijo no sea null entonces a la cola se le coloca el nodo hijo.
                hijo = n.getHijoIzquierdo();
                while(hijo != null){
                    nuevaCola.poner(hijo);
                    hijo = hijo.getHermanoDerecho();
                }
            }
        }
        return ls;
    }

    @Override
    public ArbolGen clone() {
        ArbolGen nuevo = new ArbolGen();
        //Llamo al método clonarAux con la raiz como parámetro.
        nuevo.raiz = clonarAux(this.raiz);
        return nuevo;
    }

    private NodoGen clonarAux(NodoGen aux) {
        //Creo un nodo hijo en null.
        NodoGen hijo = null;
        
        if (aux != null) {
            //Si aux no es null entonces asigno recursivamente al nodo hijo con el elemento de aux
            //Y con el HEI y HD correspondiente al original.
            hijo = new NodoGen(aux.getElem(), clonarAux(aux.getHijoIzquierdo()), clonarAux(aux.getHermanoDerecho()));
        }
        return hijo;
    }

    public void vaciar() {
        //Método para vaciar el Árbol genérico asignándole null a la raiz.
        this.raiz = null;
    }

    @Override
    public String toString() {
        //Llama al método toStringAux.
        return toStringAux(this.raiz);
    }

    private String toStringAux(NodoGen n) {
        String s = "";
        if (n != null) {
            //Si n no es null entonces a la cadena s se le pone el elemento n y una flecha(indicando a los hijos de dicho elemento).
            s += n.getElem().toString() + "->";
            //Se crea un nodo hijo con el nodo del HEI de n.
            NodoGen hijo = n.getHijoIzquierdo();
            while (hijo != null) {
                //Mientras hijo no sea null entonces a la cadena s se le pone el elemento de hijo junto con una coma.
                //Y después se mueve al hermano derecho de hijo. Esto hace que imprima todos los hijos del nodo n.
                s += hijo.getElem().toString() + ",";
                hijo = hijo.getHermanoDerecho();
            }
            //Cuando termina la repetitiva, a hijo se le asigna al nodo del HEI de n.
            hijo = n.getHijoIzquierdo();
            while (hijo != null) {
                //Mientras hijo no sea null entonces hace un salto de linea y llama recursivamente al método 
                //Con hijo de parámetro y después se mueve al hermano derecho de hijo.
                s += "\n" + toStringAux(hijo);
                hijo = hijo.getHermanoDerecho();
            }
        } else {
            //Si n es null entonces a la cadena s se imprime Árbol vacio.
            s = "Árbol vacío";
        }
        return s;
    }
}