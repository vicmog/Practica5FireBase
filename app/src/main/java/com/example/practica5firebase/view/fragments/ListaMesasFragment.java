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
import android.widget.LinearLayout;

import com.example.practica5firebase.R;
import com.example.practica5firebase.model.Mesa;
import com.example.practica5firebase.model.Reserva;
import com.example.practica5firebase.view.adapters.MesasAdapter;
import com.example.practica5firebase.viewmodel.ViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;


public class ListaMesasFragment extends Fragment {

    private ViewModel miViewModel;
    private List<Mesa> mesas = new ArrayList<>();
    private BottomNavigationView bottomNavigationView;
    private NavController navController;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_lista_mesas, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    private void init() {
        miViewModel = new ViewModelProvider(getActivity()).get(ViewModel.class);

        navController = Navigation.findNavController(getView());

//        Mesa mesa = new Mesa(1,3,"",null);
//        List<Reserva>reservas = new ArrayList<>();
//        reservas.add( new Reserva(mesa.getNumeroMesa(),4,2021,8,12,"Noche","Pepe","555444333"));
//        mesa.setReservas(reservas);
//        miViewModel.insertMesa(mesa);


        RecyclerView recyclerView = getView().findViewById(R.id.recyclerMesas);
        MesasAdapter adapter = new MesasAdapter(mesas,getActivity(),getView());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));

        bottomNavigationView = getView().findViewById(R.id.bNvMesas);

        bottomNavigationView.setSelectedItemId(R.id.listaMesas);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){

                    case R.id.listaReservas: navController.navigate(R.id.listaReservasFragment);
                        break;
                }
                return true;
            }
        });


        miViewModel.getMesas().observe(getActivity(), new Observer<List<Mesa>>() {
            @Override
            public void onChanged(List<Mesa> m) {
               mesas.clear();
               mesas.addAll(m);
               adapter.notifyDataSetChanged();
            }
        });

        getView().findViewById(R.id.fabAddMesa).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.addMesaFragment);
            }
        });

        //miViewModel.insertMesa(new Mesa(1,4,"Madera",null));
    }
}