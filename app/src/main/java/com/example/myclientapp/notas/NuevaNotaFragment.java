package com.example.myclientapp.notas;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myclientapp.R;
import com.example.myclientapp.bbdd.DataBase;
import com.example.myclientapp.cliente.Cliente;

import java.util.Calendar;


public class NuevaNotaFragment extends Fragment {


    public NuevaNotaFragment() {
        // Required empty public constructor
    }

    public static NuevaNotaFragment newInstance(String param1, String param2) {
        NuevaNotaFragment fragment = new NuevaNotaFragment();
        Bundle args = new Bundle();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }
    EditText titulo, detalle;
    Button guardar;
    String fecha, hora;
    Calendar calendario;
    Notas notaModelo;
    int id;
    Cliente cliente;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_nueva_nota, container, false);
        titulo = root.findViewById(R.id.anadeNota);
        detalle = root.findViewById(R.id.cuerpoNota);
        guardar = root.findViewById(R.id.btn_guardaNota);

        //Para que muestre fecha y hora de la nota
        calendario = Calendar.getInstance();
        fecha=calendario.get(Calendar.YEAR)+"/"+ calendario.get(Calendar.MONTH)+"/"+ calendario.get(Calendar.DAY_OF_MONTH);
        hora = pad(calendario.get(Calendar.HOUR))+":"+ pad(calendario.get(Calendar.MINUTE));
        Log.d("calendar", "Fecha y Hora "+fecha+ "/"+hora);

        //Obtengo datos del Cliente
        id = getArguments().getInt("idCl");
        DataBase db = new DataBase(getContext());
        cliente = db.getClientes(id);

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return root;
    }

    protected void almacenaNota(){
        notaModelo= new Notas(titulo.getText().toString(), detalle.getText().toString(),fecha, hora);
        DataBase db = new DataBase(getContext());
        db.anadeNota(notaModelo, cliente);
        // Pongo el intent para volver al main
        Intent intent = new Intent(getContext(), NotasActivity.class);
        startActivity(intent);
        // aviso al usuario que la nota ha sido guardada
        Toast.makeText(getContext(), "Nota guardada", Toast.LENGTH_SHORT).show();
    }

    //metodo para que la hora se visualice correctamente (con dos caracteres siempre)
    public String pad(int i){
        if(i<0)
            return "0"+i;
        return String.valueOf(i);
    }
}