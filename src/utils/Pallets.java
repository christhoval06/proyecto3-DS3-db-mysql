package utils;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: cristobal
 * Date: 06/24/14
 * Time: 04:25 PM
 * To change this template use File | Settings | File Templates.
 */
public class Pallets {
    HashMap<String, Color> pallet = new HashMap<String, Color>();
    public Pallets(String p){
        pallet = fillPallet(p);
    }

    public List<String> getPalletsNames(){
        List<String> palletsNames = new ArrayList<String>();
        palletsNames.add("septermber");
        palletsNames.add("lila");
        palletsNames.add("green");
        palletsNames.add("greyen");
        palletsNames.add("river");
        return palletsNames;
    }

    private HashMap<String, Color> palleteSeptemberEvening(){
        HashMap<String, Color> pallet = new HashMap<String, Color>();
        pallet.put("color1", Color.decode("#fa2020"));
        pallet.put("color2", Color.decode("#801a12"));
        pallet.put("color3", Color.decode("#7cfff9"));
        pallet.put("color4",Color.decode("#3b899b"));
        pallet.put("color5", Color.decode("#3a5055"));
        return pallet;
    }

    private HashMap<String, Color> palleteLila(){
        HashMap<String, Color> pallet = new HashMap<String, Color>();
        pallet.put("color1", Color.decode("#aa92ff"));
        pallet.put("color2", Color.decode("#554980"));
        pallet.put("color3", Color.decode("#393155"));
        pallet.put("color4",Color.decode("#2b2540"));
        pallet.put("color5", Color.decode("#221d33"));
        return pallet;
    }

    private HashMap<String, Color> palleteGreen(){
        HashMap<String, Color> pallet = new HashMap<String, Color>();
        pallet.put("color1", Color.decode("#00aa00"));
        pallet.put("color2", Color.decode("#00bb00"));
        pallet.put("color3", Color.decode("#00cc00"));
        pallet.put("color4",Color.decode("#00dd00"));
        pallet.put("color5", Color.decode("#00ee00"));
        return pallet;
    }

    private HashMap<String, Color> palleteRiver(){
        HashMap<String, Color> pallet = new HashMap<String, Color>();
        pallet.put("color1", Color.decode("#84ffff"));
        pallet.put("color2", Color.decode("#427e80"));
        pallet.put("color3", Color.decode("#2c5455"));
        pallet.put("color4",Color.decode("#213f40"));
        pallet.put("color5", Color.decode("#1a3333"));
        return pallet;
    }

    private HashMap<String, Color> palleteGreyen(){
        HashMap<String, Color> pallet = new HashMap<String, Color>();
        pallet.put("color1", Color.decode("#cfffe4"));
        pallet.put("color2", Color.decode("#688072"));
        pallet.put("color3", Color.decode("#45554c"));
        pallet.put("color4",Color.decode("#344039"));
        pallet.put("color5", Color.decode("#2a332e"));
        return pallet;
    }

    public HashMap<String, Color> getPallet() {
        return pallet;
    }

    public HashMap<String, Color> getPallet(String p) {
        return fillPallet(p);
    }

    private HashMap<String, Color> fillPallet(String p){
        HashMap<String, Color> pallet = null;
        if(p.equalsIgnoreCase("septermber"))
            pallet = palleteSeptemberEvening();
        else if(p.equalsIgnoreCase("lila"))
            pallet = palleteLila();
        else if(p.equalsIgnoreCase("green"))
            pallet = palleteGreen();
        else if(p.equalsIgnoreCase("greyen"))
            pallet = palleteGreyen();
        else if(p.equalsIgnoreCase("river"))
            pallet = palleteRiver();
        return pallet;
    }

    public void setPallets(String p) {
        pallet = getPallet(p);
    }
}
