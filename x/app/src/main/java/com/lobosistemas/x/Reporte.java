package com.lobosistemas.x;

import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.internal.NavigationMenu;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import java.math.BigDecimal;
import java.util.ArrayList;
import io.github.yavski.fabspeeddial.FabSpeedDial;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.lobosistemas.x.io.LoboVentasApiAdapter;
import com.lobosistemas.x.io.LoboVentasApiService;
import com.lobosistemas.x.model.ConteoFacturas;
import com.lobosistemas.x.model.Reportes;
import com.lobosistemas.x.ui.adapter.ReportesAdapter;

public class Reporte extends AppCompatActivity {

    ArrayList<Reportes> reportes = new ArrayList<>(); //Para consultar los reportes
    ArrayList<ConteoFacturas> estado = new ArrayList<>(); //Para consultar el estado

    private ReportesAdapter mAdapter; //Adaptador para los reportes
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;

    TextView txtRazonSocial, txtRUC, txtSerie, txtFechaEmision, txtFechaVencimiento, txtTotal, txtPagado, txtRestante, lblPagado, lblRestante, lblTotal, lblConexionReporte;
    ImageView alertaReporte;
    FabSpeedDial fabSpeedDial;
    ProgressBar progressReporte;
    String factura_num, RUC;
    Float restante, total, pagado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporte);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        txtRazonSocial = findViewById(R.id.txtRazonSocial);
        txtRUC = findViewById(R.id.txtRUC);
        txtSerie = findViewById(R.id.txtSerie);
        txtFechaEmision = findViewById(R.id.txtFechaEmision);
        txtFechaVencimiento = findViewById(R.id.txtFechaVencimiento);
        txtTotal = findViewById(R.id.txtTotal);
        txtPagado = findViewById(R.id.txtPagado);
        txtRestante = findViewById(R.id.txtRestante);

        lblPagado = findViewById(R.id.lblPagado);
        lblRestante = findViewById(R.id.lblRestante);
        lblTotal = findViewById(R.id.lblTotal);
        lblConexionReporte = findViewById(R.id.lblConexionReporte);
        alertaReporte = findViewById(R.id.alertReporte);

        progressReporte = findViewById(R.id.progressReporte);
        fabSpeedDial = findViewById(R.id.fabSpeedDial);

        Bundle parametros = this.getIntent().getExtras();
        if (parametros != null) {
            String razon_social = getIntent().getExtras().getString("empresa_razonsocial");
            factura_num = getIntent().getExtras().getString("factura_num");
            txtRazonSocial.setText(razon_social.trim());
        }

        //------------------------------------Conexión con la API------------------------------------//
        final LoboVentasApiService ApiService = LoboVentasApiAdapter.getApiService();

        //----------------------------Botones Flotantes----------------------------//
        fabSpeedDial.setMenuListener(new FabSpeedDial.MenuListener() {
            @Override
            public boolean onPrepareMenu(NavigationMenu navigationMenu) {
                return true;
            }

            @Override
            public boolean onMenuItemSelected(MenuItem menuItem) {

                int id = menuItem.getItemId();
                if(id == R.id.enviarFactura){
                    new DialogoReporte(Reporte.this, ""+RUC, ""+factura_num,"factura");
                } else if(id == R.id.enviarEstado){

                    Log.d("onResponse conteo","Se contará.");

                    //------------------------------------RetroFit Conteo--------------------------------------//

                    Call<ArrayList<ConteoFacturas>> call = ApiService.getConteo(""+RUC);
                    call.enqueue(new Callback<ArrayList<ConteoFacturas>>() {
                        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                        @Override
                        public void onResponse(Call<ArrayList<ConteoFacturas>> call, Response<ArrayList<ConteoFacturas>> response) {
                            if(response.isSuccessful()){
                                estado = response.body();
                                Log.d("onResponse conteo","Resultado: "+ estado.get(0).getEstado()+".");

                                if(estado.get(0).getEstado().equals("si")){
                                    new DialogoReporte(Reporte.this, ""+RUC, ""+factura_num,"estado");
                                } else {
                                    new DialogoEstado(Reporte.this, ""+txtRazonSocial.getText());
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<ArrayList<ConteoFacturas>> call, Throwable t) {
                            Log.d("onResponse conteo","Algo salió mal: "+t.getMessage());
                        }
                    });
                }
                return true;
            }

            @Override
            public void onMenuClosed() {

            }
        });

        //-------------------------RecyclerView-------------------------//
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_reportes);
        mRecyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        //---------------------------Adaptador--------------------------//
        mAdapter= new ReportesAdapter();
        mRecyclerView.setAdapter(mAdapter);

        Log.d("reportes",""+factura_num);

        //------------------------------------RetroFit Reportes--------------------------------------//
        Call<ArrayList<Reportes>> call = ApiService.getReportes(""+factura_num);
        call.enqueue(new Callback<ArrayList<Reportes>>() {
            @Override
            public void onResponse(Call<ArrayList<Reportes>> call, retrofit2.Response<ArrayList<Reportes>> response) {
                if(response.isSuccessful()) {
                    reportes = response.body();
                    mAdapter.setmDataSet(reportes); //Agregamos el adaptador al Recycler View//

                    RUC = ""+reportes.get(0).getRuc();

                    txtRUC.setText("RUC: "+reportes.get(0).getRuc());
                    txtSerie.setText(reportes.get(0).getSerie()+" - "+factura_num);
                    txtFechaEmision.setText(reportes.get(0).getFemision());
                    txtFechaVencimiento.setText(reportes.get(0).getFvencimiento());
                    txtTotal.setText(reportes.get(0).getMoneda()+" "+darFormato(reportes.get(0).getTotal()));

                    if(reportes.get(0).getA_cuenta() == null){
                        ViewGroup.LayoutParams params = lblPagado.getLayoutParams();
                        params.height = 0;
                        lblPagado.setLayoutParams(params);
                        lblRestante.setLayoutParams(params);
                        txtPagado.setLayoutParams(params);
                        txtRestante.setLayoutParams(params);
                        lblTotal.setText("Total:");
                        txtTotal.setTextColor(Color.parseColor("#d02e26"));
                        lblTotal.setTextColor(Color.parseColor("#d02e26"));
                    } else {
                        total = Float.parseFloat(reportes.get(0).getTotal());
                        pagado = Float.parseFloat(reportes.get(0).getA_cuenta());

                        lblTotal.setText("Total:");
                        lblPagado.setText("Pagado:");
                        lblRestante.setText("Restante:");

                        restante = total - pagado;

                        txtPagado.setText(reportes.get(0).getMoneda()+" "+darFormato(reportes.get(0).getA_cuenta()));
                        txtRestante.setText(reportes.get(0).getMoneda()+" "+darFormato(String.valueOf(round (restante,2))));

                        lblRestante.setTextColor(Color.parseColor("#d02e26"));
                        txtRestante.setTextColor(Color.parseColor("#d02e26"));
                    }

                    progressReporte.setVisibility(View.INVISIBLE);
                    Log.d("onResponse reportes","Se cargaron "+reportes.size()+" reportes.");

                }
            }

            @Override
            public void onFailure(Call<ArrayList<Reportes>> call, Throwable t) {
                lblConexionReporte.setVisibility(TextView.VISIBLE);
                alertaReporte.setVisibility(ImageView.VISIBLE);
                progressReporte.setVisibility(View.INVISIBLE);
                Log.d("onResponse reportes","Algo salió mal: "+t.getMessage());
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menu_elegido){
        finish();
        return true;
    }

    public static float round(float d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd.floatValue();
    }

    public String darFormato(String num){
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
}