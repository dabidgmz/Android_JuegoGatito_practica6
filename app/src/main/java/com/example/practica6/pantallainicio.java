package com.example.practica6;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
public class pantallainicio extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantallainicio);
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            Intent intent = new Intent(pantallainicio.this, MainActivity.class);
            startActivity(intent);
            finish();
        }, 3000);
    }
}

