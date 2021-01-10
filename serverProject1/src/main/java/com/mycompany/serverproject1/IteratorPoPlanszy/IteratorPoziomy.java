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
public class IteratorPoziomy implements IIteratorPlansza {

    
    private int x,y;
    private final int pierwszy_x,pierwszy_y;
    private IPlansza plansza;
    public IteratorPoziomy(IPlansza plansza)
    {
        this.plansza = plansza;
        x=0;
        y=0;
        pierwszy_x=0;
        pierwszy_y=0;
    }
    @Override
    public IPionek pierwszy() {
        return plansza.pobierzPionka(pierwszy_x, pierwszy_y);
    }

    @Override
    public Boolean czyNastepny() {
        if(plansza.SprawdzCzyPoleIstnieje(x+1, y))
        {
            return true;
        }
        else
        {
            if(plansza.SprawdzCzyPoleIstnieje(0, y+1))
            {
                return true;
            }
        }
        return false;
    }

    @Override
    public IPionek nastepny() throws IndexOutOfBoundsException {
        if(plansza.SprawdzCzyPoleIstnieje(x+1, y))
        {
            x++;
            return plansza.pobierzPionka(x, y);
        }
        else
        {
            if(plansza.SprawdzCzyPoleIstnieje(0, y+1))
            {
                x=0;
                y++;
                return plansza.pobierzPionka(x, y);
            }
        }
        throw new IndexOutOfBoundsException("wyjatek -nastepny- iterator Poziomy " +"xy"+x +" "+y);
    }

    @Override
    public IPionek obecny() {
        
        
            return plansza.pobierzPionka(x, y);
        
        
    }
    
}
