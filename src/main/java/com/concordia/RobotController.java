package com.concordia;

import java.util.ArrayList;
import java.util.List;

public class RobotController {
    private Robot robot;

    public RobotController(int initialSize) {
        this.robot = new Robot(initialSize);
    }

    public void execute(String input) {
        if (input == null || input.isBlank()) return;
        
        String[] parts = input.trim().split("\\s+");
        char command = Character.toUpperCase(parts[0].charAt(0));

        // Add to history unless it's a history or quit command
        if (command != 'H' && command != 'Q') {
            robot.addToHistory(input);
        }

        switch (command) {
            case 'U' -> robot.setPenDown(false);
            case 'D' -> robot.setPenDown(true);
            case 'R' -> robot.turnRight();
            case 'L' -> robot.turnLeft();
            case 'M' -> robot.move(Integer.parseInt(parts[1]));
            case 'P' -> robot.printFloor();
            case 'C' -> robot.printCurrentState();
            case 'I' -> robot.initialize(Integer.parseInt(parts[1]));
            case 'H' -> replayHistory();
            case 'Q' -> System.out.println("Stopping program...");
            default  -> System.out.println("Invalid Command");
        }
    }

    private void replayHistory() {
        List<String> history = robot.getHistory();
        for (String cmd : history) {
            execute(cmd);
        }
    }
    
    public Robot getRobot() { return robot; }
}
