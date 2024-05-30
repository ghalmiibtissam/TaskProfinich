package com.example.taskprofinich;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Category extends AppCompatActivity {
    Button btnPlombier, btnMacon ,btnCouturier,btnCoiffeure,btnMecanicien,btnAgleriste,btnChef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        btnPlombier = findViewById(R.id.button_plumber);
        btnMacon = findViewById(R.id.button_macon);
        btnCouturier=findViewById(R.id.button_couturier);
        btnCoiffeure=findViewById(R.id.button_coifeur);
        btnChef=findViewById(R.id.button_chef);
        btnAgleriste=findViewById(R.id.button_angleriste);
        btnMecanicien=findViewById(R.id.button_mécanicien);

        btnPlombier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Category.this, Plombier.class);
                startActivity(intent);
            }
        });

        btnMacon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Category.this, Macon.class);
                startActivity(intent);
            }
        });
        btnCouturier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                {
                    Intent intent = new Intent(Category.this, Couturier.class);
                    startActivity(intent);
                }
            }
        });
        btnMecanicien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                {
                    Intent intent = new Intent(Category.this, Mecanicien.class);
                    startActivity(intent);
                }
            }
        });
        btnAgleriste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                {
                    Intent intent = new Intent(Category.this, Ongleriste.class);
                    startActivity(intent);
                }
            }
        });
        btnCoiffeure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                {
                    Intent intent = new Intent(Category.this, Coiffeure.class);
                    startActivity(intent);
                }
            }
        });
        btnChef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                {
                    Intent intent = new Intent(Category.this, Chef.class);
                    startActivity(intent);
                }
            }
        });

    }
}

