<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use DB;
use App\Http\Requests;

class FacturasController extends Controller{

    //funcion para generar el monto total vendido en el mes
    public function facturasMonto(Request $request){
        $fechaEmi = $request->input('fechaInicio');
        $fechaVen = $request->input('fechaFin');
        $cancelado = 0;
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
                                    AND f.factura_fvencimiento < ?
                                limit ?
                    ',[2, $tipo_moneda, $fechaEmi, $fechaVen, 10]);

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
                                ON f.factura_femision = t.fecha
                                WHERE f.factura_estado != ?
                                  AND f.factura_tpmoneda_cod = ?
                                  AND f.factura_femision >= ?
                                  AND f.factura_fvencimiento < ?;
                                  ', 
                                  [2, $tipo_moneda, $fechaEmi, $fechaVen]
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
                        ->WHERE('factura_femision', '>=', $fechaEmi)
                        ->WHERE('factura_fvencimiento', '<', $fechaVen)
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
                                ON f.factura_femision = t.fecha
                                WHERE f.factura_estado = ?
                                  AND f.factura_tpmoneda_cod = ?
                                  AND f.factura_femision >= ?
                                  AND f.factura_fvencimiento < ?;
                                  ', 
                                  [$estado, $tipo_moneda, $fechaEmi, $fechaVen]
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
        $cancelado = $condicion1 + $condicion2 + $condicion3 + $condicion4;
        return $cancelado;
    }

    //funcion para generar el reporte de las emrpresas segun su factura
    public function datos_factura($factura_num){
        $datos_fac = DB::SELECT('
            SELECT  
            (
                SELECT f.factura_femision 
                    FROM tbl_factura as f 
                        INNER JOIN
                        tbl_empresa as e 
                        ON e.empresa_cod = f.factura_cliente_cod
                            WHERE f.factura_numero = ?
                )as femision,
                (
                SELECT f.factura_fvencimiento 
                    FROM tbl_factura as f 
                        INNER JOIN
                        tbl_empresa as e 
                        ON e.empresa_cod = f.factura_cliente_cod
                            WHERE f.factura_numero = ?
                )as fvencimiento,
                (
                SELECT e.empresa_ruc
                    FROM tbl_factura as f 
                        INNER JOIN
                        tbl_empresa as e 
                        ON e.empresa_cod = f.factura_cliente_cod
                            WHERE f.factura_numero = ?
                )as ruc, 
                (
                SELECT e.empresa_razonsocial 
                    FROM tbl_factura as f 
                        INNER JOIN
                        tbl_empresa as e 
                        ON e.empresa_cod = f.factura_cliente_cod
                            WHERE f.factura_numero = ?
                )as cliente,
                (
                SELECT m.tpmoneda_dsc
                    FROM tbl_tpmoneda as m 
                        INNER JOIN
                        tbl_factura as f
                        ON f.factura_tpmoneda_cod = m.tpmoneda_cod
                        WHERE f.factura_numero = ?
                )as moneda,
                fd.facturadet_descripciondet as descripcion,
                fd.facturadet_preciounitario as valor_unitario,
                (
                SELECT sum(p.pagos_monto)
                    FROM tbl_pagos as p 
                        INNER JOIN
                        tbl_factura as f
                        ON f.factura_cod = p.pagos_factura_cod
                        WHERE f.factura_numero = ?
                )as a_cuenta,
                (
                SELECT f.factura_total
                    FROM tbl_tpmoneda as m 
                        INNER JOIN
                        tbl_factura as f
                        ON f.factura_tpmoneda_cod = m.tpmoneda_cod
                        WHERE f.factura_numero = ?
                )as total

            FROM tbl_facturadet as fd 
                INNER JOIN
                tbl_factura as f
                ON f.factura_cod = fd.facturadet_factura_cod
                    WHERE f.factura_numero = ?
        ', [$factura_num, $factura_num, $factura_num, $factura_num, $factura_num, $factura_num, $factura_num, $factura_num,]);

        return $datos_fac;
    }
}