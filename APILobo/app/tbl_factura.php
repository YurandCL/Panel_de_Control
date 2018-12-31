<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class tbl_factura extends Model
{
    protected $fiillable = ['factura_cod','factura_cliente_cod', 'factura_fvencimiento', 'factura_total', 'factura_estado'];

    public function pagos(){
        return $this->hasMany('APILobo\tbl_pagos');
    }

    public function propietario(){
        return $this->belongsTo('APILobo\tbl_empresa');
    }
}