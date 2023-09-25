<?php

require_once '../models/Mascota.php';

$mascota = new Mascota();

if(isset($_POST['operacion'])){
  
  switch($_POST['operacion'] ){
    case 'registrarMascota':
      $datos = [
        "idcliente"   => $_POST['idcliente'],
        "idraza"      => $_POST['idraza'],
        "nombre"      => $_POST['nombre'],
        "fotografia"  => $_POST['fotografia'],
        "color"       => $_POST['color'],
        "genero"      => $_POST['genero']
      ];
      echo json_encode($mascota->registrarMascota($datos));
      break;

      case 'mostrarMascota':
        echo json_encode($mascota->mostrarMascota(['idmascota' => $_POST['idmascota']]));
  }
}