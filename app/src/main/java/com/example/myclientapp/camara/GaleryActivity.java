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
    String path;
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==RESULT_OK){
            if(requestCode==1) {
                Uri miPath;
                int id;
                MediaScannerConnection.scanFile(this, new String[]{path}, null, new MediaScannerConnection.OnScanCompletedListener() {
                    @Override
                    public void onScanCompleted(String path, Uri uri) {
                        Log.i("Ruta de almacenamiento", "Path " + path);
                    }
                });
                miPath = data.getData();
                id = data.getIntExtra("idCliente",0);  //recibe el id del cliente que tomó la foto
                String uri = String.valueOf(miPath);
                DataBase imgDB = new DataBase(this);
                imgDB.anadeImagen(uri, id);      //  añado la imagen a la bbdd
            }
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_galery);

        DataBase imgDB = new DataBase(this);
        imagList = imgDB.getImage();

        gridView = (GridView) findViewById(R.id.gv_galery_container);
        gridView.setAdapter(new AdapterGaleria(this,imagList));  // Este adaptador recibe la lista de imagenes y devuelve un ImageView
        }



}



