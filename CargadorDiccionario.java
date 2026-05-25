import java.io.File;

public class CargadorDiccionario {

    private File ruta;

    public CargadorDiccionario(String nRuta) {
	if (nRuta == null)
            throw new IllegalArgumentException("La ruta no pude ser null");
        if(nRuta.isEmpty())
            throw new IllegalArgumentException("La ruta no pude ser vacia");	
        this.ruta = new File(nRuta);
    }
    
    public boolean esUsable() {
	return ruta.exists() && ruta.isDirectory();
    }

    /**
     *
     * Ahora bien, en teoria esta clase solo debe de leer la ruta,
     * pero no se si sea necesario hacer un arreglo de archivos
     * de tipo .txt, ya que solo se debera cargar un diccionario
     * (De todas maneras lo hare, ya que pueden existir varios .txt).
     *
     */

    public File[] listadoDocs() {
        if (!esUsable())
            return new File[0];
	
	File[] temp = ruta.listFiles(archivo -> archivo.isFile() && archivo.getName().toLowerCase().endsWith(".txt"));

	if (temp == null)
	    return new File[0];
	return temp;
    }

    /**
     * Con este metodo tendremos que elegir que documento .txt
     * se usara para el diccionario, ya que estoy pensando en
     * el caso de tener mas documentos de diccionario.
     */

    public File eligeDiccionario(File[] arr, int i) {
	if (arr == null)
	    throw new IllegalArgumentException("No se aceptan arreglos nulos, bobo");

	if (arr.length == 0)
	    throw new IllegalStateException("No hay diccionarios disponibles en la ruta.");

	if (i < 0 || i >= arr.length)
	    throw new IndexOutOfBoundsException();

	return arr[i];	
    }
    
}
