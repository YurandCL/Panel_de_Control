package com.lobosistemas.x;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Configuracion extends AppCompatActivity {

    EditText txtMetaMensual;
    SharedPreferences preferencias;
    Float metaDiaria, metaMensual;
    int DiasLaborales=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        txtMetaMensual = findViewById(R.id.txtMetaMensual);

        preferencias=getSharedPreferences("Datos",Context.MODE_PRIVATE);
        metaMensual = preferencias.getFloat("MetaMensual",0);
        txtMetaMensual.setText(""+metaMensual);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menu_elegido){
        finish();
        return true;
    }

    public void guardar(View v){
        SharedPreferences.Editor editor=preferencias.edit();
        metaMensual = Float.parseFloat(txtMetaMensual.getText().toString());
        metaDiaria = metaMensual / 31;

        //-----------Calendario-----------//
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getFirstDayOfWeek());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE");

        //--------------Contamos el total de días laborales--------------//
        for(int i=1; i <= calendar.getActualMaximum(Calendar.DAY_OF_MONTH); i++, calendar.add(Calendar.DATE,1)){
            String dia = simpleDateFormat.format(calendar.getTime());
            if(!dia.equals("sábado") && !dia.equals("domingo")){
                DiasLaborales++;
            }
        }

        metaDiaria = metaMensual/DiasLaborales;

        //----------Almacenamos las metas en SharedPreferences----------//
        editor.putFloat("MetaMensual", metaMensual);
        editor.putFloat("MetaDiaria", metaDiaria);
        editor.commit();
        finish();
    }
}