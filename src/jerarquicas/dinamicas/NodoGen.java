package jerarquicas.dinamicas;

class NodoGen {
    //Atributos:
    private Object elem;
    private NodoGen hijoIzquierdo;
    private NodoGen hermanoDerecho;
    
    //Constructor:
    public NodoGen(Object elemento, NodoGen hI, NodoGen herD){
        this.elem = elemento;
        this.hijoIzquierdo = hI;
        this.hermanoDerecho = herD;
    }
    
    public Object getElem(){
        return this.elem;
    }
    
    public NodoGen getHijoIzquierdo(){
        return this.hijoIzquierdo;
    }
    
    public NodoGen getHermanoDerecho(){
        return this.hermanoDerecho;
    }
    
    public void setElem(Object elemento){
        this.elem = elemento;
    }
    
    public void setHijoIzquierdo(NodoGen hI){
        this.hijoIzquierdo = hI;
    }
    
    public void setHermanoDerecho(NodoGen herD){
        this.hermanoDerecho = herD;
    }
}