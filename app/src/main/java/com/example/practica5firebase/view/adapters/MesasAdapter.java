package com.example.practica5firebase.view.adapters;

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
import com.example.practica5firebase.model.Mesa;
import com.example.practica5firebase.viewmodel.ViewModel;

import java.util.List;

public class MesasAdapter extends RecyclerView.Adapter<MesasAdapter.ViewHolder> implements PopupMenu.OnMenuItemClickListener {

    private List<Mesa> mesas;
    private Activity activity;
    private View view;
    private ViewModel viewModel;
    private NavController navController;
    private Mesa mesa;

    public MesasAdapter(List<Mesa> mesas, Activity activity, View view) {
        this.mesas = mesas;
        this.activity = activity;
        this.view = view;
        viewModel = new ViewModelProvider((ViewModelStoreOwner) activity).get(ViewModel.class);
        navController = Navigation.findNavController(view);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mesa,parent,false);
        ViewHolder holder = new ViewHolder(vista);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvNumeroMesa.setText(mesas.get(position).getNumeroMesa()+"");
        holder.tvNumeroSillas.setText(mesas.get(position).getNumeroSillas()+"");
        holder.tvMaterial.setText(mesas.get(position).getMaterial());
        holder.clMesa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mesa = mesas.get(position);
                menuPopup(v);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mesas.size();
    }

    private void menuPopup(View anchor) {
        PopupMenu popup = new PopupMenu(activity, anchor);
        popup.setOnMenuItemClickListener(this);
        popup.getMenuInflater().inflate(R.menu.menu_popup, popup.getMenu());
        popup.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()){

            case R.id.editar:
                viewModel.setMesaEdit(mesa);
                navController.navigate(R.id.editMesaFragment);
                break;
            case R.id.borrar:
                viewModel.deleteMesa(mesa);
                navController.popBackStack(R.id.listaReservasFragment,false);
                navController.popBackStack(R.id.listaMesasFragment,false);
                break;

        }
        return true;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvNumeroMesa,tvNumeroSillas,tvMaterial;
        ConstraintLayout clMesa;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            clMesa = itemView.findViewById(R.id.clMesa);
            tvNumeroMesa = itemView.findViewById(R.id.tvNumeroMesa);
            tvNumeroSillas = itemView.findViewById(R.id.tvNumeroSillas);
            tvMaterial = itemView.findViewById(R.id.tvMaterialMesa);

        }
    }

}
