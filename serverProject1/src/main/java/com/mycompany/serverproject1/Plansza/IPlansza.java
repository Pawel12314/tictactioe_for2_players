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
public interface  IPlansza {
    
    public Boolean SprawdzCzyPoleIstnieje(int x , int y);
    public void dodajPionka(int x, int y,char p);
    public char pobierzPionka(int x, int y) throws IndexOutOfBoundsException;
    public int getSzerokosc();
    public int getWysokosc();
    public IIteratorPlansza Pobierziterator(IMetodaPlansza metoda);
    public int getLicznik();
    public Boolean SprawdzCzyPoleZajete(int x, int y) throws IndexOutOfBoundsException;
    
    public Pamiatka stworzPamiatke();

    public void przywroc(Pamiatka p);
}
