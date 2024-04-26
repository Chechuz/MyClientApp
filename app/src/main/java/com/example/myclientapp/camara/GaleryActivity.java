package com.example.myclientapp.camara;


import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;

import android.widget.GridView;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import  static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;


import com.example.myclientapp.R;
import com.example.myclientapp.adapter.AdapterGaleria;
import com.example.myclientapp.bbdd.DataBase;

import java.util.List;

public class GaleryActivity extends AppCompatActivity {
    GridView gridView;
    List<Imagenes> imagList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_galery);

        DataBase imgDB = new DataBase(this);
        imagList = imgDB.getImage();

        gridView = (GridView) findViewById(R.id.gv_galery_container);
        gridView.setAdapter(new AdapterGaleria(this,imagList));
        }

        private boolean checkForPermission() {
            if((checkSelfPermission(CAMERA) == PackageManager.PERMISSION_GRANTED) && (checkSelfPermission(WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)){
            return true;
            }
            if((shouldShowRequestPermissionRationale(CAMERA))||(shouldShowRequestPermissionRationale(WRITE_EXTERNAL_STORAGE))){
            loadRecomendationDialog();
            }else {
                requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE, CAMERA}, 100);
            }
            return false;
        }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == 100){
            if(grantResults.length==2 && grantResults[0]==PackageManager.PERMISSION_GRANTED && grantResults[1]==PackageManager.PERMISSION_GRANTED){
                Camara camara= new Camara();
                camara.takePicture();
            }
        }
    }

    private void loadRecomendationDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(GaleryActivity.this);
        dialog.setTitle("Permisos desactivados");
        dialog.setMessage("Debe aceptar los permisos para poder tomar fotos");
        dialog.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE, CAMERA}, 100);
            }
        });
        dialog.show();
    }


}



