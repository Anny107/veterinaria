<?php

require_once 'Conexion.php';

class Mascota extends Conexion{

  private $conexion;

  public function __CONSTRUCT(){
    $this->connection = parent::getConexion();
  }

  public function registrarMascota($datos = []){
    $respuesta=[
      "status" => false,
      "mensaje" => ""
    ];
    try{
      $consulta = $this->connection->prepare("CALL spu_registrarMascota(?,?,?,?,?,?)");
      $respuesta["status"] = $consulta->execute(
        array(
          $datos["idcliente"],
          $datos["idraza"],
          $datos["nombre"],
          $datos["fotografia"],
          $datos["color"],
          $datos["genero"]
        )
      );
    }
    catch(Exception $e){
      $respuesta["mensaje"] = "No se logro guardar. Codigo ".$e->getCode();
    }
    return $respuesta;
  }
}