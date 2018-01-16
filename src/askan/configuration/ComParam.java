/*
 * ComParam.java
 *
 * Created on 01 Kasým 2007 Perþembe, 14:41
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package askan.configuration;

/**
 *
 * @author Kerem
 */
public class ComParam {
    
    public String
            name;
    
    public int
            id,
            speed,
            dataBits,
            stopBits,
            parity;
    
    public boolean
            testMode,
            verbose;
    public double 
            defaultEmptyWeight,
            defaultFullWeight;
    
    /** Creates a new instance of ComParam */
    public ComParam() {
    }
    
}
