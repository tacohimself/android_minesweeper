package com.example.android_minesweeper;

import java.util.Random;

public class GameEngine {
    private int rows;
    private int cols;
    private int totalMines;
    private Cell[][] grid;
    private boolean gameOver;
    private boolean gameWon;

    public GameEngine(int rows, int cols, int totalMines) {
        this.rows = rows;
        this.cols = cols;
        this.totalMines = totalMines;
        this.grid = new Cell[rows][cols];
        initGrid();
    }

    private void initGrid() {
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                grid[r][c] = new Cell();
            }
        }
        placeMines();
        calculateNeighbors();
    }

    private void placeMines() {
        Random random = new Random();
        int minesPlaced = 0;
        while (minesPlaced < totalMines) {
            int r = random.nextInt(rows);
            int c = random.nextInt(cols);
            if (!grid[r][c].isMine()) {
                grid[r][c].setMine(true);
                minesPlaced++;
            }
        }
    }

    private void calculateNeighbors() {
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (grid[r][c].isMine()) continue;
                int count = 0;
                for (int dr = -1; dr <= 1; dr++) {
                    for (int dc = -1; dc <= 1; dc++) {
                        int nr = r + dr;
                        int nc = c + dc;
                        if (nr >= 0 && nr < rows && nc >= 0 && nc < cols && grid[nr][nc].isMine()) {
                            count++;
                        }
                    }
                }
                grid[r][c].setNeighborMines(count);
            }
        }
    }

    public Cell getCell(int r, int c) {
        return grid[r][c];
    }

    public int getRows() { return rows; }
    public int getCols() { return cols; }
    public boolean isGameOver() { return gameOver; }
    public boolean isGameWon() { return gameWon; }

    public void revealCell(int r, int c) {
        if (gameOver || gameWon || grid[r][c].isRevealed() || grid[r][c].isFlagged()) return;

        grid[r][c].setRevealed(true);

        if (grid[r][c].isMine()) {
            gameOver = true;
            return;
        }

        if (grid[r][c].getNeighborMines() == 0) {
            for (int dr = -1; dr <= 1; dr++) {
                for (int dc = -1; dc <= 1; dc++) {
                    int nr = r + dr;
                    int nc = c + dc;
                    if (nr >= 0 && nr < rows && nc >= 0 && nc < cols) {
                        revealCell(nr, nc);
                    }
                }
            }
        }

        checkWin();
    }

    public void toggleFlag(int r, int c) {
        if (gameOver || gameWon || grid[r][c].isRevealed()) return;
        grid[r][c].setFlagged(!grid[r][c].isFlagged());
    }

    private void checkWin() {
        int revealedCount = 0;
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (grid[r][c].isRevealed()) revealedCount++;
            }
        }
        if (revealedCount == rows * cols - totalMines) {
            gameWon = true;
        }
    }
}
