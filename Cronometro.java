
public class Cronometro implements Runnable{

    private int segundos;

    public Cronometro(int segundos){
        this.segundos = segundos;
    }

    @Override
    public void run() {

        System.out.println("Inicio del contador");
        for(int i = segundos; i >= 0; i--){
            //Según yo solo hay que dejarlo así sin nada de mensajes, de
            //eso que lo haga el controlador no? 
            
            try{
                Thread.sleep(1000);
            }catch(InterruptedException e){
                System.out.println("Se interrumpio el cronometro");
                return;
            }
        }
    }

}
