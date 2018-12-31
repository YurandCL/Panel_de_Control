<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;

use App\Http\Requests;

class PagosController extends Controller
{
    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function getIndex()
    {
        return 'PagosController';
    }

    public function getPagosCod(){
        return 'nombre de la empresa';
    }

    public function postPagosCod(){
        return 'nombre de la empresa o razon social';
    }

    public function getPagosMonto(){
        return 'nombre de la empresa';
    }

    public function postPagosMonto(){
        return 'nombre de la empresa o razon social';
    }

    public function getPagosFacturaCod(){
        return 'codigo de la factura';
    }

    public function postPagosFacturaCod(){
        return 'ncodigo de la factura';
    }

    public function getPagosFechaPago(){
        return 'nombre de la empresa';
    }

    public function postPagosFechaPago(){
        return 'nombre de la empresa o razon social';
    }

    public function getPagosEmpresaCod(){
        return 'nombre de la empresa';
    }

    public function postPagosEmpresaCod(){
        return 'nombre de la empresa o razon social';
    }

    /**
     * Show the form for creating a new resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function create()
    {
        //
    }

    /**
     * Store a newly created resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @return \Illuminate\Http\Response
     */
    public function store(Request $request)
    {
        //
    }

    /**
     * Display the specified resource.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function show($id)
    {
        //
    }

    /**
     * Show the form for editing the specified resource.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function edit($id)
    {
        //
    }

    /**
     * Update the specified resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function update(Request $request, $id)
    {
        //
    }

    /**
     * Remove the specified resource from storage.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function destroy($id)
    {
        //
    }
}
