import java.util.Scanner;
import java.text.Normalizer;


public class Palabras {

    private Scanner sc;
    
    pblic Palabras() {
	sc = new Scanner(System.in);
    }
    
    public String pideSecuencia() {
	String s = "";

	while(s.isEmpty()) {
	    System.out.println("Ingresa las letras que usaras (9 letras)");
	}
    }

    private boolean secuenciaValida(String s) {

	if (s == null)
	    throw new IllegalArgumentException("No aceptamos strings nulos");
	if (s.isEmpty() || s.length() != 9)
	    return false;
	for(char a: s.toCharArray()) {
	    if(!Character.isLetter(a))
		return false;
	}
	return true;
    }

    private String normalizar(String s) {
	if (s == null)
	    return "";
	String sPrima = Normalizer.normalize(s, Normalizer.Form.NFD);
	
    }
}
