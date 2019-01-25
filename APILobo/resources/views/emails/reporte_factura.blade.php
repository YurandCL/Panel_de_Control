<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Reporte Generado</title>
    <link rel="stylesheet" type="text/css" href="css/estilo_reporte.css">
</head>
<body>
    <div>
        <img src="imagenes/lobo.png">
        <h3>REPORTE DE FACTURAS VENCIDAS</h3>
    </div>
    @foreach($reporte as $datos_cli)
    @endforeach
    <table class="datos">
        <tr>
            <th class="datos">
                RUC
            </th>
            <td class="datos">
                <h5 class="datos">
                    : {{$datos_cli-> ruc}}
                </h5>
            </td>
        </tr>
        <tr>
            <th class="datos">
                CLIENTE
            </th>
            <td class="datos">
                <h5 class="datos h5_datos">
                : {{$datos_cli-> cliente}}</h5>
            </td>
        </tr>
        <tr>
            <th class="datos">
                FECHA
            </th>
            <td class="datos">
                <h5 class="datos">
                : {{$datos_cli -> hoy}}</h5></th>
            </td>
        </tr>
    </table>
    <br>
     <table id="detalles">
        <tr>
            <th>Factura</th>
            <th>Descripción</th>
            <th>Emisión</th>
            <th>Venci...</th>
            <th>Dias Retr.</th>
            <th>Total S/</th>
            <th>Saldo S/</th>
            <th>Total $</th>
            <th>Saldo $</th>
        </tr>

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

        @for ($i = 0; $i < sizeof($reporte); $i++)
            @if($i>0 && $reporte[$i]->numero != $reporte[$i-1]->numero)
                <tr>
                    <td><h6>{{ $reporte[$i]-> serie }}-{{ $reporte[$i]-> numero }}</h6></td>
                    <td><h6 class="descripcion"><?php
                        $descripcion = $reporte[$i]-> descripcion;
                        $temp='';
                        for($j=0; $j<13; $j++){
                            $temp = $temp.$descripcion[$j];
                        }
                        echo $temp.'...';
                    ?></h6></td>
                    <td><h6>{{ $reporte[$i]-> emision}}</h6></td>
                    <td><h6>{{ $reporte[$i]-> vencimiento}}</h6></td>
                    <td><h6 class="numeros">{{ $reporte[$i]-> dias_retraso}}</h6></td>
                    @if($reporte[$i]->moneda == '1')
                        <td><h6 class="numeros">{{redondeo($reporte[$i]-> total)}}</h6></td>
                        @if($saldos[$i] == 0)
                            <td><h6 class="numeros">{{ redondeo($reporte[$i]-> total)}}</h6></td>
                            @if($reporte[$i]->numero != $reporte[$i-1]->numero)
                                {{array_push($soles, $reporte[$i]->total)}}
                            @endif
                        @else
                            <td><h6 class="numeros">{{ redondeo($reporte[$i]-> total - $saldos[$i])}}</h6></td>

                            @if($reporte[$i]->numero != $reporte[$i-1]->numero)
                                {{array_push($soles, $reporte[$i]->total - $saldos[$i])}}
                            @endif
                        @endif
                        <td><h6 class="numeros">0.00</h6></td>
                        <td><h6 class="numeros">0.00</h6></td>
                    @else
                        <td><h6 class="numeros">0.00</h6></td>
                        <td><h6 class="numeros">0.00</h6></td>
                        <td><h6 class="numeros">{{ redondeo($reporte[$i]-> total)}}</h6></td>
                        @if($saldos[$i] == 0)
                            <td><h6 class="numeros">{{ redondeo($reporte[$i]-> total)}}</h6></td>

                            @if($reporte[$i]->numero != $reporte[$i-1]->numero)
                                {{array_push($dolares, $reporte[$i]->total)}}
                            @endif
                        @else
                            <td><h6 class="numeros">{{redondeo($reporte[$i]-> total - $saldos[$i])}}</h6></td>
                            @if($reporte[$i]->numero != $reporte[$i-1]->numero)
                                {{array_push($dolares, $reporte[$i]->total - $saldos[$i])}}
                            @endif
                        @endif
                    @endif
                </tr>
            @endif

            @if ($i == 0)
                <tr>
                    <td><h6>{{ $reporte[$i]-> serie }}-{{ $reporte[$i]-> numero }}</h6></td>
                    <td><h6 class="descripcion"><?php
                        $descripcion = $reporte[$i]-> descripcion;
                        $temp='';
                        for($j=0; $j<13; $j++){
                            $temp = $temp.$descripcion[$j];
                        }
                        echo $temp.'...';
                    ?></h6></td>
                    <td><h6>{{ $reporte[$i]-> emision}}</h6></td>
                    <td><h6>{{ $reporte[$i]-> vencimiento}}</h6></td>
                    <td><h6 class="numeros">{{ $reporte[$i]-> dias_retraso}}</h6></td>
                    @if($reporte[$i]->moneda == '1')
                        <td><h6 class="numeros">{{ redondeo($reporte[$i]-> total)}}</h6></td>
                        @if($saldos[$i] == 0)
                            <td><h6 class="numeros">{{ redondeo($reporte[$i]-> total)}}</h6></td>
                                {{array_push($soles, $reporte[$i]->total)}}
                        @else
                            <td><h6 class="numeros">{{ redondeo($reporte[$i]-> total - $saldos[$i])}}</h6></td>
                                {{array_push($soles, $reporte[$i]->total - $saldos[$i])}}
                        @endif
                        <td><h6 class="numeros">0.00</h6></td>
                        <td><h6 class="numeros">0.00</h6></td>
                    @else
                        <td><h6 class="numeros">0.00</h6></td>
                        <td><h6 class="numeros">0.00</h6></td>
                        <td><h6 class="numeros">{{ redondeo($reporte[$i]-> total)}}</h6></td>
                        @if($saldos[$i] == 0)
                            <td><h6 class="numeros">{{ redondeo($reporte[$i]-> total)}}</h6></td>
                                {{array_push($dolares, $reporte[$i]->total)}}
                        @else
                            <td><h6 class="numeros">{{redondeo($reporte[$i]-> total - $saldos[$i])}}</h6></td>
                                {{array_push($dolares, $reporte[$i]->total - $saldos[$i])}}
                        @endif
                    @endif
                </tr>
            @endif
        @endfor
        <?php
            $temp_soles = 0;
            $temp_dolares = 0;
            for ($i=0; $i < sizeof($soles); $i++) {
                $temp_soles = $temp_soles + $soles[$i];
            }
            for ($i=0; $i < sizeof($dolares); $i++) {
                $temp_dolares = $temp_dolares + $dolares[$i];
            }
            echo "<tr>
                    <th colspan=5>SALDO TOTAL HASTA ".$datos_cli -> hoy."</th>
                    <th class=\"tp_moneda\">Soles:</th>
                    <td class=\"montos\"><h6 class=\"numeros\">".redondeo($temp_soles)."</h6></td>
                    <th class=\"tp_moneda\">Dolares:</th>
                    <td class=\"montos\"><h6 class=\"numeros\">".redondeo($temp_dolares)."</h6></td>
                </tr>
            ";
        ?>
    </table>
    <br>
    <br>
    <br>
    <table id="banco">
        <tr>
            <th class="cuentas">
                <h5 class="h5_cuent">Cuentas corrientes:</h5>
            </th>    
        </tr>
        @for($i=0; $i < 3; $i++)
            <tr>
                <td class="cuentas">
                    <h5 class="h5_cuent h5_cuent_datos">
                        {{$cuentas_bancarias[$i] -> nombre}} {{$cuentas_bancarias[$i] -> simbolo}}
                    </h5>
                </td>
                <td>
                    <h5 id="nro_cuenta" class="h5_cuent h5_cuent_datos">
                        {{$cuentas_bancarias[$i] -> nro_cuenta}}
                    </h5> 
                </td> 
            </tr>
        @endfor
        <tr>
            <th class="cuentas">
                <h5 class="h5_cuent">Cuenta de Detracciones:</h5>
            </th>    
        </tr>
        <tr>
            <td class="cuentas">
                <h5 class="h5_cuent h5_cuent_datos">
                    {{$cuentas_bancarias[4] -> nombre}} {{$cuentas_bancarias[4] -> simbolo}}
                </h5>
            </td>
            <td>
                <h5 id="nro_cuenta" class="h5_cuent h5_cuent_datos">
                    {{$cuentas_bancarias[4] -> nro_cuenta}}
                </h5> 
            </td> 
        </tr>
    </table>
</body>
</html>
