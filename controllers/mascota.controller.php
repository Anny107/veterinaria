<?php

require_once '../models/Mascota.php';

if(isset($_POST['operacion'])){
  $mascota = new Mascota();

  if($_POST['operacion'] == 'registrarMascota'){
    $datos = [
      "idcliente" => $_POST['idcliente'],
      "idraza" => $_POST['idraza'],
      "nombre" => $_POST['nombre'],
      "fotografia" => $_POST['fotografia'],
      "color" => $_POST['color'],
      "genero" => $_POST['genero'],
    ];
    $respuesta = $mascota->registrarMascota($datos);
    echo json_encode($respuesta);
  }
}