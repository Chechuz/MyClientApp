package com.example.myclientapp.notas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.example.myclientapp.R;
import com.example.myclientapp.adapter.AdapterCliente;
import com.example.myclientapp.cliente.Cliente;

import java.util.List;

public class NotasActivity extends AppCompatActivity {
    TextView tvEmptyNotas;
    RecyclerView recycler;
    AdapterCliente adapter;
    List<Cliente> listaClientes;
    Fragment fragmentNewClient;
    FragmentTransaction fragmentTransaction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notas);
    }
}