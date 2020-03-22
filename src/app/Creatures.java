package app;

import java.util.Random;

class Creature {
    private int speed;
    private int energy;
    private int sense;
    private int foodQuantity;
    private int[] position;
    
    private Creature() {
        position = new int[2];
        energy = 50;
    }

    Creature(int speed, int sense) {
        this.speed = speed;
        this.sense = sense;
        position = new int[2];
        energy = 50;
    }

    public void move(Creature cr) {
        Random rd = new Random();
        int dir = rd.nextInt(4);
        switch (dir) {
            case 0:
                position[0] += speed;
                break;
            case 1:
                position[1] += speed;
                break;
            case 2:
                position[0] += speed;
                break;
            case 3:
                position[1] += speed;
                break;
        }
        // Keeping the creatures in bounds.
        if (position[0] < 0)
            position[0] = 1;

        if (position[1] < 0)
            position[1] = 1;

        if (position[0] > 99)
            position[0] = 98;

        if (position[1] > 99)
            position[1] = 98;

    }
    public void move(Creature cr, int[] foodPos){
        int xDist = Math.abs(foodPos[0] - cr.position[0]);
        int yDist = Math.abs(foodPos[1] - cr.position[1]);
        int tempSpeed = cr.speed;
        if(xDist + yDist < tempSpeed){ // food is withinn the speed range of the creature
            cr.position[0] = foodPos[0];
            cr.position[1] = foodPos[1];
            energy = energy - 2*(xDist+yDist);
        }else{
           // movement towards the food if food is outside the range of the creature
        }
    }

    public Creature reproduce(Creature parent) {
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
        if (senseChance < 50 && parent.sense > 1)
            child.sense = parent.sense / 2;
        else if (senseChance > 50)
            child.sense = parent.sense * 2;
        else
            child.sense = parent.sense;

        return child;
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

    public int getSense() {
        return this.sense;
    }

}