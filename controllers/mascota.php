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
  }
}
if(isset($_GET['operacion'])){
  switch($_GET['operacion']){
    case 'listarMascotas':
      $datos = $mascota->listarMascotas($_GET["idcliente"]);
      echo json_encode($datos);
      break;
      
    case 'buscarMascota':
      $datos = $mascota->buscarMascota($_GET["idmascota"]);
      echo json_encode($datos);
      break;
  }
}