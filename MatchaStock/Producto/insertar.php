<?php

    if($_SERVER["REQUEST_METHOD"]== "POST"){
        require_once 'conexion.php';
        $nombreProd= $_POST["nombreProd"];
        $descripcionProd=$_POST["descripcionProd"];
        $cantidadProd= $_POST["cantidadProd"];
        $idUser=$_POST["idUser"];
        $my_query = "INSERT INTO producto(nombreProd, descripcionProd, cantidadProd, idUser, estado) VALUES ('".$nombreProd."', '".$descripcionProd."', '".$cantidadProd."', '".$idUser."', 1)";
        $result = $mysql->query($my_query);
        if ($result == true) {
            echo "Realizado con exito";
        }else{
            echo "error";
        }
    }
?>