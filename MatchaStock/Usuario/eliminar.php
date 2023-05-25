<?php
    
    if($_SERVER["REQUEST_METHOD"] == "POST"){
        require_once 'conexion.php';
        $idUser = $_POST["idUser"];
        $my_query = "delete from usuario where idUser =".$idUser;
        $result = $mysql->query($my_query);

        if($result == true){
            echo 'Registro Eliminado con Exito';
        } else { 
            echo 'error';
        }
    } else {
        echo 'unknown error';
    }

?>