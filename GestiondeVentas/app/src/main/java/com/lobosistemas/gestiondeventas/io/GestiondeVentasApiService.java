package com.lobosistemas.gestiondeventas.io;

import com.lobosistemas.gestiondeventas.model.Empresa;
import com.lobosistemas.gestiondeventas.model.Pago;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GestiondeVentasApiService {

    @GET("empresas")
    Call<ArrayList<Empresa>> getEmpresas();

    @GET("pago/mes")
    Call<ArrayList<Pago>> getPagos();

}