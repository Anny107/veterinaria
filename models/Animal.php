<?php

require_once 'Conexion.php';

class Animal extends Conexion{

    private $connection;

    public function __CONSTRUCT(){
        $this->connection = parent::getConexion();
    }

    public function listarAnimales(){
        try{
            $consulta = $this->connection->prepare("CALL spu_listar_animales()");
            $consulta->execute();
            return $consulta->fetchAll(PDO::FETCH_ASSOC);
        }
        catch(Exception $e){
            die($e->getCode());
        }
    }
    public function filtrarRaza($idanimal){
        try{
            $consulta = $this->connection->prepare("CALL spu_filtrar_Raza(?)");
            $consulta->execute(
                array($idanimal)
            );
            return $consulta->fetchAll(PDO::FETCH_ASSOC);
        }
        catch(Exception $e){
            die($e->getCode());
        }
    }
}