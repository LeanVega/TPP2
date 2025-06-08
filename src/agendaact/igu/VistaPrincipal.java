package agenda.igu;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;

public class VistaPrincipal extends JFrame {

    private JTable tabla;
    private DefaultTableModel modelo;
    private JButton btnAgregar;

    private Font font = new Font("Consolas", Font.BOLD, 16);
    private Color fondoOscuro = new Color(10, 10, 10);
    private Color cianNeon = new Color(0, 255, 255);
    private Color verdeNeon = new Color(0, 255, 128);

    public VistaPrincipal() {
        setTitle("Agenda de Actividades");
        setSize(900, 500);
        setLocationRelativeTo(null);
        setLayout(null);
        setResizable(false);
        getContentPane().setBackground(fondoOscuro);

        UIManager.put("TableHeader.cellBorder", BorderFactory.createLineBorder(fondoOscuro));

        btnAgregar = new JButton("AGREGAR ACTIVIDAD") {
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(fondoOscuro);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                g2.setColor(verdeNeon);
                g2.setStroke(new BasicStroke(2));
                g2.drawRoundRect(1, 1, getWidth() - 3, getHeight() - 3, 20, 20);
                super.paintComponent(g2);
                g2.dispose();
            }
        };
        btnAgregar.setBounds(40, 30, 250, 45);
        btnAgregar.setFont(font);
        btnAgregar.setForeground(verdeNeon);
        btnAgregar.setFocusPainted(false);
        btnAgregar.setContentAreaFilled(false);
        btnAgregar.setOpaque(false);
        btnAgregar.setBorderPainted(false);
        btnAgregar.setCursor(new Cursor(Cursor.HAND_CURSOR));

        String[] columnas = {"Actividad", "Fecha", "Hora", "Tipo", "Alerta"};
        modelo = new DefaultTableModel(columnas, 0);
        tabla = new JTable(modelo);
        tabla.setBackground(fondoOscuro);
        tabla.setForeground(cianNeon);
        tabla.setFont(font);
        tabla.setRowHeight(28);
        tabla.setFocusable(false);
        tabla.setShowGrid(true);
        tabla.setGridColor(cianNeon);
        tabla.setShowHorizontalLines(true);
        tabla.setShowVerticalLines(true);

        tabla.getTableHeader().setBackground(fondoOscuro);
        tabla.getTableHeader().setForeground(cianNeon);
        tabla.getTableHeader().setFont(font);
        tabla.getTableHeader().setOpaque(false);
        tabla.getTableHeader().setReorderingAllowed(false);

        DefaultTableCellRenderer customRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {

                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                setHorizontalAlignment(JLabel.CENTER);
                c.setForeground(cianNeon);
                c.setBackground(fondoOscuro);
                setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, cianNeon));
                return c;
            }
        };

        for (int i = 0; i < tabla.getColumnCount(); i++) {
            tabla.getColumnModel().getColumn(i).setCellRenderer(customRenderer);
        }

        JScrollPane scrollPane = new JScrollPane(tabla);
        scrollPane.setBounds(40, 100, 800, 340);
        scrollPane.getViewport().setBackground(fondoOscuro);
        scrollPane.setBorder(BorderFactory.createLineBorder(cianNeon));

        add(btnAgregar);
        add(scrollPane);

        //test
        modelo.addRow(new String[]{"POO", "2025-06-09", "10:00", "Única", "Sí"});
        modelo.addRow(new String[]{"Caminar 30 min", "2025-06-09", "18:30", "Diaria", "No"});
        modelo.addRow(new String[]{"Tomar medicación", "2025-06-09", "08:00", "Diaria", "Sí"});
        modelo.addRow(new String[]{"Clases de Java", "2025-06-10", "15:00", "Semanal", "Sí"});
        modelo.addRow(new String[]{"Revisar correo", "2025-06-09", "09:00", "Diaria", "No"});
        modelo.addRow(new String[]{"Limpiar escritorio", "2025-06-11", "17:00", "Única", "No"});
        modelo.addRow(new String[]{"Enviar informe", "2025-06-12", "11:30", "Única", "Sí"});
        modelo.addRow(new String[]{"Reunión equipo", "2025-06-13", "16:00", "Semanal", "Sí"});
        modelo.addRow(new String[]{"Pagar servicios", "2025-06-15", "12:00", "Mensual", "Sí"});
        modelo.addRow(new String[]{"Leer 1 capítulo", "2025-06-09", "22:00", "Diaria", "No"});
        modelo.addRow(new String[]{"Respaldar archivos", "2025-06-14", "20:00", "Semanal", "Sí"});
        modelo.addRow(new String[]{"Revisar agenda", "2025-06-09", "07:30", "Diaria", "Sí"});

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new VistaPrincipal().setVisible(true));
    }
}
