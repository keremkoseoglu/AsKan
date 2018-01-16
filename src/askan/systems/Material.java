/*
 * Material.java
 *
 * Created on 25 Ekim 2007 Perþembe, 17:26
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package askan.systems;

import askan.Main;
import java.util.*;
import java.sql.*;
import java.io.*;
import com.sap.mw.jco.*;  

/**
 *
 * @author Kerem
 */
public class Material implements DataType {
    
    public enum UNIT_OF_MEASURE { TON, KG, ADT };
    public enum SD_WEIGHT_CALC_ALGO { DOKME, TORBA };
    
    public String
            matnr,
            maktx,
            meins;
    
    public boolean 
            isSellable,
            isPurchaseable;
    public Weight weightPerUnit;
    
    public Division division;
    
    /**
     * Creates a new instance of Material
     */
    public Material() {
    }
    
    public Material(boolean CreateWeight)
    {
        if (CreateWeight) weightPerUnit = new Weight();
    }

    public void fillFromSql(ResultSet R) throws Exception {
        matnr = Sql.getString(R, "MATNR");
        maktx = Sql.getString(R, "MAKTX");
        meins = Sql.getString(R, "MEINS");
        isSellable = Sql.getBoolean(R, "IsSellable");
        isPurchaseable = Sql.getBoolean(R, "IsPurchaseable");
        weightPerUnit = new Weight(R.getDouble("WeightPerUnit"), Material.getUOM(Sql.getString(R, "WeightPerUnitUOM")));
        
        try
        {
            division = new Division();
            division.fillFromID(Sql.getString(R, "SPART"));
        }
        catch(Exception ex)
        {
            division = null;
        }
    }
    
    public void fillFromID(String ID) throws Exception
    {
        ResultSet r = Main.sql.selectData("select * from sap_material where MATNR = '" + ID + "'");
        if (r.next()) fillFromSql(r); else throw new Exception(ID + " malzemesi mevcut deðil");
    }  
    
    public String getDisplayText()
    {
        return maktx + " (" + matnr + ") ";
    }   
    
    public void insert(Sql S) throws Exception
    {
        String command = "";
        command += "INSERT INTO sap_material (MATNR, MAKTX, MEINS, IsSellable, IsPurchaseable, WeightPerUnit, WeightPerUnitUOM, SPART) VALUES (";
        command += "'" + matnr + "', ";
        command += "'" + Sql.sqlFormatText(maktx, 40) + "', ";
        command += "'" + meins + "', ";
        command += "'" + (isSellable ? "X" : "") + "', ";
        command += "'" + (isPurchaseable ? "X" : "") + "', ";
        command += String.valueOf(weightPerUnit.getWeight()) + ", ";
        command += "'" + String.valueOf(Material.getUOM(weightPerUnit.uom)) + "', ";
        command += "'" + (division == null ? "" : division.spart) + "'";
        command += ")";
        
        S.executeQuery(command);
    }    
    
    public SD_WEIGHT_CALC_ALGO getSDWeightCalcAlgo()
    {
        return division.sdWeightCalcAlgo;
    }
    
    public static String formatUOM(String Uom)
    {
        if (Uom == null) return "";
        return Uom.toUpperCase().trim();
    }
    
    public static UNIT_OF_MEASURE getUOM(String Uom)
    {
        UNIT_OF_MEASURE ret = UNIT_OF_MEASURE.KG;
        
        String f = formatUOM(Uom);
        if (f.equals("TON")) ret = UNIT_OF_MEASURE.TON;
        if (f.equals("TO")) ret = UNIT_OF_MEASURE.TON;
        if (f.equals("KG")) ret = UNIT_OF_MEASURE.KG;
        if (f.equals("ST")) ret = UNIT_OF_MEASURE.ADT;
        
        return ret;
    }
    
    public static String getUOM(UNIT_OF_MEASURE Uom)
    {
        if (Uom == UNIT_OF_MEASURE.KG) return "KG";
        if (Uom == UNIT_OF_MEASURE.TON) return "TON";
        if (Uom == UNIT_OF_MEASURE.ADT) return "ST";
        return "";
    }
    
