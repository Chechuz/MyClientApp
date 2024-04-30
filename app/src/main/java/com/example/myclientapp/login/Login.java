package com.example.myclientapp.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myclientapp.MainActivity;
import com.example.myclientapp.databinding.ActivityLoginBinding;

public class Login extends AppCompatActivity {

    private ActivityLoginBinding binding;

    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences = getSharedPreferences("loginData", Context.MODE_PRIVATE);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
    }

    public void entrar(View view) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("user", binding.etiUser.getText().toString());
        editor.putString("pass", binding.etiPass.getText().toString());
        editor.apply();

        Intent intent = new Intent(Login.this, MainActivity.class);
        startActivity(intent);

        Toast.makeText(this, "Login success", Toast.LENGTH_SHORT).show();

    }
}