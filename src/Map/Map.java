/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Map;


import Buildings.House.House;
import utils.Functions;

import java.awt.*;
import java.util.Random;

/**
 * @author Jens
 */
public class Map {

    private Ground[][] xyMap;
    private final int width = 100;
    private final int height = 100;

    public Map() {
        init_map(width, height);
        init_trees();
        init_road();
        init_river();
        Random random = new Random();
        Ground ground = new Water();
        setGround(random.nextInt(width), random.nextInt(height), ground );
        growTrees(30, 16, ground);
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

    private void setGround(int x, int y, int sectionLength, BuildDirection buildDirection, Ground ground) {
        if (buildDirection == BuildDirection.DOWN) {
            for (int i = 0; (i < sectionLength) && ((x + i) < height); i++) {
                xyMap[x + i][y] = ground;
            }
        } else if (buildDirection == BuildDirection.UP) {
            for (int i = sectionLength; (i >= 0); i--) {
                if ((x - i) >= 0) {
                    xyMap[x - i][y] = ground;
                }
            }
        } else if (buildDirection == BuildDirection.RIGHT) {
            for (int i = 0; (i < sectionLength) && ((y + i) < width); i++) {
                if ((y + i) < width){
                    xyMap[x][y + i] = ground;
                }

            }
        } else if (buildDirection == BuildDirection.LEFT) {
            for (int i = sectionLength; (i >= 0); i--) {
                if ((y - i) > 0) {
                    xyMap[x][y - i] = ground;
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
        BuildDirection buildDirection = Functions.randomBuildDirection();

        setGround(x, y, roadLength, buildDirection, ground);

    }

    private void init_river() {
        Random random = new Random();
        Ground g = new Water();
        int sectionCount = random.nextInt(10) + 2;

        System.out.println(sectionCount);
        int endX = random.nextInt(xyMap.length);
        int endY = random.nextInt(xyMap.length);
        int teller = 0;
        BuildDirection buildDirection = Functions.randomBuildDirection();   ;
        do {
            int sectionLength = random.nextInt(10) + 2;
            System.out.println(sectionLength);
            BuildDirection nextBuildDirection;
            do {
                nextBuildDirection = Functions.randomBuildDirection();
            } while (nextBuildDirection == buildDirection.getOposite(buildDirection));
            buildDirection = nextBuildDirection;
            System.out.println(nextBuildDirection);
            setGround(endX, endY, sectionLength, nextBuildDirection, g);
            switch (buildDirection) {
                case RIGHT:
                    endY += sectionLength;
                    break;
                case DOWN:
                    endX += sectionLength;
                    break;
                case LEFT:
                    endY -= sectionLength;
                    break;
                case UP:
                    endX -= sectionLength;
                    break;
            }
            teller++;
        } while (teller <= sectionCount);
    }

    private void nextRoad() {
        // Search random buildable road
        Point p = searchBuildableRoad();

        // Draw new road from found point
        // Pick direction
        BuildDirection buildDirection = pickBuildableDirection(p.x, p.y);

        Ground ground = new Road();
        Random random = new Random();
        int roadLength = random.nextInt(50) + 1;
        setGround(p.x, p.y, roadLength, buildDirection, ground);
    }

    private boolean buildableToUp(int x, int y) {
        try {
            return xyMap[x - 1][y].canBuildOn;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean buildableToDown(int x, int y) {
        try {
            return xyMap[x + 1][y].canBuildOn;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean buildableToLeft(int x, int y) {
        try {
            return xyMap[x][y - 1].canBuildOn;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean buildableToRight(int x, int y) {
        try {
            return xyMap[x][y + 1].canBuildOn;
        } catch (Exception e) {
            return false;
        }
    }

    private BuildDirection pickBuildableDirection(int x, int y) {
        Random random = new Random();
        boolean chosen = false;
        int chosenDirection;
        BuildDirection buildDirection = null;
        while (!chosen) {
            chosenDirection = random.nextInt(4);
            switch (chosenDirection) {
                case 0: // UP
                    if (buildableToUp(x, y)) {
                        chosen = true;
                        buildDirection = BuildDirection.UP;
                    }
                    break;
                case 1: // Down
                    if (buildableToDown(x, y)) {
                        chosen = true;
                        buildDirection = BuildDirection.DOWN;
                    }
                    break;
                case 2: // Left
                    if (buildableToLeft(x, y)) {
                        chosen = true;
                        buildDirection = BuildDirection.LEFT;
                    }
                    break;
                case 3: // Right
                    if (buildableToRight(x, y)) {
                        chosen = true;
                        buildDirection = BuildDirection.RIGHT;
                    }
                    break;
            }
        }
        return buildDirection;
    }

    private Point searchBuildableRoad() {
        Point p = new Point();
        Random random = new Random();
        boolean roadFound = false;
//        System.out.println(countRoads());
        int randomRoad = random.nextInt(countRoads());
        int roadsFound = 0;
        for (int i = 0; (i < width) && !roadFound; i++) {
            for (int j = 0; (j < height) && !roadFound; j++) {
                if (xyMap[i][j].name.equals("Road")) {
                    roadsFound++;
                    if (roadsFound == randomRoad) {
                        // Road found, check buildable edges
                        if ((buildableToDown(i, j)) || (buildableToLeft(i, j)) || (buildableToRight(i, j)) || buildableToUp(i, j)) {
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

    private void placeHouse() {
        Point p = searchBuildableRoad();
//        System.out.println(p);
        BuildDirection buildDirection = pickBuildableDirection(p.x, p.y);
        House house = new House();
        Ground[][] grounds = house.getHousePlan();
        switch (buildDirection) {
            case RIGHT:
                for (int i = 0; i < grounds.length; i++) {
                    for (int j = 0; j < grounds[i].length; j++) {
                        grounds[i][j].setBuildDirection(BuildDirection.RIGHT);
                        setGround(p.x + i, p.y + j + 1, grounds[i][j]);
                    }
                }
                break;
            case DOWN:
                grounds = Functions.rotateCW(grounds);
                for (int i = 0; i < grounds.length; i++) {
                    for (int j = 0; j < grounds[i].length; j++) {
                        grounds[i][j].setBuildDirection(BuildDirection.DOWN);
                        setGround(p.x + i + 1, p.y + j, grounds[i][j]);
                    }
                }
                break;
            case LEFT:
                grounds = Functions.rotateCW(grounds);
                grounds = Functions.rotateCW(grounds);
                for (int i = 0; i < grounds.length; i++) {
                    for (int j = 0; j < grounds[i].length; j++) {
                        grounds[i][j].setBuildDirection(BuildDirection.LEFT);
                        setGround(p.x + i, p.y + j - 2, grounds[i][j]);
                    }
                }
                break;
            case UP:
                grounds = Functions.rotateCW(grounds);
                grounds = Functions.rotateCW(grounds);
                grounds = Functions.rotateCW(grounds);
                for (int i = 0; i < grounds.length; i++) {
                    for (int j = 0; j < grounds[i].length; j++) {
                        grounds[i][j].setBuildDirection(BuildDirection.UP);
                        setGround(p.x + i - 2, p.y + j, grounds[i][j]);
                    }
                }
                break;
        }
    }

    private int countRoads() {
        int numberOfRoads = 0;
        for (int i = 0; i < xyMap.length; i++) {
            for (int j = 0; j < xyMap[i].length; j++) {
                if (xyMap[i][j].name == "Road") {
                    numberOfRoads++;
                }
            }
        }
        return numberOfRoads;
    }


}

