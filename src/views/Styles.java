package views;

import utils.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created with IntelliJ IDEA.
 * User: cristobal
 * Date: 06/20/14
 * Time: 04:24 PM
 * To change this template use File | Settings | File Templates.
 */
public class Styles extends FormPanel {

    private DB db;
    private JFrame frame;
    private HashMap<String, Color> pallet;
    private Pallets palletsColors;


    public Styles(JFrame f, DB d, Pallets p) {
        super(p);
        frame = f;
        db = d;
        palletsColors = p;
        pallet = palletsColors.getPallet();
        setBounds(0, 0, frame.getWidth(), frame.getHeight());
        setLayout(null);
        setBackground(pallet.get("color3"));
    }

    public void lista() {
        removeAll();
        int x = 150, y= 50;
        for(final String name : palletsColors.getPalletsNames()){
            createBtn(name.toUpperCase(), new Point(x, y), new Dimension(200, 80), new Callable<Void>() {
                @Override
                public Void call() throws Exception {
                    System.out.println(name);
                    db.setConfig("pallet", name);
                    palletsColors.setPallets(name);
                    return null;
                }
            }, palletsColors.getPallet(name));
            y += 85;
        }
        repaint();
    }
}
