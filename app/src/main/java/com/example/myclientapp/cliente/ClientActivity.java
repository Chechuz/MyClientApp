package com.example.myclientapp.cliente;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myclientapp.MainActivity;
import com.example.myclientapp.R;
import com.example.myclientapp.bbdd.DataBase;

public class ClientActivity extends AppCompatActivity {
  TextView verNom, verTel, verDir, verEmail, verOtro;
  int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);
        getSupportActionBar().setTitle("Detalle del Cliente");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        verNom = findViewById(R.id.tvNom);
        verDir = findViewById(R.id.tvDir);
        verTel = findViewById(R.id.tvTel);
        verEmail = findViewById(R.id.tvEmail);
        verOtro = findViewById(R.id.tvOtro);

        DataBase db = new DataBase(this);
        Intent intent = getIntent();
        id= intent.getIntExtra("ID",0);
        Cliente clModelo = db.getClientes(id);

        verNom.setText(clModelo.getNombre());
        verDir.setText(clModelo.getDireccion());
        verTel.setText(clModelo.getTelefono());
        verEmail.setText(clModelo.getEmail());
        verOtro.setText(clModelo.getOtro());
        Toast.makeText(getApplicationContext(), "id "+ clModelo.getId(), Toast.LENGTH_SHORT).show();
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
}