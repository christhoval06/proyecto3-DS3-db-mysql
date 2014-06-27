package utils;

import javax.swing.*;

/**
 * Created with IntelliJ IDEA.
 * User: cristobal
 * Date: 06/26/14
 * Time: 05:03 PM
 * To change this template use File | Settings | File Templates.
 */
public class MyButtonGroup extends ButtonGroup {
    private String name;
    public MyButtonGroup(String n){
        name = n;
    }

    public String getName() {
        return name;
    }
}
