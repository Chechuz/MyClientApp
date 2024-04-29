package com.example.myclientapp.cliente;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;


import androidx.activity.result.ActivityResultLauncher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.content.Intent;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myclientapp.MainActivity;
import com.example.myclientapp.R;
import com.example.myclientapp.bbdd.DataBase;
import com.example.myclientapp.camara.GaleryActivity;
import com.example.myclientapp.notas.NotasActivity;
import com.example.myclientapp.notas.NuevaNotaFragment;

import java.io.File;
import java.io.IOException;

public class ClientActivity extends AppCompatActivity {
  TextView verNom, verTel, verDir, verEmail, verOtro;
  Button btnEditar, btnVolver;
  ImageButton btn_camara, btn_galeria;
  Fragment fragmentEditaCl;
  FragmentTransaction fragmentTransaction;
  LinearLayout viewCliente;
  Cliente clModelo;
  String rutaImagen;

  int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cargarVista();

    }
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_borrar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()== android.R.id.home){
            finish();
            return true;
        }
        if(item.getItemId()==R.id.delete){
            DataBase db= new DataBase(this);
            Intent intent= getIntent();
            id=intent.getIntExtra("ID", 0);
            db.eliminaCliente(id);
            Toast.makeText(getApplicationContext(), "Cliente eliminado", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(ClientActivity.this, MainActivity.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }
    protected void cargarVista(){
        setContentView(R.layout.activity_client);
        getSupportActionBar().setTitle("Detalle del Cliente");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewCliente = (LinearLayout)findViewById(R.id.layout_contenedor);

        verNom = findViewById(R.id.tvNom);
        verDir = findViewById(R.id.tvDir);
        verTel = findViewById(R.id.tvTel);
        verEmail = findViewById(R.id.tvEmail);
        verOtro = findViewById(R.id.tvOtro);
        btnEditar = findViewById(R.id.btn_editar);
        btnVolver = findViewById(R.id.btn_volver);
        btn_camara = findViewById(R.id.camara);
        btn_galeria = findViewById(R.id.galeria);

        DataBase db = new DataBase(this);
        Intent intent = getIntent();
        id = intent.getIntExtra("ID", 0);
        clModelo = db.getClientes(id);

        verNom.setText(clModelo.getNombre());
        verDir.setText(clModelo.getDireccion());
        verTel.setText(clModelo.getTelefono());
        verEmail.setText(clModelo.getEmail());
        verOtro.setText(clModelo.getOtro());
        Toast.makeText(getApplicationContext(), "id " + clModelo.getId(), Toast.LENGTH_SHORT).show();

        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClientActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        btn_camara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validarPermisos()){
                    takePicture();
                }else{
                    Toast.makeText(ClientActivity.this, "DEBE DAR LOS PERMISOS", Toast.LENGTH_LONG).show();
                    loadRecomendationDialog();
                }
            }
        });
        btn_galeria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirGaleria();
            }
        });
    }

    private boolean validarPermisos() {
        if(Build.VERSION.PREVIEW_SDK_INT<Build.VERSION_CODES.M){
            return  true;
        }
        if((checkSelfPermission(CAMERA)==PackageManager.PERMISSION_GRANTED)&&
                (checkSelfPermission(WRITE_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED)){
            return true;
        }

        if((shouldShowRequestPermissionRationale(CAMERA)) ||
                (shouldShowRequestPermissionRationale(WRITE_EXTERNAL_STORAGE))){
            loadRecomendationDialog();
            }else{
            requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE,CAMERA},100);
            }
        Toast.makeText(ClientActivity.this, "DEBE DAR LOS PERMISOS", Toast.LENGTH_LONG).show();
        return false;
    }

    public void verNotas(View view){
        Intent intent = new Intent(ClientActivity.this, NotasActivity.class);
        intent.putExtra("id_cl", clModelo.getId());
        startActivity(intent);
    }
    public void addNota(View view){
        Bundle bundle = new Bundle();
        bundle.putInt("idCl",clModelo.getId());
        Log.i("id cl en ClientActivity",String.valueOf(clModelo.getId()));
       NuevaNotaFragment fragmentNuevaNota = new NuevaNotaFragment();
       fragmentNuevaNota.setArguments(bundle);
       fragmentTransaction=getSupportFragmentManager().beginTransaction();
       fragmentTransaction.replace(R.id.contenedor_nuevaNota,fragmentNuevaNota);
       fragmentTransaction.addToBackStack(null);
       fragmentTransaction.commit();
       viewCliente.setVisibility(View.INVISIBLE);
    }
    public void editClient(View view){
        Bundle bundle = new Bundle();
        bundle.putInt("id",id);

        fragmentEditaCl= new EditaClienteFragment();
        fragmentEditaCl.setArguments(bundle);
        fragmentTransaction=getSupportFragmentManager().beginTransaction()
                .replace(R.id.contenedor_editClient,fragmentEditaCl);
        fragmentTransaction.addToBackStack(null)
                .commit();

        viewCliente.setVisibility(View.INVISIBLE);
    }
    ///Metodos para habilitar la camara y tomar fotos



    private void loadRecomendationDialog() {
            AlertDialog.Builder dialog = new AlertDialog.Builder(ClientActivity.this);
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
    public void takePicture(){
        Log.i("accede a takePicture()", "si accede");
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        File imgArchivo = null;
        try {
            imgArchivo = crearImagen();
        }catch (IOException ex){
            Log.e("ERROR", ex.toString());
        }

        if(imgArchivo!=null){
            Uri fotoUri = FileProvider.getUriForFile(this, "com.example.myclientapp.fileprovider", imgArchivo);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, fotoUri);
            startActivityForResult(intent, 10);
        }
    }
    private File crearImagen() throws IOException {
        String nombreImg = "foto_";
        File directorio = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File imagenFile = File.createTempFile(nombreImg, ".jpg", directorio);

        rutaImagen = imagenFile.getAbsolutePath();
        return imagenFile;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10 && resultCode == RESULT_OK) {
            DataBase imgDB = new DataBase(this);
            imgDB.anadeImagen(rutaImagen,id);
            imgDB.close();
           abrirGaleria();
        }
    }

    private void abrirGaleria() {
        Intent iGaleria = new Intent(ClientActivity.this, GaleryActivity.class);
        Bundle extras = new Bundle();
        extras.putInt("id_cliente", id);
        iGaleria.putExtras(extras);
        startActivity(iGaleria);
    }


}
