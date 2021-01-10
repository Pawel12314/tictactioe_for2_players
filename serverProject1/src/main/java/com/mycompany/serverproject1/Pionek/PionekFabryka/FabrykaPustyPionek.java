/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.serverproject1.Pionek.PionekFabryka;

import com.mycompany.serverproject1.Pionek.IPionek;
import com.mycompany.serverproject1.Pionek.PionekPusty;

/**
 *
 * @author Geoff
 */
public class FabrykaPustyPionek implements IFabrykaPionek {

    
    public FabrykaPustyPionek()
    {
        
    }
    @Override
    public IPionek StworzPionka() {
        return new PionekPusty();
    }
    
}
