/*
 * Weight.java
 *
 * Created on 02 Kasým 2007 Cuma, 14:24
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package askan.systems;

import askan.*;
import java.text.Format.*;
import java.sql.*;
import java.text.NumberFormat;
import java.text.ParseException;

/**
 *
 * @author Kerem
 */
public class Weight {
    
    private double weight;
    public Material.UNIT_OF_MEASURE uom;
    
    /** Creates a new instance of Weight */
    public Weight() {
        weight = 0;
        uom = Material.UNIT_OF_MEASURE.KG;
    }
    
    public Weight(double Value, Material.UNIT_OF_MEASURE Uom)
    {
        setWeight(Value, Uom);
    }
    
    public double getWeight()
    {
        return weight;
    }
    
    public double toKG()
    {
        return Material.toKG(this);
    }
    
    public void setWeight(double Value, Material.UNIT_OF_MEASURE Uom)
    {
        weight = Value;
        uom = Uom;        
    }
    
    public void setWeight(double Value, String Uom)
    {
        setWeight(Value, Material.getUOM(Uom));
    }
    
    public void setWeight(double Value)
    {
        weight = Value;
    }
    
    public void setWeight(String Value, String Uom) throws Exception
    {
        Number n = NumberFormat.getNumberInstance().parse(Value);
        setWeight(n.doubleValue(), Material.getUOM(Uom));
    }
   
    @Override
   public String toString()
   {
    return toString(true);
   }
    
    public String toString(boolean IncludeUOM)
    {
        String w = NumberFormat.getNumberInstance().format(weight);
        if (IncludeUOM) w += " " + Material.getUOM(uom); 
        return w;      
    }
   
   public String getUOM()
   {
       return Material.getUOM(uom);
   }
    
}
