/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visitor;

import java.awt.Point;

/**
 *
 * @author Geoff
 */
public abstract class WstawPole extends Visitor {
    
    protected String path;
    public WstawPole(String s)
    {
        path = s;
    }
    public abstract Point getPunkt();
    public abstract String getPath();
}
