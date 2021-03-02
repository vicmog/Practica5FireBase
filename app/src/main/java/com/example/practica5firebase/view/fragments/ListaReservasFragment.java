package com.example.practica5firebase.view.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.practica5firebase.R;
import com.example.practica5firebase.model.Mesa;
import com.example.practica5firebase.model.Reserva;
import com.example.practica5firebase.view.adapters.ReservaAdapter;
import com.example.practica5firebase.viewmodel.ViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;


public class ListaReservasFragment extends Fragment {

    private BottomNavigationView bottomNavigationView;
    private NavController navController;
    private List<Reserva> reservas = new ArrayList<>();
    private ViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_lista_reservas, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    private void init() {
        viewModel = new ViewModelProvider(getActivity()).get(ViewModel.class);

        navController = Navigation.findNavController(getView());

        bottomNavigationView = getView().findViewById(R.id.bnvReservas);

        bottomNavigationView.setSelectedItemId(R.id.listaReservas);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){

                    case R.id.listaMesas: navController.navigate(R.id.listaMesasFragment);
                        break;
                }
                return true;
            }
        });

        RecyclerView recyclerView = getView().findViewById(R.id.recyclerReservas);
        ReservaAdapter adapter = new ReservaAdapter(reservas,getActivity(),getView());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));

        viewModel.getMesas().observe(getActivity(), new Observer<List<Mesa>>() {
            @Override
            public void onChanged(List<Mesa> mesas) {

                if(mesas.size()!=0 && mesas !=null){
                    reservas.clear();
                    for (int i = 0; i < mesas.size() ; i++) {
                            if (mesas.get(i).getReservas()!=null){
                                reservas.addAll(mesas.get(i).getReservas());
                            }
                    }

                    adapter.notifyDataSetChanged();
                }

            }
        });

        getView().findViewById(R.id.fabAddReserva).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.addReservaFragment);
            }
        });

    }
}