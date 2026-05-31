import java.util.Scanner;
import java.text.Normalizer;
import java.util.Random;

public class Palabras {

    private Scanner sc;
    private Random rd;
    private static final char[] ALFABETO = {'A', 'A', 'A', 'B', 'C', 'D', 'E', 'E', 'E', 'F', 'G', 'H', 'I', 'I',
					    'I', 'J', 'K', 'L', 'M', 'N', 'Ñ', 'O', 'O', 'O', 'P', 'Q', 'R', 'S',
					    'T', 'U', 'U', 'U', 'V', 'W', 'X', 'Y', 'Z'};
    private int[] inventarioLetras;
    

    /**
     * Hay que aumentar la probabilidad de las vocales
     * Actualmente si se generan de manera aleatoria, pero
     * la cantidad de vocales es infima en comparacion con
     * las consonantes, tal vez metiendo mas vocales en la
     * variable ALFABETO
     */
    
    public Palabras() {
        sc = new Scanner(System.in);
        rd = new Random();
    }

    public String secuenciaComputadora() {
        StringBuilder stb = new StringBuilder();

        while(stb.length() != 9) {
            stb.append(ALFABETO[rd.nextInt(ALFABETO.length)]);
        }
        String secuencia = stb.toString();
        
        //esto hace el que el arreglo sea de minusculas
        String secuenciaLimpia = normalizar(secuencia);

        generaInventario(secuenciaLimpia);

        // esto hace que en terminal salga en mayusculas, pq se ve feo en minus
        return secuencia;
    }
    
    public String pideSecuencia() {
        String s = "";
        String sPrima = "";
        
        while(true) {
            System.out.println("Ingresa la secuencia de letras (9 letras):");
            s = sc.nextLine();
            sPrima = normalizar(s);

            if (secuenciaValida(sPrima)) {
                    break;
                }
            System.out.println("Secuencia invalida (deben ser exactamente 9 letras)");
        }
        generaInventario(sPrima);
            return sPrima.toUpperCase();
    }

    private void generaInventario(String s) {
        this.inventarioLetras = new int[27];
        for (int i = 0; i < s.length(); i++) {
            char a = s.charAt(i);
            int b = calculaIndice(a);
            if (b != -1)
            this.inventarioLetras[b]++;
        }
	
    }

    public boolean sePuedeFormar(String intento) {
        String intentoLimpio = normalizar(intento);
        int[] inventarioTemporal = this.inventarioLetras.clone();
        
        for (int i = 0; i < intentoLimpio.length(); i++) {
            char letra = intentoLimpio.charAt(i);
            int indice = calculaIndice(letra);
            
            if (indice == -1) return false; 
            
            inventarioTemporal[indice]--;
            if (inventarioTemporal[indice] < 0) {
                return false; 
            }
        }
        return true;
    }

    private int calculaIndice(char c) {
        if (c >= 'a' && c <= 'n') {
                return c - 'a';         
            } else if (c == 'ñ') {
                return 14;              
            } else if (c >= 'o' && c <= 'z') {
                return (c - 'o') + 15;  
            }
            return -1;
    }
    
    public void cerrarScanner() {
        if (sc != null) {
            sc.close();
        }
    }
    
    private boolean secuenciaValida(String s) {
        if (s == null)
            throw new IllegalArgumentException("No aceptamos entradas nulas, bobo");
        
	    return s.length() == 9; 
    }

    private String normalizar(String letras) {
        if (letras == null)
            return "";
        String s = letras.trim().toLowerCase();
        
        String sPrima = Normalizer.normalize(s, Normalizer.Form.NFD);

        StringBuilder stb = new StringBuilder();

        for (int i = 0; i < sPrima.length(); i++) {

            char a = sPrima.charAt(i);
            
            if (a == '\u0303') {
            if (stb.length() > 0) {
                        stb.deleteCharAt(stb.length() - 1);
                    }
            stb.append('ñ');
            
            }
            else if (a >= 'a' && a <= 'z') { 
                    stb.append(a);
                }
        }
        return stb.toString();
    }
}
