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
public class IteratorPionowy implements IIteratorPlansza {
    
    IPlansza plansza;
    private int x,y;
    private int pierwszy_x, pierwszy_y;
    //private IPionek pierwszy;
    
    public IteratorPionowy(IPlansza plansza)
    {
        pierwszy_x=0;
        x=0;
        pierwszy_y=0;
        y=0;
        
        this.plansza = plansza;
    }
    @Override
    public IPionek pierwszy() {
        return plansza.pobierzPionka(pierwszy_x, pierwszy_y);
    }

    @Override
    public Boolean czyNastepny() {
         //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
         if(plansza.SprawdzCzyPoleIstnieje(x, y+1))
         {
             return true;
         }else
         {
             if(plansza.SprawdzCzyPoleIstnieje(x+1,0))
                 return true;
         }
         return false;
             
             
         
    }

    @Override
    public IPionek nastepny() throws IndexOutOfBoundsException{
        if(plansza.SprawdzCzyPoleIstnieje(x, y+1))
         {
             y++;
             return plansza.pobierzPionka(x, y);
         }else
         {
             if(plansza.SprawdzCzyPoleIstnieje(x+1,0))
             {
                 y=0;
                 x++;
                 return plansza.pobierzPionka(x, y);
             }
                 
         }
        throw new IndexOutOfBoundsException("wyjątek w iteratorze" +"xy"+x +" "+y);
    }

    @Override
    public IPionek obecny() {
        
        
            return plansza.pobierzPionka(x, y);
        
        
    }
    
}
