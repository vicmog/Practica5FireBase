package com.example.practica5firebase.view.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CalendarView;

import com.example.practica5firebase.R;
import com.example.practica5firebase.model.Reserva;
import com.example.practica5firebase.viewmodel.ViewModel;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Calendar;


public class AddReservaFragment extends Fragment {

   private CalendarView calendarView;
   private TextInputLayout tilNumeroPersonas,tilHorario;
   private TextInputEditText tietNumeroPersonas;
   private AutoCompleteTextView actHorario;

   private ArrayList<String> opciones;
   private ArrayAdapter<String> arrayAdapter;
   private int day,month,year = 0;
   private ViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_add_reserva, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    private void init() {
        calendarView = getView().findViewById(R.id.calendarView);
        tilNumeroPersonas = getView().findViewById(R.id.tilNumeroPersonasReserva);
        tilHorario = getView().findViewById(R.id.tilHorario);
        tietNumeroPersonas = getView().findViewById(R.id.tietNumeroPersonasReserva);
        actHorario = getView().findViewById(R.id.actHorario);

        NavController navController = Navigation.findNavController(getView());

        viewModel = new ViewModelProvider(getActivity()).get(ViewModel.class);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int y, int m, int dayOfMonth) {
                day = dayOfMonth;
                month = m+1;
                year = y;
            }
        });

        opciones = new ArrayList<>();
        opciones.add("Medio Día");
        opciones.add("Noche");

        arrayAdapter = new ArrayAdapter<>(getActivity(),R.layout.support_simple_spinner_dropdown_item,opciones);

        actHorario.setAdapter(arrayAdapter);
        actHorario.setThreshold(1);


        getView().findViewById(R.id.btBuscar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validaCampos()){
                    int numeroPersonas = Integer.valueOf(tietNumeroPersonas.getText().toString());
                    Reserva reserva = new Reserva(-1,numeroPersonas,year,month,day,actHorario.getText().toString(),"","");
                    viewModel.setPosibleReserva(reserva);
                   navController.navigate(R.id.mesasDisponiblesFragment);



                }
            }
        });
        getView().findViewById(R.id.btAtrasReserva).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.popBackStack(R.id.listaReservasFragment,false);
            }
        });


    }

    private boolean validaCampos() {
        Boolean validado = false;
        if(tietNumeroPersonas.getText().toString().length()!=0){
            tilNumeroPersonas.setErrorEnabled(false);
            if(Integer.valueOf(tietNumeroPersonas.getText().toString())>0){
                tilNumeroPersonas.setErrorEnabled(false);
                if(day != 0 || month != 0 || year != 0 ){
                    tilNumeroPersonas.setErrorEnabled(false);
                    if(actHorario.getText().toString().length()!=0){
                        tilHorario.setErrorEnabled(false);
                        validado = true;
                    }else {
                        tilHorario.setError("Rellena este campo");
                    }

                }else{
                    tilNumeroPersonas.setError("Elige una fecha valida");
                }
            }else{
                tilNumeroPersonas.setError("Numero de personas no valido");
            }

        }else {
            tilNumeroPersonas.setError("Rellena este campo");
        }


        return validado;

    }
}