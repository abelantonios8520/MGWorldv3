package com.abelsalcedo.mgworldv2.Model;

import android.view.View;

public class Cliente {

    String id;
    String username;
    String ape;
    String telf;
    String email;
    String gustos;
    String imageURL;
    String status;
    String search;
    String bio;

    public Cliente(){

    }

    public Cliente(String id, String username, String ape, String telf, String email, String gustos, String imageURL, String status, String search, String bio) {
        this.id = id;
        this.username = username;
        this.ape = ape;
        this.telf = telf;
        this.email = email;
        this.gustos = gustos;
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

    public String getGustos() {
        return gustos;
    }

    public void setGustos(String gustos) {
        this.gustos = gustos;
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

    public void setBio(String bio) {
        this.bio = bio;
    }
}