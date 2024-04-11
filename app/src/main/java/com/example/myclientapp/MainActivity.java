package com.example.myclientapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.annotation.NonNull;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.Adapter;

import com.example.myclientapp.bbdd.DataBase;
import com.example.myclientapp.cliente.Cliente;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView recycler;
    Adapter adapter;
    List<Cliente> listaClientes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recycler = findViewById(id.recycler_clientes);
        DataBase clientesDB = new DataBase(this);
        listaClientes = clientesDB.getCliente();  //llamo al List de la bbdd

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);

        recycler.setLayoutManager(layoutManager);
        adapter = new Adapter(this, listaClientes);
        recycler.setAdapter(adapter);
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_anade, menu);
        return true;
    }
}