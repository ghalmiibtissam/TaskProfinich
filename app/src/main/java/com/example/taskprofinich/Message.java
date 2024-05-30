package com.example.taskprofinich;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Message extends AppCompatActivity {

    private EditText contenuEditText;
    private EditText nomClientEditText;
    private EditText numeroCommandeEditText;
    private Button envoyerButton;
    private DatabaseReference commandeRef;
    private String clientId;
    private String workerId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        Intent intent = getIntent();
        clientId = intent.getStringExtra("CLIENT_ID");
        workerId = intent.getStringExtra("WORKER_ID");

        contenuEditText = findViewById(R.id.contenu_edit_text);
        nomClientEditText = findViewById(R.id.nom_client_edit_text);
        numeroCommandeEditText = findViewById(R.id.numero_commande_edit_text);
        envoyerButton = findViewById(R.id.envoyer_button);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        commandeRef = database.getReference("commandes");

        envoyerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                envoyerCommande();
            }
        });
    }

    private void envoyerCommande() {
        String contenu = contenuEditText.getText().toString().trim();
        String nomClient = nomClientEditText.getText().toString().trim();
        String numeroCommande = numeroCommandeEditText.getText().toString().trim();

        if (!contenu.isEmpty() && !nomClient.isEmpty() && !numeroCommande.isEmpty()) {
            Commande commande = new Commande(contenu, clientId, workerId, nomClient, numeroCommande);

            commandeRef.push().setValue(commande);

            contenuEditText.setText("");
            nomClientEditText.setText("");
            numeroCommandeEditText.setText("");

            Toast.makeText(Message.this, "Commande envoyée avec succès", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(Message.this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
        }
    }
}
