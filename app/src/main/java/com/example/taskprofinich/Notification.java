package com.example.taskprofinich;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Notification extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ClientCommandeAdapter clientCommandeAdapter;
    private List<Commande> commandes;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        recyclerView = findViewById(R.id.recycler_view_notifications);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        commandes = new ArrayList<>();
        clientCommandeAdapter = new ClientCommandeAdapter(commandes, this);
        recyclerView.setAdapter(clientCommandeAdapter);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        fetchCommandes();
    }

    private void fetchCommandes() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String workerId = user.getUid();
            DatabaseReference commandesRef = mDatabase.child("commandes");

            commandesRef.orderByChild("workerId").equalTo(workerId).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    commandes.clear();
                    for (DataSnapshot commandeSnapshot : dataSnapshot.getChildren()) {
                        Commande commande = commandeSnapshot.getValue(Commande.class);
                        if (commande != null) {
                            commandes.add(commande);
                        }
                    }
                    clientCommandeAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(Notification.this, "Failed to load commandes", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
