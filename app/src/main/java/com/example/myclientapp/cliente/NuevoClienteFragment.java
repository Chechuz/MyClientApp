package com.example.myclientapp.cliente;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myclientapp.R;


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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_nuevo_cliente, container, false);
    }
}