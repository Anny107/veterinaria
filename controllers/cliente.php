<?php

require_once '../models/Cliente.php';
$cliente = new Cliente();

if(isset($_GET['operacion'])){

  switch($_GET['operacion']){
    case 'registrarCliente':
      
      $datos = [
        "apellidos"   => $_GET['apellidos'],
        "nombres"     => $_GET['nombres'],
        "dni"         => $_GET['dni'],
        "claveAcceso" => password_hash($_GET['claveAcceso'], PASSWORD_BCRYPT)
      ];
      $cliente->registrarCliente($datos);
      break;

    case 'login':
      $resultado = [
        "login"     => false,
        "mensaje"   => "",
        "idcliente" => 0
      ];

      $data = $cliente->login($_GET['dni']);
      
      if($data){
        $claveIngresada = $_GET['claveAcceso'];
        if(password_verify($claveIngresada, $data["claveAcceso"])){
          $resultado["login"] = true;
          $resultado["idcliente"] = $data["idcliente"];
        }else{
          $resultado["mensaje"] = "Contraseña incorrecta";
        }
      }else{
        $resultado["mensaje"] = "No existe el usuario";
      }
      echo json_encode($resultado);
      break;
  }
}

if(isset($_POST['operacion'])){
  switch($_POST['operacion']){
    case 'registrarCliente':
      $datos = [
        "apellidos"   => $_POST['apellidos'],
        "nombres"     => $_POST['nombres'],
        "dni"         => $_POST['dni'],
        "claveAcceso" => password_hash($_POST['claveAcceso'], PASSWORD_BCRYPT)
      ];
      $cliente->registrarCliente($datos);
      break;
  }
}