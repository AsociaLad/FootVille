package com.emsi.footville.ui.auth;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.emsi.footville.MainActivity;
import com.emsi.footville.RegisterActivity;
import com.emsi.footville.api.ApiClient;
import com.emsi.footville.api.ApiService;
import com.emsi.footville.databinding.ActivityLoginBinding;
import com.emsi.footville.models.Utilisateur;
import com.emsi.footville.utils.SessionManager;
import com.google.firebase.auth.FirebaseAuth;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private ApiService apiService;
    private SessionManager sessionManager;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        apiService = ApiClient.getClient().create(ApiService.class);
        sessionManager = new SessionManager(this);
        mAuth = FirebaseAuth.getInstance();

        // Si l'utilisateur est déjà connecté, rediriger vers MainActivity
        if (sessionManager.isLoggedIn()) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }

        binding.loginButton.setOnClickListener(view -> {
            String email = binding.email.getText().toString().trim();
            String password = binding.password.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(LoginActivity.this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
                return;
            }

            login(email, password);
        });

        binding.registerLink.setOnClickListener(view -> {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        });
    }

    private void login(String email, String password) {
        binding.progressBar.setVisibility(View.VISIBLE);

        Utilisateur utilisateur = new Utilisateur(email, password);

        Call<Utilisateur> call = apiService.login(utilisateur);
        call.enqueue(new Callback<Utilisateur>() {
            @Override
            public void onResponse(@NonNull Call<Utilisateur> call, @NonNull Response<Utilisateur> response) {
                binding.progressBar.setVisibility(View.GONE);

                if (response.isSuccessful() && response.body() != null) {
                    // Authentification réussie
                    Utilisateur loggedInUser = response.body();
                    sessionManager.setLogin(true);
                    sessionManager.saveUser(loggedInUser);

                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                } else {
                    // Échec de l'authentification
                    Toast.makeText(LoginActivity.this, "Identifiants incorrects", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Utilisateur> call, Throwable t) {
                binding.progressBar.setVisibility(View.GONE);
                Toast.makeText(LoginActivity.this, "Erreur de connexion: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loginWithFirebase(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this, task -> {
                if (task.isSuccessful()) {
                    // Connexion réussie
                } else {
                    // Erreur
                }
            });
    }
}