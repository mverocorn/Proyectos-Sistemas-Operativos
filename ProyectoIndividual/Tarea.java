import java.util.concurrent.atomic.AtomicInteger;

public class Tarea implements Runnable {
    private static final AtomicInteger idGenerator = new AtomicInteger(0);
    private final int id;
    private int tiempoVida;
    private final int memoriaAsignada;
    private final SimuladorProcesos simulador;

    public Tarea(SimuladorProcesos simulador, int tiempoVida, int memoriaAsignada) {
        this.id = idGenerator.incrementAndGet();
        this.simulador = simulador;
        this.tiempoVida = tiempoVida;
        this.memoriaAsignada = memoriaAsignada;
    }

    public int getId() {
        return id;
    }

    public int getTiempoVida() {
        return tiempoVida;
    }

    @Override
    public void run() {
        simulador.marcarComoEjecutando(this);
        try {
            while (tiempoVida > 0) {
                Thread.sleep(1000);
                tiempoVida -= 1000;
                simulador.actualizarTarea(this);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            simulador.marcarComoTerminado(this);
            simulador.eliminarTarea(this);
        }
    }

    @Override
    public String toString() {
        return "ID: " + id + " - Tiempo: " + tiempoVida / 1000 + "s, Memoria: " + memoriaAsignada + " MB";
    }
}
