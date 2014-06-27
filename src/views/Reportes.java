package views;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;
import utils.DB;
import utils.FormPanel;
import utils.Pallets;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * Created with IntelliJ IDEA.
 * User: cristobal
 * Date: 06/26/14
 * Time: 02:43 PM
 * To change this template use File | Settings | File Templates.
 */
public class Reportes extends FormPanel {

    private DB db;
    private JFrame frame;
    private HashMap<String, Color> pallet;
    private Pallets palletsColors;
    private JasperPrint jasperPrint = null;

    public Reportes(JFrame f, DB d, Pallets p) {
        super(p);
        frame = f;
        db = d;
        palletsColors = p;
        pallet = palletsColors.getPallet();
        setBounds(0, 0, frame.getWidth(), frame.getHeight());
        setLayout(null);
        setBackground(pallet.get("color3"));
    }


    public void cliente(){
        removeAll();
        final Map parametros = new HashMap();
        parametros.put("ORDEN","cedula");
        Font fnt = new Font(null, Font.BOLD, 18);
        createRadioButtonGroupAndLabel("orden", new String[]{"cédula", "nombre", "apellido", "compra anual"}, "cédula", new Point(50, 100), new Dimension(200,30), fnt);
        createBtn("Reporte", new Point(300, 200), new Dimension(200, 80), new Callable<Void>() {
            @Override
            public Void call() throws Exception {

                try {
                    jasperPrint = JasperFillManager.fillReport(db.getResourcePath("clientes.jasper"), parametros, db.getCon());
                    JasperViewer jasperViewer = new JasperViewer(jasperPrint,false);
                    jasperViewer.setVisible(true);
                } catch (JRException e) {
                    e.printStackTrace();
                }
                return null;
            }
        });
        repaint();
    }
}
