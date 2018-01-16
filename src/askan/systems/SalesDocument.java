/*
 * SalesDocument.java
 *
 * Created on 26 Ekim 2007 Cuma, 11:54
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package askan.systems;

import askan.Main;
import java.sql.*;
import java.util.*;
import java.io.*;
import com.sap.mw.jco.*;  

/**
 *
 * @author Kerem
 */
public class SalesDocument implements DataType {
    
    public enum DOCUMENT_TYPE { ORDER, DELIVERY };
    
    public String
            vbeln,      // Belge numarasý
            trmtyp;     // Plaka
    
    public Customer
            custOwner;      // Sipariþ veren
    
    private Customer custReceiver;
    
    public DOCUMENT_TYPE docType;
    public IncoTerms incoTerms;
    
    public ArrayList items;
    
    /**
     * Creates a new instance of SalesDocument
     */
    public SalesDocument() {
        custOwner = null;
        custReceiver = null;
        incoTerms = null;
        items = new ArrayList();
    }

    @Override
    public void fillFromSql(ResultSet R) throws Exception {
        vbeln = Sql.getString(R, "VBELN");
        trmtyp = Sql.getString(R, "TRMTYP");
        
        custOwner = new Customer();
        custOwner.fillFromID(Sql.getString(R, "KUNNR"));
        
        if (Sql.getString(R, "KUNWE").equals(""))
        {
            custReceiver = null;
        }
        else
        {
            custReceiver = new Customer();
            custReceiver.fillFromID(Sql.getString(R, "KUNWE"));
        }
        
        if (Sql.getString(R, "INCO1").equals(""))
        {
            incoTerms = null;
        }
        else
        {
            incoTerms = new IncoTerms();
            incoTerms.fillFromID(Sql.getString(R, "INCO1"));
        }        
        
        if (Sql.getString(R, "dtype").equals("O")) docType = DOCUMENT_TYPE.ORDER;
        if (Sql.getString(R, "dtype").equals("D")) docType = DOCUMENT_TYPE.DELIVERY;
        
        items = new ArrayList();
        ResultSet ri = Main.sql.selectData("select * from sap_sales_document_item where VBELN = '" + vbeln + "'");
        while (ri.next())
        {
            SalesDocumentItem di = new SalesDocumentItem();
            di.fillFromSql(ri);
            items.add(di);
        }
    }
    
    @Override
    public void fillFromID(String ID) throws Exception
    {
        ResultSet r = Main.sql.selectData("select * from sap_sales_document_head where VBELN = '" + ID + "'");
        if (r.next()) fillFromSql(r); else throw new Exception(ID + " belgesi mevcut deðil");        
    }    
    
    @Override
    public String getDisplayText()
    {
        return vbeln;
    }    
    
    public static void deleteAll(Sql S) throws Exception
    {
        S.executeQuery("delete from sap_sales_document_head");
        S.executeQuery("delete from sap_sales_document_item");
    }
    
    @Override
    public void insert(Sql S) throws Exception
    {
        String command = "";
        command += "INSERT INTO sap_sales_document_head (";
        command += " [VBELN]";
        command += " ,[TRMTYP]";
        command += " ,[KUNNR]";
        command += " ,[KUNWE]";
        command += " ,[dtype]";
        command += " ,[INCO1]";
        command += ") VALUES (";
        command += " '" + vbeln + "'";
        command += " ,'" + trmtyp + "'";
        command += " ,'" + (custOwner == null ? "" : custOwner.kunnr) + "'";
        command += " ,'" + (custReceiver == null ? "" : custReceiver.kunnr) + "'";
        command += " ,'" + getDocumentTypeText() + "'";
        command += " ,'" + (incoTerms == null ? "" : incoTerms.inco1) + "'";
        command += ")";
        
        S.executeQuery(command);
        
        for (int n = 0; n < items.size(); n++)
        {
            SalesDocumentItem sdi = (SalesDocumentItem) items.get(n);
            command = "";
            command += "INSERT INTO sap_sales_document_item (";
            command += " [VBELN]";
            command += " ,[POSNR]";
            command += " ,[MATNR]";
            command += " ,[LFIMG]";
            command += " ,[VRKME]";
            command += " ,[TRMTYP]";
            command += " ,[KUNWE]";
            command += " ) VALUES (";
            command += " '" + vbeln + "'";
            command += " ,'" + sdi.posnr + "'";
            command += " ,'" + (sdi.material == null ? "" : sdi.material.matnr) + "'";
            command += " ," + sdi.lfimg;
            command += " ,'" + sdi.vrkme + "'";
            command += " ,'" + sdi.trmtyp + "'";
            command += " ,'" + (sdi.custReceiver == null ? "" : sdi.custReceiver.kunnr) + "'";
            command += ")";
            
            S.executeQuery(command);
        }
    }    
    
