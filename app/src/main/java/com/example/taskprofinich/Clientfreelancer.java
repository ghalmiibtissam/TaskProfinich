package com.example.taskprofinich;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Clientfreelancer extends AppCompatActivity {

    private static final String TAG = "Clientfreelancer";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clientfreelancer);

        Button clientButton = findViewById(R.id.btnclient);
        Button freelancerButton = findViewById(R.id.btnfreelancer);

        // Log statement to confirm onCreate is called
        Log.d(TAG, "onCreate: Activity Created");

        // Click listener for the client signup button
        clientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: Client button clicked");
                Intent intent = new Intent(Clientfreelancer.this, Signup.class);
                startActivity(intent);
            }
        });

        // Click listener for the freelancer signup button
        freelancerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: Freelancer button clicked");
                Intent intent = new Intent(Clientfreelancer.this, Signuser.class);
                startActivity(intent);
            }
        });
    }
}
