/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package askan.configuration;

import askan.*;
import askan.systems.*;
import java.sql.*;
import com.sap.mw.jco.*;  
import java.util.*;

/**
 *
 * @author Kerem
 */
public class SteelyardParam {
    
    public String
            werks,
            ltext,
            addre,
            name1,
            telno,
            faxno;
    
    public WareHouse lgorm;
    
    public boolean
            amanu,
            atole;
    
    public SteelyardParam()
    {
        werks = "";
        ltext = "";
        amanu = true;
        atole = true;
        lgorm = null;
    }
    
    public void read(String KantarID) throws Exception
    {
        ResultSet rs = Main.sql.selectData("select * from sap_steelyard_config where KANID = '" + KantarID + "'");
        if (rs == null) return;
        if (rs.next())
        {
            werks = Sql.getString(rs, "WERKS");
            ltext = Sql.getString(rs, "LTEXT");
            amanu = Sql.getBoolean(rs, "AMANU");
            atole = Sql.getBoolean(rs, "ATOLE");
            addre = Sql.getString(rs, "ADDRE");
            name1 = Sql.getString(rs, "NAME1");
            telno = Sql.getString(rs, "TELNO");
            faxno = Sql.getString(rs, "FAXNO");
            
            try
            {
                lgorm = new WareHouse();
                lgorm.fillFromID(Sql.getString(rs, "LGORM"));
            }
            catch(Exception ex)
            {
                lgorm = null;
            }
        }
    }
    
    public void write(Sql S) throws Exception
    {
        S.executeQuery("DELETE FROM sap_steelyard_config WHERE KANID = '" + Main.config.intParam.kantarID + "'");

        String command = "INSERT INTO sap_steelyard_config (KANID, WERKS, LTEXT, LGORM, AMANU, ATOLE, ADDRE, NAME1, TELNO, FAXNO) VALUES (";
        command += "  '" + Main.config.intParam.kantarID + "'";
        command += ", '" + werks + "'";
        command += ", '" + Sql.sqlFormatText(ltext, 40) + "'";
        command += ", '" + lgorm.lgort + "'";
        command += ", '" + (amanu ? "X" : "") + "'";
        command += ", '" + (atole ? "X" : "") + "'";
        command += ", '" + Sql.sqlFormatText(addre, 500) + "'";
        command += ", '" + Sql.sqlFormatText(name1, 40) + "'";
        command += ", '" + Sql.sqlFormatText(telno, 30) + "'";
        command += ", '" + Sql.sqlFormatText(faxno, 30) + "'";
        command += ")";
        S.executeQuery(command);

        S.endTransaction();
    }
    
    public void read(Sap S) throws Exception
    {
        boolean b = true;
        
        IFunctionTemplate ftemp = S.repository.getFunctionTemplate("ZCZMKAN_GET_STEELYARD_CONFIG");
        JCO.Function function = ftemp.getFunction();
        function.getImportParameterList().setValue(Main.config.intParam.kantarID, "I_KANID");
        S.client.execute(function);
        
        JCO.Structure s = function.getExportParameterList().getStructure("E_CONF");
        werks = s.getString("WERKS");
        ltext = s.getString("LTEXT");
        amanu = s.getString("AMANU").equals("X");
        atole = s.getString("ATOLE").equals("X");
        addre = s.getString("ADDRE");
        name1 = s.getString("NAME1");
        telno = s.getString("TELNO");
        faxno = s.getString("FAXNO");
        
        try
        {
            lgorm = new WareHouse();
            lgorm.fillFromID(s.getString("LGORM"));
        }
        catch(Exception ex)
        {
            lgorm = null;
        }
        
    }
}
