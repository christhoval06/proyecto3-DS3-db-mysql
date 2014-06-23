package modelos;

/**
 * Created with IntelliJ IDEA.
 * User: cristobal
 * Date: 06/23/14
 * Time: 02:11 PM
 * To change this template use File | Settings | File Templates.
 */
public class Item {
    private String code, nombre;

    public Item(String c, String n){
        code = c;
        nombre = n;
    }

    public String getCode() {
        return code;
    }

    public String getNombre() {
        return nombre;
    }

    @Override
    public String toString() {
        return getNombre();
    }
}
