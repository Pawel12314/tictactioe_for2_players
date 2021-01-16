/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pionek.pylek;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import pionek.IPionek;
import pionek.dekorator.DekoratorPionek;

/**
 *
 * @author Geoff
 */
public class ConcretePionek implements IPionek {
    private Image pole;
    private static Map<String,IPionek> pionki = new HashMap<String,IPionek>();
    public ConcretePionek(Image g)
    {
        this.pole = g;
    }
    @Override
    public void draw(Graphics2D g ,Point p) {
        g.drawImage(pole, p.x, p.y, p.x + 1, p.y + 1,null);
        
        
    }

    
    public static IPionek getByBitmap(String path) throws IOException{
        if(pionki.containsKey(path))
        {
            return pionki.get(path);
        }
        else
        {
            //URI uri = URI.create(path);
            
            //File file = new File(path);
            try
            {
                //FileInputStream instream = new  FileInputStream(file);
            Image im = ImageIO.read(new File(path));
            //Graphics2D bmp = im.createGraphics();
            //Bitmap bitmap;// = BitmapFactory.decodeStream(instream);
            //IPionek p = new DekoratorPionek(new ConcretePionek(im));
            IPionek p = new ConcretePionek(im);
            pionki.put(path, p);
            return p;
            }
            catch(IOException e)
            {
                throw new IOException("no such file");
            }
            
        }
    }
    
}
