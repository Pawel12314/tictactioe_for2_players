/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.serverproject1.IteratorPoPlanszy;

import com.mycompany.serverproject1.Pionek.IPionek;
import com.mycompany.serverproject1.Plansza.IPlansza;

/**
 *
 * @author Geoff
 */
public class IteratorUkosnyDoPrzodu implements IIteratorPlansza{
    
     private int x,y;
    private final int pierwszy_x,pierwszy_y;
    private IPlansza plansza;
    public IteratorUkosnyDoPrzodu(IPlansza p)
    {
       this.plansza = p;
       x=0;
       y=0;
       pierwszy_x=0;
       pierwszy_y=0;
    }
    @Override
    public char pierwszy() {
        return plansza.pobierzPionka(pierwszy_x, pierwszy_y);
    }

    @Override
    public Boolean czyNastepny() {
        if(plansza.SprawdzCzyPoleIstnieje(x+1, y+1))
        {
            return true;
        }
        return false;
    }

    @Override
    public char nastepny() throws IndexOutOfBoundsException {
        if(plansza.SprawdzCzyPoleIstnieje(x+1, y+1))
        {
            x++;
            y++;
            return plansza.pobierzPionka(x, y);
        }
        throw new IndexOutOfBoundsException("wyjątek w iteratorze ukośnym do przodu" +"xy"+x +" "+y);
    }

    @Override
    public char obecny() {
        
            return plansza.pobierzPionka(x, y);
        
        
    }
    
}
