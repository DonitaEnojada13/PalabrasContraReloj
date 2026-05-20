public class LectorDiccionario {

    /**
     * Clase encargada de leer el diccionario
     * y, posteriormente, agregarla a una estructura de datos
     * auxiliar (yo creo que seria un mapa hash o algo asi), a
     * ver si me sale esto, sigo sin entender a ciencia cierta
     * lo que son lo buffer reader y esas cosas
     */

    private boolean palabraValida(String s) {
	if (s == null)
	    throw new IllegalArgumentException("Un string vacio, que brujeria es esta");
	if (s.isEmpty() || s.length() > 9)
	    return false;
	
    }
}
