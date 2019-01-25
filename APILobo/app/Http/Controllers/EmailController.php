<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use DB;
use Mail;
use PDF;
use Storage;
use App\Http\Requests;

class EmailController extends Controller
{
    //Envio de una factura seleccionada
    public function enviarCorreo_pdf(Request $request){
    	$factura_num = $request->input('factura_num');
    	$correo 	 = $request->input('correo');
    	//consulta con BD, lleva por dato la variable $factura_num
    	$datos = DB::SELECT('
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
                SELECT e.empresa_direccion
                    FROM tbl_factura as f
                        INNER JOIN
                        tbl_empresa as e
                        ON e.empresa_cod = f.factura_cliente_cod
                            WHERE f.factura_numero = ?
                )as direccion_cliente,
                (
                SELECT m.tpmoneda_dsc
                    FROM tbl_tpmoneda as m
                        INNER JOIN
                        tbl_factura as f
                        ON f.factura_tpmoneda_cod = m.tpmoneda_cod
                        WHERE f.factura_numero = ?
                )as moneda,
                fd.facturadet_descripciondet as descripcion,
                fd.facturadet_valorunitario as valor_unitario,
                fd.facturadet_cantidad as cantidad,
                f.factura_serie as serie,
                f.factura_numero as numero,
                f.factura_opgravadas as opgravadas,
                f.factura_montoigv as monto_igv,
                f.factura_descuento as descuento,
                fd.facturadet_codigo_prod as codigo_prod,
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
        	',
        	[$factura_num, $factura_num, $factura_num, $factura_num, $factura_num, $factura_num, $factura_num, $factura_num, $factura_num,]);

    	$cuentas_bancarias = DB::SELECT('
				SELECT b.bancos_descripcion as nombre,
					tm.tpmoneda_smbl as simbolo,
					cb.cuentasbancarias_nrocuenta as nro_cuenta
					FROM tbl_bancos as b
				    	INNER JOIN
				    	tbl_cuentasbancarias as cb
				        	ON (b.bancos_cod = 
				        	cb.cuentasbancarias_banco_cod)
				        INNER JOIN
				        tbl_tpmoneda as tm
				        	ON (cb.cuentasbancarias_tpmoneda_cod
				       			= tm.tpmoneda_cod)
			');

    	//Eliminación del PDF anteriormente creado si esque este no se ha borrado antes
    	if (file_exists('factura.pdf')) {
    		\File::delete(public_path('factura.pdf'));
    	}

    	//Creacion de pdf con datos obtenidos en la consulta
    	$pdf = PDF::loadview('emails.factura', ['datos' => $datos], ['cuentas_bancarias' => $cuentas_bancarias])
    		->save('factura.pdf');
    	//return $pdf->stream();
    	//informacion adicional que puede llevar el correo. (nombre de la empresa, numeros de telefono, etc.)
    	//(telefonos, correos adicionales, gerente, etc)
    	$info = array(
    		'nombre' 			=>  'Lobo Sistemas S.A.C',
			'saludo' 			=>	'Estimado cliente '.$datos[0] -> cliente.' le adjuntamos el documento electrónico de su factura',
			'mensaje'			=>	'Nro. de Factura: '.$datos[0] -> serie.'-'.$datos[0] -> numero.'<br>
			Fecha de Emisión: '.$datos[0] -> femision.'<br>
			Fecha de Vencimiento: '.$datos[0] -> fvencimiento,
			'telefono'			=> 'fijo:(054)627479',
			'celular' 			=> 'cel: 958310833',
    	);
		//return $info;
	    //verificamos que efectivamente se mande el correo, sinó se enviará un error
		try{
	    	//armamos la estructura del correo, le pasamos una vista seguido del array
	    	//que tiene la información de la empresa, y por último la función que le dirá
	    	//que es lo que tiene que hacer o enviar
	    	Mail::send('emails.correo', $info, function($msj) use ($correo, $pdf){
	    		
	    		//emisario del correo electronico, junto al nombre, (puede ser el nombre
	    		//de la empresa o algo que lo caracterize)
	    		$msj->from('frezer828@gmail.com', 'Lobo Sistemas');
	    		
	    		//se elije el destinatario y el asunto que tendrá el correo
	    		$msj->to($correo)->subject('Factura Electronica Lobo Sistemas');
	    		
	    		//se adjunta el archivo pdf que se enviará mediante este.
	    		$msj->attach('factura.pdf');	
	    	});

	    }catch (\Exception $e){
	    	$error = [['estado' => 'error']];
	    	return response(json_encode($error))->header('Content-Type','application/json');
	    }
    	//eliminamos el archivo para que no nos gaste espacio de almacenamiento
    	\File::delete(public_path('factura.pdf'));
    	
    	$correcto = [['estado'=>'ok']];
    	return response(json_encode($correcto))->header('Content-Type','application/json');
    }

