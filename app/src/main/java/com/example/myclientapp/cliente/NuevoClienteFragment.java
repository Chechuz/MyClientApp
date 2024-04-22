package com.example.myclientapp.cliente;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Intent;


import com.example.myclientapp.MainActivity;
import com.example.myclientapp.R;
import com.example.myclientapp.bbdd.DataBase;
import com.example.myclientapp.notas.NotasActivity;


public class NuevoClienteFragment extends Fragment {

    public NuevoClienteFragment() {
        // Required empty public constructor
    }

    public static NuevoClienteFragment newInstance(String param1, String param2) {
        NuevoClienteFragment fragment = new NuevoClienteFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }
    private Button btnguardar, btncancelar;
    private EditText nom, dir, tel, mail, otro;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_nuevo_cliente, container, false);
        btnguardar= root.findViewById(R.id.btn_guardaNota);
        btncancelar = root.findViewById(R.id.btn_cancelar);
        nom = root.findViewById(R.id.eti_nom);
        dir = root.findViewById(R.id.eti_dir);
        tel = root.findViewById(R.id.eti_tel);
        mail = root.findViewById(R.id.eti_email);
        otro = root.findViewById(R.id.eti_otro);

        btnguardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveCl();
            }
        });
        btncancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnCancel();
            }
        });
        return root;
    }

    protected void saveCl(){
        Cliente miCliente= new Cliente(nom.getText().toString(), dir.getText().toString(), tel.getText().toString(),mail.getText().toString(),otro.getText().toString());
        DataBase db = new DataBase(getContext());
        db.anadeCliente(miCliente);
        Intent intent = new Intent(getContext(), MainActivity.class);
        startActivity(intent);
        // aviso al usuario que el cliente ha sido guardado
        Toast.makeText(getContext(), "Cliente guardado", Toast.LENGTH_SHORT).show();
    }
    protected void btnCancel(){
        Intent intent = new Intent(getContext(), ClientActivity.class);
        startActivity(intent);
    }
}