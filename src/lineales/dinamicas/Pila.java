package lineales.dinamicas;

import lineales.dinamicas.Nodo;

public class Pila {

    private Nodo tope;

    public Pila() {
        this.tope = null;
    }

    public boolean apilar(Object nuevoElem) {
        
        //Crea un nuevo Nodo delante de la antigua cabecera.
        Nodo nuevo = new Nodo(nuevoElem, this.tope);
        
        //Actualiza el tope para que apunte al nodo nuevo.
        this.tope = nuevo;
        
        //Nunca hay error de pila llena, entonces devuelve true.
        return true;
    }
    
    public boolean desapilar(){
        boolean exito;
        
        if(this.tope != null){
            //Si la pila no está vacía, entonces el tope accede al siguiente enlace
            //Dejando que Garbage Collector se lo lleve y devuelve true.
            this.tope= this.tope.getEnlace();
            exito = true;
        }else{
            //Si la pila está vacía, entonces devuelve false.
            exito= false;
        }
             
        return exito;
    }
    
    public Object obtenerTope(){
        Object elem;
        
        if(this.tope != null){
            //Si la pila no está vacía entonces elem recibe el elemento de tope.
            elem= this.tope.getElem();          
        }else{
            //En caso contrario, elem devuelve null.
            elem = null;   
        }
        return elem;
    }
    
    public boolean esVacia(){
        boolean vacio;
        
        if(this.tope == null){
            //Si la pila está vacía entonces devuelve true.
            vacio = true;
        }else{
            //En caso contrario devuelve false.
            vacio = false;
        }
        return vacio;
    }
    
    public void vaciar(){
        //Pone el tope en null dejando así que el Garbage Collector haga su trabajo.
        this.tope = null;
        
    }
    
    public Pila clone(){
        //Creo una nueva pila
        Pila clon = new Pila();
        
        //Llama al método privado con la pila creada anteriormente y 
        //el tope de la pila actual.
        this.cloneRecursivoPaso(clon,this.tope);
        return clon;
    }
    
    private void cloneRecursivoPaso(Pila pilaClon, Nodo enlace){
        if(enlace != null){
            //Si el nodo no es vacío entonces creo un nuevo nodo "enlaceTope" en el
            //Que le asigno el siguiente nodo de "enlace".
            Nodo enlaceTope = enlace.getEnlace();
            
            //LLama recursivamente al método con la pila clonada y "enlaceTope".
            cloneRecursivoPaso(pilaClon, enlaceTope);
            
            //El tope de la pilaClon va a ser con el elemento de "enlace" y va a estar enlazado.
            pilaClon.tope = new Nodo(enlace.getElem(), pilaClon.tope);
        }
    }
    
    @Override
    public String toString() {
        String s = "";

        if (this.tope == null) {
            s = "Pila Vacía";
        } else {
            //Se ubica para recorrer la pila.
            Nodo aux = this.tope;
            s = "[";

            while (aux != null) {
                //Agrega el texto del elem y avanza.
                s += aux.getElem().toString();                
                aux = aux.getEnlace();
                
                if (aux != null) {
                    s += ",";
                }                
            }
            s += "]";
        }
        return s;
    }
}