import java.util.Scanner;
import java.io.File;

public class Controlador {

    private Scanner in;

    private MapaHash<String, Integer> diccionario;

    private Palabras palabras;

    private Puntuacion puntuacion;

    private Estadisticas estadisticas;

    private String secuenciaActual;

    public Controlador() {
        this.in = new Scanner(System.in);
        this.diccionario = new MapaHash<>();
        this.palabras = new Palabras();
        this.estadisticas = new Estadisticas();
    }

    public void inicio() {
        System.out.println("=== PALABRAS CONTRARRELOJ ===\n");

        cargarDiccionario();

        boolean seguirJugando = true;
        while (seguirJugando) {

            configurarSecuencia();

            jugarPartida();

            mostrarResultado();
            guardarEstadistica();

            System.out.print("\n¿Deseas ver todas las estadisticas globales? (s/n): ");
            String respuesta = in.nextLine().trim().toLowerCase();
            if (respuesta.equals("s")) {
                estadisticas.mostrarTodasLasEstadisticas();
            }

            System.out.print("\n¿Deseas jugar otra partida? (s/n): ");
            respuesta = in.nextLine().trim().toLowerCase();
            seguirJugando = respuesta.equals("s");

            if (seguirJugando) {
                restaurarEstado();
            }
        }

        System.out.println("\nBye bye");
        in.close();
    }

    private void cargarDiccionario() {

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
    private void configurarSecuencia() {
        System.out.println("\n¿Como quieres obtener la secuencia de 9 letras?");
        System.out.println("  1. Que la computadora la genere aleatoriamente");
        System.out.println("  2. Ingresarla yo mismo");

        String opcion = "";
        while (!opcion.equals("1") && !opcion.equals("2")) {
            System.out.print("Elige una opcion (1 o 2): ");
            opcion = in.nextLine().trim();
        }

        if (opcion.equals("1")) {
            secuenciaActual = palabras.secuenciaComputadora();
            System.out.println("\nTu secuencia es: " + secuenciaActual);
        } else {
            secuenciaActual = palabras.pideSecuencia(in);
            System.out.println("\nTu secuencia es: " + secuenciaActual);
        }
    }

    private void jugarPartida() {
        System.out.println("\n¡El juego comienza! Tienes 60 segundos.");
        System.out.println("Escribe palabras y presiona Enter. Solo cuentan palabras formadas con las letras de tu secuencia.\n");

        Cronometro cronometro = new Cronometro(60);
        Thread hiloCronometro = new Thread(cronometro);
        hiloCronometro.start();

        while (cronometro.tiempoCorre()) {
            String entrada = "";

            try {
                entrada = in.nextLine();
            } catch (Exception e) {
                break;
            }
            if (!cronometro.tiempoCorre()) break;

            procesarPalabra(entrada.trim(), cronometro);
        }

        cronometro.detener();

        System.out.println("\n¡Se acabo el tiempo!");
    }

    private void procesarPalabra(String entrada, Cronometro cronometro) {
        if (entrada.isEmpty()) return;

        if (!validarConSecuencia(entrada)) {
            System.out.println("  [!] '" + entrada + "' no se puede formar con las letras de tu secuencia.");
            System.out.print("Tiempo restante: " + cronometro.getSegundos() + "s | ");
            return;
        }

        String normalizada = normalizarParaDiccionario(entrada);

        Integer valor = diccionario.buscar(normalizada);

        if (valor == null) {
            System.out.println("  [!] '" + entrada + "' no esta en el diccionario.");
            System.out.print("Tiempo restante: " + cronometro.getSegundos() + "s | ");
            return;
        }

        boolean nueva = puntuacion.metePalabra(normalizada, valor);

        if (!nueva) {
            System.out.println("  [!] '" + entrada + "' ya la habias ingresado.");
        } else {
            System.out.println("  [+] '" + entrada + "' vale " + valor + " puntos! Total: " + puntuacion.getPuntuacionTotal());
        }
        System.out.print("Tiempo restante: " + cronometro.getSegundos() + "s | ");
    }

    private boolean validarConSecuencia(String entrada) {
        return palabras.sePuedeFormar(entrada);
    }

    private String normalizarParaDiccionario(String s) {
        return s.trim().toLowerCase();
    }

    private void mostrarResultado() {
        System.out.println("\n=== RESULTADO FINAL ===");
        puntuacion.imprimePalabras();
        System.out.println("Puntuacion total: " + puntuacion.getPuntuacionTotal() + " pts");
        System.out.println("Palabras validas: " + puntuacion.getTotalPalabras());

        estadisticas.mostrarTop3(secuenciaActual);
    }

    private void guardarEstadistica() {
        estadisticas.registrarPuntaje(secuenciaActual, puntuacion.getPuntuacionTotal());
    }

    private void restaurarEstado() {
        this.puntuacion = new Puntuacion();
        this.secuenciaActual = null;
    }
}
