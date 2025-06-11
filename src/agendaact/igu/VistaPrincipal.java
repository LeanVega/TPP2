package agenda.igu;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;

public class VistaPrincipal extends JFrame {

    private JTable tabla;
    private DefaultTableModel modelo;
    private JButton btnAgregar, btnEliminar, btnMarcar, btnConfigurar;
    private Font font = new Font("Segoe UI Semibold", Font.BOLD, 16);
    private Color fondo = new Color(30, 30, 30);
    private Color texto = new Color(240, 240, 240);
    private Color naranja = new Color(255, 152, 0);
    private Color grisClaro = new Color(60, 60, 60);

    public VistaPrincipal() {
        setUndecorated(true);
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setLayout(null);
        getContentPane().setBackground(fondo);

        crearBotonesVentana();

        btnAgregar = crearBoton("AGREGAR ACTIVIDAD", 30, 60);
        btnEliminar = crearBoton("ELIMINAR ACTIVIDAD", 270, 60);
        btnMarcar = crearBoton("MARCAR COMO REALIZADA", 510, 60);
        btnConfigurar = crearBoton("CONFIGURAR ACTIVIDAD", 750, 60);

        add(btnAgregar);
        add(btnEliminar);
        add(btnMarcar);
        add(btnConfigurar);

        // tabla y modelo
        String[] columnas = {"Actividad", "Fecha", "Hora", "Tipo", "Estado", "Repeticiones"};
        modelo = new DefaultTableModel(columnas, 0);
        tabla = new JTable(modelo);
        tabla.setFont(font);
        tabla.setForeground(texto);
        tabla.setBackground(grisClaro);
        tabla.setRowHeight(30);
        tabla.setGridColor(naranja);
        tabla.setShowGrid(true);
        tabla.setSelectionBackground(new Color(80, 80, 80));
        tabla.setSelectionForeground(naranja);

        // header de la tabla, naranja y centrado
        tabla.getTableHeader().setBackground(new Color(40, 40, 40));
        tabla.getTableHeader().setForeground(naranja);
        tabla.getTableHeader().setFont(font);
        tabla.setFillsViewportHeight(true);

        // centro
        DefaultTableCellRenderer center = new DefaultTableCellRenderer();
        center.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < tabla.getColumnCount(); i++) {
            tabla.getColumnModel().getColumn(i).setCellRenderer(center);
        }

