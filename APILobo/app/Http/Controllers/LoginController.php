<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use DB;
use App\Http\Requests;

class LoginController extends Controller
{
    public function logeo(Request $request){
    	$usuario = $request->input('usuario');
    	$password = $request->input('password');
    	
    	$autorizados = DB::SELECT('
    		SELECT u.usuario, u.password
				FROM tbl_usuarios as u
			    	WHERE u.activo = ?
    	',['Y']);

    	for ($i=0; $i < sizeof($autorizados); $i++) { 
    		if ($autorizados[$i]->usuario == $usuario && 
    			$autorizados[$i]->password == $password) {
    			
    			$correcto = [['estado' => 'ok']];
	    		return response(json_encode($correcto))
	    			->header('Content-Type','application/json');
    		}
    	}
    	$error = [['estado' => 'error']];
	    return response(json_encode($error))
	    	->header('Content-Type','application/json');
    }
}
