package com.lobosistemas.gestiondeventas.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lobosistemas.gestiondeventas.R;
import com.lobosistemas.gestiondeventas.model.Empresa;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolderClientes> implements View.OnClickListener {

    ArrayList<Empresa> listaClientes;
    private View.OnClickListener listener;

    public static class ViewHolderClientes extends RecyclerView.ViewHolder {

        TextView nombre_cliente, dias_retraso;

        public ViewHolderClientes(@NonNull View itemView) {
            super(itemView);

            nombre_cliente = (TextView)itemView.findViewById(R.id.lbl_cliente);
            dias_retraso  = (TextView)itemView.findViewById(R.id.lbl_dias_retraso);
        }
    }

    public Adapter(ArrayList<Empresa> listaClientes) {
        this.listaClientes = listaClientes;
    }

    @NonNull
    @Override
    public ViewHolderClientes onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.lista_clientes,null,false);
        view.setOnClickListener(this);
        return new ViewHolderClientes(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderClientes viewHolderClientes, int i) {
        viewHolderClientes.nombre_cliente.setText(listaClientes.get(i).getEmpresa_razonsocial());
        viewHolderClientes.dias_retraso.setText(listaClientes.get(i).getCalc());
    }

    @Override
    public int getItemCount() {
        return listaClientes.size() ;
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        if (listener != null){
            listener.onClick(v);
        }
    }
}