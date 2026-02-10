Robot Motion Project
Description

This project implements a robot simulation program in Java. The robot moves on an N × N grid and supports commands for initialization, movement, turning, pen control, grid printing, and history replay.

Features

Grid initialization (I n)

Move forward (M n)

Turn right (R)

Turn left (L)

Pen up / Pen down (U / D)

Print current state (C)

Print grid (P)

Replay history (H)

Quit (Q)
Technologies Used

Java 17

Maven

JUnit 5

How to Run

Compile:

mvn clean compile


Run tests:

mvn clean test


Run application:

mvn exec:java
Unit Testing

All core functionalities are verified using JUnit tests.

Example test summary:

Tests run: 7, Failures: 0, Errors: 0, Skipped: 0

Author

Godfred Kuwornu
Concordia University
COEN 6761
