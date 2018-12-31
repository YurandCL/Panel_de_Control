<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class tbl_empresa extends Model
{
    protected $fiillable = ['empresa_cod'/*se enlaza con el campo factura_cliente_cod de la tabla factura*/,'empresa_razonsocial'];

    public function facturas(){
        return $this->hasMany('APILobo\tbl_factura');
    }
}


/*namespace App;

use Illuminate\Foundation\Auth\User as Authenticatable;

class User extends Authenticatable
{
    /**
     * The attributes that are mass assignable.
     *
     * @var array
     */
    /*protected $fillable = [
        'name', 'email', 'password',
    ];

    /**
     * The attributes excluded from the model's JSON form.
     *
     * @var array
     */
    /*protected $hidden = [
        'password', 'remember_token',
    ];
}*/