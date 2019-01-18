package com.lobosistemas.x;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lobosistemas.x.adapter.EmailAdapter;
import com.lobosistemas.x.io.LoboVentasApiAdapter;
import com.lobosistemas.x.io.LoboVentasApiService;
import com.lobosistemas.x.model.Emails;
import com.lobosistemas.x.model.Estado;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DialogoReporte {

    LoboVentasApiService ApiService; //Para conectar con la API

    ArrayList<Emails> emails = new ArrayList<>(); //Para consultar los emails
    ArrayList<Estado> estados = new ArrayList<>(); //Para consultar la respuesta del envio

    private EmailAdapter mAdapter; //Adaptador para los emails//
    private RecyclerView mRecyclerView;

    TextView lblEmails, lblOtro, lblCancelar, lblDescripcion;
    EditText txtEmail;
    Button btnEnviar;
    ProgressBar progressEstado;
    LinearLayout contenedor;

    String email, state;

    public DialogoReporte(final Context context, final String RUC, final String factura_num, final String accion){

        final Dialog dialogo = new Dialog(context);
        dialogo.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogo.setCancelable(true);
        dialogo.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogo.setContentView(R.layout.dialog_reporte);

        lblEmails = (TextView) dialogo.findViewById(R.id.lblEmails);
        lblOtro = (TextView) dialogo.findViewById(R.id.lblOtro);
        lblCancelar = (TextView) dialogo.findViewById(R.id.lblCancelar);
        lblDescripcion = (TextView) dialogo.findViewById(R.id.lblDescripcion);
        txtEmail = (EditText) dialogo.findViewById(R.id.txtEmail);
        btnEnviar = (Button) dialogo.findViewById(R.id.btnEnviar);
        progressEstado = (ProgressBar) dialogo.findViewById(R.id.progressEstado);
        contenedor = (LinearLayout) dialogo.findViewById(R.id.contenedor);

        if(accion.equals("estado")){
            lblDescripcion.setText("Enviar Estado de Cuenta");
        }else{
            lblDescripcion.setText("Enviar Factura");
        }

        lblCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogo.dismiss();
            }
        });
        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = txtEmail.getText().toString();

                AlertDialog.Builder builder= new AlertDialog.Builder(context);

                if(accion.equals("estado")){
                    builder.setMessage("¿Está seguro de enviar el estado de cuenta a: "+ email +"?");
                } else {
                    builder.setMessage("¿Está seguro de enviar la factura a: "+ email +"?");
                }

                builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, int which) {

                        progressEstado.setVisibility(View.VISIBLE);
                        dialogo.setCancelable(false);
                        lblCancelar.setClickable(false);
                        txtEmail.setEnabled(false);
                        btnEnviar.setEnabled(false);
                        state="enviando";

                        if(accion.equals("estado")){
                            //------------------------------------RetroFit EstadoFactura--------------------------------------//
                            Log.d("Estado",""+RUC+" "+email);
                            Call<ArrayList<Estado>> callEstadoFactura = ApiService.getEstadoFactura(""+RUC,""+email);
                            callEstadoFactura.enqueue(new Callback<ArrayList<Estado>>() {
                                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                                @Override
                                public void onResponse(Call<ArrayList<Estado>> call, Response<ArrayList<Estado>> response) {
                                    if(response.isSuccessful()){
                                        estados = response.body();
                                        Log.d("onResponse Estado",estados.get(0).getEstado()+".");

                                        if(estados.get(0).getEstado().equals("error")){
                                            new DialogoEstado(context,"error",""+email,""+accion);
                                        } else {
                                            new DialogoEstado(context,"ok",""+email, ""+accion);
                                            dialogo.dismiss();
                                        }

                                        state="";
                                        progressEstado.setVisibility(View.INVISIBLE);
                                        dialogo.setCancelable(true);
                                        lblCancelar.setClickable(true);
                                        txtEmail.setEnabled(true);
                                        btnEnviar.setEnabled(true);
                                    }
                                }

                                @Override
                                public void onFailure(Call<ArrayList<Estado>> call, Throwable t) {
                                    Log.d("onResponse Estado","Algo salió mal: "+t.getMessage());
                                }
                            });
                        } else {
                            //------------------------------------RetroFit Factura--------------------------------------//
                            Log.d("Factura",""+factura_num+" "+email);
                            Call<ArrayList<Estado>> callEstado = ApiService.getEstado(""+factura_num,""+email);
                            callEstado.enqueue(new Callback<ArrayList<Estado>>() {
                                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                                @Override
                                public void onResponse(Call<ArrayList<Estado>> call, Response<ArrayList<Estado>> response) {
                                    if(response.isSuccessful()){
                                        estados = response.body();
                                        Log.d("onResponse Estado",estados.get(0).getEstado()+".");

                                        if(estados.get(0).getEstado().equals("error")){
                                            new DialogoEstado(context,"error",""+email,""+accion);
                                        } else {
                                            new DialogoEstado(context,"ok",""+email,""+accion);
                                            dialogo.dismiss();
                                        }

                                        state="";
                                        progressEstado.setVisibility(View.INVISIBLE);
                                        dialogo.setCancelable(true);
                                        lblCancelar.setClickable(true);
                                        btnEnviar.setEnabled(true);
                                        txtEmail.setEnabled(true);
                                    }
                                }

                                @Override
                                public void onFailure(Call<ArrayList<Estado>> call, Throwable t) {
                                    Log.d("onResponse Estado","Algo salió mal: "+t.getMessage());
                                }
                            });
                        }
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
        });

        //-------------------------RecyclerView-------------------------//
        mRecyclerView = (RecyclerView) dialogo.findViewById(R.id.recycler_view_emails);
        mRecyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        mRecyclerView.setLayoutManager(layoutManager);

        //---------------------------Adaptador--------------------------//
        mAdapter= new EmailAdapter();
        mRecyclerView.setAdapter(mAdapter);

        //------------------------------------Conexión con la API------------------------------------//
        ApiService = (LoboVentasApiService) LoboVentasApiAdapter.getApiService();

        //------------------------------------RetroFit Emails--------------------------------------//
        Call<ArrayList<Emails>> callEmail = ApiService.getEmails(""+RUC);
        callEmail.enqueue(new Callback<ArrayList<Emails>>() {
            @Override
            public void onResponse(Call<ArrayList<Emails>> call, Response<ArrayList<Emails>> response) {
                if(response.isSuccessful()){
                    emails = response.body();
                    clickAdaptador(emails, context, dialogo, factura_num, RUC, accion);

                    if(emails.size() == 0){
                        lblOtro.setText("Email:");
                    } else {
                        ViewGroup.LayoutParams params = lblEmails.getLayoutParams();
                        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                        lblEmails.setLayoutParams(params);

                        lblOtro.setText("Otro:");
                        lblEmails.setText("Lista de Emails");
                        contenedor.setBackgroundColor(Color.parseColor("#faa519"));
                        mAdapter.setmDataSet(emails); //Agregamos el adaptador al Recycler View//
                    }
                    Log.d("onResponse emails","Se cargaron "+ emails.size() +" emails.");
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Emails>> call, Throwable t) {
                Log.d("onResponse emails","Algo salió mal: "+t.getMessage());
            }
        });

        dialogo.show();
    }

    //-----------------Mostrar Reportes de la Empresa-----------------//
    private void clickAdaptador(final ArrayList<Emails> emails, final Context context, final Dialog dialogo, final String factura_num, final String ruc, final String accion) {

        final EmailAdapter mAdapter= new EmailAdapter(emails);

        mAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!state.equals("enviando")){
                    email = emails.get(mRecyclerView.getChildAdapterPosition(v)).getEmail();

                    AlertDialog.Builder builder= new AlertDialog.Builder(context);

                    if(accion.equals("estado")){
                        builder.setMessage("¿Está seguro de enviar el estado de cuenta a: "+ email +"?");
                    } else {
                        builder.setMessage("¿Está seguro de enviar la factura a: "+ email +"?");
                    }

                    builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(final DialogInterface dialog, int which) {
                            progressEstado.setVisibility(View.VISIBLE);
                            dialogo.setCancelable(false);
                            lblCancelar.setClickable(false);
                            txtEmail.setEnabled(false);
                            btnEnviar.setEnabled(false);
                            state="enviando";

                            if(accion.equals("estado")){
                                //------------------------------------RetroFit ReporteEstado--------------------------------------//
                                Call<ArrayList<Estado>> call = ApiService.getEstadoFactura(""+ruc,""+email);
                                call.enqueue(new Callback<ArrayList<Estado>>() {
                                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                                    @Override
                                    public void onResponse(Call<ArrayList<Estado>> call, Response<ArrayList<Estado>> response) {
                                        if(response.isSuccessful()){
                                            estados = response.body();
                                            Log.d("onResponse Estado",estados.get(0).getEstado()+".");

                                            if(estados.get(0).getEstado().equals("error")){
                                                new DialogoEstado(context,"error",""+email, ""+accion);
                                            } else {
                                                new DialogoEstado(context,"ok",""+email, ""+accion);
                                                dialogo.dismiss();
                                            }

                                            progressEstado.setVisibility(View.INVISIBLE);
                                            dialogo.setCancelable(true);
                                            lblCancelar.setClickable(true);
                                            state="";
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<ArrayList<Estado>> call, Throwable t) {
                                        Log.d("onResponse Estado","Algo salió mal: "+t.getMessage());
                                    }
                                });
                            } else {
                                //------------------------------------RetroFit Factura--------------------------------------//
                                Call<ArrayList<Estado>> call = ApiService.getEstado(""+factura_num,""+email);
                                call.enqueue(new Callback<ArrayList<Estado>>() {
                                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                                    @Override
                                    public void onResponse(Call<ArrayList<Estado>> call, Response<ArrayList<Estado>> response) {
                                        if(response.isSuccessful()){
                                            estados = response.body();
                                            Log.d("onResponse Estado",estados.get(0).getEstado()+".");

                                            if(estados.get(0).getEstado().equals("error")){
                                                new DialogoEstado(context,"error",""+email, ""+accion);
                                            } else {
                                                new DialogoEstado(context,"ok",""+email, ""+accion);
                                                dialogo.dismiss();
                                            }

                                            progressEstado.setVisibility(View.INVISIBLE);
                                            dialogo.setCancelable(true);
                                            lblCancelar.setClickable(true);
                                            state="";
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<ArrayList<Estado>> call, Throwable t) {
                                        Log.d("onResponse Estado","Algo salió mal: "+t.getMessage());
                                    }
                                });
                            }

                            dialogo.dismiss();
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
            }
        });

        mRecyclerView.setAdapter(mAdapter);
    }
}
