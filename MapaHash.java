public class MapaHash<K extends Comparable<K>, V> {
    //expresiones regulares

    private class Entrada implements Comparable<Entrada>{
		private K llave;
		private	 V valor;

		public Entrada(K llave, V valor, boolean estado) {
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

    
    
     
}
