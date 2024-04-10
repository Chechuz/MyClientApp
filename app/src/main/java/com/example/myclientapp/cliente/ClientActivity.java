package com.example.myclientapp.cliente;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.myclientapp.R;

public class ClientActivity extends AppCompatActivity {
    /**
     *
     Esta podria ser la "Vista del cliente?? o deberia crear un fragment para la vista
     del Cliente con su respectivo menú.???
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);
    }
}