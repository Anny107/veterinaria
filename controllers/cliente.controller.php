<?php

require_once '../models/Cliente.php';

if(isset($_POST['operacion'])){
  $cliente = new Cliente();

  if($_POST['operacion'] == 'registrarCliente'){
    $datos = [
      "apellidos"   => $_POST['apellidos'],
      "nombres"     => $_POST['nombres'],
      "dni"         => $_POST['dni'],
      "claveAcceso" => $_POST['claveAcceso'],
    ];
    $respuesta = $cliente->registrarCliente($datos);
    echo json_encode($respuesta);
  }

  if($_POST['operacion'] == 'buscarCliente'){
    $dato = $cliente->buscarCliente($_POST['dni']);
    echo json_encode($dato);
  }
}