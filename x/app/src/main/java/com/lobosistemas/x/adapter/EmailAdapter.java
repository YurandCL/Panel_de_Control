package com.lobosistemas.x.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lobosistemas.x.R;
import com.lobosistemas.x.model.Emails;

import java.util.ArrayList;


public class EmailAdapter extends RecyclerView.Adapter<com.lobosistemas.x.adapter.EmailAdapter.ViewHolder> implements View.OnClickListener{

    private ArrayList<Emails> mDataSet;
    private View.OnClickListener listener;

    public EmailAdapter(ArrayList<Emails> listado_emails) {
        mDataSet = listado_emails;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView email;
        public ViewHolder(View v) {
            super(v);

            email = (TextView)itemView.findViewById(R.id.email);
        }
    }

    public EmailAdapter() {
        mDataSet = new ArrayList<>();
    }

    public void setmDataSet(ArrayList<Emails> dataSet){
        mDataSet = dataSet;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.view_email,null,false);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int i) {
        holder.email.setText(mDataSet.get(i).getEmail());
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