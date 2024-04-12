package com.example.myclientapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterCliente.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}

