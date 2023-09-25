<?php

require_once '../models/Cliente.php';
$cliente = new Cliente();

if(isset($_POST['operacion'])){

  switch($_POST['operacion']){
    case 'registrarCliente':
      
      $claveOriginal = $_POST['claveAcceso'];
      $claveEncriptada = password_hash($claveOriginal, PASSWORD_BCRYPT);

      $datos = [
        "apellidos"   => $_POST['apellidos'],
        "nombres"     => $_POST['nombres'],
        "dni"         => $_POST['dni'],
        "claveAcceso" => $_POST['claveAcceso']
      ];
      echo json_encode($cliente->registrarCliente($datos));
      break;

    case 'login':
      $resultado = [
        "login"     => false,
        "apellidos" => "",
        "nombres"   => "",
        "mensaje"   => ""
      ];

      $data = $cliente->login($_POST['dni']);
      
      if($data){
        $claveIngresada = $_POST['claveAcceso'];
        if(password_verify($claveIngresada, $data["claveAcceso"])){
          $resultado['login'] = true;
          $resultado['apellidos'] = $data['apellidos'];
          $resultado['nombres'] = $data['nombres'];
        }else{
          $resultado['mensaje'] = 'Contraseña incorrecta';
        }
      }else{
        $resultado['mensaje'] = 'No existe el usuario';
      }
      echo json_encode($resultado);
      break;

    case 'buscarCliente':
      echo json_encode($cliente->buscarCliente($_POST['dni']));
      break;
  }
  
}