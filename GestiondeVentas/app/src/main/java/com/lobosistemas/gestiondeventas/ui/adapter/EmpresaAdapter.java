package com.lobosistemas.gestiondeventas.ui.adapter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.lobosistemas.gestiondeventas.MainActivity;
import com.lobosistemas.gestiondeventas.R;
import com.lobosistemas.gestiondeventas.Reporte;
import com.lobosistemas.gestiondeventas.model.Empresa;

import java.util.ArrayList;

public class EmpresaAdapter extends RecyclerView.Adapter<EmpresaAdapter.ViewHolder> implements View.OnClickListener{

    private ArrayList<Empresa> mDataSet;
    private View.OnClickListener listener;

    public EmpresaAdapter(ArrayList<Empresa> listado_clientes) {
        mDataSet = listado_clientes;
    }

    // Obtener referencias de los componentes visuales para cada elemento
    // Es decir, referencias de los EditText, TextViews, Buttons
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView empresa, retraso;
        public ViewHolder(View v) {
            super(v);

            empresa = (TextView)itemView.findViewById(R.id.empresa);
            retraso = (TextView)itemView.findViewById(R.id.retraso);
        }
    }

    // Este es nuestro constructor (puede variar según lo que queremos mostrar)
    public EmpresaAdapter() {
        mDataSet = new ArrayList<>();
    }

    public void setmDataSet(ArrayList<Empresa> dataSet){
        mDataSet = dataSet;
        notifyDataSetChanged();
    }

    // El layout manager invoca este método
    // para renderizar cada elemento del RecyclerView
    @Override
    public EmpresaAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.empresa_view,null,false);
        view.setOnClickListener(this);
        return new EmpresaAdapter.ViewHolder(view);
    }
    /*public EmpresaAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                        int viewType) {
        // Creamos una nueva vista
        TextView tv = (TextView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.empresa_view, parent, false);

        // Aquí podemos definir tamaños, márgenes, paddings
        // ...

        return new ViewHolder(tv);
    }*/

    // Este método reemplaza el contenido de cada view,
    // para cada elemento de la lista (nótese el argumento position)
    @Override
    public void onBindViewHolder(ViewHolder holder, int i) {
        // - obtenemos un elemento del dataset según su posición
        // - reemplazamos el contenido de los views según tales datos

        holder.empresa.setText(mDataSet.get(i).getEmpresa_razonsocial());
        holder.retraso.setText(mDataSet.get(i).getCalc());
    }

    // Método que define la cantidad de elementos del RecyclerView
    // Puede ser más complejo (por ejemplo si implementamos filtros o búsquedas)
    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        Log.d("Hiciste Click","Me tocaste");
        if(listener != null){
            listener.onClick(v);
        }
    }
}