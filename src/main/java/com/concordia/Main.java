package com.concordia;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        RobotController controller = new RobotController(10);
        Scanner scanner = new Scanner(System.in);

        System.out.println("--- Robot Motion Program ---");
        System.out.println("1. Run Specific Test Scenario");
        System.out.println("2. Interactive Mode");
        System.out.print("Select an option: ");
        
        String choice = scanner.nextLine();

        if (choice.equals("1")) {
            runSpecificScenario(controller);
        } else {
            runInteractiveMode(controller, scanner);
        }
    }

    private static void runSpecificScenario(RobotController controller) {
        System.out.println("\nExecuting Scenario: D -> M 2 -> R -> M 5 -> L -> M 3 -> L -> M 2\n");

        controller.execute("D");   // Pen down
        controller.execute("M 2"); // Move North 2 -> [0, 2]
        controller.execute("R");   // Face East
        controller.execute("M 5"); // Move East 5  -> [5, 2]
        controller.execute("L");   // Face North
        controller.execute("M 3"); // Move North 3 -> [5, 5]
        controller.execute("L");   // Face West
        controller.execute("M 2"); // Move West 2  -> [3, 5]

        // Verification Output
        controller.execute("C"); // Print Current State
        controller.execute("P"); // Print Floor

        Robot r = controller.getRobot();
    }

    private static void runInteractiveMode(RobotController controller, Scanner scanner) {
        System.out.println("Enter commands (Q to quit):");
        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("Q")) break;
            
            try {
                controller.execute(input);
            } catch (Exception e) {
                System.out.println("Error: Invalid command format. Try 'M 5' or 'I 10'.");
            }
        }
    }
}