package com.emsi.footville.ui.terrains;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.emsi.footville.api.ApiClient;
import com.emsi.footville.api.ApiService;
import com.emsi.footville.databinding.FragmentTerrainsBinding;
import com.emsi.footville.models.Terrain;
import com.emsi.footville.models.Ville;
import com.emsi.footville.ui.reservations.NouvelleReservationActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TerrainsFragment extends Fragment implements TerrainAdapter.OnTerrainClickListener {

    private FragmentTerrainsBinding binding;
    private ApiService apiService;
    private List<Ville> villeList;
    private List<Terrain> terrainList;
    private TerrainAdapter terrainAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentTerrainsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialisation de l'API
        apiService = ApiClient.getClient().create(ApiService.class);

        // Initialisation des listes
        villeList = new ArrayList<>();
        terrainList = new ArrayList<>();

        // Configuration du RecyclerView
        binding.recyclerViewTerrains.setLayoutManager(new LinearLayoutManager(requireContext()));
        terrainAdapter = new TerrainAdapter(requireContext(), terrainList, this);
        binding.recyclerViewTerrains.setAdapter(terrainAdapter);

        // Charger les villes
        loadVilles();

        // Configurez le Spinner pour les villes
        binding.spinnerVilles.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Ville selectedVille = villeList.get(position);
                loadTerrainsByVille(selectedVille.getId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Ne rien faire
            }
        });
    }

    private void loadVilles() {
        binding.progressBarTerrains.setVisibility(View.VISIBLE);

        Call<List<Ville>> call = apiService.getAllVilles();
        call.enqueue(new Callback<List<Ville>>() {
            @Override
            public void onResponse(Call<List<Ville>> call, Response<List<Ville>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    villeList.clear();
                    villeList.addAll(response.body());

                    // Configurer le spinner avec les noms des villes
                    ArrayAdapter<Ville> adapter = new ArrayAdapter<>(
                            requireContext(),
                            android.R.layout.simple_spinner_item,
                            villeList);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    binding.spinnerVilles.setAdapter(adapter);

                    if (!villeList.isEmpty()) {
                        // Chargez les terrains pour la première ville par défaut
                        binding.spinnerVilles.setSelection(0);
                    } else {
                        binding.progressBarTerrains.setVisibility(View.GONE);
                        binding.textViewEmpty.setVisibility(View.VISIBLE);
                        binding.textViewEmpty.setText("Aucune ville disponible");
                    }
                } else {
                    Toast.makeText(requireContext(), "Erreur lors du chargement des villes", Toast.LENGTH_SHORT).show();
                    binding.progressBarTerrains.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<Ville>> call, Throwable t) {
                Toast.makeText(requireContext(), "Erreur réseau: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                binding.progressBarTerrains.setVisibility(View.GONE);
            }
        });
    }

    private void loadTerrainsByVille(Long villeId) {
        binding.progressBarTerrains.setVisibility(View.VISIBLE);
        binding.textViewEmpty.setVisibility(View.GONE);

        Call<List<Terrain>> call = apiService.getTerrainsByVille(villeId);
        call.enqueue(new Callback<List<Terrain>>() {
            @Override
            public void onResponse(Call<List<Terrain>> call, Response<List<Terrain>> response) {
                binding.progressBarTerrains.setVisibility(View.GONE);

                if (response.isSuccessful() && response.body() != null) {
                    terrainList.clear();
                    terrainList.addAll(response.body());
                    terrainAdapter.notifyDataSetChanged();

                    if (terrainList.isEmpty()) {
                        binding.textViewEmpty.setVisibility(View.VISIBLE);
                    } else {
                        binding.textViewEmpty.setVisibility(View.GONE);
                    }
                } else {
                    Toast.makeText(requireContext(), "Erreur lors du chargement des terrains", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Terrain>> call, Throwable t) {
                binding.progressBarTerrains.setVisibility(View.GONE);
                Toast.makeText(requireContext(), "Erreur réseau: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onTerrainClick(Terrain terrain) {
        // Redirection vers l'écran de réservation avec les détails du terrain
        Intent intent = new Intent(requireContext(), NouvelleReservationActivity.class);
        intent.putExtra("terrain_id", terrain.getId());
        startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}