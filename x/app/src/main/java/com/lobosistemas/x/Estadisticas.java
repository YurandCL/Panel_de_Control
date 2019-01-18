package com.lobosistemas.x;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.lobosistemas.x.io.LoboVentasApiAdapter;
import com.lobosistemas.x.io.LoboVentasApiService;
import com.lobosistemas.x.model.VentasMes;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Estadisticas extends AppCompatActivity {

    LoboVentasApiService ApiService; //Para conectar con la API
    ArrayList<String> anios = new ArrayList<>();
    Map<Integer, Float> anioYvalor;

    BarChart barChart;
    ArrayList<VentasMes> ventas = new ArrayList<>();

    int anioActual;
    String fechaInicio, fechaFin;
    Spinner meses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estadisticas);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        //---------------Calendario---------------//
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY");
        anioActual = Integer.parseInt(simpleDateFormat.format(date).toUpperCase());

        for(int i=2016; i <= anioActual; i++){
            anios.add(""+i);
        }

        meses = findViewById(R.id.meses);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.meses, R.layout.spinner_item_meses);
        meses.setAdapter(adapter);


        //------------------------------------Conexión con la API------------------------------------//
        ApiService = LoboVentasApiAdapter.getApiService();

        barChart = findViewById(R.id.barchart);
        barChart.setNoDataText("El diagrama está cargando...");
        barChart.getDescription().setEnabled(false);
        barChart.getAxisRight().setDrawLabels(false);
        barChart.setDrawBarShadow(false);
        barChart.setDrawValueAboveBar(true);
        barChart.setDrawGridBackground(true);
        barChart.setScaleEnabled(false);

        meses.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(final AdapterView<?> parent, View view, int position, long id) {

                anioYvalor = new HashMap<Integer, Float>();

                position++;
                for(int i=2016, j=1; i<=anioActual; i++, j++){

                    if(position<9){
                        fechaInicio = i+"-0"+position+"-01";
                        fechaFin = i+"-0"+ (position+1) +"-01";

                    } else if(position<12){
                        fechaInicio = i+"-"+position+"-01";
                        fechaFin = i+"-"+ (position+1) +"-01";

                    } else {
                        fechaInicio = i+"-"+position+"-01";
                        fechaFin = (i+1) +"-01-01";
                    }

                    //------------------------------------RetroFit Ventas--------------------------------------//
                    Call<ArrayList<VentasMes>> callVentas = ApiService.getVentas(""+fechaInicio, ""+fechaFin);
                    Log.d("anioYvalor", "fechaInicio: "+fechaInicio+" fechaFin: "+fechaFin);
                    final int finalJ = j;
                    final int finalPosition = position;
                    final int finalI = i;
                    final int finalI1 = i;
                    callVentas.enqueue(new Callback<ArrayList<VentasMes>>() {
                        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                        @Override
                        public void onResponse(Call<ArrayList<VentasMes>> call, Response<ArrayList<VentasMes>> response) {
                            if(response.isSuccessful()){

                                ventas = response.body();

                                anioYvalor.put(finalI,Float.parseFloat(ventas.get(0).getSuma_mes_especifico()));

                                if(anioYvalor.size() == anios.size()){
                                    crearGrafico(""+parent.getItemAtPosition(finalPosition -1));
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<ArrayList<VentasMes>> call, Throwable t) {
                            Log.d("onResponse Ventas","Algo salió mal: "+t.getMessage());
                        }
                    });
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menu_elegido){
        finish();
        return true;
    }

    public void crearGrafico(String label){

        ArrayList xVals = new ArrayList();
        ArrayList<BarEntry> barEntries  = new ArrayList<>();

        for(int i=2016, j=0; j<anioYvalor.size(); i++, j++){
            barEntries.add(new BarEntry(j+1, anioYvalor.get(i)));
        }

        Log.d("anioYvalor",""+ColorTemplate.rgb("#d20e26"));
        Log.d("anioYvalor",""+ColorTemplate.rgb("#004d7f"));
        Log.d("anioYvalor",""+ColorTemplate.rgb("#73BE46"));

        BarDataSet barDataSet = new BarDataSet(barEntries, ""+label);
        barDataSet.setColors(ColorTemplate.rgb("#73BE46"),ColorTemplate.rgb("#d20e26"),ColorTemplate.rgb("#004d7f"),ColorTemplate.rgb("#faa519")
                ,ColorTemplate.rgb("#d7e141"),ColorTemplate.rgb("#8cd7f5"),ColorTemplate.rgb("#f58223"));

        BarData barData = new BarData(barDataSet);
        barData.setBarWidth(1f);
        barChart.setData(barData);
        barChart.getBarData().setBarWidth(1f);
        barChart.getXAxis().setAxisMaximum(20f);
        barChart.getXAxis().setCenterAxisLabels(false);
        barChart.animateY(900);

        xVals.add("Años");
        xVals.add("2016");
        xVals.add("2017");
        xVals.add("2018");
        xVals.add("2019");

        XAxis xAxis = barChart.getXAxis();
        xAxis.setAxisMinimum(0f);
        xAxis.setGranularity(1f);
        xAxis.setGranularityEnabled(true);
        xAxis.setAxisMaximum(anios.size()+1);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xVals));
    }
}
