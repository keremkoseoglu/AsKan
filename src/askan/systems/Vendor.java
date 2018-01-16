/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package askan.systems;

import askan.*;
import java.sql.*;
import java.util.*;
import java.io.*;
import com.sap.mw.jco.*;  

/**
 *
 * @author Kerem
 */
public class Vendor implements DataType {

    public String
            lifnr,
            name1,
            address,
            lzone;
    
    public ArrayList
            senders,
            materials;
    
    public boolean
            isPurchaseable;
    
    public Vendor()
    {
        lifnr = "";
        name1 = "";
        address = "";
        lzone = "";
        senders = new ArrayList();
        materials = new ArrayList();
        isPurchaseable = false;
    }
    
    @Override
    public void fillFromSql(ResultSet R) throws Exception {
        fillFromSql(R, true);
    }

    @Override
    public void fillFromID(String ID) throws Exception {
        fillFromID(ID, true);
    }

    @Override
    public void insert(Sql S) throws Exception {
        String command = "";
        command += "INSERT INTO sap_vendor (LIFNR, NAME1, Address, LZONE, IsPurchaseable) values (";
        command += "'" + lifnr + "', ";
        command += "'" + Sql.sqlFormatText(name1, 35) + "', ";
        command += "'" + Sql.sqlFormatText(address, 500) + "', ";
        command += "'" + Sql.sqlFormatText(lzone, 10) + "', ";
        command += "'" + (isPurchaseable ? "X" : "") + "'";
        command += ")";
        S.executeQuery(command);
        
        for (int n = 0; n < senders.size(); n++)
        {
            Vendor v = (Vendor) senders.get(n);
            
            command = "";
            command = "INSERT INTO sap_vendor_sender (LIFNR, LIFN2) VALUES (";
            command += " '" + v.lifnr + "', ";
            command += " '" + lifnr + "' ";
            command += ")";
            S.executeQuery(command);
        }
        
        for (int n = 0; n < materials.size(); n++)
        {
            Material m = (Material) materials.get(n);
            
            command = "";
            command = "INSERT INTO sap_vendor_material (LIFNR, MATNR) VALUES (";
            command += " '" + lifnr + "', ";
            command += " '" + m.matnr + "' ";
            command += ")";
            S.executeQuery(command);
        }
    }

    @Override
    public String getDisplayText() {
        return name1 + " (" + lifnr + ")";
    }
    
    public void fillFromID(String ID, boolean ReadSenders) throws Exception
    {
        fillFromID(ID, ReadSenders, true);
    }
    
    public void fillFromID(String ID, boolean ReadSenders, boolean ReadMaterials) throws Exception {
        ResultSet r = Main.sql.selectData("select * from sap_vendor where LIFNR = '" + ID + "'");
        if (r == null) return;
        while (r.next()) fillFromSql(r, ReadSenders, ReadMaterials);
    }    
    
    public void fillFromSql(ResultSet R, boolean ReadSenders) throws Exception
    {
        fillFromSql(R, ReadSenders, true);
    }
    
    public void fillFromSql(ResultSet R, boolean ReadSenders, boolean ReadMaterials) throws Exception
    {
        lifnr = Sql.getString(R, "LIFNR");
        name1 = Sql.getString(R, "NAME1");
        address = Sql.getString(R, "Address");        
        isPurchaseable = Sql.getBoolean(R, "IsPurchaseable");
        
        if (ReadSenders)
        {
            try
            {
                senders = new ArrayList();
                ResultSet r = Main.sql.selectData("select * from sap_vendor_sender where LIFN2 = '" + lifnr + "'");
                if (r != null) 
                {
                    int pos = 0;
                    while (r.next())
                    {
                        Vendor c = new Vendor();
                        try
                        {
                            c.fillFromID(Sql.getString(r, "LIFNR"), false, false);
                            senders.add(c);
                        }
                        catch(Exception ex)
                        {
                            Main.appendLog(lifnr + " alýcýlarýndan birinde problem var: " + ex.toString());
                        }

                        pos++;
                    }
                }
            }
            catch(Exception ex)
            {
                Main.appendLog(lifnr + " satýcýsýnýn alýcýlarýný bulurken hata oluþtu: " + ex.toString());
            }   
        }     
        
        if (ReadMaterials)
        {
            try
            {
                materials = new ArrayList();
                ResultSet r = Main.sql.selectData("select sap_material.MATNR from sap_material, sap_vendor_material where sap_material.MATNR = sap_vendor_material.MATNR and sap_vendor_material.LIFNR = '" + lifnr + "' order by sap_material.MAKTX");
                if (r != null) 
                {
                    int pos = 0;
                    while (r.next())
                    {
                        Material c = new Material();
                        try
                        {
                            c.fillFromID(Sql.getString(r, "MATNR"));
                            materials.add(c);
                        }
                        catch(Exception ex)
                        {
                            Main.appendLog(lifnr + " malzemelerinden birinde problem var: " + ex.toString());
                        }

                        pos++;
                    }
                }
            }
            catch(Exception ex)
            {
                Main.appendLog(lifnr + " satýcýsýnýn malzemelerini bulurken hata oluþtu: " + ex.toString());
            }   
        }           
    }
    
