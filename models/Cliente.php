<?php

require_once 'Conexion.php';

class Cliente extends Conexion{

  private $connection;

  public function __CONSTRUCT(){
    $this->connection = parent::getConexion();
  }

  public function registrarCliente($datos = []){
    $respuesta=[
      "status" => false,
      "mensaje" => ""
    ];
    try{
      $consulta = $this->connection->prepare("CALL spu_registrar_Cliente(?,?,?,?)");
      $respuesta["status"] = $consulta->execute(
        array(
          $datos["apellidos"],
          $datos["nombres"],
          $datos["dni"],
          $datos["claveAcceso"],
        )
      );
    }
    catch(Exception $e){
      $respuesta["mensaje"] = "No se logro guardar. Codigo ".$e->getCode();
    }
    return $respuesta;
  }

  public function login($dni){
    try{
      $consulta = $this->connection->prepare("CALL spu_login_cliente(?)");
      $consulta->execute(array($dni));
      return $consulta->fetch(PDO::FETCH_ASSOC);
    }
    catch(Exception $e){
      die($e->getMessage());
    }
  }
}