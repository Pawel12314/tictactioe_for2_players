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
public class IteratorUkosnyDoTylu implements IIteratorPlansza{
    private int x,y;
    private final int pierwszy_x,pierwszy_y;
    private IPlansza plansza;
    
    public IteratorUkosnyDoTylu(IPlansza p)
    {
        x=2;
        y=0;
        pierwszy_x=2;
        pierwszy_y=0;
        plansza=p;
    }
    @Override
    public IPionek pierwszy() {
        return plansza.pobierzPionka(pierwszy_x, pierwszy_y);
    }

    @Override
    public Boolean czyNastepny() {
        if(plansza.SprawdzCzyPoleIstnieje(x-1, y+1))
        {
            return true;
        }
        return false;
    }

    @Override
    public IPionek nastepny() throws IndexOutOfBoundsException {
        if(plansza.SprawdzCzyPoleIstnieje(x-1, y+1))
        {
            x--;
            y++;
            return plansza.pobierzPionka(x, y);
        }
        throw new IndexOutOfBoundsException("wyjątek w iteratorze ukośnym do tyłu" +"xy"+x +" "+y);
    }

    @Override
    public IPionek obecny() {
        
            return plansza.pobierzPionka(x, y);
        
       
    }
    
}
