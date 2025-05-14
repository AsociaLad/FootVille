package com.emsi.footville.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.emsi.footville.models.Utilisateur;
import com.google.gson.Gson;

public class SessionManager {
    private static final String PREF_NAME = "FootVilleSession";
    private static final String KEY_IS_LOGGED_IN = "isLoggedIn";
    private static final String KEY_USER = "user";

    private final SharedPreferences pref;
    private final SharedPreferences.Editor editor;
    private final Context context;

    public SessionManager(Context context) {
        this.context = context;
        pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    public void setLogin(boolean isLoggedIn) {
        editor.putBoolean(KEY_IS_LOGGED_IN, isLoggedIn);
        editor.apply();
    }

    public boolean isLoggedIn() {
        return pref.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    public void saveUser(Utilisateur user) {
        Gson gson = new Gson();
        String json = gson.toJson(user);
        editor.putString(KEY_USER, json);
        editor.apply();
    }

    public Utilisateur getUser() {
        Gson gson = new Gson();
        String json = pref.getString(KEY_USER, null);
        return gson.fromJson(json, Utilisateur.class);
    }

    public void logout() {
        editor.clear();
        editor.apply();
    }
}