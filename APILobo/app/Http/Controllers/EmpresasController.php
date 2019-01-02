<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
//use Illuminate\Support\Facades\DB;
use DB;
use App\Http\Requests;

class EmpresasController extends Controller
{
    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function getIndex(){
        $factura_vencida = DB::table('tbl_factura')->select('factura_cod','factura_fvencimiento')->where('factura_estado', '=', '0')->get();
        //codigo de la factura emitida
        $arreglo_facturas_cod = array();
        //fecha de la factura emitida
        $arreglo_facturas_fvencimiento = array();
        
        //obtenemos todos los codigos de las empresas
        for ($i=0; $i < sizeof($factura_vencida); $i++) { 
             array_push($arreglo_facturas_cod, $factura_vencida[$i]->factura_cod);
         }

        //obtenemos la fecha de vencimiento de cada una de las facturas
        for ($i=0; $i < sizeof($factura_vencida); $i++) { 
             array_push($arreglo_facturas_fvencimiento, $factura_vencida[$i]->factura_fvencimiento);
         }
        
        //obtenemos el codigo de todas las empresas que nos deben
        $arreglo_empresas = array();
        $codigo=0;
        
        for ($i=0; $i < sizeof($arreglo_facturas_cod); $i++){

            $codigo_empresa = DB::table('tbl_factura')->select('factura_cliente_cod')->where('factura_cod', '=', $arreglo_facturas_cod[$i])->first();
            foreach ($codigo_empresa as $key => $value) {
               $codigo = $value;

               //obtenemos el nombre de la empresa con el codigo que obtenemos antes
               $nombre_empresa = DB::table('tbl_empresa')->select('empresa_razonsocial')->where('empresa_cod', '=', $codigo)->first();
               array_push($arreglo_empresas, $nombre_empresa->empresa_razonsocial);
            }
        }
       
        return $arreglo_empresas;
    }

    public function recorrido_array($arreglos){
        foreach ($arreglos as $key => $value) {
           $valor_final = $value;
       }
        return $valor_final;
    }

    public function create()
    {
        //
    }

    /**
     * Store a newly created resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @return \Illuminate\Http\Response
     */
    public function store(Request $request)
    {
        //
    }

    /**
     * Display the specified resource.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function show($id)
    {
        //
    }

    /**
     * Show the form for editing the specified resource.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function edit($id)
    {
        //
    }

    /**
     * Update the specified resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function update(Request $request, $id)
    {
        //
    }

    /**
     * Remove the specified resource from storage.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function destroy($id)
    {
        //
    }
}
