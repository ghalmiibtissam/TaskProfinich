package com.example.taskprofinich;

import android.os.Bundle;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Account extends AppCompatActivity {

    private TextView accountNameTextView;
    private TextView accountEmailTextView;
    private TextView accountCategoryTextView;
    private TextView accountLocationTextView;
    private TextView accountPasswordTextView;
    private DatabaseReference usersRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        // Initialiser les TextViews
        accountNameTextView = findViewById(R.id.accaunt_name);
        accountEmailTextView = findViewById(R.id.accaunt_email);
        accountCategoryTextView = findViewById(R.id.accaunt_category);
        accountLocationTextView = findViewById(R.id.accaunt_location);
        accountPasswordTextView = findViewById(R.id.accaunt_password);

        // Récupérer les données de Firebase et afficher les informations du travailleur
        fetchUserData();
    }

    private void fetchUserData() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String userId = user.getUid();

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            usersRef = database.getReference().child("workers").child(userId);

            usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        Worker worker = dataSnapshot.getValue(Worker.class);
                        if (worker != null) {
                            accountNameTextView.setText(worker.getName());
                            accountEmailTextView.setText(worker.getEmail());
                            accountCategoryTextView.setText(worker.getCategory());
                            accountLocationTextView.setText(worker.getLocation());
                            accountPasswordTextView.setText(worker.getPassword());
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Gérer l'erreur de récupération des données depuis Firebase
                }
            });
        }
    }
}
