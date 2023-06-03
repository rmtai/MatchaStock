<?php
    if($_SERVER["REQUEST_METHOD"] == "POST"){
        require_once 'conexion.php';
        $idUser = $_POST["idUser"];

        $stmt = $mysql->prepare("DELETE FROM usuario WHERE idUser = ?");
        $stmt->bind_param("i", $idUser);

        if($stmt->execute()){
            echo 'Registro Eliminado con Exito';
        } else {
            echo 'Error al eliminar el registro';
        }

        $stmt->close();
        $mysql->close();
    } else {
        echo 'Unknown error';
    }
?>