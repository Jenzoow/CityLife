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
    BuildDirection buildDirection;

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

    public String getImagePath(){
        return "/Images/" + name + ".jpg";
    }

    public BuildDirection getBuildDirection() {
        return buildDirection;
    }

    public void setBuildDirection(BuildDirection buildDirection) {
        this.buildDirection = buildDirection;
    }

    @Override
    public String toString() {
        String s = Character.toString(symbol);
        return s;
    }
}
