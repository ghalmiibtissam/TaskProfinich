package com.example.taskprofinich;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.UUID;

public class Publier extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;

    private EditText editTextMessage;
    private ImageView imageView,ouvrir;
    private Button buttonPublier;
    private Uri selectedImageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publier);

        editTextMessage = findViewById(R.id.editTextText);
        imageView = findViewById(R.id.ph_publier);
        buttonPublier = findViewById(R.id.publier);
        ouvrir = findViewById(R.id.ouvre_galry);

        ouvrir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        buttonPublier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = editTextMessage.getText().toString();
                if (!message.isEmpty() && selectedImageUri != null) {
                    uploadPost(message, selectedImageUri);
                } else {
                    Toast.makeText(Publier.this, "Please enter a message and select an image.", Toast.LENGTH_SHORT).show();
                }
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
            imageView.setImageURI(selectedImageUri);
        }
    }

    private void uploadPost(String message, Uri imageUri) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String userId = user.getUid();
            String postId = UUID.randomUUID().toString();
            DatabaseReference postsRef = FirebaseDatabase.getInstance().getReference().child("posts").child(postId);

            // Upload image to Firebase Storage
            StorageReference storageRef = FirebaseStorage.getInstance().getReference().child("post_images").child(postId);
            storageRef.putFile(imageUri).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    storageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                        String imageUrl = uri.toString();
                        Post newPost = new Post(userId, imageUrl, message);
                        postsRef.setValue(newPost).addOnCompleteListener(postTask -> {
                            if (postTask.isSuccessful()) {
                                Toast.makeText(Publier.this, "Post published successfully", Toast.LENGTH_SHORT).show();
                                // Optionally, start the Publicattion activity to see the new post
                                Intent intent = new Intent(Publier.this, Publicattion.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(Publier.this, "Failed to publish post", Toast.LENGTH_SHORT).show();
                            }
                        });
                    });
                } else {
                    Toast.makeText(Publier.this, "Failed to upload image", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
