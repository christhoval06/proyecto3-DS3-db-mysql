import utils.DB;
import utils.MyMenu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.HashMap;

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

    private HashMap<String, Color> pallet;

    public Proyecto3() {
        pallet = new HashMap<String, Color>();
        palleteLila();
        final DB database = new DB();
        setTitle(String.format("Tienda %s", VERSION));
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setSize(FRAME_W, FRAME_H);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
        setResizable(false);
        final MyMenu menu = new MyMenu(this, database, pallet);
        setJMenuBar(menu.makeMenu());
        getContentPane().setBackground(pallet.get("color3"));
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

    private void palleteSeptemberEvening(){
        pallet.put("color1", Color.decode("#fa2020"));
        pallet.put("color2", Color.decode("#801a12"));
        pallet.put("color3", Color.decode("#7cfff9"));
        pallet.put("color4",Color.decode("#3b899b"));
        pallet.put("color5", Color.decode("#3a5055"));
    }

    private void palleteLila(){
        pallet.put("color1", Color.decode("#aa92ff"));
        pallet.put("color2", Color.decode("#554980"));
        pallet.put("color3", Color.decode("#393155"));
        pallet.put("color4",Color.decode("#2b2540"));
        pallet.put("color5", Color.decode("#221d33"));
    }

    private void palleteGreen(){
        pallet.put("color1", Color.decode("#00aa00"));
        pallet.put("color2", Color.decode("#00bb00"));
        pallet.put("color3", Color.decode("#00cc00"));
        pallet.put("color4",Color.decode("#00dd00"));
        pallet.put("color5", Color.decode("#00ee00"));
    }

    private void palleteRiver(){
        pallet.put("color1", Color.decode("#84ffff"));
        pallet.put("color2", Color.decode("#427e80"));
        pallet.put("color3", Color.decode("#2c5455"));
        pallet.put("color4",Color.decode("#213f40"));
        pallet.put("color5", Color.decode("#1a3333"));
    }

    private void palleteGreyen(){
        pallet.put("color1", Color.decode("#cfffe4"));
        pallet.put("color2", Color.decode("#688072"));
        pallet.put("color3", Color.decode("#45554c"));
        pallet.put("color4",Color.decode("#344039"));
        pallet.put("color5", Color.decode("#2a332e"));
    }

    public static void main(String[] args) {
        new Proyecto3();
    }
}
