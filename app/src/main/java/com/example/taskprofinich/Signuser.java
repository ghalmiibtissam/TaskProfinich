package com.example.taskprofinich;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Signuser extends AppCompatActivity {
    private EditText editTextName, editTextEmail, editTextPassword, editTextLocation;
    private Spinner categorySpinner;
    private Button signUpButton, loginButton;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signuser);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        editTextName = findViewById(R.id.nameuser);
        editTextEmail = findViewById(R.id.emailuser);
        editTextPassword = findViewById(R.id.Passworduser);
        editTextLocation = findViewById(R.id.localisation);
        categorySpinner = findViewById(R.id.categorySpinner);
        signUpButton = findViewById(R.id.signuserbutton);
        loginButton = findViewById(R.id.loginuserbutton);

        // Déclaration des catégories
        String[] categories = {"Plombier", "Maçon", "Couturier","Chef","Onglerist","Coiffeure","Mécanicien"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        categorySpinner.setAdapter(adapter);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createUser();
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLoginUserPage();
            }
        });
    }

    private void createUser() {
        String name = editTextName.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String category = categorySpinner.getSelectedItem().toString();
        String location = editTextLocation.getText().toString().trim();

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(location)) {
            Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            String userId = mAuth.getCurrentUser().getUid();

                            Worker newWorker = new Worker(userId, name, email, password, category, location, "");

                            mDatabase.child("workers").child(userId).setValue(newWorker)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Intent intent = new Intent(Signuser.this, Publicattion.class);
                                                intent.putExtra("workerName", name);
                                                startActivity(intent);
                                            } else {
                                                Toast.makeText(Signuser.this, "Erreur d'enregistrement dans la base de données", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        } else {
                            Toast.makeText(Signuser.this, "Erreur de création d'utilisateur", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void openLoginUserPage() {
        Intent intent = new Intent(this, Loginuser.class);
        startActivity(intent);
    }
}
