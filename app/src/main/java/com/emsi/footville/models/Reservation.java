package com.emsi.footville.models;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Reservation implements Serializable {
    private Long id;
    private Terrain terrain;
    private Utilisateur utilisateur;
    private LocalDateTime dateDebut;
    private LocalDateTime dateFin;
    private StatutReservation statut;
    private String commentaire;

    public Reservation() {
    }

    public Reservation(Long id, Terrain terrain, Utilisateur utilisateur, LocalDateTime dateDebut, LocalDateTime dateFin, StatutReservation statut, String commentaire) {
        this.id = id;
        this.terrain = terrain;
        this.utilisateur = utilisateur;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.statut = statut;
        this.commentaire = commentaire;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Terrain getTerrain() {
        return terrain;
    }

    public void setTerrain(Terrain terrain) {
        this.terrain = terrain;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public LocalDateTime getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(LocalDateTime dateDebut) {
        this.dateDebut = dateDebut;
    }

    public LocalDateTime getDateFin() {
        return dateFin;
    }

    public void setDateFin(LocalDateTime dateFin) {
        this.dateFin = dateFin;
    }

    public StatutReservation getStatut() {
        return statut;
    }

    public void setStatut(StatutReservation statut) {
        this.statut = statut;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }
}