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
public class VisitorWstawPionka implements VisitorState{

    
    private char pionek;
    private int x;
    private int y;
    public VisitorWstawPionka(char pionek, int x, int y)
    {
        
        this.pionek = pionek;
        this.x=x;
        this.y=y;
    }
    @Override
    public void wykonaj(Gracz gracz) {
        gracz.stan.wstawPionka(pionek, x, y);
    }
    
}
