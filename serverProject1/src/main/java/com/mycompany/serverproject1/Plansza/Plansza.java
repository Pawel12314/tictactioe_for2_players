/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.serverproject1.Plansza;

import com.mycompany.serverproject1.IteratorPoPlanszy.Gosc.IMetodaPlansza;
import com.mycompany.serverproject1.IteratorPoPlanszy.IIteratorPlansza;
import com.mycompany.serverproject1.IteratorPoPlanszy.IteratorPionowy;
import com.mycompany.serverproject1.Pionek.IPionek;
import com.mycompany.serverproject1.Pionek.PionekFabryka.FabrykaPustyPionek;
import com.mycompany.serverproject1.Pionek.PionekFabryka.IFabrykaPionek;
import com.mycompany.serverproject1.Pionek.PionekPusty;

/**
 *
 * @author Geoff
 */
public class Plansza implements IPlansza {

    private IPionek[][] plansza;
    private IFabrykaPionek fabrykaPusty;
    private int licznik=0;
    public Plansza(IFabrykaPionek fabryka)
    {
        this.fabrykaPusty = fabryka;
        plansza = new IPionek[3][3];
    }
    @Override
    public void dodajPionka(int x, int y, IPionek p) {
        plansza[x][y]= p;
        licznik++;
    }

    @Override
    public IPionek pobierzPionka(int x, int y) throws IndexOutOfBoundsException{
        IPionek p= null;
        
        if(SprawdzCzyPoleIstnieje(x, y)==false)
        {
            throw new IndexOutOfBoundsException("wyjątek w Planszy");
        }
        
           p = plansza[x][y];
       if(p==null){
           p = fabrykaPusty.StworzPionka();
       }
       
        return p;
    }

    @Override
    public int getSzerokosc() {
        return 3;
    }

    @Override
    public int getWysokosc() {
        return 3;
    }

    @Override
    public Boolean SprawdzCzyPoleIstnieje(int x, int y) {
        if(x>=getSzerokosc()|| x<0)
            return false;
        if(y>=getWysokosc() || y<0)
            return false;
        return true;
    }

    @Override
    public IIteratorPlansza Pobierziterator(IMetodaPlansza metoda) {
        return metoda.pobierzIterator(this);
    }

    @Override
    public int getLicznik() {
        return licznik;
    }

    @Override
    public Boolean SprawdzCzyPoleZajete(int x, int y) throws IndexOutOfBoundsException {
        
        if(SprawdzCzyPoleIstnieje(x, y)==false)
        {
            throw new IndexOutOfBoundsException();
        }
        if(plansza[x][y] ==null)
        {
            return false;
        }
        else
        {
            return true;
        }
    }


    
}
