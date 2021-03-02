package com.example.practica5firebase.model;

import java.util.Objects;

public class Reserva {

    private int numeroMesa;
    private int numeroPersonas;
    private int year;
    private int month;
    private int dayOfMonth;
    private String mediodioaNoche;
    private String nombreReserva;
    private String telefonoContacto;


    public Reserva(int numeroMesa, int numeroPersonas, int year, int month, int dayOfMonth, String mediodioaNoche, String nombreReserva, String telefonoContacto) {
        this.numeroMesa = numeroMesa;
        this.numeroPersonas = numeroPersonas;
        this.year = year;
        this.month = month;
        this.dayOfMonth = dayOfMonth;
        this.mediodioaNoche = mediodioaNoche;
        this.nombreReserva = nombreReserva;
        this.telefonoContacto = telefonoContacto;
    }

    public Reserva() {
    }

    public int getNumeroMesa() {
        return numeroMesa;
    }

    public void setNumeroMesa(int numeroMesa) {
        this.numeroMesa = numeroMesa;
    }

    public int getNumeroPersonas() {
        return numeroPersonas;
    }

    public void setNumeroPersonas(int numeroPersonas) {
        this.numeroPersonas = numeroPersonas;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDayOfMonth() {
        return dayOfMonth;
    }

    public void setDayOfMonth(int dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
    }

    public String getMediodioaNoche() {
        return mediodioaNoche;
    }

    public void setMediodioaNoche(String mediodioaNoche) {
        this.mediodioaNoche = mediodioaNoche;
    }

    public String getNombreReserva() {
        return nombreReserva;
    }

    public void setNombreReserva(String nombreReserva) {
        this.nombreReserva = nombreReserva;
    }

    public String getTelefonoContacto() {
        return telefonoContacto;
    }

    public void setTelefonoContacto(String telefonoContacto) {
        this.telefonoContacto = telefonoContacto;
    }

    @Override
    public String toString() {
        return "Reserva{" +
                "numeroMesa=" + numeroMesa +
                ", numeroPersonas=" + numeroPersonas +
                ", year=" + year +
                ", month=" + month +
                ", dayOfMonth=" + dayOfMonth +
                ", mediodioaNoche='" + mediodioaNoche + '\'' +
                ", nombreReserva='" + nombreReserva + '\'' +
                ", telefonoContacto='" + telefonoContacto + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reserva reserva = (Reserva) o;
        return numeroMesa == reserva.numeroMesa &&
                numeroPersonas == reserva.numeroPersonas &&
                year == reserva.year &&
                month == reserva.month &&
                dayOfMonth == reserva.dayOfMonth &&
                Objects.equals(mediodioaNoche, reserva.mediodioaNoche) &&
                Objects.equals(nombreReserva, reserva.nombreReserva) &&
                Objects.equals(telefonoContacto, reserva.telefonoContacto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numeroMesa, numeroPersonas, year, month, dayOfMonth, mediodioaNoche, nombreReserva, telefonoContacto);
    }
}
