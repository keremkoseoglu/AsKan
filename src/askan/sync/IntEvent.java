/*
 * IntEvent.java
 *
 * Created on 26 Þubat 2007 Pazartesi, 13:13
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package askan.sync;

import java.util.*;

/**
 *
 * @author Kerem
 */
public class IntEvent extends EventObject {
    
    public String text;
    
    /** Creates a new instance of IntEvent */
    public IntEvent(Object source) {
        super(source);
    }
}
