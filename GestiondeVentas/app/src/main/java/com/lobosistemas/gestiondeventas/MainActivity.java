package com.lobosistemas.gestiondeventas;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.text.TextWatcher;

import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView lstClientes;
    ProgressBar pbMes, pbDia;
    EditText txtBuscar;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtBuscar = findViewById(R.id.txtBuscar);
        lstClientes = findViewById(R.id.lstClientes);
        pbMes = findViewById(R.id.pbMes);
        pbDia = findViewById(R.id.pbDia);

        pbMes.setProgress(50);
        pbMes.setScaleY(12.0f);

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

        String[] empresas = {"lobo", "monday", "git Hub", "Microsoft", "serbosa", "Jurado", "alfandina", "Consorcio", "hsec peru", "imagen ALternativa", "Calquipa"
            ,"Continental", "la Joya mina", "art atlas", "Compañia safranal", "pro espiritu", "pulso medico", "gut hib", "jurado", "adsad"};

        String[] values = new String[20];
        for (int i=0; i<values.length; i++){
            values[i] = "Nombre" + i;
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1
                ,android.R.id.text1, values);
        lstClientes.setAdapter(adapter);
        lstClientes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                int posicion = i;
                String valor = lstClientes.getItemAtPosition(posicion).toString();
                Intent mostrar = new Intent(MainActivity.this, Reporte.class);
                mostrar.putExtra("nombre", valor);
                startActivity(mostrar);
            }
        });

        pbMes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mostrar = new Intent(MainActivity.this, Estadisticas.class);
                startActivity(mostrar);
            }
        });

        txtBuscar.addTextChangedListener(myTextWatcher);
    }

    TextWatcher myTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
}