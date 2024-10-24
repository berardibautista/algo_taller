package aed;
// elem1.compareTo(elem2) devuelve un entero. Si es mayor a 0, entonces elem1 > elem2

import sun.font.TrueTypeFont;

public class ABB<T extends Comparable<T>> implements Conjunto<T> {
    // Agregar atributos privados del Conjunto
    private Nodo raiz;
    private int altura;

    private class Nodo {
        // Agregar atributos privados del Nodo
        private T valor;
        private Nodo izquierdo;
        private Nodo derecho;
        private Nodo padre; 
        // Crear Constructor del nodo
        public Nodo(T valor, Nodo padre) {
            this.valor = valor;
            this.izquierdo = null;
            this.derecho = null;
            this.padre = padre;
        }
    }

    public ABB() {

        this.raiz = null;
        this.altura = 0;
    }

    public int cardinal() {
        return this.altura;
    }

    public T minimo(){
        Nodo actual = this.raiz;
        while (actual.izquierdo != null) {
            actual = actual.izquierdo;
        }
        return actual.valor;
    }

    public T maximo(){
        Nodo actual = this.raiz;
        while (actual.derecho != null) {
            actual = actual.derecho;
        }
        return actual.valor;
    }

    public void insertar(T elem){
           if (this.raiz == null) {
            this.raiz = new Nodo(elem, null);
            this.altura++;
        } else {
            insertarRecursivo(raiz, elem);
        }
    } private void insertarRecursivo(Nodo actual, T elem) {
        if (elem.compareTo(actual.valor) < 0) {
            if (actual.izquierdo == null) {
                actual.izquierdo = new Nodo(elem, actual);
                this.altura ++;
            } else {
                insertarRecursivo(actual.izquierdo, elem);
            }
        } else if (elem.compareTo(actual.valor) > 0) {
            if (actual.derecho == null) {
                actual.derecho = new Nodo(elem, actual);
                this.altura ++;
            } else {
                insertarRecursivo(actual.derecho, elem);
            }
        }
    }
    public boolean pertenece(T elem){
          return perteneceRecursivo(raiz, elem);
    }
    private boolean perteneceRecursivo(Nodo actual, T elem) {
        if (actual == null) {
            return false;
        }
        int comparacion = elem.compareTo(actual.valor);
        if (comparacion == 0) {
            return true;
        } else if (comparacion < 0) {
            return perteneceRecursivo(actual.izquierdo, elem);
        } else {
            return perteneceRecursivo(actual.derecho, elem);
        }
    }
    

    public void eliminar(T elem){
          raiz = eliminarRecursivo(raiz, elem);
}

private Nodo eliminarRecursivo(Nodo actual, T elem) {
    if (actual == null) {
        return null;
    }

    int comparacion = elem.compareTo(actual.valor);
    if (comparacion < 0) {
        actual.izquierdo = eliminarRecursivo(actual.izquierdo, elem);
    } else if (comparacion > 0) {
        actual.derecho = eliminarRecursivo(actual.derecho, elem);
    } else {
        // Nodo encontrado, procedemos a eliminarlo

        // Caso 1: Nodo sin hijos izquierdos
        if (actual.izquierdo == null) {
            altura--; // Disminuir el tamaño
            return actual.derecho;
        }
        // Caso 2: Nodo sin hijos derechos
        else if (actual.derecho == null) {
            altura--; // Disminuir el tamaño
            return actual.izquierdo;
        } else {
            // Caso 3: Nodo con dos hijos, reemplazar por el sucesor (el menor del subárbol derecho)
            Nodo sucesor = minimo(actual.derecho);
            actual.valor = sucesor.valor;
            actual.derecho = eliminarRecursivo(actual.derecho, sucesor.valor);
        }
    }
    return actual;
}

private Nodo minimo(Nodo actual) {
    while (actual.izquierdo != null) {
        actual = actual.izquierdo;
    }
    return actual;
}

    public String toString(){
        return "{" + toStringRecursivo(raiz).trim() + "}";
}

    private String toStringRecursivo(Nodo actual) {
    if (actual == null) {
        return "";
    }

   
    String izquierdo = toStringRecursivo(actual.izquierdo);
    String derecho = toStringRecursivo(actual.derecho);
    String resultado = "";
    
    if (!izquierdo.isEmpty()) {
        resultado += izquierdo + ",";
    }
    
    resultado += actual.valor;
    
    if (!derecho.isEmpty()) {
        resultado += "," + derecho;
    }
    
    return resultado;
}


    private class ABB_Iterador implements Iterador<T> {
        private Nodo _actual;
        
        
        public boolean haySiguiente() {            
            if (_actual.izquierdo != null){
            return true;
            }
            if (_actual.derecho != null){
            return true;
            }
            else{
            return false;
            }
        }
    
        public T siguiente() {
          
        }
    }

    public Iterador<T> iterador() {
        return new ABB_Iterador();
    }

}
