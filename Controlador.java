import java.util.Scanner;

public class Controlador{

    private Scanner in;
    //Aquí no sé si el diccionario debe de ir en el hash map de arboles o en un árbol aparte
    private MapaHash<String, Boolean> diccionario;
    private MapaHash<String, Boolean> palabrasIngreso;
    private Estadisticas estadisticas;
    private String secuenciaActual;
    private int puntuacionTotal;

    public Controlador(){
        this.in = new Scanner(System.in);
        this.diccionario = new MapaHash<>();
        this.palabrasIngreso = new MapaHash<>();
        this.estadisticas = new Estadisticas();
        this.puntuacionTotal = 0;
    }

    public void inicio(){

    }

    private void cargarDiccionario(){

    }

    //Para que el usuario elija si la secuencia de letras sea random o escritas por él
    private void configurarSecuencia(){

    }

    private void jugarPartida(){

    }

    //Sería la parte de la normalización de las letras de que todo pase a minúsculas y así 
    //tanto esta como la de validarConSecuencia ocupamos lo de la clase Palabras no? 
    private void procesarPalabras(){

    }

    private boolean validarConSecuencia(){
        return false;
    }

    private void mostrarResultado(){

    }

    private void guardarEstadistica(){

    }

    private void restaurarEstado(){
        
    }


}