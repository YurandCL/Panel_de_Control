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
    		SELECT u.usuario, u.password, p.persona_nombres as nombres
				FROM tbl_usuarios as u
                INNER JOIN
                    tbl_persona as p
                    ON u.persona_cod = p.persona_cod
			    	    WHERE u.activo = ?
    	',['Y']);

    	for ($i=0; $i < sizeof($autorizados); $i++) { 
    		if ($autorizados[$i] -> usuario == $usuario && 
    			$autorizados[$i] -> password == $password) {
    			
    			$correcto = [['acceso' => $autorizados[$i] -> nombres]];
	    		return response(json_encode($correcto))
	    			->header('Content-Type','application/json');
    		}
    	}
    	$error = [['acceso' => 'error']];
	    return response(json_encode($error))
	    	->header('Content-Type','application/json');
    }
}
