package com.example.myclientapp.cliente;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myclientapp.MainActivity;
import com.example.myclientapp.R;
import com.example.myclientapp.bbdd.DataBase;

import java.sql.SQLClientInfoException;
import java.util.List;


public class EditaClienteFragment extends Fragment {
    public EditaClienteFragment() {
        // Required empty public constructor
    }


    public static EditaClienteFragment newInstance(String param1, String param2) {
        EditaClienteFragment fragment = new EditaClienteFragment();
        Bundle args = new Bundle();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    EditText verNom, verTel, verDir, verEmail, verOtro;
    Button btnCancel, btnGuardar;
    int id;
    Cliente clAeditar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_edita_cliente, container, false);
        verNom = root.findViewById(R.id.eti_nom1);
        verDir = root.findViewById(R.id.eti_dir1);
        verTel = root.findViewById(R.id.eti_tel1);
        verEmail = root.findViewById(R.id.eti_email1);
        verOtro = root.findViewById(R.id.eti_otro1);
        btnCancel = root.findViewById(R.id.btn_cancelar1);
        btnGuardar = root.findViewById(R.id.btn_guardar1);

        obtieneDatos();

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnMain();
            }
        });
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardaCambios();
            }
        });
        return root;
    }

    protected void obtieneDatos(){
        id = getArguments().getInt("id");
        DataBase db = new DataBase(getContext());
        clAeditar = db.getClientes(id);

        verNom.setText(clAeditar.getNombre());
        verDir.setText(clAeditar.getDireccion());
        verTel.setText(clAeditar.getTelefono());
        verEmail.setText(clAeditar.getEmail());
        verOtro.setText(clAeditar.getOtro());
    }

    protected void returnMain(){
        Intent intent = new Intent(getContext(), MainActivity.class);
        startActivity(intent);
    }
    protected void guardaCambios(){
        String nom, dir, tel, email, otro;
        nom= verNom.getText().toString();
        dir = verDir.getText().toString();
        tel=verTel.getText().toString();
        email=verEmail.getText().toString();
        otro=verOtro.getText().toString();
        Cliente clEditado = new Cliente(id,nom, dir, tel, email, otro);
        DataBase db = new DataBase(getContext());
        db.editaCliente(clEditado);
        Intent intent = new Intent(getContext(), MainActivity.class);
        startActivity(intent);
        // aviso al usuario que el cliente ha sido actualizado
        Toast.makeText(getContext(), "Cliente actualizado", Toast.LENGTH_SHORT).show();
    }

}