package global;

import java.util.Random;

public class Utility {
    public static int[] generateRandomCoordinates(){
        int[] cord = new int[2];
        Random rd = new Random();
        cord[0] = rd.nextInt(Constants.XCAP);
        cord[1] = rd.nextInt(Constants.YCAP);
        return cord;
    }

    public static void printArr(char[][] arr){
        for(int i=0;i<arr.length;i++){
            for(int j=0;j<arr[0].length;j++){
                System.out.print(arr[i][j]+" ");
            }
            System.out.println(" ");
        }
    }
}