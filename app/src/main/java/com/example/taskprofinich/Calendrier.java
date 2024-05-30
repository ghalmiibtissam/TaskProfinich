package com.example.taskprofinich;


import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.Toast;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class Calendrier extends AppCompatActivity {

    private CalendarView calendarView;
    private Button saveButton;
    private String selectedDate;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendrier);

        calendarView = findViewById(R.id.calendarView);
        saveButton = findViewById(R.id.saveButton);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
            }
        });

        saveButton.setOnClickListener(view -> saveDateToDatabase());
    }

    private void saveDateToDatabase() {
        if (selectedDate != null) {
            String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
            String dateId = mDatabase.child("workers").child(userId).child("dates").push().getKey();

            mDatabase.child("users").child(userId).child("dates").child(dateId).setValue(selectedDate)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(Calendrier.this, "Date saved successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(Calendrier.this, "Failed to save date", Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            Toast.makeText(this, "Please select a date", Toast.LENGTH_SHORT).show();
        }
    }
}

