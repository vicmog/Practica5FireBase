package com.example.practica5firebase.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.practica5firebase.model.Mesa;
import com.example.practica5firebase.model.Repository;
import com.example.practica5firebase.model.Reserva;

import java.util.List;

public class ViewModel extends AndroidViewModel {

    private Repository repos;

    public ViewModel(@NonNull Application application) {
        super(application);
        repos = new Repository();
    }


    public MutableLiveData<Integer> loginUser(String email, String pass) {
        return repos.loginUser(email, pass);
    }

    public MutableLiveData<Integer> signupUser(String email, String pass) {
        return repos.signupUser(email, pass);
    }

    public void insertMesa(Mesa mesa) {
        repos.insertMesa(mesa);
    }

    public MutableLiveData<List<Mesa>> getMesas() {
        return repos.getMesas();
    }

    public Reserva getPosibleReserva() {
        return repos.getPosibleReserva();
    }

    public void setPosibleReserva(Reserva posibleReserva) {
        repos.setPosibleReserva(posibleReserva);
    }

    public Mesa getMesaReservada() {
        return repos.getMesaReservada();
    }

    public void setMesaReservada(Mesa mesaReservada) {
        repos.setMesaReservada(mesaReservada);
    }

    public void updateMesa(Mesa mesa) {
        repos.updateMesa(mesa);
    }

    public void deleteMesa(Mesa mesa) {
        repos.deleteMesa(mesa);
    }

    public Mesa getMesaEdit() {
        return repos.getMesaEdit();
    }

    public void setMesaEdit(Mesa mesaEdit) {
        repos.setMesaEdit(mesaEdit);
    }

    public void deleteReserva(Reserva reserva) {
        repos.deleteReserva(reserva);
    }

    public Reserva getReservaDelete() {
        return repos.getReservaDelete();
    }

    public void setReservaDelete(Reserva reservaDelete) {
        repos.setReservaDelete(reservaDelete);
    }
}