    public SalesDocumentItem getItem(String MATNR)
    {
        SalesDocumentItem ret = null;
        for (int n = 0; n < items.size(); n++)
        {
            SalesDocumentItem d = (SalesDocumentItem) items.get(n);
            if (d.material.matnr.equals(MATNR)) ret = d;
        }
        
        return ret;
    }
    
    public String getDocumentTypeText()
    {
        String ret = "";
        
        if (docType == DOCUMENT_TYPE.DELIVERY) ret = "D";
        if (docType == DOCUMENT_TYPE.ORDER) ret = "O";
        
        return ret;
    }
    
    public Customer getReceiver(String Matnr)
    {
        Customer ret = null;
        
        if (docType == SalesDocument.DOCUMENT_TYPE.ORDER)
        {
            SalesDocumentItem sdi = getItem(Matnr);
            ret = sdi.custReceiver;
        }
        else
        {
            ret = custReceiver;
        }      
        
        return ret;
    }    
    
    public static ArrayList getDocumentsOfPlate(String Plate)
    {
        return getDeliveriesOfPlate(Plate, null);
    }     
    
    public static ArrayList getDeliveriesOfPlate(String Plate, String KUNNR)
    {
        String query = "";
        ArrayList ret = new ArrayList();
        
        query = "select * from sap_sales_document_head where ";
        query += " (";
        query += " ( dtype = 'D' and TRMTYP = '" + Sql.sqlFormatText(Util.formatPlate(Plate), 18) + "' )";
        query += " OR";
        query += " ( dtype = 'O' and exists (select VBELN from sap_sales_document_item where VBELN = sap_sales_document_head.VBELN and TRMTYP = '" + Sql.sqlFormatText(Util.formatPlate(Plate), 18) + "') )";
        query += " ) AND";
        query += " (processed is null or processed <> 'X')";
        if (KUNNR != null) query += " and KUNNR = '" + Sql.sqlFormatText(KUNNR, 10) + "'";
        
        try
        {
            ResultSet r = Main.sql.selectData(query);
            if (r == null) return ret;
            
            int pos = 0;
            while (r.next())
            {
                SalesDocument d = new SalesDocument();
                try
                {
                    d.fillFromSql(r);
                    ret.add(d);
                }
                catch(Exception ex)
                {
                    Main.appendLog(Plate + " plakasýndan bulunan belgelerden birinde problem var: " + ex.toString());
                }
                
                pos++;
            }
        }
        catch(Exception ex)
        {
            Main.appendLog(Plate + " plakasýndan belge bulurken hata oluþtu: " + ex.toString());
        }
        
        return ret;
    }      
    
