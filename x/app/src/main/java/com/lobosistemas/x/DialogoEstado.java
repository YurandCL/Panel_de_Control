package com.lobosistemas.x;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class DialogoEstado {

    TextView lblEstado, txtInformacion;
    Button btnAceptar;

    public DialogoEstado(final Context context, String estado, String email, String accion) {

        final Dialog dialogo = new Dialog(context);
        dialogo.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogo.setCancelable(true);
        dialogo.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogo.setContentView(R.layout.dialog_estado);

        lblEstado = dialogo.findViewById(R.id.lblEstado);
        txtInformacion = dialogo.findViewById(R.id.txtInformacion);

        if(estado.equals("ok")){
            lblEstado.setText("¡LISTO!");
            lblEstado.setBackgroundColor(Color.parseColor("#2382c8"));

            if(accion.equals("estado")){
                txtInformacion.setText("El estado de cuenta fue enviado satisfatoriamente a: "+email);
            } else {
                txtInformacion.setText("La factura fue enviada satisfatoriamente a: "+email);
            }

        } else if(estado.equals("conexion")) {
            lblEstado.setText("¡ERROR DE CONEXIÓN!");
            lblEstado.setBackgroundColor(Color.parseColor("#d02e26"));

            if(accion.equals("estado")){
                txtInformacion.setText("El estado de cuenta no pudo ser enviado. Asegúrese de tener una conexión a internet estable y vuelva a intentarlo.");
            } else {
                txtInformacion.setText("La factura no pudo ser enviada. Asegúrese de tener una conexión a internet estable y vuelva a intentarlo.");
            }

        } else {
            lblEstado.setText("¡ERROR!");
            lblEstado.setBackgroundColor(Color.parseColor("#d02e26"));

            if(accion.equals("estado")){
                txtInformacion.setText("El estado de cuenta no pudo ser enviado a: "+email+". Asegúrese de que el email ingresado es válido.");
            } else {
                txtInformacion.setText("La factura no pudo ser enviada a: "+email+". Asegúrese de que el email ingresado es válido.");
            }

        }

        dialogo.show();
    }

    public DialogoEstado(final Context context) {

        final Dialog dialogo = new Dialog(context);
        dialogo.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogo.setCancelable(false);
        dialogo.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogo.setContentView(R.layout.dialog_estado);

        lblEstado = dialogo.findViewById(R.id.lblEstado);
        txtInformacion = dialogo.findViewById(R.id.txtInformacion);
        btnAceptar = dialogo.findViewById(R.id.btnAceptar);

        lblEstado.setText("Acceso Denegado");
        lblEstado.setBackgroundColor(Color.parseColor("#d02e26"));

        txtInformacion.setText("Su cuenta ha sido deshabilitada. Si se tratase de  un error, contácte con el administrador.");

        ViewGroup.LayoutParams params = btnAceptar.getLayoutParams();
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        btnAceptar.setLayoutParams(params);

        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, LoginActivity.class);
                context.startActivity(intent);
                Main2Activity.activity.finish();
                dialogo.dismiss();
            }
        });

        dialogo.show();

    }

    public DialogoEstado(final Context context, String razonSocial){
        final Dialog dialogo = new Dialog(context);
        dialogo.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogo.setCancelable(true);
        dialogo.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogo.setContentView(R.layout.dialog_estado);

        lblEstado = dialogo.findViewById(R.id.lblEstado);
        txtInformacion = dialogo.findViewById(R.id.txtInformacion);
        btnAceptar = dialogo.findViewById(R.id.btnAceptar);

        lblEstado.setText("TODO AL DÍA");
        lblEstado.setBackgroundColor(Color.parseColor("#2382c8"));

        txtInformacion.setText("Esta empresa por ahora no tiene ninguna factura vencida.");

        dialogo.setCanceledOnTouchOutside(true);

        dialogo.show();
    }
}
