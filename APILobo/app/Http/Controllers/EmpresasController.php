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
    public function getListado(){
        $test = DB::select('SELECT f.factura_numero, e.empresa_razonsocial, (CAST(now() as date) - f.factura_fvencimiento) as calc from tbl_empresa as e inner join tbl_factura as f on e.empresa_cod = f.factura_cliente_cod where f.factura_estado = ? order by calc desc', [0]);
          return $test;
    }

        /*DB::select('SELECT id FROM professions WHERE title = ?', ['Desarrollador back-end']);
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

        $fecha_actual = date('Y-m-d');

        //obtenemos el total de dias que nos debe la empresa (todo está ordenado por el codigo de la factura)
        $dias_atrasados = array();

        for ($i=0; $i <sizeof($arreglo_facturas_fvencimiento) ; $i++) { 
            
            //creamos el array que usaremos para almacenar los datos por cada vez que lo usemos
            //y lo limpiamos para almacenar siempre en la misma posición
            $dias_retraso = array();

            //creamos una lista de variables que almacenarán nuestras fechas de acuerdo a la fecha de vencimiento de cada factura (formato "Y-m-d" siempre);
            //el metodo explode() nos ayuda separando los datos para poder almacenarlos en las varialbes antes definidas;
            list($anio_vencimiento, $mes_vencimiento, $dia_vencimiento) = explode('-', $arreglo_facturas_fvencimiento[$i]);

            //acomodamos las fechas almacenadas anteriormente para volverlas un solo string y poder crear una variable de tipo fecha después
            $fecha_ven = ''.$anio_vencimiento . '-'. $mes_vencimiento . '-' . $dia_vencimiento.'';

            //usamos las fechas ya obtenidas (actual y al momento que vence la factura);
            //para poder extraer el número de días que un cliente debe a la empresa Lobo Sistemas (solo son los dias de una factura)
            $fecha_ini = date_create($fecha_ven);
            $fecha_fin = date_create($fecha_actual);
            $interval = date_diff($fecha_ini, $fecha_fin);
            
            //el intervalo obtenido es transformado a un array para poder hacer la extracción de los datos
            foreach ($interval as $retraso) {
                $dias_retraso[] = $retraso;
            }

            //Extraemos el dato que nos interesa (el número de los dias que se nos debe por cada factura)
            //usamos la posicion 10 porque en el array el dato que necesitamos se encuentra en dicha posición
            array_push($dias_atrasados, $dias_retraso[10]);
        }

        $NomEmpresa_diasAtraso = array();
        for ($i=0; $i < sizeof($arreglo_empresas); $i++) { 
            $NomEmpresa_diasAtraso[$arreglo_empresas[$i]] = $dias_atrasados[$i];
        }

        echo sizeof($NomEmpresa_diasAtraso);
        echo "<pre>";
        print_r($NomEmpresa_diasAtraso);
        echo "</pre>"; */

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
