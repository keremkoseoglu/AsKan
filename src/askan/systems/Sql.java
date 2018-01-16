/*
 * Sql.java
 *
 * Created on 26 Þubat 2007 Pazartesi, 11:24
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package askan.systems;

/**
 *
 * @author Kerem
 */

import askan.*;
import com.microsoft.sqlserver.jdbc.*;
import java.sql.*;
import java.util.*;

public class Sql {
    
    private Connection conn;
		
    
    /** Creates a new instance of Sql */
    public Sql() throws Exception {

        try
        {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        }
        catch(Exception ex)
        {
            throw new Exception("JDBC kütüphane hatasý: " + ex.toString());
        }
        
    }
    
    public static String sqlFormatDate(Calendar C, DATE_FORMAT D, boolean IncludeTime)
    {
        String ret = "";
        
        if (D == DATE_FORMAT.ENGLISH)
        {
            ret += String.valueOf(C.get(Calendar.MONTH) + 1) + "/" + String.valueOf(C.get(Calendar.DAY_OF_MONTH)) + "/" + String.valueOf(C.get(Calendar.YEAR));
            if (IncludeTime)
            {
                ret += " ";
                ret += String.valueOf(C.get(Calendar.HOUR_OF_DAY)) + ":" + String.valueOf(C.get(Calendar.MINUTE)) + ":"+ String.valueOf(C.get(Calendar.SECOND));
            }
        }
        else
        {
            ret += String.valueOf(C.get(Calendar.DAY_OF_MONTH)) + "." + String.valueOf(C.get(Calendar.MONTH) + 1) + "." +  String.valueOf(C.get(Calendar.YEAR));
            if (IncludeTime)
            {
                ret += " ";
                ret += String.valueOf(C.get(Calendar.HOUR_OF_DAY)) + ":" + String.valueOf(C.get(Calendar.MINUTE)) + ":"+ String.valueOf(C.get(Calendar.SECOND));
            }
        }
        
        return ret;
    }
    
    public static String sqlFormatText(String S)
    {
        return sqlFormatText(S, 0);
    }
    
    public static String sqlFormatText(String S, int MaxLength)
    {
        if (S == null) return "";
        String ret = S.replaceAll("'", "''");
        
        if ((MaxLength != 0) && (MaxLength < ret.length())) ret = ret.substring(0, MaxLength);
        
        return ret;
    }    
    
    public void connect() throws Exception
    {
        try
        {
            //String url="jdbc:sqlserver://" + Main.config.sqlParam.ip + ";DatabaseName=" + Main.config.sqlParam.database;
            String url = "jdbc:sqlserver://localhost;instanceName=SQLEXPRESS;DatabaseName=askandb";
            conn = DriverManager.getConnection(url, Main.config.sqlParam.user, Main.config.sqlParam.pass);
            if (conn == null)
            {
                throw new Exception("JDBC baðlantýsý açýlamadý, ayar dosyasýný kontrol edin");
            }
            
            executeQuery("set dateformat dmy");
        }
        catch(Exception ex)
        {
             throw new Exception("JDBC baðlantý hatasý: " + ex.toString());
        }        
    }
    
    public void disconnect()
    {
        try
        {
            if (conn != null) conn.close();
        }
        catch(Exception ex)
        {
            
        }
    }
    
    public ResultSet selectData(String Query) throws Exception
    {
        ResultSet ret = null;
        
        Statement s = conn.createStatement();
        s.executeQuery(Query);
        ret = s.getResultSet();
        
        return ret;
    }
    
    public void executeQuery(String Query) throws Exception
    {
        Statement s = conn.createStatement();
        s.execute(Query);
    }    
    
    public void startTransaction() throws Exception
    {
        conn.setAutoCommit(false);
    }
    
    public void endTransaction() throws Exception
    {
        conn.commit();
        conn.setAutoCommit(true);
    }
    
    public void cancelTransaction() 
    {
        try
        {
            conn.rollback();
            conn.setAutoCommit(true);
        }
        catch(Exception ex)
        {
            Main.appendLog("Rollback hatasý: " + ex.toString());
        }
    }
    
    public static String getString(ResultSet R, String ColumnName)
    {
        try
        {
            return (R.getString(ColumnName) == null ? "" : R.getString(ColumnName));
        }
        catch(Exception ex)
        {
            return "";
        }
    }
    
    public static boolean getBoolean(ResultSet R, String ColumnName)
    {
        return getString(R, ColumnName) != null && Sql.getString(R, ColumnName).equals("X");     
    } 
    
    public static Calendar getCalendar(ResultSet R, String ColumnName) throws Exception
    {
        Calendar ret = Calendar.getInstance();
        String s = R.getString(ColumnName);
        
        if (s != null)
        {
            ret.clear();
            
            int hour = Integer.parseInt(s.substring(11, 13));
            
            ret.set(
                    Integer.parseInt(s.substring(0, 4)), 
                    Integer.parseInt(s.substring(5, 7)), 
                    Integer.parseInt(s.substring(8, 10)), 
                    hour, 
                    Integer.parseInt(s.substring(14, 16)), 
                    Integer.parseInt(s.substring(17, 19))
                    );
        }
        else ret = null;
        return ret;
    }
        
}
