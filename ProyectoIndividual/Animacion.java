
package com.mycompany.proyectoindividualsistemas;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;
import java.awt.Color;


public class Animacion extends javax.swing.JFrame {
    private SistemaOperativo SO;
    private GeneradorProcesos generadorDeProcesos;
    public static List<Proceso> listaDeEspera = new ArrayList<>();
    public static DefaultTableModel modeloTabla;


    public Animacion() {
        initComponents();
        this.setLocationRelativeTo(null);
        this.setTitle("Proyecto SJN");

        SO = new SistemaOperativo(this, 500);
        generadorDeProcesos = new GeneradorProcesos(this, 500);

        modeloTabla = (DefaultTableModel) jTable2.getModel();
    }
    
    public JLabel getMensaje(){
        return mensaje;
    }
    
    public JLabel getTope(){
        return tope;
    }
    
    public void actualizarInterfaz() {
        SwingUtilities.invokeLater(() -> {
            modeloTabla.setRowCount(0);
            synchronized (listaDeEspera) {
                for (int i = 0; i < listaDeEspera.size(); i++) {
                    Proceso proceso = listaDeEspera.get(i);
                    Object[] rowData = {proceso.getIdP(), proceso.getTiempo(), proceso.getMemoriaAsignada(), proceso.getEstado()};
                    modeloTabla.addRow(rowData);

                   if ("En ejecución".equals(proceso.getEstado()) || "Completado".equals(proceso.getEstado())) {
                        jTable2.setRowSelectionInterval(i, i);
                        jTable2.setSelectionBackground(new Color(255, 200, 200)); // Color de fondo para la fila en ejecución
                    }
                }
            }
            jTable2.setModel(modeloTabla);
        });
    }

    @SuppressWarnings("unchecked")                  
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        Fondo = new javax.swing.JPanel();
        boton = new javax.swing.JButton();
        mensaje = new javax.swing.JLabel();
        tope = new javax.swing.JLabel();
        tiempo = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        Fondo.setBackground(new java.awt.Color(250, 255, 200));
        Fondo.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        Fondo.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        boton.setText("INICIO");
        boton.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 16));
        boton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonActionPerformed(evt);
            }
        });
        Fondo.add(boton, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 420, -1, -1));

        mensaje.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 18)); 
        Fondo.add(mensaje, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 20, 200, 40));

        tope.setBackground(new java.awt.Color(255, 0, 0));
        tope.setSize(30, 210);
        Fondo.add(tope, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 140, 20, 210));

        tiempo.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 16)); 
        Fondo.add(tiempo, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 100, 290, 30));

        jLabel1.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 18)); 
        jLabel1.setText("Shortest Job Next");

        Fondo.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(225, 30, -1, -1));

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
            },
            new String [] {
                "ID PROCESO", "DURACIÓN", "MEMORIA", "ESTADO"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jTable2.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 14));
        jTable2.setBackground(new java.awt.Color(200, 160, 255));
        jTable2.setForeground(new java.awt.Color(0, 0, 0));
         
        this.setSize(800, 600);
        jScrollPane2.setViewportView(jTable2);
        jScrollPane2.setPreferredSize(new java.awt.Dimension(490, 321));


        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 490, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 321, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        Fondo.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 70, 490, 320));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Fondo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Fondo, javax.swing.GroupLayout.PREFERRED_SIZE, 484, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }                     

    private void botonActionPerformed(java.awt.event.ActionEvent evt) {
        SO.start();
        generadorDeProcesos.start();
    }                                     

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Animacion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Animacion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Animacion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Animacion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Animacion().setVisible(true);
            }
        });
    }
    public synchronized boolean verificarMemoriaDisponible(int memoriaSolicitada) {
        if(SO.memUtilizada<SO.capacidadMemMax){
            return true;
        }else{
            return false;
        }
    }

    /**
     * Agrega un proceso a la lista de espera y actualiza la interfaz gráfica.
     *
     * @param proceso El proceso a agregar.
     */
    public synchronized void agregarProceso(Proceso proceso) {
        listaDeEspera.add(proceso);
        actualizarInterfaz();
    }
    
    public synchronized void eliminarProceso(Proceso proceso) {
        int fila=proceso.getIdP();
        for (int i = 0; i < modeloTabla.getRowCount(); i++) {
                if ((int) modeloTabla.getValueAt(i, 0) == fila) {
                    listaDeEspera.remove(i);
                    break; 
                }
            }
        actualizarInterfaz();
    }
    // Variables declaration - do not modify                     
    private javax.swing.JPanel Fondo;
    private javax.swing.JButton boton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable2;
    private javax.swing.JLabel mensaje;
    private javax.swing.JLabel tiempo;
    private javax.swing.JLabel tope;
    // End of variables declaration                   
}
