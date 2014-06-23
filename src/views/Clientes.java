package views;

import modelos.Cliente;
import modelos.Item;
import utils.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.concurrent.Callable;

/**
 * Created with IntelliJ IDEA.
 * User: cristobal
 * Date: 06/20/14
 * Time: 04:24 PM
 * To change this template use File | Settings | File Templates.
 */
public class Clientes extends FormPanel {

    private DB db;
    private JFrame frame;
    private HashMap<String, Color> pallet;


    public Clientes(JFrame f, DB d, HashMap<String, Color> p) {
        super(p);
        frame = f;
        db = d;
        pallet = p;
        setBounds(0, 0, frame.getWidth(), frame.getHeight());
        setLayout(null);
        setBackground(pallet.get("color3"));
    }

    public void lista() {
        removeAll();
        System.out.println("views.Clientes Lista");

        Font fnt = new Font(null, Font.BOLD, 18);
        JLabel label = new JLabel("Buscar:");
        label.setForeground(pallet.get("color1"));
        label.setFont(fnt);
        label.setHorizontalAlignment(SwingConstants.RIGHT);
        label.setBounds(10, 20, 100, 40);
        add(label);

        final QueryTableModel qtm = new QueryTableModel(db);
        qtm.setQuery("select nombre,apellido,cedula,FORMAT(compra_anual,2) as Compra, CASE activo WHEN 1 THEN 'SI' ELSE 'NO' END as Activo from cliente;");
        final JTable table = new JTable(qtm);
        JScrollPane scrollpane = new JScrollPane(table);
        scrollpane.setBounds(40, 70, 720, 450);
        scrollpane.setBackground(pallet.get("color1"));
        table.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent me) {
                JTable table = (JTable) me.getSource();
                Point p = me.getPoint();
                int row = table.rowAtPoint(p);
                if (me.getClickCount() == 2) {
                    final String cedula = (String) table.getModel().getValueAt(table.getSelectedRow(), 2);
                    new Dialogs(frame, pallet).confirm(String.format("Quieres editar al Cliente %s?", cedula), "NO", "SI", new Callable<Void>() {
                        @Override
                        public Void call() throws Exception {
                            editar(cedula);
                            return null;
                        }
                    });
                }
            }
        });
        table.getTableHeader().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                qtm.setQuery(String.format("select nombre,apellido,cedula,FORMAT(compra_anual,2) as Compra, CASE activo WHEN 1 THEN 'SI' ELSE 'NO' END as Activo from cliente ORDER BY %s;",table.getColumnName(table.columnAtPoint(e.getPoint()))));
            }
        });

        final JTextField query = new JTextField();
        query.setBorder(null);
        query.setBackground(pallet.get("color1"));
        query.setForeground(pallet.get("color4"));
        query.setFont(fnt);
        query.setBounds(120, 20, 500, 40);
        query.addKeyListener(new KeyListener() {
            public void keyPressed(KeyEvent keyEvent) {
                if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER) {
                    buscarClientes(table, qtm, query.getText().trim(), null);
                }
            }

            public void keyReleased(KeyEvent keyEvent) {
                printIt("Released", keyEvent);
            }

            public void keyTyped(KeyEvent keyEvent) {
                printIt("Typed", keyEvent);
            }

            private void printIt(String title, KeyEvent keyEvent) {
                int keyCode = keyEvent.getKeyCode();
                String keyText = KeyEvent.getKeyText(keyCode);
                System.out.println(title + " : " + keyText);
            }
        });
        add(query);
        ImageButton btn = new ImageButton("IR", pallet.get("color1"), pallet.get("color2"), pallet.get("color5"));
        btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                buscarClientes(table, qtm, query.getText().trim(), null);
            }
        });
        btn.setBounds(630, 20, 100, 40);
        add(btn);
        add(scrollpane, BorderLayout.CENTER);
        repaint();
    }

    private void buscarClientes(JTable table, QueryTableModel qtm, String s, String orderBy) {
        String sql = "SELECT nombre,apellido,cedula,FORMAT(compra_anual,2) AS Compra FROM cliente WHERE activo=1 AND cedula='%1$s' OR nombre LIKE '%%%1$s%%' OR apellido LIKE '%%%1$s%%' order by %2$s;";
        qtm.setQuery(String.format(sql, s, (orderBy != null ? orderBy : "nombre")));
        if (!(table.getModel().getRowCount() > 0))
            new Dialogs(frame, pallet).alert("No hay resultados!!!", "Aceptar");
    }

    private List<Item> getProvincias(){
        List<Item> provincias = new ArrayList<Item>();
        provincias.add(new Item("", "----"));
        provincias.add(new Item("BT", "BOCAS DEL TORO"));
        provincias.add(new Item("CO", "COCLE"));
        provincias.add(new Item("CL", "COLON"));
        provincias.add(new Item("CH", "CHIRIQUI"));
        provincias.add(new Item("DA", "DARIEN"));
        provincias.add(new Item("HE", "HERRERA"));
        provincias.add(new Item("LS", "LOS SANTOS"));
        provincias.add(new Item("PA", "PANAMA"));
        provincias.add(new Item("VE", "VERAGUAS"));
        provincias.add(new Item("PO", "PANAMA OESTE"));
        return provincias;
    }

    private void buscarParaEditar(){
        Cliente cliente = new Cliente(getForm());
        editar(cliente.getCedula());
    }

    public void editar(String cedula){
        Cliente cliente = null;
        if (cedula != null){
            cliente = new Cliente(db.select(String.format("SELECT * FROM cliente WHERE cedula='%s';", cedula)));
        }else
            cliente = new Cliente();

        editar(cliente);
    }

    private void editar(Cliente cliente) {
        removeAll();
        Font fnt = new Font(null, Font.BOLD, 18);
        createTextFieldHidden("clienteid", String.valueOf(cliente.getClienteid()), 75);
        createTextFieldAndLabel("cedula", cliente.getCedula(), 75, fnt).addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    buscarParaEditar();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {}
        });
        createTextFieldAndLabel("nombre", cliente.getNombre(), 120, fnt);
        createTextFieldAndLabel("apellido", cliente.getApellido(), 165, fnt);
        createTextFieldAndLabel("dirección", cliente.getDireccion(), 210, fnt);
        createTextFieldAndLabel("télefono", cliente.getTelefono(), 255, fnt);
        createComboBoxAndLabel("provincia", cliente.getProvincia(), 300, fnt, getProvincias());
        createTextFieldAndLabel("compra anual", String.valueOf(cliente.getCompra_anual()), 345, fnt);
        createCheckBoxdAndLabel("activo", cliente.getActivo() == 1, 390, fnt);
        createBtn("BUSCAR", new Point(640, 75), new Dimension(120, 40), new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                buscarParaEditar();
                return null;
            }
        });

        final String accion = cliente.isEmpty() ? "GUARDAR" : "ACTUALIZAR";

        createBtn(accion, new Point(cliente.isEmpty() ? 440 : 230, 440), new Dimension(190, 40), new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                new Dialogs(frame, pallet).confirm(String.format("Quieres %s al Cliente?", accion), "NO", "SI", new Callable<Void>() {
                    @Override
                    public Void call() throws Exception {
                        save();
                        return null;
                    }
                });
                return null;
            }
        });

        if (!cliente.isEmpty()) {
            final String clienteNombre = cliente.toString();
            createBtn("ELIMINAR", new Point(440, 440), new Dimension(190, 40), new Callable<Void>() {
                @Override
                public Void call() throws Exception {
                    new Dialogs(frame, pallet).confirm(String.format("Quieres ELIMINAR a %s?", clienteNombre), "NO", "SI", new Callable<Void>() {
                        @Override
                        public Void call() throws Exception {
                            eliminar();
                            return null;
                        }
                    });
                    return null;
                }
            });
        }

        repaint();
    }

    private void save() {
        HashMap<String, Object> form = getForm();
        if (!form.isEmpty()) {
            if (String.valueOf(form.get("nombre")).isEmpty()) {
                new Dialogs(frame, pallet).alert("debe colocar el NOMBRE", "Aceptar");
                return;
            } else if (String.valueOf(form.get("apellido")).isEmpty()) {
                new Dialogs(frame, pallet).alert("debe colocar el APELLIDO", "Aceptar");
                return;
            } else if (String.valueOf(form.get("cedula")).isEmpty()) {
                new Dialogs(frame, pallet).alert("debe colocar la CÉDULA", "Aceptar");
                return;
            }

            Cliente c = new Cliente(form);
            String sql, operacion;

            if (c.getClienteid() > 0) {
                sql = String.format("UPDATE cliente SET nombre='%s', apellido='%s', cedula='%s', direccion='%s', telefono='%s', provincia='%s', compra_anual=%s, activo=%s WHERE clienteid=%s ", c.getNombre(), c.getApellido(), c.getCedula(), c.getDireccion(), c.getTelefono(), c.getProvincia(), String.valueOf(c.getCompra_anual()), String.valueOf(c.getActivo()), String.valueOf(c.getClienteid()));
                operacion = "Actualizado";
            } else {
                sql = String.format("INSERT INTO cliente (nombre, apellido, cedula, direccion, telefono, provincia, compra_anual, activo) VALUES ('%s', '%s', '%s', '%s', '%s', '%s', %s, %s);", c.getNombre(), c.getApellido(), c.getCedula(), c.getDireccion(), c.getTelefono(), c.getProvincia(), String.valueOf(c.getCompra_anual()), String.valueOf(c.getActivo()));
                operacion = "Guardado";
            }
            if (db.execSQL(sql)) {
                new Dialogs(frame, pallet).alert(String.format("Cliente %s correctamente", operacion), "Aceptar");
                lista();
                return;
            }
            new Dialogs(frame, pallet).alert("a ocurrido un error, revise el log.", "Aceptar");
        }
    }

    private void eliminar() {
        HashMap<String, Object> form = getForm();
        String clienteid = String.valueOf(form.get("clienteid")),
                cedula = String.valueOf(form.get("cedula"));
        if (db.execSQL(String.format("delete from cliente where clienteid=%s", clienteid))) {
            new Dialogs(frame, pallet).alert(String.format("Cliente %s Eliminado correctamente", cedula), "Aceptar");
            lista();
            return;
        }
        new Dialogs(frame, pallet).alert("a ocurrido un error, revise el log.", "Aceptar");
    }
}
