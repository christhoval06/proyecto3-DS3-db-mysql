package utils;

import modelos.Item;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created with IntelliJ IDEA.
 * User: cristobal
 * Date: 06/20/14
 * Time: 08:28 PM
 * To change this template use File | Settings | File Templates.
 */
public class FormPanel extends JPanel {

    private HashMap<String, Color> pallet;
    private HashMap<String, Object> form;
    private Pallets palletsColors;

    public FormPanel(Pallets p) {
        palletsColors = p;
        pallet = palletsColors.getPallet();
    }

    public void createTextFieldHidden(String nombre, String value, int y){
        JTextField field = new JTextField(value);
        field.setName(removeCosasRaras(nombre));
        field.setBounds(230, y, 400, 40);
        field.setVisible(false);
        add(field);
    }
    public ImageButton createBtn(String text, Point xy, Dimension wh, final Callable<Void> callable){
        return  createBtn(text, xy, wh, callable, null);
    }

    public ImageButton createBtn(String text, Point xy, Dimension wh, final Callable<Void> callable, HashMap<String, Color> p){
        HashMap<String, Color> paleta = p!=null ? p : pallet;
        ImageButton btn = new ImageButton(text, paleta.get("color1"), paleta.get("color2"), paleta.get("color5"));
        btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    callable.call();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });
        btn.setSize(wh);
        btn.setLocation(xy);
        add(btn);
        return btn;
    }

    public JTextField createTextFieldAndLabel(String nombre, String value, int y, Font fnt){
        JLabel label = new JLabel(String.format("%s:", nombre.toUpperCase()));
        label.setForeground(pallet.get("color1"));
        label.setFont(fnt);
        label.setHorizontalAlignment(SwingConstants.RIGHT);
        label.setBounds(10, y, 210, 40);
        add(label);

        JTextField field = new JTextField(value);
        field.setBorder(null);
        field.setBackground(pallet.get("color1"));
        field.setForeground(pallet.get("color4"));
        field.setFont(fnt);
        field.setName(removeCosasRaras(nombre));
        field.setBounds(230, y, 400, 40);
        add(field);
        return field;
    }

    public JCheckBox createCheckBoxdAndLabel(String nombre, boolean value, int y, Font fnt){
        JCheckBox chk = new JCheckBox(nombre.toUpperCase());
        chk.setBounds(230, y, 120, 40);
        chk.setSelected(value);
        chk.setFont(fnt);
        chk.setBackground(pallet.get("color3"));
        chk.setForeground(pallet.get("color1"));
        chk.setName(removeCosasRaras(nombre));
        add(chk);
        return  chk;
    }

    public JComboBox createComboBoxAndLabel(String nombre, String value, int y, Font fnt, final List<Item> values){
        JLabel label = new JLabel(String.format("%s:", nombre.toUpperCase()));
        label.setForeground(pallet.get("color1"));
        label.setFont(fnt);
        label.setHorizontalAlignment(SwingConstants.RIGHT);
        label.setBounds(10, y, 210, 40);
        add(label);

        JComboBox<Item> combo = new JComboBox<Item>();
        MyComboBoxModel modelo = new MyComboBoxModel(values);
        combo.setModel(modelo);
        combo.setSelectedItem(modelo.getElementWithId(value));
        combo.setBorder(null);
        combo.setSelectedItem(value);
        combo.setBackground(pallet.get("color1"));
        combo.setForeground(pallet.get("color4"));
        combo.setFont(fnt);
        combo.setName(removeCosasRaras(nombre));
        combo.setBounds(230, y, 400, 40);
        add(combo);
        return combo;
    }

    public String removeCosasRaras(String s){
        return s.replace(' ', '_').replace('ó', 'o').replace('é', 'e').replace('á', 'a').replace('í', 'i');
    }

    public HashMap<String, Object> getForm(){
        form = new HashMap<String, Object>();
        Component[] fields = getComponents();
        for (int i = 0; i < fields.length; i++) {
            if (fields[i].getClass().getSimpleName().equalsIgnoreCase("JTextField")) {
                JTextField f = (JTextField) fields[i];
                form.put(f.getName(), f.getText().trim());
            }
            if (fields[i].getClass().getSimpleName().equalsIgnoreCase("JCheckBox")) {
                JCheckBox f = (JCheckBox) fields[i];
                form.put(f.getName(), f.isSelected()?1:0);
            }
            if (fields[i].getClass().getSimpleName().equalsIgnoreCase("JComboBox")) {
                JComboBox f = (JComboBox) fields[i];
                Item item = (Item) f.getSelectedItem();
                form.put(f.getName(), item!=null? item.getCode(): "");
            }
        }
        return form;
    }
}
