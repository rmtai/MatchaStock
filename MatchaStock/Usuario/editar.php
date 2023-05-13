<?php
    
    if($_SERVER["REQUEST_METHOD"] == "POST"){
        require_once 'conexion.php';
        $idUser = $_POST["idUser"];
        $nombre= $_POST["nombre"];
        $apellido=$_POST["apellido"];
        $username= $_POST["username"];
        $passwordUser=$_POST["passwordUser"];
        $email=$_POST["email"];

        $my_query = "update usuario set nombre= '".$nombre."', apellido= '".$apellido."', username= '".$username."', passwordUser= '".$passwordUser."', email= '".$email."' where idUser=".$idUser;
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