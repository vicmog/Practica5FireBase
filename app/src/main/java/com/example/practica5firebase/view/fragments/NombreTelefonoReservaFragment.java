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
import com.example.practica5firebase.model.Reserva;
import com.example.practica5firebase.viewmodel.ViewModel;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;


public class NombreTelefonoReservaFragment extends Fragment {

    private TextInputEditText tietNombre ,tietNumeroTelefono;
    private TextInputLayout tilNombre,tilNumeroTelefono;
    private Button btCancel,btConfirmar;
    private ViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_nombre_telefono_reserva, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    private void init() {
        NavController navController = Navigation.findNavController(getView());
        viewModel = new ViewModelProvider(getActivity()).get(ViewModel.class);
        tietNombre = getView().findViewById(R.id.tietNombreReserva);
        tietNumeroTelefono = getView().findViewById(R.id.tietTelefonoReserva);
        tilNombre = getView().findViewById(R.id.tilNombreReserva);
        tilNumeroTelefono = getView().findViewById(R.id.tilTelefonoReserva);
        btCancel = getView().findViewById(R.id.btCancelarReserva);
        btConfirmar = getView().findViewById(R.id.btConfirmarReservas);


        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                navController.popBackStack(R.id.listaReservasFragment,false);

            }
        });

        btConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validaCampos()){
                    Reserva reserva = viewModel.getPosibleReserva();
                    String nombre = tietNombre.getText().toString();
                    String tele = tietNumeroTelefono.getText().toString();
                    reserva.setNombreReserva(nombre);
                    reserva.setTelefonoContacto(tele);
                    Mesa mesaReservada = viewModel.getMesaReservada();

                    List<Reserva> reservas = mesaReservada.getReservas();
                    if(reservas == null){
                        reservas = new ArrayList<>();
                        reservas.add(reserva);
                    }else{
                        reservas.add(reserva);
                    }

                    mesaReservada.setReservas(reservas);

                    viewModel.updateMesa(mesaReservada);

                    navController.popBackStack(R.id.listaReservasFragment,false);


                }
            }
        });


    }

    private boolean validaCampos() {
        Boolean validado = false;

        if(tietNumeroTelefono.getText().toString().length()!=0){
            tilNumeroTelefono.setErrorEnabled(false);
            if(tietNombre.getText().toString().length()!=0){
                tilNombre.setErrorEnabled(false);
                validado = true;

            }else{
                tilNombre.setError("Rellene este campo ");
            }
        }else{
            tilNumeroTelefono.setError("Rellene este campo");
        }


        return validado;

    }
}