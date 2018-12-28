package com.lobosistemas.gestiondeventas;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class Adaptador extends RecyclerView.Adapter<Adaptador.ViewHolderClientes> {

    ArrayList<ClientesVo> listaClientes;

    public static class ViewHolderClientes extends RecyclerView.ViewHolder {

        TextView nombre_cliente, dias_retraso;

        public ViewHolderClientes(@NonNull View itemView) {
            super(itemView);

            nombre_cliente = (TextView)itemView.findViewById(R.id.lbl_cliente);
            dias_retraso  = (TextView)itemView.findViewById(R.id.lbl_dias_retraso);
        }
    }

    public Adaptador(ArrayList<ClientesVo> listaClientes) {
        this.listaClientes = listaClientes;
    }

    @NonNull
    @Override
    public ViewHolderClientes onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.lista_clientes,null,false);
        return new ViewHolderClientes(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderClientes viewHolderClientes, int i) {
        viewHolderClientes.nombre_cliente.setText(listaClientes.get(i).getNombre());
        viewHolderClientes.dias_retraso.setText(listaClientes.get(i).getDias_retraso());
    }

    @Override
    public int getItemCount() {
        return listaClientes.size() ;
    }
}
