package com.example.practica5firebase.view.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.practica5firebase.R;
import com.example.practica5firebase.model.Mesa;
import com.example.practica5firebase.model.Reserva;
import com.example.practica5firebase.viewmodel.ViewModel;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;


public class AddMesaFragment extends Fragment {

    private TextInputLayout tilNumero,tilNumeroPersonas,tilMaterial;
    private TextInputEditText tietNumero,tietNumeroPersonas,tietMaterial;
    private ViewModel miViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_add_mesa, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    private void init() {
        miViewModel = new ViewModelProvider(getActivity()).get(ViewModel.class);
        NavController navController = Navigation.findNavController(getView());
        tilNumero = getView().findViewById(R.id.tilNumeroMesa);
        tilMaterial = getView().findViewById(R.id.tilMaterial);
        tilNumeroPersonas = getView().findViewById(R.id.tilNumeroSillas);

        tietMaterial = getView().findViewById(R.id.tietMaterial);
        tietNumero = getView().findViewById(R.id.tietNumeroMesa);
        tietNumeroPersonas = getView().findViewById(R.id.tietNumeroSillas);


        getView().findViewById(R.id.btAddMesa).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validacampos()){
                    int numero = Integer.valueOf(tietNumero.getText().toString());
                    miViewModel.getMesas().observe(getActivity(), new Observer<List<Mesa>>() {
                        @Override
                        public void onChanged(List<Mesa> mesas) {
                            Boolean existe = false;
                            for (int i = 0; i < mesas.size(); i++) {
                                if(mesas.get(i).getNumeroMesa() == numero){
                                    existe = true;
                                    break;

                                }

                            }
                            if(existe){
                                Snackbar.make(getView(),"Este Numero de mesa ya existe",Snackbar.LENGTH_LONG).show();
                            }else{
                                int numeroPersonas = Integer.valueOf(tietNumeroPersonas.getText().toString());
                                String material = tietMaterial.getText().toString();
                                List<Reserva>reservas = new ArrayList<>();
                                Mesa mesa = new Mesa(numero,numeroPersonas,material,reservas);
                                miViewModel.insertMesa(mesa);
                                navController.popBackStack(R.id.listaMesasFragment,false);
                            }
                        }
                    });

                }
            }
        });

        getView().findViewById(R.id.btAtrasAddMesa).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.popBackStack(R.id.listaMesasFragment,false);
            }
        });




    }

    private boolean validacampos() {
        Boolean validado = false;
        if(Integer.valueOf(tietNumero.getText().toString())>0){
            tilNumero.setErrorEnabled(false);
            if(Integer.valueOf(tietNumeroPersonas.getText().toString())>0){
                tilNumeroPersonas.setErrorEnabled(false);
                if(tietMaterial.getText().toString().length()!=0){
                    tilMaterial.setErrorEnabled(false);
                    validado = true;
                }else{
                    tilMaterial.setError("Reelene este campo ,por favor");
                }
            }else{
               tilNumeroPersonas.setError("El minimo de persona es 1");
            }
        }else{
            tilNumero.setError("El numero de mesa tiene que ser positivo");
        }


        return validado;
    }
}