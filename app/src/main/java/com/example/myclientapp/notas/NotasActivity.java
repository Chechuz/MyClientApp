package com.example.myclientapp.notas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.myclientapp.R;
import com.example.myclientapp.adapter.AdapterNotas;
import com.example.myclientapp.bbdd.DataBase;


import java.util.List;

public class NotasActivity extends AppCompatActivity {
    TextView tvEmptyNotas;
    RecyclerView recycler;
    AdapterNotas adapter;
    List<Notas> listaNotas;
    Fragment fragmentNuevaNota;
    FragmentTransaction fragmentTransaction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notas);
        tvEmptyNotas = findViewById(R.id.tvEmptyNotas);
        tvEmptyNotas.setVisibility(View.INVISIBLE);
        recycler = findViewById(R.id.recycler_notas);
        DataBase notasDB = new DataBase(this);
        listaNotas = notasDB.getNote();  //llamo al List de la bbdd

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);

        recycler.setLayoutManager(layoutManager);
        adapter = new AdapterNotas(this, listaNotas);
        recycler.setAdapter(adapter);
        if(listaNotas.isEmpty()){
            tvEmptyNotas.setVisibility(View.VISIBLE);
        }
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_anade, menu);
        return true;
    }
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.add){
            fragmentNuevaNota= new NuevaNotaFragment();
            fragmentTransaction=getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.contenedor_nueva_nota,fragmentNuevaNota);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
            recycler.setVisibility(View.GONE);
            tvEmptyNotas.setVisibility(View.GONE);}
        return super.onOptionsItemSelected(item);
    }
}