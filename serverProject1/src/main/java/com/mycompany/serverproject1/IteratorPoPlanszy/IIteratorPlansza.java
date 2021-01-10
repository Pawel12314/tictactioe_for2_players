/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.serverproject1.IteratorPoPlanszy;

import com.mycompany.serverproject1.Pionek.IPionek;

/**
 *
 * @author Geoff
 */
public interface IIteratorPlansza {
    
    
    public IPionek pierwszy();
    public Boolean czyNastepny();
    public IPionek nastepny() throws IndexOutOfBoundsException;
    public IPionek obecny();
}
