/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.serverproject1.Plansza;

import java.io.Serializable;

/**
 *
 * @author Geoff
 */
public interface Pamiatka extends Serializable {
    public Pamiatka setStan(char[][] stan);
    public char[][] getStan();
}
