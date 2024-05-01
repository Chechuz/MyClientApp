package com.example.myclientapp.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myclientapp.MainActivity;
import com.example.myclientapp.R;

public class Login extends AppCompatActivity {
    Button btnEntrar;
    EditText etiUser, etiPass;
    SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnEntrar=(Button) findViewById(R.id.btn_entrar);
        etiUser =(EditText) findViewById(R.id.eti_user);
        etiPass =(EditText) findViewById(R.id.eti_pass);

        preferences = getSharedPreferences("loginData", Context.MODE_PRIVATE);
        etiUser.setText(preferences.getString("user", ""));
        etiPass.setText(preferences.getString("pass", ""));
    }
    public void entrar (View view){
        preferences = getSharedPreferences("loginData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("user", etiUser.getText().toString());
        editor.putString("pass", etiPass.getText().toString());
        editor.apply();

        Intent intent = new Intent(Login.this, MainActivity.class);
        startActivity(intent);

        Toast.makeText(this, "Login success", Toast.LENGTH_SHORT).show();

    }
}