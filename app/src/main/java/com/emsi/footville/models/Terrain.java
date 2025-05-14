package com.emsi.footville.models;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Terrain implements Serializable {
    private Long id;
    private String nom;
    private String adresse;
    private TypeSurface typeSurface;
    private int capacite;
    private boolean estDisponible;
    private Ville ville;

    // Constructeurs
    public Terrain() {
    }

    public Terrain(Long id, String nom, String adresse, TypeSurface typeSurface, int capacite, boolean estDisponible) {
        this.id = id;
        this.nom = nom;
        this.adresse = adresse;
        this.typeSurface = typeSurface;
        this.capacite = capacite;
        this.estDisponible = estDisponible;
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

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public TypeSurface getTypeSurface() {
        return typeSurface;
    }

    public void setTypeSurface(TypeSurface typeSurface) {
        this.typeSurface = typeSurface;
    }

    public int getCapacite() {
        return capacite;
    }

    public void setCapacite(int capacite) {
        this.capacite = capacite;
    }

    public boolean isEstDisponible() {
        return estDisponible;
    }

    public void setEstDisponible(boolean estDisponible) {
        this.estDisponible = estDisponible;
    }

    public Ville getVille() {
        return ville;
    }

    public void setVille(Ville ville) {
        this.ville = ville;
    }

    public boolean estDisponiblePourReservation(LocalDateTime debut, LocalDateTime fin) {
        // La logique sera implémentée plus tard
        return estDisponible;
    }

    @Override
    public String toString() {
        return nom;
    }
}