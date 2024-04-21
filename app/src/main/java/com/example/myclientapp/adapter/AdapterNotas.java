package com.example.myclientapp.adapter;

import android.view.ViewGroup;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myclientapp.R;
import com.example.myclientapp.notas.DetalleNota;
import com.example.myclientapp.notas.Notas;

import java.util.List;

public class AdapterNotas extends RecyclerView.Adapter<AdapterNotas.ViewHolder> {
    LayoutInflater inflater;
    List<Notas> listaNotas;
    public AdapterNotas(Context context, List<Notas>notaModelo){
        this.inflater=LayoutInflater.from(context);
        this.listaNotas=notaModelo;
    }

    @NonNull
    @Override
    public AdapterNotas.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.note_view,parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String titulo = listaNotas.get(position).getTitulo();
        String fecha = listaNotas.get(position).getFecha();
        String hora = listaNotas.get(position).getHora();

        holder.nTitulo.setText(titulo);
        holder.nFecha.setText(fecha);
        holder.nHora.setText(hora);
    }


    @Override
    public int getItemCount() { return listaNotas.size(); }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nTitulo, nFecha, nHora;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nTitulo = itemView.findViewById(R.id.nTitulo);
            nFecha = itemView.findViewById(R.id.nFecha);
            nHora = itemView.findViewById(R.id.nHora);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), DetalleNota.class);
                    intent.putExtra("noteID", listaNotas.get(getAdapterPosition()).getId());
                    v.getContext().startActivity(intent);

                }
            });
        }
    }
}
