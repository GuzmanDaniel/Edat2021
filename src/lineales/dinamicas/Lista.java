package lineales.dinamicas;

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
                Nodo aux = this.cabecera;
                int i = 1;
                while (i < pos - 1) {
                    aux = aux.getEnlace();
                    i++;
                }
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

        if ((pos < 1) || (pos > this.longitud())) {
            exito = false;
        } else {
            if (pos == 1) {               
                this.cabecera = this.cabecera.getEnlace();
                this.longitud--;
            } else {
                Nodo aux = this.cabecera;
                int i = 1;
                while (i < pos - 1) {
                    aux = aux.getEnlace();
                    i++;
                }
                aux.setEnlace(aux.getEnlace().getEnlace());
                this.longitud--;
            }
        }
        return exito;
    }

    public Object recuperar(int pos) {
        Object elem;
        
        if ((pos < 1) || (pos > this.longitud())) {
            elem = null;
        } else {
            Nodo aux = this.cabecera;
            int i = 1;
            while (i != pos) {
                aux = aux.getEnlace();
                i++;
            }
            elem = aux.getElem();
        }
        return elem;
    }

    public int localizar(Object nuevoElem) {
        int pos = -1, i = 1, aux1 = this.longitud();
        Nodo aux = this.cabecera;
        
        
        if (aux != null) {
            while ((i < aux1) && (!nuevoElem.equals(aux.getElem()))) {
                aux = aux.getEnlace();
                i++;
            }

            if (nuevoElem.equals(aux.getElem())) {
                pos = i;
            } else {
                pos = -1;
            }
        }
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
            Nodo aux1 = this.cabecera;
            Nodo aux2;
            nuevaLista.cabecera = new Nodo(aux1.getElem(), null);
            aux2 = nuevaLista.cabecera;
            aux1 = aux1.getEnlace();
            while (aux1 != null) {
                aux2.setEnlace(new Nodo(aux1.getElem(), null));
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

    public void eliminarApariciones(Object x) {
        int lon = this.longitud(), i = 1;
        Nodo aux = this.cabecera;
        Nodo aux1 = this.cabecera;
        
        while (i <= lon) {
            if ((aux.getElem().equals(x)) && (i == 1)) {
                aux = aux.getEnlace();
                aux1 = aux;
                this.cabecera = this.cabecera.getEnlace();
            } else if (aux.getElem().equals(x)) {
                aux = aux.getEnlace();
                aux1.setEnlace(aux1.getEnlace().getEnlace());
                aux1 = aux;
            } else {
                aux = aux.getEnlace();
                aux1 = aux;
            }
            if ((aux.getElem().equals(x)) && (i == lon)) {
                aux1.setEnlace(aux1.getEnlace());
            }
            i++;
        }
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
}