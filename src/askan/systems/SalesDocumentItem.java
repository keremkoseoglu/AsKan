/*
 * SalesDocumentItem.java
 *
 * Created on 26 Ekim 2007 Cuma, 16:47
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package askan.systems;

import java.sql.*;
/**
 *
 * @author Kerem
 */
public class SalesDocumentItem implements DataType {
    
    public String
            posnr,
            vrkme,
            trmtyp;
            
    public Material material;
    public double lfimg;
    
    protected Customer custReceiver;
    
    /**
     * Creates a new instance of SalesDocumentItem
     */
    public SalesDocumentItem() {
        posnr = "";
        material = new Material();
        vrkme = "";
        trmtyp = "";
        lfimg = 0;
    }
    
    public void fillFromSql(ResultSet R) throws Exception {
        posnr = Sql.getString(R, "POSNR");
        
        material = new Material();
        material.fillFromID(Sql.getString(R, "MATNR"));
        
        lfimg = R.getDouble("LFIMG");
        vrkme = Sql.getString(R, "VRKME");
        trmtyp = Sql.getString(R, "TRMTYP");
        
        if (Sql.getString(R, "KUNWE").equals(""))
        {
            custReceiver = null;
        }
        else
        {
            custReceiver = new Customer();
            custReceiver.fillFromID(Sql.getString(R, "KUNWE"));
        }        
    }
    
    public void fillFromID(String ID) throws Exception
    {
        throw new Exception("This method has not been implemented yet");
    }        
    
    public String getDisplayText()
    {
        return posnr;
    }  
    
    public void deleteAll(Sql S) throws Exception
    {
        throw new Exception("This method has not been implemented yet");
    }
    
    public void insert(Sql S) throws Exception
    {
        throw new Exception("This method has not been implemented yet");
    }
    
    public Customer getReceiver()
    {
        return custReceiver;
    }
    
}
