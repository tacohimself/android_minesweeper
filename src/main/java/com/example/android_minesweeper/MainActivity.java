package com.example.android_minesweeper;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private static final int ROWS = 10;
    private static final int COLS = 10;
    private static final int MINES = 10;

    private GameEngine gameEngine;
    private GridLayout gameGrid;
    private Button[][] buttons;
    private TextView mineCountText;
    private TextView timerText;
    
    private int secondsElapsed = 0;
    private boolean timerRunning = false;
    private Handler timerHandler = new Handler(Looper.getMainLooper());
    private Runnable timerRunnable = new Runnable() {
        @Override
        public void run() {
            if (timerRunning) {
                secondsElapsed++;
                updateTimerText();
                timerHandler.postDelayed(this, 1000);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gameGrid = findViewById(R.id.game_grid);
        mineCountText = findViewById(R.id.mine_count_text);
        timerText = findViewById(R.id.timer_text);
        Button restartButton = findViewById(R.id.restart_button);

        restartButton.setOnClickListener(v -> startNewGame());

        startNewGame();
    }

    private void startNewGame() {
        stopTimer();
        secondsElapsed = 0;
        updateTimerText();
        
        gameEngine = new GameEngine(ROWS, COLS, MINES);
        buttons = new Button[ROWS][COLS];
        gameGrid.removeAllViews();
        gameGrid.setRowCount(ROWS);
        gameGrid.setColumnCount(COLS);

        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                Button b = new Button(this);
                int size = getResources().getDisplayMetrics().widthPixels / (COLS + 1);
                GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                params.width = size;
                params.height = size;
                params.rowSpec = GridLayout.spec(r);
                params.columnSpec = GridLayout.spec(c);
                b.setLayoutParams(params);
                b.setPadding(0, 0, 0, 0);
                b.setMinimumWidth(0);
                b.setMinimumHeight(0);
                b.setIncludeFontPadding(false);
                b.setBackgroundResource(R.drawable.cell_raised);

                final int row = r;
                final int col = c;

                b.setOnClickListener(v -> {
                    if (!timerRunning && !gameEngine.isGameOver() && !gameEngine.isGameWon()) {
                        startTimer();
                    }
                    gameEngine.revealCell(row, col);
                    updateUI();
                });

                b.setOnLongClickListener(v -> {
                    gameEngine.toggleFlag(row, col);
                    updateUI();
                    return true;
                });

                buttons[r][c] = b;
                gameGrid.addView(b);
            }
        }
        updateUI();
    }

    private void startTimer() {
        timerRunning = true;
        timerHandler.postDelayed(timerRunnable, 1000);
    }

    private void stopTimer() {
        timerRunning = false;
        timerHandler.removeCallbacks(timerRunnable);
    }

    private void updateTimerText() {
        timerText.setText(getString(R.string.timer, secondsElapsed));
    }

    private void updateUI() {
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                Cell cell = gameEngine.getCell(r, c);
                Button b = buttons[r][c];

                if (cell.isRevealed()) {
                    b.setEnabled(false);
                    b.setBackgroundResource(R.drawable.cell_sunken);
                    if (cell.isMine()) {
                        b.setText("M");
                        b.setTextColor(Color.RED);
                    } else {
                        b.setText(cell.getNeighborMines() > 0 ? String.valueOf(cell.getNeighborMines()) : "");
                        b.setTextColor(Color.BLACK);
                    }
                } else {
                    b.setEnabled(true);
                    b.setBackgroundResource(R.drawable.cell_raised);
                    if (cell.isFlagged()) {
                        b.setText("F");
                        b.setTextColor(Color.BLUE);
                    } else {
                        b.setText("");
                    }
                }
            }
        }

        mineCountText.setText(getString(R.string.mines_left, MINES));

        if (gameEngine.isGameOver() || gameEngine.isGameWon()) {
            stopTimer();
            if (gameEngine.isGameOver()) {
                Toast.makeText(this, R.string.game_over, Toast.LENGTH_SHORT).show();
                revealAllMines();
            } else {
                Toast.makeText(this, R.string.you_win, Toast.LENGTH_SHORT).show();
                revealAllMines();
            }
        }
    }

    private void revealAllMines() {
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                Cell cell = gameEngine.getCell(r, c);
                if (cell.isMine()) {
                    buttons[r][c].setText("M");
                }
            }
        }
    }
}