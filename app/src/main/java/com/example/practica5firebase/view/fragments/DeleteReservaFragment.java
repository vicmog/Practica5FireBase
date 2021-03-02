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

import com.example.practica5firebase.R;
import com.example.practica5firebase.model.Reserva;
import com.example.practica5firebase.viewmodel.ViewModel;


public class DeleteReservaFragment extends Fragment {

   private ViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_delete_reserva, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    private void init() {
        viewModel = new ViewModelProvider(getActivity()).get(ViewModel.class);
        NavController navController = Navigation.findNavController(getView());
        Reserva reserva = viewModel.getReservaDelete();
        viewModel.deleteReserva(reserva);

        navController.popBackStack(R.id.listaMesasFragment,false);
       


    }
}