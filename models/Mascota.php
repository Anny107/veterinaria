<?php

require_once 'Conexion.php';

class Mascota extends Conexion{

  private $connection;

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

  public function buscarMascota($idmascota = 0){
    try{
      $consulta = $this->connection->prepare("CALL spu_detalle_Mascota(?)");
      $consulta->execute(array($idmascota));
      return $consulta->fetch(PDO::FETCH_ASSOC);
    }
    catch(Exception $e){
      die($e->getMessage());
    }
  }

  public function listarMascotas($idcliente = 0){
    try{
      $consulta = $this->connection->prepare("CALL spu_listar_mascotas_clientes(?)");
      $consulta->execute(
        array(
          $idcliente
        ));
        return $consulta->fetchAll(PDO::FETCH_ASSOC);
    }
    catch(Exception $e){
      die($e->getMessage());
    }
  }
}