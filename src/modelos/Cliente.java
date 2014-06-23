package modelos;

import java.sql.ResultSet;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: cristobal
 * Date: 06/20/14
 * Time: 04:24 PM
 * To change this template use File | Settings | File Templates.
 */
public class Cliente {
    private String nombre, apellido, cedula, direccion, telefono, provincia;
    private double compra_anual;
    private int clienteid, activo;

    public Cliente(ResultSet rs) {
        try {
            while (rs.next()) {
                clienteid = rs.getInt("clienteid");
                nombre = rs.getString("nombre");
                apellido = rs.getString("apellido");
                cedula = rs.getString("cedula");
                direccion = String.valueOf(rs.getString("direccion"));
                telefono = String.valueOf(rs.getString("telefono"));
                provincia = String.valueOf(rs.getString("provincia"));
                compra_anual = rs.getDouble("compra_anual");
                activo = rs.getInt("activo");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Cliente() {
        clienteid = 0;
        nombre = apellido = cedula = direccion = telefono = provincia = "";
        compra_anual = 0.0;
        activo = 1;
    }

    public Cliente(HashMap<String, Object> form) {
        clienteid = Integer.parseInt(String.valueOf(form.get("clienteid")));
        nombre = String.valueOf(form.get("nombre"));
        apellido = String.valueOf(form.get("apellido"));
        cedula = String.valueOf(form.get("cedula"));
        direccion = String.valueOf(form.get("direccion"));
        telefono = String.valueOf(form.get("telefono"));
        provincia = String.valueOf(form.get("provincia"));
        compra_anual = Double.parseDouble(String.valueOf(form.get("compra_anual")));
        activo = Integer.parseInt(String.valueOf(form.get("activo")));
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getCedula() {
        return cedula;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getProvincia() {
        return provincia;
    }

    public double getCompra_anual() {
        return compra_anual;
    }

    public int getClienteid() {
        return clienteid;
    }

    public int getActivo() {
        return activo;
    }

    @Override
    public String toString() {
        return getNombre() + " " + getApellido();
    }

    public boolean isEmpty(){
        return !(getClienteid()>0);
    }
}
