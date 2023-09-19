<?php

require_once 'Conexion.php';

class Cliente extends Conexion{

  private $conexion;

  public function __CONSTRUCT(){
    $this->connection = parent::getConexion();
  }

  public function registrarCliente($datos = []){
    $respuesta=[
      "status" => false,
      "mensaje" => ""
    ];
    try{
      $consulta = $this->connection->prepare("CALL spu_registrarCliente(?,?,?,?)");
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

  public function buscarCliente($dni){
    try{
      $consulta = $this->connection->prepare("CALL spu_buscarClientes(?)");
      $consulta->execute(array($dni));
      return $consulta->fetchAll(PDO::FETCH_ASSOC);
    }
    catch(Exception $e){
      die($e->getMessage());
    }
  }
}