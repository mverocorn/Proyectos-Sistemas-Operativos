import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class SimuladorProcesos extends JFrame {
    private GestorProcesos gestorProcesos;
    private JPanel panelProcesos;
    private Map<Integer, JLabel> procesosEnUI;

    public SimuladorProcesos() {
        gestorProcesos = new GestorProcesos(this);
        procesosEnUI = new HashMap<>();
        iniciarUI();
    }

    private void iniciarUI() {
        setTitle("Simulador de Procesos SJN");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        int filas = 10;
        int columnas = 10;
        panelProcesos = new JPanel(new GridLayout(filas, columnas, 5, 5));
        panelProcesos.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));

        add(new JScrollPane(panelProcesos), BorderLayout.CENTER);

        JButton iniciarButton = new JButton("Iniciar");
        iniciarButton.addActionListener(e -> {
            gestorProcesos.start();
            new CreadorProcesos(this).start();
        });

        add(iniciarButton, BorderLayout.SOUTH);
        setVisible(true);
    }

    public void agregarTarea(Tarea tarea) {
        gestorProcesos.agregarTarea(tarea);
        SwingUtilities.invokeLater(() -> {
            JLabel label = new JLabel(tarea.toString());
            label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            panelProcesos.add(label);
            panelProcesos.revalidate();
            procesosEnUI.put(tarea.getId(), label);
        });
    }

    public void actualizarTarea(Tarea tarea) {
        SwingUtilities.invokeLater(() -> {
            JLabel label = procesosEnUI.get(tarea.getId());
            if (label != null) {
                label.setText(tarea.toString());
            }
        });
    }

    public void marcarComoEjecutando(Tarea tarea) {
        SwingUtilities.invokeLater(() -> {
            JLabel label = procesosEnUI.get(tarea.getId());
            if (label != null) {
                label.setBorder(BorderFactory.createLineBorder(Color.GREEN, 2));
            }
        });
    }

    public void marcarComoTerminado(Tarea tarea) {
        SwingUtilities.invokeLater(() -> {
            JLabel label = procesosEnUI.get(tarea.getId());
            if (label != null) {
                label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            }
        });
    }

    public void eliminarTarea(Tarea tarea) {
        SwingUtilities.invokeLater(() -> {
            JLabel label = procesosEnUI.remove(tarea.getId());
            if (label != null) {
                panelProcesos.remove(label);
                panelProcesos.revalidate();
                panelProcesos.repaint();
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SimuladorProcesos().setVisible(true));
    }
}
