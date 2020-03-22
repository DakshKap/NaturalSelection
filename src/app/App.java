package app;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class App {
    public final static int xCap = 20;
    public final static int yCap = 20;
    private static char[][] grid;
    private static ArrayList<Creature> crList;
    public static void main(String[] args) throws Exception {
        // Set up size of the grid.
        grid = new char[xCap][yCap];
        for(int i=0;i<xCap;i++)
            Arrays.fill(grid[i], '-');
        crList = new ArrayList<Creature>();
        
        createCreatures(5);
        setUpFood(20);
        printArr(grid);
        //create intial creatures

    }

    public static void printArr(char[][] arr){
        for(int i=0;i<arr.length;i++){
            for(int j=0;j<arr[0].length;j++){
                System.out.print(arr[i][j]+" ");
            }
            System.out.println(" ");
        }
    }

    public static void createCreatures(int number){
        while(number > 0){
            int[] pos = generateRandomCoordinates();
            Creature cr = new Creature(1, 2, pos);
            grid[pos[0]][pos[1]] = 'C';
            crList.add(cr);
            number--;
        }
    }

    public static void setUpFood(int quantity){
        for(int i=0;i<quantity;i++){
            int[] temp = generateRandomCoordinates();
            if(grid[temp[0]][temp[1]] != 'F' || grid[temp[0]][temp[1]] != 'C'){
                grid[temp[0]][temp[1]] = 'F';
            }else{// to avoid duplication of food on the same spot
                i--;
            }
        }
    }

    public static int[] generateRandomCoordinates(){
        int[] cord = new int[2];
        Random rd = new Random();
        cord[0] = rd.nextInt(xCap);
        cord[1] = rd.nextInt(yCap);
        return cord;
    }
}