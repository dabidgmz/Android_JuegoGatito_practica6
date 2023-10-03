package com.example.practica6;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.view.View;

public class reinicio extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reinicio);
        Intent intent = getIntent();
        int ganador = intent.getIntExtra("ganador", 0); // 0 es un valor predeterminado si no se encuentra
        int victoriasJugador1 = intent.getIntExtra("victoriasJugador1", 0);
        int victoriasJugador2 = intent.getIntExtra("victoriasJugador2", 0);
        TextView messageText = findViewById(R.id.messageText);
        TextView victoriasText = findViewById(R.id.victoriasText);
        Button startAgainButton = findViewById(R.id.startAgainButton);
        if (ganador == 1) {
            messageText.setText("¡Jugador 1 ha ganado el juego!");
        } else if (ganador == 2) {
            messageText.setText("¡Jugador 2 ha ganado el juego!");
        } else {
            messageText.setText("Empate");
        }
        victoriasText.setText("Victorias Jugador 1: " + victoriasJugador1 + "\nVictorias Jugador 2: " + victoriasJugador2);
        startAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentToMain = new Intent(reinicio.this, MainActivity.class);
                startActivity(intentToMain);
                finish();
            }
        });
    }

}
