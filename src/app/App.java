package app;

import java.util.ArrayList;

public class App {
    char[][] grid = new char[10][10];
    ArrayList<Creature> crList = new ArrayList<Creature>();
    public static void main(String[] args) throws Exception {
        //create intial creatures
        
    }

    public static void printArr(int[][] arr){
        for(int i=0;i<arr.length;i++){
            for(int j=0;j<arr[0].length;j++){
                System.out.print(arr[i][j]+",");
            }
            System.out.println(" ");
        }
    }
}