    public static void deleteAll(Sql S) throws Exception
    {
        S.executeQuery("delete from sap_vendor");
        S.executeQuery("delete from sap_vendor_sender");
        S.executeQuery("delete from sap_vendor_material");
    }     
    
    public static ArrayList getAll()
    {
        return getAll(true);
    }
    
    public static ArrayList getAllOfMaterial(String MATNR)
    {
                ArrayList ret = new ArrayList();
        
        try
        {
            String command = "select distinct sap_vendor.LIFNR, sap_vendor.NAME1 from sap_vendor_material inner join sap_vendor on sap_vendor.LIFNR = sap_vendor_material.LIFNR";
            command += " where sap_vendor_material.MATNR = '" + MATNR + "'";
            command += " order by sap_vendor.NAME1";
            ResultSet r = Main.sql.selectData(command);
            if (r == null) return ret;
            
            while (r.next())
            {
                Vendor c = new Vendor();
                try
                {
                    c.fillFromID(Sql.getString(r, "LIFNR"), true, true);
                    ret.add(c);
                }
                catch(Exception ex)
                {
                    Main.appendLog(MATNR + " malzemesinden bulunan satýcýlardan birinde problem var: " + ex.toString());
                }
            }
        }
        catch(Exception ex)
        {
            Main.appendLog(MATNR + " malzemesinin satýcý listesi çekilirken hata oluþtu: " + ex.toString());
        }
        
        return ret; 
    }
    
    public static ArrayList getAll(boolean IncludeNotPurchaseables)
    {
        ArrayList ret = new ArrayList();
        
        try
        {
            String command = "select LIFNR from sap_vendor";
            if (!IncludeNotPurchaseables) command += " where IsPurchaseable = 'X'";
            command += " order by NAME1";
            ResultSet r = Main.sql.selectData(command);
            if (r == null) return ret;
            
            while (r.next())
            {
                Vendor c = new Vendor();
                try
                {
                    c.fillFromID(Sql.getString(r, "LIFNR"), true, true);
                    ret.add(c);
                }
                catch(Exception ex)
                {
                    Main.appendLog("Bulunan satýcýlardan birinde problem var: " + ex.toString());
                }
            }
        }
        catch(Exception ex)
        {
            Main.appendLog("Satýcý listesi çekilirken hata oluþtu: " + ex.toString());
        }
        
        return ret;        
    }
    
    public static ArrayList getAll(Sap S) throws Exception
    {
        ArrayList ret = new ArrayList();
        boolean b = true;
        
        IFunctionTemplate ftemp = S.repository.getFunctionTemplate("ZCZMKAN_GET_VENDORS");
        JCO.Function function = ftemp.getFunction();
        S.client.execute(function);
        
        JCO.Table t = function.getTableParameterList().getTable("E_VENDOR");   
        JCO.Table s = function.getTableParameterList().getTable("E_ADDRESS");   
        JCO.Table m = function.getTableParameterList().getTable("E_MATERIAL"); 
        
        if (t.getNumRows() > 0)
        {
            t.firstRow();
            while (b)
            {
                Vendor d = new Vendor();
                for (JCO.FieldIterator e = t.fields(); e.hasMoreElements();)
                {
                    JCO.Field field = e.nextField();
                    if (field.getName().equals("LIFNR")) d.lifnr = field.getString();
                    if (field.getName().equals("NAME1")) d.name1 = field.getString();
                    if (field.getName().equals("ADDRE")) d.address = field.getString();
                    if (field.getName().equals("LZONE")) d.lzone = field.getString();
                    if (field.getName().equals("PURAB")) d.isPurchaseable = field.getString().equals("X");
                }
                
                // Adresler
                if (s.getNumRows() > 0)
                {
                    s.firstRow();
                    boolean bs = true;
                    while (bs)
                    {
                        Vendor d2 = new Vendor();
                        boolean bsValid = false;
                        for (JCO.FieldIterator e = s.fields(); e.hasMoreElements();)
                        {
                            JCO.Field field = e.nextField();
                            if (field.getName().equals("LIFNR")) d2.lifnr = field.getString();
                            if (field.getName().equals("LIFN2")) 
                            {
                                bsValid = field.getString().equals(d.lifnr);
                            }
                        }
                        
                        if (bsValid) d.senders.add(d2);
                        bs = s.nextRow();
                    }    
                }             
                
                // Adresler
                if (m.getNumRows() > 0)
                {
                    m.firstRow();
                    boolean bs = true;
                    while (bs)
                    {
                        Material mat = new Material();
                        boolean bsValid = false;
                        for (JCO.FieldIterator e = m.fields(); e.hasMoreElements();)
                        {
                            JCO.Field field = e.nextField();
                            if (field.getName().equals("MATNR")) mat.matnr = field.getString();
                            if (field.getName().equals("LIFNR")) 
                            {
                                bsValid = field.getString().equals(d.lifnr);
                            }
                        }
                        
                        if (bsValid) d.materials.add(mat);
                        bs = m.nextRow();
                    }    
                }                    
                
                // Satýcýyý ekleyelim
                ret.add(d);
                b = t.nextRow();
            }    
        }
        
        return ret;        
    }        

}
