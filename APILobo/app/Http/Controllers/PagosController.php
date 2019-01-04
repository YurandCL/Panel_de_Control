<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use DB;
use App\Http\Requests;
use App\Http\Controllers\Controller;

class PagosController extends Controller
{

    public function pagosMensuales()
    {
        $anio_mes_Actual = date("Y-m");
        $fechaFiltro = $anio_mes_Actual.'-01';
        $pagos_monto_mes = DB::select('SELECT sum(pagos_monto) as suma_mes FROM tbl_pagos WHERE pagos_fechapago > ?', [$fechaFiltro]);
        return $pagos_monto_mes;
    }

    public function pagosDiarios()
    {   
        $pagos_monto_dia = DB::select('SELECT sum(pagos_monto) as suma_dia FROM tbl_pagos WHERE pagos_fechapago = (CAST(now() as date))');
        return $pagos_monto_dia;
    }
}


