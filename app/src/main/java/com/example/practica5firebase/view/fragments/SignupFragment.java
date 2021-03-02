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


public class SignupFragment extends Fragment {

    private TextInputLayout layoutCorreoRegistro;
    private TextInputLayout layoutContraseñaRegistro;
    private TextInputLayout layoutContraseñaConfirmacionRegistro;
    private TextInputLayout layoutNombreRegistro;
    private TextInputEditText etCorreoRegistro;
    private TextInputEditText etContraseñaRegistro;
    private TextInputEditText etNombreRegistro;
    private TextInputEditText etContraseñaConfirmacionRegistro;
    private String correo,contraseña,confirmacion;
    private ViewModel miViewModel;
    private NavController navController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_signup, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    private void init() {

        miViewModel = new ViewModelProvider(getActivity()).get(ViewModel.class);

        navController = Navigation.findNavController(getView());

        layoutContraseñaRegistro = getView().findViewById(R.id.textInputLayoutContraseñaRegistro);
        layoutCorreoRegistro = getView().findViewById(R.id.textInputLayoutCorreoRegistro);
        etContraseñaRegistro = getView().findViewById(R.id.etContraseñaRegistro);
        etCorreoRegistro = getView().findViewById(R.id.etCorreoRegistro);
        etContraseñaConfirmacionRegistro = getView().findViewById(R.id.etValidaContraseña);
        layoutContraseñaConfirmacionRegistro = getView().findViewById(R.id.textInputLayoutValidaContraseña);

        Button register = getView().findViewById(R.id.btRegistoMainRegsitro);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                correo = etCorreoRegistro.getText().toString();
                contraseña = etContraseñaRegistro.getText().toString();
                confirmacion = etContraseñaConfirmacionRegistro.getText().toString();


                if(validaDatos(correo,contraseña,confirmacion)){
                    miViewModel.signupUser(correo,contraseña).observe(getActivity(), new Observer<Integer>() {
                        @Override
                        public void onChanged(Integer integer) {
                            if(integer == -1 ){
                                Snackbar.make(getView(),"Signup Error",Snackbar.LENGTH_LONG).show();
                            }else{
                                Snackbar.make(getView(),"Signup Succesfully",Snackbar.LENGTH_LONG).show();
                                navController.navigate(R.id.listaMesasFragment);
                            }
                        }
                    });
                }


            }
        });

        Button btLogin = getView().findViewById(R.id.btRegistroTengoCuenta);
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.loginFragment);
            }
        });
    }

    private boolean validaDatos(String correo, String contraseña,String confirmacion) {

        final String regex = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@[a-z0-9-]+(.[a-z0-9-]+)*(.[a-z]{2,4})$";

        boolean validado = false;


        if(correo.length()==0 || correo.length()<10 ){
            layoutNombreRegistro.setErrorEnabled(false);
            layoutCorreoRegistro.setError("El correo electronico debe contener al menos 10 caracteres");


        }else if (!correo.matches(regex)) {
            layoutCorreoRegistro.setError("El correo electronico debe tener formato de correo electronico. Ej: nombre@email.com");
            layoutContraseñaRegistro.setErrorEnabled(false);

        } else if(contraseña.length()==0 || contraseña.length()<6){
            layoutCorreoRegistro.setErrorEnabled(false);
            layoutContraseñaRegistro.setError("La contraseña debe contener al menos 6 caracteres");
        }else if(!confirmacion.equals(contraseña)){
            layoutContraseñaRegistro.setErrorEnabled(false);
            layoutContraseñaConfirmacionRegistro.setError("Las contraseñas no coinciden");
        } else{
            layoutCorreoRegistro.setErrorEnabled(false);
            layoutContraseñaConfirmacionRegistro.setErrorEnabled(false);
            layoutContraseñaRegistro.setErrorEnabled(false);

            validado = true;
        }
        return  validado;
    }


}