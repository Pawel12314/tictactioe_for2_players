/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pionek.dekorator;

import pionek.IPionek;

/**
 *
 * @author Geoff
 */
public class ResizeDecorator extends IDekoratorPionek {
    
    public ResizeDecorator(IPionek p) {
        super(p);
    }
    
}
