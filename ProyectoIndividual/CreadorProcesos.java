import java.util.Random;

public class CreadorProcesos extends Thread {
    private final Random random = new Random();
    private final SimuladorProcesos simulador;

    public CreadorProcesos(SimuladorProcesos simulador) {
        this.simulador = simulador;
    }

    @Override
    public void run() {
        while (true) {
            int tiempo = random.nextInt(9000) + 1000;
            int memoria = random.nextInt(100) + 10;
            Tarea tarea = new Tarea(simulador, tiempo, memoria);
            simulador.agregarTarea(tarea);

            try {
                Thread.sleep(random.nextInt(2000) + 500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
