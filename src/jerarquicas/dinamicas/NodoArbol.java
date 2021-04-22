/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jerarquicas.dinamicas;

/**
 *
 * @author DanielPatricio
 */
class NodoArbol {
    //Atributos:
    private Object elem;
    private NodoArbol izquierdo;
    private NodoArbol derecho;
    
    //Constructor:
    public NodoArbol(Object nuevoElem, NodoArbol izq, NodoArbol der){
        this.elem = nuevoElem;
        this.izquierdo = izq;
        this.derecho = der;
    }
    //Observadores:
    public Object getElem(){
        return elem;
    }
    
    public NodoArbol getIzquierdo(){
        return izquierdo;
    }
    
    public NodoArbol getDerecho(){
        return derecho;
    }
    //Modificadores:
    public void setElem(Object elem){
        this.elem = elem;
    }
    
    public void setIzquierdo(NodoArbol izquierdo){
        this.izquierdo = izquierdo;
    }
    
    public void setDerecho(NodoArbol derecho){
        this.derecho = derecho;
    }
}