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
    
    
    public char pierwszy();
    public Boolean czyNastepny();
    public char nastepny() throws IndexOutOfBoundsException;
    public char obecny();
}
