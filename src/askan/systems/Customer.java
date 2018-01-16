/*
 * Customer.java
 *
 * Created on 25 Ekim 2007 Perþembe, 17:20
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package askan.systems;

import askan.*;
import java.util.*;
import java.sql.*;
import java.io.*;
import com.sap.mw.jco.*;  


/**
 *
 * @author Kerem
 */
public class Customer implements DataType {

    /**
     * Customer code
     */
    public String kunnr;

    /**
     * Customer name
     */
    public String name1;
    
    /**
     * Address of customer
     */
    public String
            address;

    /**
     * Tax office
     */
    public String stcd1;

    /**
     * Tax code
     */
    public String stcd2;

    /**
     * Is customer blocked?
     */
    public boolean isBlocked;
    
    public String lzone;
    
    /**
     * Does customer have bad credit
     */
    public boolean
            hasBadCredit;
    
    public boolean
            isSellable;
    
    /**
     * Receiving customers of this customer. Used for address purposes.
     */
    public ArrayList receivers;
    
    public ArrayList vehicles;
    
    /**
     * Creates a new instance of Customer
     */
    public Customer() {
        kunnr = "";
        name1 = "";
        lzone = "";
        isBlocked = false;
        receivers = new ArrayList();
        vehicles = new ArrayList();
    }
    
    @Override
    public void fillFromSql(ResultSet R) throws Exception {
        fillFromSql(R, true);
    }
   
    @Override
    public void insert(Sql S) throws Exception
    {
        String command = "";
        command += "INSERT INTO sap_customer (KUNNR, NAME1, Address, STCD1, STCD2, LZONE) values (";
        command += "'" + kunnr + "', ";
        command += "'" + Sql.sqlFormatText(name1, 35) + "', ";
        command += "'" + Sql.sqlFormatText(address, 500) + "', ";
        command += "'" + Sql.sqlFormatText(stcd1, 16) + "', ";
        command += "'" + Sql.sqlFormatText(stcd2, 11) + "', ";
        command += "'" + Sql.sqlFormatText(lzone, 10) + "'";
        command += ")";
        S.executeQuery(command);
        
        command = "";
        command += "INSERT INTO sap_customer_comp (KUNNR, WERKS, IsBlocked, HasBadCredit) values (";
        command += "'" + kunnr + "', ";
        command += "'" + Main.config.steelyardParam.werks + "', ";
        command += "'" + (isBlocked ? "X" : "") + "', ";
        command += "'" + (hasBadCredit ? "X" : "") + "' ";
        command += ")";
        S.executeQuery(command);
        
        for (int n = 0; n < receivers.size(); n++)
        {
            Customer rece = (Customer) receivers.get(n);
            command = "";
            command += "INSERT INTO sap_customer_receiver (KUNNR, KUNWE) values (";
            command += "'" + kunnr + "', ";
            command += "'" + rece.kunnr + "'";
            command += ")";
            S.executeQuery(command);   
        }
        
        for (int n = 0; n < vehicles.size(); n++)
        {
            String ve = (String) vehicles.get(n);
            command = "";
            command += "INSERT INTO sap_plate_customer (TRMTYP, KUNNR) values (";
            command += "'" + ve + "', ";
            command += "'" + kunnr + "'";
            command += ")";
            S.executeQuery(command);   
        }      
        
    }
    
    public void fillFromSql(ResultSet R, boolean ReadReceivers) throws Exception  
    {
        fillFromSql(R, ReadReceivers, false);
    }

