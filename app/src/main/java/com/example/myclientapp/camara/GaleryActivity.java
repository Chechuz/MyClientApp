package com.example.myclientapp.camara;

import android.content.Intent;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;

import android.util.Log;
import android.widget.GridView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myclientapp.R;
import com.example.myclientapp.adapter.AdapterGaleria;
import com.example.myclientapp.bbdd.DataBase;


import java.util.List;

public class GaleryActivity extends AppCompatActivity {
    GridView gridView;
    List<Imagenes> imagList;
    int id;
    String nombre;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_galery);
        getSupportActionBar().setTitle("Galería de Fotos");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        id = getIntent().getIntExtra("id_cliente",0);

        DataBase imgDB = new DataBase(this);
        imagList = imgDB.getImage(id);

        gridView = (GridView) findViewById(R.id.gv_galery_container);
        gridView.setAdapter(new AdapterGaleria(this,imagList));  // Este adaptador recibe la lista de imagenes y devuelve un ImageView
        }

        private String getNombreCl(){
            DataBase db = new DataBase(this);
        nombre = db.getClientes(id).getNombre();
        return nombre;
        }

}




