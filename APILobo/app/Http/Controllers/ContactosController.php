<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use DB;
use App\Http\Requests;

class ContactosController extends Controller
{
    public function cargarUsuarios($ruc){
    	$usuarios = DB::connection('mysql')
    		->SELECT('
    			SELECT u.email
					FROM lobosoportedb.users as u
						INNER JOIN
				        lobosoportedb.empresas as e
				        ON e.id = u.empresa_id
							WHERE e.ruc =  ?
    		', [$ruc]
    	);
    	return $usuarios;
    }
}
