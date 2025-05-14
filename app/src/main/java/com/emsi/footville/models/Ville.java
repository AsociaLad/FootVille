package com.emsi.footville.models;

import java.io.Serializable;
import java.util.List;

public class Ville implements Serializable {
    private Long id;
    private String nom;
    private String codePostal;
    private String description;
    private List<Terrain> terrains;

    // Constructeurs
    public Ville() {
    }

    public Ville(Long id, String nom, String codePostal, String description) {
        this.id = id;
        this.nom = nom;
        this.codePostal = codePostal;
        this.description = description;
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Terrain> getTerrains() {
        return terrains;
    }

    public void setTerrains(List<Terrain> terrains) {
        this.terrains = terrains;
    }

    @Override
    public String toString() {
        return nom;
    }
}