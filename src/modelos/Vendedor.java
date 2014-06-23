package modelos;

import java.sql.ResultSet;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: cristobal
 * Date: 06/23/14
 * Time: 09:42 AM
 * To change this template use File | Settings | File Templates.
 */
public class Vendedor {
    private String codigo, nombre, apellido, departamento, cargo;
    private double venta_mensual, venta_anual;
    private int vendedorid, activo;

    public Vendedor(ResultSet rs) {
        try {
            while (rs.next()) {
                vendedorid = rs.getInt("vendedorid");
                nombre = rs.getString("nombre");
                apellido = rs.getString("apellido");
                codigo = rs.getString("codigo");
                cargo = String.valueOf(rs.getString("cargo"));
                departamento = String.valueOf(rs.getString("departamento"));
                venta_mensual = rs.getDouble("venta_mensual");
                venta_anual = rs.getDouble("venta_anual");
                activo = rs.getInt("activo");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Vendedor() {
        vendedorid = 0;
        nombre = apellido = codigo = cargo = departamento = "";
        venta_mensual = venta_anual = 0.0;
        activo = 1;
    }

    public Vendedor(HashMap<String, Object> form) {
        vendedorid = Integer.parseInt(String.valueOf(form.get("vendedorid")));
        nombre = String.valueOf(form.get("nombre"));
        apellido = String.valueOf(form.get("apellido"));
        codigo = String.valueOf(form.get("codigo"));
        cargo = String.valueOf(form.get("cargo"));
        departamento = String.valueOf(form.get("departamento"));
        venta_mensual = Double.parseDouble(String.valueOf(form.get("venta_mensual")));
        venta_anual = Double.parseDouble(String.valueOf(form.get("venta_anual")));
        activo = Integer.parseInt(String.valueOf(form.get("activo")));
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getDepartamento() {
        return departamento;
    }

    public String getCargo() {
        return cargo;
    }

    public double getVenta_mensual() {
        return venta_mensual;
    }

    public double getVenta_anual() {
        return venta_anual;
    }

    public int getVendedorid() {
        return vendedorid;
    }

    public int getActivo() {
        return activo;
    }

    @Override
    public String toString() {
        return getNombre() + " " + getApellido();
    }

    public boolean isEmpty(){
        return !(getVendedorid()>0);
    }
}
