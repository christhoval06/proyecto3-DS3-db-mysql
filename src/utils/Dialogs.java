package utils;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.concurrent.Callable;

/**
 * Created with IntelliJ IDEA.
 * User: cristobal
 * Date: 06/20/14
 * Time: 03:06 PM
 * To change this template use File | Settings | File Templates.
 */
public class Dialogs extends JDialog {
    private HashMap<String, Color> pallet;
    private JFrame frame;

    public Dialogs(JFrame f, HashMap<String, Color> p) {
        super(f, "", false);
        frame = f;
        pallet = p;
    }

    public void confirm(String msg, String btn, Callable<Void> callback){
        confirm(msg,"CANCELAR", btn, null, callback);
    }

    public void confirm(String msg, String btn, String btn2, Callable<Void> callback){
        confirm(msg, btn, btn2, null, callback);
    }

    public void confirm(String msg, String btn1, String btn2, final Callable<Void> callback1, final Callable<Void> callback2) {
        setSize(442, 200);
        setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        setUndecorated(true);
        setLocationRelativeTo(frame);
        getContentPane().setLayout(null);
        getContentPane().setBackground(pallet.get("color1"));
        getRootPane().setBackground(pallet.get("color1"));
        getRootPane().setBorder(new CompoundBorder(new LineBorder(pallet.get("color3")), new EmptyBorder(20, 20, 20, 20)));

        JLabel titulo = new JLabel(String.format("<html><h1>%s</h1></html>", msg));
        titulo.setHorizontalTextPosition(SwingConstants.CENTER);
        titulo.setVerticalTextPosition(SwingConstants.CENTER);
        titulo.setBounds(10, 10, 402, 80);
        titulo.setForeground(pallet.get("color3"));
        add(titulo);

        ImageButton btn = new ImageButton(btn1, pallet.get("color1"), pallet.get("color3"));
        btn.setBounds(20, 110, 120, 40);
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                if (callback1 != null)
                    try {
                        callback1.call();
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
            }
        });
        add(btn);

        btn = new ImageButton(btn2, pallet.get("color1"), pallet.get("color3"));
        btn.setBounds(248, 110, 120, 40);
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                if (callback2 != null)
                    try {
                        callback2.call();
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
            }
        });
        add(btn);
        setVisible(true);
    }


    public void alert(String msg, String btnLabel) {
        setSize(442, 200);
        setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        setUndecorated(true);
        setLocationRelativeTo(frame);
        getContentPane().setLayout(null);
        getContentPane().setBackground(pallet.get("color1"));
        getRootPane().setBackground(pallet.get("color1"));
        getRootPane().setBorder(new CompoundBorder(new LineBorder(pallet.get("color3")), new EmptyBorder(20, 20, 20, 20)));

        JLabel titulo = new JLabel(String.format("<html><h1>%s</h1></html>", msg));
        titulo.setHorizontalTextPosition(SwingConstants.CENTER);
        titulo.setVerticalTextPosition(SwingConstants.CENTER);
        titulo.setBounds(10, 10, 402, 80);
        titulo.setForeground(pallet.get("color3"));
        add(titulo);

        ImageButton btn;
        add(btn = new ImageButton(btnLabel, pallet.get("color1"), pallet.get("color3")));
        btn.setBounds(248, 110, 120, 40);
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        setVisible(true);
    }
}
