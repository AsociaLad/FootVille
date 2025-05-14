package com.emsi.footville.api;

import com.emsi.footville.models.Terrain;
import com.emsi.footville.models.Utilisateur;
import com.emsi.footville.models.Ville;
import com.emsi.footville.models.Reservation;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {
    // Authentification
    @POST("auth/login")
    Call<Utilisateur> login(@Body Utilisateur utilisateur);

    @POST("auth/register")
    Call<Utilisateur> register(@Body Utilisateur utilisateur);

    // Villes
    @GET("villes")
    Call<List<Ville>> getAllVilles();

    @GET("villes/{id}")
    Call<Ville> getVilleById(@Path("id") Long id);

    // Terrains
    @GET("terrains")
    Call<List<Terrain>> getAllTerrains();

    @GET("terrains/ville/{villeId}")
    Call<List<Terrain>> getTerrainsByVille(@Path("villeId") Long villeId);

    @GET("terrains/{id}")
    Call<Terrain> getTerrainById(@Path("id") Long id);

    // Réservations
    @GET("reservations/utilisateur/{userId}")
    Call<List<Reservation>> getReservationsByUser(@Path("userId") Long userId);

    @POST("reservations")
    Call<Reservation> createReservation(@Body Reservation reservation);

}