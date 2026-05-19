public class ArbolBinarioRojinegro <T extends Comparable<T>>  {

    /**
     * Logica propuesta para esta clase:
     * un arbol binario autobal ordenado
     * de palabras que las vaya acomodando
     * de manera lexicogarficamente, asi
     * tenemos una busqueda en tiempo log
     *
     */

    /**
     * Cada vertice sera una tupla de tipo
     * -String con la palabra
     * -booleano de palabara activada
     * (Si la palabra ya fue introducida, se pone en true)
     * esto para facilitar la validacion al momento de
     * jugar contrareloj
     */
    private class VerticeRn {
	
	public T elemento;
	public Color color;
	public boolean estado;
	
	public VerticeRn padre;
	public VerticeRn izquierdo;
	public VerticeRn derecho;
    
}
