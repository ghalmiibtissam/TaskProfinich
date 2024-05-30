package com.example.taskprofinich;


import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;
import com.example.taskprofinich.Worker;
import com.example.taskprofinich.WorkerAdapter;

public class Coiffeure extends AppCompatActivity implements WorkerAdapter.OnWorkerClickListener {

    private RecyclerView recyclerView;
    private WorkerAdapter adapter;
    private List<Worker> CoiffeureList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coiffeure);

        recyclerView = findViewById(R.id.recycler_viewcoif);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        CoiffeureList = new ArrayList<>();
        adapter = new WorkerAdapter(CoiffeureList, this);
        recyclerView.setAdapter(adapter);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("workers");

        databaseReference.orderByChild("category").equalTo("Coiffeure").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                CoiffeureList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Worker worker = snapshot.getValue(Worker.class);
                    if (worker != null) {
                        CoiffeureList.add(worker);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    @Override
    public void onWorkerClick(Worker worker) {
        Intent intent = new Intent(this, Clientact.class);
        intent.putExtra("workerId", worker.getId());
        startActivity(intent);
    }
}


