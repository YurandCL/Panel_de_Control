package com.lobosistemas.gestiondeventas;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.text.TextWatcher;
import android.widget.Toast;

import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<ClientesVo> empresas;
    ProgressBar pbMes, pbDia;
    EditText txtBuscar;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtBuscar = findViewById(R.id.txtBuscar);

        empresas = new ArrayList<>();

        mRecyclerView = findViewById(R.id.rlClientes);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        llenarClientes();
        mAdapter = new Adaptador(empresas);
        mRecyclerView.setAdapter(mAdapter);

        pbMes = findViewById(R.id.pbMes);
        pbDia = findViewById(R.id.pbDia);

        pbMes.setProgress(50);
        pbMes.setScaleY(10f);

        pbDia.setProgress(30);
        pbDia.setScaleY(4f);


        if (pbMes.getProgress() < 40){
            pbMes.setProgressTintList(ColorStateList.valueOf(ColorTemplate.rgb("#d02e26")));
        }else if (pbMes.getProgress() >= 40 && pbMes.getProgress() < 70){
            pbMes.setProgressTintList(ColorStateList.valueOf(ColorTemplate.rgb("#faa519")));
        }else if (pbMes.getProgress() >= 70){
            pbMes.setProgressTintList(ColorStateList.valueOf(ColorTemplate.rgb("#73BE46")));
        }

        if (pbDia.getProgress() < 40){
            pbDia.setProgressTintList(ColorStateList.valueOf(ColorTemplate.rgb("#d02e26")));
        }else if (pbDia.getProgress() >= 40 && pbDia.getProgress() < 70){
            pbDia.setProgressTintList(ColorStateList.valueOf(ColorTemplate.rgb("#faa519")));
        }else if (pbDia.getProgress() >= 70){
            pbDia.setProgressTintList(ColorStateList.valueOf(ColorTemplate.rgb("#73BE46")));
        }



        //Llamando otra actividad para mostrar graficas de los ingresos obtenidos
        pbMes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mostrar = new Intent(MainActivity.this, Estadisticas.class);
                startActivity(mostrar);
            }
        });
        txtBuscar.addTextChangedListener(myTextWatcher);
    }

    private ArrayList<ClientesVo> llenarClientes() {
        String[] empresas_clientes = {"lobo", "monday", "git Hub", "Microsoft", "serbosa", "Jurado", "alfandina", "Consorcio", "hsec peru", "imagen ALternativa", "Calquipa"
                ,"Continental", "la Joya mina", "art atlas", "Compañia safranal", "pro espiritu", "pulso medico", "gut hib", "jurado", "adsad"};
        String[] dias_retraso = {"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20"};
        for (int i = 0; i<empresas_clientes.length; i++) {
            empresas.add(new ClientesVo(empresas_clientes[i], dias_retraso[i]));
        }
        return null;
    }

    TextWatcher myTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            Toast.makeText(MainActivity.this,"estas buscando",Toast.LENGTH_SHORT).show();
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
}