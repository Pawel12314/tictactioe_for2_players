/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visitor;

import plansza.Plansza;

/**
 *
 * @author Geoff
 */
public abstract class Visitor {
    public abstract Object wykonajNaPlanszy(Plansza p);
    public abstract Object wykonajNaPowtorce(Plansza p);
}
