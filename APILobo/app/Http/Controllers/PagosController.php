<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use DB;
use App\Http\Requests;
use App\Http\Controllers\Controller;

class PagosController extends Controller
{
    //suma total en soles de todo lo que se nos ha pagado durante el mes actual
    public function pagosMensuales(){
    	$anio_mes_Actual = date("Y-m");
        $fechaFiltro = $anio_mes_Actual.'-01';
        $cancelado_mes = 0;
        $condicion1 = 0;
        $condicion2 = 0;
        $condicion3 = 0;
        $condicion4 = 0;
        //calculamos para los estados
        for ($estado = 1; $estado < 3; $estado++){

            for ($tipo_moneda = 1; $tipo_moneda < 3; $tipo_moneda++){
                //se haya todo lo relacionado a los pagos mientras sea en soles y aun no esté cancelada
                //condición 1
                if ($estado == 1 && $tipo_moneda == 1) {
                    $miArray = array();
                    $a_cuenta = DB::SELECT('
                        SELECT sum(p.pagos_monto)
                            FROM tbl_factura as f
                                INNER join
                                tbl_pagos as p
                                ON p.pagos_factura_cod = f.factura_cod
                                WHERE f.factura_estado != ?
                                    AND f.factura_tpmoneda_cod = ?
                                    AND f.factura_femision >= ?
                    ',[2, $tipo_moneda, $fechaFiltro]);

                    foreach ($a_cuenta as $valor) {
                        array_push($miArray, $valor->sum);
                    }
                    if (is_null($miArray[0])) {
                        $miArray[0] = 0;
                    }
                    $condicion1 = $miArray[0];
                }
                //si el tipo de moneda es 2 (dolar) se extrae el tipo de cambio de esa fecha
                //y se multiplica por la cantidad de dolares
                //condición 2
                if ($estado == 1 && $tipo_moneda == 2) {
                    $valores = DB::SELECT('
                        SELECT f.factura_total, t.valorventa
                            FROM tbl_factura as f
                                INNER JOIN
                                tbl_tipocambiosunat as t
                                ON f.factura_femision = fecha
                                WHERE f.factura_estado != ?
                                  AND f.factura_tpmoneda_cod = ?
                                  AND f.factura_femision >= ?
                                  ',
                                [2, $tipo_moneda, $fechaFiltro]
                    );
                    $dolares = array();
                    $tip_cam = array();
                    foreach ($valores as $valor) {
                        array_push($dolares, $valor->factura_total);
                        array_push($tip_cam, $valor->valorventa);
                    }
                    for ($k=0; $k < sizeof($dolares) ; $k++) {
                        $condicion2 = $condicion2 + ($dolares[$k] * $tip_cam[$k]);
                    }
                }
                //condición 3
                if ($estado == 2 && $tipo_moneda == 1) {
                    //$cancelado = se extrae el contenido de la columna factura_total
                    $soles = DB::table('tbl_factura')
                        ->SELECT('factura_total')
                        ->WHERE('factura_estado', '=', $estado)
                        ->WHERE('factura_tpmoneda_cod', '=', $tipo_moneda)
                        ->WHERE('factura_femision', '>=', $fechaFiltro)
                        ->sum('factura_total');

                    $condicion3 = $soles;
                }
                //condición 4
                if ($estado == 2 && $tipo_moneda == 2) {
                    $valores = DB::SELECT('
                        SELECT f.factura_total, t.valorventa
                            FROM tbl_factura as f
                                INNER JOIN
                                tbl_tipocambiosunat as t
                                ON f.factura_femision = fecha
                                WHERE f.factura_estado = ?
                                  AND f.factura_tpmoneda_cod = ?
                                  AND f.factura_femision >= ?
                                  ',
                                  [$estado, $tipo_moneda, $fechaFiltro]
                    );
                    $dolares = array();
                    $tip_cam = array();
                    foreach ($valores as $valor) {
                        array_push($dolares, $valor->factura_total);
                        array_push($tip_cam, $valor->valorventa);
                    }
                    for ($k=0; $k < sizeof($dolares) ; $k++) {
                        $condicion4 = $condicion4 + ($dolares[$k] * $tip_cam[$k]);
                    }
                }
            }
        }
        $cancelado_mes = $condicion1 + $condicion2 + $condicion3 + $condicion4;
        return $cancelado_mes;
    }

    //suma total en soles de todo lo que se nos pagó hoy (solo día actual)
    public function pagosDiarios(){
        $cancelado_hoy = 0;
        $condicion1 = 0;
        $condicion2 = 0;
        $condicion3 = 0;
        $condicion4 = 0;
        for ($estado = 1; $estado < 3; $estado++){

            for ($tipo_moneda = 1; $tipo_moneda < 3; $tipo_moneda++){
                //se haya todo lo relacionado a los pagos mientras sea en soles y aun no esté cancelada
                //condición 1
                if ($estado == 1 && $tipo_moneda == 1) {
                    $miArray = array();
                    $a_cuenta = DB::SELECT('
                        SELECT sum(p.pagos_monto)
                            FROM tbl_factura as f
                                INNER join
                                tbl_pagos as p
                                ON p.pagos_factura_cod = f.factura_cod
                                WHERE f.factura_estado != ?
                                    AND f.factura_tpmoneda_cod = ?
                                    AND p.pagos_fechapago = (CAST(now() as date))
                    ',
                    [2, $tipo_moneda]);

                    foreach ($a_cuenta as $valor) {
                        array_push($miArray, $valor->sum);
                    }
                    if (is_null($miArray[0])) {
                        $miArray[0] = 0;
                    }
                    $condicion1 = $miArray[0];
                }
                //si el tipo de moneda es 2 (dolar) se extrae el tipo de cambio de esa fecha
                //y se multiplica por la cantidad de dolares
                //condición 2
                if ($estado == 1 && $tipo_moneda == 2) {
                    $valores = DB::SELECT('
                        SELECT p.pagos_monto, t.valorventa
                            FROM tbl_factura as f
                                INNER JOIN
                                tbl_tipocambiosunat as t
                                ON f.factura_femision = t.fecha
                                JOIN
                                tbl_pagos as p
                                ON p.pagos_factura_cod = f.factura_cod
                                WHERE f.factura_estado != ?
                                  AND f.factura_tpmoneda_cod = ?
                                  AND f.factura_femision = (CAST(now() as date))
                                ',
                                [2, $tipo_moneda]
                    );
                    $dolares = array();
                    $tip_cam = array();
                    foreach ($valores as $valor) {
                        array_push($dolares, $valor->pagos_monto);
                        array_push($tip_cam, $valor->valorventa);
                    }
                    for ($k=0; $k < sizeof($dolares) ; $k++) {
                        $condicion2 = $condicion2 + ($dolares[$k] * $tip_cam[$k]);
                    }
                }
                //condición 3
                if ($estado == 2 && $tipo_moneda == 1) {
                    //$cancelado = se extrae el contenido de la columna factura_total
                    $soles = DB::SELECT('
                        SELECT sum(factura_total)
                            FROM tbl_factura
                                WHERE factura_estado = ?
                                    AND factura_tpmoneda_cod = ?
                                    AND factura_femision = (CAST(now() as date))
                            ',
                            [$estado, $tipo_moneda]);

                    $miArray = array();
                    foreach ($soles as $valor) {
                        array_push($miArray, $valor->sum);
                    }
                    if (is_null($miArray[0])) {
                        $miArray[0] = 0;
                    }
                    $condicion3 = $miArray[0];
                }
                //condición 4
                if ($estado == 2 && $tipo_moneda == 2) {
                    $valores = DB::SELECT('
                        SELECT f.factura_total, t.valorventa
                            FROM tbl_factura as f
                                INNER JOIN
                                tbl_tipocambiosunat as t
                                ON f.factura_femision = t.fecha
                                WHERE f.factura_estado = ?
                                  AND f.factura_tpmoneda_cod = ?
                                  AND f.factura_femision = (CAST(now() as date))
                                  ',
                                [$estado, $tipo_moneda]
                    );
                    $dolares = array();
                    $tip_cam = array();
                    foreach ($valores as $valor) {
                        array_push($dolares, $valor->factura_total);
                        array_push($tip_cam, $valor->valorventa);
                    }
                    for ($k=0; $k < sizeof($dolares) ; $k++) {
                        $condicion4 = $condicion4 + ($dolares[$k] * $tip_cam[$k]);
                    }
                }
            }
        }
        $cancelado_hoy = $condicion1 + $condicion2 + $condicion3 + $condicion4;
        return $cancelado_hoy;
    }
}
