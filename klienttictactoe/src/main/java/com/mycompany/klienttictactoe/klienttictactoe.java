/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.klienttictactoe;

import java.awt.Point;
import java.io.IOException;
import pionek.IPionek;
import pionek.PionekFactory.IPionekFactory;
import pionek.PionekFactory.StandardFactory;
import pionek.pylek.PionekPylekFactory;

/**
 *
 * @author Geoff
 */
public class klienttictactoe {
    public static void main(String[] args) {
        PionekPylekFactory fabryka = new PionekPylekFactory() ;
        IPionekFactory f = new StandardFactory();
        try
        {
            IPionek p = fabryka.getPionek(new Point(0,0), f.getBitmap_O());
        }
        catch(IOException e)
        {
            System.out.println("file not found");
        }
        
        
    }
}
