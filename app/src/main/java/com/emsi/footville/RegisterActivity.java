package com.emsi.footville;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.emsi.footville.api.ApiClient;
import com.emsi.footville.api.ApiService;
import com.emsi.footville.databinding.ActivityRegisterBinding;
import com.emsi.footville.models.Role;
import com.emsi.footville.models.Utilisateur;
import com.emsi.footville.ui.auth.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private ActivityRegisterBinding binding;
    private ApiService apiService;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        apiService = ApiClient.getClient().create(ApiService.class);
        mAuth = FirebaseAuth.getInstance();

        binding.registerButton.setOnClickListener(view -> {
            if (validateForm()) {
                registerUser();
            }
        });

        binding.loginLink.setOnClickListener(view -> {
            finish();
        });
    }

    private boolean validateForm() {
        boolean valid = true;
        String nom = binding.nomEdit.getText() != null ? binding.nomEdit.getText().toString().trim() : "";
        String prenom = binding.prenomEdit.getText().toString().trim();
        String email = binding.emailRegisterEdit.getText().toString().trim();
        String telephone = binding.telephoneEdit.getText().toString().trim();
        String password = binding.passwordRegisterEdit.getText().toString().trim();
        String confirmPassword = binding.confirmPasswordEdit.getText().toString().trim();

        // Validation du nom
        if (TextUtils.isEmpty(nom)) {
            binding.nomLayout.setError("Le nom est obligatoire");
            valid = false;
        } else {
            binding.nomLayout.setError(null);
        }

        // Validation du prénom
        if (TextUtils.isEmpty(prenom)) {
            binding.prenomLayout.setError("Le prénom est obligatoire");
            valid = false;
        } else {
            binding.prenomLayout.setError(null);
        }

        // Validation de l'email
        if (TextUtils.isEmpty(email)) {
            binding.emailRegisterLayout.setError("L'email est obligatoire");
            valid = false;
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.emailRegisterLayout.setError("Email invalide");
            valid = false;
        } else {
            binding.emailRegisterLayout.setError(null);
        }

        // Validation du téléphone
        if (TextUtils.isEmpty(telephone)) {
            binding.telephoneLayout.setError("Le téléphone est obligatoire");
            valid = false;
        } else {
            binding.telephoneLayout.setError(null);
        }

        // Validation du mot de passe
        if (TextUtils.isEmpty(password)) {
            binding.passwordRegisterLayout.setError("Le mot de passe est obligatoire");
            valid = false;
        } else if (password.length() < 6) {
            binding.passwordRegisterLayout.setError("Le mot de passe doit contenir au moins 6 caractères");
            valid = false;
        } else {
            binding.passwordRegisterLayout.setError(null);
        }

        // Validation de la confirmation du mot de passe
        if (!password.equals(confirmPassword)) {
            binding.confirmPasswordLayout.setError("Les mots de passe ne correspondent pas");
            valid = false;
        } else {
            binding.confirmPasswordLayout.setError(null);
        }

        return valid;
    }

    private void registerUser() {
        binding.progressBarRegister.setVisibility(View.VISIBLE);

        String nom = binding.nomEdit.getText() != null ? binding.nomEdit.getText().toString().trim() : "";
        String prenom = binding.prenomEdit.getText().toString().trim();
        String email = binding.emailRegisterEdit.getText().toString().trim();
        String telephone = binding.telephoneEdit.getText().toString().trim();
        String password = binding.passwordRegisterEdit.getText().toString().trim();

        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setNom(nom);
        utilisateur.setPrenom(prenom);
        utilisateur.setEmail(email);
        utilisateur.setTelephone(telephone);
        utilisateur.setMotDePasse(password);
        utilisateur.setRole(Role.UTILISATEUR); // Par défaut, on enregistre un utilisateur normal

        Call<Utilisateur> call = apiService.register(utilisateur);
        call.enqueue(new Callback<Utilisateur>() {
            @Override
            public void onResponse(Call<Utilisateur> call, Response<Utilisateur> response) {
                binding.progressBarRegister.setVisibility(View.GONE);

                if (response.isSuccessful() && response.body() != null) {
                    registerUserWithFirebase(email, password);
                    Toast.makeText(RegisterActivity.this, "Inscription réussie! Connectez-vous", Toast.LENGTH_LONG).show();

                    // Redirection vers l'écran de connexion
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    // Gérer les erreurs de l'API
                    if (response.code() == 409) {
                        Toast.makeText(RegisterActivity.this, "Cet email est déjà utilisé", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(RegisterActivity.this, "Erreur lors de l'inscription: " + response.message(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Utilisateur> call, Throwable t) {
                binding.progressBarRegister.setVisibility(View.GONE);
                Toast.makeText(RegisterActivity.this, "Erreur de connexion: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void registerUserWithFirebase(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this, task -> {
                if (task.isSuccessful()) {
                    // Inscription réussie
                } else {
                    // Erreur
                }
            });
    }
}