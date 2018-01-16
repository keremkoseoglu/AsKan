/*
 * TwoWaySerialComm.java
 *
 * Created on 01 Kasým 2007 Perþembe, 14:31
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package askan.steelyard;

import askan.*;
import askan.systems.*;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;

import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class TwoWaySerialComm
{
    private static String lastData;
    private static double lastWeight;
    
    public enum TRUCK_STATUS { EMPTY, FULL };
    public static TRUCK_STATUS truckStatus;
    
    public TwoWaySerialComm()
    {
        super();
        lastData = "";
        lastWeight = 0;
    }
    
    public void connect () throws Exception
    {
        if (!Main.config.comParam.testMode)
        {
            CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(Main.config.comParam.name);
            if ( portIdentifier.isCurrentlyOwned() )
            {
                throw new Exception(Main.config.comParam.name + " kullanýmda");
            }
            else
            {
                CommPort commPort = portIdentifier.open(this.getClass().getName(), Main.config.comParam.id);

                SerialPort serialPort = (SerialPort) commPort;
                try
                {
                    serialPort.setSerialPortParams(Main.config.comParam.speed, Main.config.comParam.dataBits, Main.config.comParam.stopBits, Main.config.comParam.parity);
                }
                catch(Exception ex)
                {
                    serialPort.setSerialPortParams(9600,SerialPort.DATABITS_8,SerialPort.STOPBITS_1,SerialPort.PARITY_NONE);
                }

                InputStream in = serialPort.getInputStream();
                OutputStream out = serialPort.getOutputStream();

                (new Thread(new SerialReader(in))).start();
                (new Thread(new SerialWriter(out))).start();
            } 
        }
    }
    
    public Weight getLastWeight()
    {
        Weight w = new Weight();
        
        if (!Main.config.comParam.testMode)
        {
            w.setWeight(lastWeight, Material.UNIT_OF_MEASURE.KG);
        }
        else
        {
            if (truckStatus == TRUCK_STATUS.EMPTY) w.setWeight(Main.config.comParam.defaultEmptyWeight, Material.UNIT_OF_MEASURE.KG);
            if (truckStatus == TRUCK_STATUS.FULL) w.setWeight(Main.config.comParam.defaultFullWeight, Material.UNIT_OF_MEASURE.KG);
        }
        
        return w;
    }
    
    /** */
    public static class SerialReader implements Runnable 
    {
        InputStream in;
        
        public SerialReader ( InputStream in )
        {
            this.in = in;
        }
        
        public void run ()
        {
            byte[] buffer = new byte[1024];
            int len = -1;
            try
            {
                while ( ( len = this.in.read(buffer)) > -1 )
                {
                    lastData = new String(buffer, 0, len);
                    try
                    {
                        lastWeight = Double.parseDouble(lastData.substring(3, 8));
                    }
                    catch(Exception ex)
                    {
                        if (Main.config.comParam.verbose) Main.appendLog(lastData + " tartým hatasý: " + ex.toString());
                    }
                }
            }
            catch ( IOException e )
            {
                e.printStackTrace();
            }            
        }
    }

    /** */
    public static class SerialWriter implements Runnable 
    {
        OutputStream out;
        
        public SerialWriter ( OutputStream out )
        {
            this.out = out;
        }
        
        public void run ()
        {
            try
            {                
                int c = 0;
                while ( ( c = System.in.read()) > -1 )
                {
                    this.out.write(c);
                }                
            }
            catch ( IOException e )
            {
                e.printStackTrace();
            }            
        }
    }
}
    
    
