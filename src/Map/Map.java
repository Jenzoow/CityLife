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
public class Map {

    private Ground[][] xyMap;
    private final int width = 100;
    private final int height = 100;
    private int endX = 0, endY = 0;

    public Map() {
        init_map(width, height);
        init_trees();
        init_road();
        init_river();
        nextRoad();
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
                    endX++;
                }
            } else {// Vertical Up
                for (int i = sectionLength; (i >= 0); i--) {
                    if ((x - i) >= 0) {
                        xyMap[x - i][y] = ground;
                        endX--;
                    }
                }
            }
        } else {
            if (rightDown) { // Horizontal right
                for (int i = 0; (i < sectionLength) && ((y + i) < width); i++) {
                    xyMap[x][y + i] = ground;
                    endY++;
                }
            } else { // Horizontal Left
                for (int i = sectionLength; (i >= 0); i--) {
                    if ((y - i) > 0) {
                        xyMap[x][y - i] = ground;
                        endY--;
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

    private void init_river() {
        Random random = new Random();
        Water w = new Water();
        Ground g = w;
        boolean vertical = true, v;
        boolean horizontal = true, h;
        int sectionCount = random.nextInt(10);
        System.out.println(sectionCount);
        int endX = random.nextInt(xyMap.length);
        int endY = random.nextInt(xyMap.length);
        int teller = 0;
        do {
            Random r = new Random();
            int sectionLength = random.nextInt(10);
            System.out.println(sectionLength);

            v = (Math.random() < 0.5);
            h = (Math.random() < 0.5);

            vertical = v;
            horizontal = h;
            setGround(endX, endY, sectionLength, vertical, horizontal, g);
            teller++;
        } while (teller <= sectionCount);
    }

    private void nextRoad() {
        // Search buildable road;
        Ground road = new Road();
        boolean roadFound = false;
        boolean buildLeft = false;
        boolean buildUp = false;
        boolean buildRight = false;
        boolean buildDown = false;
        int x = 0;
        int y = 0;
        Ground buildableRoad = null;
        for (int i = 0; (i < width) && !roadFound; i++) {
            for (int j = 0; (j < height) && !roadFound; j++) {
                if (xyMap[i][j].name == "Road") {
                    // Road found, check buildable edges
                    try {
                        if (xyMap[i - 1][j].canBuildOn) {
                            roadFound = true;
                            buildUp = true;
                            buildableRoad = xyMap[i - 1][j];
                        }
                        if (xyMap[i + 1][j].canBuildOn) {
                            roadFound = true;
                            buildDown = true;
                            buildableRoad = xyMap[i + 1][j];
                        }
                        if (xyMap[i][j - 1].canBuildOn) {
                            roadFound = true;
                            buildLeft = true;
                            buildableRoad = xyMap[i][j - 1];
                        }
                        if (xyMap[i][j + 1].canBuildOn) {
                            roadFound = true;
                            buildRight = true;
                            buildableRoad = xyMap[i][j + 1];
                        }
                        x = i;
                        y = j;
                    } catch (Exception e) {
                        // Happens when looking for (i-1) at edge of map. No problem. Just an easy way to fix this!!
                    }
                }
            }
        }

        // Draw new road from found [x,y]
        // Pick direction
        Random random = new Random();
        boolean chosen = false;
        boolean vertical = false;
        boolean rightDown = false;
        int chosenDirection;
        while (!chosen) {
            chosenDirection = random.nextInt(4);
            switch (chosenDirection) {
                case 0: // UP
                    if (buildUp == true) {
                        chosen = true;
                        vertical = true;
                        rightDown = false;
                    }
                    break;
                case 1: // Down
                    if (buildDown == true) {
                        chosen = true;
                        vertical = true;
                        rightDown = true;
                    }
                    break;
                case 2: // Left
                    if (buildLeft == true) {
                        chosen = true;
                        vertical = false;
                        rightDown = false;
                    }
                    break;
                case 3: // Right
                    if (buildRight == true) {
                        chosen = true;
                        vertical = false;
                        rightDown = true;
                    }
                    break;
            }
        }
        Ground ground = new Road();
        setGround(x, y, random.nextInt(50) + 1, vertical, rightDown, ground);
    }
}