    public static String getSDWeightAlgo(SD_WEIGHT_CALC_ALGO Algo)
    {
        if (Algo == SD_WEIGHT_CALC_ALGO.DOKME) return "D";
        if (Algo == SD_WEIGHT_CALC_ALGO.TORBA) return "T";
        return "";
    }
    
    public static SD_WEIGHT_CALC_ALGO getSDWeightAlgo(String S)
    {
        if (S.equals("D")) return SD_WEIGHT_CALC_ALGO.DOKME;
        if (S.equals("T")) return SD_WEIGHT_CALC_ALGO.TORBA;
        return null;
    }
    
    public static String getUOMText(UNIT_OF_MEASURE Uom)
    {
        if (Uom == UNIT_OF_MEASURE.KG) return "KG";
        if (Uom == UNIT_OF_MEASURE.TON) return "TON";
        if (Uom == UNIT_OF_MEASURE.ADT) return "ADT";
        return "";        
    }
    
    public static double toKG(Weight W)
    {
        if (W.uom == UNIT_OF_MEASURE.TON) return W.getWeight() * 1000; else return W.getWeight();
    }
    
    private static ArrayList getMaterialsWithCondition(String Condition)
    {
        return getMaterialsWithCondition(Condition, "");
    }
    
    private static ArrayList getMaterialsWithCondition(String Condition, String Sort)
    {
        ArrayList ret = new ArrayList();
        
        try
        {
            ResultSet r = Main.sql.selectData("select * from sap_material where " + Condition + (Sort == null || Sort.equals("") ? " order by MAKTX" : " order by " + Sort));
            if (r == null) return ret;
            
            int pos = 0;
            while (r.next())
            {
                Material m = new Material();
                try
                {
                    m.fillFromID(Sql.getString(r, "MATNR"));
                    ret.add(m);
                }
                catch(Exception ex)
                {
                    Main.appendLog("Malzemelerden biri okunurken bir hata oluþtu: " + ex.toString());
                }
                
                pos++;
            }
        }
        catch(Exception ex)
        {
            Main.appendLog("Malzeme listesi çekilirken bir hata oluþtu: " + ex.toString());
        }
        
        return ret;         
    }
    
    public static ArrayList getProducts()
    {
        return getProducts("");
    }
    
    public static ArrayList getProducts(String Sort)
    {
       return getMaterialsWithCondition("IsSellable = 'X'", Sort);
    }    
    
    public static ArrayList getPurchaseables()
    {
        return getMaterialsWithCondition("IsPurchaseable = 'X' and exists (select MATNR from sap_vendor_material where MATNR = sap_material.MATNR)");
    }
    
    public static void deleteAll(Sql S) throws Exception
    {
        S.executeQuery("delete from sap_material");
    }       
    
    public static ArrayList getAll(Sap S)
    {
        ArrayList ret = new ArrayList();
        boolean b = true;
        
        IFunctionTemplate ftemp = S.repository.getFunctionTemplate("ZCZMKAN_GET_MATERIALS");
        JCO.Function function = ftemp.getFunction();
        S.client.execute(function);
        JCO.Table t = function.getTableParameterList().getTable("E_MAT");   
        
        if (t.getNumRows() > 0)
        {
            t.firstRow();
            while (b)
            {
                Material d = new Material(true);
                for (JCO.FieldIterator e = t.fields(); e.hasMoreElements();)
                {
                    JCO.Field field = e.nextField();
                    if (field.getName().equals("MATNR")) d.matnr = field.getString();
                    if (field.getName().equals("MAKTX")) d.maktx = field.getString();
                    if (field.getName().equals("MEINS")) d.meins = field.getString();
                    if (field.getName().equals("SALAB")) d.isSellable = field.getString().equals("X");
                    if (field.getName().equals("PURAB")) d.isPurchaseable = field.getString().equals("X");
                    if (field.getName().equals("WEPEU")) d.weightPerUnit.setWeight(field.getDouble());
                    if (field.getName().equals("WEPEM")) d.weightPerUnit.uom = Material.getUOM(field.getString());
                    if (field.getName().equals("SPART")) 
                    {
                        d.division = new Division();
                        d.division.spart = field.getString();
                    }
                }

                ret.add(d);
                b = t.nextRow();
            }    
        }
        
        return ret;        
    }   
    
}
