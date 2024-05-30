package com.example.taskprofinich;


import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Clientdetail extends AppCompatActivity {

    private TextView clientNameTextView;
    private TextView clientEmailTextView;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clientdetail);

        clientNameTextView = findViewById(R.id.clientNameTextView);
        clientEmailTextView = findViewById(R.id.clientEmailTextView);

        String clientId = getIntent().getStringExtra("clientId");
        mDatabase = FirebaseDatabase.getInstance().getReference().child("clients").child(clientId);

        fetchClientDetails(clientId);
    }

    private void fetchClientDetails(String clientId) {
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Client client = dataSnapshot.getValue(Client.class);
                    if (client != null) {
                        clientNameTextView.setText(client.getName());
                        clientEmailTextView.setText(client.getEmail());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error
            }
        });
    }
}
