package com.example.taskprofinich;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
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
import de.hdodenhof.circleimageview.CircleImageView;

public class Clientact extends AppCompatActivity {

    private TextView nameTextView;
    private TextView locationTextView;
    private RecyclerView postsRecyclerView;
    private CircleImageView message, workerProfile;
    private PostAdapter postAdapter;
    private List<Post> postList;
    private String workerId;
    private String clientId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clientact);

        nameTextView = findViewById(R.id.nom);
        locationTextView = findViewById(R.id.locationTextView);
        postsRecyclerView = findViewById(R.id.recycler_view3);
        postsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        message = findViewById(R.id.message);
        workerProfile = findViewById(R.id.workerprofile);

        postList = new ArrayList<>();
        postAdapter = new PostAdapter(postList, this);
        postsRecyclerView.setAdapter(postAdapter);

        workerId = getIntent().getStringExtra("workerId");

        loadWorkerDetails(workerId);
        loadWorkerPosts(workerId);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            clientId = currentUser.getUid();
        } else {
            // Gérer le cas où l'utilisateur n'est pas connecté
        }

        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ouvrir l'activité Message et passer les identifiants du client et du travailleur
                Intent intent = new Intent(Clientact.this, Message.class);
                intent.putExtra("CLIENT_ID", clientId);
                intent.putExtra("WORKER_ID", workerId);
                startActivity(intent);
            }
        });

        // Ajouter un OnClickListener pour ouvrir la page WorkerProfile
        workerProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ouvrir l'activité WorkerProfile et passer l'identifiant du travailleur
                Intent intent = new Intent(Clientact.this, Workerprofile.class);
                intent.putExtra("WORKER_ID", workerId);
                startActivity(intent);
            }
        });
    }

    private void loadWorkerDetails(String workerId) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("workers").child(workerId);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Worker worker = dataSnapshot.getValue(Worker.class);
                if (worker != null) {
                    nameTextView.setText(worker.getName());
                    locationTextView.setText(worker.getLocation());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Gérer l'erreur
            }
        });
    }

    private void loadWorkerPosts(String workerId) {
        DatabaseReference postsReference = FirebaseDatabase.getInstance().getReference("posts");
        postsReference.orderByChild("workerId").equalTo(workerId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                postList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Post post = snapshot.getValue(Post.class);
                    if (post != null) {
                        postList.add(post);
                    }
                }
                postAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
}
