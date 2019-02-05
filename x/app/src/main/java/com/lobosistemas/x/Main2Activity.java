package com.lobosistemas.x;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.lobosistemas.x.io.LoboVentasApiAdapter;
import com.lobosistemas.x.io.LoboVentasApiService;
import com.lobosistemas.x.model.Login;
import com.lobosistemas.x.model.MetaMensual;
import com.lobosistemas.x.model.PagoDia;
import com.lobosistemas.x.model.PagoMes;

public class Main2Activity extends AppCompatActivity{

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    public static Activity activity;

    static LoboVentasApiService ApiService; //Para conectar con la API

    static ArrayList<PagoDia> pagoDia = new ArrayList<>(); //Para consultar los pagos diarios
    static ArrayList<PagoMes> pagoMes = new ArrayList<>(); //Para consultar los pagos mensuales
    static ArrayList<MetaMensual> metaMensual = new ArrayList<>(); //Para consultar los pagos mensuales
    static ArrayList<Login> login = new ArrayList<>(); //Para consultar el estado del usuario

    static ProgressBar pbMes, pbDia;
    static TextView lblMetaMensual, lblMetaDiaria;
    static Float PagoMes, PagoDia, MetaMensual, metaDiaria, progresoMes, progresoDia;
    static String nombre;
    SharedPreferences preferencias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        activity = this;

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        lblMetaDiaria = findViewById(R.id.lblMetaDiaria);
        lblMetaMensual = findViewById(R.id.lblMetaMensual);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        //------------------Preferencias------------------//
        preferencias = getSharedPreferences("Datos",Context.MODE_PRIVATE);

        //------------------------------------Conexión con la API------------------------------------//
        ApiService = LoboVentasApiAdapter.getApiService();

        //------------------------------------Verificación de Acceso--------------------------------------//

        String usuario = preferencias.getString("Usuario","");
        String password = preferencias.getString("Password","");

