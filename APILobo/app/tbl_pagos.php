<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class tbl_pagos extends Model
{
    protected $fiillable = ['pagos_cod','pagos_monto','pagos_fechapago', 'pagos_factura_cod', 'pagos_empresa_cod'];

    public function propietario(){
        return $this->belongsTo('APILobo\tbl_factura');
    }
}