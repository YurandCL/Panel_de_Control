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
  <br><br><br>
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
                        <td>{{ $saldos[$i]}}</td>
                        {{array_push($soles, $reporte[$i]->total)}}
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
                        <td>{{ $saldos[$i]}}</td>
                        {{array_push($dolares, $reporte[$i]->total)}}
                    @endif
            @endif
        </tr>
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
</body>
</html>
