
public class Cronometro implements Runnable{

    private long segundosTotales, segundosRestantes ;
    private boolean terminado;

    public Cronometro(long segundos){
        this.segundosTotales = segundos;
        this.segundosRestantes = segundos;
        this.terminado = false;
    }

    @Override
    public void run() {

        System.out.println("Inicio del contador");
        for(long i = segundosTotales; i >= 0; i--){
            //Según yo solo hay que dejarlo así sin nada de mensajes, de
            //eso que lo haga el controlador no? 

            try{
                Thread.sleep(1000);
            }catch(InterruptedException e){
                System.out.println("Se interrumpio el cronometro");
                return;
            }
        }

        this.terminado = true;
    }

    public boolean haTerminado(){
        return this.terminado;
    }

    public String tiempoRestante(){
        long min = segundosRestantes / 60;
        long seg = segundosRestantes % 60;
        return String.format("%02d:%02d", min, seg);
    }
}
