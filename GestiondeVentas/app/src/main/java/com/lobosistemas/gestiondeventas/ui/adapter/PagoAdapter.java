package com.lobosistemas.gestiondeventas.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lobosistemas.gestiondeventas.R;
import com.lobosistemas.gestiondeventas.model.PagoDia;

import java.util.ArrayList;

public class PagoAdapter extends RecyclerView.Adapter<PagoAdapter.ViewHolder> implements View.OnClickListener{

    private ArrayList<PagoDia> pDataSet;
    private View.OnClickListener listener;

    public PagoAdapter(ArrayList<PagoDia> pagoMes) {
        pDataSet = pagoMes;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView empresa;
        public ViewHolder(View v) {
            super(v);

            empresa = (TextView)itemView.findViewById(R.id.empresa);
        }
    }

    public PagoAdapter() {
        pDataSet = new ArrayList<>();
    }

    public void setpDataSet(ArrayList<PagoDia> dataSet){
        pDataSet = dataSet;
        notifyDataSetChanged();
    }

    @Override
    public PagoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.empresa_view,null,false);
        view.setOnClickListener(this);
        return new PagoAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int i) {

        holder.empresa.setText(pDataSet.get(i).getSuma_dia());
    }

    @Override
    public int getItemCount() {
        return pDataSet.size();
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        if(listener != null){
            listener.onClick(v);
        }
    }
}