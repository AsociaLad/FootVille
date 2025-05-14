package com.emsi.footville.ui.reservations;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.emsi.footville.api.ApiClient;
import com.emsi.footville.api.ApiService;
import com.emsi.footville.databinding.ActivityNouvelleReservationBinding;
import com.emsi.footville.models.Terrain;
import com.emsi.footville.utils.SessionManager;

import java.time.LocalDateTime;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NouvelleReservationActivity extends AppCompatActivity {

    private ActivityNouvelleReservationBinding binding;
    private ApiService apiService;
    private Long terrainId;
    private Terrain terrain;
    private LocalDateTime dateHeureDebut;
    private LocalDateTime dateHeureFin;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNouvelleReservationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        apiService = ApiClient.getClient().create(ApiService.class);
        sessionManager = new SessionManager(this);

        // Récupérer l'ID du terrain passé en paramètre
        terrainId = getIntent().getLongExtra("terrain_id", -1);
        if (terrainId == -1) {
            Toast.makeText(this, "Erreur: Terrain non trouvé", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Charger les détails du terrain
        loadTerrainDetails();

        // Configurer les sélecteurs de date et d'heure
        binding.buttonDateDebut.setOnClickListener(v -> showDatePicker(true));
        binding.buttonHeureDebut.setOnClickListener(v -> showTimePicker(true));
        binding.buttonDateFin.setOnClickListener(v -> showDatePicker(false));
        binding.buttonHeureFin.setOnClickListener(v -> showTimePicker(false));

        // Configurer les boutons
        binding.buttonConfirmerReservation.setOnClickListener(v -> confirmerReservation());
        binding.buttonAnnuler.setOnClickListener(v -> finish());
    }

    private void loadTerrainDetails() {
        binding.progressBarReservation.setVisibility(View.VISIBLE);

        Call<Terrain> call = apiService.getTerrainById(terrainId);
        call.enqueue(new Callback<Terrain>() {
            @Override
            public void onResponse(Call<Terrain> call, Response<Terrain> response) {
                binding.progressBarReservation.setVisibility(View.GONE);
                if (response.isSuccessful() && response.body() != null) {
                    terrain = response.body();
                    updateTerrainUI();
                } else {
                    Toast.makeText(NouvelleReservationActivity.this,
                            "Erreur lors du chargement des détails du terrain",
                            Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<Terrain> call, Throwable t) {
                binding.progressBarReservation.setVisibility(View.GONE);
                Toast.makeText(NouvelleReservationActivity.this,
                        "Erreur de connexion: " + t.getMessage(),
                        Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private void updateTerrainUI() {
        if (terrain != null) {
            binding.textViewNomTerrainDetail.setText(terrain.getNom());
            binding.textViewAdresseTerrainDetail.setText(terrain.getAdresse());

            String infos = "Type: " + terrain.getTypeSurface() + " | Capacité: " + terrain.getCapacite();
            binding.textViewInfosTerrainDetail.setText(infos);
        }
    }

    private void showDatePicker(boolean isStartDate) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    String date = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;
                    if (isStartDate) {
                        binding.textViewDateHeureDebut.setText("Date: " + date);
                        // Mettre à jour la date de début (à compléter)
                    } else {
                        binding.textViewDateHeureFin.setText("Date: " + date);
                        // Mettre à jour la date de fin (à compléter)
                    }
                }, year, month, day);
        datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());
        datePickerDialog.show();
    }

    private void showTimePicker(boolean isStartTime) {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                (view, selectedHour, selectedMinute) -> {
                    String time = selectedHour + ":" + (selectedMinute < 10 ? "0" : "") + selectedMinute;
                    if (isStartTime) {
                        String currentText = binding.textViewDateHeureDebut.getText().toString();
                        binding.textViewDateHeureDebut.setText(currentText + " - Heure: " + time);
                        // Mettre à jour l'heure de début (à compléter)
                    } else {
                        String currentText = binding.textViewDateHeureFin.getText().toString();
                        binding.textViewDateHeureFin.setText(currentText + " - Heure: " + time);
                        // Mettre à jour l'heure de fin (à compléter)
                    }
                }, hour, minute, true);
        timePickerDialog.show();
    }

    private void confirmerReservation() {
        // Vérifications à implémenter
        Toast.makeText(this, "Fonctionnalité à implémenter complètement", Toast.LENGTH_SHORT).show();

        // Création d'une réservation de base pour test
        /*
        Reservation reservation = new Reservation();
        reservation.setTerrain(terrain);
        reservation.setUtilisateur(sessionManager.getUser());
        reservation.setDateDebut(dateHeureDebut);
        reservation.setDateFin(dateHeureFin);
        reservation.setStatut(StatutReservation.EN_ATTENTE);
        reservation.setCommentaire(binding.editTextCommentaire.getText().toString());

        // Appel à l'API
        binding.progressBarReservation.setVisibility(View.VISIBLE);
        Call<Reservation> call = apiService.createReservation(reservation);
        call.enqueue(new Callback<Reservation>() {
            @Override
            public void onResponse(Call<Reservation> call, Response<Reservation> response) {
                binding.progressBarReservation.setVisibility(View.GONE);
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(NouvelleReservationActivity.this,
                            "Réservation créée avec succès",
                            Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(NouvelleReservationActivity.this,
                            "Erreur lors de la création de la réservation",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Reservation> call, Throwable t) {
                binding.progressBarReservation.setVisibility(View.GONE);
                Toast.makeText(NouvelleReservationActivity.this,
                        "Erreur de connexion: " + t.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
        */
    }
}