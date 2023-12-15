package com.mycompany.proyectoindividualsistemas;
import java.util.Random;
import javax.swing.JLabel;


public class GeneradorProcesos extends Thread {
    private final Animacion interfaz; 
    private final Random aleatorio; 
    private final int capacidadMemMax; 


    public GeneradorProcesos(Animacion animacion, int capacidadMemoriaMaxima) {
        this.interfaz = animacion;
        this.aleatorio = new Random();
        this.capacidadMemMax = capacidadMemoriaMaxima;
    }

    @Override
    public void run() {
        while (true) {
            int tiempoAleatorio = generarTiempoAleatorio();
            int memoriaAleatoria = generarMemoriaAleatoria();

            Proceso nuevoProceso = new Proceso(interfaz,tiempoAleatorio, memoriaAleatoria);
            
            
            if (interfaz.verificarMemoriaDisponible(memoriaAleatoria)) {
                interfaz.agregarProceso(nuevoProceso);
            } else {
            }

            try {
                sleep(generarTiempoEntreProcesos());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private int generarTiempoAleatorio() {
        return (aleatorio.nextInt(50)+1) * 100;
    }


    private int generarTiempoEntreProcesos() {  
        return aleatorio.nextInt(1200) + 1000;
    }

    private int generarMemoriaAleatoria() {
        return (aleatorio.nextInt(10)+1) * 10;
    }
}