    //envio del reporte de todas las facturas segun el cliente
    public function reporte_facturas(Request $request){
        
        $ruc = $request->input('ruc');
    	$correo = $request->input('correo');

        $reporte = DB::SELECT(
        	'SELECT f.factura_serie as serie, f.factura_numero as numero, 
					fd.facturadet_descripciondet as descripcion, f.factura_femision as emision, 
			        f.factura_fvencimiento as vencimiento, 
			        (CAST(now() as date) - f.factura_fvencimiento) as dias_retraso,
			        f.factura_total as total, f.factura_tpmoneda_cod as moneda,
			        e.empresa_ruc as ruc, e.empresa_razonsocial as cliente,
			        (CAST(now() as date)) as hoy
				FROM tbl_factura as f
			    	INNER JOIN
			    	tbl_empresa as e
			        ON (e.empresa_cod = f.factura_cliente_cod)
			        INNER JOIN
			        tbl_facturadet as fd
			        ON (f.factura_cod = fd.facturadet_factura_cod)
			        where e.empresa_ruc = ?
			        	AND f.factura_estado = ?
			        	GROUP BY  f.factura_serie, f.factura_numero, 
			        	f.factura_cod,	fd.facturadet_descripciondet,
			        	f.factura_femision, f.factura_fvencimiento, 
			        	f.factura_total, f.factura_tpmoneda_cod,
			        	e.empresa_razonsocial, e.empresa_ruc
              		ORDER BY f.factura_numero'
			       	,[$ruc, '0']
        );

        $saldos = array();
		for ($i=0; $i < sizeof($reporte); $i++) { 
			$saldo_cod = DB::SELECT(
        	'SELECT sum(p.pagos_monto) as suma
				from tbl_pagos as p
			    inner join 
			    tbl_factura as f
			    on p.pagos_factura_cod = f.factura_cod
				WHERE f.factura_numero = ?'
			, [$reporte[$i] -> numero]
        	);
        	//return $reporte;
        	foreach ($saldo_cod as $value) {
        		if (is_null($value->suma)) {
        			array_push($saldos, 0);
        			break;
        		}
        		array_push($saldos, $value -> suma);
        	}
		}
		$cuentas_bancarias = DB::SELECT('
				SELECT b.bancos_descripcion as nombre,
					tm.tpmoneda_smbl as simbolo,
					cb.cuentasbancarias_nrocuenta as nro_cuenta
					FROM tbl_bancos as b
				    	INNER JOIN
				    	tbl_cuentasbancarias as cb
				        	ON (b.bancos_cod = 
				        	cb.cuentasbancarias_banco_cod)
				        INNER JOIN
				        tbl_tpmoneda as tm
				        	ON (cb.cuentasbancarias_tpmoneda_cod
				       			= tm.tpmoneda_cod)
			');
		//Eliminación del PDF anteriormente creado si esque este no se ha borrado antes
    	if (file_exists('reporte_de_facturas.pdf')) {
    		\File::delete(public_path('reporte_de_facturas.pdf'));
    	}
 		$soles = array();
 		$dolares = array();
    	//Creacion de pdf con datos obtenidos en la consulta
    	$pdf = PDF::loadview('emails.reporte_factura', 
    		['reporte' => $reporte, 'saldos' => $saldos, 
    		'cuentas_bancarias' => $cuentas_bancarias,
    		'soles' => $soles, 'dolares' => $dolares])
    		->save('reporte_de_facturas.pdf');
    	//return $pdf->stream();
    	//informacion adicional que puede llevar el correo. (nombre de la empresa)
    	//(telefonos, correos adicionales, gerente, etc)
    	$info = array(
    		'saludo'	=> 'Estimado cliente '.$reporte[0] -> cliente,
			'mensaje' =>	'Adjuntamos su estado de cuenta para su pronta cancelacion<br>
			Atentamente:',
			'nombre' 	=>  'Lobo Sistemas S.A.C',
			'telefono'	=>	'Telefono: (054) 627479',
			'celular'	=>	'Celular: 958310833',
    	);


	    //verificamos que efectivamente se mande el correo, sinó se enviará un error
		try{
	    	//armamos la estructura del correo, le pasamos una vista seguido del array
	    	//que tiene la información de la empresa, y por último la función que le dirá
	    	//que es lo que tiene que hacer o enviar
	    	Mail::send('emails.correo', $info, function($msj) use ($correo, $pdf){
	    		
	    		//emisario del correo electronico, junto al nombre, (puede ser el nombre
	    		//de la empresa o algo que lo caracterize)
	    		$msj->from('frezer828@gmail.com', 'Lobo Sistemas');
	    		
	    		//se elije el destinatario y el asunto que tendrá el correo
	    		$msj->to($correo)->subject('Reporte de Facturas');
	    		
	    		//se adjunta el archivo pdf que se enviará mediante este.
	    		$msj->attach('reporte_de_facturas.pdf');	
	    	});

	    /*capturamos el error que se genera cuando el envio 
	    falla y mandamos un json con mensaje de error para que 
	    la aplicacion consumidora sepa que hubo un error al 
	    momento de enviar el mensaje*/
	    }catch (\Exception $e){
	    	$error = [['estado' => 'error']];
	    	return response(json_encode($error))->header('Content-Type','application/json');
	    }
    	//eliminamos el archivo para que no nos gaste espacio de almacenamiento
    	\File::delete(public_path('reporte_de_facturas.pdf'));
    	
    	$correcto = [['estado'=>'ok']];
    	return response(json_encode($correcto))->header('Content-Type','application/json');
    }
}