        Call<ArrayList<Login>> callLogin = ApiService.getLogin(""+usuario,""+password);
        callLogin.enqueue(new Callback<ArrayList<Login>>() {
            @Override
            public void onResponse(Call<ArrayList<Login>> call, Response<ArrayList<Login>> response) {
                if(response.isSuccessful()){
                    login = response.body();
                    Log.d("onResponse login","Resultado: "+login.get(0).getAcceso()+".");
                    if(login.get(0).getAcceso().equals("error")){

                        SharedPreferences.Editor editor=preferencias.edit();
                        editor.putString("Usuario", "");
                        editor.putString("Password", "");
                        editor.commit();

                        new DialogoEstado(Main2Activity.this);
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Login>> call, Throwable t) {
                Log.d("onResponse login","Algo salió mal: "+t.getMessage());
            }
        });

        //------------FUNCIONES RETROFIT------------//
        cargarMetaMensual();

        //-------------------------Barras de Progreso-------------------------//
        pbMes = findViewById(R.id.pbMes);
        pbDia = findViewById(R.id.pbDia);

        //------------------------Mostrar Gráficos de Ventas------------------------//
        pbMes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mostrar = new Intent(Main2Activity.this, Estadisticas.class);
                startActivity(mostrar);
            }
        });

        pbDia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mostrar = new Intent(Main2Activity.this, Estadisticas.class);
                startActivity(mostrar);
            }
        });
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder= new AlertDialog.Builder(Main2Activity.this);
        builder.setMessage("¿Desea salir de la aplicación?");
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menuprincipal, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menu_elegido){
        int id = menu_elegido.getItemId();

        if(id == R.id.acercaDe){
            Intent acercade = new Intent(this, AcercaDe.class);
            startActivity(acercade);

        } else if(id == R.id.cerrarSesion){

            SharedPreferences.Editor editor = preferencias.edit();
            editor.putString("Usuario", "");
            editor.commit();

            Intent login = new Intent(this, LoginActivity.class);
            startActivity(login);
            finish();

        } else if(id == R.id.salir){
            AlertDialog.Builder builder= new AlertDialog.Builder(Main2Activity.this);
            builder.setMessage("¿Desea salir de la aplicación?");
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

    //------------------------------------RetroFit PagoDia--------------------------------------//
    public void cargarPagoDia(){
        Call<ArrayList<PagoDia>> call = ApiService.getPagosDia();
        call.enqueue(new Callback<ArrayList<PagoDia>>() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onResponse(Call<ArrayList<PagoDia>> call, Response<ArrayList<PagoDia>> response) {
                if(response.isSuccessful()){
                    pagoDia = response.body();
                    PagoDia= Float.parseFloat(pagoDia.get(0).getCancelado_hoy());
                    Log.d("onResponse pagoDia","Se cargó S/. "+ PagoDia +".");
                    actualizarBarraDia();
                }
            }

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onFailure(Call<ArrayList<PagoDia>> call, Throwable t) {
                Log.d("onResponse pagoDia","Algo salió mal: "+t.getMessage());
            }
        });
    }

    //------------------------------------RetroFit PagoMes--------------------------------------//
    public void cargarPagoMes(){
        Call<ArrayList<PagoMes>> call = ApiService.getPagosMes();
        call.enqueue(new Callback<ArrayList<PagoMes>>() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onResponse(Call<ArrayList<PagoMes>> call, Response<ArrayList<PagoMes>> response) {
                if(response.isSuccessful()){
                    pagoMes = response.body();
                    PagoMes = Float.parseFloat(pagoMes.get(0).getCancelado_mes());
                    Log.d("onResponse pagoMes","Se cargó S/. "+ PagoMes +".");
                    actualizarBarraMes();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<PagoMes>> call, Throwable t) {
                Log.d("onResponse pagoMes","Algo salió mal: "+t.getMessage());
            }
        });
    }

    //------------------------------------RetroFit MetaMensual--------------------------------------//
    public void cargarMetaMensual(){
        Call<ArrayList<MetaMensual>> call = ApiService.getMetaMensual();
        call.enqueue(new Callback<ArrayList<MetaMensual>>() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onResponse(Call<ArrayList<MetaMensual>> call, Response<ArrayList<MetaMensual>> response) {
                if(response.isSuccessful()){
                    metaMensual = response.body();
                    MetaMensual = Float.parseFloat(metaMensual.get(0).getMeta_mensual_monto());
                    Log.d("onResponse metaMensual","Se cargó S/. "+ MetaMensual +".");

                    int DiasLaborales = 0;

                    //-----------Calendario-----------// Cargamos la meta diaria en base a la meta mensual y los días laborables del mes
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

                    metaDiaria = MetaMensual/DiasLaborales;

                    cargarPagoMes();
                    cargarPagoDia();
                }
            }

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onFailure(Call<ArrayList<MetaMensual>> call, Throwable t) {
                Log.d("onResponse pagoMes","Algo salió mal: "+t.getMessage());
            }
        });
    }

    //-----------------Cargar ProgressBar Mes-----------------//
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static void actualizarBarraMes(){

        progresoMes = (PagoMes * 100) / MetaMensual;
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

        lblMetaMensual.setText(darFormato(String.valueOf(round(PagoMes,2)))+" / "+darFormato(String.valueOf(round(MetaMensual,2))));
        Log.d("actualizarBarraMes","La barra mensual se actualizó");
    }

    //-----------------Cargar ProgressBar Día-----------------//
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
        lblMetaDiaria.setText(darFormato(String.valueOf(round (PagoDia,2)))+" / "+darFormato(String.valueOf(round (metaDiaria,2))));
        Log.d("actualizarBarraDia","La barra diaria se actualizó");
    }

    //-------------------Rendondear a X Decimales-------------------//
    public static float round(float d, int x) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(x, BigDecimal.ROUND_HALF_UP);
        return bd.floatValue();
    }

    //-------------------Agregar Ceros (dinero)-------------------//
    public static String darFormato(String num){
        int cont=-1;

        for(int i=0; i<num.length(); i++){
            if(num.charAt(i) == '.'){
                cont++;
            }else if(cont>=0){
                cont++;
            }
        }

        if(cont==-1){num+=".00";}
        else if(cont==1){num+="0";}

        return num;
    }


    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0:
                    ListaVencidas frmVencidas = new ListaVencidas();
                    return frmVencidas;
                case 1:
                    ListaEmitidas frmEmitidas = new ListaEmitidas();
                    return frmEmitidas;
            }

            ListaPagadas frmPagadas = new ListaPagadas();
            return frmPagadas;
        }

        @Override
        public int getCount() {
            return 3;
        }
    }
}
