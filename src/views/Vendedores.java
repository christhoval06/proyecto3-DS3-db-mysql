package views;

import modelos.Item;
import modelos.Vendedor;
import utils.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created with IntelliJ IDEA.
 * User: cristobal
 * Date: 06/23/14
 * Time: 10:06 AM
 * To change this template use File | Settings | File Templates.
 */
public class Vendedores extends FormPanel {

    private DB db;
    private JFrame frame;
    private HashMap<String, Color> pallet;

    public Vendedores(JFrame f, DB d, HashMap<String, Color> p) {
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
        System.out.println("Vendedores Lista");

        Font fnt = new Font(null, Font.BOLD, 18);
        JLabel label = new JLabel("Buscar:");
        label.setForeground(pallet.get("color1"));
        label.setFont(fnt);
        label.setHorizontalAlignment(SwingConstants.RIGHT);
        label.setBounds(10, 20, 100, 40);
        add(label);

        final QueryTableModel qtm = new QueryTableModel(db);
        qtm.setQuery("select nombre,apellido,codigo,FORMAT(venta_mensual,2) as Venta, CASE activo WHEN 1 THEN 'SI' ELSE 'NO' END as Activo from vendedor;");
        final JTable table = new JTable(qtm);
        JScrollPane scrollpane = new JScrollPane(table);
        scrollpane.setColumnHeader(new JViewport() {
            @Override
            public Dimension getPreferredSize() {
                Dimension d = super.getPreferredSize();
                d.height = 32;
                return d;
            }
        });
        scrollpane.setBounds(40, 70, 720, 450);
        scrollpane.setBackground(pallet.get("color1"));
        table.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent me) {
                JTable table = (JTable) me.getSource();
                if (me.getClickCount() == 2) {
                    final String codigo = (String) table.getModel().getValueAt(table.getSelectedRow(), 2);
                    new Dialogs(frame, pallet).confirm(String.format("Quieres editar al Vendedor %s?", codigo), "NO", "SI", new Callable<Void>() {
                        @Override
                        public Void call() throws Exception {
                            editar(codigo);
                            return null;
                        }
                    });
                }
            }
        });
        table.getTableHeader().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                qtm.setQuery(String.format("select nombre,apellido,codigo,FORMAT(venta_mensual,2) as Venta, CASE activo WHEN 1 THEN 'SI' ELSE 'NO' END as Activo from vendedor ORDER BY %s;", table.getColumnName(table.columnAtPoint(e.getPoint()))));
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
        String sql = "select nombre,apellido,codigo,FORMAT(venta_mensual,2) as Venta, CASE activo WHEN 1 THEN 'SI' ELSE 'NO' END as Activo from vendedor WHERE activo=1 AND codigo='%1$s' OR nombre LIKE '%%%1$s%%' OR apellido LIKE '%%%1$s%%' order by %2$s;";
        qtm.setQuery(String.format(sql, s, (orderBy != null ? orderBy : "nombre")));
        if (!(table.getModel().getRowCount() > 0))
            new Dialogs(frame, pallet).alert("No hay resultados!!!", "Aceptar");
    }

    private List<Item> getDepartamentos(){
        List<Item> departamentos = new ArrayList<Item>();
        departamentos.add(new Item("", "----"));
        departamentos.add(new Item("LN", "LENCERIA"));
        departamentos.add(new Item("CO", "CONSTRUCCION"));
        departamentos.add(new Item("FA", "FARMACIA"));
        departamentos.add(new Item("LI", "LIBROS"));
        departamentos.add(new Item("OF", "OFICINA"));
        departamentos.add(new Item("DE", "DEPORTES"));
        departamentos.add(new Item("PL", "PLAYA"));
        departamentos.add(new Item("AC", "ACCESORIOS"));
        departamentos.add(new Item("JO", "JOYERIA"));
        departamentos.add(new Item("CA", "CABALLEROS"));
        departamentos.add(new Item("DA", "DAMAS"));
        departamentos.add(new Item("ZA", "ZAPATERIA"));
        return departamentos;
    }

    private void buscarParaEditar(){
        Vendedor v = new Vendedor(getForm());
        editar(v.getCodigo());
    }

    public void editar(String codigo) {
        Vendedor vendedor = null;
        if (!codigo.isEmpty()){
            vendedor = new Vendedor(db.select(String.format("SELECT * FROM vendedor WHERE codigo='%s';", codigo)));
        }else
            vendedor = new Vendedor();

        editar(vendedor);
    }

    private void editar(Vendedor vendedor){
        removeAll();
        Font fnt = new Font(null, Font.BOLD, 18);
        final String accion = vendedor.isEmpty() ? "GUARDAR" : "ACTUALIZAR";
        createTextFieldHidden("vendedorid", String.valueOf(vendedor.getVendedorid()), 75);
        createTextFieldAndLabel("codigo", vendedor.getCodigo(), 75, fnt).addKeyListener(new KeyListener() {
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
        createTextFieldAndLabel("nombre", vendedor.getNombre(), 120, fnt);
        createTextFieldAndLabel("apellido", vendedor.getApellido(), 165, fnt);
        createComboBoxAndLabel("departamento", vendedor.getDepartamento(), 210, fnt, getDepartamentos());
        createTextFieldAndLabel("cargo", vendedor.getCargo(), 255, fnt);
        createTextFieldAndLabel("venta mensual", String.valueOf(vendedor.getVenta_mensual()), 300, fnt);
        createTextFieldAndLabel("venta anual", String.valueOf(vendedor.getVenta_anual()), 345, fnt);
        createCheckBoxdAndLabel("activo", vendedor.getActivo() == 1, 390, fnt);

        createBtn("BUSCAR", new Point(640, 75), new Dimension(120, 40), new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                buscarParaEditar();
                return null;
            }
        });

        createBtn(accion, new Point(vendedor.isEmpty() ? 440 : 230, 440), new Dimension(190, 40), new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                new Dialogs(frame, pallet).confirm(String.format("Quieres %s al Vendedor?", accion), "NO", "SI", new Callable<Void>() {
                    @Override
                    public Void call() throws Exception {
                        save();
                        return null;
                    }
                });
                return null;
            }
        });


        if (!vendedor.isEmpty()) {
            final String vendedorNombre = vendedor.toString();
            createBtn("ELIMINAR", new Point(440, 440), new Dimension(190, 40), new Callable<Void>() {
                @Override
                public Void call() throws Exception {
                    new Dialogs(frame, pallet).confirm(String.format("Quieres ELIMINAR a %s?", vendedorNombre), "NO", "SI", new Callable<Void>() {
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
            } else if (String.valueOf(form.get("codigo")).isEmpty()) {
                new Dialogs(frame, pallet).alert("debe colocar un CODIGO", "Aceptar");
                return;
            }

            Vendedor v = new Vendedor(form);
            String sql, operacion;

            if (v.getVendedorid() > 0) {
                sql = String.format("UPDATE vendedor SET nombre='%s', apellido='%s', codigo='%s', departamento='%s', cargo='%s', venta_mensual=%s, venta_anual=%s, activo=%s WHERE vendedorid=%s ", v.getNombre(), v.getApellido(), v.getCodigo(), v.getDepartamento(), v.getCargo(), String.valueOf(v.getVenta_mensual()), String.valueOf(v.getVenta_anual()), String.valueOf(v.getActivo()), String.valueOf(v.getVendedorid()));
                operacion = "Actualizado";
            } else {
                sql = String.format("INSERT INTO vendedor (nombre, apellido, codigo, departamento, cargo, venta_mensual, venta_anual, activo) VALUES ('%s', '%s', '%s', '%s', '%s', '%s', %s, %s);", v.getNombre(), v.getApellido(), v.getCodigo(), v.getDepartamento(), v.getCargo(), String.valueOf(v.getVenta_mensual()), String.valueOf(v.getVenta_anual()), String.valueOf(v.getActivo()));
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
        if (db.execSQL(String.format("delete from vendedor where vendedorid=%s", clienteid))) {
            new Dialogs(frame, pallet).alert(String.format("Cliente %s Eliminado correctamente", cedula), "Aceptar");
            lista();
            return;
        }
        new Dialogs(frame, pallet).alert("a ocurrido un error, revise el log.", "Aceptar");
    }
}
