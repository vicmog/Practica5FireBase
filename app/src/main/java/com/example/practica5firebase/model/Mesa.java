package com.example.practica5firebase.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Mesa {


    private int numeroMesa;
    private int numeroSillas;
    private String material;
    private List<Reserva> reservas;


    public Mesa(int numeroMesa, int numeroSillas, String material, List<Reserva> reservas) {
        this.numeroMesa = numeroMesa;
        this.numeroSillas = numeroSillas;
        this.material = material;
        this.reservas = reservas;
    }

    public Mesa() {
    }

    public int getNumeroSillas() {
        return numeroSillas;
    }

    public void setNumeroSillas(int numeroSillas) {
        this.numeroSillas = numeroSillas;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public List<Reserva> getReservas() {
        return reservas;
    }

    public void setReservas(List<Reserva> reservas) {
        this.reservas = reservas;
    }

    public int getNumeroMesa() {
        return numeroMesa;
    }

    public void setNumeroMesa(int numeroMesa) {
        this.numeroMesa = numeroMesa;
    }

    @Override
    public String toString() {
        return "Mesa{" +
                "numeroMesa=" + numeroMesa +
                ", numeroSillas=" + numeroSillas +
                ", material='" + material + '\'' +
                ", reservas=" + reservas +
                '}';
    }



}
