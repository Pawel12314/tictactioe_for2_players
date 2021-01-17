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
public class PolecenieRozpocznijGre implements Polecenie{

    private Gracz gracz;
    private char pionek;
    
    public PolecenieRozpocznijGre(Gracz gracz,char pionek)
    {
        this.gracz= gracz;
        this.pionek = pionek;
    }
    @Override
    public void wykonaj() {
        gracz.stan.rozpocznijGre(pionek);
    }
    
}
