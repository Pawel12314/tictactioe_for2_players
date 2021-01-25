/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.serverproject1.Gracz.Polecenie;

import com.mycompany.serverproject1.Gracz.Gracz;

/**
 *
 * @author Geoff
 */
public class VistitorRemis implements VisitorState{

   // private Gracz gracz;
    public VistitorRemis(){
        
    }
    @Override
    public void wykonaj(Gracz gracz) {
        gracz.stan.remis();
    }
    
}
