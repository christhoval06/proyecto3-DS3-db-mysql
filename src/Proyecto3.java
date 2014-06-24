import utils.DB;
import utils.MyMenu;
import utils.Pallets;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * Created with IntelliJ IDEA.
 * User: cristobal
 * Date: 06/17/14
 * Time: 04:05 PM
 * To change this template use File | Settings | File Templates.
 */
public class Proyecto3 extends JFrame {
    private String VERSION = "1.0.4";
    private int FRAME_W = 800, FRAME_H = 600;
    private String PALLET = "lila";

    public Proyecto3() {
        final DB database = new DB();
        Pallets pallet = new Pallets(database.getConfig("pallet", PALLET));
        setTitle(String.format("Tienda %s", VERSION));
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setSize(FRAME_W, FRAME_H);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
        setResizable(false);
        final MyMenu menu = new MyMenu(this, database, pallet);
        setJMenuBar(menu.makeMenu());
        getContentPane().setBackground(pallet.getPallet().get("color3"));
        addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {

            }

            @Override
            public void windowClosing(WindowEvent e) {
                database.close();
                menu.salir();
            }

            @Override
            public void windowClosed(WindowEvent e) {}

            @Override
            public void windowIconified(WindowEvent e) {}

            @Override
            public void windowDeiconified(WindowEvent e) {}

            @Override
            public void windowActivated(WindowEvent e) {}

            @Override
            public void windowDeactivated(WindowEvent e) {}
        });
        setVisible(true);
    }

    public static void main(String[] args) {
        new Proyecto3();
    }
}
