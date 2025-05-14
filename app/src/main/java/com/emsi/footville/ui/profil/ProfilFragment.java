package com.emsi.footville.ui.profil;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.emsi.footville.R;
import com.emsi.footville.models.Utilisateur;
import com.emsi.footville.utils.SessionManager;

public class ProfilFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profil, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView textView = view.findViewById(R.id.text_profil);

        // Récupérer les informations de l'utilisateur
        SessionManager sessionManager = new SessionManager(requireContext());
        Utilisateur utilisateur = sessionManager.getUser();

        if (utilisateur != null) {
            String profilText = "Nom: " + utilisateur.getNom() + "\n" +
                    "Prénom: " + utilisateur.getPrenom() + "\n" +
                    "Email: " + utilisateur.getEmail() + "\n" +
                    "Téléphone: " + utilisateur.getTelephone();
            textView.setText(profilText);
        } else {
            textView.setText("Informations de profil indisponibles");
        }
    }
}