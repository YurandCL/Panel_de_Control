package com.lobosistemas.x.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lobosistemas.x.R;
import com.lobosistemas.x.model.Empresa;

import java.util.ArrayList;


public class EmpresaAdapter extends RecyclerView.Adapter<com.lobosistemas.x.adapter.EmpresaAdapter.ViewHolder> implements View.OnClickListener{

    private ArrayList<Empresa> mDataSet;
    private View.OnClickListener listener;

    public EmpresaAdapter(ArrayList<Empresa> listado_clientes) {
        mDataSet = listado_clientes;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView empresa, retraso;
        public ViewHolder(View v) {
            super(v);

            empresa = (TextView)itemView.findViewById(R.id.empresa);
            retraso = (TextView)itemView.findViewById(R.id.retraso);
        }
    }

    public EmpresaAdapter() {
        mDataSet = new ArrayList<>();
    }

    public void setmDataSet(ArrayList<Empresa> dataSet){
        mDataSet = dataSet;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.view_empresa,null,false);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int i) {

        holder.empresa.setText(mDataSet.get(i).getEmpresa_razonsocial());
        holder.retraso.setText(""+mDataSet.get(i).getCalc());
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
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