package com.example.practica5firebase.view.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.practica5firebase.R;
import com.example.practica5firebase.model.Reserva;
import com.example.practica5firebase.viewmodel.ViewModel;

import java.util.List;

public class ReservaAdapter extends RecyclerView.Adapter<ReservaAdapter.ViewHolder> implements PopupMenu.OnMenuItemClickListener {

    private List<Reserva>reservas;
    private Activity activity;
    private View view;
    private ViewModel viewModel;
    private NavController navController;
    private Reserva reserva;

    public ReservaAdapter(List<Reserva> reservas, Activity activity, View view) {
        this.reservas = reservas;
        this.activity = activity;
        this.view = view;
        viewModel = new ViewModelProvider((ViewModelStoreOwner) activity).get(ViewModel.class);
        navController = Navigation.findNavController(view);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reserva,parent,false);
        ReservaAdapter.ViewHolder holder = new ReservaAdapter.ViewHolder(vista);
        return holder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(reservas.size()==0 || reservas == null){
            holder.tvFecha.setText("No hay Reservas");
        }

    holder.tvNumeroMesa.setText("Numero de Mesa : "+reservas.get(position).getNumeroMesa());
    holder.tvFecha.setText("Fecha : "+reservas.get(position).getDayOfMonth()+"/"+reservas.get(position).getMonth()+"/"+reservas.get(position).getYear()+"       Horario: "+reservas.get(position).getMediodioaNoche());
        if(reservas.get(position).getNumeroPersonas()!=1){
            holder.tvNumeroPersonas.setText("Para "+reservas.get(position).getNumeroPersonas()+" personas");
        }else{
            holder.tvNumeroPersonas.setText("Para "+reservas.get(position).getNumeroPersonas()+" persona");
        }

    holder.tvNombre.setText("Nombre : "+reservas.get(position).getNombreReserva());
    holder.tvTelefono.setText("Telefono : "+reservas.get(position).getTelefonoContacto());
    holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            reserva = reservas.get(position);
            menuPopup(v);
        }
    });
    }

    private void menuPopup(View anchor) {
        PopupMenu popup = new PopupMenu(activity, anchor);
        popup.setOnMenuItemClickListener(this);
        popup.getMenuInflater().inflate(R.menu.menu_popup2, popup.getMenu());
        popup.show();
    }

    @Override
    public int getItemCount() {
        return reservas.size();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()){

            case R.id.borrarReserva:
                viewModel.setReservaDelete(reserva);
                navController.navigate(R.id.deleteReservaFragment);
                break;

        }
        return true;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvNumeroPersonas,tvFecha,tvNombre,tvTelefono,tvNumeroMesa;
        ConstraintLayout constraintLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            constraintLayout = itemView.findViewById(R.id.clReserva);
            tvFecha = itemView.findViewById(R.id.tvFechaReserva);
            tvNumeroPersonas = itemView.findViewById(R.id.tvNumeroPersonasReserva);
            tvNombre = itemView.findViewById(R.id.tvNombreReserva);
            tvTelefono = itemView.findViewById(R.id.tvTelefonoReserva);
            tvNumeroMesa = itemView.findViewById(R.id.tvNumeroMesaReserva);

        }
    }
}
