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
    private final int mapWidth = 100;
    private final int mapHeight = 100;

    public Map() {
        init_map(mapWidth, mapHeight);
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
            for (int x = 0; x < mapWidth; x++) {
                for (int y = 0; y < mapHeight; y++) {
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
        return mapWidth;
    }

    public int getHeight() {
        return mapHeight;
    }

    public Ground[][] getXyMap() {
        return xyMap;
    }

    private void setGround(int x, int y, int sectionLength, BuildDirection buildDirection, Ground ground) {
        System.out.println("new " + ground);
        if (buildDirection == BuildDirection.DOWN) {
            for (int i = 1; (i < sectionLength); i++) {
                if ((x + i) < mapHeight && (y < mapWidth)) {
                    System.out.println(sectionLength);
                    System.out.println(xyMap[x + i][y].name);
                    if (!xyMap[x + i][y].canBuildOn) {
                        i = sectionLength;
                    } else {
                        xyMap[x + i][y] = ground;
                    }
                }
                System.out.println("jup down " + i);
            }
        } else if (buildDirection == BuildDirection.UP) {
            for (int i = 1; i < sectionLength && (x - i) >= 0; i++) {
                if (!xyMap[x - i][y].canBuildOn) {
                    i = sectionLength;
                } else {
                    xyMap[x - i][y] = ground;
                }
                System.out.println("jup up");
            }
        } else if (buildDirection == BuildDirection.RIGHT) {
            for (int i = 1; (i < sectionLength) && ((y + i) < mapWidth); i++) {
                if ((y + i) < mapWidth - 1 && (y >= 0) && (x >= 0)) {
                    if (!xyMap[x][y + i].canBuildOn) {
                        i = sectionLength;
                    } else {
                        xyMap[x][y + i] = ground;
                    }
                }
                System.out.println("jup right");

            }
        } else if (buildDirection == BuildDirection.LEFT) {
            for (int i = 1; i < sectionLength; i++) {
                if (!xyMap[x][y - i].canBuildOn) {
                    i = sectionLength;
                } else {
                    xyMap[x][y - i] = ground;
                }
                System.out.println("jup left");
            }
        }
    }


    private void setGround(int x, int y, Ground ground) {
        if ((x < mapHeight) && (y < mapWidth) && (x >= 0) && (y >= 0)) {
            xyMap[x][y] = ground;
        }
    }


    private void init_road() {
        Random random = new Random();
        int x = random.nextInt(mapHeight);
        int y = random.nextInt(mapWidth);
        Ground ground = new Road();
        int roadLength = random.nextInt(Math.max(mapHeight, mapWidth)) + 10;
        BuildDirection buildDirection = Functions.randomBuildDirection();
        setGround(x, y, roadLength, buildDirection, ground);
    }

    private void init_river() {
        Random random = new Random();
        Ground g = new Water();
        int sectionCount = random.nextInt(10) + 2;
        int endX = random.nextInt(xyMap.length);
        int endY = random.nextInt(xyMap.length);
        int teller = 0;
        BuildDirection buildDirection = Functions.randomBuildDirection();
        do {
            int sectionLength = random.nextInt(10) + 2;
            BuildDirection nextBuildDirection;
            do {
                nextBuildDirection = Functions.randomBuildDirection();
            } while (nextBuildDirection == buildDirection.getOposite(buildDirection));
            buildDirection = nextBuildDirection;
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

        Ground ground = new Water();
        setGround(random.nextInt(mapWidth), random.nextInt(mapHeight), ground);
        growTrees(30, 16, ground);
    }

    private void nextRoad() {
        Ground ground = new Road();
        Random random = new Random();
        int roadLength = random.nextInt(50) + 1;
        Point p = searchBuildableRoad(1, 1);
        BuildDirection buildDirection = pickBuildableDirection(p.x, p.y, 1, 1);
        setGround(p.x, p.y, roadLength, buildDirection, ground);
    }

    private boolean buildableToUp(int x, int y, int widthOnStreet, int height) {
        boolean canBuildOn = true;
        for (int i = 1; i <= widthOnStreet; i++) {
            for (int j = 0; j < height; j++)
                try {
                    if (!xyMap[x - i][y + j - 1].canBuildOn) {
                        canBuildOn = false;
                    }
                } catch (Exception e) {
                    canBuildOn = false;
                }
        }
        return canBuildOn;
    }

    private boolean buildableToDown(int x, int y, int width, int height) {
        boolean canBuildOn = true;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                try {
                    if (!xyMap[x + 1 + i][y + j].canBuildOn) {
                        canBuildOn = false;
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    canBuildOn = false;
                }
            }
        }
        return canBuildOn;
    }

    private boolean buildableToLeft(int x, int y, int width, int height) {
        boolean canBuildOn = true;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                try {
                    if (!xyMap[x + i][y + j - 2].canBuildOn) {
                        canBuildOn = false;
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    canBuildOn = false;
                }
            }
        }
        return canBuildOn;
    }

    private boolean buildableToRight(int x, int y, int width, int height) {
        boolean canBuildOn = true;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                try {
                    if (!xyMap[x + i][y + j + 1].canBuildOn) {
                        canBuildOn = false;
                    }
                } catch (Exception e) {
                    canBuildOn = false;
                }
            }
        }
        return canBuildOn;
    }

    private BuildDirection pickBuildableDirection(int x, int y, int width, int height) {
        Random random = new Random();
        boolean chosen = false;
        int chosenDirection;
        BuildDirection buildDirection = null;
        while (!chosen) {
            chosenDirection = random.nextInt(4);
            switch (chosenDirection) {
                case 0: // UP
                    if (buildableToUp(x, y, width, height)) {
                        chosen = true;
                        buildDirection = BuildDirection.UP;
                    }
                    break;
                case 1: // Down
                    if (buildableToDown(x, y, width, height)) {
                        chosen = true;
                        buildDirection = BuildDirection.DOWN;
                    }
                    break;
                case 2: // Left
                    if (buildableToLeft(x, y, width, height)) {
                        chosen = true;
                        buildDirection = BuildDirection.LEFT;
                    }
                    break;
                case 3: // Right
                    if (buildableToRight(x, y, width, height)) {
                        chosen = true;
                        buildDirection = BuildDirection.RIGHT;
                    }
                    break;
            }
        }
        return buildDirection;
    }

    private Point searchBuildableRoad(int width, int height) {
        Point p = new Point();
        boolean roadFound = false;
        int roadsSearched = 0;
        do {
            Random random = new Random();
            int randomRoad = random.nextInt(countRoads());
//            System.out.println("Random road: " + randomRoad);
            int roadsFound = 0;

            for (int i = 0; (i < mapHeight); i++) {
                for (int j = 0; (j < mapWidth); j++) {
                    if (xyMap[i][j].name.equals("Road")) {
                        roadsFound++;
                        if (roadsFound == randomRoad) {
                            roadsSearched++;
                            if ((buildableToDown(i, j, width, height)) || (buildableToLeft(i, j, width, height)) || (buildableToRight(i, j, width, height)) || buildableToUp(i, j, width, height)) {
//                                System.out.println("Buildable road found");
                                roadFound = true;
                                p.x = i;
                                p.y = j;
                            } else {
//                                System.out.println("No buildable direction");
                            }
//                            System.out.println(p);
                        }
                    }
                }
            }
//            System.out.println("Roadssearched: " + roadsSearched);
            if (roadsSearched > 100) {
                nextRoad();
                roadsSearched = 0;
            }
        } while (!roadFound);
        return p;

    }

    public void placeHouse() {
        Point p = searchBuildableRoad(2, 2);
        BuildDirection buildDirection = pickBuildableDirection(p.x, p.y, 2, 2);
        House house = new House();
        Ground[][] grounds = house.getHousePlan();
//        System.out.println(buildDirection);
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
                        setGround(p.x + i - 2, p.y + j - 1, grounds[i][j]);
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

