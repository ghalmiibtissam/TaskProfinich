package com.example.taskprofinich;
public class Commande {
    private String contenu;
    private String clientId;
    private String workerId;
    private String publication;
    private String numero;

    public Commande() {
        // Constructeur par défaut requis par Firebase
    }

    public Commande(String contenu, String clientId, String workerId , String publication, String numero) {
        this.contenu = contenu;
        this.clientId = clientId;
        this.workerId = workerId;
        this.publication = publication;
        this.numero = numero;
    }

    // Getters et Setters
    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getWorkerId() {
        return workerId;
    }

    public void setWorkerId(String workerId) {
        this.workerId = workerId;
    }

    public String getPublication() {
        return publication;
    }

    public void setPublication(String publication) {
        this.publication = publication;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }
}
