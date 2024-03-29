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
import com.mycompany.serverproject1.Pionek.PionekFabryka.IFabrykaPionek;

/**
 *
 * @author Geoff
 */
public class PlanszaProxyWirtualne implements IPlansza{

    private Plansza plansza;
    private IFabrykaPionek fabrykaPusty;
    private int licznik=0;
    private FactoryMethodChar factoryNotDefinedChar;
    private FactoryMethodChar factoryEmptyChar;
    public PlanszaProxyWirtualne()
    {
        //this.fabrykaPusty = fabryka;
        plansza = null;
        factoryNotDefinedChar = new FactoryMethodNotDefinedChar();
        factoryEmptyChar = new FactoryMethodEmptyChar();
    }
    @Override
    public void dodajPionka(int x, int y, char p) {
        if(plansza==null)
        {
            plansza=new Plansza();
        }
        plansza.dodajPionka(x, y, p);
        licznik++;
    }

    @Override
    public char pobierzPionka(int x, int y) throws IndexOutOfBoundsException {
        
        
        if(SprawdzCzyPoleIstnieje(x, y)==false)
        {
            throw new IndexOutOfBoundsException("wyjątek w proxy planszy");
        }
        if(plansza == null)
        {
            
            return factoryEmptyChar.getChar();
        }   
        char p;
        
        
        p = plansza.pobierzPionka(x, y);
        
           
        
        return p;
         
    }

    @Override
    public int getSzerokosc() {
        
        if(plansza == null)
            return 3;
        return plansza.getSzerokosc();
    }

    @Override
    public int getWysokosc() {
        if(plansza == null)
            return 3;
        return plansza.getWysokosc();
    }

    @Override
    public Boolean SprawdzCzyPoleIstnieje(int x, int y) {
        if(plansza ==null)
        {
            if(x>=getSzerokosc()|| x<0)
            return false;
        if(y>=getWysokosc() || y<0)
            return false;
        return true;
        }
        return plansza.SprawdzCzyPoleIstnieje(x, y);
    }

    @Override
    public IIteratorPlansza Pobierziterator(IMetodaPlansza metoda) {
        return metoda.pobierzIterator(this);
    }

  

    @Override
    public Boolean SprawdzCzyPoleZajete(int x, int y) {
        if(SprawdzCzyPoleIstnieje(x, y)==false)
        {
            throw new IndexOutOfBoundsException();
        }
        if(plansza==null)
        {
            return false;
        }
        return plansza.SprawdzCzyPoleZajete(x, y);
    }

    @Override
    public Pamiatka stworzPamiatke() {
        if(plansza ==null)
        {
            plansza = new Plansza();
        }
        return plansza.stworzPamiatke();
    }

    @Override
    public void przywroc(Pamiatka p) {
        if(plansza ==null)
        {
            plansza =new Plansza();
        }
        plansza.przywroc(p);
    }

    
    
}
