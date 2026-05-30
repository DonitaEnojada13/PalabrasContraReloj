
public class Cronometro implements Runnable{

    private int segundos;

    public Cronometro(int segundos){
        this.segundos = segundos;
    }

    @Override
    public void run() {
        
        for(int i = segundos; i >= 0; i--){

            try{
                Thread.sleep(1000);
            }catch(InterruptedException e){
                System.out.println("Se interrumpio el cronometro");
                return;
            }
        }
    }

}
