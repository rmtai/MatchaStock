<?php
    
    if($_SERVER["REQUEST_METHOD"] == "POST"){
        require_once 'conexion.php';
        $idItem = $_POST["idItem"];
        $my_query = "UPDATE producto SET estado = 3 WHERE idItem =".$idItem;
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