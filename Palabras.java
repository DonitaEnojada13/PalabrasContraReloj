import java.util.Scanner;
import java.text.Normalizer;


public class Palabras {

    private Scanner sc;
    
    public Palabras() {
	sc = new Scanner(System.in);
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
