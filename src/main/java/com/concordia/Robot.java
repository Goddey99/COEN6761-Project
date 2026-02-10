package com.concordia;

import java.util.ArrayList;
import java.util.List;

public class Robot {
    private int size;
    private int[][] floor;
    private int x, y;
    private boolean penDown;
    private Direction facing;
    private final List<String> history;

    public enum Direction {
        NORTH, EAST, SOUTH, WEST;
        public Direction turnRight() { return values()[(this.ordinal() + 1) % 4]; }
        public Direction turnLeft() { return values()[(this.ordinal() + 3) % 4]; }
    }

    public Robot(int n) {
        if (n <= 0) throw new IllegalArgumentException("Size must be > 0");
        this.history = new ArrayList<>();
        initialize(n);
    }

    public void initialize(int n) {
        this.size = n;
        this.floor = new int[n][n];
        this.x = 0;
        this.y = 0;
        this.penDown = false;
        this.facing = Direction.NORTH;
        System.out.print("System initialized with floor size " + n + "x" + n + ". ");
        // History is NOT cleared on 'I' command per typical requirements, 
        // but can be cleared if "since last start" implies initialization.
    }

    public void move(int steps) {
        for (int i = 0; i < steps; i++) {
            int nextX = x;
            int nextY = y;

            switch (facing) {
                case NORTH -> nextY++;
                case SOUTH -> nextY--;
                case EAST  -> nextX++;
                case WEST  -> nextX--;
            }

            if (nextX >= 0 && nextX < size && nextY >= 0 && nextY < size) {
                x = nextX;
                y = nextY;
                if (penDown) floor[y][x] = 1;
            } else {
                break; // Stop at boundary
            }
        }
    }

    public void turnRight() { facing = facing.turnRight(); }
    public void turnLeft() { facing = facing.turnLeft(); }
    public void setPenDown(boolean down) { 
        this.penDown = down; 
        if (penDown) floor[y][x] = 1; // Mark current spot when pen goes down
    }

    public void printCurrentState() {
        System.out.printf("Position: [%d, %d] - Pen: %s - Facing: %s%n", 
            x, y, (penDown ? "Down" : "Up"), facing);
    }

    public void printFloor() {
        int n = size;
        int[][] floor = this.floor;
        for (int i = n - 1; i >= 0; i--) {
            System.out.print(i + " ");
            for (int j = 0; j < n; j++) {
                System.out.print(floor[i][j] == 1 ? "* " : "  ");
            }
            System.out.println();
        }
        // Print X-axis indices
        System.out.print("  ");
        for (int i = 0; i < n; i++) System.out.print(i + " ");
        System.out.println();
    }

    public void addToHistory(String cmd) { history.add(cmd); }
    public List<String> getHistory() { return new ArrayList<>(history); }

    // Getters for Testing
    public int getX() { return x; }
    public int getY() { return y; }
    public boolean isPenDown() { return penDown; }
    public Direction getFacing() { return facing; }
    public int[][] getFloor() { return floor; }
    public int getSize() { return size; }
}