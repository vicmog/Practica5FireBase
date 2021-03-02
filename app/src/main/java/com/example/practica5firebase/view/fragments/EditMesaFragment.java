package com.example.practica5firebase.view.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.practica5firebase.R;
import com.example.practica5firebase.model.Mesa;
import com.example.practica5firebase.viewmodel.ViewModel;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;


public class EditMesaFragment extends Fragment {

    private TextInputLayout tilNumeroPersonas , tilMaterial;
    private TextInputEditText tietNumeroPersonas,tietMaterial;
    private Button btEditar,btAtras;
    private ViewModel viewModel;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_edit_mesa, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    private void init() {
        tilMaterial = getView().findViewById(R.id.tilEditMaterial);
        tilNumeroPersonas = getView().findViewById(R.id.tilEditNumeroPersonas);
        tietMaterial = getView().findViewById(R.id.tietEditMaterial);
        tietNumeroPersonas = getView().findViewById(R.id.tietEditNumeroPersonas);

        NavController navController = Navigation.findNavController(getView());

        viewModel = new ViewModelProvider(getActivity()).get(ViewModel.class);
        Mesa mesaEditar = viewModel.getMesaEdit();

        tietMaterial.setText(mesaEditar.getMaterial());
        tietNumeroPersonas.setText(mesaEditar.getNumeroSillas()+"");


        btEditar = getView().findViewById(R.id.btEditarMesa);
        btAtras = getView().findViewById(R.id.btEditarSalir);

        btEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if(mesaEditar.getReservas().size() == 0){

                if(validaCampos()){
                    mesaEditar.setNumeroSillas(Integer.valueOf(tietNumeroPersonas.getText().toString()));
                    mesaEditar.setMaterial(tietMaterial.getText().toString());
                    viewModel.updateMesa(mesaEditar);
                    navController.popBackStack(R.id.listaMesasFragment,false);
                }

            }else{
                Snackbar.make(v,"Hay reservas de esta mesa,eliminalas para poder editar la mesa",Snackbar.LENGTH_LONG).show();
            }
            }
        });

        btAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            viewModel.setMesaEdit(null);
            navController.popBackStack(R.id.listaMesasFragment,false);
            }
        });
    }

    private boolean validaCampos() {
        Boolean bool = false;
        if(tietNumeroPersonas.getText().toString().length() !=0 && Integer.valueOf(tietNumeroPersonas.getText().toString())>0){
            tilNumeroPersonas.setErrorEnabled(false);
            if(tietMaterial.getText().toString().length()!=0){
                tilMaterial.setErrorEnabled(false);
                bool = true;
            }else{
                tilMaterial.setError("Introduce un valor correcto");
            }
        }else{
            tilNumeroPersonas.setError("Introduce un valor correcto");
        }

        return bool;

    }
}