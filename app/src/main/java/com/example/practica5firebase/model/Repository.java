package com.example.practica5firebase.model;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.List;

public class Repository {

    FirebaseFirestore db;
    FirebaseAuth auth;
    FirebaseUser user;
    Reserva posibleReserva;
    Mesa mesaReservada;
    Mesa mesaEdit;
    Reserva reservaDelete;


    public Repository() {
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
    }

    public MutableLiveData<Integer> loginUser(String email, String pass){
        MutableLiveData<Integer>loginCorrecto = new MutableLiveData<>();

        auth.signInWithEmailAndPassword(email,pass)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        loginCorrecto.setValue(1);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        loginCorrecto.setValue(-1);
                        Log.v("ZZZ",e.toString());
                    }
                })
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        user = auth.getCurrentUser();
                    }
                });
        return loginCorrecto;
    }

    public MutableLiveData<Integer> signupUser(String email,String pass){
        MutableLiveData<Integer>registroCorrecto = new MutableLiveData<>();

        auth.createUserWithEmailAndPassword(email,pass)
        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                registroCorrecto.setValue(1);
            }
        })
        .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                registroCorrecto.setValue(-1);
                Log.v("ZZZ",e.toString());
            }
        })
        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                user = auth.getCurrentUser();

            }
        });

        return registroCorrecto;
    }

    public Reserva getPosibleReserva() {
        return posibleReserva;
    }

    public void setPosibleReserva(Reserva posibleReserva) {
        this.posibleReserva = posibleReserva;
    }

    public Mesa getMesaReservada() {
        return mesaReservada;
    }

    public void setMesaReservada(Mesa mesaReservada) {
        this.mesaReservada = mesaReservada;
    }

    public void insertMesa(Mesa mesa){
        if(auth.getCurrentUser() != null){

            db.collection("user/"+auth.getCurrentUser().getUid()+"/mesas").document(mesa.getNumeroMesa()+"").set(mesa)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.v("ZZZ","Todo bien");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.v("ZZZ",e.getLocalizedMessage());
                        }
                    });
        }

    }

    public MutableLiveData<List<Mesa>> getMesas(){
        MutableLiveData<List<Mesa>> mesas = new MutableLiveData<>();
        if(auth.getCurrentUser() != null){

                db.collection("user/"+auth.getCurrentUser().getUid()+"/mesas")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if(task.isSuccessful()){
                                mesas.setValue(task.getResult().toObjects(Mesa.class));
                            }else{
                                mesas.setValue(null);
                            }

                        }
                    });
        }


        return  mesas;
    }

    public void updateMesa(Mesa mesa){
        if(auth.getCurrentUser() != null){

            db.collection("user/"+auth.getCurrentUser().getUid()+"/mesas").document(mesa.getNumeroMesa()+"")
                    .set(mesa, SetOptions.merge())
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.v("ZZZ","todo bien");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.v("ZZZ","todo mal");
                        }
                    });

        }

    }

    public void deleteMesa (Mesa mesa){
        if(auth.getCurrentUser() != null){

            db.collection("user/"+auth.getCurrentUser().getUid()+"/mesas").document(mesa.getNumeroMesa()+"").delete()

            .addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("ZZZ","DELETE BIEN");
                }
            })

            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.v("ZZZ","DELETE MAL");
                }
            });

        }

    }

    public void deleteReserva (Reserva reserva){
        if(auth.getCurrentUser() != null){

            db.collection("user/"+auth.getCurrentUser().getUid()+"/mesas").document(reserva.getNumeroMesa()+"").get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            Mesa mesa = task.getResult().toObject(Mesa.class);
                            Log.v("ZZZ",mesa.toString());
                            Log.v("ZZZ",reserva.toString());
                            for (int i = 0; i < mesa.getReservas().size(); i++) {
                                if(mesa.getReservas().get(i).equals(reserva)){
                                    Log.v("ZZZ","ENTROO");
                                    mesa.getReservas().remove(mesa.getReservas().get(i));
                                }else {
                                    Log.v("ZZZ","NO ENTROO");
                                }
                            }
                            updateMesa(mesa);

                        }
                    });


        }

    }



    public Mesa getMesaEdit() {
        return mesaEdit;
    }

    public void setMesaEdit(Mesa mesaEdit) {
        this.mesaEdit = mesaEdit;
    }

    public Reserva getReservaDelete() {
        return reservaDelete;
    }

    public void setReservaDelete(Reserva reservaDelete) {
        this.reservaDelete = reservaDelete;
    }
}
