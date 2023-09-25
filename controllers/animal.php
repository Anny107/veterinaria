<?php

require_once '../models/Animal.php';
$animal = new Animal();
if(isset($_GET['operacion'])){
    switch($_GET['operacion']){
        case 'listarAnimales':
            echo json_encode($raza->listarAnimales());
            break;
        case 'fitrarRaza':
            $data = $raza->fitrarRaza($_GET['idanimal']);
            echo json_encode($data);
            break;
    }
}