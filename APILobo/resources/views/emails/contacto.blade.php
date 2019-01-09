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
  <table id="datos_cli">
    @foreach($datos as $datos_cli)
      @endforeach
      <tr>
        <th>FECHA DE EMISIÓN:</th>
        <td>{{ $datos_cli-> femision }}</td>
      </tr>
      <tr>
        <th>FECHA DE VENCIMIENTO: </th>
        <td>{{ $datos_cli-> fvencimiento}}</td>
      </tr>
      <tr>
        <th>CLIENTE: </th>
        <td>{{ $datos_cli-> cliente}}</td>
      </tr>
      <tr>
        <th>RUC: </th>
        <td>{{ $datos_cli-> ruc}}</td>
      </tr>
      <tr>
        <th>MONEDA: </th>
        <td>{{ $datos_cli-> moneda}}</td>
      </tr>
  </table>
  <br>
    <table id="fac_datos">
        <thead>
            <tr >
                <th class="datos_fac" colspan="2">CANTIDAD:</th>
                <th class="datos_fac" colspan="3">DESCIPCIÓN:</th>
                <th class="datos_fac" colspan="2">VALOR UNITARIO:</th>
            </tr>
        </thead>
        <tbody>
            @foreach($datos as $datos_fac)
                <tr>
                    <td class="monto" colspan="2">{{ $datos_fac-> cantidad}}</td>
                    <td class="monto" colspan="3">{{ $datos_fac-> descripcion}}</td>
                    <td class="monto" colspan="2">{{ $datos_fac-> valor_unitario}}</td>
                </tr>
            @endforeach
        </tbody>
    </table>
    <br>
    <table id="tbl_izquierda">
        <tr >
            <th>A CUENTA:</th>
            <td>{{ $datos_fac-> a_cuenta}}</td>
        </tr>
        <tr>
            <th>IGV: </th>
            <td>{{ $datos_fac-> total - $datos_fac->opgravadas}}</td>
        </tr>
        <tr>
            <th>TOTAL:</th>
            <td>{{ $datos_fac-> total}}</td>
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
