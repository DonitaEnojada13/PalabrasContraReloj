import java.util.Scanner;
import java.io.File;

public class Controlador{

    private Scanner in;
    //Aquí no sé si el diccionario debe de ir en el hash map de arboles o en un árbol aparte
    private MapaHash<String, Integer> diccionario;
    private String secuenciaActual;
    private Palabras gestorPalabras;
    private Estadisticas estadisticas;
    private Puntuacion puntuacionTotal;

    public Controlador(){
        this.in = new Scanner(System.in);
        this.diccionario = new MapaHash<>();
        this.gestorPalabras = new Palabras();
        this.puntuacionTotal = new Puntuacion();
        this.estadisticas = new Estadisticas();
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

       CargadorDiccionario cargador = null;

        while (cargador == null || !cargador.esUsable()) {
            System.out.print("Ingresa la ruta del directorio con el diccionario: ");
            String ruta = in.nextLine().trim();

            try {
                cargador = new CargadorDiccionario(ruta);
                if (!cargador.esUsable()) {
                    System.out.println("La ruta no existe o no es un directorio valido. Intenta de nuevo.");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Ruta invalida: " + e.getMessage());
            }
        }

        File[] archivos = cargador.listadoDocs();

        if (archivos.length == 0) {
            System.out.println("No se encontraron archivos .txt en esa ruta. El programa no puede continuar.");
            System.exit(1);
        }

        File diccionarioElegido;

        if (archivos.length == 1) {
            diccionarioElegido = archivos[0];
            System.out.println("Diccionario encontrado: " + diccionarioElegido.getName());
        } else {
            System.out.println("\nArchivos disponibles:");
            for (int i = 0; i < archivos.length; i++) {
                System.out.println("  " + i + ". " + archivos[i].getName());
            }

            int eleccion = -1;
            while (eleccion < 0 || eleccion >= archivos.length) {
                System.out.print("Elige el numero del diccionario a usar: ");
                try {
                    eleccion = Integer.parseInt(in.nextLine().trim());
                } catch (NumberFormatException e) {
                    System.out.println("Ingresa un numero valido.");
                }
            }
            diccionarioElegido = cargador.eligeDiccionario(archivos, eleccion);
        }

        LectorDiccionario lector = new LectorDiccionario();
        lector.preparaMapa(diccionario, diccionarioElegido);
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
        //long tiempoLimite = pedirTiempo();
        long tiempoLimite = 3 * (60 * 1000);

        System.out.println("Inicia la partida. Ingresar palabras: ");

        Cronometro cronometro = new Cronometro(tiempoLimite);
        Thread deamonCronometro = new Thread();
        deamonCronometro.setDaemon(true);
        deamonCronometro.start();

        while(!cronometro.haTerminado()){
            String palabra;
            System.out.println("Tiempo Restante: " + cronometro.tiempoRestante());
            System.out.println("Ingresa Palabra: ");
            try{
                palabra = in.nextLine();                
            }catch(Exception e){
                break;
            }

            if(cronometro.haTerminado()){
                System.out.println("TIEMPO TERMINADO :(");
                break;
            }

            procesarPalabras(palabra);
        }
    }

    /* private long pedirTiempo(){
        long minutos = 0;
        int minMinimos = 1;
        int minMaximos = 10; // se queda a cambio;
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
    } */

    //Sería la parte de la normalización de las letras de que todo pase a minúsculas y así 
    //tanto esta como la de validarConSecuencia ocupamos lo de la clase Palabras no? 
    private void procesarPalabras(String entrada){
        if (entrada.isEmpty()) return;

        if (!validarConSecuencia(entrada)) {
            System.out.println("  [!] '" + entrada + "' no se puede formar con las letras de tu secuencia.");
            return;
        }

        String normalizada = normalizarParaDiccionario(entrada);

        Integer valor = diccionario.buscar(normalizada);

        if (valor == null) {
            System.out.println("  [!] '" + entrada + "' no esta en el diccionario.");
            return;
        }

        boolean nueva = puntuacionTotal.metePalabra(normalizada, valor);

        if (!nueva) {
            System.out.println("  [!] '" + entrada + "' ya la habias ingresado.");
        } else {
            System.out.println("  [+] '" + entrada + "' vale " + valor + " puntos! Total: " + puntuacionTotal.getPuntuacionTotal());
        }
    }

    private boolean validarConSecuencia(String entrada) {
        return gestorPalabras.sePuedeFormar(entrada);
    }

    private String normalizarParaDiccionario(String s) {
        return s.trim().toLowerCase();
    }

    private void mostrarResultado(){
        System.out.println("FIN DEL JUEGO :D");
        System.out.println("Tu puntuación es de: " + puntuacionTotal);
    }

    private void restaurarEstado(){
        this.puntuacionTotal = new Puntuacion();
        this.secuenciaActual = null;
    }

    private void guardarEstadistica() {
        estadisticas.registrarPuntaje(secuenciaActual, puntuacionTotal.getPuntuacionTotal());
    }


}