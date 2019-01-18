package com.lobosistemas.x;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;
import android.widget.TextView;

public class DialogoEstado {

    TextView lblEstado, txtInformacion;

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
            lblEstado.setBackgroundColor(Color.parseColor("#faa519"));

            if(accion.equals("estado")){
                txtInformacion.setText("El estado de cuenta fue enviado satisfatoriamente a: "+email);
            } else {
                txtInformacion.setText("La factura fue enviada satisfatoriamente a: "+email);
            }

        } else {
            lblEstado.setText("¡ERROR!");
            lblEstado.setBackgroundColor(Color.parseColor("#d02e26"));

            if(accion.equals("estado")){
                txtInformacion.setText("El estado de cuenta no pudo ser enviado a: "+email+". Asegúrate de que el email ingresado sea válido.");
            } else {
                txtInformacion.setText("La factura no pudo ser enviada a: "+email+". Asegúrate de que el email ingresado sea válido.");
            }

        }

        dialogo.show();
    }
}
