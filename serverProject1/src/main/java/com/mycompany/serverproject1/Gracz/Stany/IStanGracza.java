/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.serverproject1.Gracz.Stany;

import com.mycompany.serverproject1.Pionek.IPionek;

/**
 *
 * @author Geoff
 */
public abstract class IStanGracza {
    
    public static String EXIT_MSG_KOLEJKA = "QUIT_QUEUE";
    public static String NIE_TWOJA_TURA = "NOT_TURN";
    public static String EXIT_MSG_GRA = "BREAK";
    public static String ZLA_INSTRUKCJA = "BAD_INSTR";
    public static String BAD_MOVE="BAD_MOVE";
    public static String CANT_MOVE="CANT_MOVE";
    public static String OPPONENT_WIN="OPPONENT_WIN";
    public static String YOU_WIN="WON";
    public static String REMIS="REMIS";
    public static String RUCH="MOVE";
    public static String TY_ZACZYNASZ="ZACZYNASZ_GRE";
    public static String ZACZYNA_PRZECIWNIK="ZACZYNA_PRZECIWNIK";
    public abstract void wygrana();
    public abstract String pobierzStrumienWejsciowy(String strumien);
    public abstract void zmienTure();
    public abstract Boolean sprawdzCzyKoniec();
    public abstract void zakonczGre();
    public abstract void remis();
    public abstract void wstawPionka(char pionek,int x, int y);
    public abstract void rozpocznijGre(Character pionek);
 
    //public boolean czyMogeRuszyc();
    //public boolean rusz();
    //public boolean czyWygralem();
    
}
