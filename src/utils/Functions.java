package utils;

import Map.BuildDirection;
import Map.Ground;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: Jens
 * Date: 28/02/13
 * Time: 15:02
 * To change this template use File | Settings | File Templates.
 */
public class Functions {
    public static Ground[][] rotateCW(Ground[][] building) {
        final int M = building.length;
        final int N = building[0].length;
        Ground[][] ret = new Ground[N][M];

            for (int r = 0; r < M; r++) {
                for (int c = 0; c < N; c++) {
                    ret[c][M-1-r] = building[r][c];
                }
            }

        return ret;
    }

    public static BuildDirection randomBuildDirection(){
        BuildDirection buildDirection = null;
        Random random = new Random();
        int i = random.nextInt(4)+1;
        switch (i){
            case 1: buildDirection = BuildDirection.LEFT; break;
            case 2: buildDirection = BuildDirection.RIGHT; break;
            case 3: buildDirection = BuildDirection.DOWN; break;
            case 4: buildDirection = BuildDirection.UP; break;
        }
        return buildDirection;
    }

}
