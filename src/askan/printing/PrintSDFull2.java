/*
 * PrintSDSevk.java
 *
 * Created on 27 Ekim 2007 Cumartesi, 13:13
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package askan.printing;

import askan.Main;
import askan.systems.*;
import java.awt.*;
import java.awt.print.*;
import javax.print.*;

/**
 *
 * @author Kerem
 */
public class PrintSDFull2 implements Printable {
    
    private SalesProcess salesProcess;
    
    /** Creates a new instance of PrintSDSevk */
    public PrintSDFull2(SalesProcess SP) {
        salesProcess = SP;
    }
    
    @Override
  public int print (Graphics g, PageFormat f, int pageIndex)
  {
    PrintMaster pm = new PrintMaster();
        
    if (pageIndex == 0)
    {
      Paper pap = new Paper();
      pap.setSize(PrintMaster.paperWidth, PrintMaster.paperHeight);
      f.setPaper(pap);
        
      pm.paintTitle(g, "KANTAR FÝÞÝ - " + Main.config.intParam.kantarID + "-" + askan.systems.Sap.getNumc(salesProcess.id));
      
      pm.paintTitledString(g, PrintMaster.TABLE_COLUMN.LEFT, "Plaka", salesProcess.trmtyp);
      pm.newLine();
      pm.paintTitledString(g, PrintMaster.TABLE_COLUMN.LEFT, "Müþteri", salesProcess.getCustomerDisplayText());
      pm.newLine();
      pm.paintTitledString(g, PrintMaster.TABLE_COLUMN.LEFT, "Sevk Yeri", salesProcess.getReceiverAddress());
      pm.newLine();
      pm.paintTitledString(g, PrintMaster.TABLE_COLUMN.LEFT, "Malzeme", salesProcess.material.getDisplayText());
      pm.newLine();
      pm.paintTitledString(g, PrintMaster.TABLE_COLUMN.LEFT, "Operatör", Main.operatorName);
      pm.newLine();
      pm.paintTitledString(g, PrintMaster.TABLE_COLUMN.LEFT, "Þöför", salesProcess.getDriverName());
      pm.newLine();      
      pm.paintTitledString(g, PrintMaster.TABLE_COLUMN.LEFT, "Sip. Miktarý", salesProcess.lfimg.toString());
      pm.newLine();
      pm.paintTitledString(g, PrintMaster.TABLE_COLUMN.LEFT, "Dolu Aðýrlýk", salesProcess.fullWeight.toString());
      pm.newLine();
      pm.paintTitledString(g, PrintMaster.TABLE_COLUMN.LEFT, "Boþ Aðýrlýk", salesProcess.emptyWeight.toString());
      pm.newLine();
      pm.paintTitledString(g, PrintMaster.TABLE_COLUMN.LEFT, "Net Aðýrlýk", salesProcess.calcWeight.toString());
      pm.newLine();
      pm.paintTitledString(g, PrintMaster.TABLE_COLUMN.LEFT, "Tarih", salesProcess.dateFull == null ? "" : Sql.sqlFormatDate(salesProcess.dateFull, Sql.DATE_FORMAT.TURKISH, true));
      
      return PAGE_EXISTS;
    }
    else return NO_SUCH_PAGE;
  }    
        
    
}
