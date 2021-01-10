/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.serverproject1.IteratorPoPlanszy.Gosc;

import com.mycompany.serverproject1.IteratorPoPlanszy.IIteratorPlansza;
import com.mycompany.serverproject1.IteratorPoPlanszy.IteratorPoziomy;
import com.mycompany.serverproject1.Plansza.IPlansza;

/**
 *
 * @author Geoff
 */
public class MetodaIteratorPoziomy implements IMetodaPlansza{

    @Override
    public IIteratorPlansza pobierzIterator(IPlansza p) {
        return new IteratorPoziomy(p);
    }
     
}
