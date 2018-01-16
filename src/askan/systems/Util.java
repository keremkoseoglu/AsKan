/*
 * Util.java
 *
 * Created on 26 Ekim 2007 Cuma, 13:40
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package askan.systems;

/**
 *
 * @author Kerem
 */

import java.awt.*;
import javax.swing.*;

public class Util {
    
    /** Creates a new instance of Util */
    public Util() {
    }
    
    public static String formatPlate(String P)
    {
        String ret = "";
        for (int n = 0; n < P.length(); n++) if (!P.substring(n, n + 1).equals(" ")) ret += P.substring(n, n + 1).toUpperCase();
        return ret;
    }
    
    public static boolean confirmStep(Component Sender, String Message)
    {
        return JOptionPane.showConfirmDialog(Sender, Message, "Onay", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == 0;
    }
    
    public static String getPopupValue(String Message) throws Exception
    {
        return JOptionPane.showInputDialog(Message, "");
    }
    
}
