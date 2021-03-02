package com.example.practica5firebase.view.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.practica5firebase.R;
import com.example.practica5firebase.model.Mesa;
import com.example.practica5firebase.model.Reserva;
import com.example.practica5firebase.view.adapters.MesasDisponiblesAdapter;
import com.example.practica5firebase.viewmodel.ViewModel;

import java.util.ArrayList;
import java.util.List;


public class MesasDisponiblesFragment extends Fragment {

   private ViewModel viewModel;
   private List<Mesa> mesasDisponibles = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_mesas_disponibles, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    private void init() {
        viewModel = new ViewModelProvider(getActivity()).get(ViewModel.class);
        MesasDisponiblesAdapter adapter = new MesasDisponiblesAdapter(mesasDisponibles,getActivity(),getView());
        RecyclerView recyclerView = getView().findViewById(R.id.recyclerMesasDisponibles);
        TextView tvError = getView().findViewById(R.id.tvMensajeError);
        tvError.setVisibility(View.INVISIBLE);


        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));

        viewModel.getMesas().observe(getActivity(), new Observer<List<Mesa>>() {
            @Override
            public void onChanged(List<Mesa> mesas) {
                mesasDisponibles.clear();

                Reserva reserva = viewModel.getPosibleReserva();
                String horario;
                int month;
                int year;
                int day;

                for (int i = 0; i < mesas.size(); i++) {
                    if(mesas.get(i).getNumeroSillas() == reserva.getNumeroPersonas()){
                        if(mesas.get(i).getReservas().size() == 0){

                            mesasDisponibles.add(mesas.get(i));

                        }else{
                            mesasDisponibles.add(mesas.get(i));

                            for (int j = 0; j < mesas.get(i).getReservas().size(); j++) {
                                year = mesas.get(i).getReservas().get(j).getYear();
                                month = mesas.get(i).getReservas().get(j).getMonth();
                                day = mesas.get(i).getReservas().get(j).getDayOfMonth();
                                horario = mesas.get(i).getReservas().get(j).getMediodioaNoche();

                                if(year == reserva.getYear() && month == reserva.getMonth() && day == reserva.getDayOfMonth() && horario.compareToIgnoreCase(reserva.getMediodioaNoche()) == 0 ){
                                    Log.v("ZZZ","F EN EL CHAT");
                                    mesasDisponibles.remove(mesas.get(i));
                                    break;

                                }
                            }
                        }
                    }
                }



                adapter.notifyDataSetChanged();

                if(recyclerView.getAdapter().getItemCount() == 0){
                    recyclerView.setVisibility(View.INVISIBLE);
                    tvError.setVisibility(View.VISIBLE);

                }else {
                    recyclerView.setVisibility(View.VISIBLE);
                }
            }
        });



    }
}