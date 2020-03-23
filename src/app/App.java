package app;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import global.Constants;
import global.Utility;

public class App {
    final static int xCap = Constants.XCAP;
    final static int yCap = Constants.YCAP;
    private static char[][] grid;
    private static ArrayList<Creature> crList;

    public static void main(String[] args) throws Exception {
        // Set up size of the grid.
        grid = new char[xCap][yCap];
        for (int i = 0; i < xCap; i++)
            Arrays.fill(grid[i], '-');
        crList = new ArrayList<Creature>();
        // create intial creatures
        initCreatures(5);
        setUpFood(20);
        System.out.println("................Intial State...........");
        Utility.printArr(grid);
        System.out.println("................After first move...........");
        for (Creature cr : crList) {
            int[] start = cr.getPosition();
            grid[start[0]][start[1]] = '-';

            // use sense to detect food

            int[] end = cr.move();
            // if the creature gets food
            ArrayList<Integer[]> foodList = foundFood(start, end);
            if (foodList.size() > 0) {
                cr.addEnergy(foodList.size() * 50);
            }
            // Check if the creature can reproduce
            if (cr.getEnergy() > 100) {
                int[] childPos = generateChildCord(end);
                crList.add(cr.reproduce(cr, childPos));
                grid[end[0]][end[1]] = 'C';
            }
            // Death of a creature
            if (cr.getEnergy() <= 0) {
                crList.remove(cr);
                cr = null;
            } else {
                grid[end[0]][end[1]] = 'C';
            }

        }
        Utility.printArr(grid);

    }

    // Trace through the path to see if the creature ran into food
    public static ArrayList<Integer[]> foundFood(int[] start, int[] end) {
        ArrayList<Integer[]> foodList = new ArrayList<>();
        int xMove = end[0] - start[0];
        int yMove = end[1] - start[1];
        int steps = Math.abs(xMove + yMove);
        int dir = 0;
        if (xMove >= 0 && yMove >= 0) {
            dir = 1;
        } else {
            dir = -1;
        }
        while (steps > 0) {
            if (xMove != 0) {
                start[0] += dir;
                if (grid[start[0]][start[1]] == 'F') {
                    Integer foodPos[] = { start[0], start[1] };
                    foodList.add(foodPos);
                }
                steps--;
            } else if (yMove != 0) {
                start[1] += dir;
                if (grid[start[0]][start[1]] == 'F') {
                    Integer foodPos[] = { start[0], start[1] };
                    foodList.add(foodPos);
                }
                steps--;
            }
        }
        return foodList;

    }

    // use sense ability to look for food
    public static int[] searchSenseArea(int[][] areaCord, int[] currPos) {
        int[] result = { -1, -1 };
        for (int i = 0; i < areaCord.length; i++) {
            int[] pointer = Arrays.copyOf(currPos, currPos.length);
            int xDist = Math.abs(areaCord[i][0] - currPos[0]);
            int yDist = Math.abs(areaCord[i][1] - currPos[1]);
            int xDir = (areaCord[i][0] - currPos[0]) / xDist;
            int yDir = (areaCord[i][1] - currPos[1]) / yDist;
            while (xDist + yDist > 0) {
                if (pointer[0] + xDir > 0 && pointer[1] + yDir > 0 && pointer[0] + xDir < Constants.XCAP
                        && pointer[1] + yDir < Constants.YCAP) {
                    if (grid[pointer[0] + xDir][pointer[1] + yDir] == 'F') {
                        result[0] = pointer[0] + xDir;
                        result[1] = pointer[1] + yDir;
                        return result;
                    }
                    pointer[0] += xDir;
                    pointer[1] += yDir;
                    if (xDist > 0)
                        xDist--;
                    if (yDist > 0)
                        yDist--;
                }
            }
        }
        return result;
    }

    // Generate coordinates for child while reproduction
    public static int[] generateChildCord(int[] position) {
        Random rd = new Random();
        int dir = rd.nextInt(4);
        switch (dir) {
            case 0:
                position[0] -= 1;
                break;
            case 1:
                position[1] += 1;
                break;
            case 2:
                position[0] += 1;
                break;
            case 3:
                position[1] -= 1;
                break;
        }
        // Keeping the creatures in bounds.
        if (position[0] < 0)
            position[0] = 1;

        if (position[1] < 0)
            position[1] = 1;

        if (position[0] >= Constants.XCAP)
            position[0] = Constants.XCAP - 2;

        if (position[1] >= Constants.YCAP)
            position[1] = Constants.YCAP - 2;

        while (grid[position[0]][position[1]] == 'C' || grid[position[0]][position[1]] == 'F') {
            position = generateChildCord(position);
        }
        return position;

    }

    public static void initCreatures(int number) {
        while (number > 0) {
            int[] pos = Utility.generateRandomCoordinates();
            Creature cr = new Creature(1, 2, pos);
            grid[pos[0]][pos[1]] = 'C';
            crList.add(cr);
            number--;
        }
    }

    public static void setUpFood(int quantity) {
        for (int i = 0; i < quantity; i++) {
            int[] temp = Utility.generateRandomCoordinates();
            if (grid[temp[0]][temp[1]] != 'F' || grid[temp[0]][temp[1]] != 'C') {
                grid[temp[0]][temp[1]] = 'F';
            } else {// to avoid duplication of food on the same spot
                i--;
            }
        }
    }

}