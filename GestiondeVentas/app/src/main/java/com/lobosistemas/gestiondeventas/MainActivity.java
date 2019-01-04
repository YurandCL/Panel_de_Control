package com.lobosistemas.gestiondeventas;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.text.TextWatcher;
import android.widget.Toast;

import com.github.mikephil.charting.utils.ColorTemplate;
import com.lobosistemas.gestiondeventas.io.GestiondeVentasApiAdapter;
import com.lobosistemas.gestiondeventas.io.GestiondeVentasApiService;
import com.lobosistemas.gestiondeventas.model.Empresa;
import com.lobosistemas.gestiondeventas.model.Pago;
import com.lobosistemas.gestiondeventas.ui.adapter.EmpresaAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity{

    ArrayList<Empresa> empresas; //Para consultar las empresas

    ProgressBar pbMes, pbDia;
    EditText txtBuscar;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;

    private EmpresaAdapter mAdapter; //Adaptador para las empresas//

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtBuscar = findViewById(R.id.txtBuscar);

        empresas = new ArrayList<>();

        //-------------------------RecyclerView-------------------------//
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_empresas);
        mRecyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        //---------------------------Adaptador--------------------------//
        mAdapter= new EmpresaAdapter();
        mRecyclerView.setAdapter(mAdapter);

        //------------------------------------RetroFit Empresa--------------------------------------//
        GestiondeVentasApiService ApiService = GestiondeVentasApiAdapter.getApiService();
        Call<ArrayList<Empresa>> call = ApiService.getEmpresas();
        call.enqueue(new Callback<ArrayList<Empresa>>() {
            @Override
            public void onResponse(Call<ArrayList<Empresa>> call, Response<ArrayList<Empresa>> response) {
                if(response.isSuccessful()){
                    empresas = response.body();
                    Log.d("onResponse empresas","Se cargaron "+empresas.size()+" empresas.");
                    mAdapter.setmDataSet(empresas); //Agregamos el adaptador al Recycler View//

                    clickAdaptador(empresas);

                    TextWatcher myTextWatcher = new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            txtBuscar.setTextColor(ColorTemplate.rgb("#00417d"));
                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                            filtro(s.toString());
                        }
                    };

                    txtBuscar.addTextChangedListener(myTextWatcher);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Empresa>> call, Throwable t) {
                Log.d("onResponse empresas","Algo salió mal.");
            }
        });

        //------------------------------------RetroFit Pago--------------------------------------//
        Call<ArrayList<Pago>> call_dos = ApiService.getPagos();
        call_dos.enqueue(new Callback<ArrayList<Pago>>() {
            @Override
            public void onResponse(Call<ArrayList<Pago>> call, Response<ArrayList<Pago>> response) {

            }

            @Override
            public void onFailure(Call<ArrayList<Pago>> call, Throwable t) {

            }
        });

        //-------------------------Barras de Progreso-------------------------//
        pbMes = findViewById(R.id.pbMes);
        pbDia = findViewById(R.id.pbDia);

        pbMes.setProgress(50);
        pbMes.setScaleY(10f);

        pbDia.setProgress(30);
        pbDia.setScaleY(4f);

        //-----------------Propiedades de las Barras-----------------//
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
    }

    //-----------------Búsqueda de Empresas-----------------//
    private void filtro(String text) {
        ArrayList<Empresa> filterList = new ArrayList<>();
        for (Empresa item : empresas){
            if (item.getEmpresa_razonsocial().toLowerCase().contains(text.toLowerCase())){
                filterList.add(item);
            }
        }
        clickAdaptador(filterList);
    }

    //-----------------Mostrar Reporte de la Empresa-----------------//
    private void clickAdaptador(final ArrayList<Empresa> empresas) {

        final EmpresaAdapter mAdapter= new EmpresaAdapter(empresas);

        mAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Reporte.class);
                intent.putExtra("Nombre", empresas.get(mRecyclerView.getChildAdapterPosition(v)).getEmpresa_razonsocial());
                startActivity(intent);
            }
        });

        mRecyclerView.setAdapter(mAdapter);
    }
}