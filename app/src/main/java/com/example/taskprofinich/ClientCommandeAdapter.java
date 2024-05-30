package com.example.taskprofinich;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class ClientCommandeAdapter extends RecyclerView.Adapter<ClientCommandeAdapter.ViewHolder> {

    private List<Commande> commandes;
    private Context context;

    public ClientCommandeAdapter(List<Commande> commandes, Context context) {
        this.commandes = commandes;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_commande, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Commande commande = commandes.get(position);
        holder.bind(commande);
    }

    @Override
    public int getItemCount() {
        return commandes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView clientNameTextView;
        public TextView contenuTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            clientNameTextView = itemView.findViewById(R.id.clientNameTextView);
            contenuTextView = itemView.findViewById(R.id.contenuTextView);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    Commande commande = commandes.get(position);
                    Intent intent = new Intent(context, Clientdetail.class);
                    intent.putExtra("clientId", commande.getClientId());
                    context.startActivity(intent);
                }
            });
        }

        public void bind(Commande commande) {
            contenuTextView.setText(commande.getContenu());

            DatabaseReference clientRef = FirebaseDatabase.getInstance().getReference().child("clients").child(commande.getClientId());
            clientRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        Client client = dataSnapshot.getValue(Client.class);
                        if (client != null) {
                            clientNameTextView.setText(client.getName());
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
}
