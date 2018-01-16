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
public class PrintSDFull implements Printable {
    
    private SalesProcess salesProcess;
    private final int POINT_LEFT_1 = 135;
    private final int POINT_LEFT_2 = 180;
    private final int POINT_LEFT_3 = 230;
    
    private final int POINT_RIGHT_1 = 210;
    private final int POINT_RIGHT_2 = 300;
    private final int POINT_RIGHT_3 = 310;    
    
    private final int LINE_START_LEFT = 120;
    
    private final int LINE_SIZE = 12;
    
    private int yPosLeft;
    
    /** Creates a new instance of PrintSDSevk */
    public PrintSDFull(SalesProcess SP) {
        salesProcess = SP;
    }
    
    @Override
  public int print (Graphics g, PageFormat f, int pageIndex)
  {   
    if (pageIndex == 0)
    {
      Paper pap = new Paper();
      pap.setSize(600, 360);
      f.setPaper(pap);
      yPosLeft = 0;
        
      //g.drawRect(1, 1, 598, 358);      
      g.setFont(new Font("Monospaced", Font.PLAIN, 8));
      
      g.drawString(Sql.sqlFormatDate(salesProcess.dateFull, Sql.DATE_FORMAT.TURKISH, true), 430, 10);
      g.drawString(Sql.sqlFormatDate(salesProcess.dateFull, Sql.DATE_FORMAT.TURKISH, true), 430, 20);
      
      paintLeftTable(g, (salesProcess.customer == null ? "" : salesProcess.customer.kunnr));
      paintLeftTable(g, (salesProcess.customer == null ? salesProcess.kunnrManual : salesProcess.customer.name1));
      paintLeftTable(g, (salesProcess.customer == null ? "" : salesProcess.customer.address));
      yPosLeft = POINT_LEFT_2;
      paintLeftTable(g, (salesProcess.customer == null ? "" : salesProcess.customer.stcd1));
      paintLeftTable(g, salesProcess.getReceiverAddress());
      
      yPosLeft = POINT_LEFT_3;
      paintLeftTable(g, salesProcess.trmtyp);
      
      // Miktarý yazdýralým
      // Malzemenin bölümüne baðlý olarak ya tartýlan aðýrlýðý, ya da
      // teorik aðýrlýðý yazdýrýyor olacaðýz
      if (salesProcess.material.getSDWeightCalcAlgo() == Material.SD_WEIGHT_CALC_ALGO.DOKME)
      {
        g.drawString(salesProcess.calcWeight.toString(), 510, yPosLeft);
      }
      else
      {
        g.drawString(salesProcess.lfimg.toString(), 510, yPosLeft);
      }
      
      
      // Devam
      paintLeftTable(g, salesProcess.getDriverName()); 
      g.drawString(salesProcess.material.getDisplayText(), 360, yPosLeft);
      
      paintLeftTable(g, Main.config.intParam.kantarID + "-" + String.valueOf(salesProcess.id));
      
      //paintRightTable(g, "ÜRÜN", salesProcess.material.maktx);
      //paintRightTable(g, "MÝKTAR", salesProcess.getEligibleWeight().toString());
      
      return PAGE_EXISTS;
    }
    else return NO_SUCH_PAGE;
  } 
  
  private void paintLeftTable(Graphics G, String Value)
  {   
      if (yPosLeft == 0) yPosLeft = LINE_START_LEFT; else yPosLeft += LINE_SIZE;   
      G.drawString(Value, POINT_LEFT_1, yPosLeft);
  }
        
    
}
