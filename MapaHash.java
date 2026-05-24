public class MapaHash<K extends Comparable<K>, V> {
    //expresiones regulares

    private class Entrada implements Comparable<Entrada>{
	K llave;
	V valor;
	
	public Entrada(K llave, V valor) {
	    this.llave = llave;	
	    this.valor = valor;
	}
	
	@Override
	public int compareTo(Entrada otraEntrada){
	    return this.llave.compareTo(otraEntrada.llave);
	}
	
	@Override
	public String toString(){
	    return "{ " + llave + ": " + valor + " }";
	}
    }
    
    private ArbolAVL<Entrada>[] entradas;
    private int elementos;
    
    public MapaHash() {
	this.elementos = 0;
	this.entradas = inicializarTabla();
    }
    
    /**
     * La verdad, no se si esto funcione, ya que
     * hay demasiadas cosas que estoy suponiendo.
     * La idea es que cada casilla del arreglo
     * corresponda a dos letras. Ej
     * Casilla 0 corresponde a (Aa), 1 a (Ab), ...
     * etc
     * por eso el tamano de 27x27 = 729
     * por cada letra hay 27 combinaciones
     */

    @SuppressWarnings("unchecked")
    private ArbolAVL<Entrada>[] inicializarTabla() {
	
	ArbolAVL<Entrada>[] lista = (ArbolAVL<Entrada>[]) new ArbolAVL[729];
	
	for(int i = 0; i < 729; i++) {
	    lista[i] = new ArbolAVL<Entrada>();
	}
	return lista;
    }

    
    private int obtenerValorLetra(char c) {
        c = Character.toUpperCase(c);
        
        switch (c) {
	case 'Á': case 'À': case 'Â': case 'Ä': 
	    c = 'A'; break;
	case 'É': case 'È': case 'Ê': case 'Ë': 
	    c = 'E'; break;
	case 'Í': case 'Ì': case 'Î': case 'Ï': 
	    c = 'I'; break;
	case 'Ó': case 'Ò': case 'Ô': case 'Ö': 
	    c = 'O'; break;
	case 'Ú': case 'Ù': case 'Û': case 'Ü': 
	    c = 'U'; break;
	case 'Ç': 
	    c = 'C'; break;
        }
	if (c >= 'A' && c <= 'N') {
            return c - 'A';
        } else if (c == 'Ñ') {
            return 14; 
        } else if (c >= 'O' && c <= 'Z') {
            return (c - 'O') + 15;
        }
        return 0;
    }

    private int calcularIndice(K llave) {
        String s = llave.toString();
        
        if (s == null || s.isEmpty()) {
            return 0;
        }
	// hola -> h
	return (obtenerValorLetra(s.charAt(0)) * 27) + 
               obtenerValorLetra((s.length() > 1) ? s.charAt(1) : 'A');
	// hola -> o , si solo fuera la pura 'a', seria 'A' que es 0
    }

    // Como mierda se calcula la llave
    public void insertar(K llave, V valor) {
        if (llave == null) return;
	
        Entrada nuevaEntrada = new Entrada(llave, valor);
	int indice = calcularIndice(llave);
	
	if (this.entradas[indice].buscar(nuevaEntrada) == null) {
	    this.entradas[indice].agregar(nuevaEntrada);
	    this.elementos++;
	}
    }
    
    public int getElementos() {
        return this.elementos;
    }
    
    public V buscar(K llave) {
	if (llave == null)
	    return null;
	
        Entrada resultado = this.entradas[calcularIndice(llave)].buscar(new Entrada(llave, null));   
	
	return (resultado != null) ? resultado.valor : null;
    }    
     
}
