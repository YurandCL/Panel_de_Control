package com.lobosistemas.x.io;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import com.lobosistemas.x.model.ConteoFacturas;
import com.lobosistemas.x.model.Emails;
import com.lobosistemas.x.model.Estado;
import com.lobosistemas.x.model.Login;
import com.lobosistemas.x.model.MetaMensual;
import com.lobosistemas.x.model.Reportes;
import com.lobosistemas.x.model.Empresa;
import com.lobosistemas.x.model.PagoDia;
import com.lobosistemas.x.model.PagoMes;
import com.lobosistemas.x.model.VentasMes;

public interface LoboVentasApiService {

    @GET("empresas")
    Call<ArrayList<Empresa>> getEmpresas(
            @Query("estado") String estado,
            @Query("orden") String orden);

    @GET("pagos/dia")
    Call<ArrayList<PagoDia>> getPagosDia();

    @GET("pagos/mes")
    Call<ArrayList<PagoMes>> getPagosMes();

    @GET("ver/meta")
    Call<ArrayList<MetaMensual>> getMetaMensual();

    @GET("reportes/clientes/{factura_num}")
    Call<ArrayList<Reportes>> getReportes(@Path("factura_num") String factura_num);

    @GET("usuarios/{ruc}")
    Call<ArrayList<Emails>> getEmails(@Path("ruc") String RUC);

    @GET("reportes/ventas")
    Call<ArrayList<VentasMes>> getVentas(
            @Query("fechaInicio") String fechaInicio,
            @Query("fechaFin") String fechaFin);

    @GET("factura")
    Call<ArrayList<Estado>> getEstado(
            @Query("factura_num") String factura_num,
            @Query("correo") String correo);

    @GET("reporte/factura")
    Call<ArrayList<Estado>> getEstadoFactura(
            @Query("ruc") String ruc,
            @Query("correo") String correo);

    @GET("login")
    Call<ArrayList<Login>> getLogin(
            @Query("usuario") String usuario,
            @Query("password") String password);

    @GET("conteo/facturas/{ruc}")
    Call<ArrayList<ConteoFacturas>> getConteo(@Path("ruc") String RUC);
}
