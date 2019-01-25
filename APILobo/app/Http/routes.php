<?php
//obtenemos todas las empresas que deban alguna factura
Route::get('empresas', 'EmpresasController@getListado');
//devuelve JSON

//------------------------------------------------------------------------------------

//Obtenemos todos los pagos que se hicieron durante un mes especificado de todos los
//años desde el 2016 hasta el actual
Route::get('reportes/ventas', 'FacturasController@facturasMonto');
//devuelve JSON

//------------------------------------------------------------------------------------

//Obtenemos todos los datos necesarios de una factura seleccionada por su número
Route::get('reportes/clientes/{factura_num}', 'FacturasController@datos_factura');
//devuelve JSON

//------------------------------------------------------------------------------------

//Obtenemos la suma de todos los pagos que se hicieron durante el mes actual
Route::get('pagos/mes','PagosController@pagosMensuales');
//devuelve JSON

//------------------------------------------------------------------------------------

//Obtenemos la suma de todos los pagos que se hicieron durante el dia de hoy
Route::get('pagos/dia','PagosController@pagosDiarios');
//devuelve JSON

//------------------------------------------------------------------------------------

//Obtenemos los correos de todos los usuarios que pertenezcan a la empresa seleccionada
//por su numero de ruc único
Route::get('usuarios/{ruc}','ContactosController@cargarUsuarios');
//devuelve JSON

//------------------------------------------------------------------------------------

//Se envia el correo electronico al usuario seleccionado con los datos de la factura
//también seleccionada anteriormente
//Se genera el reporte de acuerdo al numero de factura pasado como parametro
Route::get('pdf','EmailController@enviarCorreo_pdf');
//devuelve JSON

//------------------------------------------------------------------------------------

//Se envia el correo electronico al usuario seleccionado con 
//algunos datos de sus facturas
//Se genera el reporte de acuerdo al ruc de la empresa pasado como parametro
Route::get('reporte/factura','EmailController@reporte_facturas');
//devuelve JSON

//------------------------------------------------------------------------------------

//Se hace la verificación del usuario y contraseña mandadas por 
//parametro a la ruta, ambas se buscan en la base de datos y se 
//verifica si dicho usuario se encuentra activo en la empresa
Route::get('login','LoginController@logeo');
//devuelve JSON

//------------------------------------------------------------------------------------
//aquí debajo nueva función si fuera necesaria
// | | | | | | | | | | | | | | | | | | | | | |
// V V V V V V V V V V V V V V V V V V V V V V