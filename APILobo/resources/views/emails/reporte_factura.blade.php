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
        <h1>REPORTE DE FACTURAS VENCIDAS</h1>
    </div>
    @foreach($reporte as $datos_cli)
    @endforeach
    <p>RUC: {{$datos_cli-> ruc}}</p>
    <p>CLIENTE: {{$datos_cli-> cliente}}</p>
    <p>FECHA: {{$datos_cli -> hoy}}</p>
  <br>
 <table id="detalles">
    <tr>
        <th>Factura</th>
        <th>Descripción</th>
        <th>Emisión</th>
        <th>Vencimiento</th>
        <th>Retraso</th>
        <th>Total S/</th>
        <th>Saldo S/</th>
        <th>Total $</th>
        <th>Saldo $</th>
    </tr>

    @for ($i = 0; $i < sizeof($reporte); $i++)
        @if($i>0 && $reporte[$i]->numero != $reporte[$i-1]->numero)
            <tr>
                <td>{{ $reporte[$i]-> serie }}-{{ $reporte[$i]-> numero }}</td>
                <td>{{ $reporte[$i]-> descripcion}}</td>
                <td>{{ $reporte[$i]-> emision}}</td>
                <td>{{ $reporte[$i]-> vencimiento}}</td>
                <td>{{ $reporte[$i]-> dias_retraso}}</td>
                @if($reporte[$i]->moneda == '1')
                    <td>{{ $reporte[$i]-> total}}</td>
                    @if($saldos[$i] == 0)
                        <td>{{ $reporte[$i]-> total}}</td>
                        @if($reporte[$i]->numero != $reporte[$i-1]->numero)
                            {{array_push($soles, $reporte[$i]->total)}}
                        @endif
                    @else
                        <td>{{ $reporte[$i]-> total - $saldos[$i]}}</td>

                        @if($reporte[$i]->numero != $reporte[$i-1]->numero)
                            {{array_push($soles, $reporte[$i]->total - $saldos[$i])}}
                        @endif
                    @endif
                    <td>0</td>
                    <td>0</td>
                @else
                    <td>0</td>
                    <td>0</td>
                    <td>{{ $reporte[$i]-> total}}</td>
                    @if($saldos[$i] == 0)
                        <td>{{ $reporte[$i]-> total}}</td>

                        @if($reporte[$i]->numero != $reporte[$i-1]->numero)
                            {{array_push($dolares, $reporte[$i]->total)}}
                        @endif
                    @else
                        <td>{{$reporte[$i]-> total - $saldos[$i]}}</td>
                        @if($reporte[$i]->numero != $reporte[$i-1]->numero)
                            {{array_push($dolares, $reporte[$i]->total - $saldos[$i])}}
                        @endif
                    @endif
                @endif
            </tr>
        @endif

        @if ($i == 0)
            <tr>
                <td>{{ $reporte[$i]-> serie }}-{{ $reporte[$i]-> numero }}</td>
                <td>{{ $reporte[$i]-> descripcion}}</td>
                <td>{{ $reporte[$i]-> emision}}</td>
                <td>{{ $reporte[$i]-> vencimiento}}</td>
                <td>{{ $reporte[$i]-> dias_retraso}}</td>
                @if($reporte[$i]->moneda == '1')
                    <td>{{ $reporte[$i]-> total}}</td>
                    @if($saldos[$i] == 0)
                        <td>{{ $reporte[$i]-> total}}</td>
                            {{array_push($soles, $reporte[$i]->total)}}
                    @else
                        <td>{{ $reporte[$i]-> total - $saldos[$i]}}</td>
                            {{array_push($soles, $reporte[$i]->total - $saldos[$i])}}
                    @endif
                    <td>0</td>
                    <td>0</td>
                @else
                    <td>0</td>
                    <td>0</td>
                    <td>{{ $reporte[$i]-> total}}</td>
                    @if($saldos[$i] == 0)
                        <td>{{ $reporte[$i]-> total}}</td>
                            {{array_push($dolares, $reporte[$i]->total)}}
                    @else
                        <td>{{$reporte[$i]-> total - $saldos[$i]}}</td>
                            {{array_push($dolares, $reporte[$i]->total - $saldos[$i])}}
                    @endif
                @endif
            </tr>
        @endif
    @endfor
  </table>
  <br><br>
  <?php
    $temp_soles = 0;
    $temp_dolares = 0;
    for ($i=0; $i < sizeof($soles); $i++) {
        $temp_soles = $temp_soles + $soles[$i];
    }
    for ($i=0; $i < sizeof($dolares); $i++) {
        $temp_dolares = $temp_dolares + $dolares[$i];
    }
    echo "<table id=\"totales\">
            <tr>
                <th>Saldo Total</th>
                <td>S/. ".$temp_soles."</td>
                <td>$ ".$temp_dolares."</td>
            </tr>
         </table>
        ";
  ?>
  <br><br><br>
  <table id="banco">
    
    <tr>
        <th class="cuentas">
            <h5>Cuentas Corrientes:</h5>
        </th>    
    </tr>
    <tr>
        <td class="cuentas cuentas_td">
            <h5>BCP S/. 215 - 1785352074</h5>
        </td>
        <td class="cuentas">
            <h5>CCI : 00221500178535207424</h5>
        </td>
    </tr>
    <tr>
        <td class="cuentas cuentas_td">
            <h5>BCP $.USD 215-2248711-1-78</h5>
        </td>
        <td class="cuentas">
            <h5>CCI : 00221500224871117827</h5>
        </td>
    </tr>
    <tr>
        <td class="cuentas cuentas_td">
            <h5>BBVA S/. 0011 0226 0100020989 88</h5>
        </td>
        <td class="cuentas">
            <h5>CCI : 011-226-000100020989-88</h5>
        </td>
    </tr>
    
    <tr>
        <th class="cuentas">
            <h5>Cuentas de Detracciones:</h5>
        </th>
    </tr>
    <tr>
        <td class="cuentas cuentas_td">
            <h5>BN  S/. 00-113-004460</h5>
        </td>
    </tr>
  </table>
</body>
</html>
