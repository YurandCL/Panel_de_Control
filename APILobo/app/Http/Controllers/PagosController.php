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
    	$añoActual = date("Y");
    	$mesActual = date("m");
    	$fechaFiltro = $añoActual."-".$mesActual."-01";
    	//$fechaFiltroEstatica = "2018-12-01";
    
        $pagos_monto = DB::table('tbl_pagos')->where('pagos_fechapago', '>', $fechaFiltro)->sum('pagos_monto');        
               
        $pagos_monto = number_format($pagos_monto, 2, ',', ' ');
        return $pagos_monto;
    }

    public function pagosDiarios()
    {
    	$añoActual = date("Y");
    	$mesActual = date("m");
    	$diaActual = date("d");
    	$fechaFiltro = $añoActual."-".$mesActual."-".$diaActual;
    
        $pagos_monto = DB::table('tbl_pagos')->where('pagos_fechapago', '=', $fechaFiltro)->sum('pagos_monto');        
               
        $pagos_monto = number_format($pagos_monto, 2, ',', ' ');
        return $pagos_monto;
    }
}


