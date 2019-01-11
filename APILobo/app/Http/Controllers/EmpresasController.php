<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use DB;
use App\Http\Requests;

class EmpresasController extends Controller
{
    public function getListado(){
        $test = DB::select('SELECT f.factura_numero, e.empresa_razonsocial,
          (CAST(now() as date) - f.factura_fvencimiento) as calc
          from tbl_empresa as e inner join tbl_factura as f
          on e.empresa_cod = f.factura_cliente_cod where f.factura_estado = ?
          order by calc desc', [0]);
          return $test;
    }
}
