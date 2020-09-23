package com.abelsalcedo.mgworldv2.Model;

public class Cliente {
    String id;
    String username;
    String ape;
    String telf;
    String email;
    String image;
    String gustos;
    private String imageURL;
    private String status;
    private String search;
    private String bio;

    public Cliente() {
    }

    public Cliente(String id, String name, String ape, String telf, String email) {
        this.id = id;
        this.username = name;
        this.ape = ape;
        this.telf = telf;
        this.email = email;
        this.image = image;
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

    public String getName() {
        return username;
    }

    public void setName(String name) {
        this.username = name;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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