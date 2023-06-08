<?php

    if($_SERVER["REQUEST_METHOD"] == "POST"){
        require_once 'conexion.php';
        $idUser = $_POST["idUser"];
        $passwordUser=$_POST["passwordUser"];

        $my_query = "update usuario set passwordUser= '".$passwordUser."'
        where idUser= '".$idUser."'";
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