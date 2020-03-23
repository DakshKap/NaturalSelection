package app;
import java.util.Random;
import global.Constants;


class Creature {
    private int speed;
    private int energy;
    private int sense;
    private int foodQuantity;
    private int[] position;
    private int generation;

    
    private Creature() {
        position = new int[2];
        energy = 50;
    }

    Creature(int speed, int sense,int[] position) {
        this.speed = speed;
        this.sense = sense;
        energy = 50;
        this.position = position;
        generation = 1;
    }

    // Add energy burning and check with every step taken
    public int[] move() {
        Random rd = new Random();
        int dir = rd.nextInt(4);
        switch (dir) {
            case 0:
                position[0] -= speed;
                break;
            case 1:
                position[1] += speed;
                break;
            case 2:
                position[0] += speed;
                break;
            case 3:
                position[1] -= speed;
                break;
        }
        energy -= 2*speed;
        // Keeping the creatures in bounds.
        if (position[0] < 0)
            position[0] = 1;

        if (position[1] < 0)
            position[1] = 1;

        if (position[0] >= Constants.XCAP)
            position[0] = Constants.XCAP-2;

        if (position[1] >= Constants.YCAP)
            position[1] = Constants.YCAP-2;

        return position;

    }

    // Add energy burning and check with every step taken
    public boolean move(int[] foodPos) {
        int xDist = Math.abs(foodPos[0] - position[0]);
        int yDist = Math.abs(foodPos[1] - position[1]);
        int xDir = (foodPos[0] - position[0]) / xDist;
        int yDir = (foodPos[1] - position[1]) / yDist;
        int tempSpeed = speed;
        if (xDist + yDist < tempSpeed) { // food is within the speed range of the creature
            position[0] = foodPos[0];
            position[1] = foodPos[1];
            energy = energy - 2 * (xDist + yDist);
            return true;
        } else {
            // movement towards the food if food is outside the range of the creature
            while (tempSpeed > 0) {
                if (xDist > 0) {
                    position[0] += xDir;
                    xDist--;
                    tempSpeed--;
                    energy -= 2;
                } else {
                    position[1] += yDir;
                    yDist--;
                    tempSpeed--;
                    energy -= 2;
                }
            }
            return false;
        }
    }

    public Creature reproduce(Creature parent, int[] pos) {
        int speedChance = (int) Math.random() * 100;
        int senseChance = (int) Math.random() * 100;
        Creature child = new Creature();

        // Speed
        if (speedChance < 50 && parent.speed > 1)
            child.speed = parent.speed / 2;
        else if (speedChance > 50)
            child.speed = parent.speed * 2;
        else
            child.speed = parent.speed;

        // Sense
        if (senseChance < 50 && parent.sense > 2)
            child.sense = parent.sense / 2;
        else if (senseChance > 50)
            child.sense = parent.sense * 2;
        else
            child.sense = parent.sense;
        child.position = pos;
        energy -= 50;
        child.generation = parent.generation + 1;
        return child;
    }

    public int[][] getSenseArea(){
        int[][] senseArea = {{position[0]-sense,position[1]},
                            {position[0]+sense,position[1]},
                            {position[0],position[1]-sense},
                            {position[0],position[1]+sense},
                            {position[0]+sense,position[1]+sense}, //Diagnol Coordinates
                            {position[0]-sense,position[1]-sense},
                            {position[0]+sense,position[1]-sense},
                            {position[0]-sense,position[1]+sense}};
        return senseArea;
    }

    // Getters & Setters
    public int getFoodQuantity() {
        return this.foodQuantity;
    }

    public void setFoodQuantity(int foodQuantity) {
        this.foodQuantity = foodQuantity;
    }

    public int[] getPosition() {
        return this.position;
    }

    public int getSpeed() {
        return this.speed;
    }

    public int getEnergy() {
        return this.energy;
    }
    public void addEnergy(int energy) {
        this.energy += energy;
    }

    public int getSense() {
        return this.sense;
    }
    public int getGeneration() {
        return this.generation;
    }


}