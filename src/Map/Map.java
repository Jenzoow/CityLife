/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Map;

import Buildings.House.*;


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
    private int numberOfRoads = 0;

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
        numberOfRoads += roadLength;
        setGround(x, y, roadLength, random.nextBoolean(), random.nextBoolean(), ground);

    }

    private void init_river() {
        Random random = new Random();
        Ground g = new Water();
        boolean vertical = true, v;
        boolean horizontal = true, h;
        int sectionCount = random.nextInt(10);
        //System.out.println(sectionCount);
        int endX = random.nextInt(xyMap.length);
        int endY = random.nextInt(xyMap.length);
        int teller = 0;
        do {
            int sectionLength = random.nextInt(10);
            //System.out.println(sectionLength);

            v = (Math.random() < 0.5);
            h = (Math.random() < 0.5);

            vertical = v;
            horizontal = h;
            setGround(endX, endY, sectionLength, vertical, horizontal, g);
            teller++;
        } while (teller <= sectionCount);
    }

    private void nextRoad() {
        // Search random buildable road
        Point p = searchBuildableRoad();

        // Draw new road from found point
        // Pick direction
        boolean[] direction = pickBuildableDirection(p.x, p.y);

        Ground ground = new Road();
        Random random = new Random();
        int roadLength = random.nextInt(50) + 1;
        numberOfRoads += roadLength;
        setGround(p.x, p.y, roadLength, direction[0], direction[1], ground);
    }
    
    private boolean buildableToUp(int x, int y){
        try {
            return xyMap[x - 1][y].canBuildOn;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean buildableToDown(int x, int y){
        try {
            return xyMap[x + 1][y].canBuildOn;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean buildableToLeft(int x, int y){
        try {
            return xyMap[x][y  - 1].canBuildOn;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean buildableToRight(int x, int y){
        try {
            return xyMap[x][y  + 1].canBuildOn;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean[] pickBuildableDirection(int x, int y){
        Random random = new Random();
        boolean chosen = false;
        int chosenDirection;
        boolean[] verticalAndRightDown = new boolean[2];
        while (!chosen) {
            chosenDirection = random.nextInt(4);
            switch (chosenDirection) {
                case 0: // UP
                    if (buildableToUp(x, y)) {
                        chosen = true;
                        verticalAndRightDown[0] = true;
                        verticalAndRightDown[1] = false;
                    }
                    break;
                case 1: // Down
                    if (buildableToDown(x, y)) {
                        chosen = true;
                        verticalAndRightDown[0] = true;
                        verticalAndRightDown[1] = true;
                    }
                    break;
                case 2: // Left
                    if (buildableToLeft(x, y)) {
                        chosen = true;
                        verticalAndRightDown[0] = false;
                        verticalAndRightDown[1] = false;
                    }
                    break;
                case 3: // Right
                    if (buildableToRight(x, y)) {
                        chosen = true;
                        verticalAndRightDown[0] = false;
                        verticalAndRightDown[1] = true;
                    }
                    break;
            }
        }
        return verticalAndRightDown;
    }
    
    private Point searchBuildableRoad(){
        Point p = new Point();

        Random random = new Random();
        boolean roadFound = false;               
        int randomRoad = random.nextInt(numberOfRoads);
        int roadsFound = 0;
        for (int i = 0; (i < width) && !roadFound; i++) {
            for (int j = 0; (j < height) && !roadFound; j++) {
                if (xyMap[i][j].name.equals("Road")) {
                    roadsFound++;
                    if (roadsFound == randomRoad) {
                        // Road found, check buildable edges
                            if ((buildableToDown(i, j)) || (buildableToLeft(i, j)) || (buildableToRight(i, j)) || buildableToUp(i, j)){
                                roadFound = true;
                            }
                            p.x = i;
                            p.y = j;
                    }
                }
            }
        }
        return p;
       
    }

    private void placeHouse(int x, int y, boolean vertical, boolean rightDown) {
        Ground hA1 = new HouseA1();
        Ground hA2 = new HouseA2();
        Ground hB1 = new HouseB1();
        Ground hB2 = new HouseB2();
        for (int i = x; i < 2; i++) {
            for (int j = y; j < 2; j++) {
                setGround(i, j, hA1);
            }
        }
    }


}

