/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Map;

import java.awt.Color;

/**
 * @author Jens
 */
public abstract class Ground {

    String name;
    char symbol;
    boolean canBuildOn;

    public Ground(String name, Boolean canBuildOn) {
        this.name = name;
        symbol = name.charAt(0);
        this.canBuildOn = canBuildOn;
    }

    public Ground(String name) {
        this(name, true);
    }

    public Ground() {
        this("Ground");
    }

    public Color getColor() {
        return Color.black;
    }

    public String getImagePath(){
        return "/Images/" + name + ".jpg";
    }

    @Override
    public String toString() {
        String s = Character.toString(symbol);
        return s;
    }
}
