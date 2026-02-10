package com.concordia;

import org.junit.jupiter.api.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RobotControllerAssignmentTest {

    private PrintStream originalOut;
    private ByteArrayOutputStream outContent;

    @BeforeEach
    void captureStdOut() {
        originalOut = System.out;
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void restoreStdOut() {
        System.setOut(originalOut);
    }

    // 1) I command: initialize system
    @Test
    void I_shouldInitializeGridAndResetRobotState() {
        RobotController controller = new RobotController(10);

        controller.execute("I 10"); // required by assignment

        Robot r = controller.getRobot();
        assertNotNull(r, "Robot should exist after initialization");
        assertEquals(0, r.getX(), "Robot X should reset to 0");
        assertEquals(0, r.getY(), "Robot Y should reset to 0");
        assertFalse(r.isPenDown(), "Pen should be UP after initialization");
        assertEquals(Robot.Direction.NORTH, r.getFacing(), "Robot should face NORTH after initialization");

        int[][] floor = r.getFloor();
        assertNotNull(floor, "Floor array should exist after initialization");
        assertEquals(10, floor.length, "Floor should be 10x10 (rows)");
        assertEquals(10, floor[0].length, "Floor should be 10x10 (cols)");

        // all zeros
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                assertEquals(0, floor[row][col], "All floor cells should be 0 after init");
            }
        }
    }

    // 2) C command: print current state
    @Test
    void C_shouldPrintCurrentStateCorrectly() {
        RobotController controller = new RobotController(10);

        controller.execute("I 10");
        controller.execute("C");

        String output = outContent.toString().toLowerCase();
        assertTrue(output.contains("position"), "C output should include 'Position'");
        assertTrue(output.contains("0") , "C output should contain coordinates");
        assertTrue(output.contains("pen"), "C output should include 'Pen'");
        assertTrue(output.contains("up"), "Pen should be 'up' after init");
        assertTrue(output.contains("facing"), "C output should include 'Facing'");
        assertTrue(output.contains("north"), "Facing should be 'north' after init");
    }

    // 3) D command: pen down
    @Test
    void D_shouldSetPenDown() {
        RobotController controller = new RobotController(10);

        controller.execute("I 10");
        controller.execute("D");

        Robot r = controller.getRobot();
        assertTrue(r.isPenDown(), "Pen should be DOWN after D");
    }

    // 4) M command: move and mark floor when pen down
    @Test
    void M_withPenDown_shouldMoveAndMarkFloor() {
        RobotController controller = new RobotController(10);

        controller.execute("I 10");
        controller.execute("D");
        controller.execute("M 4"); // move north 4: from (0,0) -> (0,4)

        Robot r = controller.getRobot();
        assertEquals(0, r.getX(), "X should remain 0 when moving NORTH");
        assertEquals(4, r.getY(), "Y should become 4 after moving NORTH 4");

        // Verify floor marking happened on the path.
        // Assumption: floor indexed as floor[y][x] (row=y, col=x)
        int[][] floor = r.getFloor();
        for (int y = 0; y <= 4; y++) {
            assertEquals(1, floor[y][0], "Path cells should be marked as 1 when pen is down");
        }
    }

    // 5) R command: turn right
    @Test
    void R_shouldTurnRobotRight() {
        RobotController controller = new RobotController(10);

        controller.execute("I 10");
        controller.execute("R");

        Robot r = controller.getRobot();
        assertEquals(Robot.Direction.EAST, r.getFacing(), "After turning right from NORTH, facing should be EAST");
    }

    // 6) P command: print floor with asterisks for 1 and blanks for 0
    @Test
    void P_shouldPrintFloorUsingAsterisksForOnes() {
        RobotController controller = new RobotController(10);

        controller.execute("I 10");
        controller.execute("D");
        controller.execute("M 4");
        controller.execute("R");
        controller.execute("M 3"); // from (0,4) -> (3,4)
        controller.execute("P");

        String output = outContent.toString();

        // Minimal, robust checks:
        assertTrue(output.contains("*"), "Printed floor should contain '*' for traced cells");

        // Expect at least 10 lines for a 10x10 floor print (may be more due to prompts/other prints)
        long lineCount = output.lines().count();
        assertTrue(lineCount >= 10, "Printed output should contain at least 10 lines for a 10x10 floor");
    }

    // 7) H command: replay history + verify history captures commands
    @Test
    void H_shouldReplayHistoryWithoutCrashing_andHistoryShouldContainCommands() {
        RobotController controller = new RobotController(10);

        controller.execute("I 10");
        controller.execute("D");
        controller.execute("M 2");
        controller.execute("R");
        controller.execute("M 3");

        Robot r = controller.getRobot();

        // If your Robot stores history (your earlier code showed getHistory()), verify it:
        List<String> history = r.getHistory();
        assertNotNull(history, "History list should not be null");
        assertTrue(history.size() >= 5, "History should contain executed commands");
        assertEquals("I 10", history.get(0), "History should start with initialization command");

        // Replay should not crash
        assertDoesNotThrow(() -> controller.execute("H"), "H (history replay) should not throw");
    }
}
