package com.example.myclientapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myclientapp.R;
import com.example.myclientapp.cliente.ClientActivity;
import com.example.myclientapp.cliente.Cliente;

import java.util.List;

public class AdapterCliente extends RecyclerView.Adapter<AdapterCliente.ViewHolder> {
    LayoutInflater inflater;
    List<Cliente> clienteModelo;

    public AdapterCliente(Context context, List<Cliente>clienteModelo){
        this.inflater=LayoutInflater.from(context);
        this.clienteModelo=clienteModelo;
    }
    @NonNull
    @Override
    public AdapterCliente.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.client_view,parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterCliente.ViewHolder holder, int position) {
        String nombre = clienteModelo.get(position).getNombre();
        String telefono = clienteModelo.get(position).getTelefono();

        holder.clNombre.setText(nombre);
        holder.clTelefono.setText(telefono);

    }

    @Override
    public int getItemCount() {
        return clienteModelo.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView clNombre, clTelefono;
        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            clNombre = itemView.findViewById(R.id.clNombre);
            clTelefono = itemView.findViewById(R.id.clTelefono);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), ClientActivity.class);
                    intent.putExtra("ID", clienteModelo.get(getAdapterPosition()).getId());
                    v.getContext().startActivity(intent);

                }
            });
        }
    }
}

