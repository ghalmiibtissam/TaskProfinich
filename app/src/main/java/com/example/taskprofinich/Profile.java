package com.example.taskprofinich;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class Profile extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int REQUEST_CODE_PERMISSION = 101;
    private CircleImageView profileImageView, addProfileButton;
    private CircleImageView notification, about, account, calandrier;
    private TextView textname, textemail;
    private Uri imageUri;
    private FirebaseDatabase db;
    private FirebaseStorage storage;
    private DatabaseReference workerRef;
    private Worker worker;
    private Uri selectedImageUri;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        fetchUserData();
        initViews();
        setupButtons();

    }


    private void initViews() {
        profileImageView = findViewById(R.id.profileImageView);
        addProfileButton = findViewById(R.id.addProfileButton);
        notification = findViewById(R.id.NOTIFICATION);
        about = findViewById(R.id.ABOUT);
        account = findViewById(R.id.accaunt);
        calandrier = findViewById(R.id.calandier);
        textname = findViewById(R.id.textname);
        textemail = findViewById(R.id.textemail);


        db = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();
    }


    private void fetchUserData() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String userId = user.getUid();

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference usersRef = database.getReference().child("workers").child(userId);

            usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        Worker worker = dataSnapshot.getValue(Worker.class);
                        if (worker != null) {
                            textname.setText(worker.getName());
                            textemail.setText(worker.getEmail());
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
        }
    }

    private void setupButtons() {
        addProfileButton.setOnClickListener(v -> {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_PERMISSION);
            } else {
                openGallery();
            }
        });

        notification.setOnClickListener(v -> {
            Intent intent = new Intent(Profile.this, Notification.class);
            startActivity(intent);
        });

        about.setOnClickListener(v -> {
            Intent intent = new Intent(Profile.this, About.class);
            startActivity(intent);
        });

        account.setOnClickListener(v -> {
            Intent intent = new Intent(Profile.this, Account.class);
            startActivity(intent);
        });

        calandrier.setOnClickListener(v -> {
            Intent intent = new Intent(Profile.this, Calendrier.class);
            startActivity(intent);
        });
        addProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });
    }

    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            selectedImageUri = data.getData();
            profileImageView.setImageURI(selectedImageUri);

            uploadImageToFirebase();
        }
    }

    private void uploadImageToFirebase() {
        if (selectedImageUri != null) {
            StorageReference storageRef = storage.getReference();
            StorageReference profileImageRef = storageRef.child("profileImages/" + FirebaseAuth.getInstance().getCurrentUser().getUid() + ".jpg");

            profileImageRef.putFile(selectedImageUri)
                    .addOnSuccessListener(taskSnapshot -> profileImageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                        String downloadUrl = uri.toString();
                        updateProfilePicUrl(downloadUrl);
                    }))
                    .addOnFailureListener(e -> Toast.makeText(Profile.this, "Failed to upload image: " + e.getMessage(), Toast.LENGTH_SHORT).show());
        }
    }

    private void updateProfilePicUrl(String url) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String userId = user.getUid();
            DatabaseReference userRef = db.getReference().child("workers").child(userId);

            userRef.child("profilePic").setValue(url)
                    .addOnSuccessListener(aVoid -> Toast.makeText(Profile.this, "Profile picture updated", Toast.LENGTH_SHORT).show())
                    .addOnFailureListener(e -> Toast.makeText(Profile.this, "Failed to update profile picture: " + e.getMessage(), Toast.LENGTH_SHORT).show());
        }
    }
}

