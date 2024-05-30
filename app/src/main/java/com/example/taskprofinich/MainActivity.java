package com.example.taskprofinich;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class MainActivity extends AppCompatActivity {

    private static final int DELAY_TIME = 3000; // Délai en millisecondes (3 secondes dans cet exemple)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Utilisation d'un Handler pour déclencher le changement d'activité après un délai
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Intent pour ouvrir Main2Activity
                Intent intent;
                intent = new Intent(MainActivity.this, Clientfreelancer.class);
                startActivity(intent);
                // Fermer MainActivity
                finish();
            }
        }, DELAY_TIME);
    }
}