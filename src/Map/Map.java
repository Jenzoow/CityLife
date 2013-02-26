/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Map;

import java.awt.*;
import java.util.Random;

/**
 * @author Jens
 */
public class Map {      // Test

    private Ground[][] xyMap;
    private final int width = 100;
    private final int height = 100;

    public Map() {
        init_map(width, height);
        init_trees();
        init_road();
    }

    private void init_map(int width, int height) {
        xyMap = new Ground[height][width];

        Ground ground;
        ground = new Grass();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                setGround(i, j, ground);
            }
        }
    }

    private void init_trees() {
        Ground ground = new Tree();
        growTrees(1, 500, ground);
        growTrees(10, 16, ground);
        ground = new Flower();
        growTrees(1, 500, ground);
        growTrees(10, 16, ground);
    }

    private void growTrees(int times, int initialChance, Ground ground) {
        Random random = new Random();
        for (int i = 0; i < times; i++) {
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    int chance = initialChance;
                    try {
                        if (xyMap[x - 1][y] == ground) {
                            chance /= 2;
                        }
                        if (xyMap[x + 1][y] == ground) {
                            chance /= 2;
                        }
                        if (xyMap[x][y - 1] == ground) {
                            chance /= 2;
                        }
                        if (xyMap[x][y + 1] == ground) {
                            chance /= 2;
                        }
                    } catch (Exception e) {
                        // Happens when looking for (x-1) at edge of map. No problem. Just an easy way to fix this!!
                    }
                    if (chance != 16) {
                        int chanceOfTree = random.nextInt(chance);
                        if (chanceOfTree == 0) {
                            setGround(x, y, ground);
                        }
                    }
                }
            }
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Ground[][] getXyMap() {
        return xyMap;
    }

    private void setGround(int x, int y, int sectionLength, boolean vertical, boolean rightDown, Ground ground) {
        if (vertical) {
            if (rightDown) { // Vertical Down
                for (int i = 0; (i < sectionLength) && ((x + i) < height); i++) {
                    xyMap[x + i][y] = ground;
                }
            } else {// Vertical Up
                for (int i = sectionLength; (i >= 0); i--) {
                    if ((x - i) >= 0) {
                        xyMap[x - i][y] = ground;
                    }
                }
            }
        } else {
            if (rightDown) { // Horizontal right
                for (int i = 0; (i < sectionLength) && ((y + i) < width); i++) {
                    xyMap[x][y + i] = ground;
                }
            } else { // Horizontal Left
                for (int i = sectionLength; (i >= 0); i--) {
                    if ((y - i) > 0) {
                        xyMap[x][y - i] = ground;
                    }
                }
            }
        }

    }

    private void setGround(int x, int y, Ground ground) {
        if ((x < height) && (y < width)) {
            xyMap[x][y] = ground;
        }
    }


    private void init_road() {
        Random random = new Random();
        int x = random.nextInt(height);
        int y = random.nextInt(width);
        Ground ground = new Road();
        int roadLength = random.nextInt(Math.max(height, width)) + 10;
        setGround(x, y, roadLength, random.nextBoolean(), random.nextBoolean(), ground);
    }

    private  void init_shit(){}
}
