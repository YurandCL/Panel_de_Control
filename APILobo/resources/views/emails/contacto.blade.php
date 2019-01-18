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
        <div id="div_dat">
            <p>FACTURA ELECTRONICA</p>
            @foreach($datos as $datos_lobo)
            @endforeach
            <p>RUC: 20454825455</p>
            <p>{{$datos_lobo->serie}}-{{$datos_lobo->numero}}</p>
        </div>
    </div>
  <br><br><br>
    @foreach($datos as $datos_cli)
    @endforeach
    <table id="tabla_cliente">
        <tr class="filas_cliente">
            <td class="filas_cliente"><h5>DIRECCIÓN</h5></td>
            <td><h5>:</h5></td>
            <td><h5>Av. Ejercito 103 of. 305</h5></td>
        </tr>
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
            <td class="filas_cliente"><h5>FECHA DE EMISIÓN</h5></td>
            <td><h5>:</h5></td>
            <td><h5>{{ $datos_cli-> femision }}</h5></td>
        </tr>
        <tr class="filas_cliente">
            <td class="filas_cliente"><h5>FECHA DE VENCIMIENTO</h5></td>
            <td><h5>:</h5></td>
            <td><h5>{{ $datos_cli-> fvencimiento}}</h5></td>
        </tr>
        <tr class="filas_cliente">
            <td class="filas_cliente"><h5>CLIENTE</h5></td>
            <td><h5>:</h5></td>
            <td><h5>{{ $datos_cli-> cliente}}</h5></td>
        </tr>
        <tr class="filas_cliente">
            <td class="filas_cliente"><h5>RUC</h5></td>
            <td><h5>:</h5></td>
            <td><h5>{{ $datos_cli-> ruc}}</h5></td>
        </tr>
        <tr class="filas_cliente">
            <td class="filas_cliente"><h5>TIPO DE MONEDA</h5></td>
            <td><h5>:</h5></td>
            <td><h5>{{ $datos_cli-> moneda}}</h5></td>
    </table>
    <?php 
        function redondeo($redon){
            $test = round($redon,2);
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
            <tr >
                <th class="datos_fac" colspan="2"><h5 class="h5_th">CANTIDAD:</h5></th>
                <th class="datos_fac" colspan="3"><h5 class="h5_th">DESCRIPCIÓN:</h5></th>
                <th class="datos_fac" colspan="2"><h5 class="h5_th">VALOR UNITARIO:</h5></th>
            </tr>
        </thead>
        <tbody>
            @foreach($datos as $datos_fac)
                <tr>
                    <td class="monto" colspan="2"><h5 class="h5">{{ $datos_fac-> cantidad}}</h5></td>
                    <td class="monto" colspan="3"><h5 class="h5">{{ $datos_fac-> descripcion}}</h5></td>
                    <td class="monto" colspan="2"><h5 class="h5">{{ redondeo($datos_fac-> valor_unitario)}}</h5></td>
                </tr>
            @endforeach
        </tbody>
    </table>
    <br>
    <table id="tbl_izquierda">
        <tr >
            <th>SUB TOTAL:</th>
            <td>{{ redondeo($datos_fac-> opgravadas)}}</td>
        </tr>
        <tr>
            <th>IGV: </th>
            <td>{{ redondeo($datos_fac->monto_igv)}}</td>
        </tr>
        <tr>
            <th>TOTAL:</th>
            <td>{{ redondeo($datos_fac-> total)}}</td>
        </tr>
    </table>
    <br><br>
    <br><br>
    <br><br>
    <footer>
        <table class="pie_pag">
            <tr>
                <th class="footer">
                    Esta es una representación  impresa de la factura  electrónica
                    {{$datos_lobo->serie}}-{{$datos_lobo->numero}} generada en el
                    Sistema de SUNAT. Puede verificarla utilizando su clave SOL
                </th>
            </tr>
        </table>
    </footer>
</body>
</html>
