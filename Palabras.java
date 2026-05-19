import java.util.Scanner;
import java.text.Normalizer;
import java.util.Random;

public class Palabras {

    private Scanner sc;
    private Random rd;
    public static final char[] ALFABETO = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
					   'K', 'L', 'M', 'N', 'Ñ', 'O', 'P', 'Q', 'R', 'S',
					   'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

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
	return stb.toString();
    }
    
    public String pideSecuencia() {
	String s = "";
	
	while(!secuenciaValida(s)) {
	    if (!s.isEmpty()) 
		System.out.println("Secuencia de letras invalida. Intenta de nuevo.");
	    
	    System.out.println("Ingresa la secuencia de letras (9 letras)");
	    s = sc.nextLine().trim();
	}
	return normalizar(s);
    }
    
    public void cerrarScanner() {
        if (sc != null) {
            sc.close();
        }
    }
    
    private boolean secuenciaValida(String s) {
	if (s == null)
	    throw new IllegalArgumentException("No aceptamos entradas nulas, bobo");
	if (s.length() != 9)
	    return false;
	
	for(char a: s.toCharArray()) {
	    if (!Character.isLetter(a))
		return false;
	}
	return true;
    }

    private String normalizar(String s) {
	if (s == null)
	    return "";
	String sPrima = Normalizer.normalize(s, Normalizer.Form.NFD);

	StringBuilder stb = new StringBuilder();

	for (char a : sPrima.toCharArray()) {
	    if (Character.isLetter(a))
		stb.append(Character.toUpperCase(a));
	}
	return stb.toString();
    }
}
