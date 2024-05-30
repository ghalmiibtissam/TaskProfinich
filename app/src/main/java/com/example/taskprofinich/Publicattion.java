package com.example.taskprofinich;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Publicattion extends AppCompatActivity {

    private TextView textViewNom;
    private RecyclerView recyclerView;
    private PostAdapter postAdapter;
    private List<Post> postList;
    private CircleImageView profilePicture, bouttonProfile;
    private FirebaseDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publicattion);

        initViews();

        fetchUserData();

        setupButtons();

        fetchUserProfilePic();
    }

    private void initViews() {
        textViewNom = findViewById(R.id.username);
        recyclerView = findViewById(R.id.recycler_view);
        profilePicture = findViewById(R.id.profilePicture);
        bouttonProfile = findViewById(R.id.bouttonprofile);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        postList = new ArrayList<>();
        postAdapter = new PostAdapter(postList, this);
        recyclerView.setAdapter(postAdapter);

        db = FirebaseDatabase.getInstance();
    }

    private void fetchUserData() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String userId = user.getUid();

            DatabaseReference usersRef = db.getReference().child("workers").child(userId);

            usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        Worker worker = dataSnapshot.getValue(Worker.class);
                        if (worker != null) {
                            textViewNom.setText(worker.getName());
                            fetchPublications(worker.getId());
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(Publicattion.this, "Failed to load user data: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void fetchUserProfilePic() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String userId = user.getUid();
            DatabaseReference userRef = db.getReference().child("workers").child(userId).child("profilePic");

            userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        String profilePicUrl = dataSnapshot.getValue(String.class);
                        if (profilePicUrl != null) {
                            loadProfileImage(profilePicUrl);
                        }
                    } else {
                        Toast.makeText(Publicattion.this, "Profile picture not found.", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(Publicattion.this, "Failed to load profile picture: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void loadProfileImage(String url) {
        Glide.with(this)
                .load(url)
                .into(profilePicture);

    }

    private void fetchPublications(String workerId) {
        DatabaseReference postsRef = FirebaseDatabase.getInstance().getReference().child("posts");
        Query query = postsRef.orderByChild("workerId").equalTo(workerId);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                postList.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Post post = postSnapshot.getValue(Post.class);
                    if (post != null) {
                        postList.add(post);
                    }
                }
                postAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Publicattion.this, "Failed to load posts: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupButtons() {
        findViewById(R.id.home).setOnClickListener(v -> startActivity(new Intent(Publicattion.this, Clientfreelancer.class)));
        findViewById(R.id.bouttonajouter).setOnClickListener(v -> startActivity(new Intent(Publicattion.this, Publier.class)));
        bouttonProfile.setOnClickListener(v -> startActivity(new Intent(Publicattion.this, Profile.class)));
    }

    @Override
    protected void onResume() {
        super.onResume();
        addNewPostFromIntent();
    }

    private void addNewPostFromIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            String imageUrl = intent.getStringExtra("imageUri");
            String text = intent.getStringExtra("text");

            if (imageUrl != null && text != null) {
                Post newPost = new Post("", imageUrl, text);
                postList.add(newPost);
                postAdapter.notifyDataSetChanged();
            } else {
                Toast.makeText(this, "Les données du poste sont nulles.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
