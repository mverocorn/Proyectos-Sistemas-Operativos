
package com.mycompany.proyectoindividualsistemas;

public class Proceso implements Runnable {
    private static int contProcesos = 0; 
    private int idProceso; 
    private int tiempo; 
    private int memAsignada; 
    private String estado;
    private Animacion interfaz;

    public Proceso(Animacion interfaz, int tiempo, int memAsignada) {
        this.idProceso = ++contProcesos;
        this.tiempo = tiempo;
        this.memAsignada = memAsignada;
        this.estado = "Lista de espera"; 
        this.interfaz = interfaz;
    }

    public int getIdP() {
        return idProceso;
    }

    public int getTiempo() {
        return tiempo;
    }

    public int getMemoriaAsignada() {
        return memAsignada;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }


    @Override
    public void run() {
        try {
            estado = "En ejecución";
            while (tiempo > 0) {
                tiempo-=30;
                Thread.sleep(100);
            }
            estado = "Completado";
            Thread.sleep(2000); 
            interfaz.eliminarProceso(this);
        } catch (InterruptedException e) {
            estado = "Interrumpido"; 
        } finally {

        }
    }
}
