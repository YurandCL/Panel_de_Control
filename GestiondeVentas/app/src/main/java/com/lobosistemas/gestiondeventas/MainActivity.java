package com.lobosistemas.gestiondeventas;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.github.mikephil.charting.utils.ColorTemplate;

public class MainActivity extends AppCompatActivity {

    ListView lstClientes;
    ProgressBar pbMes, pbDia;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        String[] values = new String[20];
        for (int i=0; i<values.length; i++){
            values[i] = "Cliente " + i;
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
    }
}