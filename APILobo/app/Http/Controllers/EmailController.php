<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use DB;
use Mail;
use App\Http\Requests;

class EmailController extends Controller
{
    public function enviarCorreo(){
    	$datos = 'estos son mis datos';
    	$pdf = \PDF::loadview('emails.contacto');
    	return $pdf->download('mipdf.pdf');
    }

    /*public function pdf(){
		require '../vendor/autoload.php';
		require '../config/database.php';

		ob_start();
		$productos = App\Entities\Product::get();
		include "../resources/views/emails/nuevo.php"
		$dompdf = new Dompdf\Dompdf();
		$dompdf->loadHtml(ob_get_clean());

		$dompdf->render();
		$dompdf->stream();
    }*/
}
