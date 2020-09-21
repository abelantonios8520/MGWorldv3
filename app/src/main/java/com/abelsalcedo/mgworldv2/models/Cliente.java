package com.abelsalcedo.mgworldv2.models;

public class Cliente {
    String id;
    String name;
    String ape;
    String telf;
    String email;
    String image;
    String gustos;
    String fotoPerfilUrl;
    Object createdTimestamp;


    public Cliente() {
    }

    public Cliente(String id, String name, String ape, String telef, String email) {
        this.id = id;
        this.name = name;
        this.email = email;

    }

    public Cliente(String fotoPerfilUrl) {
        this.fotoPerfilUrl = fotoPerfilUrl;
    }

    public Cliente(String id, String name, String ape, String telf, String email, String image, String gustos, String fotoPerfilUrl, Object createdTimestamp) {
        this.id = id;
        this.name = name;
        this.ape = ape;
        this.telf = telf;
        this.email = email;
        this.image = image;
        this.gustos = gustos;
        this.fotoPerfilUrl = fotoPerfilUrl;
        this.createdTimestamp = createdTimestamp;
    }

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

    public String getFotoPerfilUrl() {
        return fotoPerfilUrl;
    }

    public void setFotoPerfilUrl(String fotoPerfilUrl) {
        this.fotoPerfilUrl = fotoPerfilUrl;
    }

    public Object getCreatedTimestamp() {
        return createdTimestamp;
    }

    public void setCreatedTimestamp(Object createdTimestamp) {
        this.createdTimestamp = createdTimestamp;
    }
}