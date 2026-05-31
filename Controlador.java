import java.util.Scanner;
import java.io.File;

public class Controlador{

    private Scanner in;
    //Aquí no sé si el diccionario debe de ir en el hash map de arboles o en un árbol aparte
    private MapaHash<String, Integer> diccionario;
    private MapaHash<String, Boolean> palabrasIngreso;
    private Estadisticas estadisticas;
    private String secuenciaActual;
    private Palabras gestorPalabras;
    private int puntuacionTotal;

    public Controlador(){
        this.in = new Scanner(System.in);
        this.diccionario = new MapaHash<>();
        this.palabrasIngreso = new MapaHash<>();
        this.estadisticas = new Estadisticas();
        this.gestorPalabras = new Palabras();
        this.puntuacionTotal = 0;
    }

    public void inicio(){

        System.out.println("Iniciando el juegazo :p");
        cargarDiccionario();

        boolean jugar = true;
        while(jugar){
            configurarSecuencia();
            jugarPartida();
            mostrarResultado();

            System.out.println("¿Deseas jugar otra vez? s/n" );
            if(!in.nextLine().trim().equalsIgnoreCase("s")){
                jugar = false;
            }
            restaurarEstado();
        }
        gestorPalabras.cerrarScanner();
    }

    private void cargarDiccionario(){
        System.out.println("Cargando Diccionario ...");

        LectorDiccionario lector = new LectorDiccionario();

        File archivoTxt = new File("diccionario.txt");

        if(archivoTxt.exists()){
            lector.preparaMapa(this.diccionario, archivoTxt);
        }else{
            System.err.println("No hay archivos existentes pa");
        }
    
    }

    //Para que el usuario elija si la secuencia de letras sea random o escritas por él
    private void configurarSecuencia(){

        String opcion = "0";
        do{
            System.out.println("1.- Secuencia por computadora \n2.- Secuencia por typo ");
            opcion = in.nextLine();
        }while(!(opcion.equals("1")) || !(opcion.equals("2")));

        if(opcion.equals("1")){
            secuenciaActual = gestorPalabras.secuenciaComputadora();
        }else{
            secuenciaActual = gestorPalabras.pideSecuencia();
        }
        System.out.println("La secuencia actual es: " + secuenciaActual);
    }

    private void jugarPartida(){
        long tiempoLimite = pedirTiempo();

        System.out.println("Inicia la partida. Ingresar palabras: ");

        Cronometro cronometro = new Cronometro(tiempoLimite);
        Thread deamonCronometro = new Thread();
        deamonCronometro.setDaemon(true);
        deamonCronometro.start();

        while(!cronometro.haTerminado()){
            System.out.println("Tiempo Restante: " + cronometro.tiempoRestante());
            System.out.println("Ingresa Palabra: ");
            String palabra = in.nextLine();

            if(cronometro.haTerminado()){
                System.out.println("TIEMPO TERMINADO :(");
                break;
            }

            procesarPalabras(palabra);
        }
    }

    private long pedirTiempo(){
        long minutos = 0;
        int minMinimos = 1;
        int minMaximos = 5; // se queda a cambio;
        String minutosPedidos;

        while(true){
            System.out.println("¿Cuántos min. deseas jugar? Puede ser desde 1 min a " + minMaximos + " minutos");

            minutosPedidos = in.nextLine();

            try{
                minutos = Long.parseLong(minutosPedidos);

                if(minutos >= minMinimos && minutos <= minMaximos){
                    break;
                }else{
                    System.out.println("Error en la entrada del tiempo");
                }

            }catch(NumberFormatException e){
                System.out.println("SOLO SE ACEPTAN NUMEROS ENTEROS");
            }
        }
       return minutos * 60 * 1000;
    }

    //Sería la parte de la normalización de las letras de que todo pase a minúsculas y así 
    //tanto esta como la de validarConSecuencia ocupamos lo de la clase Palabras no? 
    private void procesarPalabras(String palabra){
        String palabraNorma = palabra.trim().toLowerCase();

        if(palabrasIngreso.buscar(palabraNorma) != null){
            System.out.println("Palabra ya ingresada. No hay puntuación");
            return;
        }

        if(!gestorPalabras.sePuedeFormar(palabraNorma)){
            System.out.println("No se pueden formar la palabra con las letras dadas. No hay puntiación");
            return;
        }

        Integer puntos = diccionario.buscar(palabraNorma);
        if(puntos != null){
            System.out.println("Palabra valida, se agregan " + puntos + " puntos");
            puntuacionTotal += puntos;
            palabrasIngreso.insertar(palabraNorma, true);
        }else{
            System.out.println("No existe en el diccionario pa. No hay puntuación");
        }

    }

    private void mostrarResultado(){
        System.out.println("FIN DEL JUEGO :D");
        System.out.println("Tu puntuación es de: " + puntuacionTotal);
    }

    private void restaurarEstado(){
        puntuacionTotal = 0;
        palabrasIngreso = new MapaHash<>();
    }


}