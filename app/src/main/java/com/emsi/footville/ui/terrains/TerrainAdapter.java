package com.emsi.footville.ui.terrains;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.core.content.ContextCompat;

import com.emsi.footville.R;
import com.emsi.footville.models.Terrain;

import java.util.List;

public class TerrainAdapter extends RecyclerView.Adapter<TerrainAdapter.TerrainViewHolder> {

    private final List<Terrain> terrainList;
    private final Context context;
    private final OnTerrainClickListener listener;

    public interface OnTerrainClickListener {
        void onTerrainClick(Terrain terrain);
    }

    public TerrainAdapter(Context context, List<Terrain> terrainList, OnTerrainClickListener listener) {
        this.context = context;
        this.terrainList = terrainList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public TerrainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_terrain, parent, false);
        return new TerrainViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TerrainViewHolder holder, int position) {
        Terrain terrain = terrainList.get(position);

        holder.textViewNomTerrain.setText(terrain.getNom());
        holder.textViewAdresseTerrain.setText(terrain.getAdresse());
        holder.textViewTypeSurface.setText(terrain.getTypeSurface().toString());
        holder.textViewCapaciteTerrain.setText(String.valueOf(terrain.getCapacite()));

        if (terrain.isEstDisponible()) {
            holder.textViewDisponibiliteTerrain.setText("Disponible");
            holder.textViewDisponibiliteTerrain.setBackgroundColor(context.getResources().getColor(android.R.color.holo_green_light));
            holder.textViewDisponibiliteTerrain.setTextColor(ContextCompat.getColor(context, R.color.design_default_color_primary));
            holder.buttonReserverTerrain.setEnabled(true);
        } else {
            holder.textViewDisponibiliteTerrain.setText("Non disponible");
            holder.textViewDisponibiliteTerrain.setBackgroundColor(context.getResources().getColor(android.R.color.holo_red_light));
            holder.textViewDisponibiliteTerrain.setTextColor(ContextCompat.getColor(context, R.color.design_default_color_primary));
            holder.buttonReserverTerrain.setEnabled(false);
        }

        holder.buttonReserverTerrain.setOnClickListener(v -> {
            if (listener != null) {
                listener.onTerrainClick(terrain);
            }
        });
    }

    @Override
    public int getItemCount() {
        return terrainList.size();
    }

    public static class TerrainViewHolder extends RecyclerView.ViewHolder {
        TextView textViewNomTerrain, textViewAdresseTerrain, textViewTypeSurface, textViewCapaciteTerrain, textViewDisponibiliteTerrain;
        Button buttonReserverTerrain;

        public TerrainViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewNomTerrain = itemView.findViewById(R.id.textViewNomTerrain);
            textViewAdresseTerrain = itemView.findViewById(R.id.textViewAdresseTerrain);
            textViewTypeSurface = itemView.findViewById(R.id.textViewTypeSurface);
            textViewCapaciteTerrain = itemView.findViewById(R.id.textViewCapaciteTerrain);
            textViewDisponibiliteTerrain = itemView.findViewById(R.id.textViewDisponibiliteTerrain);
            buttonReserverTerrain = itemView.findViewById(R.id.buttonReserverTerrain);
        }
    }
}