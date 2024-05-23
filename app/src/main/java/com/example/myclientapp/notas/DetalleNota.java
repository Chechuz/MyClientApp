package com.example.myclientapp.notas;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myclientapp.R;
import com.example.myclientapp.bbdd.DataBase;

public class DetalleNota extends AppCompatActivity {
    TextView verTitulo,verDetalle;
    Button atras;
    int id, idClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_nota);
        //cambio titulo del action bar
        getSupportActionBar().setTitle("Detalle de la Nota");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        verTitulo = findViewById(R.id.verTitulo);
        verDetalle = findViewById(R.id.verDetalle);
        atras = findViewById(R.id.btn_back_nota);

        seeNotes();

        atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back();
            }
        });

        }
    protected void seeNotes(){
        DataBase db = new DataBase(this);
        Intent intent = getIntent();
        id= intent.getIntExtra("noteID",0);
        Notas notaModelo = db.getNotas(id);
        idClient= notaModelo.getFk();

        verTitulo.setText(notaModelo.getTitulo());
        verDetalle.setText(notaModelo.getDetalle());
    }
        public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_borrar, menu);
        return true;
        }
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()== android.R.id.home){
            finish();
            return true;
        }
        if(item.getItemId()==R.id.delete){
            DataBase db= new DataBase(this);
            Intent intent= getIntent();
            id=intent.getIntExtra("noteID", 0);
            db.eliminaNota(id);
            Toast.makeText(getApplicationContext(), "Nota eliminada", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(DetalleNota.this, NotasActivity.class);
            i.putExtra("id_cl",idClient);
            Log.i("id del Cliente en Detalle nota", String.valueOf(idClient));
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }
    protected void back() {
        Intent intent = new Intent(DetalleNota.this, NotasActivity.class);
        intent.putExtra("id_cl",idClient);
        startActivity(intent);
    }


}