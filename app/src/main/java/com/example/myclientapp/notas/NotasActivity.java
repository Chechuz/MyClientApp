package com.example.myclientapp.notas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.myclientapp.R;
import com.example.myclientapp.adapter.AdapterNotas;
import com.example.myclientapp.bbdd.DataBase;
import com.example.myclientapp.cliente.ClientActivity;


import java.util.List;

public class NotasActivity extends AppCompatActivity {
    TextView tvEmptyNotas;
    RecyclerView recycler;
    AdapterNotas adapter;
    List<Notas> listaNotas;
    Fragment fragmentNuevaNota;
    FragmentTransaction fragmentTransaction;
    Button btn_volver;
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notas);
        btn_volver = findViewById(R.id.btn_back);
        tvEmptyNotas = findViewById(R.id.tvEmptyNotas);
        tvEmptyNotas.setVisibility(View.INVISIBLE);
        recycler = findViewById(R.id.recycler_notas);

        DataBase db = new DataBase(this);
        //Obtengo el id del cliente que estaba visualizando
        Intent intent = getIntent();
        id = intent.getIntExtra("id_cl", 0);
        listaNotas = db.getNote(id);  //llamo al List de la bbdd


        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);

        recycler.setLayoutManager(layoutManager);
        adapter = new AdapterNotas(this, listaNotas);
        recycler.setAdapter(adapter);
        if(listaNotas.isEmpty()){
            tvEmptyNotas.setVisibility(View.VISIBLE);
        }
        btn_volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                volverAcliente();
            }
        });
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_anade, menu);
        return true;
    }
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.add){
            Bundle bundle = new Bundle();
            bundle.putInt("idCl",id);
            Log.i("id cliente",String.valueOf(id));
            fragmentNuevaNota= new NuevaNotaFragment();
            fragmentNuevaNota.setArguments(bundle);
            fragmentTransaction=getSupportFragmentManager().beginTransaction()
                .replace(R.id.contenedor_nueva_nota,fragmentNuevaNota)
                .addToBackStack(null);
            fragmentTransaction.commit();
            recycler.setVisibility(View.GONE);
            tvEmptyNotas.setVisibility(View.GONE);}
        return super.onOptionsItemSelected(item);
    }
    public void volverAcliente(){
        getOnBackPressedDispatcher().onBackPressed();
    }
}