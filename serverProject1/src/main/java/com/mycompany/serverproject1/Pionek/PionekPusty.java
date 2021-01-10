/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.serverproject1.Pionek;

/**
 *
 * @author Geoff
 */
public class PionekPusty implements IPionek, Cloneable{
     
    public PionekPusty()
    {
        
    }
    public char getPionek() throws NoSuchFieldException
    {
        throw new NoSuchFieldException("wyjątek pionek pusty");
    }
    public Object clone()
    {
        Object cloned = null;
        try
        {
            cloned = super.clone();
        }
        catch(Exception e)
        {
            
        }
        return cloned;
                
    }
}
