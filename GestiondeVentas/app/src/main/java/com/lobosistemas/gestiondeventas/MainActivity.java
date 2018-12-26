package com.lobosistemas.gestiondeventas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ListView lstClientes;
    ProgressBar pbMes, pbDia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lstClientes = findViewById(R.id.lstClientes);
        pbDia = findViewById(R.id.pbDia);
        pbMes = findViewById(R.id.pbMes);

        pbDia.setProgress(70);
        pbMes.setProgress(30);

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