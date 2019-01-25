<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Reporte Generado</title>
    <link rel="stylesheet" type="text/css" href="css/estilo.css">
</head>
<body>
    <div>
        <img src="imagenes/lobo.png">
        <table class="tabla_cliente">
            <tr class="filas_cliente">
                <td class="filas_cliente"><h5 class="datos_lobo lobo_sistemas">Lobo Sistemas S.A.C</h5></td>
            </tr>
            <tr class="filas_cliente">
                <td class="filas_cliente"><h5 class="datos_lobo">AV. EL EJERCITO NRO. 101 INT. 305 URB. JARDIN</h5></td>
            </tr>
            <tr class="filas_cliente">
                <td class="filas_cliente"><h5 class="datos_lobo">YANAHUARA - AREQUIPA - AREQUIPA</h5></td>
            </tr>
        </table>
        <table class="tabla_cliente espaciado_tbl">
            <tr class="filas_cliente">        
                <td class="filas_cliente"><h5>TELEFONO</h5></td>
                <td><h5>:</h5></td>
                <td><h5>(054) 627479 </h5></td>
            </tr>
            <tr class="filas_cliente">
                <td class="filas_cliente"><h5>CORREO</h5></td>
                <td><h5>:</h5></td>
                <td><h5>hola@lobosistemas.com</h5></td>
            </tr>
            <tr class="filas_cliente">
                <td class="filas_cliente"><h5>WEB</h5></td>
                <td><h5>:</h5></td>
                <td><h5>www.lobosistemas.com</h5></td>
            </tr>
        </table>
        <div id="div_dat">
            <p>FACTURA ELECTRONICA</p>
            @foreach($datos as $datos_lobo)
            @endforeach
            <p>RUC: 20454825455</p>
            <p>{{$datos_lobo->serie}}-{{$datos_lobo->numero}}</p>
        </div>
    </div>
  <br>
    @foreach($datos as $datos_cli)
    @endforeach
    <table class="tabla_cliente">
        <tr class="filas_cliente">
            <td class="filas_cliente"><h5>Fecha de Emisión</h5></td>
            <td><h5>:</h5></td>
            <td><h5>{{ $datos_cli-> femision }}</h5></td>
        </tr>
        <tr class="filas_cliente">
            <td class="filas_cliente"><h5>Fecha de Vencimiento</h5></td>
            <td><h5>:</h5></td>
            <td><h5>{{ $datos_cli-> fvencimiento}}</h5></td>
        </tr>
        <tr class="filas_cliente">
            <td class="filas_cliente"><h5>Cliente</h5></td>
            <td><h5>:</h5></td>
            <td><h5>{{ $datos_cli-> cliente}}</h5></td>
        </tr>
        <tr class="filas_cliente">
            <td class="filas_cliente"><h5>Dirección</h5></td>
            <td><h5>:</h5></td>
            <td><h5>{{ $datos_cli-> direccion_cliente}}</h5></td>
        </tr>
        <tr class="filas_cliente">
            <td class="filas_cliente"><h5>Ruc</h5></td>
            <td><h5>:</h5></td>
            <td><h5>{{ $datos_cli-> ruc}}</h5></td>
        </tr>
        <tr class="filas_cliente">
            <td class="filas_cliente"><h5>Tipo de Moneda</h5></td>
            <td><h5>:</h5></td>
            <td><h5>{{ $datos_cli-> moneda}}</h5></td>
    </table>
    <br>
    <?php 
        function redondeo($redon){        
            $test = round($redon, 2);
            $test = $test.'';
            $test2='';
            for ($i=0; $i <strlen($test) ; $i++) {
                if ($test{$i} == '.') {
                    $test2=substr($test, $i+1);
                }
            }
            if($test2 == ''){
                $test = $test.'.00';
            }else if (strlen($test2) == 1) {
                $test = $test.'0';
            }
            return $test;
        }
    ?>
    <table id="fac_datos">
        <thead>
            <tr id="borde_fac_datos">
                <th class="datos_fac unidades th_c" colspan="2"><h5 class="h5_th h5_unidades">Cantidad:</h5></th>
                <th class="datos_fac unidades"><h5 class="h5_th h5_unidades">Unidad Medida:</h5></th>
                <th class="datos_fac unidades th_c"><h5 class="h5_th h5_unidades">Código:</h5></th>
                <th class="datos_fac" colspan="7"><h5 class="h5_th val_uni">Descripción:</h5></th>
                <th class="datos_fac"><h5 class="h5_th">Valor Unitario:</h5></th>
            </tr>
        </thead>
        <tbody>
            @foreach($datos as $datos_fac)
                <tr>
                    <td class="monto" colspan="2"><h5 class="h5 val_uni">{{ $datos_fac-> cantidad}}</h5></td>
                    <td class="monto"><h5 class="h5 val_uni">UNIDAD</h5></td>
                    <td class="monto"><h5 class="h5 val_uni">{{ $datos_fac-> codigo_prod}}</h5></td>
                    <td class="monto" colspan="7"><h5 class="h5 descripcion">{{ $datos_fac-> descripcion}}</h5></td>
                    <td class="monto"><h5 class="h5">{{ redondeo($datos_fac-> valor_unitario)}}</h5></td>
                </tr>
            @endforeach
        </tbody>
    </table>
    
    <br><br>
    <br><br>
    <br><br>
    <table id="banco">
        <tr>
            <th class="cuentas">
                <h5 class="h5_cuent">Cuentas Corrientes:</h5>
            </th>    
        </tr>
        @for($i=0; $i < 3; $i++)
            <tr>
                <td class="cuentas cuentas_td">
                    <h5>
                        {{$cuentas_bancarias[$i] -> nombre}}
                        {{$cuentas_bancarias[$i] -> simbolo}}
                        {{$cuentas_bancarias[$i] -> nro_cuenta}}
                    </h5>
                </td>
                <td class="cuentas">
                    <h5>CCI: {$cuentas_bancarias[$i]->cci}</h5>
                </td>
            </tr>
        @endfor
        <tr>
            <th class="cuentas">
                <h5 class="h5_cuent">Cuentas de Detracciones:</h5>
            </th>    
        </tr>
        <tr>
            <td class="cuentas cuentas_td">
                <h5>
                    {{$cuentas_bancarias[4] -> nombre}}
                    {{$cuentas_bancarias[4] -> simbolo}}
                    {{$cuentas_bancarias[4] -> nro_cuenta}}
                </h5>
            </td>
        </tr>
    </table>
    <?php
    // FUNCIONES DE CONVERSION DE NUMEROS A LETRAS.
        function centimos(){
            global $importe_parcial;

            $importe_parcial = number_format($importe_parcial, 2, ".", "") * 100;

            if ($importe_parcial > 0){

                $num_letra = " con ".decena_centimos($importe_parcial);
            }else{

                $num_letra = "";
            }
            return $num_letra;
        }

        function decena_centimos($numero){
            return $numero.'/100 SOLES';
        }

        function unidad($numero){
            switch ($numero){
                case 9:{
                    $num = "nueve";
                    break;
                }
                case 8:{
                    $num = "ocho";
                    break;
                }
                case 7:{
                    $num = "siete";
                    break;
                }
                case 6:{
                    $num = "seis";
                    break;
                }
                case 5:{
                    $num = "cinco";
                    break;
                }
                case 4:{
                    $num = "cuatro";
                    break;
                }
                case 3:{
                    $num = "tres";
                    break;
                }
                case 2:{
                    $num = "dos";
                    break;
                }
                case 1:{
                    $num = "uno";
                    break;
                }
            }
            return $num;
        }

        function decena($numero){
            if ($numero >= 90 && $numero <= 99){
                $num_letra = "noventa ";
                if ($numero > 90){
                    $num_letra = $num_letra."y ".unidad($numero - 90);
                }
            }
            else if ($numero >= 80 && $numero <= 89){
                $num_letra = "ochenta ";
                if ($numero > 80){
                    $num_letra = $num_letra."y ".unidad($numero - 80);
                }
            }
            else if ($numero >= 70 && $numero <= 79){
                    $num_letra = "setenta ";
                if ($numero > 70){
                    $num_letra = $num_letra."y ".unidad($numero - 70);
                }
            }
            else if ($numero >= 60 && $numero <= 69){
                $num_letra = "sesenta ";
                if ($numero > 60){
                    $num_letra = $num_letra."y ".unidad($numero - 60);
                }
            }
            else if ($numero >= 50 && $numero <= 59){
                $num_letra = "cincuenta ";
                if ($numero > 50){
                    $num_letra = $num_letra."y ".unidad($numero - 50);
                }
            }
            else if ($numero >= 40 && $numero <= 49){
                $num_letra = "cuarenta ";
                if ($numero > 40){
                    $num_letra = $num_letra."y ".unidad($numero - 40);
                }
            }
            else if ($numero >= 30 && $numero <= 39){
                $num_letra = "treinta ";
                if ($numero > 30){
                    $num_letra = $num_letra."y ".unidad($numero - 30);
                }
            }
            else if ($numero >= 20 && $numero <= 29){
                if ($numero == 20){
                    $num_letra = "veinte ";
                }
                else{
                    $num_letra = "veinti".unidad($numero - 20);
                }
            }
            else if ($numero >= 10 && $numero <= 19){
                switch ($numero){
                    case 10:{
                        $num_letra = "diez ";
                        break;
                    }
                    case 11:{
                        $num_letra = "once ";
                        break;
                    }
                    case 12:{
                        $num_letra = "doce ";
                        break;
                    }
                    case 13:{
                        $num_letra = "trece ";
                        break;
                    }
                    case 14:{
                        $num_letra = "catorce ";
                        break;
                    }
                    case 15:{
                        $num_letra = "quince ";
                        break;
                    }
                    case 16:{
                        $num_letra = "dieciseis ";
                        break;
                    }
                    case 17:{
                        $num_letra = "diecisiete ";
                        break;
                    }
                    case 18:{
                        $num_letra = "dieciocho ";
                        break;
                    }
                    case 19:{
                        $num_letra = "diecinueve ";
                        break;
                    }
                }
            }
            else{
                $num_letra = unidad($numero);
            }
            return $num_letra;
        }

        function centena($numero){
            if ($numero >= 100){
                if ($numero >= 900 & $numero <= 999){
                    $num_letra = "novecientos ";
                    if ($numero > 900){
                        $num_letra = $num_letra.decena($numero - 900);
                    }
                }
                else if ($numero >= 800 && $numero <= 899){
                    $num_letra = "ochocientos ";
                    if ($numero > 800){
                        $num_letra = $num_letra.decena($numero - 800);
                    }
                }
                else if ($numero >= 700 && $numero <= 799){
                    $num_letra = "setecientos ";
                    if ($numero > 700){
                        $num_letra = $num_letra.decena($numero - 700);
                    }
                }
                else if ($numero >= 600 && $numero <= 699){
                    $num_letra = "seiscientos ";
                    if ($numero > 600){
                        $num_letra = $num_letra.decena($numero - 600);
                    }
                }
                else if ($numero >= 500 && $numero <= 599){
                    $num_letra = "quinientos ";
                    if ($numero > 500){
                        $num_letra = $num_letra.decena($numero - 500);
                    }
                }
                else if ($numero >= 400 && $numero <= 499){
                    $num_letra = "cuatrocientos ";
                    if ($numero > 400){
                        $num_letra = $num_letra.decena($numero - 400);
                    }
                }
                else if ($numero >= 300 && $numero <= 399){
                    $num_letra = "trescientos ";
                    if ($numero > 300){
                        $num_letra = $num_letra.decena($numero - 300);
                    }
                }
                else if ($numero >= 200 && $numero <= 299){
                    $num_letra = "doscientos ";
                    if ($numero > 200){
                        $num_letra = $num_letra.decena($numero - 200);
                    }
                }
                else if ($numero >= 100 && $numero <= 199){
                    if ($numero == 100){
                        $num_letra = "cien ";
                    }
                    else{
                        $num_letra = "ciento ".decena($numero - 100);
                    }
                }
            }
            else{
                $num_letra = decena($numero);
            }
            return $num_letra;
        }

        function cien(){
            global $importe_parcial;
            $parcial = 0; $car = 0;

            while (substr($importe_parcial, 0, 1) == 0){
                $importe_parcial = substr($importe_parcial, 1, strlen($importe_parcial) - 1);
            }
            if ($importe_parcial >= 1 && $importe_parcial <= 9.99){
                $car = 1;
            }
            else if ($importe_parcial >= 10 && $importe_parcial <= 99.99){
                $car = 2;
            }
            else if ($importe_parcial >= 100 && $importe_parcial <= 999.99){
                $car = 3;
            }

            $parcial = substr($importe_parcial, 0, $car);
            $importe_parcial = substr($importe_parcial, $car);
            $num_letra = centena($parcial).centimos();
            return $num_letra;
        }

        function cien_mil(){
            global $importe_parcial;
            $parcial = 0; $car = 0;

            while (substr($importe_parcial, 0, 1) == 0){
                $importe_parcial = substr($importe_parcial, 1, strlen($importe_parcial) - 1);
            }

            if ($importe_parcial >= 1000 && $importe_parcial <= 9999.99){
                $car = 1;
            }
            else if ($importe_parcial >= 10000 && $importe_parcial <= 99999.99){
                $car = 2;
            }
            else if ($importe_parcial >= 100000 && $importe_parcial <= 999999.99){
                $car = 3;
            }

            $parcial = substr($importe_parcial, 0, $car);
            $importe_parcial = substr($importe_parcial, $car);

            if ($parcial > 0){
                if ($parcial == 1){
                    $num_letra = "mil ";
                }
                else{
                    $num_letra = centena($parcial)." mil ";
                }
            }
            return $num_letra;
        }

        function millon(){
            global $importe_parcial;
            $parcial = 0; $car = 0;

            while (substr($importe_parcial, 0, 1) == 0){
                $importe_parcial = substr($importe_parcial, 1, strlen($importe_parcial) - 1);
            }

            if ($importe_parcial >= 1000000 && $importe_parcial <= 9999999.99){
                $car = 1;
            }

            else if ($importe_parcial >= 10000000 && $importe_parcial <= 99999999.99){
                $car = 2;
            }

            else if ($importe_parcial >= 100000000 && $importe_parcial <= 999999999.99){
                $car = 3;
            }

            $parcial = substr($importe_parcial, 0, $car);
            $importe_parcial = substr($importe_parcial, $car);

            if ($parcial == 1){
                $num_letras = "un millón ";
            }

            else{
                $num_letras = centena($parcial)." millones ";
            }
            return $num_letras;
        }

        function convertir_a_letras($numero)

        {
            global $importe_parcial;
            $importe_parcial = $numero;

            if ($numero < 1000000000){
                if ($numero >= 1000000 && $numero <= 999999999.99){
                    $num_letras = millon().cien_mil().cien();
                }
                else if ($numero >= 1000 && $numero <= 999999.99){
                    $num_letras = cien_mil().cien();
                }

                else if ($numero >= 1 && $numero <= 999.99){
                    $num_letras = cien();
                }
                else if ($numero >= 0.01 && $numero <= 0.99){

                    if ($numero == 0.01){
                        $num_letras = "un céntimo";
                    }
                    else{
                        $num_letras = convertir_a_letras(($numero * 100)."/100")." céntimos";
                    }
                }
            }
            return $num_letras;
        }
    ?>
    <h4>SON {{strtoupper(convertir_a_letras($datos_fac-> total))}}</h4>
    <table id="tbl_izquierda">
        <tr >
            <th class="it_tbl_izquierda">Sub Total Ventas:</th>
            <td class="it_tbl_izquierda">{{ redondeo($datos_fac-> opgravadas)}}</td>
        </tr>
        <tr >
            <th class="it_tbl_izquierda">Descuentos:</th>
            <td class="it_tbl_izquierda">{{ redondeo($datos_fac-> descuento)}}</td>
        </tr>
        <tr >
            <th class="it_tbl_izquierda">Valor Venta:</th>
            <td class="it_tbl_izquierda">{{ redondeo($datos_fac-> opgravadas)}}</td>
        </tr>
        <tr>
            <th class="it_tbl_izquierda">IGV: </th>
            <td class="it_tbl_izquierda"><?php 
                echo redondeo($datos_fac-> total - $datos_fac-> opgravadas);
            ?></td>
        </tr>
        <tr>
            <th class="it_tbl_izquierda">Importe Total:</th>
            <td class="it_tbl_izquierda">{{ redondeo($datos_fac-> total)}}</td>
        </tr>
    </table>
    <br><br><br><br><br>
    <table >
        <tr>
            <th class="footer">
                Esta es una representación  impresa de la factura  electrónica
                {{$datos_lobo->serie}}-{{$datos_lobo->numero}} generada en el
                Sistema de SUNAT. Puede verificarla utilizando su clave SOL
            </th>
        </tr>
    </table>
</body>
</html>
