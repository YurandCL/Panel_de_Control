<?php
//obtenemos todas las empresas que deban alguna factura
Route::get('empresas', 'EmpresasController@getListado');

//Obtenemos todos los pagos que se hicieron durante un mes especificado de todos los
//años desde el 2016 hasta el actual
Route::get('reportes/ventas', 'FacturasController@facturasMonto');

//Obtenemos todos los datos necesarios de una factura seleccionada por su número
Route::get('reportes/clientes/{factura_num}', 'FacturasController@datos_factura');

//Obtenemos la suma de todos los pagos que se hicieron durante el mes actual
Route::get('pagos/mes','PagosController@pagosMensuales');

//Obtenemos la suma de todos los pagos que se hicieron durante el dia de hoy
Route::get('pagos/dia','PagosController@pagosDiarios');

//Obtenemos los correos de todos los usuarios que pertenezcan a la empresa seleccionada
//por su numero de ruc único
Route::get('usuarios/{ruc}','ContactosController@cargarUsuarios');

//Se envia el correo electronico al usuario seleccionado con los datos de la factura
//también seleccionada anteriormente
//Se genera el reporte de acuerdo al numero de factura pasado como parametro
Route::get('pdf/','EmailController@enviarCorreo_pdf');