    public void fillFromSql(ResultSet R, boolean ReadReceivers, boolean ReadVehicles) throws Exception {
        kunnr = Sql.getString(R, "KUNNR");
        name1 = Sql.getString(R, "NAME1");
        address = Sql.getString(R, "Address");
        stcd1 = Sql.getString(R, "STCD1");
        stcd2 = Sql.getString(R, "STCD2");
        lzone = Sql.getString(R, "LZONE");
        
        ResultSet rb = Main.sql.selectData("select * from sap_customer_comp where KUNNR = '" + kunnr + "' and WERKS = '" + Main.config.steelyardParam.werks + "'");
        if (rb != null && rb.next())
        {
            isBlocked = Sql.getBoolean(rb, "IsBlocked");
            hasBadCredit = Sql.getBoolean(rb, "HasBadCredit");
        }
        else
        {
            isBlocked = false;
            hasBadCredit = false;
        }
        
        if (ReadReceivers)
        {
            try
            {
                receivers = new ArrayList();
                ResultSet r = Main.sql.selectData("select * from sap_customer_receiver where KUNNR = '" + kunnr + "'");
                if (r != null) 
                {
                    int pos = 0;
                    while (r.next())
                    {
                        Customer c = new Customer();
                        try
                        {
                            c.fillFromID(Sql.getString(r, "KUNWE"), false);
                            receivers.add(c);
                        }
                        catch(Exception ex)
                        {
                            Main.appendLog(kunnr + " alýcýlarýndan birinde problem var: " + ex.toString());
                        }

                        pos++;
                    }
                }
            }
            catch(Exception ex)
            {
                Main.appendLog(kunnr + " müþterisinin alýcýlarýný bulurken hata oluþtu: " + ex.toString());
            }   
        }
        
        if (ReadVehicles)
        {
            try
            {
                vehicles = new ArrayList();
                ResultSet r = Main.sql.selectData("select * from sap_plate_customer where KUNNR = '" + kunnr + "'");
                if (r != null) 
                {
                    int pos = 0;
                    while (r.next())
                    {
                        String c = new String();
                        try
                        {
                            c = Sql.getString(r, "TRMTYP");
                            vehicles.add(c);
                        }
                        catch(Exception ex)
                        {
                            Main.appendLog(kunnr + " araçlarýndan birinde problem var: " + ex.toString());
                        }

                        pos++;
                    }
                }
            }
            catch(Exception ex)
            {
                Main.appendLog(kunnr + " müþterisinin araçlarýný bulurken hata oluþtu: " + ex.toString());
            }   
        }        
    }
    
    @Override
    public void fillFromID(String ID) throws Exception
    {
        fillFromID(ID, true);
    }
    
    public void fillFromID(String ID, boolean ReadReceivers) throws Exception
    {
        ResultSet r = Main.sql.selectData("select * from sap_customer where KUNNR = '" + ID + "'");
        if (r.next()) fillFromSql(r, ReadReceivers); else throw new Exception(ID + " müþterisi mevcut deðil");
    }
    
    @Override
    public String getDisplayText()
    {
        return name1 + " - " + lzone + " (" + kunnr + ") ";
    }
    
    /**
     * Returns an array of "Customer()"s who use this plate number
     * @param Plate String containing the plate number
     * @return An arraylist containing Customer() classes
     */
    public static ArrayList getCustomersOfPlate(String Plate)
    {
        return getCustomers(Plate);
    }           
    
    public static ArrayList getCustomers(String Plate)
    {
        ArrayList ret = new ArrayList();
        
        try
        {
            String command = "select * from " + (Plate != null && !Plate.equals("") ? "sap_plate_customer" : "sap_customer");
            if (Plate != null && !Plate.equals("")) 
            {
                command += " where TRMTYP = '" + Sql.sqlFormatText(Util.formatPlate(Plate), 18) + "'";
            }
            else
            {
                command += " where not exists (select KUNWE from sap_customer_receiver where KUNNR <> KUNWE and KUNWE = sap_customer.KUNNR) order by NAME1";
            }
            ResultSet r = Main.sql.selectData(command);
            if (r == null) return ret;
            
            int pos = 0;
            while (r.next())
            {
                Customer c = new Customer();
                try
                {
                    c.fillFromID(Sql.getString(r, "KUNNR"));
                    ret.add(c);
                }
                catch(Exception ex)
                {
                    Main.appendLog(Plate + " plakasýndan bulunan müþterilerden birinde problem var: " + ex.toString());
                }
                
                pos++;
            }
        }
        catch(Exception ex)
        {
            Main.appendLog(Plate + " plakasýndan müþteri bulurken hata oluþtu: " + ex.toString());
        }
        
        return ret;        
    }
    
