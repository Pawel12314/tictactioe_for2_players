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
public class PolecenieWyjdzZgry implements Polecenie
{
    private Gracz gracz;
    public PolecenieWyjdzZgry(Gracz gracz)
    {
        this.gracz= gracz;
    }
    @Override
    public void wykonaj() {
        gracz.stan.zakonczGre();
    }
    
}
