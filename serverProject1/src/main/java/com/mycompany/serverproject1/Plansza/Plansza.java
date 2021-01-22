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

    private char[][] plansza;
    private IFabrykaPionek fabrykaPusty;
    private int licznik=0;
    
    private FactoryMethodChar factoryNotDefinedChar;
    private FactoryMethodChar factoryEmptyChar;
    public Plansza()
    {
        //this.fabrykaPusty = fabryka;
        plansza = new char[3][3];
        factoryNotDefinedChar= new FactoryMethodNotDefinedChar();
        factoryEmptyChar = new FactoryMethodEmptyChar();
    }
    @Override
    public void dodajPionka(int x, int y, char p) {
        plansza[x][y]= p;
        licznik++;
    }

    @Override
    public char pobierzPionka(int x, int y) throws IndexOutOfBoundsException{
        char p;//= ' ';
        
        if(SprawdzCzyPoleIstnieje(x, y)==false)
        {
            throw new IndexOutOfBoundsException("wyjątek w Planszy");
        }
        
           p = plansza[x][y];
       if(p==factoryNotDefinedChar.getChar()){
           p = factoryEmptyChar.getChar();
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
        if(plansza[x][y] ==factoryNotDefinedChar.getChar())
        {
            return false;
        }
        else
        {
            return true;
        }
    }
    @Override
    public Pamiatka stworzPamiatke()
    {
        return new PlanszaMemento().setStan(plansza);
    }
    @Override
    public void przywroc(Pamiatka p)
    {
        plansza = p.getStan();
    }
   private static class PlanszaMemento implements Pamiatka
   {
       private char[][] stan;
       
       public PlanszaMemento setStan(char[][] stan)
       {
           this.stan =stan;
           return this;
       }
       public char[][] getStan()
       {
           return this.stan;
       }
       
   }
    
}
