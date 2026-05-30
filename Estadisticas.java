import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Estadisticas {

    private static final String NOMBRE_ARCHIVO = "estadisticas.txt";
    private static final int CAPACIDAD = 10;

    private static class Registro {
        String secuencia;
        int[] top3;

        public Registro(String secuencia, int[] top3) {
            if (secuencia == null)
                throw new IllegalArgumentException("La secuencia no puede ser null");

            this.secuencia = secuencia;
            this.top3 = top3;
        }
    }

    private final Registro[] historial;
    private int totalRegistros;
    private final String nombreArchivo;

    public Estadisticas() {
        this(NOMBRE_ARCHIVO);
    }

    public Estadisticas(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
        this.historial = new Registro[CAPACIDAD];
        this.totalRegistros = 0;
        cargarEstadisticas();
    }

    private Registro buscar(String secuencia) {
        for (int i = 0; i < totalRegistros; i++) {
            if (historial[i].secuencia.equals(secuencia)) {
                return historial[i];
            }
        }
        return null;
    }

    private boolean agregarRegistro(String secuencia, int nuevoPuntaje) {
      
        if (totalRegistros < historial.length) {
            historial[totalRegistros++] = new Registro(secuencia, new int[]{0, 0, 0});
            return true;
        }

        int indicePeor = 0;
        int peorPuntaje = historial[0].top3[0];

        for (int i = 1; i < totalRegistros; i++) {
            if (historial[i].top3[0] < peorPuntaje) {
                peorPuntaje = historial[i].top3[0];
                indicePeor = i;
            }
        }

        if (nuevoPuntaje > peorPuntaje) {
            historial[indicePeor] = new Registro(secuencia, new int[]{0, 0, 0});
            return true;
        }

        return false; 
    }

    private void cargarEstadisticas() {
        File archivo = new File(nombreArchivo);

        if (!archivo.exists()) {
            try {
                archivo.createNewFile();
            } catch (IOException e) {
                System.err.println("Error al crear el archivo de estadisticas: " + e.getMessage());
            }
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                linea = linea.trim();
                if (linea.isEmpty()) continue;

                String[] partes = linea.split(":");
                if (partes.length == 2) {
                    String secuencia = partes[0].trim().toUpperCase();
                    String[] puntajesTxt = partes[1].split(",");
                    int[] puntajes = new int[3];

                    for (int i = 0; i < 3; i++) {
                        if (i < puntajesTxt.length) {
                            try {
                                puntajes[i] = Integer.parseInt(puntajesTxt[i].trim());
                            } catch (NumberFormatException e) {
                                puntajes[i] = 0;
                            }
                        } else {
                            puntajes[i] = 0;
                        }
                    }
                    
                    if (totalRegistros < CAPACIDAD) {
                        historial[totalRegistros++] = new Registro(secuencia, puntajes);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo de estadisticas: " + e.getMessage());
        }
    }

    public void guardarEstadisticas() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(nombreArchivo))) {
            for (int i = 0; i < totalRegistros; i++) {
                Registro r = historial[i];
                bw.write(r.secuencia + ":" + r.top3[0] + "," + r.top3[1] + "," + r.top3[2]);
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error al guardar el archivo de estadisticas: " + e.getMessage());
        }
    }

    public void registrarPuntaje(String secuencia, int nuevoPuntaje) {
        String seqKey = secuencia.trim().toUpperCase();
        Registro r = buscar(seqKey);
        if (r == null) {
            boolean agregado = agregarRegistro(seqKey, nuevoPuntaje);
            if (!agregado) {
                System.out.println("Historial lleno. Tu puntaje no alcanzo para registrar la secuencia ["
                        + seqKey + "] en el Top " + CAPACIDAD + ".");
                return;
            }
            r = buscar(seqKey);
        }

        if (r == null) return;

        if (nuevoPuntaje > r.top3[0]) {
            r.top3[2] = r.top3[1];
            r.top3[1] = r.top3[0];
            r.top3[0] = nuevoPuntaje;
        } else if (nuevoPuntaje > r.top3[1]) {
            r.top3[2] = r.top3[1];
            r.top3[1] = nuevoPuntaje;
        } else if (nuevoPuntaje > r.top3[2]) {
            r.top3[2] = nuevoPuntaje;
        }

        guardarEstadisticas();
    }

    public void mostrarTop3(String secuencia) {
        String seqKey = secuencia.trim().toUpperCase();
        Registro r = buscar(seqKey);

        System.out.println("\n--- RECORDS HISTORICOS DE ESTA SECUENCIA [" + seqKey + "] ---");
        if (r == null) {
            System.out.println("  1º Lugar: 0 pts\n  2º Lugar: 0 pts\n  3º Lugar: 0 pts");
        } else {
            System.out.println("  1º Lugar: " + r.top3[0] + " pts");
            System.out.println("  2º Lugar: " + r.top3[1] + " pts");
            System.out.println("  3º Lugar: " + r.top3[2] + " pts");
        }
    }

    public void mostrarTodasLasEstadisticas() {
        if (totalRegistros == 0) {
            System.out.println("El archivo de estadisticas esta vacio.");
            return;
        }
        System.out.println("\n=================================================");
        System.out.println("         TOP 10 DE SECUENCIAS GLOBALES          ");
        System.out.println("=================================================");
        for (int i = 0; i < totalRegistros; i++) {
            Registro r = historial[i];
            System.out.printf("%d. Secuencia [%s] -> 1º: %d pts | 2º: %d pts | 3º: %d pts\n",
                    (i + 1), r.secuencia, r.top3[0], r.top3[1], r.top3[2]);
        }
        System.out.println("=================================================");
    }
}
