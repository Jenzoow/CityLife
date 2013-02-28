package Map;

import Map.BuildDirection;
import Map.Ground;

import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: Jens
 * Date: 28/02/13
 * Time: 15:02
 * To change this template use File | Settings | File Templates.
 */
public class Needs {
    public static Ground[][] rotateBuilding(Ground[][] building) {
        Ground[][] newArray = new Ground[building[0].length][building.length];

        for (int i = 0; i < building[0].length; i++) {
            for (int j = building.length - 1; j >= 0; j--) {
                newArray[i][j] = building[j][i];
            }
        }
        return newArray;
    }

    public static BuildDirection randomBuildDirection(){
        BuildDirection buildDirection = null;
        Random random = new Random();
        int i = random.nextInt(4);
        switch (i){
            case 1: buildDirection = BuildDirection.LEFT; break;
            case 2: buildDirection = BuildDirection.RIGHT; break;
            case 3: buildDirection = BuildDirection.DOWN; break;
            case 4: buildDirection = BuildDirection.UP; break;
        }
        return buildDirection;
    }
}
