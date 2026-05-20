public class MapaHash<K, V> {
    //expresiones regulares

    private class Entrada {
	public K llave;
	public V valor;

	public Entrada(K llave, V valor, boolean estado) {
	    this.llave = llave;
	    this.valor = valor;
	}
    }

    private ArbolAVL<Entrada>[] entradas;
    private int elementos;

    
    
     
}
