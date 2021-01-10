/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.serverproject1.Gracz;

import com.mycompany.serverproject1.Pionek.IPionek;

/**
 *
 * @author Geoff
 */
public abstract class IGraczObserwator implements Runnable{
    public abstract void updateRozpocznijGre();
    public abstract void zakonczGre();
    public abstract void run();
}
