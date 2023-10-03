package com.example.practica6;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private int[] boxPositions = {0, 0, 0, 0, 0, 0, 0, 0, 0}; // 0 representa casilla vacía
    private int playerTurn = 1;
    private CountDownTimer timer;
    private TextView timerTextView1;

    private TextView gameTimer;
    private TextView playerOneWins;
    private TextView playerTwoWins;
    private int playerOneVictories = 0;
    private int playerTwoVictories = 0;
    private boolean gameActive = true;

    private static final int VICTORY_THRESHOLD = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView[] boxes = {
                findViewById(R.id.image1), findViewById(R.id.image2), findViewById(R.id.image3),
                findViewById(R.id.image4), findViewById(R.id.image5), findViewById(R.id.image6),
                findViewById(R.id.image7), findViewById(R.id.image8), findViewById(R.id.image9)
        };

        timerTextView1 = findViewById(R.id.timerTextView1);
        gameTimer = findViewById(R.id.gameTimer);
        playerOneWins = findViewById(R.id.playerOneWins);
        playerTwoWins = findViewById(R.id.playerTwoWins);

        for (int i = 0; i < boxes.length; i++) {
            final int position = i;
            boxes[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBoxClick((ImageView) view, position);
                }
            });
        }
        playerTurn = 1;
        startTurnTimer();
    }

    private void onBoxClick(ImageView box, int position) {
        if (gameActive && boxPositions[position] == 0) {
            boxPositions[position] = playerTurn;
            if (playerTurn == 1) {
                box.setImageResource(R.drawable.ximage);
            } else {
                box.setImageResource(R.drawable.oimage);
            }
            if (checkForWin()) {
                handleWin();
            } else if (checkForDraw()) {
                handleDraw();
            } else {
                switchPlayer();
            }
        }
    }

    private void switchPlayer() {
        playerTurn = (playerTurn == 1) ? 2 : 1;
        updateTurnText();
        if (timer != null) {
            timer.cancel();
        }
        startTurnTimer();
    }

    private void updateTurnText() {
        timerTextView1.setText("Es turno del jugador " + playerTurn);
    }

    private void startTurnTimer() {
        timer = new CountDownTimer(10000, 1000) { // 30 segundos
            public void onTick(long millisUntilFinished) {
                long seconds = millisUntilFinished / 1000;
                String timeLeftFormatted = String.format("%02d:%02d", seconds / 60, seconds % 60);
                gameTimer.setText("Tiempo restante: " + timeLeftFormatted);
            }

            public void onFinish() {
                // Temporizador finalizado, cambiar al turno del otro jugador
                switchPlayer();
            }
        }.start();
    }

    private boolean checkForWin() {
        for (int row = 0; row < 3; row++) {
            if (boxPositions[row * 3] == playerTurn &&
                    boxPositions[row * 3 + 1] == playerTurn &&
                    boxPositions[row * 3 + 2] == playerTurn) {
                return true;
            }
        }

        // Verificar victoria vertical
        for (int col = 0; col < 3; col++) {
            if (boxPositions[col] == playerTurn &&
                    boxPositions[col + 3] == playerTurn &&
                    boxPositions[col + 6] == playerTurn) {
                return true;
            }
        }

        // Verificar victoria diagonal (de izquierda a derecha)
        if (boxPositions[0] == playerTurn && boxPositions[4] == playerTurn && boxPositions[8] == playerTurn) {
            return true;
        }

        // Verificar victoria diagonal (de derecha a izquierda)
        if (boxPositions[2] == playerTurn && boxPositions[4] == playerTurn && boxPositions[6] == playerTurn) {
            return true;
        }

        return false;
    }

    private boolean checkForDraw() {
        for (int position : boxPositions) {
            if (position == 0) {
                return false; // Todavía hay casillas vacías
            }
        }
        return true; // Todas las casillas están llenas (empate)
    }

    private void handleWin() {
        if (playerTurn == 1) {
            playerOneVictories++;
            playerOneWins.setText("Victorias Jugador 1: " + playerOneVictories);
            if (playerOneVictories == VICTORY_THRESHOLD) {
                Toast.makeText(this, "¡Jugador 1 ha ganado el juego!", Toast.LENGTH_SHORT).show();
                redirectToAnotherView(playerTurn, playerOneVictories, playerTwoVictories);
            } else {
                Toast.makeText(this, "¡Jugador 1 ha ganado!", Toast.LENGTH_SHORT).show();
                resetRound();
            }
        } else {
            playerTwoVictories++;
            playerTwoWins.setText("Victorias Jugador 2: " + playerTwoVictories);
            if (playerTwoVictories == VICTORY_THRESHOLD) {
                Toast.makeText(this, "¡Jugador 2 ha ganado el juego!", Toast.LENGTH_SHORT).show();
                redirectToAnotherView(playerTurn, playerOneVictories, playerTwoVictories);
            } else {
                Toast.makeText(this, "¡Jugador 2 ha ganado!", Toast.LENGTH_SHORT).show();
                resetRound();
            }
        }
    }

    private void handleDraw() {
        Toast.makeText(this, "¡Empate!", Toast.LENGTH_SHORT).show();
        resetRound();
    }

    private void resetRound() {
        for (int i = 0; i < boxPositions.length; i++) {
            boxPositions[i] = 0;
            ImageView box = findViewById(getResources().getIdentifier("image" + (i + 1), "id", getPackageName()));
            box.setImageResource(android.R.color.transparent);
        }
        playerTurn = 1;
        gameActive = true;
        startTurnTimer();
    }

    private void redirectToAnotherView(int ganador, int victoriasJugador1, int victoriasJugador2) {
        Intent intent = new Intent(this, reinicio.class);

        intent.putExtra("ganador", ganador);
        intent.putExtra("victoriasJugador1", victoriasJugador1);
        intent.putExtra("victoriasJugador2", victoriasJugador2);

        startActivity(intent);
    }
}
