package com.example.myclientapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.annotation.NonNull;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Adapter;
import static com.example.myclientapp.R.*;
import com.example.myclientapp.adapter.AdapterCliente;
import com.example.myclientapp.bbdd.DataBase;
import com.example.myclientapp.cliente.ClientActivity;
import com.example.myclientapp.cliente.Cliente;
import com.example.myclientapp.cliente.NuevoClienteFragment;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView recycler;
    AdapterCliente adapter;
    List<Cliente> listaClientes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recycler = findViewById(id.recycler_client);
        DataBase clientesDB = new DataBase(this);
        listaClientes = clientesDB.getCliente();  //llamo al List de la bbdd

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);

        recycler.setLayoutManager(layoutManager);
        adapter = new AdapterCliente(this, listaClientes);
        recycler.setAdapter(adapter);
    }
    // Hago visible el menu
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_anade, menu);
        return true;
    }

    //Añado el onclic en el item seleccionado (el intent en el item del menu)
    // ponemos el intent en el item del menu
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.add);
        Intent i = new Intent(MainActivity.this, NuevoClienteFragment.class);
        startActivity(i);
        return super.onOptionsItemSelected(item);
    }
}