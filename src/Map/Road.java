/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Map;

import java.awt.Color;

/**
 * @author Jens
 */
public class Road extends Ground {

    public Road(String name) {
        super(name, false);
    }

    public Road() {
        this("Road");
    }

    @Override
    public Color getColor() {
        return Color.GRAY;
    }


}
