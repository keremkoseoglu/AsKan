/*
 * DataType.java
 *
 * Created on 26 Ekim 2007 Cuma, 12:04
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package askan.systems;

import java.sql.*;
/**
 * This interface will be used for all items representing a SAP object
 * @author Kerem
 */
public interface DataType {
    
    /**
     * Fills the object from a database record
     * @param R Recordset retured from a query
     * @throws java.lang.Exception Any SQL exception
     */
    public void fillFromSql(ResultSet R) throws Exception;
    /**
     * Fills the object with its ID
     * @param ID ID of the object
     * @throws java.lang.Exception Any SQL exception
     */
    public void fillFromID(String ID) throws Exception;
    public void insert(Sql S) throws Exception;
    /**
     * Returns a text representing the object. 
     * You can use this text to represent the object on the user interface.
     * @return String reprsenting the object
     */
    public String getDisplayText();
    
}
