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
public class PrintMMFull implements Printable {
    
    private PurchProcess purchProcess;
    
    /** Creates a new instance of PrintSDSevk */
    public PrintMMFull(PurchProcess PP) {
        purchProcess = PP;
    }
    
    @Override
  public int print (Graphics g, PageFormat f, int pageIndex)
  {
    if (pageIndex == 0)
    {
      Paper pap = new Paper();
      pap.setSize(PrintMaster.paperWidth, PrintMaster.paperHeight);
      f.setPaper(pap);
        
      PrintMaster pm = new PrintMaster();
      pm.paintTitle(g, "SATINALMA GÝRÝÞ FÝÞÝ - " + Main.config.intParam.kantarID + "-" + askan.systems.Sap.getNumc(purchProcess.id));      
      
      pm.paintTitledString(g, PrintMaster.TABLE_COLUMN.LEFT, "Satýcý", purchProcess.getVendorDisplayText());
      pm.newLine();
      pm.paintTitledString(g, PrintMaster.TABLE_COLUMN.LEFT, "Plaka", purchProcess.trmtyp);
      pm.newLine();
      pm.paintTitledString(g, PrintMaster.TABLE_COLUMN.LEFT, "Malzeme", purchProcess.getMaterialDisplayText());
      pm.newLine();
      pm.paintTitledString(g, PrintMaster.TABLE_COLUMN.LEFT, "Ýrs. Miktarý", purchProcess.lfimg.toString());
      pm.newLine();
      pm.paintTitledString(g, PrintMaster.TABLE_COLUMN.LEFT, "Dolu Aðýrlýk", purchProcess.fullWeight.toString());
      pm.newLine();
      pm.paintTitledString(g, PrintMaster.TABLE_COLUMN.LEFT, "Tarih", purchProcess.dateFull == null ? "" : Sql.sqlFormatDate(purchProcess.dateFull, Sql.DATE_FORMAT.TURKISH, true));      
      
      return PAGE_EXISTS;
    }
    else return NO_SUCH_PAGE;
  }    
        
    
}
