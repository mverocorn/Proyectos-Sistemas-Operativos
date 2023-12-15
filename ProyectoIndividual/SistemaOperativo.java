
package com.mycompany.proyectoindividualsistemas;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import javax.swing.SwingUtilities;


public class SistemaOperativo extends Thread {
    private final Animacion interfaz; 
    public final int capacidadMemMax; 
    public int memUtilizada; 


    public SistemaOperativo(Animacion animacion, int capacidadMemoriaMaxima) {
        this.interfaz = animacion;
        this.capacidadMemMax = capacidadMemoriaMaxima;
        this.memUtilizada = 0;
    }


    @Override
   public void run() {
    while (true) {
        Proceso procesoAEjecutar = seleccionarProceso();

        if (procesoAEjecutar != null) {
            asignarTiempoCPU(procesoAEjecutar);
        }

        try {
            sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
    private Proceso seleccionarProceso() {
        List<Proceso> procesosElegibles = new ArrayList<>();
        synchronized (interfaz.listaDeEspera) {
            for (Proceso proceso : interfaz.listaDeEspera) {
                if (memUtilizada + proceso.getMemoriaAsignada() <= capacidadMemMax) {
                    procesosElegibles.add(proceso);
                }
            }
        }

        return procesosElegibles.stream()
                .min(Comparator.comparingInt(Proceso::getTiempo)) 
                .orElse(null);
    }

    private void asignarTiempoCPU(Proceso proceso) {
        proceso.setEstado("En ejecucion");
        Thread procesoThread = new Thread(proceso);

        memUtilizada += proceso.getMemoriaAsignada();
        procesoThread.start(); 

        try {
            procesoThread.join();
            proceso.setEstado("Completado"); 
            Thread.sleep(1000); 
            interfaz.agregarProceso(proceso);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        memUtilizada -= proceso.getMemoriaAsignada(); 
}
}
