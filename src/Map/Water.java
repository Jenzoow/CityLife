/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Map;

import java.awt.Color;

/**
 * @author Jens
 */
public class Water extends Ground {

    public Water(String name) {
        super(name, false);
    }

    public Water() {
        this("OWater");
    }


    @Override
    public String getImagePath() {
        return "/Images/Water.jpg";
    }
}
