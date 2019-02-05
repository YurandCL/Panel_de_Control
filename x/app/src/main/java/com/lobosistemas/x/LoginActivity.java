package com.lobosistemas.x;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.lobosistemas.x.io.LoboVentasApiAdapter;
import com.lobosistemas.x.io.LoboVentasApiService;
import com.lobosistemas.x.model.Login;

public class LoginActivity extends AppCompatActivity {

    LoboVentasApiService ApiService; //Para conectar con la API

    ArrayList<Login> login = new ArrayList<>(); //Para validar las crendeciales

    EditText usuario, password;
    TextView lblError;
    Button btnIngresar;
    ProgressBar progressLogin;
    SharedPreferences preferencias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usuario  = findViewById(R.id.txtUsuario);
        password = findViewById(R.id.txtPassword);
        lblError = findViewById(R.id.lblError);
        btnIngresar = findViewById(R.id.btnIngresar);
        progressLogin = findViewById(R.id.progressLogin);

        preferencias = getSharedPreferences("Datos", Context.MODE_PRIVATE);

        //------------------------------------Conexión con la API------------------------------------//
        ApiService = LoboVentasApiAdapter.getApiService();
    }

    //----------------------Validar las credenciales----------------------//
    public void ingresar(View v){

        usuario.setEnabled(false);
        password.setEnabled(false);
        btnIngresar.setClickable(false);
        progressLogin.setVisibility(ProgressBar.VISIBLE);

        //------------------------------------RetroFit Acceso--------------------------------------//
        Call<ArrayList<Login>> callLogin = ApiService.getLogin(""+usuario.getText(),""+password.getText());
        callLogin.enqueue(new Callback<ArrayList<Login>>() {
            @Override
            public void onResponse(Call<ArrayList<Login>> call, Response<ArrayList<Login>> response) {
                if(response.isSuccessful()){
                    login = response.body();
                    Log.d("onResponse login","Resultado: "+login.get(0).getAcceso()+".");
                    if(!login.get(0).getAcceso().equals("error")){

                        SharedPreferences.Editor editor=preferencias.edit();
                        editor.putString("Usuario", ""+usuario.getText());
                        editor.putString("Password", ""+password.getText());
                        editor.commit();

                        Intent intent = new Intent(LoginActivity.this, Main2Activity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        habilitar("El usuario o la contraseña ingresada es incorrecta. ");
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Login>> call, Throwable t) {
                habilitar("En este momento no se puede acceder. Por favor, inténtelo más tarde.");
                Log.d("onResponse login","Algo salió mal: "+t.getMessage());
            }
        });
    }

    public void habilitar(String m){
        password.setText("");

        usuario.setEnabled(true);
        password.setEnabled(true);
        btnIngresar.setClickable(true);
        progressLogin.setVisibility(ProgressBar.INVISIBLE);

        lblError.setText(m);
        ViewGroup.LayoutParams params = lblError.getLayoutParams();
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        lblError.setLayoutParams(params);
    }
}
