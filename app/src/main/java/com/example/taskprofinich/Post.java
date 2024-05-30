package com.example.taskprofinich;

import java.io.Serializable;

public class Post implements Serializable {
    private String workerId;
    private String imageUrl;
    private String text;

    // Constructeur par défaut sans argument pour Firebase
    public Post() {
        // Ce constructeur vide est nécessaire pour la désérialisation par Firebase
    }

    // Constructeur avec tous les champs
    public Post(String workerId, String imageUrl, String text) {
        this.workerId = workerId;
        this.imageUrl = imageUrl;
        this.text = text;
    }

    // Getters et setters
    public String getWorkerId() {
        return workerId;
    }

    public void setWorkerId(String workerId) {
        this.workerId = workerId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
