package com.lobosistemas.x;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.lobosistemas.x.adapter.EmpresaAdapter;
import com.lobosistemas.x.io.LoboVentasApiAdapter;
import com.lobosistemas.x.io.LoboVentasApiService;
import com.lobosistemas.x.model.Empresa;

import java.math.BigDecimal;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    LoboVentasApiService ApiService; //Para conectar con la API

    ArrayList<Empresa> empresas = new ArrayList<>(); //Para consultar las empresas
    ArrayList<com.lobosistemas.x.model.PagoDia> pagoDia = new ArrayList<>(); //Para consultar los pagos diarios
    ArrayList<com.lobosistemas.x.model.PagoMes> pagoMes = new ArrayList<>(); //Para consultar los pagos mensuales

    private EmpresaAdapter mAdapter; //Adaptador para las empresas//
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout swipeRefreshLayout; //Para refrescar la lista

    ProgressBar pbMes, pbDia;
    EditText txtBuscar;
    TextView lblMetaMensual, lblMetaDiaria;
    SharedPreferences preferencias;
    Float PagoMes, PagoDia, metaMensual, metaDiaria, progresoMes, progresoDia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lblMetaDiaria = findViewById(R.id.lblMetaDiaria);
        lblMetaMensual = findViewById(R.id.lblMetaMensual);
        txtBuscar = findViewById(R.id.txtBuscar);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);

        //-------------------------RecyclerView-------------------------//
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_empresas);
        mRecyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        //---------------------------Adaptador--------------------------//
        mAdapter= new EmpresaAdapter();
        mRecyclerView.setAdapter(mAdapter);

        //------------------------------------Conexión con la API------------------------------------//
        ApiService = LoboVentasApiAdapter.getApiService();

        //------------FUNCIONES RETROFIT------------//
        cargarEmpresas();
        cargarPagoDia();
        cargarPagoMes();

        //------------------Preferencias------------------//
        preferencias=getSharedPreferences("Datos",Context.MODE_PRIVATE);

        //------------------Metas de Ventas------------------//
        metaMensual = preferencias.getFloat("MetaMensual",0);
        metaDiaria  = preferencias.getFloat("MetaDiaria",0);

        //-------------------------Barras de Progreso-------------------------//
        pbMes = findViewById(R.id.pbMes);
        pbDia = findViewById(R.id.pbDia);

        pbMes.setScaleY(10f);
        pbDia.setScaleY(4f);

        //-------------------------Actualizar Datos-------------------------//
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Esto se ejecuta cada vez que se realiza el gesto
                metaMensual = preferencias.getFloat("MetaMensual",0);
                metaDiaria  = preferencias.getFloat("MetaDiaria",0);
                cargarEmpresas();
                cargarPagoDia();
                cargarPagoMes();
                txtBuscar.setText("");
                Toast.makeText(com.lobosistemas.x.MainActivity.this, "La Lista de Empresas y las Barras de Ventas han sido actualizadas.", Toast.LENGTH_SHORT).show();
            }
        });

        //Llamando otra actividad para mostrar graficas de los ingresos obtenidos
        pbMes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mostrar = new Intent(com.lobosistemas.x.MainActivity.this, Estadisticas.class);
                startActivity(mostrar);
            }
        });

        pbDia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mostrar = new Intent(com.lobosistemas.x.MainActivity.this, Estadisticas.class);
                startActivity(mostrar);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menuprincipal){
        getMenuInflater().inflate(R.menu.menuprincipal, menuprincipal);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menu_elegido){
        int id = menu_elegido.getItemId();

        if(id == R.id.configuracion){
            Intent configuracion = new Intent(this, Configuracion.class);
            startActivity(configuracion);

        } else if(id == R.id.acercaDe){
            Intent acercade = new Intent(this, AcercaDe.class);
            startActivity(acercade);

        } else {
            AlertDialog.Builder builder= new AlertDialog.Builder(com.lobosistemas.x.MainActivity.this);
            builder.setMessage("¿Desea salir de la Aplicacion?");
            builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            AlertDialog dialog=builder.create();
            dialog.show();
        }
        return true;
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
        mAdapter.setmDataSet(filterList); //Agregamos el adaptador al Recycler View//
    }

    //-----------------Mostrar Reportes de la Empresa-----------------//
    private void clickAdaptador(final ArrayList<Empresa> empresas) {

        final EmpresaAdapter mAdapter= new EmpresaAdapter(empresas);

        mAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(com.lobosistemas.x.MainActivity.this, Reporte.class);
                intent.putExtra("empresa_razonsocial", empresas.get(mRecyclerView.getChildAdapterPosition(v)).getEmpresa_razonsocial());
                intent.putExtra("factura_num",empresas.get(mRecyclerView.getChildAdapterPosition(v)).getFactura_numero());
                startActivity(intent);
            }
        });

        mRecyclerView.setAdapter(mAdapter);
    }

    //------------------------------------RetroFit Empresa--------------------------------------//
    public void cargarEmpresas(){
        Call<ArrayList<Empresa>> call = ApiService.getEmpresas();
        call.enqueue(new Callback<ArrayList<Empresa>>() {
            @Override
            public void onResponse(Call<ArrayList<Empresa>> call, Response<ArrayList<Empresa>> response) {
                if(response.isSuccessful()){
                    empresas = response.body();
                    Log.d("onResponse empresas","Se cargaron "+empresas.size()+" empresas.");
                    mAdapter.setmDataSet(empresas); //Agregamos el adaptador al Recycler View//

                    clickAdaptador(empresas);
                    swipeRefreshLayout.setRefreshing(false);

                    TextWatcher myTextWatcher = new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            txtBuscar.setTextColor(Color.parseColor("#00417d"));

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
                Log.d("onResponse empresas","Algo salió mal: "+t.getMessage());
            }
        });
    }

    //------------------------------------RetroFit PagoDia--------------------------------------//
    public void cargarPagoDia(){
        Call<ArrayList<com.lobosistemas.x.model.PagoDia>> call = ApiService.getPagosDia();
        call.enqueue(new Callback<ArrayList<com.lobosistemas.x.model.PagoDia>>() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onResponse(Call<ArrayList<com.lobosistemas.x.model.PagoDia>> call, Response<ArrayList<com.lobosistemas.x.model.PagoDia>> response) {
                if(response.isSuccessful()){
                    pagoDia = response.body();
                    PagoDia= Float.parseFloat(pagoDia.get(0).getSuma_dia());
                    Log.d("onResponse pagoDia","Se cargó S/. "+ PagoDia +".");
                    actualizarBarraDia();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<com.lobosistemas.x.model.PagoDia>> call, Throwable t) {
                Log.d("onResponse pagoDia","Algo salió mal: "+t.getMessage());
            }
        });
    }

    //------------------------------------RetroFit PagoMes--------------------------------------//
    public void cargarPagoMes(){
        Call<ArrayList<com.lobosistemas.x.model.PagoMes>> call = ApiService.getPagosMes();
        call.enqueue(new Callback<ArrayList<com.lobosistemas.x.model.PagoMes>>() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onResponse(Call<ArrayList<com.lobosistemas.x.model.PagoMes>> call, Response<ArrayList<com.lobosistemas.x.model.PagoMes>> response) {
                if(response.isSuccessful()){
                    pagoMes = response.body();
                    PagoMes = Float.parseFloat(pagoMes.get(0).getSuma_mes());
                    Log.d("onResponse pagoMes","Se cargó S/. "+ PagoMes +".");
                    actualizarBarraMes();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<com.lobosistemas.x.model.PagoMes>> call, Throwable t) {
                Log.d("onResponse pagoMes","Algo salió mal: "+t.getMessage());
            }
        });
    }


    //-----------------Funciones de las Barras-----------------//

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void actualizarBarraMes(){
        progresoMes = (PagoMes * 100) / metaMensual;
        pbMes.setProgress(Math.round(progresoMes));

        if (pbMes.getProgress() < 40){
            pbMes.getProgressDrawable().setColorFilter(
                    Color.parseColor("#d02e26"), android.graphics.PorterDuff.Mode.SRC_IN);
        }else if (pbMes.getProgress() >= 40 && pbMes.getProgress() < 70){
            pbMes.getProgressDrawable().setColorFilter(
                    Color.parseColor("#d7e141"), android.graphics.PorterDuff.Mode.SRC_IN);
        }else if (pbMes.getProgress() >= 70){
            pbMes.getProgressDrawable().setColorFilter(
                    Color.parseColor("#2382c8"), android.graphics.PorterDuff.Mode.SRC_IN);
        }
        lblMetaMensual.setText(PagoMes+" / "+metaMensual);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void actualizarBarraDia(){
        progresoDia = (PagoDia * 100) / metaDiaria;
        pbDia.setProgress(Math.round(progresoDia));

        if (pbDia.getProgress() < 40){
            pbDia.getProgressDrawable().setColorFilter(
                    Color.parseColor("#d02e26"), android.graphics.PorterDuff.Mode.SRC_IN);
        }else if (pbDia.getProgress() >= 40 && pbDia.getProgress() < 70){
            pbDia.getProgressDrawable().setColorFilter(
                    Color.parseColor("#d7e141"), android.graphics.PorterDuff.Mode.SRC_IN);
        }else if (pbDia.getProgress() >= 70){
            pbDia.getProgressDrawable().setColorFilter(
                    Color.parseColor("#2382c8"), android.graphics.PorterDuff.Mode.SRC_IN);
        }
        lblMetaDiaria.setText(PagoDia+" / "+String.valueOf(round (metaDiaria,2)));
    }

    public static float round(float d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd.floatValue();
    }
}
