/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plansza;

import java.awt.Graphics;

/**
 *
 * @author Geoff
 */
public class FactoryMethodInProgress extends FactoryMethodBuilder{
    @Override
    public IFrame getFrame(int width,int height,Graphics graphic)
    {
        return new Frame(graphic,width,height);
    }
}