    public static void deleteAll(Sql S) throws Exception
    {
        S.executeQuery("delete from sap_customer");
        S.executeQuery("delete from sap_customer_receiver");
        S.executeQuery("delete from sap_plate_customer");
        S.executeQuery("delete from sap_customer_comp where WERKS = '" + Main.config.steelyardParam.werks + "'");
    }    
    
    public static ArrayList getAll(Sap S) throws Exception
    {
        ArrayList ret = new ArrayList();
        boolean b = true;
        
        IFunctionTemplate ftemp = S.repository.getFunctionTemplate("ZCZMKAN_GET_CUSTOMERS");
        JCO.Function function = ftemp.getFunction();
        function.getImportParameterList().setValue(Main.config.steelyardParam.werks, "I_WERKS");
        S.client.execute(function);
        JCO.Table t = function.getTableParameterList().getTable("E_CUST");   
        JCO.Table rece = function.getTableParameterList().getTable("E_RECE");   
        JCO.Table vehi = function.getTableParameterList().getTable("E_PLATE");
        
        if (t.getNumRows() > 0)
        {
            t.firstRow();
            while (b)
            {
                Customer d = new Customer();
                for (JCO.FieldIterator e = t.fields(); e.hasMoreElements();)
                {
                    JCO.Field field = e.nextField();
                    if (field.getName().equals("KUNNR")) d.kunnr = field.getString();
                    if (field.getName().equals("NAME1")) d.name1 = field.getString();
                    if (field.getName().equals("BLOCK")) d.isBlocked = field.getString().equals("X");
                    if (field.getName().equals("BADCR")) d.hasBadCredit = field.getString().equals("X");
                    if (field.getName().equals("STCD1")) d.stcd1 = field.getString();
                    if (field.getName().equals("STCD2")) d.stcd2 = field.getString();
                    if (field.getName().equals("LZONE")) d.lzone = field.getString();
                    if (field.getName().equals("ADDRE")) d.address = field.getString();
                }
                
                if (rece.getNumRows() > 0)
                {
                    rece.firstRow();
                    boolean bi = true;
                    while (bi)
                    {
                        Customer di = new Customer();
                        boolean canBeAdded = true;
                        for (JCO.FieldIterator e = rece.fields(); e.hasMoreElements();)
                        {
                            JCO.Field field = e.nextField();
                            if (field.getName().equals("KUNNR") && !field.getString().equals(d.kunnr)) canBeAdded = false;
                            if (canBeAdded)
                            {
                                if (field.getName().equals("KUNWE")) di.kunnr = field.getString();
                            }
                        }
                        
                        if (canBeAdded) d.receivers.add(di);
                        bi = rece.nextRow();
                    }
                }
                
                if (vehi.getNumRows() > 0)
                {
                    vehi.firstRow();
                    boolean bi = true;
                    while (bi)
                    {
                        String plate = "";
                        boolean canBeAdded = true;
                        for (JCO.FieldIterator e = vehi.fields(); e.hasMoreElements();)
                        {
                            JCO.Field field = e.nextField();
                            if (field.getName().equals("KUNNR") && !field.getString().equals(d.kunnr)) canBeAdded = false;
                            if (canBeAdded)
                            {
                                if (field.getName().equals("TRMTYP")) plate = field.getString();
                            }
                        }
                        
                        if (canBeAdded) d.vehicles.add(plate);
                        bi = vehi.nextRow();
                    }
                }                
                
                
                ret.add(d);
                b = t.nextRow();
            }    
        }
        
        return ret;        
    }         
    
}
