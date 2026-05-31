import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.IOException;


public class LectorDiccionario {

    /**
     * Clase encargada de leer el diccionario
     * y, posteriormente, agregarla a una estructura de datos
     * auxiliar (yo creo que seria un mapa hash o algo asi), a
     * ver si me sale esto, sigo sin entender a ciencia cierta
     * lo que son lo buffer reader y esas cosas
     */

    public void preparaMapa(MapaHash<String, Integer> estructura, File diccionario) {
        try (BufferedReader lector = new BufferedReader(new FileReader(diccionario))) {
                String linea;

            while ((linea = lector.readLine()) != null) {
                    detectaLinea(linea, estructura);
                }
            System.out.println("Se cargo el diccionario");
                System.out.println("Total de palabras: " + estructura.getElementos());

            } catch (IOException e) {
                System.err.println("Error de tipo: " + e.getMessage());
            }
    }
    
    private void detectaLinea(String s, MapaHash<String, Integer> estructura) {

        String sPrima = s.trim().toLowerCase();

        if (palabraValida(sPrima)) {
            int v = calculaValor(sPrima.length());
            estructura.insertar(sPrima,v);
        }
    }
    
    private int calculaValor(int tamano) {
    	return tamano * tamano;
    }
    
    private boolean palabraValida(String s) {
        if (s == null || s.isEmpty())
            return false;
        int largo = s.length();
        
        if (largo < 1 || largo > 9)
            return false;
        
        for (int i = 0; i < largo; i++) {
            char c = s.charAt(i);
            if (!Character.isLetter(c))
            return false;
        }
            return true;
    }
    
}
