package com.abelsalcedo.mgworldv2.Model;

import android.widget.TextView;

public class Colaborador {

    String id;
    String username;
    String ape;
    String dni;
    String telf;
    String email;
    private String imageURL;
    private String status;
    private String search;
    private String bio;

    public Colaborador() {
    }

    public Colaborador(String id, String name, String ape, String dni, String telf, String email) {
        this.id = id;
        this.username = name;
        this.ape = ape;
        this.dni = dni;
        this.telf = telf;
        this.email = email;
        this.imageURL = imageURL;
        this.status = status;
        this.search = search;
        this.bio = bio;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getApe() {
        return ape;
    }

    public void setApe(String ape) {
        this.ape = ape;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getTelf() {
        return telf;
    }

    public void setTelf(String telf) {
        this.telf = telf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(TextView bio) {
        this.bio = bio;
    }
}