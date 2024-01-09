import java.util.Comparator;
import java.util.PriorityQueue;

public class GestorProcesos extends Thread {
    private final PriorityQueue<Tarea> colaDeTareas;
    private SimuladorProcesos simulador;

    public GestorProcesos(SimuladorProcesos simulador) {
        this.simulador = simulador;
        Comparator<Tarea> comparador = Comparator.comparingInt(Tarea::getTiempoVida);
        this.colaDeTareas = new PriorityQueue<>(comparador);
    }

    public synchronized void agregarTarea(Tarea tarea) {
        colaDeTareas.add(tarea);
        notify();
    }

    @Override
    public void run() {
        while (!interrupted()) {
            Tarea tarea = null;
            synchronized (this) {
                while (colaDeTareas.isEmpty()) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        return;
                    }
                }
                tarea = colaDeTareas.poll();
            }

            if (tarea != null) {
                Thread tareaThread = new Thread(tarea);
                tareaThread.start();
                try {
                    tareaThread.join();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
        }
    }
}
