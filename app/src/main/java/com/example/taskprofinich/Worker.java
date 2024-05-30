package com.example.taskprofinich;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
public class Worker implements Serializable {
    private String id;
    private String name;
    private String email;
    private String password;
    private String category;
    private String location;
    private String profilePic;


    // Constructeur par défaut sans argument pour Firebase
    public Worker() {
        // Ce constructeur vide est nécessaire pour la désérialisation par Firebase
    }

    // Constructeur avec tous les champs
    public Worker(String id, String name, String email, String password, String category, String location, String profilePic) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.category = category;
        this.location = location;
        this.profilePic = profilePic;

    }

    // Constructeur avec liste de publications
    public Worker(String id, String name, String email, String password, String category, String location, String profilePic, List<Post> publications) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.category = category;
        this.location = location;
        this.profilePic = profilePic;

    }

    // Getters et setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

}

