package com.example.practica5firebase.view.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.practica5firebase.R;
import com.example.practica5firebase.viewmodel.ViewModel;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;


public class LoginFragment extends Fragment {

    private Button btInicio;
    private Button btRegistrar;
    private TextInputEditText etCorreo;
    private TextInputEditText etContraseña;
    private String correo ="";
    private String contraseña ="";
    private TextInputLayout layoutCorreo;
    private TextInputLayout layoutContraseña;
    private ViewModel miViewModel;
    private NavController navController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    private void init() {
        miViewModel = new ViewModelProvider(getActivity()).get(ViewModel.class);

        navController = Navigation.findNavController(getView());

        btInicio = getView().findViewById(R.id.btInicio);
        btRegistrar = getView().findViewById(R.id.btRegistrar);
        etCorreo = getView().findViewById(R.id.etCorreo);
        etContraseña = getView().findViewById(R.id.etContraseña);
        layoutCorreo = getView().findViewById(R.id.textInputLayoutCorreo);
        layoutContraseña = getView().findViewById(R.id.textInputLayouPassword);

        btInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                correo = etCorreo.getText().toString().trim();
                contraseña = etContraseña.getText().toString().trim();
                if(validaDatos(correo,contraseña)){
                    miViewModel.loginUser(correo,contraseña).observe(getActivity(), new Observer<Integer>() {
                        @Override
                        public void onChanged(Integer integer) {
                            if(integer == -1 ){
                                Snackbar.make(getView(),"Login Incorrecto",Snackbar.LENGTH_LONG).show();
                            }else{
                                Snackbar.make(getView(),"Login Correcto",Snackbar.LENGTH_LONG).show();
                                navController.navigate(R.id.listaMesasFragment);
                            }
                        }
                    });
                }



            }
        });
        btRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.signupFragment);
            }
        });
    }

    private boolean validaDatos(String correo, String contraseña) {

        final String regex = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@[a-z0-9-]+(.[a-z0-9-]+)*(.[a-z]{2,4})$";

        boolean validado = false;

        if(correo.length()==0 || correo.length()<10 ){
            layoutCorreo.setError("El correo electronico debe contener al menos 10 caracteres");
        }else if (!correo.matches(regex)) {
            layoutCorreo.setError("El correo electronico debe tener formato de correo electronico. Ej: nombre@email.com");
            layoutContraseña.setErrorEnabled(false);
        } else if(contraseña.length()==0 || contraseña.length()<6){
            layoutCorreo.setErrorEnabled(false);
            layoutContraseña.setError("La contraseña debe contener al menos 6 caracteres");
        }else{
            layoutCorreo.setErrorEnabled(false);
            layoutContraseña.setErrorEnabled(false);
            validado = true;
        }
        return validado;
    }
}