    public static ArrayList getDeliveryCustomersOfPlate(String Plate)
    {
        ArrayList ret = new ArrayList();
        
        try
        {
            ResultSet r = Main.sql.selectData("select distinct KUNNR from sap_sales_document_head where TRMTYP = '" + Sql.sqlFormatText(Util.formatPlate(Plate), 18) + "' and (processed is null or processed <> 'X')");
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
                    Main.appendLog(Plate + " plakasýndan bulunan belgenin müþterilerden birinde problem var: " + ex.toString());
                }
                
                pos++;
            }
        }
        catch(Exception ex)
        {
            Main.appendLog(Plate + " plakasýndan belge müþterisi bulurken hata oluþtu: " + ex.toString());
        }
        
        return ret;        
    }
    
    public static ArrayList getAll(Sap S)
    {
        ArrayList ret = new ArrayList();
        boolean b = true;
        
        IFunctionTemplate ftemp = S.repository.getFunctionTemplate("ZCZMKAN_GET_DOCUMENTS");
        JCO.Function function = ftemp.getFunction();
        S.client.execute(function);
        JCO.Table head = function.getTableParameterList().getTable("E_HEAD");   
        JCO.Table item = function.getTableParameterList().getTable("E_ITEM");
        
        if (head.getNumRows() > 0)
        {
            head.firstRow();
            while (b)
            {
                SalesDocument d = new SalesDocument();
                for (JCO.FieldIterator e = head.fields(); e.hasMoreElements();)
                {
                    JCO.Field field = e.nextField();
                    if (field.getName().equals("VBELN")) d.vbeln = field.getString();
                    if (field.getName().equals("TRMTYP")) d.trmtyp = field.getString();
                    if (field.getName().equals("KUNNR")) 
                    {
                        d.custOwner = new Customer();
                        try 
                        { 
                            d.custOwner.fillFromID(field.getString());
                        }
                        catch(Exception ex) { d.custOwner.kunnr = field.getString(); }
                    }
                    if (field.getName().equals("KUNWE")) 
                    {
                        d.custReceiver = new Customer();
                        try 
                        { 
                            d.custReceiver.fillFromID(field.getString());
                        }
                        catch(Exception ex) { d.custReceiver.kunnr = field.getString(); }
                    }  
                    if (field.getName().equals("INCO1")) 
                    {
                        d.incoTerms = new IncoTerms();
                        try 
                        { 
                            d.incoTerms.fillFromID(field.getString());
                        }
                        catch(Exception ex) { d.incoTerms.inco1 = field.getString(); }
                    }                       
                    if (field.getName().equals("DTYPE")) d.docType = (field.getString().equals("D") ? DOCUMENT_TYPE.DELIVERY : DOCUMENT_TYPE.ORDER);
                }
                
                if (item.getNumRows() > 0)
                {
                    item.firstRow();
                    boolean bi = true;
                    while (bi)
                    {
                        SalesDocumentItem di = new SalesDocumentItem();
                        boolean canBeAdded = true;
                        for (JCO.FieldIterator e = item.fields(); e.hasMoreElements();)
                        {
                            JCO.Field field = e.nextField();
                            if (field.getName().equals("VBELN") && !field.getString().equals(d.vbeln)) canBeAdded = false;
                            if (field.getName().equals("DTYPE") && !field.getString().equals(d.getDocumentTypeText())) canBeAdded = false;
                            if (canBeAdded)
                            {
                                if (field.getName().equals("POSNR")) di.posnr = field.getString();
                                if (field.getName().equals("MATNR")) 
                                {
                                    di.material = new Material();
                                    try
                                    {
                                        di.material.fillFromID(field.getString());
                                    }
                                    catch(Exception ex) { di.material.matnr = field.getString(); }
                                }
                                if (field.getName().equals("LFIMG")) di.lfimg = field.getDouble();
                                if (field.getName().equals("VRKME")) di.vrkme = field.getString();
                                if (field.getName().equals("TRMTYP")) di.trmtyp = field.getString();
                                if (field.getName().equals("KUNWE")) 
                                {
                                    di.custReceiver = new Customer();
                                    try 
                                    { 
                                        di.custReceiver.fillFromID(field.getString());
                                    }
                                    catch(Exception ex) { di.custReceiver.kunnr = field.getString(); }
                                }      
                            }
                        }
                        
                        if (canBeAdded) d.items.add(di);
                        bi = item.nextRow();
                    }
                }

                
                ret.add(d);
                b = head.nextRow();
            }    
        }
        
        return ret;        
    }      
    
}
