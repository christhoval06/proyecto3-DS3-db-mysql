package utils;

import views.Clientes;
import views.Vendedores;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.concurrent.Callable;

/**
 * Created with IntelliJ IDEA.
 * User: cristobal
 * Date: 06/20/14
 * Time: 01:07 PM
 * To change this template use File | Settings | File Templates.
 */
public class MyMenu implements ActionListener {
    private DB db;
    private JFrame frame;
    private HashMap<String,Color> pallet;

    public MyMenu(JFrame f, DB d, HashMap<String,Color> p) {
        frame = f;
        db = d;
        pallet = p;
    }

    public JMenuBar makeMenu() {
        JMenuBar menuBar;
        JMenu menu;
        JMenuItem menuItem;
        menuBar = new JMenuBar();

        //INICIO
        menu = new JMenu("Inicio");
        menu.setMnemonic(KeyEvent.VK_I);
        menu.getAccessibleContext().setAccessibleDescription("Acceso a las funciones basicas del programa");
        menuBar.add(menu);

        menuItem = new JMenuItem("Presentación", KeyEvent.VK_P);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.ALT_MASK));
        menuItem.getAccessibleContext().setAccessibleDescription("Visualize la presentación de la aplicación");
        menuItem.setActionCommand("presentacion");
        menuItem.addActionListener(this);
        menu.add(menuItem);

        menu.addSeparator();

        menuItem = new JMenuItem("Salir", KeyEvent.VK_Q);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.CTRL_MASK));
        menuItem.getAccessibleContext().setAccessibleDescription("Salir de la aplicación");
        menuItem.setActionCommand("salir");
        menuItem.addActionListener(this);
        menu.add(menuItem);

        //CLIENTES
        menu = new JMenu("Clientes");
        menu.setMnemonic(KeyEvent.VK_C);
        menu.getAccessibleContext().setAccessibleDescription("Menu de views.Clientes");
        menuBar.add(menu);

        menuItem = new JMenuItem("Lista", KeyEvent.VK_L);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, ActionEvent.ALT_MASK));
        menuItem.setActionCommand("clist");
        menuItem.addActionListener(this);
        menu.add(menuItem);

        menuItem = new JMenuItem("Agregar", KeyEvent.VK_A);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.ALT_MASK));
        menuItem.setActionCommand("cadd");
        menuItem.addActionListener(this);
        menu.add(menuItem);

        //VENDEDORES
        menu = new JMenu("Vendedores");
        menu.setMnemonic(KeyEvent.VK_V);
        menu.getAccessibleContext().setAccessibleDescription("Menu de views.Vendedores");
        menuBar.add(menu);

        menuItem = new JMenuItem("Lista", KeyEvent.VK_I);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, ActionEvent.ALT_MASK));
        menuItem.setActionCommand("vlist");
        menuItem.addActionListener(this);
        menu.add(menuItem);

        menuItem = new JMenuItem("Agregar", KeyEvent.VK_G);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, ActionEvent.ALT_MASK));
        menuItem.setActionCommand("vadd");
        menuItem.addActionListener(this);
        menu.add(menuItem);

        //REPORTES
        menu = new JMenu("Reportes");
        menu.setMnemonic(KeyEvent.VK_R);
        menu.getAccessibleContext().setAccessibleDescription("Menu de Reportes");
        menuBar.add(menu);

        menuItem = new JMenuItem("Clientes", KeyEvent.VK_N);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.ALT_MASK));
        menuItem.setActionCommand("rclientes");
        menuItem.addActionListener(this);
        menu.add(menuItem);

        menuItem = new JMenuItem("Vendedores", KeyEvent.VK_D);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, ActionEvent.ALT_MASK));
        menuItem.setActionCommand("rvendedores");
        menuItem.addActionListener(this);
        menu.add(menuItem);

        return menuBar;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        if(action.equalsIgnoreCase("presentacion")){

        }else if(action.equalsIgnoreCase("salir")){
            salir();
        }else if(action.equalsIgnoreCase("clist") || action.equalsIgnoreCase("cadd")){
            frame.getContentPane().removeAll();
            Clientes clientes = new Clientes(frame, db, pallet);
            frame.add(clientes, BorderLayout.CENTER);
            if(action.equalsIgnoreCase("clist")){
                clientes.lista();
            }else if(action.equalsIgnoreCase("cadd")){
                clientes.editar("");
            }
            frame.repaint();
        }else if(action.equalsIgnoreCase("vlist") || action.equalsIgnoreCase("vadd")){
            frame.getContentPane().removeAll();
            Vendedores vendedores = new Vendedores(frame, db, pallet);
            frame.add(vendedores, BorderLayout.CENTER);
            if(action.equalsIgnoreCase("vlist")){
                vendedores.lista();
            }else if(action.equalsIgnoreCase("vadd")){
                vendedores.editar("");
            }
            frame.repaint();
        }else if(action.equalsIgnoreCase("rclientes")){

        }else if(action.equalsIgnoreCase("rvendedores")){

        }else{
            db.log(String.format("Funcion %s, no implementada", action));
        }
    }

    public void salir(){
        new Dialogs(frame, pallet).confirm("Desea Salir de la aplicación?", "SALIR", new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                System.exit(0);
                return null;
            }
        });
    }
}
