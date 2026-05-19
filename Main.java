public class Main {
    public static void main(String[] args) {

	Palabras juego = new Palabras();
	String alfa = juego.pideSecuencia();
	System.out.println("Las letras a  usar son: " + alfa);

	String beta = juego.secuenciaComputadora();
	System.out.println("Las letras a random son: " + beta);


	juego.cerrarScanner();
	System.out.println("Programa terminado y recursos liberados.");

    }
}
