public class Puntuacion {
    private int puntuacionTotal;
    private int totalPalabras;
    private ArbolAVL palabrasJuego;

    public Puntuacion() {
	this.palabrasJuego = new ArbolAVL<String>;
    }
    
    public int getPuntuacionTotal() {
        return this.puntuacionTotal;
    }

    public int getTotalPalabras() {
        return this.totalPalabras;
    }

    public boolean metePalabra(String s, int v) {
	if(s == null)
	    throw new IllegalArgumentException("Un string nulo, que paso aqui");
	if (palabrasJuego.contains(s))
	    return false;
	
	palabrasJuego.agregar(s);
	puntuacionTotal += v;
	totalPalabras++;
	return true;
    }

    public void imprimePalabras() {
	System.out.println("\nPalabras que metiste: ");

	palabrasJuego.dfsInorden();
    }

    
    
}