        // render custom para los bordes y el estado
        DefaultTableCellRenderer customRenderer = new DefaultTableCellRenderer() {
            private final Font estadoFont = new Font("Segoe UI Symbol", Font.BOLD, 18);
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                c.setForeground(texto);
                c.setBackground(grisClaro);
                setHorizontalAlignment(JLabel.CENTER);
                if (column == 4) {
                    setFont(estadoFont); // para los simbolitos
                    Object estado = table.getModel().getValueAt(row, column);
                    if ("✔".equals(estado)) {
                        setText("✔");
                    } else {
                        setText("⏳");
                    }
                } else {
                    setFont(font);
                    setText(value != null ? value.toString() : "");
                }
                if (isSelected) {
                    c.setBackground(new Color(80, 80, 80));
                    setForeground(naranja);
                    setBorder(BorderFactory.createLineBorder(naranja, 2));
                } else if (hasFocus) {
                    setBorder(BorderFactory.createLineBorder(naranja, 2));
                } else {
                    setBorder(BorderFactory.createLineBorder(naranja, 1));
                }
                return c;
            }
        };
        for (int i = 0; i < tabla.getColumnCount(); i++) {
            tabla.getColumnModel().getColumn(i).setCellRenderer(customRenderer);
        }
        tabla.setDefaultRenderer(Object.class, customRenderer);

        // header con borde naranja
        tabla.getTableHeader().setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel lbl = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                lbl.setBackground(new Color(40, 40, 40));
                lbl.setForeground(naranja);
                lbl.setFont(font);
                lbl.setHorizontalAlignment(JLabel.CENTER);
                lbl.setBorder(BorderFactory.createLineBorder(naranja, 2));
                return lbl;
            }
        });

        // scrollpane para la tabla
        JScrollPane scrollPane = new JScrollPane(tabla);
        scrollPane.setBounds(30, 120, 940, 440);
        scrollPane.setBorder(BorderFactory.createLineBorder(naranja));
        add(scrollPane);

        cargarEjemplos();

        // Deseleccionar fila al hacer click fuera de la tabla
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // Si el click no es sobre la tabla ni sus hijos
                if (!SwingUtilities.isDescendingFrom(e.getComponent(), tabla)) {
                    tabla.clearSelection();
                }
            }
        });
    }

    private JButton crearBoton(String texto, int x, int y) {
        JButton btn = new JButton(texto);
        btn.setBounds(x, y, 220, 40);
        btn.setFocusPainted(false);
        btn.setBackground(fondo);
        btn.setForeground(naranja);
        btn.setFont(font);
        btn.setBorder(new RoundBorder(20)); // Más redondeado
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                btn.setBorder(new RoundBorder(20, Color.WHITE));
            }
            public void mouseExited(MouseEvent e) {
                btn.setBorder(new RoundBorder(20, naranja));
            }
        });
        return btn;
    }

    private void crearBotonesVentana() {
        JPanel barra = new JPanel(null);
        barra.setBounds(0, 0, 1000, 40);
        barra.setBackground(new Color(20, 20, 20));

        JButton btnCerrar = crearControlVentana(Color.RED, "cerrar");
        btnCerrar.setBounds(960, 10, 28, 28);
        btnCerrar.addActionListener(e -> System.exit(0));

        JButton btnMaximizar = crearControlVentana(naranja, "maximizar");
        btnMaximizar.setBounds(926, 10, 28, 28);

        JButton btnMinimizar = crearControlVentana(Color.GRAY, "minimizar");
        btnMinimizar.setBounds(892, 10, 28, 28);
        btnMinimizar.addActionListener(e -> setState(Frame.ICONIFIED));

        barra.add(btnCerrar);
        barra.add(btnMaximizar);
        barra.add(btnMinimizar);
        add(barra);
    }

    private JButton crearControlVentana(Color color, String tipo) {
        Color iconColor = new Color(220, 220, 220); // Gris claro para el ícono
        JButton btn = new JButton() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(color);
                g2.fillOval(0, 0, getWidth() - 1, getHeight() - 1);
                // Dibuja el ícono centrado
                g2.setStroke(new BasicStroke(2.2f));
                g2.setColor(iconColor);
                int margin = 9;
                int w = getWidth(), h = getHeight();
                if ("cerrar".equals(tipo)) {
                    g2.drawLine(margin, margin, w - margin, h - margin);
                    g2.drawLine(w - margin, margin, margin, h - margin);
                } else if ("maximizar".equals(tipo)) {
                    g2.drawRect(margin, margin, w - 2 * margin, h - 2 * margin);
                } else if ("minimizar".equals(tipo)) {
                    g2.drawLine(margin, h / 2, w - margin, h / 2);
                }
                g2.dispose();
            }
        };
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setContentAreaFilled(false);
        btn.setOpaque(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(new CircleBorder());
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                btn.setBorder(new CircleBorder(Color.WHITE));
            }
            public void mouseExited(MouseEvent e) {
                btn.setBorder(new CircleBorder());
            }
        });
        return btn;
    }

    private void cargarEjemplos() {
        modelo.addRow(new String[]{"Estudiar POO", "2025-06-10", "10:00", "Única", "⏳", "0"});
        modelo.addRow(new String[]{"Ejercicio", "2025-06-10", "18:00", "Diaria", "✔", "3"});
        modelo.addRow(new String[]{"Leer libro", "2025-06-11", "21:00", "Semanal", "⏳", "1"});
        for (int i = 1; i <= 16; i++) {
            String nombre = "Random " + i;
            String fecha = "2025-06-" + String.format("%02d", (i % 28) + 1);
            String hora = String.format("%02d:00", (8 + i) % 24);
            String tipo = (i % 3 == 0) ? "Única" : (i % 3 == 1) ? "Diaria" : "Semanal";
            String estado = (i % 2 == 0) ? "✔" : "⏳";
            String repeticiones = String.valueOf(i);
            modelo.addRow(new String[]{nombre, fecha, hora, tipo, estado, repeticiones});
        }
    }

    // el main para levantar la ventana principal
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new VistaPrincipal().setVisible(true));
    }

    // borde redondeado para los botones principales
    class RoundBorder implements Border {
        private int radius;
        private Color color;
        RoundBorder(int radius) {
            this(radius, new Color(255, 152, 0));
        }
        RoundBorder(int radius, Color color) {
            this.radius = radius;
            this.color = color;
        }
        public Insets getBorderInsets(Component c) {
            return new Insets(radius + 1, radius + 1, radius + 2, radius);
        }
        public boolean isBorderOpaque() {
            return false;
        }
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(color);
            g2.setStroke(new BasicStroke(2));
            g2.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
            g2.dispose();
        }
    }

    // borde circular para los controles de ventana
    class CircleBorder implements Border {
        private Color color;
        CircleBorder() {
            this(new Color(255, 152, 0));
        }
        CircleBorder(Color color) {
            this.color = color;
        }
        public Insets getBorderInsets(Component c) {
            return new Insets(2, 2, 2, 2);
        }
        public boolean isBorderOpaque() {
            return false;
        }
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(color);
            g2.setStroke(new BasicStroke(2));
            g2.drawOval(x, y, width - 1, height - 1);
            g2.dispose();
        }
    }
}
