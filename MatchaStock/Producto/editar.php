<?php
    
    if($_SERVER["REQUEST_METHOD"] == "POST"){
        require_once 'conexion.php';
        $idItem = $_POST["idItem"];
        $nombreProd= $_POST["nombreProd"];
        $descripcionProd=$_POST["descripcionProd"];
        $cantidadProd= $_POST["cantidadProd"];
        $idUser=$_POST["idUser"];

        $my_query = "UPDATE producto SET nombreProd= '".$nombreProd."', descripcionProd= '".$descripcionProd."', cantidadProd= '".$cantidadProd."', idUser= '".$idUser."', estado= 2 WHERE idItem=".$idItem;
        $result = $mysql->query($my_query);

        if($result == true){
            echo 'Registro Actualizado con Exito';
        } else { 
            echo 'error';
        }
    } else {
        echo 'unknown error';
    }

?>