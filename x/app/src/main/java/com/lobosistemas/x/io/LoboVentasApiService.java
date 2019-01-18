package com.lobosistemas.x.io;

import com.lobosistemas.x.model.Emails;
import com.lobosistemas.x.model.Empresa;
import com.lobosistemas.x.model.Estado;
import com.lobosistemas.x.model.PagoDia;
import com.lobosistemas.x.model.PagoMes;
import com.lobosistemas.x.model.Reportes;
import com.lobosistemas.x.model.VentasMes;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface LoboVentasApiService {

    @GET("empresas")
    Call<ArrayList<Empresa>> getEmpresas();

    @GET("pagos/dia")
    Call<ArrayList<PagoDia>> getPagosDia();

    @GET("pagos/mes")
    Call<ArrayList<PagoMes>> getPagosMes();

    @GET("reportes/clientes/{factura_num}")
    Call<ArrayList<Reportes>> getReportes(@Path("factura_num") String factura_num);

    @GET("usuarios/{ruc}")
    Call<ArrayList<Emails>> getEmails(@Path("ruc") String RUC);

    @GET("reportes/ventas")
    Call<ArrayList<VentasMes>> getVentas(
            @Query("fechaInicio") String fechaInicio,
            @Query("fechaFin") String fechaFin);

    @GET("pdf")
    Call<ArrayList<Estado>> getEstado(
            @Query("factura_num") String factura_num,
            @Query("correo") String correo);

    @GET("reporte/factura")
    Call<ArrayList<Estado>> getEstadoFactura(
            @Query("ruc") String ruc,
            @Query("correo") String correo);
}
