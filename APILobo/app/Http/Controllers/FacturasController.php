<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;

use App\Http\Requests;

class FacturasController extends Controller
{
    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function getIndex()
    {
        return 'FacturasController';
    }

    public function getFacturaCod(){
        return 'nombre de la empresa';
    }

    public function postFacturaCod(){
        return 'nombre de la empresa o razon social';
    }

    public function getFacturaFVencimiento(){
        return 'codigo de la factura';
    }

    public function postFacturaFVencimiento(){
        return 'ncodigo de la factura';
    }

    public function getFacturaEstado(){
        return 'nombre de la empresa';
    }

    public function postFacturaEstado(){
        return 'nombre de la empresa o razon social';
    }

    public function getFacturaClienteCod(){
        return 'nombre de la empresa';
    }

    public function postFacturaClienteCod(){
        return 'nombre de la empresa o razon social';
    }

    public function getFacturaTotal(){
        return 'nombre de la empresa';
    }

    public function postFacturaTotal(){
        return 'nombre de la empresa o razon social';
    }

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
