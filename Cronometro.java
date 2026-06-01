public class Cronometro implements Runnable{

    private int segundos;
    private volatile boolean estaVivo;

    public Cronometro(int segundos){
        this.segundos = segundos;
	this.estaVivo = true;
    }

    @Override
    public void run() {
	
        System.out.println("Inicio del contador");
        for(int i = segundos; i > 0 && estaVivo; i--){
	    this.segundos = i;
	    
            try{
                Thread.sleep(1000);
            }catch(InterruptedException e){
		detener();
		return;
	    }
        }
	detener();
    }

    public int getSegundos() {
	return this.segundos;
    }
    public boolean tiempoCorre() {
	return this.estaVivo;
    }
    public void detener() {
	this.estaVivo = false;
    }
    
}
