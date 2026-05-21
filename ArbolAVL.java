public class ArbolAVL<T extends Comparable<T>>{

    private Nodo raiz;
    private int size;
    
    private class Nodo{
	
	T elemento;
	Nodo padre, izquierdo, derecho;
	int altura;
	
	public Nodo(T elemento){
	    this.elemento = elemento;
	    this.altura = 1;
	}
	
	public int balance(){
	    int alturaIzquierda = (izquierdo == null) ? 0 : izquierdo.altura;
	    int alturaDerecho = (derecho == null) ? 0 : derecho.altura;
	    
	    return alturaIzquierda - alturaDerecho;
	}
    }

    public T buscar(T elemento) {
	return buscar(raiz, elemento);
    }
    
    private T buscar(Nodo a, T elemento) {
	if (a == null)
	    return null;
	if (elemento.compareTo(a.elemento) == 0)
	    return a.elemento;
	if(elemento.compareTo(a.elemento) < 0) {
	    return buscar(a.izquierdo, elemento);
	} else {
	    return buscar(a.derecho, elemento);
	}
    }
    
    //Inicio de los métodos del AVL
    public void agregar(T elemento){
	this.raiz = agregar(this.raiz, elemento);
    }
    
    private Nodo agregar(Nodo actual, T elemento){
	if(actual == null){
	    size++;
	    return new Nodo(elemento);
	}
	
	if(elemento.compareTo(actual.elemento) < 0){
	    actual.izquierdo = agregar(actual.izquierdo, elemento);
	    actual.izquierdo.padre = actual;
	    
	}else if(elemento.compareTo(actual.elemento) > 0){
	    actual.derecho = agregar(actual.derecho, elemento);
	    actual.derecho.padre = actual;
	}else{
	    return actual;
	}
	
	actual.altura = this.alturaNodo(actual);
	return this.balanceo(actual); //cambiar
    }
    
    public void eliminar(T elemento){
	this.raiz = eliminar(this.raiz, elemento);
    }
    
    private Nodo eliminar(Nodo actual, T elemento){
	if(actual == null) return null;
	
	if(elemento.compareTo(actual.elemento) < 0){
	    actual.izquierdo = eliminar(actual.izquierdo, elemento);
	}else if(elemento.compareTo(actual.elemento) > 0){
	    actual.derecho = eliminar(actual.derecho, elemento);
	}else{
	    
	    if(actual.izquierdo == null && actual.derecho == null){
		size--;
		return null;
	    }else if(actual.izquierdo == null){
		size--;
		actual.derecho.padre = actual.padre;
		return actual.derecho;
	    }else if(actual.derecho == null){
		size--;
		actual.izquierdo.padre = actual.padre;
		return actual.izquierdo;
	    }else{
		Nodo sucesor = encontrarMin(actual.derecho);
		actual.elemento = sucesor.elemento;
		actual.derecho = eliminar(actual.derecho, sucesor.elemento);  
	    }
	}
	
	actual.altura = this.alturaNodo(actual);
	return this.balanceo(actual);
    }
    
    public int altura(){
	return alturaNodo(raiz);
    }
    
    private int alturaNodo(Nodo nodo){
	if(nodo == null) return 0;
	
	return 1 + Math.max(alturaNodo(nodo.izquierdo), alturaNodo(nodo.derecho));
    }
    
    public int profundidad(T elemento){
	return profundidad(raiz, elemento, 0);
    }
    
    private int profundidad(Nodo nodo, T elemento, int nivel){
	if(nodo == null) return -1;
	
	if(nodo.elemento.equals(elemento)) return nivel;
	
	if(elemento.compareTo(nodo.elemento) < 0){
	    return profundidad(nodo.izquierdo, elemento, nivel + 1);
	}else{
	    return profundidad(nodo.derecho, elemento, nivel + 1);
	}
    }
    
    
    //Metodos de rotación y otras cosas
    private Nodo rotacionIzq(Nodo x){
	Nodo y = x.derecho;
	x.derecho = y.izquierdo;
	
	if(y.izquierdo != null){
	    y.izquierdo.padre = x;
	}
	
	y.padre = x.padre;
	if(x.padre == null){
	    this.raiz = y;
	}else if(x.equals(x.padre.izquierdo)){
	    x.padre.izquierdo = y;
	}else{
	    x.padre.derecho = y;
	}
	
	y.izquierdo = x;
	x.padre = y;
	
	x.altura = this.alturaNodo(x);
	y.altura = this.alturaNodo(y);
	
	return y; //Nueva subraíz del árbol
    }
    private Nodo rotacionDer(Nodo x){
	Nodo y = x.izquierdo;
	x.izquierdo = y.derecho;
	
	if(y.derecho != null){
	    y.derecho.padre = x;
	}
	
	y.padre = x.padre;
	if(x.padre == null){
	    this.raiz = y;
	}else if(x.equals(x.padre.izquierdo)){
	    x.padre.izquierdo = y;
	}else{
	    x.padre.derecho = y;
	}
	
	y.derecho = x;
	x.padre = y;
	
	x.altura = this.alturaNodo(x);
	y.altura = this.alturaNodo(y);
	
	return y; //nueva raíz del subárbol kjrfajlajkl
    }
    
    private Nodo balanceo(Nodo nodo){
	int balance = nodo.balance();
	
	if(balance > 1 && nodo.izquierdo.balance() >= 0){
	    return rotacionDer(nodo);
	}
	
	if(balance < -1 && nodo.derecho.balance() <= 0){
	    return rotacionIzq(nodo);
	}
	
	if(balance > 1 && nodo.izquierdo.balance() < 0){
	    nodo.izquierdo = rotacionIzq(nodo.izquierdo);
	    return rotacionDer(nodo);
	}
	
	if(balance < -1 && nodo.derecho.balance() > 0){
	    nodo.derecho = rotacionDer(nodo.derecho);
	    return rotacionIzq(nodo);
	}
	return nodo;
    }
    
    private Nodo encontrarMin(Nodo nodo){
	while(nodo.izquierdo != null){
	    nodo = nodo.izquierdo;
	}
	return nodo;
    }
}
